package com.thoughtworks.gauge;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StepValueExtractorTest {

    @Test
    public void shouldExtractNoParameters() {
        String stepText = "A step with no parameters";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValue(stepText);
        assertEquals("A step with no parameters", stepValue.getValue());
        assertEquals(0, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractNoParametersWithTable() {
        String stepText = "A step with no parameters";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValueWithTable(stepText);
        assertEquals("A step with no parameters {}", stepValue.getValue());
        assertEquals(1, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractParametersInQuotes() {
        String stepText = "A step with \"parm0\" and \"param1\"";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValue(stepText);
        assertEquals("A step with {} and {}", stepValue.getValue());
        assertEquals(2, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractParameterInBracket() {
        String stepText = "A step with <parm0> and <param1>";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValue(stepText);
        assertEquals("A step with {} and {}", stepValue.getValue());
        assertEquals(2, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractParamterWhenStepAndParamterHasEscapedQuotes() {
        String stepText = "A step with \\\" \"param0\" and \"param with \\\" quote\"";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValue(stepText);
        assertEquals("A step with \" {} and {}", stepValue.getValue());
        assertEquals(2, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractParamterWhenAngularBracketParamHasEscapedQuotes() {
        String stepText = "A step with \\\" <param0> and <param with \\\" quote>";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValue(stepText);
        assertEquals("A step with \" {} and {}", stepValue.getValue());
        assertEquals(2, stepValue.getParamCount());
    }

    @Test
    public void shouldExtractWithTable() {
        String stepText = "A step with \\\" \"param0\" and \"param with \\\" quote\"";
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getValueWithTable(stepText);
        assertEquals("A step with \" {} and {} {}", stepValue.getValue());
        assertEquals(3, stepValue.getParamCount());
    }

}
