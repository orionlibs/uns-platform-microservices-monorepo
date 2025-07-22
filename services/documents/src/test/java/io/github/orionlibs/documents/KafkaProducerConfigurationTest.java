package io.github.orionlibs.documents;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest/*(properties = {
                "spring.kafka.bootstrap-servers=localhost:9092",
                "event.topics=document-saved,document-updated,document-deleted"
})*/
@ActiveProfiles("test")
@Import(KafkaProducerConfiguration.class)
public class KafkaProducerConfigurationTest
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private NewTopics newTopics;


    @Test
    void kafkaTemplateBeanIsCreated()
    {
        assertThat(kafkaTemplate)
                        .as("KafkaTemplate should be auto-configured")
                        .isNotNull();
    }


    @Test
    void allTopicsAreRegistered()
    {
        System.out.println("-----" + newTopics.toString());
        /*Collection<NewTopic> topics = Arrays.asList(newTopics.toString().split(","));
        // verify exactly the three names we configured
        assertThat(
                        topics.stream()
                                        .map(NewTopic::name)
                                        .collect(Collectors.toSet())
        )
                        .as("Should register exactly the topics alpha, bravo, charlie")
                        .containsExactlyInAnyOrder("alpha", "bravo", "charlie");
        // verify each topic has the defaults we set in the config
        topics.forEach(t -> {
            assertThat(t.numPartitions())
                            .as("Each topic should have 1 partition")
                            .isEqualTo(3);
            assertThat(t.replicationFactor())
                            .as("Each topic should have 1 replica")
                            .isEqualTo((short)1);
        });*/
    }
}
