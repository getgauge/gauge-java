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

import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;

import java.util.Set;

public class StepNameRequestProcessor implements IMessageProcessor {
    private final StepRegistry registry;

    public StepNameRequestProcessor(ClassInstanceManager instanceManager, StepRegistry registry) {
        this.registry = registry;
    }

    public Messages.Message process(Messages.Message message) {
        Set<String> stepAnnotations = registry.getAllAliasAnnotationTextsFor(message.getStepNameRequest().getStepValue());
        boolean hasAlias = false, isStepPresent = false;

        if (stepAnnotations.size() > 1) {
            hasAlias = true;
        }

        if (stepAnnotations.size() >= 1) {
            isStepPresent = true;
        }

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setMessageType(Messages.Message.MessageType.StepNameResponse)
                .setStepNameResponse(Messages.StepNameResponse.newBuilder().addAllStepName(stepAnnotations).setIsStepPresent(isStepPresent).setHasAlias(hasAlias).build())
                .build();
    }
}
