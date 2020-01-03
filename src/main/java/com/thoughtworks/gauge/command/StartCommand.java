package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.RunnerServiceHandler;
import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.scan.StaticScanner;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.concurrent.Executors;

public class StartCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws Exception {
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsToRegistry();
        Server server;
        MessageProcessorFactory messageProcessorFactory = new MessageProcessorFactory(staticScanner);
        RunnerServiceHandler runnerServiceHandler = new RunnerServiceHandler(messageProcessorFactory);
        server = ServerBuilder.forPort(0).addService(runnerServiceHandler).executor(Executors.newFixedThreadPool(1)).build();
        runnerServiceHandler.addServer(server);
        server.start();
        int port = server.getPort();
        Logger.info("Listening on port:" + port);
        server.awaitTermination();
        System.exit(0);
    }
}
