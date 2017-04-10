package com.thoughtworks.gauge.processor;

import com.google.common.collect.Sets;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StepRegistry.class)
public class StepNameRequestProcessorTest {

    private static final String STEP_TEXT = "stepText \\ text";

    private Messages.Message message;

    private StepNameRequestProcessor stepNameRequestProcessor;

    @Before
    public void setupMessage() {
        Messages.Message.Builder messageBuilder = Messages.Message.newBuilder().setMessageType(Messages.Message.MessageType.StepNameRequest).setMessageId(1l);
        Messages.StepNameRequest stepNameRequest = Messages.StepNameRequest.newBuilder().setStepValue(STEP_TEXT).build();
        messageBuilder.setStepNameRequest(stepNameRequest);
        this.message = messageBuilder.build();
    }

    @Before
    public void setupStepProcessor() {
        stepNameRequestProcessor = new StepNameRequestProcessor(new ClassInstanceManager());
    }

    @Test
    public void shouldHaveTheStep() {
        mockStepRegistry(Sets.newHashSet(anyString));
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertFalse(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }

    @Test
    public void shouldHaveTheStepAlias() {
        mockStepRegistry(Sets.newHashSet(anyString, anyOtherString));
        Messages.Message outputMessage = stepNameRequestProcessor.process(message);

        assertTrue(outputMessage.getStepNameResponse().getHasAlias());
        assertTrue(outputMessage.getStepNameResponse().getIsStepPresent());
    }

    private String anyString = "stepText \\ text";
    private String anyOtherString = "stepText \\ name";

    private void mockStepRegistry(Set<String> stringSet) {
        mockStatic(StepRegistry.class);
        when(StepRegistry.getAllAliasAnnotationTextsFor(STEP_TEXT)).thenReturn(stringSet);
    }
}
