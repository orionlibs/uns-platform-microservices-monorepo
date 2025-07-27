package io.github.orionlibs.user.observability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.info.Info;
import org.springframework.test.context.ActiveProfiles;

//@SpringBootTest
@ActiveProfiles("test")
public class BuildInfoTest
{
    @Test
    void contributesBuildDetails()
    {
        BuildInfo contributor = new BuildInfo();
        Info.Builder builder = new Info.Builder();
        contributor.contribute(builder);
        Info info = builder.build();
        assertThat(info.getDetails().containsKey("build")).isTrue();
        Object build = info.getDetails().get("build");
        assertThat(build).isInstanceOf(Map.class);
        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> details = (java.util.Map<String, Object>)build;
        assertThat(details.get("version")).isEqualTo("1.0.2");
        assertThat(details.get("environment")).isEqualTo("production");
    }
}
