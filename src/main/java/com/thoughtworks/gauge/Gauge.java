/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.thoughtworks.gauge.screenshot.ScreenshotFactory;

import java.util.ArrayList;
import java.util.List;

public class Gauge {

    private static final ThreadLocal<List<String>> MESSAGES = new InheritableThreadLocal<>() {
        @Override
        protected List<String> initialValue() {
            return new ArrayList<>();
        }
    };
    private static ClassInstanceManager instanceManager;
    private static final ThreadLocal<List<String>> SCREENSHOTS = new InheritableThreadLocal<>() {
        @Override
        protected List<String> initialValue() {
            return new ArrayList<>();
        }
    };

    public static void setInstanceManager(ClassInstanceManager instanceManager) {
        Gauge.instanceManager = instanceManager;
    }

    /**
     * @param message - Custom message that can be added at runtime that will be visible in reports.
     */
    public static void writeMessage(String message) {
        getMessages().add(message);
    }

    /**
     * @param format - Custom messages that can be added at runtime that will be visible in reports.
     *               Format of the string message
     * @param args   - Arguments for the format string as passed into String.format()
     */
    public static void writeMessage(String format, String... args) {
        getMessages().add(String.format(format, args));
    }

    static List<String> getMessages() {
        return MESSAGES.get();
    }

    public static void captureScreenshot() {
        String screenshotFileName = new ScreenshotFactory(instanceManager).getScreenshotBytes();
        getScreenshots().add(screenshotFileName);
    }

    static List<String> getScreenshots() {
        return SCREENSHOTS.get();
    }
}
