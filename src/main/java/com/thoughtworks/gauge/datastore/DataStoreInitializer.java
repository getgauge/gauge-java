/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.datastore;



import com.thoughtworks.gauge.processor.IMessageProcessor;
import gauge.messages.Messages;
import gauge.messages.Spec;

public class DataStoreInitializer implements IMessageProcessor {

    public Messages.Message process(Messages.Message message) {
        switch (message.getMessageType()) { // SUPPRESS CHECKSTYLE
            case SuiteDataStoreInit:
                SuiteDataStore.clear();
                break;
            case SpecDataStoreInit:
                SpecDataStore.clear();
                break;
            case ScenarioDataStoreInit:
                ScenarioDataStore.clear();
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
