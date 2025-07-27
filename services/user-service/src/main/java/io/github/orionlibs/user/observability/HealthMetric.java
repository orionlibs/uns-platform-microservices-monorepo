package io.github.orionlibs.user.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/metrics/custom.login.count
public class HealthMetric
{
    private final Counter loginCounter;


    public HealthMetric(MeterRegistry meterRegistry)
    {
        this.loginCounter = Counter.builder("custom.login.count")
                        .description("Number of user logins")
                        .register(meterRegistry);
    }


    public void recordLogin()
    {
        loginCounter.increment();
    }
}
