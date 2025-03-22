/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.thoughtworks.gauge.command.GaugeCommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Holds Main for starting Gauge-java 1. Makes connections to gauge 2. Scans
 * Classpath 3. Dispatched all message responses
 */
public class GaugeRuntime {

    private static final Logger LOGGER = LogManager.getLogger(GaugeRuntime.class);

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> GaugeExceptionLogger.fatal(LOGGER, "Error in thread " + t.getId(), e));
        if (args.length == 0) {
            System.out.println("usage: GaugeJava --<start|init>");
            System.exit(1);
        }
        String phase = args[0];
        GaugeCommandFactory gaugeCommandFactory = new GaugeCommandFactory();
        gaugeCommandFactory.getExecutor(phase).execute();
    }

}
