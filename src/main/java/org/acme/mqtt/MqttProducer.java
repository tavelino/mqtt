package org.acme.mqtt;

import org.apache.camel.Produce;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
public class MqttProducer {

    @Inject
    @ConfigProperty(name = "mqtt.artemis.server")
    private String MQTT_BROKER = "tcp://ex-aao-hdls-svc:61616";

    @Getter
    @Setter
    private String topic = "";

    void Produce(MqttSendMessage mqttMes) {

        System.out.println("Enviando mensagens para o topico: " + topic);
        System.out.println("Enviando mensagens: " + mqttMes.getMessage());

        try {
            MqttClient mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
            mqttClient.connect();
            System.out.println("Enviando mensagens: " + mqttMes.getMessage() + "para o topico" + topic);

            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(mqttMes.serialize());

            mqttClient.publish(topic, mqttMessage);

            mqttClient.disconnect();
            mqttClient.close();

        } catch (MqttException e) {
            System.out.println("Failed to publish message to MQTT broker: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
