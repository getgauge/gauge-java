/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Throwables;

import java.util.Optional;

/**
 * Class responsible for logging exceptions thrown within the Gauge Java plugin.
 */
public class GaugeExceptionLogger {

    /**
     * Logs a warning message {@link Throwable}.
     *
     * @param logger The {@link Logger} that the {@link Throwable} will be logged under
     * @param message The message to log with the {@link Throwable}
     * @param throwable The {@link Throwable} being logged
     */
    public static void warning(Logger logger, String message, Throwable throwable) {
        logThrowable(logger, Level.WARN, message, throwable);
    }

    /**
     * Logs an error message {@link Throwable}.
     *
     * @param logger The {@link Logger} that the {@link Throwable} will be logged under
     * @param message The message to log with the {@link Throwable}
     * @param throwable The {@link Throwable} being logged
     */
    public static void error(Logger logger, String message, Throwable throwable) {
        logThrowable(logger, Level.ERROR, message, throwable);
    }

    /**
     * Logs a fatal message & {@link Throwable} and calls {@link System#exit}.
     *
     * @param logger The {@link Logger} that the {@link Throwable} will be logged under
     * @param message The message to log with the {@link Throwable}
     * @param throwable The {@link Throwable} being logged
     */
    public static void fatal(Logger logger, String message, Throwable throwable) {
        logThrowable(logger, Level.FATAL, message, throwable);
    }

    /**
     * Logs a fatal message and calls {@link System#exit}.
     *
     * @param logger The {@link Logger} that the message will be logged under
     * @param message The message being logged
     */
    public static void fatal(Logger logger, String message) {
        fatal(logger, message, null);
    }

    /**
     * Logs a message & {@link Throwable}. If the {@link Level} provided is fatal, {@link System#exit} will be called.
     *
     * @param logger The {@link Logger} that the {@link Throwable} will be logged under
     * @param level The {@link Level} to log the {@link Throwable} as
     * @param message The message to log with the {@link Throwable}
     * @param throwable The {@link Throwable} being logged
     */
    public static void logThrowable(Logger logger, Level level, String message, Throwable throwable) {
        StringBuilder logMessageBuilder = new StringBuilder();

        addMessageIfNotNull(logMessageBuilder, null, message);

        // Don't want any null pointer exceptions
        if (throwable != null) {
            addMessageIfNotNull(logMessageBuilder, "Throwable Message: ", throwable.getMessage());
            addMessageIfNotNull(logMessageBuilder, "Stacktrace: ", Throwables.getStackTrace(throwable));
        }

        logger.log(level, logMessageBuilder.toString().strip());

        if (level == Level.FATAL) {
            System.exit(1);
        }
    }

    /**
     * Adds to the given {@link StringBuilder} if the message is not null.
     *
     * @param stringBuilder The {@link StringBuilder} to add to
     * @param appendBefore Any {@link String} to append before the message
     * @param message The message being appended
     */
    private static void addMessageIfNotNull(StringBuilder stringBuilder, String appendBefore, String message) {
        Optional.ofNullable(message)
                .ifPresent(msg -> {
                    if (appendBefore != null) {
                        stringBuilder.append(appendBefore);
                    }
                    stringBuilder.append(message).append("\n");
                });
    }



}
