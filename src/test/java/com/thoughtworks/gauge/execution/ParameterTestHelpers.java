package com.thoughtworks.gauge.execution;

import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;

public class ParameterTestHelpers {
    public static Parameter parameter(String value) {
        return Spec.Parameter.newBuilder().setValue(value).build();
    }

    public static Object asObject(Object any) {
        return any;
    }
}
