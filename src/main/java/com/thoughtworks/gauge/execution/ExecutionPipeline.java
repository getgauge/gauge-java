// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;

public class ExecutionPipeline {
    private ExecutionStage firstStage;

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
