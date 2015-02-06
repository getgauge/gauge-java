// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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
