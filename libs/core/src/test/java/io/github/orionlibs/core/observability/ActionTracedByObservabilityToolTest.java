package io.github.orionlibs.core.observability;

import static org.assertj.core.api.Assertions.assertThat;

import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ActionTracedByObservabilityToolTest
{
    @Test
    void doSomethingObservedRegistersObservation()
    {
        List<Context> collected = new ArrayList<>();
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(new ObservationHandler<Context>()
        {
            @Override
            public boolean supportsContext(Context context)
            {
                return true;
            }


            @Override
            public void onStop(Context context)
            {
                collected.add(context);
            }
        });
        ActionTracedByObservabilityTool service = new ActionTracedByObservabilityTool(registry);
        service.doSomethingObserved();
        assertThat(collected.size()).isEqualTo(1);
        Context context = collected.get(0);
        assertThat(context.getName()).isEqualTo("my.custom.observation");
    }
}
