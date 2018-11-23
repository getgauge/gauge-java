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
