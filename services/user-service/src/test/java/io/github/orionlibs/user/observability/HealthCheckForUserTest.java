package io.github.orionlibs.user.observability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;

public class HealthCheckForUserTest
{
    @Test
    void health()
    {
        HealthCheckerForUser indicator = new HealthCheckerForUser();
        Health health = indicator.health();
        assertThat(Health.up().build().getStatus()).isEqualTo(health.getStatus());
        assertThat(health.getDetails().containsKey("custom-check")).isTrue();
        assertThat(health.getDetails().get("custom-check")).isEqualTo("Everything OK");
    }
}
