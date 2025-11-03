/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

public class DefaultClassInitializer implements ClassInitializer {

    @Override
    public Object initialize(Class<?> classToInitialize) throws Exception {
        return Class.forName(classToInitialize.getName()).getDeclaredConstructor().newInstance();
    }
}
