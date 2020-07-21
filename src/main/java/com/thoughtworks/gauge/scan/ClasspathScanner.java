/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.ClasspathHelper;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.vfs.SystemDir;
import org.reflections.vfs.Vfs;
import org.reflections.vfs.ZipDir;

import java.io.File;
import java.net.URL;
import java.util.jar.JarFile;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;

/**
 * Scans the current Classpath and passes to all the scanners passed.
 */
public class ClasspathScanner {

    private Reflections reflections;

    public void scan(IScanner... scanners) {
        reflections = createReflections();
        for (IScanner scanner : scanners) {
            scanner.scan(reflections);
        }
    }

    private Reflections createReflections() {
        Vfs.addDefaultURLTypes(new Vfs.UrlType() {
            @Override
            public boolean matches(URL url) {
                return "file".equals(url.getProtocol());
            }

            @Override
            public Vfs.Dir createDir(URL url) throws Exception {
                File file = Vfs.getFile(url);
                return file.isDirectory() ? new SystemDir(file) : new ZipDir(new JarFile(Vfs.getFile(url)));
            }
        });

        Configuration config = new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner(), new SubTypesScanner())
                .addUrls(ClasspathHelper.getUrls())
                .filterInputsBy(this::shouldScan);

        return new Reflections(config);
    }

    private boolean shouldScan(String s) {
        final String packagesToScan = System.getenv(PACKAGE_TO_SCAN);
        if (packagesToScan == null || packagesToScan.isEmpty()) {
            return new FilterBuilder().include(".+\\.class").apply(s);
        }
        final String[] packages = packagesToScan.split(",");
        for (String packageToScan : packages) {
            String regex = String.format(".?\\.??%s\\..+\\.class", packageToScan);
            if (new FilterBuilder().include(regex).apply(s)) {
                return true;
            }
        }
        return false;
    }
}
