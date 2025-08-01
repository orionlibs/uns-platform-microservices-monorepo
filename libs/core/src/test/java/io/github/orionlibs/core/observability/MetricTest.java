package io.github.orionlibs.core.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class MetricTest
{
    @Autowired MetricTemp customMetric;
    SimpleMeterRegistry registry;


    @BeforeEach
    void setUp()
    {
        registry = new SimpleMeterRegistry();
        //customMetric = new MetricTemp(registry);
    }


    @Test
    void counterIncrementIncreasesCount()
    {
        Counter counter = registry.find("custom.login.count").counter();
        assertThat(counter).isNotNull();
        double before = counter.count();
        customMetric.update();
        customMetric.update();
        double after = counter.count();
        assertThat(before + 2).isEqualTo(after, Offset.<Double>offset(0.001));
    }
}
