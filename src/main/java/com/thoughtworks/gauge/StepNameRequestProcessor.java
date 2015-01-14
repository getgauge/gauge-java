package com.thoughtworks.gauge;

import main.Messages;

import java.util.List;

public class StepNameRequestProcessor implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        List<String> stepAnnotations = StepRegistry.getStepAnnotationFor(StepRegistry.getAliasStepTexts(message.getStepNameRequest().getStepValue()));
        boolean hasAlias = false, isStepPresent = false;
        if (stepAnnotations.size() > 1) hasAlias = true;
        if (stepAnnotations.size() >= 1) isStepPresent = true;
        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNameResponse)
                .setStepNameResponse(Messages.GetStepNameResponse.newBuilder().addAllStepName(stepAnnotations).setIsStepPresent(isStepPresent).setHasAlias(hasAlias).build())
                .build();
    }
}
