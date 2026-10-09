/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.reflections.vfs.Vfs;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ClasspathHelper {

    public static Collection<URL> getUrls() {
        final String packagesToScan = System.getenv(GaugeConstant.PACKAGE_TO_SCAN);
        if (packagesToScan != null) {
            Collection<URL> urls = new ArrayList<>();
            for (String packageToScan : packagesToScan.split(",")) {
                urls.addAll(org.reflections.util.ClasspathHelper.forPackage(packageToScan.trim()));
            }
            return urls;
        }
        // Gradle puts source set output dirs on the classpath even if they were never created, e.g. build/classes/java/main in Kotlin-only projects.
        // Scanning such dirs logs NullPointerException stack traces, so skip entries that don't exist.
        return org.reflections.util.ClasspathHelper.forJavaClassPath().stream()
                .filter(url -> Vfs.getFile(url) != null)
                .collect(Collectors.toList());
    }

}
