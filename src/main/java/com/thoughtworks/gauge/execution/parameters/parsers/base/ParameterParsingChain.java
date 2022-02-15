/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.ClasspathHelper;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.EnumParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitiveParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitivesConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.TableParameterParser;
import gauge.messages.Spec.Parameter;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import javax.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ParameterParsingChain implements ParameterParser {

    private List<ParameterParser> chain = new LinkedList<>();

    public ParameterParsingChain() {
        createReflections().getSubTypesOf(CustomParameterParser.class).stream()
                .map(this::asCustomParameterParser)
                .filter(Objects::nonNull)
                .forEach(chain::add);
        chain.add(new TableParameterParser(new TableConverter()));
        chain.add(new EnumParameterParser());
        chain.add(new PrimitiveParameterParser(new PrimitivesConverter()));
    }

    private Reflections createReflections() {
        Configuration config = new ConfigurationBuilder()
                .setScanners(Scanners.SubTypes)
                .addUrls(ClasspathHelper.getUrls())
                .filterInputsBy(new FilterBuilder().includePattern(".+\\.class"));
        return new Reflections(config);
    }

    private @Nullable
    ParameterParser asCustomParameterParser(Class<? extends ParameterParser> clazz) {
        try {
            ParameterParser instance = clazz.newInstance();
            Logger.debug(String.format("Adding %s as custom parameter parser", clazz.getName()));
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.error(String.format("Cannot add %s as custom parameter parser", clazz.getName()), e);
            return null;
        }
    }

    @Override
    public boolean canParse(Class<?> parameterType, Parameter parameter) {
        return true;
    }

    public Object parse(Class<?> parameterType, Parameter parameter) throws ParsingException {
        for (ParameterParser parser : chain) {
            if (parser.canParse(parameterType, parameter)) {
                return parser.parse(parameterType, parameter);
            }
        }
        return parameter.getValue();
    }

}
