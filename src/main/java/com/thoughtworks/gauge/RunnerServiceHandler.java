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
import com.thoughtworks.gauge.execution.ExecutorPool;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Messages;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.RunnerGrpc.RunnerImplBase;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class RunnerServiceHandler extends RunnerImplBase {
    private MessageProcessorFactory messageProcessorFactory;
    private boolean multithreading;
    private Server server;
    private ExecutorPool pool;

    public RunnerServiceHandler(MessageProcessorFactory messageProcessorFactory, boolean multithreading, int pool) {
        this.messageProcessorFactory = messageProcessorFactory;
        this.multithreading = multithreading;
        this.pool = new ExecutorPool(pool);
    }

    public void addServer(Server lspServer) {
        this.server = lspServer;
    }

    @Override
    public void initializeSuiteDataStore(Messages.SuiteDataStoreInitRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            int stream = getStream(request.getStream());
            pool.execute(stream, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.SuiteDataStoreInit);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setMessageType(MessageType.SuiteDataStoreInit);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.SuiteDataStoreInit, e.toString()));
            responseObserver.onError(e);
        }

    }

    @Override
    public void startExecution(Messages.ExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ExecutionStarting);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setExecutionStartingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ExecutionStarting, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void initializeSpecDataStore(Messages.SpecDataStoreInitRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.SpecDataStoreInit);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setMessageType(MessageType.SpecDataStoreInit);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.SpecDataStoreInit, e.toString()));
            responseObserver.onError(e);
        }

    }

    @Override
    public void startSpecExecution(Messages.SpecExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.SpecExecutionStarting);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setSpecExecutionStartingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.SpecExecutionStarting, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void initializeScenarioDataStore(Messages.ScenarioDataStoreInitRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ScenarioDataStoreInit);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setMessageType(MessageType.ScenarioDataStoreInit);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ScenarioDataStoreInit, e.toString()));
            responseObserver.onError(e);
        }

    }

    @Override
    public void startScenarioExecution(Messages.ScenarioExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ScenarioExecutionStarting);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setScenarioExecutionStartingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ScenarioExecutionStarting, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void startStepExecution(Messages.StepExecutionStartingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepExecutionStarting);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepExecutionStartingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepExecutionEnding, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void executeStep(Messages.ExecuteStepRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ExecuteStep);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setExecuteStepRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ExecuteStep, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void finishStepExecution(Messages.StepExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepExecutionEnding);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepExecutionEndingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepExecutionEnding, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void finishScenarioExecution(Messages.ScenarioExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ScenarioExecutionEnding);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setScenarioExecutionEndingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ScenarioExecutionEnding, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void finishSpecExecution(Messages.SpecExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.SpecExecutionEnding);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setSpecExecutionEndingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.SpecExecutionEnding, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void finishExecution(Messages.ExecutionEndingRequest request, StreamObserver<Messages.ExecutionStatusResponse> responseObserver) {
        try {
            pool.execute(getStream(request.getStream()), () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.ExecutionEnding);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setExecutionEndingRequest(request);
                Messages.Message message = processor.process(builder.build());
                responseObserver.onNext(message.getExecutionStatusResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ExecutionEnding, e.toString()));
            responseObserver.onError(e);
        }
    }


    @Override
    public void getStepNames(Messages.StepNamesRequest request, StreamObserver<Messages.StepNamesResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepNamesRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepNamesRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getStepNamesResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepNameRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void cacheFile(Messages.CacheFileRequest request, StreamObserver<Messages.Empty> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.CacheFileRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setCacheFileRequest(request);
                processor.process(builder.build());
                responseObserver.onNext(Messages.Empty.newBuilder().build());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.CacheFileRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void getStepPositions(Messages.StepPositionsRequest request, StreamObserver<Messages.StepPositionsResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepPositionsRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepPositionsRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getStepPositionsResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepPositionsRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void getImplementationFiles(Messages.Empty request, StreamObserver<Messages.ImplementationFileListResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                Iterable<String> allImplFiles = FileHelper.getAllImplementationFiles();
                responseObserver.onNext(Messages.ImplementationFileListResponse.newBuilder().addAllImplementationFilePaths(allImplFiles).build());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ImplementationFileListRequest, e.toString()));
            responseObserver.onError(e);
        }

    }

    @Override
    public void implementStub(Messages.StubImplementationCodeRequest request, StreamObserver<Messages.FileDiff> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StubImplementationCodeRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStubImplementationCodeRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getFileDiff());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StubImplementationCodeRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void validateStep(Messages.StepValidateRequest request, StreamObserver<Messages.StepValidateResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepValidateRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepValidateRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getStepValidateResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepValidateRequest, e.toString()));
            responseObserver.onError(e);
        }

    }

    @Override
    public void refactor(Messages.RefactorRequest request, StreamObserver<Messages.RefactorResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.RefactorRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setRefactorRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getRefactorResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.RefactorRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void getStepName(Messages.StepNameRequest request, StreamObserver<Messages.StepNameResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                IMessageProcessor processor = messageProcessorFactory.getProcessor(MessageType.StepNameRequest);
                Messages.Message.Builder builder = Messages.Message.newBuilder();
                builder.setStepNameRequest(request);
                Messages.Message process = processor.process(builder.build());
                responseObserver.onNext(process.getStepNameResponse());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.StepNameRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void getGlobPatterns(Messages.Empty request, StreamObserver<Messages.ImplementationFileGlobPatternResponse> responseObserver) {
        try {
            pool.execute(1, () -> {
                List<String> patterns = FileHelper.getStepImplDirs().stream().map(dir -> dir + "/**/*.java").collect(Collectors.toList());
                responseObserver.onNext(Messages.ImplementationFileGlobPatternResponse.newBuilder().addAllGlobPatterns(patterns).build());
                responseObserver.onCompleted();
            });
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.ImplementationFileGlobPatternRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    @Override
    public void kill(Messages.KillProcessRequest request, StreamObserver<Messages.Empty> responseObserver) {
        try {
            responseObserver.onNext(Messages.Empty.newBuilder().build());
            responseObserver.onCompleted();
            pool.stopAfterCompletion();
            server.shutdownNow();
        } catch (Throwable e) {
            Logger.error(String.format("Failed to process %s.\nReason: %s", MessageType.KillProcessRequest, e.toString()));
            responseObserver.onError(e);
        }
    }

    private int getStream(int stream) {
        if (!multithreading) {
            return 1;
        }
        return Math.max(stream, 1);
    }


}
