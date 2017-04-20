package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public class StringToBooleanConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Boolean.parseBoolean(source.getValue());
    }
}
