package io.github.orionlibs.documents;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
@EnableConfigurationProperties(KafkaEventTopics.class)
public class KafkaProducerConfiguration
{
    private final KafkaEventTopics eventTopics;


    public KafkaProducerConfiguration(KafkaEventTopics eventTopics)
    {
        this.eventTopics = eventTopics;
    }


    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf)
    {
        return new KafkaTemplate<>(pf);
    }


    @Bean
    public NewTopics kafkaTopics()
    {
        NewTopic[] topics = eventTopics.getTopics().stream()
                        .map(name -> TopicBuilder.name(name)
                                        .partitions(1)
                                        .replicas(1)
                                        .build())
                        .toArray(NewTopic[]::new);
        return new NewTopics(topics);
    }
}
