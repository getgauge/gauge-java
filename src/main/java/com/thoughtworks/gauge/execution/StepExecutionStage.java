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


import com.google.common.base.Throwables;
import gauge.messages.Messages;
import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;
import gauge.messages.Spec.Parameter.ParameterType;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.gauge.registry.StepRegistry;


public class StepExecutionStage extends AbstractExecutionStage {
    public static final String ENUM_VALUE_NOT_FOUND_MESSAGE = "%s is not an enum value of %s.";

    private ExecutionStage next;
    private Messages.ExecuteStepRequest executeStepRequest;
    private Map<Class<?>, StringToPrimitiveConverter> primitiveConverters = new HashMap<Class<?>, StringToPrimitiveConverter>();
    private TableConverter tableConverter;

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
        tableConverter = new TableConverter();
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
            return Spec.ProtoExecutionResult.newBuilder()
                    .setFailed(true)
                    .setExecutionTime(0)
                    .setErrorMessage(String.format("Argument length mismatch for: %s. Actual Count: [%d], Expected Count: [%d]",
                            this.executeStepRequest.getActualStepText(), implementationParamCount, numberOfParameters))
                    .build();
        }
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
                Parameter parameter = args.get(i);
                if (isTable(parameter)) {
                    parameters[i] = this.tableConverter.convert(parameter);
                } else if (parameterType.isEnum()) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) parameterType;
                    String enumValue = parameter.getValue();
                    try {
                        parameters[i] = getEnumInstance(enumClass, enumValue);
                    } catch (IllegalArgumentException e) {
                        return Spec.ProtoExecutionResult.newBuilder().setFailed(true)
                                .setExecutionTime(0)
                                .setStackTrace(Throwables.getStackTraceAsString(e))
                                .setErrorMessage(String.format(ENUM_VALUE_NOT_FOUND_MESSAGE,
                                        enumValue, enumClass.getSimpleName()))
                                .build();
                    }
                } else if (primitiveConverters.containsKey(parameterType)) {
                    try {
                        parameters[i] = primitiveConverters.get(parameterType).convert(parameter);
                    } catch (Exception e) {
                        return Spec.ProtoExecutionResult.newBuilder()
                                .setFailed(true)
                                .setExecutionTime(0)
                                .setStackTrace(Throwables.getStackTraceAsString(e))
                                .setErrorMessage(String.format("Failed to convert argument from type String to type %s. %s", parameterType.toString(), e.getMessage()))
                                .build();
                    }

                } else {
                    parameters[i] = parameter.getValue();
                }
            }
            return methodExecutor.execute(method, parameters);
        } else {
            return methodExecutor.execute(method);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T extends Enum<T>> Enum<T> getEnumInstance(Class<? extends Enum> clazz, String name) {
        return Enum.valueOf(clazz, name);
    }

    private boolean isTable(
            Spec.Parameter parameter) {

        return parameter.getParameterType().equals(ParameterType.Special_Table) || parameter.getParameterType().equals(ParameterType.Table);
    }

    protected ExecutionStage next() {
        return next;
    }
}
