/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static uk.org.webcompere.systemstubs.SystemStubs.*;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.gauge.GaugeConstant.GAUGE_CUSTOM_COMPILE_DIR;
import static com.thoughtworks.gauge.GaugeConstant.GAUGE_PROJECT_ROOT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@ExtendWith(SystemStubsExtension.class)
public class FileHelperTest {   

    String gaugeProjRoot = Util.workingDir().getAbsolutePath() + File.separator + String.format("src%stest%sresources%stest", File.separator, File.separator, File.separator);

    @Test
    public void testGetAllImplementationFiles() throws Exception {
        String gaugeProjRoot = Util.workingDir().getAbsolutePath() + File.separator + String.format("src%stest%sresources%stest", File.separator, File.separator, File.separator);

        List<String> implFiles = withEnvironmentVariable(GAUGE_CUSTOM_COMPILE_DIR, String.format("files%sformatted, files%sunformatted", File.separator, File.separator))
            .and(GAUGE_PROJECT_ROOT, gaugeProjRoot)
            .execute(() -> FileHelper.getAllImplementationFiles());
        assertEquals(3, implFiles.size());
        List<String> expectedImplFiles = Arrays.asList(String.format("%s%sfiles%sformatted%sStepImpl.java", gaugeProjRoot, File.separator, File.separator, File.separator),
                String.format("%s%sfiles%sformatted%sStepImplWithComments.java", gaugeProjRoot, File.separator, File.separator, File.separator),
                String.format("%s%sfiles%sunformatted%sUnFormattedStepImpl.java", gaugeProjRoot, File.separator, File.separator, File.separator));
        assertTrue(expectedImplFiles.containsAll(implFiles));
    }


    @Test
    public void testGetStepImplDirsWhenCustomCompileDirEnvIsSet() throws Exception {
        String gaugeProjRoot = Util.workingDir().getAbsolutePath();

        List<String> stepImplDirs = withEnvironmentVariable(GAUGE_CUSTOM_COMPILE_DIR, String.format("files%sformatted, files%sunformatted", File.separator, File.separator))
            .and(GAUGE_PROJECT_ROOT, gaugeProjRoot)
            .execute(() -> FileHelper.getStepImplDirs());

        assertEquals(2, stepImplDirs.size());
        List<String> expectedImplDirs = Arrays.asList(String.format("%s%sfiles%sformatted", gaugeProjRoot, File.separator, File.separator),
            String.format("%s%sfiles%sunformatted", gaugeProjRoot, File.separator, File.separator)
        );
        assertTrue(expectedImplDirs.containsAll(stepImplDirs));
    }

    @Test
    public void testGetStepImplDirsWhenDefaultImplDirsDoesNotExists() throws Exception {
        String gaugeProjRoot = Util.workingDir().getAbsolutePath() + File.separator + String.format("src%stest%sresources%stest", File.separator, File.separator, File.separator);

        List<String> stepImplDirs = withEnvironmentVariable(GAUGE_PROJECT_ROOT, gaugeProjRoot)
            .execute(() -> FileHelper.getStepImplDirs());

        assertEquals(0, stepImplDirs.size());
    }

    @Test
    public void testGetStepImplDirs() throws Exception {
        String gaugeProjRoot = Util.workingDir().getAbsolutePath();

        List<String> stepImplDirs = withEnvironmentVariable(GAUGE_PROJECT_ROOT, gaugeProjRoot)
            .execute(() -> FileHelper.getStepImplDirs());

        assertEquals(2, stepImplDirs.size());
        List<String> expectedImplDirs = Arrays.asList(
            String.format("%s%ssrc%smain%sjava", gaugeProjRoot, File.separator, File.separator, File.separator),
            String.format("%s%ssrc%stest%sjava", gaugeProjRoot, File.separator, File.separator, File.separator)
        );
        assertTrue(expectedImplDirs.containsAll(stepImplDirs));
    }
}