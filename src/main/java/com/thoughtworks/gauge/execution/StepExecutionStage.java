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

import com.thoughtworks.gauge.StepRegistry;
import com.thoughtworks.gauge.Table;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StepExecutionStage extends AbstractExecutionStage {
    private ExecutionStage next;
    private Messages.ExecuteStepRequest executeStepRequest;
    private Map<Class<?>, StringToPrimitiveConverter> primitiveConverters = new HashMap<Class<?>, StringToPrimitiveConverter>();

    public StepExecutionStage(Messages.ExecuteStepRequest executeStepRequest) {
        primitiveConverters.put(int.class, new StringToIntegerConverter());
        primitiveConverters.put(Integer.class, new StringToIntegerConverter());
        primitiveConverters.put(boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(Boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(long.class, new StringToLongConverter());
        primitiveConverters.put(Long.class, new StringToLongConverter());
        primitiveConverters.put(float.class, new StringToFloatConverter());
        primitiveConverters.put(Float.class, new StringToFloatConverter());
        primitiveConverters.put(double.class, new StringToDoubleConverter());
        primitiveConverters.put(Double.class, new StringToDoubleConverter());
        primitiveConverters.put(Table.class, new TableConverter());
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
        MethodExecutor methodExecutor = new MethodExecutor();
        return executeStepMethod(methodExecutor, method);

    }

    public Spec.ProtoExecutionResult executeStepMethod(MethodExecutor methodExecutor, Method method) {
        List<Spec.Parameter> args = executeStepRequest.getParametersList();
        if (args != null && args.size() > 0) {
            Object[] parameters = new Object[args.size()];
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> parameterType = parameterTypes[i];
                if (primitiveConverters.containsKey(parameterType)) {
                    parameters[i] = primitiveConverters.get(parameterType).convert(args.get(i));
                } else {
                    parameters[i] = args.get(i).getValue();
                }
            }
            return methodExecutor.execute(method, parameters);
        } else {
            return methodExecutor.execute(method);
        }
    }

    protected ExecutionStage next() {
        return next;
    }
}
