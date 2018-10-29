package com.thoughtworks.gauge;

import com.thoughtworks.gauge.connection.MessageDispatcher;
import com.thoughtworks.gauge.scan.StaticScanner;
import gauge.messages.Messages;
import io.grpc.Context;

public class LspServer extends LspServiceGrpc.LspServiceImplBase {
    private StaticScanner staticScanner;
    private MessageDispatcher messageDispatcher;

    public LspServer(StaticScanner staticScanner, MessageDispatcher messageDispatcher) {

        this.staticScanner = staticScanner;
        this.messageDispatcher = messageDispatcher;
    }

    public Messages.StepNameResponse getStepNameResponse(Messages.Message request, Context context) {
        messageDispatcher.getProcessor(Messages.Message.MessageType.StepNameRequest).process(request);
        return Messages.Message.newBuilder().getStepNameResponse();
    }
}
