// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

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
        StepRegistryEntry entry = registry.get(message.getStepNameRequest().getStepValue());
        List<String> aliases = entry.getAliases();
        String stepText = entry.getStepText();
        String fileName = entry.getFileName();

        Range range = entry.getSpan();

        Spec.Span.Builder spanBuilder = Spec.Span.newBuilder()
                .setStart(range.begin.line)
                .setStartChar(range.begin.column)
                .setEnd(range.end.line)
                .setEndChar(range.end.column);

        boolean hasAlias = entry.getHasAlias(), isStepPresent = false;

        if (!hasAlias) {
            aliases.add(stepText);
        }

        if (aliases.size() >= 1) {
            isStepPresent = true;
        }

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNameResponse)
                .setStepNameResponse(Messages.StepNameResponse.newBuilder()
                        .addAllStepName(aliases)
                        .setIsStepPresent(isStepPresent)
                        .setHasAlias(hasAlias)
                        .setFileName(fileName)
                        .setSpan(spanBuilder).build())
                .build();
    }
}
