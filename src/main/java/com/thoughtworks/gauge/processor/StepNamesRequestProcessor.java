/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;


import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

import java.util.List;

public class StepNamesRequestProcessor implements IMessageProcessor {
    private final StepRegistry registry;

    public StepNamesRequestProcessor(StepRegistry registry) {
        this.registry = registry;
    }

    public Messages.Message process(Messages.Message receivedMessage) {
        List<String> stepTexts = registry.getAllStepAnnotationTexts();

        return Messages.Message.newBuilder()
                .setMessageId(receivedMessage.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNamesResponse)
                .setStepNamesResponse(Messages.StepNamesResponse.newBuilder().addAllSteps(stepTexts).build())
                .build();
    }
}
