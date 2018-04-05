package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;
import gauge.messages.Spec.Parameter;
import gauge.messages.Spec.Parameter.ParameterType;

public class TableParameterParser implements ParameterParser {
    private TableConverter tableConverter;

    public TableParameterParser(TableConverter tableConverter) {
        this.tableConverter = tableConverter;
    }

    @Override
    public boolean canParse(Class<?> parameterType, Parameter parameter) {
        return parameter.getParameterType().equals(ParameterType.Special_Table)
                || parameter.getParameterType().equals(ParameterType.Table);
    }

    @Override
    public Object parse(Class<?> parameterType, Parameter parameter) {
        return tableConverter.convert(parameter);
    }
}
