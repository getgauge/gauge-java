package com.thoughtworks.gauge.processor;

import com.google.common.collect.Sets;
import com.thoughtworks.gauge.ClassInstanceManager;
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidateStepProcessorTest {

    private static final String STEP_TEXT = "stepText";

    private Message message;

    @Before
    public void setupMessage() {
        Builder messageBuilder = Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1l);
        Spec.ProtoStepValue.Builder protoStep = Spec.ProtoStepValue.newBuilder().setStepValue(STEP_TEXT).setParameterizedStepValue(STEP_TEXT).addAllParameters(new ArrayList<String>());
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText(STEP_TEXT).setNumberOfParameters(1).setStepValue(protoStep).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message = messageBuilder.build();
    }


    @Test
    public void shouldFailIfStepIsNotFoundAndShouldGiveSuggestion() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.getAll(STEP_TEXT)).thenReturn(new HashSet<>());
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(new ClassInstanceManager(), stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, outputMessage.getStepValidateResponse().getErrorType());
        String suggestion = "\n\t@Step(\"stepText\")\n" + "\tpublic void steptext(){\n\t\t" +
                "throw new UnsupportedOperationException(\"Provide custom implementation\");\n\t}";
        assertEquals(suggestion, outputMessage.getStepValidateResponse().getSuggestion());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    @Test
    public void ShouldGiveSuggestionWithMethodNameimplementation1() {
        Builder messageBuilder = Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1l);
        Spec.ProtoStepValue.Builder protoStep = Spec.ProtoStepValue.newBuilder()
                .setStepValue("<abc> <xyz>")
                .setParameterizedStepValue("<abc> <xyz>")
                .addAllParameters(Arrays.asList("abc", "xyz"));
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText("{} {}").setNumberOfParameters(1).setStepValue(protoStep).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message = messageBuilder.build();
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.getAll(STEP_TEXT)).thenReturn(new HashSet<>());
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(new ClassInstanceManager(), stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND, outputMessage.getStepValidateResponse().getErrorType());
        String suggestion = "\n\t@Step(\"<abc> <xyz>\")\n" + "\tpublic void implementation1(Object arg0, Object arg1){\n\t\t" +
                "throw new UnsupportedOperationException(\"Provide custom implementation\");\n\t}";
        assertEquals(suggestion, outputMessage.getStepValidateResponse().getSuggestion());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    @Test
    public void shouldNotFailIfStepIsFoundAndShouldNotGiveSuggestion() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.getAll(STEP_TEXT)).thenReturn(Sets.newHashSet(anyMethod()));
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(new ClassInstanceManager(), stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertTrue(outputMessage.getStepValidateResponse().getIsValid());
        assertEquals("", outputMessage.getStepValidateResponse().getSuggestion());
    }

    @Test
    public void shouldFailIfStepIsDefinedTwice() {
        StepRegistry stepRegistry = mock(StepRegistry.class);
        when(stepRegistry.getAll(STEP_TEXT)).thenReturn(Sets.newHashSet(anyMethod(), anyOtherMethod()));
        ValidateStepProcessor stepProcessor = new ValidateStepProcessor(new ClassInstanceManager(), stepRegistry);

        Message outputMessage = stepProcessor.process(message);

        assertEquals(ErrorType.DUPLICATE_STEP_IMPLEMENTATION, outputMessage.getStepValidateResponse().getErrorType());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }

    private Method anyMethod() {

        try {
            return String.class.getMethod("toString");
        } catch (NoSuchMethodException | SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Method anyOtherMethod() {

        try {
            return String.class.getMethod("hashCode");
        } catch (NoSuchMethodException | SecurityException exception) {
            throw new RuntimeException(exception);
        }
    }
}
