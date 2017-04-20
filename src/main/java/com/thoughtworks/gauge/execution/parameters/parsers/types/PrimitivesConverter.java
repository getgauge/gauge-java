package com.thoughtworks.gauge.execution.parameters.parsers.types;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToBooleanConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToDoubleConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToFloatConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToIntegerConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToLongConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.StringToPrimitiveConverter;

import gauge.messages.Spec.Parameter;

public class PrimitivesConverter {
    private Map<Class<?>, StringToPrimitiveConverter> primitiveConverters = new HashMap<Class<?>, StringToPrimitiveConverter>();

    public PrimitivesConverter() {
        primitiveConverters.put(int.class, new StringToIntegerConverter());
        primitiveConverters.put(Integer.class, new StringToIntegerConverter());
        primitiveConverters.put(boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(Boolean.class, new StringToBooleanConverter());
        primitiveConverters.put(long.class, new StringToLongConverter());
        primitiveConverters.put(Long.class, new StringToLongConverter());
        primitiveConverters.put(float.class, new StringToFloatConverter());
        primitiveConverters.put(Float.class, new StringToFloatConverter());
        primitiveConverters.put(double.class, new StringToDoubleConverter());
        primitiveConverters.put(Double.class, new StringToDoubleConverter());
    }

    public boolean contains(Class<?> parameterType) {
        return primitiveConverters.containsKey(parameterType);
    }

    public Object convert(Class<?> parameterType, Parameter parameter) throws Exception {
        return primitiveConverters.get(parameterType).convert(parameter);
    }
}
