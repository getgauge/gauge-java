package com.thoughtworks.gauge.scan;

import java.util.NoSuchElementException;
import java.util.Set;

import org.reflections.Reflections;

import com.thoughtworks.gauge.ClassInitializer;
import com.thoughtworks.gauge.CustomClassInitializer;
import com.thoughtworks.gauge.registry.ClassInitializerRegistry;

public class CustomClassInitializerScanner implements IScanner {
    @Override
    public void scan(Reflections reflections) {
        scanForInitializer(reflections);
    }

    private void scanForInitializer(Reflections reflections) {
        Set<Class<?>> initializers = reflections.getTypesAnnotatedWith(CustomClassInitializer.class);

        if (initializers.size() > 1) {
            throw new ScanningException("Only 1 class can be annotated with @CustomClassInitializer");
        }

        try {
            Class<?> initializer = initializers.iterator().next();
            if (!ClassInitializer.class.isAssignableFrom(initializer)) {
                throw new ScanningException("Custom class initializer should implement ClassInitializer interface");
            }
            ClassInitializerRegistry.classInitializer((ClassInitializer) initializer.newInstance());
        } catch (InstantiationException e) {
            throw new ScanningException(e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ScanningException(e.getMessage());
        } catch (NoSuchElementException e) {
            // Class initializer is not defined, use default!
        }
    }
}
