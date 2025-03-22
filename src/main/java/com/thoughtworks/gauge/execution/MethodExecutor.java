/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.ContinueOnFailure;
import com.thoughtworks.gauge.GaugeExceptionLogger;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.screenshot.ScreenshotFactory;
import gauge.messages.Spec;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Set;

public class MethodExecutor {
    private static final Logger LOGGER = LogManager.getLogger(MethodExecutor.class);
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
            boolean recoverable = method.isAnnotationPresent(ContinueOnFailure.class);
            Class[] continuableExceptions = new Class[]{};
            if (recoverable) {
                continuableExceptions = method.getAnnotation(ContinueOnFailure.class).value();
            }
            long endTime = System.currentTimeMillis();
            return createFailureExecResult(method.getName(), endTime - startTime, e, recoverable, continuableExceptions);
        }
    }

    private Spec.ProtoExecutionResult createFailureExecResult(String methodName, long execTime, Throwable e, boolean recoverable, Class[] continuableExceptions) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder().setFailed(true);
        if (Util.shouldTakeFailureScreenshot()) {
            String screenshotFileName = new ScreenshotFactory(instanceManager).getScreenshotBytes();
            builder.setFailureScreenshotFile(screenshotFileName);
        }

        Level logLevel = Level.ERROR;

        if (e.getCause() != null) {
            builder.setRecoverableError(false);
            for (Class c : continuableExceptions) {
                if (c.isAssignableFrom(e.getCause().getClass()) && recoverable) {
                    builder.setRecoverableError(true);
                    logLevel = Level.INFO;
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

        String logMessage = String.format("Method with name %s threw a%srecoverable exception", methodName, builder.getRecoverableError() ? " " : "n un");
        GaugeExceptionLogger.logThrowable(LOGGER, logLevel, logMessage, e);
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
