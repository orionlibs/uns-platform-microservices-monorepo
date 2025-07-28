package io.github.orionlibs.user.observability;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
//GET /actuator/info
public class BuildInfo implements InfoContributor
{
    @Value("${version:0.0.1}")
    private String version;
    @Value("${environment:production}")
    private String environment;


    @Override
    public void contribute(Info.Builder builder)
    {
        builder.withDetail("build", Map.of(
                        "version", version,
                        "environment", environment
        ));
    }
}
