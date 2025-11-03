/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import com.google.common.base.Splitter;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {
    private static final Splitter SPACE_SPLITTER = Splitter.on(" ").trimResults().omitEmptyStrings();

    public static File workingDir() {
        String wd = System.getenv(GaugeConstant.GAUGE_PROJECT_ROOT);
        if (wd != null && !wd.isEmpty()) {
            return new File(wd);
        }
        return new File(System.getProperty("user.dir"));
    }

    public static String convertToCamelCase(String s) {
        List<String> words = SPACE_SPLITTER.splitToList(s);
        return IntStream.range(0, words.size())
                .mapToObj(i -> {
                    String word = words.get(i);
                    return i == 0 ? word.toLowerCase() : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
                })
                .collect(Collectors.joining());
    }

    public static String getValidJavaIdentifier(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isJavaIdentifierPart(s.charAt(i))) {
                builder.append(s.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String trimQuotes(String text) {
        if (text == null || text.isBlank()) {
            return text;
        }
        int start = text.charAt(0) == '"'  ? 1 : 0;
        int end = text.length();
        if (end > start && text.charAt(end - 1) == '"') {
            end--;
        }
        return text.substring(start, end);
    }

    public static boolean shouldTakeFailureScreenshot() {
        String screenshotOnFailureEnabled = System.getenv(GaugeConstant.SCREENSHOT_ON_FAILURE_ENABLED);
        return !(screenshotOnFailureEnabled == null || "false".equalsIgnoreCase(screenshotOnFailureEnabled));
    }

    public static String stacktraceFrom(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw, true)) {
            throwable.printStackTrace(pw);
            return sw.getBuffer().toString();
        }
    }

}
