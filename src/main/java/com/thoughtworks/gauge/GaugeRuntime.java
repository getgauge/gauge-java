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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thoughtworks.gauge.connection.GaugeConnector;
import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParsingChain;
import com.thoughtworks.gauge.registry.StepRegistry;
import com.thoughtworks.gauge.scan.ClasspathScanner;
import com.thoughtworks.gauge.scan.CustomClassInitializerScanner;
import com.thoughtworks.gauge.scan.HooksScanner;
import com.thoughtworks.gauge.scan.StepsScanner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;

/**
 * Holds Main for starting Gauge-java
 * 1. Makes connections to gauge
 * 2. Scans Classpath
 * 3. Dispatched all message responses
 */
public class GaugeRuntime {

    private static List<Thread> threads = new ArrayList<Thread>();

    public static void main(String[] args) throws Exception {
        int apiPort = readEnvVar(GaugeConstant.GAUGE_API_PORT);
        String portInfo = System.getenv("GAUGE_API_PORTS");
        StepRegistry stepRegistry = new StepRegistry();
        if (portInfo != null && !portInfo.trim().isEmpty()) {
            List<String> ports = Arrays.asList(portInfo.split(","));
            for (int i = 0, portsSize = ports.size(); i < portsSize; i++) {
                if (i == 0) {
                    connectSynchronously(Integer.parseInt(ports.get(i)), apiPort, stepRegistry);
                } else {
                    connectInParallel(Integer.parseInt(ports.get(i)), apiPort, stepRegistry);
                }
            }
        } else {
            connectSynchronously(readEnvVar(GaugeConstant.GAUGE_INTERNAL_PORT), apiPort, stepRegistry);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.exit(0);
    }

    private static int readEnvVar(String env) {
        String port = System.getenv(env);
        if (port == null || port.equalsIgnoreCase("")) {
            throw new RuntimeException(env + " not set");
        }
        return Integer.parseInt(port);
    }

    private static void connectInParallel(final int gaugeInternalPort, final int gaugeApiPort, StepRegistry stepRegistry) {
        Thread thread = new Thread(() -> dispatchMessages(makeConnection(gaugeInternalPort, gaugeApiPort), stepRegistry));
        startThread(thread);
    }

    private static void connectSynchronously(final int gaugeInternalPort, final int gaugeApiPort, StepRegistry stepRegistry) {
        final GaugeConnector connector = makeConnection(gaugeInternalPort, gaugeApiPort);
        new ClasspathScanner().scan(new StepsScanner(connector, stepRegistry), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
        Thread thread = new Thread(() -> dispatchMessages(connector, stepRegistry));
        startThread(thread);
    }

    private static void startThread(Thread thread) {
        threads.add(thread);
        thread.start();
    }

    private static GaugeConnector makeConnection(int gaugeInternalPort, int gaugeApiPort) {
        GaugeConnector connector = new GaugeConnector();
        connector.makeConnectionsToGaugeCore(gaugeInternalPort, gaugeApiPort);
        return connector;
    }

    private static void dispatchMessages(GaugeConnector connector, StepRegistry stepRegistry) {
        try {
            new MessageDispatcher(new ParameterParsingChain(), stepRegistry).dispatchMessages(connector);
        } catch (IOException e) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, e);
        }
    }
}
