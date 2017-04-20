package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public class StringToDoubleConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Double.parseDouble(source.getValue());
    }
}
