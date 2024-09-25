/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClassInstanceManagerTest {

    @Test
    public void testObjectsAreCachedInClassInstanceManager() throws Exception {
        ClassInstanceManager manager = new ClassInstanceManager();
        Object object = manager.get(TestStepImplClass.class);
        assertTrue(object instanceof TestStepImplClass);

        Object object2 = manager.get(TestStepImplClass.class);
        assertTrue(object2 instanceof TestStepImplClass);
        assertEquals(object, object2);
    }

    @Test
    public void testSettingClassInitializer() throws Exception {
        final TestStepImplClass expectedObject = new TestStepImplClass();
        ClassInstanceManager manager = new ClassInstanceManager();
        ClassInstanceManager.setClassInitializer(new ClassInitializer() {
            public Object initialize(Class<?> classToInitialize) throws Exception {
                return expectedObject;
            }
        });
        Object object1 = manager.get(TestStepImplClass.class);
        Object object2 = manager.get(String.class);
        ClassInstanceManager.setClassInitializer(null);

        assertEquals(expectedObject, object1);
        assertEquals(expectedObject, object2);
    }

    @Test
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
