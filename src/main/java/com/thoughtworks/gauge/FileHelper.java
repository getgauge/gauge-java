package com.thoughtworks.gauge;

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
}
