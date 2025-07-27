package io.github.orionlibs.user.observability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@ActiveProfiles("test")
public class HealthCheckTest
{
    @Test
    void health()
    {
        HealthCheck indicator = new HealthCheck();
        Health health = indicator.health();
        assertThat(Health.up().build().getStatus()).isEqualTo(health.getStatus());
        assertThat(health.getDetails().containsKey("custom-check")).isTrue();
        assertThat(health.getDetails().get("custom-check")).isEqualTo("Everything OK");
    }
}
