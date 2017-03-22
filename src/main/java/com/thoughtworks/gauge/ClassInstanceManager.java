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

import java.util.HashMap;
import java.util.Map;

/**
 * Manages class instance creation, lifetime and caching.
 */
public class ClassInstanceManager {
    private Map<Class<?>, Object> classInstanceMap = new HashMap<Class<?>, Object>();
    private static ThreadLocal<ClassInitializer> initializer = new InheritableThreadLocal<ClassInitializer>();

    public Object get(Class<?> declaringClass) throws Exception {
        Object classInstance = classInstanceMap.get(declaringClass);
        if (classInstance == null) {
            if (getInitializer() != null) {
                classInstance = getInitializer().initialize(declaringClass);
            } else {
                classInstance = Class.forName(declaringClass.getName()).newInstance();
            }
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
