package org.acme.mqtt;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * A simple resource retrieving the "in-memory" "my-data-stream" and sending the
 * items to a server sent event.
 */
@Path("/mqtt")
public class MqttResource {

    @Inject
    @ConfigProperty(name = "mqtt.artemis.server")
    private String MQTT_BROKER;
    private static final String TOPIC = "mqtt-message-in/1/2/app/test";

    // @Inject
    // MqttConsumerService mqttConsumerService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("Stream");
        return "hello";
    }

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage() {
        System.out.println("Enviando mensagens: " + TOPIC);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                int i = 0;
                while (true) {
                    MqttClient mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
                    mqttClient.connect();
                    MqttSendMessage mqttMes = new MqttSendMessage();
                    mqttMes.setJwt("Token");
                    mqttMes.setMessage("message to kafka " + i);
                    System.out.println("Enviando mensagens: " + mqttMes.getMessage());

                    MqttMessage mqttMessage = new MqttMessage();
                    mqttMessage.setPayload(mqttMes.serialize());

                    mqttClient.publish(TOPIC, mqttMessage);

                    mqttClient.disconnect();
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Error sent: ");
                    }
                }

            } catch (MqttException e) {
                System.out.println("Failed to publish message to MQTT broker: " + e.getMessage());
                e.printStackTrace();
            }
        });

        return "Message published to MQTT broker: ";
    }

    // @GET
    // @Path("receive")
    // public String receive() {
    // mqttConsumerService.startMqttConsumer();
    // return "Consumindo";
    // }
}
