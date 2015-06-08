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

import com.thoughtworks.gauge.SpecificationInfo;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HooksExecutor {
    private final Set<Method> hooks;
    private final SpecificationInfo info;

    public HooksExecutor(Set<Method> hooks, SpecificationInfo executionInfo) {
        this.hooks = hooks;
        info = executionInfo;
    }

    public Spec.ProtoExecutionResult execute() {
        MethodExecutor methodExecutor = new MethodExecutor();
        Spec.ProtoExecutionResult result;
        long totalHooksExecutionTime = 0;
        for (Method method : hooks) {
            if (methodHasArguments(method, info)) {
                result = methodExecutor.execute(method, info);
            } else {
                result = methodExecutor.execute(method);
            }
            totalHooksExecutionTime += result.getExecutionTime();
            if (result.getFailed()) {
                return Spec.ProtoExecutionResult.newBuilder(result).setExecutionTime(totalHooksExecutionTime).build();
            }
        }
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(totalHooksExecutionTime).build();
    }

    private boolean methodHasArguments(Method method, Object... arg) {
        if (method.getParameterTypes().length != arg.length) {
            return false;
        }
        List<Class> argsClassList = createClassList(arg);
        Class<?>[] parameterTypes = method.getParameterTypes();
        boolean isValid = true;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!(argsClassList.indexOf(parameterTypes[i]) == i)) {
                isValid = false;
            }
        }
        return isValid;
    }

    private List<Class> createClassList(Object[] objects) {
        ArrayList<Class> classes = new ArrayList<Class>();
        for (Object obj : objects) {
            classes.add(obj.getClass());
        }
        return classes;
    }
}
