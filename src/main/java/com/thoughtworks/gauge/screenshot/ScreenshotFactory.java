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
import com.thoughtworks.gauge.Logger;

import javax.imageio.ImageIO;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Used to take screenshots on failure.
 */
public class ScreenshotFactory {

    public static final String IMAGE_EXTENSION = "png";
    private static Class<? extends ICustomScreenshotGrabber> customScreenshotGrabber;
    private static ClassInstanceManager manager;

    public ScreenshotFactory(ClassInstanceManager manager) {
        this.manager = manager;
    }

    static void setCustomScreenshotGrabber(Class<? extends ICustomScreenshotGrabber> customScreenGrabber) {
        customScreenshotGrabber = customScreenGrabber;
    }

    public String getScreenshotBytes() {
        if (shouldTakeScreenshot()) {
            return takeScreenshot();
        }
        return "";
    }

    private String takeScreenshot() {
        if (customScreenshotGrabber != null) {
            try {
                ICustomScreenshotGrabber customScreenGrabberInstance = (ICustomScreenshotGrabber) manager.get(customScreenshotGrabber);
                byte[] bytes = customScreenGrabberInstance.takeScreenshot();
                File file = generateUniqueScreenshotFile();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                ImageIO.write(bufferedImage, IMAGE_EXTENSION, file);
                return file.getName();
            } catch (Exception e) {
                Logger.error(String.format("Failed to take Custom screenshot: %s : %s", customScreenshotGrabber.getCanonicalName(), e.getMessage()));
                Logger.warning("Capturing regular screenshot..");
            }
        }
        return captureScreen();
    }

    private File generateUniqueScreenshotFile() {
        Path path = Paths.get(System.getenv("screenshots_dir"), String.format("screenshot-%s.png", UUID.randomUUID().toString()));
        return new File(path.toAbsolutePath().toString());
    }

    private String captureScreen() {
        File file = generateUniqueScreenshotFile();
        if (shouldTakeScreenshot()) {
            try {
                // Union together all screen devices for 1 large screenshot
                Rectangle screenRect = new Rectangle(0, 0, 0, 0);
                for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                    screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
                }
                BufferedImage image = new Robot().createScreenCapture(screenRect);
                ImageIO.write(image, IMAGE_EXTENSION, file);
            } catch (Throwable e) {
                Logger.error("Failed to take regular screenshot: " + e.getMessage());
            }
        }
        return file.getName();
    }

    private boolean shouldTakeScreenshot() {
        String screenshotEnabled = System.getenv(GaugeConstant.SCREENSHOT_ENABLED);
        return !(screenshotEnabled == null || screenshotEnabled.toLowerCase().equals("false"));
    }
}
