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

package com.thoughtworks.gauge;

import com.thoughtworks.gauge.screenshot.ScreenshotFactory;

import java.util.ArrayList;
import java.util.List;

public class Gauge {

    private static ThreadLocal<List<String>> messages = new InheritableThreadLocal<List<String>>() {
        @Override
        protected List<String> initialValue() {
            return new ArrayList<>();
        }
    };
    private static ClassInstanceManager instanceManager;
    private static ThreadLocal<List<byte[]>> screenshots = new InheritableThreadLocal<List<byte[]>>() {
        @Override
        protected List<byte[]> initialValue() {
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
        return messages.get();
    }

    public static void captureScreenshot() {
        byte[] screenshotBytes = new ScreenshotFactory(instanceManager).getScreenshotBytes();
        getScreenshots().add(screenshotBytes);
    }

    static List<byte[]> getScreenshots() {
        return screenshots.get();
    }
}
