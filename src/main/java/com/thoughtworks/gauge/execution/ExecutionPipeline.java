// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.
package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;

public class ExecutionPipeline {
    private ExecutionStage firstStage;
    private ExecutionStage lastStage;

    public ExecutionPipeline(ExecutionStage firstStage) {
        this.firstStage = firstStage;
        this.lastStage = this.firstStage;
    }

    public Spec.ProtoExecutionResult start() {
        Spec.ProtoExecutionResult result = Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
        return firstStage.execute(result);
    }

    public void addStages(ExecutionStage... stages) {
        ExecutionStage lastStage = finalStage();
        for (ExecutionStage stage : stages) {
            lastStage.setNextStage(stage);
            lastStage = stage;
            this.lastStage = lastStage;
        }
    }

    private ExecutionStage finalStage() {
        return lastStage;
    }
}
