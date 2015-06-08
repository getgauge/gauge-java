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

public abstract class AbstractExecutionStage implements ExecutionStage {

    public Spec.ProtoExecutionResult executeNext(Spec.ProtoExecutionResult previousStageResult) {
        if (next() != null) {
            return next().execute(previousStageResult);
        } else {
            return previousStageResult;
        }
    }

    protected Spec.ProtoExecutionResult mergeExecResults(Spec.ProtoExecutionResult previousStageResult, Spec.ProtoExecutionResult execResult) {
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
        } else if (execResult.getFailed()) {
            builder.setErrorMessage(execResult.getErrorMessage());
            builder.setScreenShot(execResult.getScreenShot());
            builder.setStackTrace(execResult.getStackTrace());
        }
        return builder.build();
    }

    protected abstract ExecutionStage next();

}
