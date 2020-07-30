/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.thoughtworks.gauge.ClasspathHelper;
import com.thoughtworks.gauge.Logger;
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
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarFile;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;

/**
 * Scans the current Classpath and passes to all the scanners passed.
 */
public class ClasspathScanner {

    private static AtomicBoolean done = new AtomicBoolean();

    public static void scanOnce(IScanner... scanners) {
        if (!done.get()) {
            Logger.debug("Creating reflections to scan...");
            Reflections reflections = createReflections();
            for (IScanner scanner : scanners) {
                scanner.scan(reflections);
            }
            done.set(true);
        }
    }

    private static Reflections createReflections() {
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

        Collection<URL> urls = ClasspathHelper.getUrls();
        Logger.debug("CLASSPATH resolved these URLs: " + urls);
        Configuration config = new ConfigurationBuilder()
                .setScanners(new MethodAnnotationsScanner(), new SubTypesScanner())
                .addUrls(urls)
                .filterInputsBy(s -> {
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

                });

        return new Reflections(config);
    }
}
