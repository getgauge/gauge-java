package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.RunnerServiceHandler;
import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.scan.StaticScanner;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.concurrent.Executor;

import static com.thoughtworks.gauge.GaugeConstant.STREAMS_COUNT_ENV;

public class StartCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws Exception {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsToRegistry();
        Server server;
        boolean multithreading = false;
        int stream = 1;
        String streamValue = System.getenv(STREAMS_COUNT_ENV);
        if (streamValue != null && !streamValue.isEmpty()) {
            stream = Integer.parseInt(streamValue);
            multithreading = true;
        }
        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(staticScanner);
        RunnerServiceHandler runnerServiceHandler = new RunnerServiceHandler(messageProcessorFactory, multithreading, stream);
        server = ServerBuilder.forPort(0).addService(runnerServiceHandler).executor((Executor) Runnable::run).build();
        runnerServiceHandler.addServer(server);
        server.start();
        int port = server.getPort();
        Logger.info("Listening on port:" + port);
        server.awaitTermination();
        System.exit(0);
    }
}
