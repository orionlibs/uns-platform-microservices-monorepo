package io.github.orionlibs.user.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.stereotype.Service;

@Service
public class ActionTracedByObservabilityTool
{
    private final ObservationRegistry observationRegistry;


    public ActionTracedByObservabilityTool(ObservationRegistry observationRegistry)
    {
        this.observationRegistry = observationRegistry;
    }


    public void doSomethingObserved()
    {
        Observation.createNotStarted("my.custom.observation", observationRegistry)
                        .lowCardinalityKeyValue("step", "one")
                        .observe(() -> {
                            //my code to trace/observe a task
                        });
    }
}
