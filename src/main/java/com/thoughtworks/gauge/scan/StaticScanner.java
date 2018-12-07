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
import com.google.common.base.Charsets;
import com.thoughtworks.gauge.FileHelper;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepRegistryEntry;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.registry.StepRegistry;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

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
        try {
            StringReader reader = new StringReader(contents);
            CompilationUnit compilationUnit = JavaParser.parse(reader);
            RegistryMethodVisitor methodVisitor = new RegistryMethodVisitor(stepRegistry, file);
            methodVisitor.visit(compilationUnit, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void removeSteps(String fileName) {
        stepRegistry.removeSteps(fileName);
    }

    public void addStepsToRegistry() {
        Iterable<String> files = FileHelper.getAllImplementationFiles();
        for (String file : files) {
            String contents = readFile(file, Charsets.UTF_8);
            addStepsFromFileContents(file, contents);
        }
    }

    public StepRegistry getStepRegistry(ClasspathScanner classpathScanner) {
        Set<Method> methods = classpathScanner.getAllMethods();
        for (Method method : methods) {
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) {
                for (String stepName : annotation.value()) {
                    String stepText = stepName.replaceAll("(<.*?>)", "{}");
                    StepValue stepValue = new StepValue(stepText, stepName);
                    StepRegistryEntry entry = stepRegistry.get(stepText);
                    entry.setMethodInfo(method);
                    stepRegistry.addStep(stepValue, entry);
                }
            }
        }
        return stepRegistry;
    }

    public String readFile(String path, Charset encoding) {
        try {
            byte[] contents = Files.readAllBytes(Paths.get(path));
            return new String(contents, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
