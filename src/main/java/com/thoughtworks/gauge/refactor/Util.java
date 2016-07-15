// Copyright 2015 ThoughtWorks, Inc.

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

package com.thoughtworks.gauge.refactor;

import java.io.File;

public class Util {
    public static File workingDir() {
        return new File(System.getProperty("user.dir"));
    }

    public static String convertToCamelCase(String s) {
        String[] words = s.trim().split(" ");
        String text = words[0].toLowerCase();
        for (int i = 1, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i].trim();
            if (word.isEmpty()) {
                continue;
            }
            text += words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }
        return text;
    }

    public static String lineSeparator() {
        return System.getProperty("line.separator");
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
}
