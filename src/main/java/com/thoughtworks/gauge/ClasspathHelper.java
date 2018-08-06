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

package com.thoughtworks.gauge;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClasspathHelper {

    public static Collection<URL> getUrls() {
        final String packagesToScan = System.getenv(GaugeConstant.PACKAGE_TO_SCAN);
        if (packagesToScan != null) {
            Collection<URL> urls = new ArrayList<>();
            final List<String> packages = Arrays.asList(packagesToScan.split(","));
            for (String packageToScan : packages) {
                urls.addAll(org.reflections.util.ClasspathHelper.forPackage(packageToScan.trim()));
            }
            return urls;
        }
        return org.reflections.util.ClasspathHelper.forJavaClassPath();
    }

}
