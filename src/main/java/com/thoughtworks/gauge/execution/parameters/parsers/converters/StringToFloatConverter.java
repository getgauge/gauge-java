package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public class StringToFloatConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Float.parseFloat(source.getValue());
    }
}
