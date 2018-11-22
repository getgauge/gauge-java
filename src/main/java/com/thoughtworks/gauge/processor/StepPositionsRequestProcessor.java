package com.thoughtworks.gauge.processor;

import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

import java.util.List;

public class StepPositionsRequestProcessor implements IMessageProcessor {
    private StepRegistry stepRegistry;

    public StepPositionsRequestProcessor(StepRegistry stepRegistry) {
        this.stepRegistry = stepRegistry;
    }

    @Override
    public Messages.Message process(Messages.Message message) {
        List<Messages.StepPositionsResponse.StepPosition> positions = stepRegistry.getStepPositions(message.getStepPositionsRequest().getFilePath());

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setStepPositionsResponse(Messages.StepPositionsResponse.newBuilder().addAllStepPositions(positions))
                .setMessageType(Messages.Message.MessageType.StepPositionsResponse)
                .build();
    }
}
