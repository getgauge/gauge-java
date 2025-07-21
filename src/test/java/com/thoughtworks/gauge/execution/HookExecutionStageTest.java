package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HookExecutionStageTest {
    @Test
    public void shouldShortCircuitIfPreviousStageSkipped() {
        HookExecutionStage stage = new HookExecutionStage(java.util.Collections.emptyList(), new com.thoughtworks.gauge.ClassInstanceManager());
        Spec.ProtoExecutionResult previous = Spec.ProtoExecutionResult.newBuilder().setSkipScenario(true).build();
        Spec.ProtoExecutionResult result = stage.execute(previous);
        assertTrue(result.getSkipScenario());
    }
} 