/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.ClassInitializer;
import com.thoughtworks.gauge.DefaultClassInitializer;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

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
                ClassInitializerRegistry.classInitializer(initializer.getDeclaredConstructor().newInstance());
                Logger.debug(String.format("Using %s as class initializer", initializer.getName()));
            } catch (InstantiationException e) {
                Logger.error(String.format("Could not instantiate %s, continuing using default class initializer", initializer.getName()));
            } catch (IllegalAccessException | NoSuchMethodException e) {
                Logger.error(String.format("Could not access %s constructor, continuing using default class initializer", initializer.getName()));
            } catch (InvocationTargetException e) {
                Logger.error(String.format("%s's default constructor threw an error [%s], continuing using default class initializer", initializer.getName(), e));
                throw new RuntimeException(e);
            }
        }

        if (initializers.size() > 1) {
            Logger.warning("Multiple class initializers found, switching to default.");
        }
    }
}
