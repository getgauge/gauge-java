/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.types;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToPrimitiveConverter;
import gauge.messages.Spec.Parameter;

public class PrimitivesConverter {
    private Map<Class<?>, StringToPrimitiveConverter> primitiveConverters = new HashMap<>();

    public PrimitivesConverter() {
        primitiveConverters.put(int.class, source -> Integer.parseInt(source.getValue()));
        primitiveConverters.put(Integer.class, source -> Integer.parseInt(source.getValue()));
        primitiveConverters.put(boolean.class, source -> Boolean.parseBoolean(source.getValue()));
        primitiveConverters.put(Boolean.class, source -> Boolean.parseBoolean(source.getValue()));
        primitiveConverters.put(long.class, source -> Long.parseLong(source.getValue()));
        primitiveConverters.put(Long.class, source -> Long.parseLong(source.getValue()));
        primitiveConverters.put(float.class, source -> Float.parseFloat(source.getValue()));
        primitiveConverters.put(Float.class, source -> Float.parseFloat(source.getValue()));
        primitiveConverters.put(double.class, source -> Double.parseDouble(source.getValue()));
        primitiveConverters.put(Double.class, source -> Double.parseDouble(source.getValue()));
    }

    public boolean contains(Class<?> parameterType) {
        return primitiveConverters.containsKey(parameterType);
    }

    public Object convert(Class<?> parameterType, Parameter parameter) throws Exception {
        return primitiveConverters.get(parameterType).convert(parameter);
    }
}
