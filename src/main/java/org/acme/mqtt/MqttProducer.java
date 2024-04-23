// package org.acme.mqtt;
// import jakarta.enterprise.context.ApplicationScoped;
// import org.eclipse.paho.client.mqttv3.MqttClient;
// import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
// import org.eclipse.paho.client.mqttv3.MqttException;
// import org.eclipse.paho.client.mqttv3.MqttMessage;

// @ApplicationScoped
// public class MqttProducer {

//     private final MqttClient mqttClient;

//     public MqttProducer() throws MqttException {
//         mqttClient = new MqttClient("tcp://localhost:61616", MqttClient.generateClientId());
//         MqttConnectOptions options = new MqttConnectOptions();
//         mqttClient.connect(options);
//     }

//     public void sendMessage(String topic, String message) throws MqttException {
//         MqttMessage mqttMessage = new MqttMessage(message.getBytes());
//         mqttClient.publish(topic, mqttMessage);
//     }

//     public void close() throws MqttException {
//         mqttClient.disconnect();
//         mqttClient.close();
//     }
// }