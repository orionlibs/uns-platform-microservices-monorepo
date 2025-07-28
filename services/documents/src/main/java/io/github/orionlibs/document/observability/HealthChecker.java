package io.github.orionlibs.document.observability;

import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/health
public class HealthChecker implements org.springframework.boot.actuate.health.HealthIndicator
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
