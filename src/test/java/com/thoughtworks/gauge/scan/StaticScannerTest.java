/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.scan;

import com.google.common.base.Charsets;
import com.thoughtworks.gauge.Util;
import com.thoughtworks.gauge.registry.StepRegistry;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StaticScannerTest {

    private static final String STEP_TEXT = "new step";
    private static final String IMPL_FILE = Util.workingDir() + File.separator + String.format("src%stest%sresources%stest%sfiles%sfoo.java", File.separator, File.separator, File.separator, File.separator, File.separator);

    @Test
    public void ShouldAddStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        String contents = staticScanner.readFile(IMPL_FILE, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(IMPL_FILE, contents);
        StepRegistry registry = staticScanner.getRegistry();
        assertTrue(registry.contains(STEP_TEXT));
    }

    @Test
    public void ShouldAddStepsWithConcatenatedAnnotationStep() {
        String contents = "public class StepTest {\n" +
                "   @Step(\"This is \" + \"a step\")\n" +
                "       public void newstep() {\n" +
                "       assertThat(2).isEqualTo(2);\n" +
                "   }\n" +
                "}";
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsFromFileContents(IMPL_FILE, contents);
        StepRegistry registry = staticScanner.getRegistry();
        assertTrue(registry.contains("This is a step"));
    }

    @Test
    public void ShouldAddStepsAliases() {
        String contents ="package foo;"+
                "public class StepTest {\n" +
                "   @Step({\"This is \" + \"a step\", \"new step\"})\n" +
                "       public void newstep() {\n" +
                "       assertThat(2).isEqualTo(2);\n" +
                "   }\n" +
                "}";
        StaticScanner staticScanner = new StaticScanner();
        staticScanner.addStepsFromFileContents(IMPL_FILE, contents);
        StepRegistry registry = staticScanner.getRegistry();
        assertTrue(registry.contains("This is a step"));
        assertTrue(registry.contains("new step"));
        assertTrue(registry.get("This is a step").getAliases().contains("new step"));
        assertTrue(registry.get("new step").getAliases().contains("This is a step"));
    }

    @Test
    public void ShouldRemoveStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        String contents = staticScanner.readFile(IMPL_FILE, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(IMPL_FILE, contents);
        StepRegistry registry = staticScanner.getRegistry();

        assertTrue(registry.contains(STEP_TEXT));
        registry.removeSteps(IMPL_FILE);
        assertFalse(registry.contains(STEP_TEXT));
    }

    @Test
    public void ShouldReloadStepsFromFile() {
        StaticScanner staticScanner = new StaticScanner();
        String contents = staticScanner.readFile(IMPL_FILE, Charsets.UTF_8);
        staticScanner.addStepsFromFileContents(IMPL_FILE, contents);
        StepRegistry registry = staticScanner.getRegistry();
        registry.get(STEP_TEXT).setHasAlias(true);

        assertTrue(registry.contains(STEP_TEXT));
        assertTrue(registry.get(STEP_TEXT).getHasAlias());

        staticScanner.reloadSteps(IMPL_FILE, contents);

        assertTrue(registry.contains(STEP_TEXT));
        assertFalse(registry.get(STEP_TEXT).getHasAlias());
    }
}
