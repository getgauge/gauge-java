// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// Gauge-Java is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// Gauge-Java is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with Gauge-Java.  If not, see <http://www.gnu.org/licenses/>.

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
}
