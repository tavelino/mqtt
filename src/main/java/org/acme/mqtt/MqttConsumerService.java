package org.acme.mqtt;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.paho.client.mqttv3.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
@RegisterForReflection
public class MqttConsumerService {

    private static final String MQTT_BROKER = "tcp://localhost:61616";
    private static final String TOPIC = "mqtt-message-in/1/2/app/test";

    @Inject
    ManagedExecutor managedExecutor;

    public static String transformTopic() {
        // Split the topic string by '/'
        String[] parts = TOPIC.split("/");

        // Join the last two parts with a dot
        return parts[parts.length - 2] + "." + parts[parts.length - 1];
    }

    public void startMqttConsumer() {
        managedExecutor.execute(() -> {
            try {
                MqttClient mqttClient = new MqttClient(MQTT_BROKER,
                        MqttClient.generateClientId());
                mqttClient.connect();
                System.out.println("Connected to MQTT broker");

                mqttClient.subscribe(TOPIC, (topic, message) -> {
                    MqttSendMessage receivedMessage = deserialize(message.getPayload());
                    System.out.println("Received message: " + receivedMessage.getMessage());
                    KafkaSend producer = new KafkaSend();
                    producer.sendMessage(receivedMessage, transformTopic());

                });

                System.out.println("Subscribed to topic: " + TOPIC);

            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    void onStart(@Observes StartupEvent ev) {
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
