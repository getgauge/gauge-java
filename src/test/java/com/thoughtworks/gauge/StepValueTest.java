package com.thoughtworks.gauge;

import gauge.messages.Spec;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StepValueTest {

    @Test
    public void testFrom(){
        Spec.ProtoStepValue protoStepValue = Spec.ProtoStepValue.newBuilder().setStepValue("step \\ 1").setParameterizedStepValue("").addParameters("").build();
        StepValue stepValue = new StepValue(protoStepValue.getStepValue(), protoStepValue.getParameterizedStepValue(), protoStepValue.getParametersList());
        assertEquals(stepValue, StepValue.from(protoStepValue));
        assertEquals("step \\ 1", stepValue.getStepText());
    }

    @Test
    public void testFromForTab(){
        Spec.ProtoStepValue protoStepValue = Spec.ProtoStepValue.newBuilder().setStepValue("step \t 1").setParameterizedStepValue("").addParameters("").build();
        StepValue stepValue = new StepValue(protoStepValue.getStepValue(), protoStepValue.getParameterizedStepValue(), protoStepValue.getParametersList());

        assertEquals(stepValue, StepValue.from(protoStepValue));
        assertEquals("step \t 1", stepValue.getStepText());
    }

    @Test
    public void testFromForTwoSlash(){
        Spec.ProtoStepValue protoStepValue = Spec.ProtoStepValue.newBuilder().setStepValue("step \\a 1").setParameterizedStepValue("").addParameters("").build();
        StepValue stepValue = new StepValue(protoStepValue.getStepValue(), protoStepValue.getParameterizedStepValue(), protoStepValue.getParametersList());

        assertEquals(stepValue, StepValue.from(protoStepValue));
        assertEquals("step \\a 1", stepValue.getStepText());
    }
}
