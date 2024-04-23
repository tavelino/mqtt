// package org.acme.mqtt;

// import jakarta.inject.Inject;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.MediaType;
// import org.eclipse.microprofile.reactive.messaging.Emitter;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;

// import io.smallrye.mutiny.Multi;
// import org.eclipse.microprofile.reactive.messaging.Channel;
// import org.eclipse.microprofile.reactive.messaging.Message;
// import java.util.HashMap;
// import java.util.Map;

// import java.util.Random;

// import org.eclipse.paho.client.mqttv3.MqttClient;
// import org.eclipse.paho.client.mqttv3.MqttException;
// import org.eclipse.paho.client.mqttv3.MqttMessage;

// /**
//  * A simple resource retrieving the "in-memory" "my-data-stream" and sending the
//  * items to a server sent event.
//  */
// @Path("/prices")
// public class PriceResource {


//     private static final String MQTT_BROKER = "tcp://ex-aao-hdls-svc:61616";
//     private static final String TOPIC = "test/topic";
//     // @Inject
//     // @Channel("mqtt-message-out/1/2")
//     // Emitter<String> mqttMessageEmitter;

//     // @GET
//     // @Produces(MediaType.TEXT_PLAIN)
//     // public String hello() {
//     //     System.out.println("Stream");
//     //     return "hello";
//     // }

//     // private Random random = new Random();

//     @GET
//     @Path("/send")
//     @Produces(MediaType.TEXT_PLAIN)
//     public String sendMessage() {
//         System.out.println("Entrou no getMessage: ");
//         ExecutorService executor = Executors.newSingleThreadExecutor();
//         executor.execute(() -> {
//             try {
//                 int i = 0;
//                 while(true){
//                     MqttClient mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
//                     mqttClient.connect();
//                     String message = "Hello MQTT!";
//                     message += i;
//                     System.out.println("Connection established");
//                     MqttMessage mqttMessage = new MqttMessage();
//                     mqttMessage.setPayload(message.getBytes());
//                     System.out.println("MqttMessage: " + mqttMessage);
    
//                     mqttClient.publish(TOPIC, mqttMessage);
//                     System.out.println("Published message");
    
//                     mqttClient.disconnect();
//                     System.out.println("Disconnected from MQTT broker");
//                     i++;
//                     try {
//                         Thread.sleep(1000);
//                     } catch (InterruptedException e) {
//                         System.out.println("Error sent: " );
//                     }
//                 }

//             } catch (MqttException e) {
//                 System.out.println("Failed to publish message to MQTT broker: " + e.getMessage());
//                 e.printStackTrace();
//             }
//         });

//         return "Message published to MQTT broker: ";
//     }

//     @Inject
//     MqttConsumerService mqttConsumerService;

//     @GET
//     @Path("/start")
//     public String startMqttConsumer() {
//         mqttConsumerService.startMqttConsumer();
//         return "MQTT consumer started successfully!";
//     }
// }
