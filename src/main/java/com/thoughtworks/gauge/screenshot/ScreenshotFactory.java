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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Used to take screenshots on failure.
 */
public class ScreenshotFactory {
    public static final String IMAGE_EXTENSION = "png";
    private static volatile Class<? extends CustomScreenshotWriter> customScreenshotWriter;

    private final ClassInstanceManager manager;

    public ScreenshotFactory(ClassInstanceManager manager) {
        this.manager = manager;
    }

    static void setCustomScreenshotGrabber(Class<? extends CustomScreenshotWriter> writerClass) {
        ScreenshotFactory.customScreenshotWriter = writerClass;
    }

    public String getScreenshotBytes() {
        return takeScreenshot();
    }

    private String takeScreenshot() {
        if (customScreenshotWriter != null) {
            try {
                if (manager.get(customScreenshotWriter) instanceof CustomScreenshotWriter writer) {
                    return writer.takeScreenshot();
                }
            } catch (Exception e) {
                Logger.error(String.format("Failed to take Custom screenshot: %s : %s", customScreenshotWriter.getCanonicalName(), e.getMessage()));
                Logger.warning("Capturing regular screenshot..");
            }
        }
        return captureScreen();
    }

    private File generateUniqueScreenshotFile() {
        String fileName = String.format("screenshot-%s.%s", UUID.randomUUID().toString(), IMAGE_EXTENSION);
        Path path = Path.of(System.getenv(GaugeConstant.SCREENSHOTS_DIR_ENV), fileName);
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
