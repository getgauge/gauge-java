/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.io.Files.getNameWithoutExtension;
import static com.thoughtworks.gauge.GaugeConstant.DEFAULT_SRC_DIR;
import static com.thoughtworks.gauge.GaugeConstant.GAUGE_CUSTOM_COMPILE_DIR;
import static com.thoughtworks.gauge.GaugeConstant.GAUGE_PROJECT_ROOT;
import static com.thoughtworks.gauge.GaugeConstant.DEFAULT_SRC_DIRS;

public class FileHelper {
    private static final String CUSTOM_COMPILE_DIR_SEPARATOR = ",";
    private static final String JAVA_FILE_EXT = ".java";

    public static List<String> getAllImplementationFiles() {
        ArrayList<String> outputFiles = new ArrayList<>();
        getStepImplDirs().forEach(dir -> {
            try (Stream<Path> filePathStream = Files.walk(Path.of(dir))) {
                filePathStream.forEach(filePath -> {
                    if (Files.isRegularFile(filePath) && filePath.toString().endsWith(JAVA_FILE_EXT)) {
                        outputFiles.add(filePath.toString());
                    }
                });
            } catch (IOException e) {
                Logger.error("", e);
            }
        });
        return outputFiles;
    }

    private static Path getAbsolutePath(String dir) {
        Path path = Path.of(dir);
        return !path.isAbsolute() ? Path.of(System.getenv(GAUGE_PROJECT_ROOT), dir) : path;
    }

    static List<String> getStepImplDirs() {
        List<String> srcDirs = new ArrayList<>();
        String customCompileDirs = System.getenv(GAUGE_CUSTOM_COMPILE_DIR);
        if (customCompileDirs != null && !customCompileDirs.isEmpty()) {
            Arrays.asList(customCompileDirs.split(CUSTOM_COMPILE_DIR_SEPARATOR))
                    .forEach(d -> srcDirs.add(getAbsolutePath(d.trim()).toString()));
        } else {
            srcDirs.addAll(getDefaultStepImplDirs());
        }
        return srcDirs;
    }

    public static String getClassName(File filepath) {
        String fileName = filepath.getName();
        return getNameWithoutExtension(fileName);
    }

    public static File getDefaultImplFileName(String suffix, int count) {
        String filename = "StepImplementation" + suffix + JAVA_FILE_EXT;
        Path filepath = Path.of(getDefaultStepImplDir(), filename);
        File file = new File(filepath.toString());
        return file.exists() ? getDefaultImplFileName(String.valueOf(++count), count) : file;
    }

    private static String getDefaultStepImplDir() {
        return getAbsolutePath(DEFAULT_SRC_DIR).toString();
    }
    private static List<String> getDefaultStepImplDirs() {
        List<String> dirs = new ArrayList<>();
        for (String dir : DEFAULT_SRC_DIRS) {
            Path dirAbsPath = getAbsolutePath(dir.trim());
            if (Files.exists(dirAbsPath)) {
                dirs.add(dirAbsPath.toString());
            }
        }
        return dirs;
    }
}
