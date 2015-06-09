package com.thoughtworks.gauge.screenshot;

import com.thoughtworks.gauge.scan.IScanner;
import org.reflections.Reflections;

import java.util.Set;

/**
 * Scans for a custom screenshot grabber
 */
public class CustomScreenshotScanner implements IScanner {
    public void scan(Reflections reflections) {
        Set<Class<? extends ICustomScreenshotGrabber>> customScreenshotGrabbers = reflections.getSubTypesOf(ICustomScreenshotGrabber.class);
        if (customScreenshotGrabbers.size() > 0) {
            ScreenshotFactory.setCustomScreenshotGrabber(customScreenshotGrabbers.iterator().next());
        }
    }
}
