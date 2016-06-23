package com.thoughtworks.gauge.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import gauge.messages.Messages;
import gauge.messages.Messages.Message;
import gauge.messages.Messages.Message.Builder;
import gauge.messages.Messages.Message.MessageType;
import gauge.messages.Messages.StepValidateRequest;
import gauge.messages.Messages.StepValidateResponse.ErrorType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.thoughtworks.gauge.registry.StepRegistry;


@RunWith(PowerMockRunner.class)
@PrepareForTest(StepRegistry.class)
public class ValidateStepProcessorTest {

    private static final String STEP_TEXT = "stepText";

    private Message message;
    
    private ValidateStepProcessor stepProcessor;

    @Before
    public void setupMessage(){
        Builder messageBuilder=Messages.Message.newBuilder().setMessageType(MessageType.StepValidateRequest).setMessageId(1l);
        StepValidateRequest stepValidationRequest = StepValidateRequest.newBuilder().setStepText(STEP_TEXT).setNumberOfParameters(1).build();
        messageBuilder.setStepValidateRequest(stepValidationRequest);
        this.message=messageBuilder.build();
    }
    
    @Before
    public void setupStepProcessor(){
        stepProcessor = new ValidateStepProcessor();
    }
    
    @Test
    public void shouldFailIfStepIsNotFound(){
        mockStepRegistry(new ArrayList<Method>());
        
        Message outputMessage=stepProcessor.process(message);
        
        assertEquals(ErrorType.STEP_IMPLEMENTATION_NOT_FOUND,outputMessage.getStepValidateResponse().getErrorType());
        assertFalse(outputMessage.getStepValidateResponse().getIsValid());
    }
    
    @Test
    public void shouldNotFailIfStepIsFound(){
        mockStepRegistry(Arrays.asList(anyMethod()));
        
        Message outputMessage=stepProcessor.process(message);

        assertTrue(outputMessage.getStepValidateResponse().getIsValid());
    }
    
    @Test
    public void shouldFailIfStepIsDefinedTwice(){
        mockStepRegistry(Arrays.asList(anyMethod(),anyMethod()));
        
        Message outputMessage=stepProcessor.process(message);
        
        assertEquals(ErrorType.DUPLICATE_STEP_IMPLEMENTATION,outputMessage.getStepValidateResponse().getErrorType());
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

    private void mockStepRegistry(List<Method> methods){
        mockStatic(StepRegistry.class);
        when(StepRegistry.getAll(STEP_TEXT)).thenReturn(methods);
    }
}
