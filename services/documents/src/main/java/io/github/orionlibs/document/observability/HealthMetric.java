package io.github.orionlibs.document.observability;

import io.github.orionlibs.core.observability.Metric;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/metrics/custom.login.count
public class HealthMetric extends Metric
{
    public HealthMetric(MeterRegistry meterRegistry)
    {
        super("custom.login.count", "Number of user logins", meterRegistry);
    }


    @Override
    public void update()
    {
        counter.increment();
    }
}
