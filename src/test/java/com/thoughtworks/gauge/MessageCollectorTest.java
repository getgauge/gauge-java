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

import com.google.protobuf.ProtocolStringList;
import gauge.messages.Spec;
import junit.framework.TestCase;

import java.util.Arrays;

public class MessageCollectorTest extends TestCase {

    public void testAddingMessagesToProtoResult() {
        Spec.ProtoExecutionResult executionResult = emptyExecResult();
        String[] messages = {"first message", "second message"};
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessages(executionResult, Arrays.asList(messages));
        ProtocolStringList actualMessageList = protoExecutionResult.getMessageList();
        for (String message : messages) {
            assertTrue(actualMessageList.contains(message));
        }
    }

    public void testAddingNullMessagesToProtoResult() {
        Spec.ProtoExecutionResult executionResult = emptyExecResult();
        String[] messages = {"first message", "second message", null};
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessages(executionResult, Arrays.asList(messages));
        ProtocolStringList actualMessageList = protoExecutionResult.getMessageList();
        assertEquals(2, actualMessageList.size());
        assertFalse(actualMessageList.contains(null));
    }

    private Spec.ProtoExecutionResult emptyExecResult() {
        return Spec.ProtoExecutionResult.newBuilder().setFailed(false).setExecutionTime(0).build();
    }
}