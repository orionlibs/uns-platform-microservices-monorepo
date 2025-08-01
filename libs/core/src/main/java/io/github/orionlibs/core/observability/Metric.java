package io.github.orionlibs.core.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public abstract class Metric
{
    protected String name;
    protected String description;
    protected Counter counter;


    public Metric(String name, String description, MeterRegistry meterRegistry)
    {
        this.name = name;
        this.description = description;
        this.counter = Counter.builder(name)
                        .description(description)
                        .register(meterRegistry);
    }


    public void update()
    {
        counter.increment();
    }


    public void update(int count)
    {
        counter.increment((double)count);
    }
}
