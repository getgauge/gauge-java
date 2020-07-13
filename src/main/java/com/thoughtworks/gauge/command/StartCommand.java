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
import io.grpc.ServerBuilder;

import java.util.concurrent.Executor;

import static com.thoughtworks.gauge.GaugeConstant.*;

public class StartCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws Exception {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsToRegistry();
        Server server;
        boolean multithreading = System.getenv(ENABLE_MULTITHREADING_ENV).toLowerCase() == "true";
        Logger.debug("multithreading is set to " + multithreading);
        int numberOfStreams = 1;

        if (multithreading) {
            String streamValue = System.getenv(STREAMS_COUNT_ENV);
            if (streamValue != null && !streamValue.isEmpty()) {
                numberOfStreams = Integer.parseInt(streamValue);
            }
            Logger.debug("multithreading enabled, number of threads=" + numberOfStreams);
        }

        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(staticScanner);
        RunnerServiceHandler runnerServiceHandler = new RunnerServiceHandler(messageProcessorFactory, multithreading, numberOfStreams);
        server = ServerBuilder.forPort(0).addService(runnerServiceHandler).executor((Executor) Runnable::run).build();
        runnerServiceHandler.addServer(server);
        Logger.debug("starting gRPC server...");
        server.start();
        int port = server.getPort();
        Logger.debug("started gRPC server on port " + port);
        Logger.info("Listening on port:" + port);
        server.awaitTermination();
    }
}
