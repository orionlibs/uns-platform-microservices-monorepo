package io.github.orionlibs.document.observability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class BuildInfoTest
{
    @Autowired
    BuildInfo contributor;


    @Test
    @SuppressWarnings("unchecked")
    void contributesBuildDetails()
    {
        Info.Builder builder = new Info.Builder();
        contributor.contribute(builder);
        Info info = builder.build();
        assertThat(info.getDetails().containsKey("build")).isTrue();
        Object build = info.getDetails().get("build");
        assertThat(build).isInstanceOf(Map.class);
        Map<String, Object> details = (Map<String, Object>)build;
        assertThat(details.get("version")).isEqualTo("0.0.1");
        assertThat(details.get("environment")).isEqualTo("production");
    }
}
