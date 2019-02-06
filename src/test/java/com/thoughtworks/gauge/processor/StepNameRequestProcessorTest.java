package com.thoughtworks.gauge.processor;

import com.github.javaparser.Range;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StepNameRequestProcessorTest {

    private static final String STEP_TEXT = "stepText \\ text";

    private String anyString = "stepText \\ text";
    private String anyOtherString = "stepText \\ name";
    private Range range = Range.range(1,5,4,5);
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

        ArrayList<String> stringSet = Lists.newArrayList(anyString);
        StepRegistry registry = mock(StepRegistry.class);
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        when(registry.contains(STEP_TEXT)).thenReturn(true);
        when(registry.get(STEP_TEXT)).thenReturn(entry);
        when(entry.getSpan()).thenReturn(range);
        when(entry.getFileName()).thenReturn("foo");
        when(entry.getHasAlias()).thenReturn(false);
        when(entry.getAliases()).thenReturn(stringSet);
        when(entry.getStepText()).thenReturn(anyString);
        StepNameRequestProcessor stepNameRequestProcessor = new StepNameRequestProcessor(registry);
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertFalse(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }

    @Test
    public void shouldHaveTheStepAlias() {
        ArrayList<String> stringSet = Lists.newArrayList(anyString, anyOtherString);

        StepRegistry registry = mock(StepRegistry.class);
        StepRegistryEntry entry = mock(StepRegistryEntry.class);
        when(registry.contains(STEP_TEXT)).thenReturn(true);
        when(registry.get(STEP_TEXT)).thenReturn(entry);
        when(entry.getSpan()).thenReturn(range);
        when(entry.getFileName()).thenReturn("foo");
        when(registry.getAllAliasAnnotationTextsFor(STEP_TEXT)).thenReturn(stringSet);
        when(entry.getStepText()).thenReturn(anyString);

        StepNameRequestProcessor stepNameRequestProcessor = new StepNameRequestProcessor(registry);
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertTrue(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }
}
