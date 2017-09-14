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
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.screenshot.ScreenshotFactory;
import gauge.messages.Spec;

import java.lang.reflect.Method;
import java.util.Set;

public class MethodExecutor {
    private ClassInstanceManager instanceManager;

    public MethodExecutor(ClassInstanceManager instanceManager) {
        this.instanceManager = instanceManager;
    }

    public Spec.ProtoExecutionResult execute(Method method, Object... args) {
        long startTime = System.currentTimeMillis();
        try {
            Object instance = instanceManager.get(method.getDeclaringClass());
            method.invoke(instance, args);
            long endTime = System.currentTimeMillis();
            return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(endTime - startTime).build();
        } catch (Throwable e) {
            boolean recoverable = method.isAnnotationPresent(ContinueOnFailure.class);
            Class[] continuableExceptions = new Class[]{};
            if (recoverable) {
                continuableExceptions = method.getAnnotation(ContinueOnFailure.class).value();
            }
            long endTime = System.currentTimeMillis();
            return createFailureExecResult(endTime - startTime, e, recoverable, continuableExceptions);
        }
    }

    private Spec.ProtoExecutionResult createFailureExecResult(long execTime, Throwable e, boolean recoverable, Class[] continuableExceptions) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder().setFailed(true);
        builder.setScreenShot(ByteString.copyFrom(new ScreenshotFactory(instanceManager).getScreenshotBytes()));
        if (e.getCause() != null) {
            builder.setRecoverableError(false);
            for (Class c : continuableExceptions) {
                if (c.isAssignableFrom(e.getCause().getClass()) && recoverable) {
                    builder.setRecoverableError(true);
                    break;
                }
            }
            builder.setErrorMessage(e.getCause().toString());
            builder.setStackTrace(formatStackTrace(e.getCause().getStackTrace()));
        } else {
            builder.setRecoverableError(recoverable);
            builder.setErrorMessage(e.toString());
            builder.setStackTrace(formatStackTrace(e.getStackTrace()));
        }
        builder.setExecutionTime(execTime);
        return builder.build();
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        if (stackTrace == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            if (element.getClassName().equals("sun.reflect.NativeMethodAccessorImpl")) {
                break;
            }
            output.append(element.toString()).append("\n");
        }
        return output.toString();
    }

    public Spec.ProtoExecutionResult executeMethods(Set<Method> methods, Object... args) {
        long totalExecutionTime = 0;
        for (Method method : methods) {
            Spec.ProtoExecutionResult result = execute(method, args);
            totalExecutionTime += result.getExecutionTime();
            if (result.getFailed()) {
                return result;
            }
        }
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(totalExecutionTime).build();
    }
}
