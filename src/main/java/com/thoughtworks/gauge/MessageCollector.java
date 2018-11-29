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
