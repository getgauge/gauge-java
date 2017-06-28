package com.thoughtworks.gauge;

public class DefaultClassInitializer implements ClassInitializer {

    @Override
    public Object initialize(Class<?> classToInitialize) throws Exception {
        return Class.forName(classToInitialize.getName()).newInstance();
    }
}
