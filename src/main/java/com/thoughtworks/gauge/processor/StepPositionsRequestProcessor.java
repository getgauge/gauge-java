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
