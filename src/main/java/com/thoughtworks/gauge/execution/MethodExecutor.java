/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.SkipScenarioException;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.screenshot.ScreenshotFactory;
import gauge.messages.Spec;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Optional;

public class MethodExecutor {
    private final ClassInstanceManager instanceManager;

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
            long execTime = System.currentTimeMillis() - startTime;

            if (e.getCause() instanceof SkipScenarioException) {
                return createSkippedExecResult(execTime, (SkipScenarioException) e.getCause());
            }

            Class<?>[] continuableExceptions = Optional.ofNullable(method.getAnnotation(ContinueOnFailure.class))
                    .map(ContinueOnFailure::value).
                    orElseGet(() -> new Class<?>[]{});

            return createFailureExecResult(execTime, e, method.isAnnotationPresent(ContinueOnFailure.class), continuableExceptions);
        }
    }

    private Spec.ProtoExecutionResult createSkippedExecResult(long execTime, SkipScenarioException cause) {
        return Spec.ProtoExecutionResult.newBuilder()
                .setSkipScenario(true)
                .addMessage(Optional.ofNullable(cause.getMessage()).orElse("SKIPPED"))
                .setExecutionTime(execTime)
                .build();
    }

    private Spec.ProtoExecutionResult createFailureExecResult(long execTime, Throwable e, boolean recoverable, Class<?>[] continuableExceptions) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder().setFailed(true);
        if (Util.shouldTakeFailureScreenshot()) {
            String screenshotFileName = new ScreenshotFactory(instanceManager).getScreenshotBytes();
            builder.setFailureScreenshotFile(screenshotFileName);
        }
        if (e.getCause() != null) {
            builder.setRecoverableError(false);
            for (Class<?> c : continuableExceptions) {
                if (c.isAssignableFrom(e.getCause().getClass()) && recoverable) {
                    builder.setRecoverableError(true);
                    break;
                }
            }
            builder.setErrorMessage(e.getCause().toString());
            builder.setStackTrace(formatStackTrace(e.getCause()));
        } else {
            builder.setRecoverableError(recoverable);
            builder.setErrorMessage(e.toString());
            builder.setStackTrace(formatStackTrace(e));
        }
        builder.setExecutionTime(execTime);
        return builder.build();
    }

    private String formatStackTrace(Throwable t) {
        if (t == null) {
            return "";
        }
        StringWriter out = new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        return out.toString();
    }
}
