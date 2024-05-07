package org.acme.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

public class ReadKafkaTopics {

    @Inject
    @ConfigProperty(name = "kafka.bootstrap.server")
    private String BOOTSTRAP_SERVERS = "my-cluster-kafka-bootstrap:9092";
    private final KafkaConsumer<String, String> consumer;
    List<String> topics = new ArrayList<>();

    public ReadKafkaTopics() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group"); // Specify a consumer group
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumer = new KafkaConsumer<>(props);
    }

    public void onStart(@Observes StartupEvent ev) {
        System.out.println("Started");

        System.out.println(BOOTSTRAP_SERVERS);

        topics = consumer.listTopics().keySet().stream().collect(Collectors.toCollection(ArrayList::new));
        for (String topic : topics) {
            if (!topic.contains("_")) {
                new KafkaMessageConsumer(topic);
            }
        }
        getTopics();
    }

    private void getTopics() {
        System.out.println("Verificando os novos topicos");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Error sent: ");
                }
                List<String> newTopics;
                newTopics = consumer.listTopics().keySet().stream().collect(Collectors.toCollection(ArrayList::new));
                System.out.println("Novos Topicos: " + newTopics);
                if (newTopics.size() > topics.size()) {
                    List<String> onlyNewTopics = new ArrayList<>(newTopics);
                    onlyNewTopics.removeAll(topics);

                    for (String topic : onlyNewTopics) {
                        new KafkaMessageConsumer(topic);

                    }
                    topics = newTopics;
                    newTopics.clear();
                }
            }
        });
    }
}
