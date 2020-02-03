package com.thoughtworks.gauge.execution;

import static com.thoughtworks.gauge.test.TestValues.A_VALUE;

import com.thoughtworks.gauge.test.TestValues;

import gauge.messages.Spec;
import gauge.messages.Spec.Parameter;
import gauge.messages.Spec.Parameter.ParameterType;

public class ParameterTestHelpers {
    public static Parameter parameter(String value) {
        return Spec.Parameter.newBuilder().setValue(value).build();
    }

    public static Parameter aParameterWith(ParameterType type) {
        return Spec.Parameter.newBuilder().setParameterType(type).setValue(TestValues.A_VALUE).build();
    }

    public static Parameter aSpecialTableParameter() {
        return aParameterWith(ParameterType.Special_Table);
    }

    public static Parameter aTableParameter() {
        return aParameterWith(ParameterType.Table);
    }

    public static Parameter aParameter() {
        return parameter(A_VALUE);
    }
}
