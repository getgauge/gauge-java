package com.thoughtworks.gauge.registry;

import com.thoughtworks.gauge.ClassInitializer;

public class ClassInitializerRegistry {

    private static ClassInitializer classInitializer;

    public static void classInitializer(ClassInitializer initializer) {
        classInitializer = initializer;
    }

    public static ClassInitializer classInitializer() {
        return classInitializer;
    }

}
