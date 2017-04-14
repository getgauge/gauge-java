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

import java.lang.reflect.Method;
import java.util.List;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.execution.parameters.ParametersExtractor;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.registry.StepRegistry;

import gauge.messages.Messages;
import gauge.messages.Spec;

public class StepExecutionStage extends AbstractExecutionStage {

    private ExecutionStage next;
    private Messages.ExecuteStepRequest executeStepRequest;
    private ClassInstanceManager manager;
    private ParametersExtractor parametersExtractor = new ParametersExtractor();

    public StepExecutionStage(Messages.ExecuteStepRequest executeStepRequest, ClassInstanceManager manager) {
        this.manager = manager;
        this.executeStepRequest = executeStepRequest;
    }

    public void setNextStage(ExecutionStage stage) {
        this.next = stage;
    }

    public Spec.ProtoExecutionResult execute(Spec.ProtoExecutionResult previousStageResult) {
        if (previousStageResult.getFailed()) {
            return executeNext(previousStageResult);
        }
        Spec.ProtoExecutionResult stageResult = executeStep();
        return executeNext(mergeExecResults(previousStageResult, stageResult));
    }

    private Spec.ProtoExecutionResult executeStep() {
        Method method = StepRegistry.get(executeStepRequest.getParsedStepText());

        int implementationParamCount = method.getParameterTypes().length;
        int numberOfParameters = this.executeStepRequest.getParametersCount();

        if (implementationParamCount != numberOfParameters) {
            return Spec.ProtoExecutionResult.newBuilder().setFailed(true).setExecutionTime(0)
                    .setErrorMessage(String.format(
                            "Argument length mismatch for: %s. Actual Count: [%d], Expected Count: [%d]",
                            this.executeStepRequest.getActualStepText(), implementationParamCount, numberOfParameters))
                    .build();
        }
        MethodExecutor methodExecutor = new MethodExecutor(manager);
        return executeStepMethod(methodExecutor, method);

    }

    public Spec.ProtoExecutionResult executeStepMethod(MethodExecutor methodExecutor, Method method) {
        List<Spec.Parameter> arguments = executeStepRequest.getParametersList();

        try {
            return methodExecutor.execute(method, parametersExtractor.extract(arguments, method.getParameterTypes()));
        } catch (ParsingException e) {
            return e.getExecutionResult();
        }
    }

    protected ExecutionStage next() {
        return next;
    }
}
