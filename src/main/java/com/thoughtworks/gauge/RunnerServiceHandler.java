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

import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Messages;
import gauge.messages.RunnerGrpc.RunnerImplBase;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class RunnerServiceHandler extends RunnerImplBase {
    private MessageProcessorFactory messageProcessorFactory;
    private Server server;

    public RunnerServiceHandler(MessageProcessorFactory messageProcessorFactory) {
        this.messageProcessorFactory = messageProcessorFactory;
    }

    public void addServer(Server lspServer) {
        this.server = lspServer;
    }

    @Override
    public void initializeSuiteDataStore(Messages.Empty request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.SuiteDataStoreInit);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setMessageType(Messages.Message.MessageType.SuiteDataStoreInit);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void startExecution(Messages.ExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ExecutionStarting);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setExecutionStartingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void initializeSpecDataStore(Messages.Empty request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.SpecDataStoreInit);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setMessageType(Messages.Message.MessageType.SpecDataStoreInit);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void startSpecExecution(Messages.SpecExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.SpecExecutionStarting);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setSpecExecutionStartingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void initializeScenarioDataStore(Messages.Empty request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ScenarioDataStoreInit);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setMessageType(Messages.Message.MessageType.ScenarioDataStoreInit);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void startScenarioExecution(Messages.ScenarioExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ScenarioExecutionStarting);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setScenarioExecutionStartingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void startStepExecution(Messages.StepExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepExecutionStarting);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepExecutionStartingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void executeStep(Messages.ExecuteStepRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ExecuteStep);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setExecuteStepRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void finishStepExecution(Messages.StepExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepExecutionEnding);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepExecutionEndingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void finishScenarioExecution(Messages.ScenarioExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ScenarioExecutionEnding);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setScenarioExecutionEndingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void finishSpecExecution(Messages.SpecExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.SpecExecutionEnding);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setSpecExecutionEndingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void finishExecution(Messages.ExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.ExecutionEnding);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setExecutionEndingRequest(request);
        Messages.Message message = processor.process(builder.build());
        responseObserver.onNext(message.getExecutionStatusResponse());
        responseObserver.onCompleted();
    }


    @Override
    public void getStepNames(Messages.StepNamesRequest request, StreamObserver<Messages.StepNamesResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepNamesRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepNamesRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepNamesResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void cacheFile(Messages.CacheFileRequest request, StreamObserver<Messages.Empty> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.CacheFileRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setCacheFileRequest(request);
        processor.process(builder.build());
        responseObserver.onNext(Messages.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepPositions(Messages.StepPositionsRequest request, StreamObserver<Messages.StepPositionsResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepPositionsRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepPositionsRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepPositionsResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getImplementationFiles(Messages.Empty request, StreamObserver<Messages.ImplementationFileListResponse> responseObserver) {
        Iterable<String> allImplFiles = FileHelper.getAllImplementationFiles();
        responseObserver.onNext(Messages.ImplementationFileListResponse.newBuilder().addAllImplementationFilePaths(allImplFiles).build());
        responseObserver.onCompleted();
    }

    @Override
    public void implementStub(Messages.StubImplementationCodeRequest request, StreamObserver<Messages.FileDiff> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StubImplementationCodeRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStubImplementationCodeRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getFileDiff());
        responseObserver.onCompleted();
    }

    @Override
    public void validateStep(Messages.StepValidateRequest request, StreamObserver<Messages.StepValidateResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepValidateRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepValidateRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepValidateResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void refactor(Messages.RefactorRequest request, StreamObserver<Messages.RefactorResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.RefactorRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setRefactorRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getRefactorResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepName(Messages.StepNameRequest request, StreamObserver<Messages.StepNameResponse> responseObserver) {
        IMessageProcessor processor = messageProcessorFactory.getProcessor(Messages.Message.MessageType.StepNameRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepNameRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepNameResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getGlobPatterns(Messages.Empty request, StreamObserver<Messages.ImplementationFileGlobPatternResponse> responseObserver) {
        List<String> patterns = FileHelper.getStepImplDirs().stream().map(dir -> dir + "/**/*.java").collect(Collectors.toList());
        responseObserver.onNext(Messages.ImplementationFileGlobPatternResponse.newBuilder().addAllGlobPatterns(patterns).build());
        responseObserver.onCompleted();
    }

    @Override
    public void kill(Messages.KillProcessRequest request, StreamObserver<Messages.Empty> responseObserver) {
        responseObserver.onNext(Messages.Empty.newBuilder().build());
        responseObserver.onCompleted();
        server.shutdownNow();
    }
}
