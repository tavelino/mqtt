package org.acme.mqtt;

import org.acme.MqttSendMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
public class MqttProducer {

    @Inject
    @ConfigProperty(name = "mqtt.artemis.server")
    private String MQTT_BROKER = "tcp://ex-aao-hdls-svc:61616";

    @Getter
    @Setter
    private String topic = "";

    @Inject
    Tracer tracer;

    public void Produce(MqttSendMessage mqttMes) {
        tracer = GlobalOpenTelemetry.getTracer("mqtt-kafka", "1.0");

        Span span = tracer.spanBuilder("Producer-Message-Mqtt")
                .setSpanKind(SpanKind.PRODUCER).setAttribute("topic", topic)
                .startSpan();
        System.out.println("Enviando mensagens para o topico: " + topic);
        System.out.println("Enviando mensagens: " + mqttMes.getMessage());

        try {
            try (Scope scope = span.makeCurrent()) {

                MqttClient mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
                mqttClient.connect();
                System.out.println("Enviando mensagens: " + mqttMes.getMessage() + "para o topico" + topic);

                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setPayload(mqttMes.serialize());

                mqttClient.publish(topic, mqttMessage);

                mqttClient.disconnect();
                mqttClient.close();
            } finally {
                // End the span
                span.end();
            }
        } catch (MqttException e) {
            System.out.println("Failed to publish message to MQTT broker: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
