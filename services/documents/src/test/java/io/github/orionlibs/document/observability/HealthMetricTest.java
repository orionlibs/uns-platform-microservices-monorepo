package io.github.orionlibs.document.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class HealthMetricTest
{
    private HealthMetric customMetric;
    private SimpleMeterRegistry registry;


    @BeforeEach
    void setUp()
    {
        registry = new SimpleMeterRegistry();
        customMetric = new HealthMetric(registry);
    }


    @Test
    void counterIncrementIncreasesCount()
    {
        Counter counter = registry.find("custom.login.count").counter();
        assertThat(counter).isNotNull();
        double before = counter.count();
        customMetric.recordLogin();
        customMetric.recordLogin();
        double after = counter.count();
        assertThat(before + 2).isEqualTo(after, Offset.<Double>offset(0.001));
    }
}
