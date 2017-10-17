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

import java.util.ArrayList;
import java.util.List;

public class Gauge {

    private static ThreadLocal<List<String>> messages = new InheritableThreadLocal<List<String>>() {
        @Override
        protected List<String> initialValue() {
            return new ArrayList<String>();
        }
    };

    static List<String> getPendingMessages() {
        List<String> pendingMessages = new ArrayList<String>(getMessages());
        getMessages().clear();
        return pendingMessages;
    }

    static void clearMessages() {
        getMessages().clear();
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

    private static List<String> getMessages() {
        return messages.get();
    }
}
