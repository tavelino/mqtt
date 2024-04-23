package org.acme.mqtt;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

/**
 * A simple resource retrieving the "in-memory" "my-data-stream" and sending the
 * items to a server sent event.
 */
@Path("/mqtt")
public class MqttResource {

    @Inject
    @Channel("mqtt-message-out/1/2")
    Emitter<String> mqttMessageEmitter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        System.out.println("Stream");
        return "hello";
    }

    private Random random = new Random();

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            int i = 0;
            String token = "";
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", token);

            while (true) {
                headers.put("Authorization", "12345");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error sent: " + i);
                }
                Message<String> mqttMessage = Message.of(String.valueOf(i)).addMetadata(headers);

                System.out.println("Message payload: " + mqttMessage.getPayload());
                System.out.println("Message headers: ");
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
                mqttMessageEmitter.send(mqttMessage);
                System.out.println("Message sent: " + i);
                i++;
                headers.clear();
            }
        });
        executor.shutdown();
        return "Enviado ";
    }
}
