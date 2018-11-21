package com.thoughtworks.gauge;

import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Lsp;
import gauge.messages.Messages;
import gauge.messages.lspServiceGrpc;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;

public class LspServer extends lspServiceGrpc.lspServiceImplBase {
    private Server server;
    private MessageDispatcher messageDispatcher;

    LspServer(Server server, MessageDispatcher messageDispatcher) {
        this.server = server;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void getStepNames(Messages.StepNamesRequest request, StreamObserver<Messages.StepNamesResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepNamesRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.StepNamesRequest getStepNamesRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getStepNamesResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void cacheFile(Messages.CacheFileRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.CacheFileRequest);
        processor.process(new Messages.Message() {
            @Override
            public Messages.CacheFileRequest getCacheFileRequest() {
                return request;
            }
        });
        responseObserver.onNext(Lsp.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepPositions(Messages.StepPositionsRequest request, StreamObserver<Messages.StepPositionsResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepPositionsRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.StepPositionsRequest getStepPositionsRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getStepPositionsResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getImplementationFiles(Lsp.Empty request, StreamObserver<Messages.ImplementationFileListResponse> responseObserver) {
        Messages.ImplementationFileListResponse response = new Messages.ImplementationFileListResponse();
        Iterable<String> allImplFiles = FileHelper.getAllImplementationFiles();
        response.toBuilder().addAllImplementationFilePaths(allImplFiles);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void implementStub(Messages.StubImplementationCodeRequest request, StreamObserver<Messages.FileDiff> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StubImplementationCodeRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.StubImplementationCodeRequest getStubImplementationCodeRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getFileDiff());
        responseObserver.onCompleted();
    }

    @Override
    public void validateStep(Messages.StepValidateRequest request, StreamObserver<Messages.StepValidateResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepValidateRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.StepValidateRequest getStepValidateRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getStepValidateResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void refactor(Messages.RefactorRequest request, StreamObserver<Messages.RefactorResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.RefactorRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.RefactorRequest getRefactorRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getRefactorResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getStepName(Messages.StepNameRequest request, StreamObserver<Messages.StepNameResponse> responseObserver) {
        IMessageProcessor processor = messageDispatcher.getProcessor(Messages.Message.MessageType.StepNameRequest);
        Messages.Message process = processor.process(new Messages.Message() {
            @Override
            public Messages.StepNameRequest getStepNameRequest() {
                return request;
            }
        });
        responseObserver.onNext(process.getStepNameResponse());
        responseObserver.onCompleted();
    }

    @Override
    public void getGlobPatterns(Lsp.Empty request, StreamObserver<Messages.ImplementationFileGlobPatternResponse> responseObserver) {
        Messages.ImplementationFileGlobPatternResponse response = new Messages.ImplementationFileGlobPatternResponse();
        String gaugeProjectRoot = System.getenv("GAUGE_PROJECT_ROOT") + "/**/*.java";
        response.toBuilder().addGlobPatterns(gaugeProjectRoot);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void killProcess(Messages.KillProcessRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        server.shutdownNow();
        responseObserver.onNext(Lsp.Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
