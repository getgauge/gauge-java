/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.thoughtworks.gauge.FileHelper;
import com.thoughtworks.gauge.Logger;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.gauge.GaugeConstant.PACKAGE_TO_SCAN;

public class StaticScanner {

    private final StepRegistry stepRegistry;

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
        ParseResult<CompilationUnit> result = new JavaParser().parse(reader);
        boolean shouldScan = result.getResult().map(this::shouldScan).orElse(false);
        if (!shouldScan) {
            return;
        }
        RegistryMethodVisitor methodVisitor = new RegistryMethodVisitor(stepRegistry, file);
        methodVisitor.visit(result.getResult().get(), null);
    }

    private boolean shouldScan(CompilationUnit unit) {
        final String packagesToScan = System.getenv(PACKAGE_TO_SCAN);
        if (packagesToScan == null || packagesToScan.isEmpty() || unit.getPackageDeclaration().isEmpty()) {
            return true;
        }
        List<String> packages = Arrays.stream(packagesToScan.split(",")).map(String::trim).collect(Collectors.toList());
        return unit.getPackageDeclaration().map((p) -> packages.contains(p.getName().asString())).orElse(false);
    }

    public void addStepsToRegistry() {
        Iterable<String> files = FileHelper.getAllImplementationFiles();
        for (String file : files) {
            String contents = readFile(file, StandardCharsets.UTF_8);
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
