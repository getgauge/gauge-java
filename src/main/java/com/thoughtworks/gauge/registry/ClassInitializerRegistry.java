/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
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
