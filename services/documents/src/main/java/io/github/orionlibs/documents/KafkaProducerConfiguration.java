package io.github.orionlibs.documents;

import io.github.orionlibs.core.event.EventNameScanner;
import java.util.List;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
//@EnableConfigurationProperties(KafkaEventTopics.class)
public class KafkaProducerConfiguration
{
    private final KafkaEventTopics eventTopics;


    public KafkaProducerConfiguration()
    {
        this.eventTopics = new KafkaEventTopics();
        List<String> allEventNames = EventNameScanner.scanEventNames("io.github.orionlibs.documents.event");
        eventTopics.setTopics(allEventNames);
    }


    /*@Bean
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
    }*/
}
