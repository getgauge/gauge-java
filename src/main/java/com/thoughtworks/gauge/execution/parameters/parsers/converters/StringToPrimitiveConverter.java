package com.thoughtworks.gauge.execution.parameters.parsers.converters;

import gauge.messages.Spec;

public interface StringToPrimitiveConverter {
    Object convert(Spec.Parameter source) throws Exception;
}
