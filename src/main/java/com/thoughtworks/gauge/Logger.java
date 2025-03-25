/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.assertj.core.util.Throwables;

/**
 * @deprecated Use a log4j2 {@link org.apache.logging.log4j.Logger} to log any required messages.
 *             Additionally {@link GaugeExceptionLogger} is available to log exceptions
 */
@Deprecated
public class Logger {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(Logger.class);

    /**
     * @deprecated Use log4j2 {@link org.apache.logging.log4j.Logger#info}
     */
    @Deprecated
    public static void info(String message) {
        logToStdout("info", message);
    }

    /**
     * @deprecated Use log4j2 {@link org.apache.logging.log4j.Logger#error}
     */
    @Deprecated
    public static void error(String message) {
        logToStdErr("error", message);
    }

    /**
     * @deprecated Use {@link GaugeExceptionLogger#error}
     */
    @Deprecated
    public static void error(String message, Throwable t) {
        error(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));
    }

    /**
     * @deprecated Use log4j2 {@link org.apache.logging.log4j.Logger#warn}
     */
    @Deprecated
    public static void warning(String message) {
        logToStdout("warn", message);
    }

    /**
     * @deprecated Use {@link GaugeExceptionLogger#warning}
     */
    @Deprecated
    public static void warning(String message, Throwable t) {
        warning(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));
    }

    /**
     * @deprecated Use log4j2 {@link org.apache.logging.log4j.Logger#debug}
     */
    @Deprecated
    public static void debug(String message) {
        logToStdout("debug", message);
    }

    /**
     * @deprecated Use {@link GaugeExceptionLogger#fatal(org.apache.logging.log4j.Logger, String)}
     */
    @Deprecated
    public static void fatal(String message) {
        logToStdErr("fatal", message);
        System.exit(1);
    }

    /**
     * @deprecated Use {@link GaugeExceptionLogger#fatal(org.apache.logging.log4j.Logger, String, Throwable)}
     */
    @Deprecated
    public static void fatal(String message, Throwable t) {
        fatal(String.format("%s\n%s\n%s", message, t.getMessage(), Throwables.getStackTrace(t)));

    }

    @Deprecated
    private static void logToStdout(String level, String message) {
        LOGGER.log(Level.valueOf(level), message);
    }

    @Deprecated
    private static void logToStdErr(String level, String message) {
        LOGGER.log(Level.valueOf(level), message);
    }
}
