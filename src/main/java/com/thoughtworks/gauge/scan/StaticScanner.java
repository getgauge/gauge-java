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

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.thoughtworks.gauge.ClasspathHelper;
import com.thoughtworks.gauge.FileHelper;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.connection.GaugeConnector;
import com.thoughtworks.gauge.registry.StepRegistry;
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
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;
import java.util.jar.JarFile;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;

public class StaticScanner {

    private StepRegistry stepRegistry = new StepRegistry();
    private Reflections reflections;

    public void scan(IScanner... scanners) {
        reflections = createReflections();
        for (IScanner scanner : scanners) {
            scanner.scan(reflections);
        }
    }

    public void buildStepRegistry(GaugeConnector connector) {
        Set<Method> stepImplementations = reflections.getMethodsAnnotatedWith(Step.class);
        stepRegistry.buildStepRegistry(stepImplementations, connector);
    }


    private Reflections createReflections() {
        Vfs.addDefaultURLTypes(new Vfs.UrlType() {
            @Override
            public boolean matches(URL url) throws Exception {
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
            String regex = String.format(".?\\.??%s.+\\.class", packageToScan);
            if (new FilterBuilder().include(regex).apply(s)) {
                return true;
            }
        }
        return false;
    }

    public StepRegistry getStepRegistry() {
        return stepRegistry;
    }

    public void reloadSteps(String fileName) {
        removeSteps(fileName);
        loadSteps();
    }

    private void loadSteps() {
        addStepsToRegistry();
    }

    public void removeSteps(String fileName) {
        getStepRegistry().removeSteps(fileName);
    }

    public void addStepsToRegistry() {
        Iterable<String> files = FileHelper.getAllImplementationFiles();
        for (String file : files) {
            addStepsFromFile(file);
        }
    }

    public void removeStepsFromRegistry() {
        Iterable<String> files = FileHelper.getAllImplementationFiles();
        for (String file : files) {
            removeSteps(file);
        }
    }

    private void addStepsFromFile(String file) {
        try {
            CompilationUnit compilationUnit = JavaParser.parse(new File(file));
            MethodVisitor methodVisitor = new MethodVisitor(stepRegistry, file);
            methodVisitor.visit(compilationUnit, null);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
