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

import com.google.protobuf.ByteString;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ScreenshotFactory;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.Set;

public class MethodExecutor {
    public Spec.ProtoExecutionResult execute(Method method, Object... args) {
        long startTime = System.currentTimeMillis();
        try {
            Object instance = ClassInstanceManager.get(method.getDeclaringClass());
            method.invoke(instance, args);
            long endTime = System.currentTimeMillis();
            return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(endTime - startTime).build();
        } catch (Throwable e) {
            long endTime = System.currentTimeMillis();
            return createFailureExecResult(endTime - startTime, e);
        }
    }

    private Spec.ProtoExecutionResult createFailureExecResult(long execTime, Throwable e) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder().setFailed(true);
        builder.setScreenShot(ByteString.copyFrom(new ScreenshotFactory().getScreenshotBytes()));
        if (e.getCause() != null) {
            builder.setErrorMessage(e.getCause().toString());
            builder.setStackTrace(formatStackTrace(e.getCause().getStackTrace()));
        } else {
            builder.setErrorMessage(e.toString());
            builder.setStackTrace(formatStackTrace(e.getStackTrace()));
        }
        builder.setRecoverableError(false);
        builder.setExecutionTime(execTime);
        return builder.build();
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        if (stackTrace == null)
            return "";

        StringBuffer output = new StringBuffer();
        for (StackTraceElement element : stackTrace) {
            output.append(element.toString());
            output.append("\n");
        }
        return output.toString();
    }

    public Spec.ProtoExecutionResult executeMethods(Set<Method> methods, Object... args) {
        long totalExecutionTime = 0;
        for (Method method : methods) {
            Spec.ProtoExecutionResult result = execute(method, args);
            totalExecutionTime += result.getExecutionTime();
            if(result.getFailed()){
                return result;
            }
        }
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(totalExecutionTime).build();
    }
}
