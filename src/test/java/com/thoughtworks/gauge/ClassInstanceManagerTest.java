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

    protected void setUp() throws Exception {
        ClassInstanceManager.clearCache();
        ClassInstanceManager.setClassInitializer(null);
    }

    public void testObjectsAreCachedInClassInstanceManager() throws Exception {
        Object object = ClassInstanceManager.get(TestImplClass.class);
        assertTrue(object instanceof TestImplClass);

        Object object2 = ClassInstanceManager.get(TestImplClass.class);
        assertTrue(object2 instanceof TestImplClass);
        assertEquals(object, object2);

    }

    public void testSettingClassInitializer() throws Exception {
        final TestImplClass expectedObject = new TestImplClass();
        ClassInstanceManager.setClassInitializer(new ClassInitializer() {
            public Object initialize(Class<?> classToInitialize) throws Exception {
                return expectedObject;
            }
        });
        Object object1 = ClassInstanceManager.get(TestImplClass.class);
        Object object2 = ClassInstanceManager.get(String.class);
        assertEquals(expectedObject, object1);
        assertEquals(expectedObject, object2);
    }

    public void testClearingCache() throws Exception {
        Object object1 = ClassInstanceManager.get(TestImplClass.class);
        ClassInstanceManager.clearCache();
        Object object2 = ClassInstanceManager.get(TestImplClass.class);
        assertTrue(object1 instanceof TestImplClass);
        assertTrue(object2 instanceof TestImplClass);
        assertFalse(object1.equals(object2));
    }
}
