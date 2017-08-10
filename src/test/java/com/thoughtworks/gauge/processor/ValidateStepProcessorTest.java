package com.thoughtworks.gauge.processor;

import com.google.common.collect.Sets;
import com.thoughtworks.gauge.ClassInstanceManager;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;
import gauge.messages.Messages;
import gauge.messages.Messages.Message;
import gauge.messages.Messages.Message.Builder;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.Messages.StepValidateRequest;
import gauge.messages.Messages.StepValidateResponse.ErrorType;
import gauge.messages.Spec;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(StepRegistry.class)
public class ValidateStepProcessorTest {

    private static final String STEP_TEXT = "stepText";

    private Message message;

    private ValidateStepProcessor stepProcessor;

    @Before
    public void setupMessage() {
        Builder messageBuilder = Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1l);
        Spec.ProtoStepValue.Builder protoStep  = Spec.ProtoStepValue.newBuilder().setStepValue(STEP_TEXT).setParameterizedStepValue(STEP_TEXT).addAllParameters(new ArrayList<String>());
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText(STEP_TEXT).setNumberOfParameters(1).setStepValue(protoStep).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message = messageBuilder.build();
    }

    @Before
    public void setupStepProcessor() {
        stepProcessor = new ValidateStepProcessor(new ClassInstanceManager());
    }

    @Test
    public void shouldFailIfStepIsNotFoundAndShouldGiveQuickFix() {
        mockStepRegistry(new HashSet<Method>());
        final StringBuilder quickFix = new StringBuilder("\n\nQuick Fix : \n@Step(\"stepText\")\n");
        quickFix.append(String.format("public void implementation(){\n"));
        quickFix.append("}\n");

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, outputMessage.getStepValidateResponse().getErrorType());
        assertEquals(quickFix.toString() , outputMessage.getStepValidateResponse().getQuickFix());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    @Test
    public void shouldNotFailIfStepIsFoundAndShouldNotGiveQuickFix() {
        mockStepRegistry(Sets.newHashSet(anyMethod()));

        Message outputMessage = stepProcessor.process(message);

        assertTrue(outputMessage.getStepValidateResponse().getIsValid());
        assertEquals("", outputMessage.getStepValidateResponse().getQuickFix());
    }

    @Test
    public void shouldFailIfStepIsDefinedTwice() {
        mockStepRegistry(Sets.newHashSet(anyMethod(), anyOtherMethod()));

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.DUPLICATE_STEP_IMPLEMENTATION, outputMessage.getStepValidateResponse().getErrorType());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    private Method anyMethod() {

        try {
            return String.class.getMethod("toString");
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        } catch (SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Method anyOtherMethod() {

        try {
            return String.class.getMethod("hashCode");
        } catch (NoSuchMethodException exception) {
            throw new RuntimeException(exception);
        } catch (SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void mockStepRegistry(Set<Method> methods) {
        mockStatic(StepRegistry.class);
        when(StepRegistry.getAll(STEP_TEXT)).thenReturn(methods);
    }
}
