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
import com.thoughtworks.gauge.scan.StaticScanner;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds Main for starting Gauge-java
 * 1. Makes connections to gauge
 * 2. Scans Classpath
 * 3. Dispatched all message responses
 */
public class GaugeRuntime {

    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        if (System.getenv(GaugeConstant.GAUGE_LSP_GRPC) != null) {
            startGRPCServer();
        } else {
            startGaugeServer();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.exit(0);
    }

    private static void startGaugeServer() {
        StaticScanner staticScanner = new StaticScanner();
        MessageDispatcher messageDispatcher = new MessageDispatcher(staticScanner);
        int apiPort = readEnvVar(GaugeConstant.GAUGE_API_PORT);
        String portInfo = System.getenv("GAUGE_API_PORTS");
        if (portInfo != null && !portInfo.trim().isEmpty()) {
            List<String> ports = Arrays.asList(portInfo.split(","));
            for (int i = 0, portsSize = ports.size(); i < portsSize; i++) {
                if (i == 0) {
                    connectSynchronously(Integer.parseInt(ports.get(i)), apiPort, messageDispatcher, staticScanner);
                } else {
                    connectInParallel(Integer.parseInt(ports.get(i)), apiPort, messageDispatcher);
                }
            }
        } else {
            connectSynchronously(readEnvVar(GaugeConstant.GAUGE_INTERNAL_PORT), apiPort, messageDispatcher, staticScanner);
        }
    }

    private static void startGRPCServer() throws IOException, InterruptedException {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsToRegistry();
        MessageDispatcher messageDispatcher = new MessageDispatcher(staticScanner);
        Server server = null;
        LspServer lspServer = new LspServer(server, messageDispatcher);
        server = ServerBuilder.forPort(0).addService(lspServer).build();
        server.start();
        int port = server.getPort();
        System.out.println("Listening on port:" + port);
        server.awaitTermination();
    }

    private static int readEnvVar(String env) {
        String port = System.getenv(env);
        if (port == null || port.equalsIgnoreCase("")) {
            throw new RuntimeException(env + " not set");
        }
        return Integer.parseInt(port);
    }

    private static void connectInParallel(final int gaugeInternalPort, final int gaugeApiPort, MessageDispatcher messageDispatcher) {
        Thread thread = new Thread(() -> dispatchMessages(messageDispatcher, makeConnection(gaugeInternalPort, gaugeApiPort)));
        startThread(thread);
    }

    private static void connectSynchronously(final int gaugeInternalPort, final int gaugeApiPort, MessageDispatcher messageDispatcher, StaticScanner staticScanner) {
        GaugeConnector connector = makeConnection(gaugeInternalPort, gaugeApiPort);
        staticScanner.buildStepRegistry(connector);
        Thread thread = new Thread(() -> dispatchMessages(messageDispatcher, connector));
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

    private static void dispatchMessages(MessageDispatcher messageDispatcher, GaugeConnector connector) {
        try {
            messageDispatcher.dispatchMessages(connector);
        } catch (IOException e) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, e);
        }
    }
}
