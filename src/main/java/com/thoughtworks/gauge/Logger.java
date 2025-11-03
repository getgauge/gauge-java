/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.assertj.core.util.Throwables;

public class Logger {
    public static void info(String message) {
        logToStdout("info", message);
    }

    public static void error(String message) {
        logToStdErr("error", message);
    }

    public static void error(String message, Throwable t) {
        error(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));
    }

    public static void warning(String message) {
        logToStdout("warning", message);
    }

    public static void warning(String message, Throwable t) {
        warning(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));
    }

    public static void debug(String message) {
        logToStdout("debug", message);
    }

    public static void fatal(String message) {
        logToStdErr("fatal", message);
        System.exit(1);
    }

    public static void fatal(String message, Throwable t) {
        fatal(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));
    }

    private static void logToStdout(String level, String message) {
        System.out.println(LogMessage.of(level, message));
    }

    private static void logToStdErr(String level, String message) {
        System.err.println(LogMessage.of(level, message));
    }

    private record LogMessage(@Expose @SerializedName("logLevel") String level, @Expose @SerializedName("message") String message) {
        private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        public static LogMessage of(String level, String message) {
            return new LogMessage(level, message);
        }

        @Override
        public String toString() {
            return GSON.toJson(this);
        }
    }
}
