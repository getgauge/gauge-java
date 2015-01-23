package com.thoughtworks.gauge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class ScreenshotFactory {

    public static final String IMAGE_EXTENSION = "png";

    public byte[] getScreenshotBytes() {
        ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
        if (shouldTakeScreenshot()) {
            try {
                BufferedImage image = null;
                image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, IMAGE_EXTENSION, imageBytes);
            } catch (Throwable e) {
                System.out.println("Failed to take screenshot: " + e.getMessage());
                return new byte[0];
            }
        }
        return imageBytes.toByteArray();
    }

    private boolean shouldTakeScreenshot() {
        String screenshotEnabled = System.getenv(GaugeConstant.SCREENSHOT_ENABLED);
        return !(screenshotEnabled == null || screenshotEnabled.toLowerCase().equals("false"));
    }
}

