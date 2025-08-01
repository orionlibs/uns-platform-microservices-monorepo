package io.github.orionlibs.document.observability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class HealthCheckForDocumentTest
{
    @Test
    void health()
    {
        HealthCheckerForDocument indicator = new HealthCheckerForDocument();
        Health health = indicator.health();
        assertThat(Health.up().build().getStatus()).isEqualTo(health.getStatus());
        assertThat(health.getDetails().containsKey("custom-check")).isTrue();
        assertThat(health.getDetails().get("custom-check")).isEqualTo("Everything OK");
    }
}
