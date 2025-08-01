package io.github.orionlibs.user.observability;

import io.github.orionlibs.core.observability.HealthChecker;
import io.github.orionlibs.user.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/health
public class HealthCheckerForUser implements HealthChecker
{
    @Autowired
    private UserDAO userDAO;


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
        try
        {
            Integer result = userDAO.testConnection();
            return result != null && result == 1;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
