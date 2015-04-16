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

import java.lang.reflect.Method;
import java.util.Set;


public class HookExecutionStage extends AbstractExecutionStage {
    private Set<Method> beforeClassStepsHooks;
    private ExecutionStage next;

    public HookExecutionStage(Set<Method> beforeClassStepsHooks) {
        this.beforeClassStepsHooks = beforeClassStepsHooks;
    }

    public void setNextStage(ExecutionStage stage) {
        this.next = stage;
    }

    public Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult result) {
        Spec.ProtoExecutionResult execResult = execute();
        Spec.ProtoExecutionResult stageResult = mergeExecResults(result, execResult);
        return executeNext(stageResult);
    }

    private Spec.ProtoExecutionResult mergeExecResults(Spec.ProtoExecutionResult previousStageResult, Spec.ProtoExecutionResult execResult) {
        long execTime = execResult.getExecutionTime() + previousStageResult.getExecutionTime();
        boolean failed = execResult.getFailed() | previousStageResult.getFailed();
        boolean recoverableError = execResult.getRecoverableError() & previousStageResult.getRecoverableError();

        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder();
        builder.setExecutionTime(execTime);
        builder.setFailed(failed);
        builder.setRecoverableError(recoverableError);

        if (previousStageResult.getFailed()) {
            builder.setErrorMessage(previousStageResult.getErrorMessage());
            builder.setScreenShot(previousStageResult.getScreenShot());
            builder.setStackTrace(previousStageResult.getStackTrace());
        } else if(execResult.getFailed()) {
            builder.setErrorMessage(execResult.getErrorMessage());
            builder.setScreenShot(execResult.getScreenShot());
            builder.setStackTrace(execResult.getStackTrace());
        }
        return builder.build();
    }

    protected ExecutionStage next() {
        return next;
    }

    private Spec.ProtoExecutionResult execute() {
        MethodExecutor methodExecutor = new MethodExecutor();
        return methodExecutor.executeMethods(beforeClassStepsHooks);
    }
}
