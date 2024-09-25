/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import java.io.IOException;
import java.nio.file.Files;

public class FileModifier {
    private final JavaRefactoringElement javaElement;

    public FileModifier(JavaRefactoringElement javaElement) {
        this.javaElement = javaElement;
    }

    public void refactor() throws IOException {
        write();
    }

    private void write() throws IOException {
        Files.writeString(javaElement.getFile().toPath(), javaElement.getText(), JavaParseWorker.CHARSET);
    }

}
