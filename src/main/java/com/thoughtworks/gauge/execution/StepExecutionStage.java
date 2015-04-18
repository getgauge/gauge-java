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