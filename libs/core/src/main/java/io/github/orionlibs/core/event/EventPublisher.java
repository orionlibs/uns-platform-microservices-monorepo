package io.github.orionlibs.core.event;

public interface EventPublisher
{
    void publish(String topic, String message);


    class EventPublisherFake implements EventPublisher
    {
        @Override
        public void publish(String topic, String message)
        {
            //fake implementation
        }
    }
}
