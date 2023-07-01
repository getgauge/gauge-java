/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class Util {
    public static File workingDir() {
        String wd = System.getenv(GaugeConstant.GAUGE_PROJECT_ROOT);
        if (wd != null && !wd.isEmpty()) {
            return new File(wd);
        }
        return new File(System.getProperty("user.dir"));
    }

    public static String convertToCamelCase(String s) {
        String[] words = s.trim().split(" ");
        String text = words[0].toLowerCase();
        for (int i = 1, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i].trim();
            if (!word.isEmpty()) {
                text += words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
            }
        }
        return text;
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
        return StringUtils.stripEnd(StringUtils.stripStart(text, "\""), "\"");
    }

    public static boolean shouldTakeFailureScreenshot() {
        String screenshotOnFailureEnabled = System.getenv(GaugeConstant.SCREENSHOT_ON_FAILURE_ENABLED);
        return !(screenshotOnFailureEnabled == null || screenshotOnFailureEnabled.toLowerCase().equals("false"));
    }

}
