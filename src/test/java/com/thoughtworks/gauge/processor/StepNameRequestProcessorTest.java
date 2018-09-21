package com.thoughtworks.gauge.processor;

import com.google.common.collect.Sets;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepNameRequestProcessorTest {

    private static final String STEP_TEXT = "stepText \\ text";

    private Messages.Message message;

    @Before
    public void setupMessage() {
        Messages.Message.Builder messageBuilder = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepNameRequest).setMessageId(1l);
        Messages.StepNameRequest stepNameRequest = Messages.StepNameRequest.newBuilder().setStepValue(STEP_TEXT).build();
        messageBuilder.setStepNameRequest(stepNameRequest);
        this.message = messageBuilder.build();
    }

    @Test
    public void shouldHaveTheStep() {
        StepRegistry registry = mockStepRegistry(Sets.newHashSet(anyString));
        StepNameRequestProcessor stepNameRequestProcessor = new StepNameRequestProcessor(new ClassInstanceManager(), registry);
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertFalse(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }

    @Test
    public void shouldHaveTheStepAlias() {
        String anyOtherString = "stepText \\ name";
        StepRegistry registry = mockStepRegistry(Sets.newHashSet(anyString, anyOtherString));
        StepNameRequestProcessor stepNameRequestProcessor = new StepNameRequestProcessor(new ClassInstanceManager(), registry);
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertTrue(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }

    private String anyString = "stepText \\ text";

    private StepRegistry mockStepRegistry(Set<String> stringSet) {
        StepRegistry registry = mock(StepRegistry.class);
        when(registry.getAllAliasAnnotationTextsFor(STEP_TEXT)).thenReturn(stringSet);
        return registry;
    }
}
