/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;

public class ExecutionPipeline {
    private final ExecutionStage firstStage;

    public ExecutionPipeline(ExecutionStage firstStage) {
        this.firstStage = firstStage;
    }

    public Spec.ProtoExecutionResult start() {
        Spec.ProtoExecutionResult result = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
        return firstStage.execute(result);
    }

    public void addStages(ExecutionStage... stages) {
        ExecutionStage currentStage = this.firstStage;
        for (ExecutionStage stage : stages) {
            currentStage.setNextStage(stage);
            currentStage = stage;
        }
    }
}
