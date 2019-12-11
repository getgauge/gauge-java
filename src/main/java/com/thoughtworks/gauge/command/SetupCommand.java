package com.thoughtworks.gauge.command;

import com.thoughtworks.gauge.Logger;
import org.apache.commons.io.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetupCommand implements GaugeJavaCommand {

    @Override
    public void execute() throws IOException {
        String gitIgnore = "# Gauge - java class output directory\n"
                + "gauge_bin\n";
        String properties = "# Specify an alternate Java home if you want to use a custom version\n"
                + "gauge_java_home =\n"
                + "\n"
                + "# IntelliJ and Eclipse out directory will be usually auto detected\n"
                + "# Use the below property if you need to override the build path\n"
                + "gauge_custom_build_path =\n"
                + "\n"
                + "# specify the directory where additional libs are kept\n"
                + "# you can specify multiple directory names separated with a comma (,)\n"
                + "gauge_additional_libs = libs/*\n"
                + "\n"
                + "# JVM arguments passed to java while launching. Enter multiple values separated by comma (,) eg. Xmx1024m, Xms128m\n"
                + "gauge_jvm_args =\n"
                + "\n"
                + "# specify the directory containing java files to be compiled\n"
                + "# you can specify multiple directory names separated with a comma (,)\n"
                + "gauge_custom_compile_dir =\n"
                + "\n"
                + "# specify the level at which the objects should be cleared\n"
                + "# Possible values are suite, spec and scenario. Default value is scenario.\n"
                + "gauge_clear_state_level = scenario\n";
        String implementation = "import com.thoughtworks.gauge.Step;\n"
                + "import com.thoughtworks.gauge.Table;\n"
                + "import com.thoughtworks.gauge.TableRow;\n"
                + "\n"
                + "import java.util.HashSet;\n"
                + "\n"
                + "import static org.assertj.core.api.Assertions.assertThat;\n"
                + "\n"
                + "public class StepImplementation {\n"
                + "\n"
                + "    private HashSet<Character> vowels;\n"
                + "\n"
                + "    @Step(\"Vowels in English language are <vowelString>.\")\n"
                + "    public void setLanguageVowels(String vowelString) {\n"
                + "        vowels = new HashSet<>();\n"
                + "        for (char ch : vowelString.toCharArray()) {\n"
                + "            vowels.add(ch);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    @Step(\"The word <word> has <expectedCount> vowels.\")\n"
                + "    public void verifyVowelsCountInWord(String word, int expectedCount) {\n"
                + "        int actualCount = countVowels(word);\n"
                + "        assertThat(expectedCount).isEqualTo(actualCount);\n"
                + "    }\n"
                + "\n"
                + "    @Step(\"Almost all words have vowels <wordsTable>\")\n"
                + "    public void verifyVowelsCountInMultipleWords(Table wordsTable) {\n"
                + "        for (TableRow row : wordsTable.getTableRows()) {\n"
                + "            String word = row.getCell(\"Word\");\n"
                + "            int expectedCount = Integer.parseInt(row.getCell(\"Vowel Count\"));\n"
                + "            int actualCount = countVowels(word);\n"
                + "\n"
                + "            assertThat(expectedCount).isEqualTo(actualCount);\n"
                + "        }\n"
                + "    }\n"
                + "\n"
                + "    private int countVowels(String word) {\n"
                + "        int count = 0;\n"
                + "        for (char ch : word.toCharArray()) {\n"
                + "            if (vowels.contains(ch)) {\n"
                + "                count++;\n"
                + "            }\n"
                + "        }\n"
                + "        return count;\n"
                + "    }\n"
                + "}";

        String projectRoot = System.getenv("GAUGE_PROJECT_ROOT");

        Path implFilePath = Paths.get(projectRoot, "src", "test", "java", "StepImplementation.java");
        Logger.info(String.format("create %s", implFilePath.toString()));
        this.writeContent(implFilePath, implementation);

        Path propFilePath = Paths.get(projectRoot, "env", "default", "java.properties");
        Logger.info(String.format("create %s", propFilePath.toString()));
        this.writeContent(propFilePath, properties);

        Path gitIgnoreFilePath = Paths.get(projectRoot, ".gitignore");
        Logger.info(String.format("create %s", gitIgnoreFilePath.toString()));
        this.writeContent(gitIgnoreFilePath, gitIgnore);

        Path libsDirPath = Paths.get(projectRoot, "libs", ".gitkeep");
        Logger.info(String.format("create %s", libsDirPath.toString()));
        this.writeContent(libsDirPath, "");
    }

    private void writeContent(Path implFilePath, String content) throws IOException {
        FileOutputStream fileOutputStream = FileUtils.openOutputStream(implFilePath.toFile(), true);
        fileOutputStream.write(content.getBytes());
    }

}
