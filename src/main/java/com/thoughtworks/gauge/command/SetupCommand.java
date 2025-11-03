/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SetupCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws IOException {
        String gitIgnore = """
                # Gauge - java class output directory
                gauge_bin
                """;
        String properties = """
                # Specify an alternate Java home if you want to use a custom version
                gauge_java_home =
                
                # IntelliJ and Eclipse out directory will be usually auto detected
                # Use the below property if you need to override the build path
                gauge_custom_build_path =
                
                # specify the directory where additional libs are kept
                # you can specify multiple directory names separated with a comma (,)
                gauge_additional_libs = libs/*
                
                # JVM arguments passed to java while launching. Enter multiple values separated by comma (,) eg. Xmx1024m, Xms128m
                gauge_jvm_args =
                
                # specify the directory containing java files to be compiled
                # you can specify multiple directory names separated with a comma (,)
                gauge_custom_compile_dir =
                
                # specify the level at which the objects should be cleared
                # Possible values are suite, spec and scenario. Default value is scenario.
                gauge_clear_state_level = scenario
                """;
        String implementation = """
                import com.thoughtworks.gauge.Step;
                import com.thoughtworks.gauge.Table;
                import com.thoughtworks.gauge.TableRow;
                
                import java.util.HashSet;
                
                import static org.assertj.core.api.Assertions.assertThat;
                
                public class StepImplementation {
                
                    private HashSet<Character> vowels;
                
                    @Step("Vowels in English language are <vowelString>.")
                    public void setLanguageVowels(String vowelString) {
                        vowels = new HashSet<>();
                        for (char ch : vowelString.toCharArray()) {
                            vowels.add(ch);
                        }
                    }
                
                    @Step("The word <word> has <expectedCount> vowels.")
                    public void verifyVowelsCountInWord(String word, int expectedCount) {
                        int actualCount = countVowels(word);
                        assertThat(expectedCount).isEqualTo(actualCount);
                    }
                
                    @Step("Almost all words have vowels <wordsTable>")
                    public void verifyVowelsCountInMultipleWords(Table wordsTable) {
                        for (TableRow row : wordsTable.getTableRows()) {
                            String word = row.getCell("Word");
                            int expectedCount = Integer.parseInt(row.getCell("Vowel Count"));
                            int actualCount = countVowels(word);
                
                            assertThat(expectedCount).isEqualTo(actualCount);
                        }
                    }
                
                    private int countVowels(String word) {
                        int count = 0;
                        for (char ch : word.toCharArray()) {
                            if (vowels.contains(ch)) {
                                count++;
                            }
                        }
                        return count;
                    }
                }""";

        String projectRoot = System.getenv("GAUGE_PROJECT_ROOT");

        Path implFilePath = Path.of(projectRoot, "src", "test", "java", "StepImplementation.java");
        this.writeContent(implFilePath, implementation);

        Path propFilePath = Path.of(projectRoot, "env", "default", "java.properties");
        this.writeContent(propFilePath, properties);

        Path gitIgnoreFilePath = Path.of(projectRoot, ".gitignore");
        this.writeContent(gitIgnoreFilePath, gitIgnore);

        Path libsDirPath = Path.of(projectRoot, "libs", ".gitkeep");
        this.writeContent(libsDirPath, "");
    }

    private void writeContent(Path templateFilePath, String content) throws IOException {
        Logger.info(String.format("create %s", templateFilePath.toString()));
        Files.writeString(templateFilePath, content, StandardOpenOption.APPEND);
    }

}
