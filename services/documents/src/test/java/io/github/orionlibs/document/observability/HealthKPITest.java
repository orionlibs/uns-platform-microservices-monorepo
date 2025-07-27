package io.github.orionlibs.document.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class HealthKPITest
{
    private HealthKPI customKPI;
    private SimpleMeterRegistry registry;


    @BeforeEach
    void setUp()
    {
        registry = new SimpleMeterRegistry();
        customKPI = new HealthKPI(registry);
    }


    @Test
    void recordRegistration_shouldIncrementTimerCount()
    {
        Timer timer = registry.find("user.registration.duration").timer();
        assertThat(timer).isNotNull();
        long initialCount = timer.count();
        assertThat(initialCount).isEqualTo(0);
        customKPI.recordUserRegistration(() -> {
            try
            {
                Thread.sleep(10);
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            }
        });
        Timer updatedTimer = registry.find("user.registration.duration").timer();
        assertThat(updatedTimer).isNotNull();
        assertThat(updatedTimer.count()).isEqualTo(initialCount + 1);
        assertThat(updatedTimer.totalTime(updatedTimer.baseTimeUnit()) >= 0.01).isTrue();
    }


    @Test
    void recordRegistration_shouldExecuteRunnable()
    {
        final boolean[] executed = {false};
        customKPI.recordUserRegistration(() -> executed[0] = true);
        assertThat(executed[0]).isTrue();
    }


    @Test
    void counterIncrementIncreasesCount()
    {
        Counter counter = registry.find("kpi.user.registrations").counter();
        assertThat(counter).isNotNull();
        double before = counter.count();
        customKPI.recordUserRegistration();
        customKPI.recordUserRegistration();
        double after = counter.count();
        assertThat(before + 2).isEqualTo(after, Offset.<Double>offset(0.001));
    }
}
