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

import com.thoughtworks.gauge.connection.GaugeConnector;
import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.scan.ClasspathScanner;
import com.thoughtworks.gauge.scan.HooksScanner;
import com.thoughtworks.gauge.scan.StepsScanner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds Main for starting Gauge-java
 * 1. Makes connections to gauge
 * 2. Scans Classpath
 * 3. Dispatched all message responses
 */
public class GaugeRuntime {

    private static List<Thread> threads = new ArrayList<Thread>();

    public static void main(String[] args) throws Exception {
        listenForRequests(readEnvVar(GaugeConstant.GAUGE_INTERNAL_PORT), readEnvVar(GaugeConstant.GAUGE_API_PORT));
        String portInfo = System.getenv("GAUGE_API_PORTS");
        if (portInfo != null && !portInfo.trim().isEmpty()) {
            String[] ports = portInfo.split(",");
            int apiPort = readEnvVar(GaugeConstant.GAUGE_API_PORT);
            for (String port : ports) {
                listenForRequests(Integer.parseInt(port), apiPort);
            }
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    private static int readEnvVar(String env) {
        String port = System.getenv(env);
        if (port == null || port.equalsIgnoreCase("")) {
            throw new RuntimeException(env + " not set");
        }
        return Integer.parseInt(port);
    }

    private static void listenForRequests(final int gaugeInternalPort, final int gaugeApiPort) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GaugeConnector connector = new GaugeConnector();
                connector.makeConnectionsToGaugeCore(gaugeInternalPort, gaugeApiPort);
                new ClasspathScanner().scan(new StepsScanner(connector), new HooksScanner(), new CustomScreenshotScanner());
                try {
                    new MessageDispatcher().dispatchMessages(connector);
                } catch (IOException e) {
                    Thread t = Thread.currentThread();
                    t.getUncaughtExceptionHandler().uncaughtException(t,e);
                }
            }
        });
        threads.add(thread);
        thread.start();
    }
}
