package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public class StringToLongConverter implements StringToPrimitiveConverter {
    public Object convert(Spec.Parameter source) throws Exception {
        return Long.parseLong(source.getValue());
    }
}
