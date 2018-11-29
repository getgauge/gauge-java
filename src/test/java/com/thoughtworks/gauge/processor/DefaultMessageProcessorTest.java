package com.thoughtworks.gauge.processor;

import gauge.messages.Messages;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class DefaultMessageProcessorTest {

    @Test
    public void shouldProcessMessage() {
        Messages.Message request = Messages.Message.newBuilder().setMessageId(1l).setMessageType(Messages.Message.MessageType.ExecutionStatusResponse).build();
        Messages.Message response = new DefaultMessageProcessor().process(request);

        assertEquals(response.getMessageId(), 1l);
        assertEquals(response.getMessageType(), Messages.Message.MessageType.ExecutionStatusResponse);
        assertFalse(response.getExecutionStatusResponse().getExecutionResult().getFailed());
    }
}