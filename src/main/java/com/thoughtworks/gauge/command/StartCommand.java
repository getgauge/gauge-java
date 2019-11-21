package com.thoughtworks.gauge.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.GaugeConstant;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.LspServer;
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

public class StartCommand implements GaugeJavaCommand {
    private int readEnvVar(String env) {
        String port = System.getenv(env);
        if (port == null || port.equalsIgnoreCase("")) {
            throw new RuntimeException(env + " not set");
        }
        return Integer.parseInt(port);
    }

    private List<Thread> threads = new ArrayList<>();

    @Override
    public void execute() throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Logger.fatal("Error in thread " + t.getId(), e));
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

    private void startGaugeServer(StaticScanner staticScanner) {
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

    private void startGRPCServer(StaticScanner staticScanner) throws IOException, InterruptedException {
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

    private void connectInParallel(final int gaugeInternalPort, StaticScanner staticScanner) {
        Thread thread = new Thread(() -> dispatchMessages(staticScanner, makeConnection(gaugeInternalPort)));
        startThread(thread);
    }

    private void connectSynchronously(final int gaugeInternalPort, StaticScanner staticScanner) {
        GaugeConnector connector = makeConnection(gaugeInternalPort);
        ClasspathScanner classpathScanner = new ClasspathScanner();
        classpathScanner.scan(new StepsScanner(staticScanner.getRegistry()), new HooksScanner(), new CustomScreenshotScanner(), new CustomClassInitializerScanner());
        Thread thread = new Thread(() -> dispatchMessages(staticScanner, connector));
        startThread(thread);
    }

    private void startThread(Thread thread) {
        threads.add(thread);
        thread.start();
    }

    private GaugeConnector makeConnection(int gaugeInternalPort) {
        GaugeConnector connector = new GaugeConnector();
        connector.makeConnectionsToGaugeCore(gaugeInternalPort);
        return connector;
    }

    private void dispatchMessages(StaticScanner staticScanner, GaugeConnector connector) {
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
