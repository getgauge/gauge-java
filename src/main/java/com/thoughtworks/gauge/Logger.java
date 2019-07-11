// Copyright 2019 ThoughtWorks, Inc.

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

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;

public class Logger {
    public static void info(String message) {
        logToStdout("info", message);
    }

    public static void error(String message) {
        logToStdErr("error", message);
    }

    public static void error(String message, Throwable t) {
        error(String.format("%s\n%s\n%s", message, t.getMessage(), ExceptionUtils.getStackTrace(t)));
    }

    public static void warning(String message) {
        logToStdout("warning", message);
    }

    public static void warning(String message, Throwable t) {
        warning(String.format("%s\n%s\n%s", message, t.getMessage(), ExceptionUtils.getStackTrace(t)));
    }

    public static void debug(String message) {
        logToStdout("debug", message);
    }

    public static void fatal(String message) {
        logToStdErr("fatal", message);
        System.exit(1);
    }

    public static void fatal(String message, Throwable t) {
        fatal(String.format("%s\n%s\n%s", message, t.getMessage(), ExceptionUtils.getStackTrace(t)));

    }

    private static void logToStdout(String level, String message) {
        System.out.println(getJsonObject(level, message));
    }

    private static JSONObject getJsonObject(String level, String message) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("logLevel", level);
        jsonObj.put("message", message);
        return jsonObj;
    }

    private static void logToStdErr(String level, String message) {
        System.err.println(getJsonObject(level, message));
    }
}
