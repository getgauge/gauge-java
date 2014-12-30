package com.thoughtworks.gauge;

import main.Messages;

public class StepNameRequestProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        String stepAnnotation = StepRegistry.getStepAnnotationFor(message.getStepNameRequest().getStepValue());
        boolean isStepPresent = true;
        if (stepAnnotation == null){
            isStepPresent = false;
            stepAnnotation = "";
        }
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNameResponse)
                .setStepNameResponse(Messages.GetStepNameResponse.newBuilder().setStepName(stepAnnotation).setIsStepPresent(isStepPresent).build())
                .build();
    }
}
