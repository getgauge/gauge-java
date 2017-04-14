package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.types.EnumParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.FallbackParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitiveParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.TableParameterParser;

import gauge.messages.Spec.Parameter;

public class ParameterParsingChain implements ParameterParser {
    private ParameterParser parameterParsingChain;

    public ParameterParsingChain() {
        parameterParsingChain = new TableParameterParser(
                new EnumParameterParser(new PrimitiveParameterParser(new FallbackParameterParser())));
    }

    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        return parameterParsingChain.parse(parameterType, parameter);
    }
}
