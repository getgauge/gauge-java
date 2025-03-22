/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.RunnerServiceHandler;
import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.scan.StaticScanner;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

import static com.thoughtworks.gauge.GaugeConstant.*;

public class StartCommand implements GaugeJavaCommand {

    private static final Logger LOGGER = LogManager.getLogger(StartCommand.class);

    @Override
    public void execute() throws Exception {
        boolean multithreading = Boolean.parseBoolean(System.getenv(ENABLE_MULTITHREADING_ENV));
        LOGGER.debug("multithreading is set to {}", multithreading);
        int numberOfStreams = 1;

        if (multithreading) {
            String streamsCount = System.getenv(STREAMS_COUNT_ENV);
            try {
                numberOfStreams = Integer.parseInt(streamsCount);
                LOGGER.debug("multithreading enabled, number of threads={}", numberOfStreams);
            } catch (NumberFormatException e) {
                LOGGER.debug("multithreading enabled, but could not read " + STREAMS_COUNT_ENV + " as int. Got " + STREAMS_COUNT_ENV + "={}", streamsCount);
                LOGGER.debug("using numberOfStreams=1, err: {}", e.getMessage());
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
            .executor(Runnable::run)
            .build();
        runnerServiceHandler.addServer(server);
        long elapsed = System.currentTimeMillis() - start;
        LOGGER.debug("gauge-java took {} milliseconds to load and scan. This should be less than 'runner_connection_timeout' config value.", elapsed);
        LOGGER.debug("run 'gauge config runner_connection_timeout' and verify that it is < {}", elapsed);
        LOGGER.debug("starting gRPC server...");
        server.start();
        int port = server.getPort();
        LOGGER.debug("started gRPC server on port {}", port);
        LOGGER.info("Listening on port:{}", port);
        server.awaitTermination();
        LOGGER.debug("Runner killed gracefully.");
        System.exit(0);
    }
}
