package io.github.orionlibs.documents;

import java.util.List;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaProducerConfiguration
{
    @Value("${event.topics}")
    private List<String> eventTopics;


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf)
    {
        return new KafkaTemplate<>(pf);
    }


    @Bean
    public NewTopics newTopics()
    {
        NewTopic[] topics = eventTopics.stream()
                        .map(name -> TopicBuilder.name(name)
                                        .partitions(1)
                                        .replicas(1)
                                        .build())
                        .toArray(NewTopic[]::new);
        return new NewTopics(topics);
    }
}
