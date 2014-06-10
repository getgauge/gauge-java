package com.thoughtworks.gauge;

import com.google.protobuf.ByteString;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static main.Messages.ExecutionStatus;

public class MethodExecutor {
    public ExecutionStatus execute(Method method, Object... args) {
        try {
            Object instance = ClassInstanceManager.get(method.getDeclaringClass());
            method.invoke(instance, args);
            return ExecutionStatus.newBuilder().setPassed(true).build();
        } catch (Throwable e) {
            ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
            try {
                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, "png", imageBytes);
            } catch (Exception ex) {
                System.out.println("Screenshot is not available. " + ex.getMessage());
            }
            ExecutionStatus.Builder builder = ExecutionStatus.newBuilder().setPassed(false);
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
