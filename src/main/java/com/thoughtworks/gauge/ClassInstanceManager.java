// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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

    public static void clearCache(){
        classInstanceMap.clear();
    }
}

