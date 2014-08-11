package com.thoughtworks.gauge;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages class instance creation, lifetime and caching
 */
public class ClassInstanceManager {
    private static Map<Class<?>, Object> classInstanceMap = new HashMap<Class<?>, Object>();
    private static ClassInitializer initializer;

    public static Object get(Class<?> declaringClass) throws Exception {
        Object classInstance = classInstanceMap.get(declaringClass);
        if (classInstance == null) {
            if (initializer != null) {
                classInstance = initializer.initialize(declaringClass);
            } else
                classInstance = Class.forName(declaringClass.getName()).newInstance();
            classInstanceMap.put(declaringClass, classInstance);
        }
        return classInstance;
    }

    public static void setClassInitializer(ClassInitializer initializer) {
        ClassInstanceManager.initializer = initializer;
    }
}

