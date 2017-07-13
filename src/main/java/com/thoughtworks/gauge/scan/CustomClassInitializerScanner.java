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
