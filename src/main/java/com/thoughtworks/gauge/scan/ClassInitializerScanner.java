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

import com.thoughtworks.gauge.ClassInitializer;
import com.thoughtworks.gauge.ClassInstanceManager;
import org.reflections.Reflections;

import java.util.Set;

public class ClassInitializerScanner implements IScanner {

    @Override
    public void scan(Reflections reflections) {
        Set<Class<? extends ClassInitializer>> classInitializers = reflections.getSubTypesOf(ClassInitializer.class);
        if (classInitializers.size() > 0) {
            try {
                ClassInitializer classInitializer = classInitializers.iterator().next().newInstance();
                ClassInstanceManager.getInstance().setClassInitializer(classInitializer);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
