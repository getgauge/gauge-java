/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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

        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder();
        builder.setExecutionTime(execTime);
        builder.setFailed(failed);
        if (previousStageResult.getFailed()) {
            builder.setErrorMessage(previousStageResult.getErrorMessage());
            builder.setErrorType(previousStageResult.getErrorType());
            builder.setFailureScreenshotFile(previousStageResult.getFailureScreenshotFile());
            builder.setStackTrace(previousStageResult.getStackTrace());
            builder.setRecoverableError(previousStageResult.getRecoverableError());
        } else if (execResult.getFailed()) {
            builder.setErrorType(execResult.getErrorType());
            builder.setErrorMessage(execResult.getErrorMessage());
            builder.setFailureScreenshotFile(execResult.getFailureScreenshotFile());
            builder.setStackTrace(execResult.getStackTrace());
            builder.setRecoverableError(execResult.getRecoverableError());
        }
        if (previousStageResult.getRecoverableError() && execResult.getFailed()) {
            builder.setRecoverableError(execResult.getRecoverableError());
        }
        return builder.build();
    }

    protected abstract ExecutionStage next();

}
