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

package com.thoughtworks.gauge.screenshot;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.GaugeConstant;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * Used to take screenshots on failure
 */
public class ScreenshotFactory {

    public static final String IMAGE_EXTENSION = "png";
    private static Class<? extends ICustomScreenshotGrabber> customScreenshotGrabber;

    static void setCustomScreenshotGrabber(Class<? extends ICustomScreenshotGrabber> customScreenGrabber) {
        customScreenshotGrabber = customScreenGrabber;
    }

    public byte[] getScreenshotBytes() {
        if (shouldTakeScreenshot()) {
            return takeScreenshot();
        }
        return new byte[0];
    }

    private byte[] takeScreenshot() {
        if (customScreenshotGrabber != null) {
            try {
                ICustomScreenshotGrabber customScreenGrabberInstance = (ICustomScreenshotGrabber) ClassInstanceManager.get(customScreenshotGrabber);
                byte[] bytes = customScreenGrabberInstance.takeScreenshot();
                if (bytes == null) {
                    bytes = new byte[0];
                }
                return bytes;
            } catch (Exception e) {
                System.err.println(String.format("Failed to take Custom screenshot: %s : %s", customScreenshotGrabber.getCanonicalName(), e.getMessage()));
                System.out.println("Capturing regular screenshot..");
            }
        }
        return captureScreen();
    }

    private byte[] captureScreen() {
        ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
        if (shouldTakeScreenshot()) {
            try {
                BufferedImage image = null;
                image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ImageIO.write(image, IMAGE_EXTENSION, imageBytes);
            } catch (Throwable e) {
                System.err.println("Failed to take regular screenshot: " + e.getMessage());
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
