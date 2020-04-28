/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StepsUtil {
    public String getStepText(String parameterizedStepText) {
        return parameterizedStepText
                .replaceAll("(<.*?>)", "{}");
    }

    public List<String> getParameters(String parameterizedStepText) {
        Pattern pattern = Pattern.compile("(<.*?>)");
        return Arrays.stream(parameterizedStepText.split(" "))
                .filter(pattern.asPredicate())
                .map(s -> s.substring(1, s.length() - 1))
                .collect(Collectors.toList());

    }
}
