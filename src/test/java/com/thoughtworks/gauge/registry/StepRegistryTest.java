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

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.TestStepImplClass;

public class StepRegistryTest extends TestCase {

    StepValue stepValue1 = new StepValue("hello world", "hello world");
    StepValue stepValue2 = new StepValue("hello world {}", "hello world <param0>");
    StepValue stepValue3 = new StepValue("a step with {} and {}", "a step with <param0> and <param1>");
    StepValue aliasStep1 = new StepValue("first step name with name <a>", "first step name with name {}");
    StepValue aliasStep2 = new StepValue("second step name with <b>", "second step name with {}");

    Method method1 = TestStepImplClass.class.getMethods()[0];
    Method method2 = TestStepImplClass.class.getMethods()[1];
    Method method3 = TestStepImplClass.class.getMethods()[2];
    Method aliasMethod = TestStepImplClass.class.getMethods()[3];

    protected void setUp() throws Exception {
        StepRegistry.addStepImplementation(stepValue1, method1);
        StepRegistry.addStepImplementation(stepValue2, method2);
        StepRegistry.addStepImplementation(stepValue3, method3);
        StepRegistry.addStepImplementation(aliasStep1, aliasMethod);
        StepRegistry.addStepImplementation(aliasStep2, aliasMethod);
    }

    public void testAddingStepImplementationToRegistry() throws Exception {
        assertTrue(StepRegistry.contains(stepValue1.getStepText()));
        assertTrue(StepRegistry.contains(stepValue2.getStepText()));
        assertTrue(StepRegistry.contains(stepValue3.getStepText()));
        assertTrue(StepRegistry.contains(aliasStep1.getStepText()));
        assertTrue(StepRegistry.contains(aliasStep2.getStepText()));

        assertEquals(method1, StepRegistry.get(stepValue1.getStepText()));
        assertEquals(method2, StepRegistry.get(stepValue2.getStepText()));
        assertEquals(method3, StepRegistry.get(stepValue3.getStepText()));
        assertEquals(null, StepRegistry.get("unknown"));
    }

    public void testGetFileNameFromStepRegistry() throws Exception {
        assertEquals(String.format("com%sthoughtworks%sgauge%sTestStepImplClass.java", File.separator, File.separator, File.separator), StepRegistry.getFileName(stepValue1.getStepText()));
        assertEquals("", StepRegistry.getFileName("unknown"));
    }

    public void testGetAllStepTexts() throws Exception {
        List<String> stepTexts = StepRegistry.getAllStepAnnotationTexts();
        assertEquals(5, stepTexts.size());
        assertTrue(stepTexts.contains(stepValue1.getStepText()));
        assertTrue(stepTexts.contains(stepValue2.getStepAnnotationText()));
        assertTrue(stepTexts.contains(stepValue3.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep2.getStepAnnotationText()));
    }

    public void testGetStepAnnotationFor() throws Exception {
        assertEquals(stepValue1.getStepAnnotationText(), StepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
        assertEquals(stepValue2.getStepAnnotationText(), StepRegistry.getStepAnnotationFor(stepValue2.getStepText()));
        assertEquals(stepValue3.getStepAnnotationText(), StepRegistry.getStepAnnotationFor(stepValue3.getStepText()));
        assertEquals(aliasStep1.getStepAnnotationText(), StepRegistry.getStepAnnotationFor(aliasStep1.getStepText()));
        assertEquals(aliasStep2.getStepAnnotationText(), StepRegistry.getStepAnnotationFor(aliasStep2.getStepText()));
        assertEquals("", StepRegistry.getStepAnnotationFor("unknown"));
    }

    public void testGetAliasStepTexts() throws Exception {
        assertEquals(1, StepRegistry.getAllAliasAnnotationTextsFor(stepValue1.getStepText()).size());
        assertEquals(1, StepRegistry.getAllAliasAnnotationTextsFor(stepValue2.getStepText()).size());
        assertEquals(1, StepRegistry.getAllAliasAnnotationTextsFor(stepValue3.getStepText()).size());

        Set<String> aliasStepTexts = StepRegistry.getAllAliasAnnotationTextsFor(aliasStep1.getStepText());
        assertEquals(2, aliasStepTexts.size());
        assertTrue(aliasStepTexts.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(aliasStepTexts.contains(aliasStep2.getStepAnnotationText()));

        Set<String> aliasStepTexts1 = StepRegistry.getAllAliasAnnotationTextsFor(aliasStep2.getStepText());
        assertEquals(2, aliasStepTexts1.size());
        assertTrue(aliasStepTexts1.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(aliasStepTexts1.contains(aliasStep2.getStepAnnotationText()));
    }

    public void testHasAlias() throws Exception {
        assertEquals(false, StepRegistry.hasAlias(stepValue1.getStepText()));
        assertEquals(false, StepRegistry.hasAlias(stepValue2.getStepText()));
        assertEquals(false, StepRegistry.hasAlias(stepValue3.getStepText()));
        assertEquals(true, StepRegistry.hasAlias(aliasStep1.getStepText()));
        assertEquals(true, StepRegistry.hasAlias(aliasStep2.getStepText()));
    }

    public void testRemoveEntry() throws Exception {
        StepRegistry.remove(stepValue1.getStepText());
        assertFalse(StepRegistry.contains(stepValue1.getStepText()));
        assertNull(StepRegistry.get(stepValue1.getStepText()));
        assertTrue(StepRegistry.getAll(stepValue1.getStepText()).isEmpty());
        assertEquals("", StepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
        assertEquals(false, StepRegistry.hasAlias(stepValue1.getStepText()));
    }
    
    public void testAddedToRawRegistry(){
        
        StepRegistry.addStepImplementation(stepValue1, method2);
        
        Set<Method> methods = StepRegistry.getAll(stepValue1.getStepText());
        
        assertTrue(methods.contains(method1));
        assertTrue(methods.contains(method2));
        
        
    }

    protected void tearDown() throws Exception {
        StepRegistry.remove(stepValue1.getStepText());
        StepRegistry.remove(stepValue2.getStepText());
        StepRegistry.remove(stepValue3.getStepText());
        StepRegistry.remove(aliasStep1.getStepText());
        StepRegistry.remove(aliasStep2.getStepText());
    }
}
