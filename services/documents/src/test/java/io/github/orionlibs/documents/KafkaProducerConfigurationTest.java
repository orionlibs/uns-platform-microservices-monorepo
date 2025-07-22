package io.github.orionlibs.documents;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(KafkaProducerConfiguration.class)
public class KafkaProducerConfigurationTest
{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private NewTopics kafkaTopics;


    @Test
    void kafkaTemplateBeanIsCreated()
    {
        assertThat(kafkaTemplate)
                        .as("KafkaTemplate should be auto-configured")
                        .isNotNull();
    }
}
