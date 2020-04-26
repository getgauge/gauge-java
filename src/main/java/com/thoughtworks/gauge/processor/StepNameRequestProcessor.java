/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import com.github.javaparser.Range;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.util.List;

public class StepNameRequestProcessor implements IMessageProcessor {
    private final StepRegistry registry;

    public StepNameRequestProcessor(StepRegistry registry) {
        this.registry = registry;
    }

    public Messages.Message process(Messages.Message message) {
        boolean isStepPresent = registry.contains(message.getStepNameRequest().getStepValue());
        if (!isStepPresent) {
            return Messages.Message.newBuilder()
                    .setMessageId(message.getMessageId())
                    .setMessageType(Messages.Message.MessageType.StepNameResponse)
                    .setStepNameResponse(Messages.StepNameResponse.newBuilder().setIsStepPresent(false).build())
                    .build();
        }
        StepRegistryEntry entry = registry.get(message.getStepNameRequest().getStepValue());

        if (entry.getIsExternal()) {
            return Messages.Message.newBuilder()
                    .setMessageId(message.getMessageId())
                    .setMessageType(Messages.Message.MessageType.StepNameResponse)
                    .setStepNameResponse(Messages.StepNameResponse.newBuilder()
                            .setIsStepPresent(true)
                            .setIsExternal(true))
                    .build();
        }
        List<String> stepTexts = entry.getAliases();
        Range range = entry.getSpan();
        String stepText = entry.getStepText();
        String fileName = entry.getFileName();
        boolean hasAlias = entry.getHasAlias();
        if (!hasAlias) {
            stepTexts.add(stepText);
        }
        Spec.Span.Builder spanBuilder = Spec.Span.newBuilder()
                .setStart(range.begin.line)
                .setStartChar(range.begin.column)
                .setEnd(range.end.line)
                .setEndChar(range.end.column);

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNameResponse)
                .setStepNameResponse(Messages.StepNameResponse.newBuilder()
                        .addAllStepName(stepTexts)
                        .setIsStepPresent(true)
                        .setHasAlias(hasAlias)
                        .setFileName(fileName)
                        .setSpan(spanBuilder).build())
                .build();
    }
}
