package io.github.orionlibs.document.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class HealthKPI
{
    private final Counter registrationCounter;
    private final Timer loginTimer;


    public HealthKPI(MeterRegistry meterRegistry)
    {
        this.registrationCounter = Counter.builder("kpi.user.registrations")
                        .description("Number of successful user registrations")
                        .tag("region", "eu") //add custom tags
                        .register(meterRegistry);
        this.loginTimer = Timer.builder("user.registration.duration")
                        .description("registration request duration")
                        .register(meterRegistry);
    }


    public void recordUserRegistration()
    {
        registrationCounter.increment();
    }


    /**
     * updates counter of number of registrations and records the duration of the registration process
     * @param executableOfRegistrationProcess
     */
    public void recordUserRegistration(Runnable executableOfRegistrationProcess)
    {
        recordUserRegistration();
        loginTimer.record(executableOfRegistrationProcess);
    }
}
