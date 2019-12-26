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

import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Lsp;
import gauge.messages.Messages;
import gauge.messages.lspServiceGrpc;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class LspServer extends lspServiceGrpc.lspServiceImplBase {
    private MessageDispatcher messageDispatcher;
    private Server server;

    public LspServer(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    public void addServer(Server lspServer) {
        this.server = lspServer;
    }

    @Override
    public void getStepNames(Messages.StepNamesRequest request, StreamObserver<Messages.StepNamesResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepNamesRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepNamesRequest(request);

        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepNamesResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void cacheFile(Messages.CacheFileRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.CacheFileRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setCacheFileRequest(request);
        processor.process(builder.build());
        responseObserver.onNext(Lsp.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepPositions(Messages.StepPositionsRequest request, StreamObserver<Messages.StepPositionsResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepPositionsRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepPositionsRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepPositionsResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getImplementationFiles(Lsp.Empty request, StreamObserver<Messages.ImplementationFileListResponse> responseObserver) {
        Iterable<String> allImplFiles = FileHelper.getAllImplementationFiles();
        responseObserver.onNext(Messages.ImplementationFileListResponse.newBuilder().addAllImplementationFilePaths(allImplFiles).build());
        responseObserver.onCompleted();
    }

    @Override
    public void implementStub(Messages.StubImplementationCodeRequest request, StreamObserver<Messages.FileDiff> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StubImplementationCodeRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStubImplementationCodeRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getFileDiff());
        responseObserver.onCompleted();
    }

    @Override
    public void validateStep(Messages.StepValidateRequest request, StreamObserver<Messages.StepValidateResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepValidateRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepValidateRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepValidateResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void refactor(Messages.RefactorRequest request, StreamObserver<Messages.RefactorResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.RefactorRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setRefactorRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getRefactorResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepName(Messages.StepNameRequest request, StreamObserver<Messages.StepNameResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepNameRequest);
        Messages.Message.Builder builder = Messages.Message.newBuilder();
        builder.setStepNameRequest(request);
        Messages.Message process = processor.process(builder.build());
        responseObserver.onNext(process.getStepNameResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getGlobPatterns(Lsp.Empty request, StreamObserver<Messages.ImplementationFileGlobPatternResponse> responseObserver) {
        List<String> patterns = FileHelper.getStepImplDirs().stream().map(dir -> dir + "/**/*.java").collect(Collectors.toList());
        responseObserver.onNext(Messages.ImplementationFileGlobPatternResponse.newBuilder().addAllGlobPatterns(patterns).build());
        responseObserver.onCompleted();
    }

    @Override
    public void killProcess(Messages.KillProcessRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        responseObserver.onNext(Lsp.Empty.newBuilder().build());
        responseObserver.onCompleted();
        server.shutdownNow();
    }
}
