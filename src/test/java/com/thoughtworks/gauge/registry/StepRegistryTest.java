/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.TestStepImplClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepRegistryTest {

    private StepValue stepValue1 = new StepValue("hello world", "hello world");
    private StepValue stepValue2 = new StepValue("hello world {}", "hello world <param0>");
    private StepValue stepValue3 = new StepValue("a step with {} and {}", "a step with <param0> and <param1>");
    private StepValue aliasStep1 = new StepValue("first step name with name <a>", "first step name with name {}");
    private StepValue aliasStep2 = new StepValue("second step name with <b>", "second step name with {}");

    private Method[] methods = TestStepImplClass.class.getMethods();
    private Method method1;
    private Method method2;
    private Method method3;
    private Method aliasMethod;
    {
        for (Method m : methods) {
            switch (m.getName()){
                case "helloWorld":
                    method1 = m;
                    break;
                case "helloWorldWithOneParam":
                    method2 = m;
                    break;
                case "helloWorldWithTwoParams":
                    method3 = m;
                    break;
                case "aliasMethod":
                    aliasMethod = m;
                    break;
            }
        }
    }
    private StepRegistry stepRegistry;

    @BeforeEach
    public void setUp() {
        this.stepRegistry = new StepRegistry();
        this.stepRegistry.addStepImplementation(stepValue1, method1, false);
        this.stepRegistry.addStepImplementation(stepValue2, method2, false);
        this.stepRegistry.addStepImplementation(stepValue3, method3, false);
        this.stepRegistry.addStepImplementation(aliasStep1, aliasMethod, false);
        this.stepRegistry.addStepImplementation(aliasStep2, aliasMethod, false);
    }

    @Test
    public void testAddingStepImplementationToRegistry() {
        assertTrue(stepRegistry.contains(stepValue1.getStepText()));
        assertTrue(stepRegistry.contains(stepValue2.getStepText()));
        assertTrue(stepRegistry.contains(stepValue3.getStepText()));
        assertTrue(stepRegistry.contains(aliasStep1.getStepText()));
        assertTrue(stepRegistry.contains(aliasStep2.getStepText()));

        assertEquals(method1, stepRegistry.get(stepValue1.getStepText()).getMethodInfo());
        assertEquals(method2, stepRegistry.get(stepValue2.getStepText()).getMethodInfo());
        assertEquals(method3, stepRegistry.get(stepValue3.getStepText()).getMethodInfo());
        assertNull(stepRegistry.get("unknown").getMethodInfo());
    }

    @Test
    public void testGetAllStepTexts() {
        List<String> stepTexts = stepRegistry.getAllStepAnnotationTexts();
        assertEquals(5, stepTexts.size());
        assertTrue(stepTexts.contains(stepValue1.getStepText()));
        assertTrue(stepTexts.contains(stepValue2.getStepAnnotationText()));
        assertTrue(stepTexts.contains(stepValue3.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep1.getStepAnnotationText()));
        assertTrue(stepTexts.contains(aliasStep2.getStepAnnotationText()));
    }

    @Test
    public void testGetStepAnnotationFor() {
        assertEquals(stepValue1.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
        assertEquals(stepValue2.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue2.getStepText()));
        assertEquals(stepValue3.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(stepValue3.getStepText()));
        assertEquals(aliasStep1.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(aliasStep1.getStepText()));
        assertEquals(aliasStep2.getStepAnnotationText(), stepRegistry.getStepAnnotationFor(aliasStep2.getStepText()));
        assertEquals("", stepRegistry.getStepAnnotationFor("unknown"));
    }

    @Test
    public void testRemoveEntry() {
        stepRegistry.remove(stepValue1.getStepText());
        assertFalse(stepRegistry.contains(stepValue1.getStepText()));
        assertNull(stepRegistry.get(stepValue1.getStepText()).getMethodInfo());
        assertEquals("", stepRegistry.getStepAnnotationFor(stepValue1.getStepText()));
    }

    @Test
    public void testAddedToRawRegistry() {
        stepRegistry.addStepImplementation(stepValue1, method2, false);

        List<StepRegistryEntry> entries = stepRegistry.getAllEntries(stepValue1.getStepText());

        assertEquals(entries.get(0).getMethodInfo(), method1);
        assertEquals(entries.get(1).getMethodInfo(), method2);
    }

    @Test
    public void testIsFileCached() {
        stepRegistry.get(stepValue1.getStepText()).setFileName("/somepath/file1.java");
        stepRegistry.get(stepValue2.getStepText()).setFileName("/somepath/file2.java");
        stepRegistry.get(stepValue3.getStepText()).setFileName("/somepath/file1.java");
        stepRegistry.get(aliasStep1.getStepText()).setFileName("/somepath/file2.java");
        stepRegistry.get(aliasStep2.getStepText()).setFileName("/somepath/file1.java");

        assertTrue(stepRegistry.isFileCached("/somepath/file1.java"));
        assertFalse(stepRegistry.isFileCached("/someotherpath/file2.java"));
    }

    @Test
    public void testGetForCurrentProject() {
        stepRegistry.addStepImplementation(stepValue1, method2, false);

        StepRegistryEntry entry = stepRegistry.getForCurrentProject(stepValue1.getStepText(), method2);

        assertNotNull(entry);
        assertEquals("com.thoughtworks.gauge.TestStepImplClass.helloWorldWithOneParam", entry.getFullyQualifiedName());
    }

    @Test
    public void testGetForCurrentProjectIgnoresExternal() {
        stepRegistry.addStepImplementation(stepValue1, method1, true);

        StepRegistryEntry entry = stepRegistry.getForCurrentProject(stepValue1.getStepText(), method2);

        assertNull(entry);
    }

    @AfterEach
    public void tearDown() {
        stepRegistry.remove(stepValue1.getStepText());
        stepRegistry.remove(stepValue2.getStepText());
        stepRegistry.remove(stepValue3.getStepText());
        stepRegistry.remove(aliasStep1.getStepText());
        stepRegistry.remove(aliasStep2.getStepText());
    }
}
