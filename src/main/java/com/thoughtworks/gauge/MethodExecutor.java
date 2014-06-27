package com.thoughtworks.gauge;

import com.google.protobuf.ByteString;
import main.Spec;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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
            ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
            try {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, "png", imageBytes);
            } catch (Exception ex) {
                System.out.println("Screenshot is not available. " + ex.getMessage());
            }
            Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder().setFailed(true);
            if (e.getCause() != null) {
                builder.setErrorMessage(e.getCause().toString());
                builder.setStackTrace(formatStackTrace(e.getCause().getStackTrace()));
            } else {
                builder.setErrorMessage(e.toString());
                builder.setStackTrace(formatStackTrace(e.getStackTrace()));
            }

            if (imageBytes.size() > 0) {
                builder.setScreenShot(ByteString.copyFrom(imageBytes.toByteArray()));
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
