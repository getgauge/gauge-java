package com.thoughtworks.gauge;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.gauge.GaugeConstant.GAUGE_CUSTOM_COMPILE_DIR;
import static com.thoughtworks.gauge.GaugeConstant.GAUGE_PROJECT_ROOT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileHelperTest {
    @Rule
    public EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void testGetAllImplementationFiles() {
        String gaugeProjRoot = Util.workingDir().getAbsolutePath() + File.separator + String.format("src%stest%sresources%stest", File.separator, File.separator, File.separator);
        environmentVariables.set(GAUGE_CUSTOM_COMPILE_DIR, String.format("files%sformatted, files%sunformatted", File.separator, File.separator));
        environmentVariables.set(GAUGE_PROJECT_ROOT, gaugeProjRoot);

        List<String> implFiles = FileHelper.getAllImplementationFiles();
        assertEquals(3, implFiles.size());
        List<String> expectedImplFiles = Arrays.asList(String.format("%s%sfiles%sformatted%sStepImpl.java", gaugeProjRoot, File.separator, File.separator, File.separator),
                String.format("%s%sfiles%sformatted%sStepImplWithComments.java", gaugeProjRoot, File.separator, File.separator, File.separator),
                String.format("%s%sfiles%sunformatted%sUnFormattedStepImpl.java", gaugeProjRoot, File.separator, File.separator, File.separator));
        assertTrue(expectedImplFiles.containsAll(implFiles));
    }


}