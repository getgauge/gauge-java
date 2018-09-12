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
