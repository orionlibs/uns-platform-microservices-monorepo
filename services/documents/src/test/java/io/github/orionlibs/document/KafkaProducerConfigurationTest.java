package io.github.orionlibs.document;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(KafkaProducerConfiguration.class)
public class KafkaProducerConfigurationTest
{
    /*@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private NewTopics kafkaTopics;


    @Test
    void kafkaTemplateBeanIsCreated()
    {
        assertThat(kafkaTemplate)
                        .as("KafkaTemplate should be auto-configured")
                        .isNotNull();
    }*/
}
