/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.thoughtworks.gauge.command.GaugeCommandFactory;

/**
 * Holds Main for starting Gauge-java 1. Makes connections to gauge 2. Scans
 * Classpath 3. Dispatched all message responses
 */
public class GaugeRuntime {
    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Logger.fatal("Error in thread " + t.getId(), e));
        if (args.length == 0) {
            System.out.println("usage: GaugeJava --<start|init>");
            System.exit(1);
        }
        String phase = args[0];
        GaugeCommandFactory gaugeCommandFactory = new GaugeCommandFactory();
        gaugeCommandFactory.getExecutor(phase).execute();
    }

}
