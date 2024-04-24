// package org.acme.mqtt;

// import io.quarkus.runtime.annotations.RegisterForReflection;
// import org.eclipse.microprofile.context.ManagedExecutor;
// import org.eclipse.paho.client.mqttv3.*;
// import jakarta.enterprise.context.ApplicationScoped;
// import jakarta.inject.Inject;

// @ApplicationScoped
// @RegisterForReflection
// public class MqttConsumerService {

// private static final String MQTT_BROKER = "tcp://ex-aao-hdls-svc:61616";
// private static final String TOPIC = "test/topic";

// @Inject
// ManagedExecutor managedExecutor;

// public void startMqttConsumer() {
// managedExecutor.execute(() -> {
// try {
// MqttClient mqttClient = new MqttClient(MQTT_BROKER,
// MqttClient.generateClientId());
// mqttClient.connect();
// System.out.println("Connected to MQTT broker");

// mqttClient.subscribe(TOPIC, (topic, message) -> {
// String receivedMessage = new String(message.getPayload());
// System.out.println("Received message: " + receivedMessage);
// });

// System.out.println("Subscribed to topic: " + TOPIC);

// } catch (MqttException e) {
// e.printStackTrace();
// }
// });
// }
// }
