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