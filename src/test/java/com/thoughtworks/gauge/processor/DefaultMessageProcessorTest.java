/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.processor;

import gauge.messages.Messages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultMessageProcessorTest {

    @Test
    public void shouldProcessMessage() {
        Messages.Message request = Messages.Message.newBuilder().setMessageId(1L).setMessageType(Messages.Message.MessageType.ExecutionStatusResponse).build();
        Messages.Message response = new DefaultMessageProcessor().process(request);

        assertEquals(response.getMessageId(), 1L);
        assertEquals(response.getMessageType(), Messages.Message.MessageType.ExecutionStatusResponse);
        assertFalse(response.getExecutionStatusResponse().getExecutionResult().getFailed());
    }
}