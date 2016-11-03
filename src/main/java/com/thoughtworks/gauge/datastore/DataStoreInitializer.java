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

package com.thoughtworks.gauge.datastore;


import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Messages;
import gauge.messages.Spec;

public class DataStoreInitializer implements IMessageProcessor {
    public Messages.Message process(Messages.Message message) {
        switch (message.getMessageType()) { // SUPPRESS CHECKSTYLE
            case SuiteDataStoreInit:
                DataStoreFactory.getSuiteDataStore().clear();
                break;
            case SpecDataStoreInit:
                DataStoreFactory.getSpecDataStore().clear();
                break;
            case ScenarioDataStoreInit:
                DataStoreFactory.getScenarioDataStore().clear();
                break;
        }
        return createMessageWithExecutionStatusResponse(message, Spec.ProtoExecutionResult.newBuilder().setExecutionTime(0).setFailed(false).build());
    }

    private Messages.Message createMessageWithExecutionStatusResponse(Messages.Message receivedMessage, Spec.ProtoExecutionResult result) {
        return Messages.Message.newBuilder()
                .setMessageId(receivedMessage.getMessageId())
                .setMessageType(Messages.Message.MessageType.ExecutionStatusResponse)
                .setExecutionStatusResponse(Messages.ExecutionStatusResponse.newBuilder().setExecutionResult(result).build())
                .build();
    }
}
