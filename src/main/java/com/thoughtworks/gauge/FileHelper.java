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

package com.thoughtworks.gauge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileHelper {

    public static Iterable<String> getAllImplementationFiles() {
        ArrayList<String> outputFiles = new ArrayList<>();
        try (Stream<Path> filePathStream = Files.walk(Paths.get(System.getenv("GAUGE_PROJECT_ROOT")))) {
            filePathStream.forEach(filePath -> {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".java")) {
                    outputFiles.add(filePath.toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFiles;
    }

    public static String getClassName(File filepath) {
        String fileName = filepath.getName();
        return com.google.common.io.Files.getNameWithoutExtension(fileName);
    }

    public static File getFileName(String suffix, int count) {

        String filename = "StepImplementation" + suffix + ".java";
        Path filepath = Paths.get(System.getenv("GAUGE_PROJECT_ROOT"), "src", "test", "java", filename);
        File file = new File(filepath.toString());
         return file.exists() ? getFileName(String.valueOf(++count), count) : file;
    }
}
