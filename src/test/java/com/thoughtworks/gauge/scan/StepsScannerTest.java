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

package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.TestStepImplClass;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StepsScannerTest {

    private StepValue stepValue1 = new StepValue("hello world", "hello world");
    private StepValue stepValue2 = new StepValue("hello world {}", "hello world <param0>");
    private StepValue stepValue3 = new StepValue("a step with {} and {}", "a step with <param0> and <param1>");
    private StepValue aliasStep1 = new StepValue("first step name with name <a>", "first step name with name {}");
    private StepValue aliasStep2 = new StepValue("second step name with <b>", "second step name with {}");

    private Method[] methods = TestStepImplClass.class.getMethods();
    private Method method1;
    private Method method2;
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
                case "aliasMethod":
                    aliasMethod = m;
                    break;
            }
        }
    }

    @Test
    public void testBuildRegistry() {
        HashSet<Method> steps = new HashSet<>();
        steps.add(method1);
        steps.add(aliasMethod);
        Reflections reflections = mock(Reflections.class);
        when(reflections.getMethodsAnnotatedWith(Step.class)).thenReturn(steps);

        StepRegistry registry = new StepRegistry();
        new StepsScanner(registry).scan(reflections);

        assertEquals(3, registry.getAllStepAnnotationTexts().size());
    }

    @Test
    public void testBuildStepRegistryUpdatesReflectedMethod() {
        HashSet<Method> steps = new HashSet<>();
        steps.add(method2);
        String expectedMethodName = method2.getName();

        StepRegistryEntry entry = new StepRegistryEntry();
        entry.setFullyQualifiedName(method2.getName()); // simulate static scan of this method

        Reflections reflections = mock(Reflections.class);
        when(reflections.getMethodsAnnotatedWith(Step.class)).thenReturn(steps);

        StepRegistry registry = mock(StepRegistry.class);
        String stepTemplateText = "hello world {}";
        when(registry.contains(stepTemplateText)).thenReturn(true);
        when(registry.getForCurrentProject(stepTemplateText, method2)).thenReturn(entry);

        new StepsScanner(registry).scan(reflections);

        assertNotNull(entry.getMethodInfo());
        assertEquals(expectedMethodName, entry.getMethodInfo().getName());
    }

    @Test
    public void testBuildStepRegistryWhenStepTextExistsInRegistry() {
        HashSet<Method> steps = new HashSet<>();
        steps.add(method2);

        Reflections reflections = mock(Reflections.class);
        when(reflections.getMethodsAnnotatedWith(Step.class)).thenReturn(steps);

        StepRegistry registry = mock(StepRegistry.class);
        String stepTemplateText = "hello world {}";
        when(registry.contains(stepTemplateText)).thenReturn(true);
        when(registry.getForCurrentProject(stepTemplateText, method2)).thenReturn(null);

        new StepsScanner(registry).scan(reflections);

        ArgumentCaptor<StepValue> stepValueArgumentCaptor = ArgumentCaptor.forClass(StepValue.class);
        ArgumentCaptor<Method> methodArgumentCaptor = ArgumentCaptor.forClass(Method.class);
        ArgumentCaptor<Boolean> booleanArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);

        verify(registry, times(1)).addStepImplementation(stepValueArgumentCaptor.capture(), methodArgumentCaptor.capture(), booleanArgumentCaptor.capture());

        assertEquals(stepTemplateText, stepValueArgumentCaptor.getValue().getStepText());
        assertEquals(method2, methodArgumentCaptor.getValue());
        assertTrue(booleanArgumentCaptor.getValue());
    }

    @Test
    public void testBuildStepRegistryForExternalReference() {
        HashSet<Method> steps = new HashSet<>();
        steps.add(method2);

        Reflections reflections = mock(Reflections.class);
        when(reflections.getMethodsAnnotatedWith(Step.class)).thenReturn(steps);

        StepRegistry registry = mock(StepRegistry.class);
        String stepTemplateText = "hello world {}";
        when(registry.contains(stepTemplateText)).thenReturn(false);

        new StepsScanner(registry).scan(reflections);

        ArgumentCaptor<StepValue> stepValueArgumentCaptor = ArgumentCaptor.forClass(StepValue.class);
        ArgumentCaptor<Method> methodArgumentCaptor = ArgumentCaptor.forClass(Method.class);
        ArgumentCaptor<Boolean> booleanArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);

        verify(registry, times(1)).addStepImplementation(stepValueArgumentCaptor.capture(), methodArgumentCaptor.capture(), booleanArgumentCaptor.capture());

        assertEquals(stepTemplateText, stepValueArgumentCaptor.getValue().getStepText());
        assertEquals(method2, methodArgumentCaptor.getValue());
        assertTrue(booleanArgumentCaptor.getValue());
    }
}