// Copyright 2019 ThoughtWorks, Inc.

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

import java.util.Set;

import org.reflections.Reflections;

import com.thoughtworks.gauge.ClassInitializer;
import com.thoughtworks.gauge.DefaultClassInitializer;
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;

public class CustomClassInitializerScanner implements IScanner {
    @Override
    public void scan(Reflections reflections) {
        scanForInitializer(reflections);
    }

    private void scanForInitializer(Reflections reflections) {
        Set<Class<? extends ClassInitializer>> initializers = reflections.getSubTypesOf(ClassInitializer.class);
        initializers.remove(DefaultClassInitializer.class);
        if (initializers.size() == 1) {
            Class<? extends ClassInitializer> initializer = initializers.iterator().next();
            try {
                ClassInitializerRegistry.classInitializer(initializer.newInstance());
                System.out.println(String.format("Using %s as class initializer", initializer.getName()));
            } catch (InstantiationException e) {
                System.err.println(String.format("Could not instantiate %s, continuing using default class initializer", initializer.getName()));
            } catch (IllegalAccessException e) {
                System.err.println(String.format("Could not access %s constructor, continuing using default class initializer", initializer.getName()));
            }
        }

        if (initializers.size() > 1) {
            System.out.println("[Warning] Multiple class initializers found, switching to default.");
        }
    }
}
