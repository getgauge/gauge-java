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

package com.thoughtworks.gauge.scan;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * Scans the current Classpath and passes to all the scanners passed.
 */
public class ClasspathScanner {
    public void scan(IScanner... scanners) {
        Reflections reflections = createReflections();
        for (IScanner scanner : scanners) {
            scanner.scan(reflections);
        }
    }

    private Reflections createReflections() {
        Configuration config = new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner(), new SubTypesScanner())
                .addUrls(ClasspathHelper.forJavaClassPath());
        return new Reflections(config);
    }
}
