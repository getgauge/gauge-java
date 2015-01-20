package com.thoughtworks.gauge;


import gauge.messages.Messages;
import gauge.messages.Spec;

public class DataStoreInitializer implements IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        switch (message.getMessageType()) {
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
