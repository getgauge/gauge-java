package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.ClassInitializer;
import com.thoughtworks.gauge.DefaultClassInitializer;

public class ClassInitializerRegistry {
    private static ClassInitializer classInitializer = new DefaultClassInitializer();

    public static void classInitializer(ClassInitializer initializer) {
        classInitializer = initializer;
    }

    public static ClassInitializer classInitializer() {
        return classInitializer;
    }

}
