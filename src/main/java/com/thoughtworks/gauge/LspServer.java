package com.thoughtworks.gauge;

import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Lsp;
import gauge.messages.Messages;
import gauge.messages.lspServiceGrpc;
import io.grpc.stub.StreamObserver;

public class LspServer extends lspServiceGrpc.lspServiceImplBase {
    private StaticScanner staticScanner;
    private MessageDispatcher messageDispatcher;

    public LspServer(StaticScanner staticScanner, MessageDispatcher messageDispatcher) {

        this.staticScanner = staticScanner;
        this.messageDispatcher = messageDispatcher;
    }

    @Override
    public void getStepNames(Messages.StepNamesRequest request, StreamObserver<Messages.StepNamesResponse> responseObserver) {
//        messageDispatcher.getProcessor(Messages.Message.MessageType.StepNameRequest).process(request);
//        return Messages.Message.newBuilder().getStepNameResponse();
        super.getStepNames(request, responseObserver);
    }

    @Override
    public void cacheFile(Messages.CacheFileRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        super.cacheFile(request, responseObserver);
    }

    @Override
    public void getStepPositions(Messages.StepPositionsRequest request, StreamObserver<Messages.StepPositionsResponse> responseObserver) {
        super.getStepPositions(request, responseObserver);
    }

    @Override
    public void getImplementationFiles(Lsp.Empty request, StreamObserver<Messages.ImplementationFileListResponse> responseObserver) {
        super.getImplementationFiles(request, responseObserver);
    }

    @Override
    public void implementStub(Messages.StubImplementationCodeRequest request, StreamObserver<Messages.FileDiff> responseObserver) {
        super.implementStub(request, responseObserver);
    }

    @Override
    public void validateStep(Messages.StepValidateRequest request, StreamObserver<Messages.StepValidateResponse> responseObserver) {
        super.validateStep(request, responseObserver);
    }

    @Override
    public void refactor(Messages.RefactorRequest request, StreamObserver<Messages.RefactorResponse> responseObserver) {
        super.refactor(request, responseObserver);
    }

    @Override
    public void getStepName(Messages.StepNameRequest request, StreamObserver<Messages.StepNameResponse> responseObserver) {
        super.getStepName(request, responseObserver);
    }

    @Override
    public void getGlobPatterns(Lsp.Empty request, StreamObserver<Messages.ImplementationFileGlobPatternResponse> responseObserver) {
        super.getGlobPatterns(request, responseObserver);
    }

    @Override
    public void killProcess(Messages.KillProcessRequest request, StreamObserver<Lsp.Empty> responseObserver) {
        super.killProcess(request, responseObserver);
    }
}
