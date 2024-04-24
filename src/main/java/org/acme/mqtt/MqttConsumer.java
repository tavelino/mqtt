package org.acme.mqtt;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.annotations.Broadcast;

@ApplicationScoped
public class MqttConsumer {

    @Incoming("mqtt-message-in/1/2")
    @Broadcast
    public CompletionStage<Void> consumeMessage(Message<String> message) {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        Optional String topic = message.getMetadata().get(String.class);
        System.out.println("Received message: " + payload + " from topic: " + topic);
        KafkaSend producer = new KafkaSend();
        producer.sendMessage();

        return message.ack();
    }
}