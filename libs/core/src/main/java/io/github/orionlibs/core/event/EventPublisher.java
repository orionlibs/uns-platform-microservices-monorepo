package io.github.orionlibs.core.event;

import lombok.extern.slf4j.Slf4j;

public interface EventPublisher
{
    void publish(String topic, String message);


    @Slf4j
    class EventPublisherFake implements EventPublisher
    {
        @Override
        public void publish(String topic, String message)
        {
            //fake implementation
            log.info("publishing event to topic {}", topic);
        }
    }
}
