package io.github.orionlibs.core.observability;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TimedMetricTemp extends TimedMetric
{
    public TimedMetricTemp(MeterRegistry meterRegistry)
    {
        super("kpi.user.registrations",
                        "Number of successful user registrations",
                        meterRegistry,
                        "user.registration.duration",
                        "registration request duration");
    }


    @Override
    public void update()
    {
        counter.increment();
    }


    @Override
    public void update(Runnable executableOfRegistrationProcess)
    {
        update();
        timer.record(executableOfRegistrationProcess);
    }
}
