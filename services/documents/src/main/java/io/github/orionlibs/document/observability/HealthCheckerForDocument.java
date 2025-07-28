package io.github.orionlibs.document.observability;

import io.github.orionlibs.core.observability.HealthChecker;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/health
public class HealthCheckerForDocument implements HealthChecker
{
    @Override
    public Health health()
    {
        boolean serverHealthy = checkSomeCustomLogic();
        if(serverHealthy)
        {
            return Health.up().withDetail("custom-check", "Everything OK").build();
        }
        else
        {
            return Health.down().withDetail("custom-check", "Something went wrong").build();
        }
    }


    private boolean checkSomeCustomLogic()
    {
        // e.g., check a database or external service
        return true;
    }
}
