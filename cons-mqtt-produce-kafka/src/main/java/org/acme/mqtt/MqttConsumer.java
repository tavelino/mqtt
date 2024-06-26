package org.acme.mqtt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.acme.MqttSendMessage;
import org.acme.kafka.KafkaSend;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@RegisterForReflection
public class MqttConsumer {

    @Inject
    @ConfigProperty(name = "mqtt.artemis.server")
    private String MQTT_BROKER;

    @Getter
    @Setter
    private String TOPIC = "mqtt-message-in/1/2/app/test";

    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    Tracer tracer;

    public String transformTopic() {
        // Split the topic string by '/'
        String[] parts = TOPIC.split("/");

        // Join the last two parts with a dot
        return parts[parts.length - 2] + "." + parts[parts.length - 1];
    }

    public String transformKey() {
        // Find the index of the third occurrence of "/"
        int thirdSlashIndex = TOPIC.indexOf('/', TOPIC.indexOf('/') + 1);
        thirdSlashIndex = TOPIC.indexOf('/', thirdSlashIndex + 1);

        // Extract the substring up to the third occurrence of "/"
        String firstThreeParts = TOPIC.substring(0, thirdSlashIndex);

        // Replace remaining "/" with "."
        return firstThreeParts.replace('/', '.');
    }

    public void startMqttConsumer() {
        tracer = GlobalOpenTelemetry.getTracer("cons-mqtt-produce-kafka", "1.0");
        Span span = tracer.spanBuilder("Consumer-Message-Mqtt")
                .setSpanKind(SpanKind.PRODUCER).setAttribute("topic", TOPIC)
                .startSpan();
        managedExecutor.execute(() -> {
            try {
                try (Scope scope = span.makeCurrent()) {
                    MqttClient mqttClient = new MqttClient(MQTT_BROKER,
                            MqttClient.generateClientId());
                    mqttClient.connect();
                    System.out.println("Connected to MQTT broker: " + MQTT_BROKER);

                    mqttClient.subscribe("#", (topic, message) -> {
                        MqttSendMessage receivedMessage = deserialize(message.getPayload());
                        System.out.println("Received message: " + receivedMessage.getMessage());
                        KafkaSend producer = new KafkaSend();
                        producer.sendMessage(receivedMessage, transformKey(), transformTopic());

                    });
                
                    span.end();
            }finally {
                // End the span
                span.end();
            }}  catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    void onStart(@Observes StartupEvent ev) {
        System.out.println("Connecting to MQTT broker: " + MQTT_BROKER);

        startMqttConsumer();
    }

    private MqttSendMessage deserialize(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (MqttSendMessage) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Handle error gracefully
        }
    }
}
