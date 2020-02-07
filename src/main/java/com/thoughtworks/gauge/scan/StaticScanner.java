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
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.google.common.base.Charsets;
import com.thoughtworks.gauge.FileHelper;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;

public class StaticScanner {

    private StepRegistry stepRegistry;

    public StaticScanner() {
        this.stepRegistry = new StepRegistry();
    }

    public StepRegistry getRegistry() {
        return stepRegistry;
    }


    public void reloadSteps(String fileName, String contents) {
        removeSteps(fileName);
        addStepsFromFileContents(fileName, contents);
    }

    public void addStepsFromFileContents(String file, String contents) {
        StringReader reader = new StringReader(contents);
        CompilationUnit compilationUnit = JavaParser.parse(reader);
        if (!this.shouldScan(compilationUnit)) {
            return;
        }
        RegistryMethodVisitor methodVisitor = new RegistryMethodVisitor(stepRegistry, file);
        methodVisitor.visit(compilationUnit, null);
    }

    private boolean shouldScan(CompilationUnit unit) {
        final String packagesToScan = System.getenv(PACKAGE_TO_SCAN);
        Optional<PackageDeclaration> pakage = unit.getPackageDeclaration();
        if (packagesToScan == null || packagesToScan.isEmpty() || !pakage.isPresent()) {
            return true;
        }
        List<String> packages = Arrays.stream(packagesToScan.split(",")).map(String::trim).collect(Collectors.toList());
        return packages.contains(pakage.get().getName().asString());
    }

    public void addStepsToRegistry() {
        Iterable<String> files = FileHelper.getAllImplementationFiles();
        for (String file : files) {
            String contents = readFile(file, Charsets.UTF_8);
            addStepsFromFileContents(file, contents);
        }
    }

    public void removeSteps(String fileName) {
        stepRegistry.removeSteps(fileName);
    }

    public boolean isFileCached(String fileName) {
        return stepRegistry.isFileCached(fileName);
    }

    public String readFile(String path, Charset encoding) {
        try {
            byte[] contents = Files.readAllBytes(Paths.get(path));
            return new String(contents, encoding);
        } catch (IOException e) {
            Logger.error("Unable to read file", e);
        }
        return null;
    }
}
