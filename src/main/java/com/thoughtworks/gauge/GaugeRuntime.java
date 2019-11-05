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
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;
import com.thoughtworks.gauge.scan.ClasspathScanner;
import com.thoughtworks.gauge.scan.CustomClassInitializerScanner;
import com.thoughtworks.gauge.scan.HooksScanner;
import com.thoughtworks.gauge.scan.StaticScanner;
import com.thoughtworks.gauge.scan.StepsScanner;
import com.thoughtworks.gauge.screenshot.CustomScreenshotScanner;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Holds Main for starting Gauge-java
 * 1. Makes connections to gauge
 * 2. Scans Classpath
 * 3. Dispatched all message responses
 */
public class GaugeRuntime {

    private static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->  Logger.fatal("Error in thread " + t.getId(), e));
        StaticScanner staticScanner = new StaticScanner();
        if (System.getenv(GaugeConstant.GAUGE_LSP_GRPC) != null) {
            staticScanner.addStepsToRegistry();
            startGRPCServer(staticScanner);
        } else {
            startGaugeServer(staticScanner);
            for (Thread thread : threads) {
                thread.join();
            }
        }
        System.exit(0);
    }

    private static void startGaugeServer(StaticScanner staticScanner) {
        String portInfo = System.getenv("GAUGE_API_PORTS");
        if (portInfo != null && !portInfo.trim().isEmpty()) {
            List<String> ports = Arrays.asList(portInfo.split(","));
            Logger.debug(String.format("Connecting to port(s): %s", portInfo));
            for (int i = 0, portsSize = ports.size(); i < portsSize; i++) {
                if (i == 0) {
                    connectSynchronously(Integer.parseInt(ports.get(i)), staticScanner);
                } else {
                    connectInParallel(Integer.parseInt(ports.get(i)), staticScanner);
                }
            }
        } else {
            int gaugeInternalPort = readEnvVar(GaugeConstant.GAUGE_INTERNAL_PORT);
            Logger.debug(String.format("Connecting to port: %d", gaugeInternalPort));
            connectSynchronously(gaugeInternalPort, staticScanner);
        }
    }

    private static void startGRPCServer(StaticScanner staticScanner) throws IOException, InterruptedException {
        Server server;
        MessageDispatcher messageDispatcher = new MessageDispatcher(staticScanner, new ClassInstanceManager(ClassInitializerRegistry.classInitializer()));
        LspServer lspServer = new LspServer(messageDispatcher);
        server = ServerBuilder.forPort(0).addService(lspServer).executor(Executors.newFixedThreadPool(1)).build();
        lspServer.addServer(server);
        server.start();
        int port = server.getPort();
        Logger.info("Listening on port:" + port);
        server.awaitTermination();
    }

    private static int readEnvVar(String env) {
        String port = System.getenv(env);
        if (port == null || port.equalsIgnoreCase("")) {
            throw new RuntimeException(env + " not set");
        }
        return Integer.parseInt(port);
    }

    private static void connectInParallel(final int gaugeInternalPort, StaticScanner staticScanner) {
        Thread thread = new Thread(() -> dispatchMessages(staticScanner, makeConnection(gaugeInternalPort)));
        startThread(thread);
    }

    private static void connectSynchronously(final int gaugeInternalPort, StaticScanner staticScanner) {
        GaugeConnector connector = makeConnection(gaugeInternalPort);
        ClasspathScanner classpathScanner = new ClasspathScanner();
        classpathScanner.scan(new StepsScanner(staticScanner.getRegistry()), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
        Thread thread = new Thread(() -> dispatchMessages(staticScanner, connector));
        startThread(thread);
    }

    private static void startThread(Thread thread) {
        threads.add(thread);
        thread.start();
    }

    private static GaugeConnector makeConnection(int gaugeInternalPort) {
        GaugeConnector connector = new GaugeConnector();
        connector.makeConnectionsToGaugeCore(gaugeInternalPort);
        return connector;
    }

    private static void dispatchMessages(StaticScanner staticScanner, GaugeConnector connector) {
        try {
            ClassInstanceManager instanceManager = new ClassInstanceManager(ClassInitializerRegistry.classInitializer());
            Gauge.setInstanceManager(instanceManager);
            MessageDispatcher messageDispatcher = new MessageDispatcher(staticScanner, instanceManager);
            messageDispatcher.dispatchMessages(connector);
        } catch (IOException e) {
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, e);
        }
    }
}
