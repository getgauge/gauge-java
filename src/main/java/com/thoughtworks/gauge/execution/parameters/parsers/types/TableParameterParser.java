package com.thoughtworks.gauge.execution.parameters.parsers.types;

import com.thoughtworks.gauge.execution.parameters.parsers.base.BaseParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.base.ParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;

import gauge.messages.Spec.Parameter;
import gauge.messages.Spec.Parameter.ParameterType;

public class TableParameterParser extends BaseParameterParser {
    private TableConverter tableConverter;

    public TableParameterParser(ParameterParser parameterParser, TableConverter tableConverter) {
        super(parameterParser);
        this.tableConverter = tableConverter;
    }

    @Override
    protected boolean condition(Class<?> parameterType, Parameter parameter) {
        return parameter.getParameterType().equals(ParameterType.Special_Table)
                || parameter.getParameterType().equals(ParameterType.Table);
    }

    @Override
    protected Object parseParameter(Class<?> parameterType, Parameter parameter) {
        return tableConverter.convert(parameter);
    }
}
