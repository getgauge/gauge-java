// Copyright 2019 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.execution.parameters.parsers.base;

import com.thoughtworks.gauge.ClasspathHelper;
import com.thoughtworks.gauge.execution.parameters.ParsingException;
import com.thoughtworks.gauge.execution.parameters.parsers.converters.TableConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.EnumParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitiveParameterParser;
import com.thoughtworks.gauge.execution.parameters.parsers.types.PrimitivesConverter;
import com.thoughtworks.gauge.execution.parameters.parsers.types.TableParameterParser;
import gauge.messages.Spec.Parameter;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
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
                .setScanners(new SubTypesScanner())
                .addUrls(ClasspathHelper.getUrls())
                .filterInputsBy(new FilterBuilder().include(".+\\.class"));
        return new Reflections(config);
    }

    private @Nullable
    ParameterParser asCustomParameterParser(Class<? extends ParameterParser> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // currently there seems to be no logging system used, so we cannot warn the user about an error
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
