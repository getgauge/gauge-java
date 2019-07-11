// Copyright 2019 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

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
