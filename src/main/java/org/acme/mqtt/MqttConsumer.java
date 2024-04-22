// import io.quarkus.runtime.annotations.RegisterForReflection;
// import org.eclipse.microprofile.reactive.messaging.Channel;

// import jakarta.enterprise.context.ApplicationScoped;

// import org.eclipse.microprofile.reactive.messaging.Incoming;
// import org.eclipse.microprofile.reactive.messaging.Outgoing;
// import org.eclipse.microprofile.reactive.messaging.Message;
// import io.smallrye.reactive.messaging.annotations.Broadcast;
// import java.util.concurrent.CompletionStage;
// import java.util.Map;
// import java.util.HashMap;
// import java.util.Optional;
// import org.eclipse.microprofile.reactive.messaging.Metadata;
// import org.eclipse.microprofile.reactive.messaging.Emitter;
// import jakarta.inject.Inject;



// @ApplicationScoped
// public class MqttConsumer {

//     // @Inject
//     // @Channel("mqtt-message-in/1/3")
//     // Emitter<String> kafkaMessageEmitter;

//     @Incoming("mqtt-message-in/1/2")
//     @Broadcast
//     public CompletionStage<Void> consumeMessage(Message<String> message) {
//         String payload = message.getPayload();

//         System.out.println("Received message: " + payload);
//         // Metadata metadata = message.getMetadata();
//         // Optional<HashMap> authorizationValueOptional = metadata.get(HashMap.class);
//         // if (authorizationValueOptional.isPresent()) {
//         //     HashMap<String, String> authorizationValueMap = authorizationValueOptional.get();
//         //     String authorizationValue = authorizationValueMap.get("Authorization");
//         //     System.out.println("Authorization: " + authorizationValue);
//         // } else {
//         //     System.out.println("Authorization metadata not found.");
//         // }

//         // kafkaMessageEmitter.send(payload);

//         return message.ack();
//     }
// }