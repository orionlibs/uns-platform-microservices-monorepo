package io.github.orionlibs.document.observability;

import java.util.Map;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/info
public class BuildInfo implements InfoContributor
{
    @Override
    public void contribute(Info.Builder builder)
    {
        builder.withDetail("build", Map.of(
                        "version", "1.0.2",
                        "environment", "production"
        ));
    }
}
