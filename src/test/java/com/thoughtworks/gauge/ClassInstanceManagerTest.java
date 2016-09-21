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

package com.thoughtworks.gauge;

import junit.framework.TestCase;

public class ClassInstanceManagerTest extends TestCase {

    public void testObjectsAreCachedInClassInstanceManager() throws Exception {
        ClassInstanceManager manager = new ClassInstanceManager();
        Object object = manager.get(TestStepImplClass.class);
        assertTrue(object instanceof TestStepImplClass);

        Object object2 = manager.get(TestStepImplClass.class);
        assertTrue(object2 instanceof TestStepImplClass);
        assertEquals(object, object2);
    }

    public void testSettingClassInitializer() throws Exception {
        final TestStepImplClass expectedObject = new TestStepImplClass();
        ClassInstanceManager manager = new ClassInstanceManager();
        manager.setClassInitializer(new ClassInitializer() {
            public Object initialize(Class<?> classToInitialize) throws Exception {
                return expectedObject;
            }
        });
        Object object1 = manager.get(TestStepImplClass.class);
        Object object2 = manager.get(String.class);
        assertEquals(expectedObject, object1);
        assertEquals(expectedObject, object2);
    }

    public void testClearingCache() throws Exception {
        ClassInstanceManager manager = new ClassInstanceManager();
        Object object1 = manager.get(TestStepImplClass.class);
        manager.clearCache();
        Object object2 = manager.get(TestStepImplClass.class);
        assertTrue(object1 instanceof TestStepImplClass);
        assertTrue(object2 instanceof TestStepImplClass);
        assertFalse(object1.equals(object2));
    }
}
