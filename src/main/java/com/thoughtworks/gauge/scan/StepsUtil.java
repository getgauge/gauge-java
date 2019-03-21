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
