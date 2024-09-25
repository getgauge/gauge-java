/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

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
        return org.reflections.util.ClasspathHelper.forJavaClassPath();
    }

}
