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
                urls.addAll(org.reflections.util.ClasspathHelper.forPackage(packageToScan));
            }
            return urls;
        }
        return org.reflections.util.ClasspathHelper.forJavaClassPath();
    }

}
