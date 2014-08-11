package com.thoughtworks.gauge;

public interface ClassInitializer {
    Object initialize(Class<?> classToInitialize) throws Exception;
}
