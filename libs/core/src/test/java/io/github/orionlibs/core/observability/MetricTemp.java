package io.github.orionlibs.core.observability;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/metrics/custom.login.count
public class MetricTemp extends Metric
{
    public MetricTemp(MeterRegistry meterRegistry)
    {
        super("custom.login.count", "Number of user logins", meterRegistry);
    }


    @Override
    public void update()
    {
        counter.increment();
    }
}
