package com.thoughtworks.gauge;

import main.Messages;

import java.util.List;

public class StepNamesRequestProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message receivedMessage) {
        List<String> stepTexts = StepRegistry.getAllStepTexts();

        return Messages.Message.newBuilder()
                .setMessageId(receivedMessage.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNamesResponse)
                .setStepNamesResponse(Messages.StepNamesResponse.newBuilder().addAllSteps(stepTexts).build())
                .build();
    }
}
