/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages class instance creation, lifetime and caching.
 */
public class ClassInstanceManager {
    private Map<Class<?>, Object> classInstanceMap = new HashMap<Class<?>, Object>();
    private static ThreadLocal<ClassInitializer> initializer = new InheritableThreadLocal<>();

    public ClassInstanceManager() {
        initializer.set(new DefaultClassInitializer());
    }

    public ClassInstanceManager(ClassInitializer classInitializer) {
        initializer.set(classInitializer);
    }

    public Object get(Class<?> declaringClass) throws Exception {
        Object classInstance = classInstanceMap.get(declaringClass);
        if (classInstance == null) {
            classInstance = getInitializer().initialize(declaringClass);
            classInstanceMap.put(declaringClass, classInstance);
        }
        return classInstance;
    }

    public static void setClassInitializer(ClassInitializer classInitializer) {
        initializer.set(classInitializer);
    }

    public void clearCache() {
        this.classInstanceMap.clear();
    }

    private static ClassInitializer getInitializer() {
        return initializer.get();
    }
}
