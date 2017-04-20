package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public class StringToIntegerConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Integer.parseInt(source.getValue());
    }
}
