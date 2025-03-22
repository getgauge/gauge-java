/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.thoughtworks.gauge.connection.MessageProcessorFactory;
import com.thoughtworks.gauge.execution.ExecutorPool;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Messages;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.RunnerGrpc.RunnerImplBase;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class RunnerServiceHandler extends RunnerImplBase {
    private static final Logger LOGGER = LogManager.getLogger(RunnerServiceHandler.class);
    private static final String ERROR_LOG_FORMAT = "Failed to process {}.\nReason: {}";

    private final MessageProcessorFactory messageProcessorFactory;
    private final boolean multithreading;
    private Server server;
    private final ExecutorPool pool;

    public RunnerServiceHandler(MessageProcessorFactory messageProcessorFactory, boolean multithreading, int poolSize) {
        this.messageProcessorFactory = messageProcessorFactory;
        this.multithreading = multithreading;
        this.pool = new ExecutorPool(poolSize);
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.SuiteDataStoreInit, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ExecutionStarting, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.SpecDataStoreInit, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.SpecExecutionStarting, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ScenarioDataStoreInit, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ScenarioExecutionStarting, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepExecutionEnding, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ExecuteStep, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepExecutionEnding, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ScenarioExecutionEnding, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.SpecExecutionEnding, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ExecutionEnding, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepNameRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.CacheFileRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepPositionsRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ImplementationFileListRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StubImplementationCodeRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepValidateRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.RefactorRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.StepNameRequest, e.toString());
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
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.ImplementationFileGlobPatternRequest, e.toString());
            responseObserver.onError(e);
        }
    }

    @Override
    public void kill(Messages.KillProcessRequest request, StreamObserver<Messages.Empty> responseObserver) {
        try {
            LOGGER.debug("Killing Java runner...");
            responseObserver.onNext(Messages.Empty.newBuilder().build());
            responseObserver.onCompleted();
            LOGGER.debug("Stopping execution pool...");
            pool.stopAfterCompletion();
            LOGGER.debug("Shutting down grpc server...");
            server.shutdownNow();
        } catch (Throwable e) {
            LOGGER.error(ERROR_LOG_FORMAT, MessageType.KillProcessRequest, e.toString());
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
