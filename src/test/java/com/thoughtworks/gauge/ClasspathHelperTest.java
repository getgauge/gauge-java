/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static uk.org.webcompere.systemstubs.SystemStubs.restoreSystemProperties;

public class ClasspathHelperTest {

    @Test
    public void testGetUrlsSkipsNonExistentClasspathEntries(@TempDir File tempDir) throws Exception {
        assumeTrue(System.getenv(PACKAGE_TO_SCAN) == null);
        File existingDir = new File(tempDir, "classes/kotlin/test");
        existingDir.mkdirs();
        File existingJar = new File(tempDir, "lib.jar");
        existingJar.createNewFile();
        File missingDir = new File(tempDir, "classes/java/test");
        String classpath = String.join(File.pathSeparator,
                existingDir.getAbsolutePath(), missingDir.getAbsolutePath(), existingJar.getAbsolutePath());

        restoreSystemProperties(() -> {
            System.setProperty("java.class.path", classpath);
            Collection<URL> urls = ClasspathHelper.getUrls();
            assertEquals(List.of(existingDir.toURI().toURL(), existingJar.toURI().toURL()), List.copyOf(urls));
        });
    }
}
