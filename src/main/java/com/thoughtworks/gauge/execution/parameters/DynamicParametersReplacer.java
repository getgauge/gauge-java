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

package com.thoughtworks.gauge.execution.parameters;

import gauge.messages.Spec;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DynamicParametersReplacer {

    private static final Pattern REPLACER_PATTERN = Pattern.compile("<(.*?)>");

    private DynamicParametersReplacer() {

    }

    public static String replacePlaceholders(String stepText, List<Spec.Parameter> parameters) {
        if (StringUtils.isEmpty(stepText) || parameters == null || parameters.isEmpty()) {
            return stepText;
        }

        for (Spec.Parameter parameter : parameters) {
            if (parameter.getParameterType() == Spec.Parameter.ParameterType.Dynamic) {
                stepText = findAndReplacePlaceholder(stepText, parameter.getValue());
            }
        }

        return stepText;
    }

    private static String findAndReplacePlaceholder(String stepText, String parameterValue) {
        Matcher matcher = REPLACER_PATTERN.matcher(stepText);
        if (matcher.find()) {
            return matcher.replaceFirst(Matcher.quoteReplacement("\"" + parameterValue + "\""));
        }
        return stepText;
    }
}
