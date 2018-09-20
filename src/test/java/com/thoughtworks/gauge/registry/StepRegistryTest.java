// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.TestStepImplClass;
import junit.framework.TestCase;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


public class StepRegistryTest extends TestCase {

    private StepValue stepValue1 = new StepValue("hello world", "hello world");
    private StepValue stepValue2 = new StepValue("hello world {}", "hello world <param0>");
    private StepValue stepValue3 = new StepValue("a step with {} and {}", "a step with <param0> and <param1>");
    private StepValue aliasStep1 = new StepValue("first step name with name <a>", "first step name with name {}");
    private StepValue aliasStep2 = new StepValue("second step name with <b>", "second step name with {}");

    private Method method1 = TestStepImplClass.class.getMethods()[0];
    private Method method2 = TestStepImplClass.class.getMethods()[1];
    private Method method3 = TestStepImplClass.class.getMethods()[2];
    private Method aliasMethod = TestStepImplClass.class.getMethods()[3];

    private StepRegistry stepRegistry;

    protected void setUp() throws Exception {
        this.stepRegistry = new StepRegistry();
        this.stepRegistry.addStepImplementation(stepValue1, method1);
        this.stepRegistry.addStepImplementation(stepValue2, method2);
        this.stepRegistry.addStepImplementation(stepValue3, method3);
        this.stepRegistry.addStepImplementation(aliasStep1, aliasMethod);
        this.stepRegistry.addStepImplementation(aliasStep2, aliasMethod);
    }

    public void testAddingStepImplementationToRegistry() throws Exception {
        assertTrue(stepRegistry.contains(stepValue1.getStepText()));
        assertTrue(stepRegistry.contains(stepValue2.getStepText()));
        assertTrue(stepRegistry.contains(stepValue3.getStepText()));
        assertTrue(stepRegistry.contains(aliasStep1.getStepText()));
        assertTrue(stepRegistry.contains(aliasStep2.getStepText()));

        assertEquals(method1, stepRegistry.get(stepValue1.getStepText()));
        assertEquals(method2, stepRegistry.get(stepValue2.getStepText()));
        assertEquals(method3, stepRegistry.get(stepValue3.getStepText()));
        assertNull(stepRegistry.get("unknown"));
    }

    public void testGetFileNameFromStepRegistry() throws Exception {
        String expected = String.format("com%sthoughtworks%sgauge%sTestStepImplClass.java", File.separator, File.separator, File.separator);
        assertEquals(expected, stepRegistry.getFileName(stepValue1.getStepText()));
        assertEquals("", stepRegistry.getFileName("unknown"));
    }

    public void testGetAllStepTexts() throws Exception {
        List<String> stepTexts = stepRegistry.getAllStepAnnotationTexts();
        assertEquals(5, stepTexts.size());
        assertTrue(stepTexts.contains(stepValue1.getStepText()));
        assertTrue(stepTexts.contains(stepValue2.getStepAnnotationText()));
        assertTrue(stepTexts.contains(stepValue3.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep2.getStepAnnotationText()));
    }

    public void testGetStepAnnotationFor() throws Exception {
        assertEquals(stepValue1.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
        assertEquals(stepValue2.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue2.getStepText()));
        assertEquals(stepValue3.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue3.getStepText()));
        assertEquals(aliasStep1.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(aliasStep1.getStepText()));
        assertEquals(aliasStep2.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(aliasStep2.getStepText()));
        assertEquals("", stepRegistry.getStepAnnotationFor("unknown"));
    }

    public void testGetAliasStepTexts() throws Exception {
        assertEquals(1, stepRegistry.getAllAliasAnnotationTextsFor(stepValue1.getStepText()).size());
        assertEquals(1, stepRegistry.getAllAliasAnnotationTextsFor(stepValue2.getStepText()).size());
        assertEquals(1, stepRegistry.getAllAliasAnnotationTextsFor(stepValue3.getStepText()).size());

        Set<String> aliasStepTexts = stepRegistry.getAllAliasAnnotationTextsFor(aliasStep1.getStepText());
        assertEquals(2, aliasStepTexts.size());
        assertTrue(aliasStepTexts.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(aliasStepTexts.contains(aliasStep2.getStepAnnotationText()));

        Set<String> aliasStepTexts1 = stepRegistry.getAllAliasAnnotationTextsFor(aliasStep2.getStepText());
        assertEquals(2, aliasStepTexts1.size());
        assertTrue(aliasStepTexts1.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(aliasStepTexts1.contains(aliasStep2.getStepAnnotationText()));
    }

    public void testHasAlias() throws Exception {
        assertFalse(stepRegistry.hasAlias(stepValue1.getStepText()));
        assertFalse(stepRegistry.hasAlias(stepValue2.getStepText()));
        assertFalse(stepRegistry.hasAlias(stepValue3.getStepText()));
        assertTrue(stepRegistry.hasAlias(aliasStep1.getStepText()));
        assertTrue(stepRegistry.hasAlias(aliasStep2.getStepText()));
    }

    public void testRemoveEntry() throws Exception {
        stepRegistry.remove(stepValue1.getStepText());
        assertFalse(stepRegistry.contains(stepValue1.getStepText()));
        assertNull(stepRegistry.get(stepValue1.getStepText()));
        assertTrue(stepRegistry.getAll(stepValue1.getStepText()).isEmpty());
        assertEquals("", stepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
        assertFalse(stepRegistry.hasAlias(stepValue1.getStepText()));
    }

    public void testAddedToRawRegistry() {
        stepRegistry.addStepImplementation(stepValue1, method2);

        Set<Method> methods = stepRegistry.getAll(stepValue1.getStepText());

        assertTrue(methods.contains(method1));
        assertTrue(methods.contains(method2));
    }

    protected void tearDown() throws Exception {
        stepRegistry.remove(stepValue1.getStepText());
        stepRegistry.remove(stepValue2.getStepText());
        stepRegistry.remove(stepValue3.getStepText());
        stepRegistry.remove(aliasStep1.getStepText());
        stepRegistry.remove(aliasStep2.getStepText());
    }
}
