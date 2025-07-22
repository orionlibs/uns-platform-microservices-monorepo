package io.github.orionlibs.documents;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "event")
public class KafkaEventTopics
{
    private List<String> topics;


    public List<String> getTopics()
    {
        return topics;
    }


    public void setTopics(List<String> topics)
    {
        this.topics = topics;
    }
}
