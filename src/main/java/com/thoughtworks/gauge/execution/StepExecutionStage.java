/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.execution.parameters.ParametersExtractor;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.List;

public class StepExecutionStage extends AbstractExecutionStage {
    private ExecutionStage next;
    private Messages.ExecuteStepRequest executeStepRequest;
    private ClassInstanceManager manager;
    private ParametersExtractor parametersExtractor;
    private StepRegistry registry;

    public StepExecutionStage(Messages.ExecuteStepRequest executeStepRequest, ClassInstanceManager manager, ParameterParsingChain chain, StepRegistry registry) {
        this.manager = manager;
        this.executeStepRequest = executeStepRequest;
        this.parametersExtractor = new ParametersExtractor(chain);
        this.registry = registry;
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
        Method method = registry.get(executeStepRequest.getParsedStepText()).getMethodInfo();

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

    Spec.ProtoExecutionResult executeStepMethod(MethodExecutor methodExecutor, Method method) {
        try {
            List<Spec.Parameter> arguments = executeStepRequest.getParametersList();
            return methodExecutor.execute(method, parametersExtractor.extract(arguments, method.getParameterTypes()));
        } catch (ParsingException e) {
            return e.getExecutionResult();
        }
    }

    protected ExecutionStage next() {
        return next;
    }
}
