/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.apache.commons.lang3.exception.ExceptionUtils;
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
