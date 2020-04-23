/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
    private static Class<? extends CustomScreenshot> customScreenshotGrabber;
    private static ClassInstanceManager manager;

    public ScreenshotFactory(ClassInstanceManager manager) {
        this.manager = manager;
    }

    static void setCustomScreenshotGrabber(Class<? extends CustomScreenshot> customScreenGrabber) {
        customScreenshotGrabber = customScreenGrabber;
    }

    public String getScreenshotBytes() {
        return takeScreenshot();
    }

    private String takeScreenshot() {
        if (customScreenshotGrabber != null) {
            try {
                CustomScreenshot customScreenInstance = (CustomScreenshot) manager.get(customScreenshotGrabber);
                if (customScreenInstance instanceof CustomScreenshotWriter) {
                     return ((CustomScreenshotWriter) customScreenInstance).takeScreenshot();
                } else {
                    byte[] bytes = ((ICustomScreenshotGrabber) customScreenInstance).takeScreenshot();
                    File file = generateUniqueScreenshotFile();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                    BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                    ImageIO.write(bufferedImage, IMAGE_EXTENSION, file);
                    return file.getName();
                }
            } catch (Exception e) {
                Logger.error(String.format("Failed to take Custom screenshot: %s : %s", customScreenshotGrabber.getCanonicalName(), e.getMessage()));
                Logger.warning("Capturing regular screenshot..");
            }
        }
        return captureScreen();
    }

    private File generateUniqueScreenshotFile() {
        String fileName = String.format("screenshot-%s.%s", UUID.randomUUID().toString(), IMAGE_EXTENSION);
        Path path = Paths.get(System.getenv(GaugeConstant.SCREENSHOTS_DIR_ENV), fileName);
        return new File(path.toAbsolutePath().toString());
    }

    private String captureScreen() {
        File file = generateUniqueScreenshotFile();
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
        return file.getName();
    }

}
