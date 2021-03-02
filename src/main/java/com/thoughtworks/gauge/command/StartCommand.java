/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.RunnerServiceHandler;
import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.scan.StaticScanner;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

import static com.thoughtworks.gauge.GaugeConstant.STREAMS_COUNT_ENV;
import static com.thoughtworks.gauge.GaugeConstant.ENABLE_MULTITHREADING_ENV;
import static com.thoughtworks.gauge.GaugeConstant.LOCALHOST;;

public class StartCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws Exception {
        boolean multithreading = Boolean.valueOf(System.getenv(ENABLE_MULTITHREADING_ENV));
        Logger.debug("multithreading is set to " + multithreading);
        int numberOfStreams = 1;

        if (multithreading) {
            String streamsCount = System.getenv(STREAMS_COUNT_ENV);
            try {
                numberOfStreams = Integer.valueOf(streamsCount);
                Logger.debug("multithreading enabled, number of threads=" + numberOfStreams);
            } catch (NumberFormatException e) {
                Logger.debug("multithreading enabled, but could not read " + STREAMS_COUNT_ENV + " as int. Got " + STREAMS_COUNT_ENV + "=" + streamsCount);
                Logger.debug("using numberOfStreams=1, err: " + e.getMessage());
            }
        }

        long start = System.currentTimeMillis();
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsToRegistry();
        Server server;
        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(staticScanner);
        RunnerServiceHandler runnerServiceHandler = new RunnerServiceHandler(messageProcessorFactory, multithreading, numberOfStreams);
        server = NettyServerBuilder
            .forAddress(new InetSocketAddress(LOCALHOST, 0))
            .addService(runnerServiceHandler)
            .executor((Executor) Runnable::run)
            .build();
        runnerServiceHandler.addServer(server);
        long elapsed = System.currentTimeMillis() - start;
        Logger.debug("gauge-java took " + elapsed + "milliseconds to load and scan. This should be less than 'runner_connection_timeout' config value.");
        Logger.debug("run 'gauge config runner_connection_timeout' and verify that it is < " + elapsed);
        Logger.debug("starting gRPC server...");
        server.start();
        int port = server.getPort();
        Logger.debug("started gRPC server on port " + port);
        Logger.info("Listening on port:" + port);
        server.awaitTermination();
        Logger.debug("Runner killed gracefully.");
        System.exit(0);
    }
}
