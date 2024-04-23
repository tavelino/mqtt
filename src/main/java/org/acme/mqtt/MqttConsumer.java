import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.reactive.messaging.Channel;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.reactive.messaging.Message;
import io.smallrye.reactive.messaging.annotations.Broadcast;


@ApplicationScoped
public class MqttConsumer {

    @Incoming("mqtt-message-in/1/2")
    @Broadcast
    public void consumeMessage(String message) {
        String payload = message;

        System.out.println("Received message: " + payload);

        System.out.println("Received message: " + message);
    }
}