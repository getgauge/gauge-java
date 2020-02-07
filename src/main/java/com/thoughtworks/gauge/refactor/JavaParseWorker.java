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

package com.thoughtworks.gauge.refactor;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.thoughtworks.gauge.Logger;

import java.io.File;

public class JavaParseWorker extends Thread {

    public static final String ENCODING = "UTF-8";
    private File javaFile;
    private CompilationUnit compilationUnit;

    JavaParseWorker(File javaFile) {
        this.javaFile = javaFile;
    }

    public void run() {
        try {
            compilationUnit = JavaParser.parse(javaFile);
        } catch (Exception e) {
            Logger.error("Unable to parse file " + javaFile.getName());
        }
    }

    public File getJavaFile() {
        return javaFile;
    }

    CompilationUnit getCompilationUnit() {
        try {
            join();
        } catch (InterruptedException e) {

        }
        return compilationUnit;
    }
}
