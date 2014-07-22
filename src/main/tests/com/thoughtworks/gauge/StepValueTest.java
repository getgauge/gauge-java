package com.thoughtworks.gauge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class StepValueTest {
    @Test
    public void shouldGetAnnotationTextForSimpleStep() {
        StepValue stepValue = new StepValue("a simple step", "", new ArrayList<String>());
        assertEquals("aSimpleStep", stepValue.getMethodName());
    }

    @Test
    public void shouldGetAnnotationTextForStepWithParams() {
        List<String> params = new ArrayList<String>();
        params.add("name");
        params.add("description");
        StepValue stepValue1 = new StepValue("step with {} and {}", "", params);

        assertEquals("stepWithAnd", stepValue1.getMethodName());
    }

}