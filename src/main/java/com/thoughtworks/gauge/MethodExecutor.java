package com.thoughtworks.gauge;

import com.google.protobuf.ByteString;
import gauge.messages.Spec;

import java.lang.reflect.Method;

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
            builder.setExecutionTime(endTime - startTime);
            return builder.build();
        }
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
}
