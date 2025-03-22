/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class JavaParseWorker extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(JavaParseWorker.class);

    public static final Charset CHARSET = StandardCharsets.UTF_8;
    private final File javaFile;
    private CompilationUnit compilationUnit;

    JavaParseWorker(File javaFile) {
        this.javaFile = javaFile;
    }

    public void run() {
        try {
            Optional<CompilationUnit> result = new JavaParser().parse(javaFile).getResult();
            result.ifPresent(value -> compilationUnit = value);
        } catch (Exception e) {
            LOGGER.error("Unable to parse file {}", javaFile.getName());
        }
    }

    public File getJavaFile() {
        return javaFile;
    }

    CompilationUnit getCompilationUnit() {
        try {
            join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return compilationUnit;
    }
}
