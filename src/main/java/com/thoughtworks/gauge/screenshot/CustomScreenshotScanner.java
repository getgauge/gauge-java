/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.screenshot;

import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.scan.IScanner;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Scans for a custom screenshot grabber.
 */
public class CustomScreenshotScanner implements IScanner {
    public void scan(Reflections reflections) {
        Set<Class<? extends ICustomScreenshotGrabber>> customScreenshotGrabbers = reflections.getSubTypesOf(ICustomScreenshotGrabber.class);

        if (customScreenshotGrabbers.size() > 0) {
            Class<? extends ICustomScreenshotGrabber> customScreenGrabber = customScreenshotGrabbers.iterator().next();
            Logger.debug(String.format("Using %s as custom screenshot grabber", customScreenGrabber.getName()));
            ScreenshotFactory.setCustomScreenshotGrabber(customScreenGrabber);
        }

        Set<Class<? extends CustomScreenshotWriter>> customScreenshotWriters = reflections.getSubTypesOf(CustomScreenshotWriter.class);

        if (customScreenshotWriters.size() > 0) {
            Class<? extends CustomScreenshotWriter> customScreenWriter = customScreenshotWriters.iterator().next();
            Logger.debug(String.format("Using %s as custom screenshot grabber", customScreenWriter.getName()));
            ScreenshotFactory.setCustomScreenshotGrabber(customScreenWriter);
        }
    }
}
