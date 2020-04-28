/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.thoughtworks.gauge.Gauge.getMessages;

public class MessageCollector {
    public Spec.ProtoExecutionResult addPendingMessagesTo(Spec.ProtoExecutionResult result) {
        List<String> messages = getPendingMessages();
        return addPendingMessages(result, messages);
    }

    Spec.ProtoExecutionResult addPendingMessages(Spec.ProtoExecutionResult result, List<String> messages) {
        Spec.ProtoExecutionResult.Builder builder = Spec.ProtoExecutionResult.newBuilder(result);
        messages.stream().filter(Objects::nonNull).forEach(builder::addMessage);
        return builder.build();
    }

    private static List<String> getPendingMessages() {
        List<String> pendingMessages = new ArrayList<>(getMessages());
        clear();
        return pendingMessages;
    }

    private static void clear() {
        getMessages().clear();
    }
}
