package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

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

    @Inject
    MqttProducer mqttProducer;

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
        ExecutorService executor = Executors.newSingleThreadExecutor();
        MqttSendMessage mqttMes = new MqttSendMessage();
        mqttMes.setJwt("Token");
        executor.execute(() -> {
            int i = 0;
            while (true) {
                mqttMes.setMessage("message to kafka " + i);
                mqttProducer.setTopic(TOPIC);
                mqttProducer.Produce(mqttMes);
                i++;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println("Error sent: ");
                }
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

