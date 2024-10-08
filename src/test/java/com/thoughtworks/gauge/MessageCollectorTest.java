/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.google.protobuf.ProtocolStringList;
import gauge.messages.Spec;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MessageCollectorTest {

    @Test
    public void testAddingMessagesToProtoResult() {
        Spec.ProtoExecutionResult executionResult = emptyExecResult();
        String[] messages = {"first message", "second message"};
        Spec.ProtoExecutionResult protoExecutionResult = new MessageCollector().addPendingMessages(executionResult, Arrays.asList(messages));
        ProtocolStringList actualMessageList = protoExecutionResult.getMessageList();
        for (String message : messages) {
            assertTrue(actualMessageList.contains(message));
        }
    }

    @Test
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
