/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge.refactor;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FileModifier {
    private JavaRefactoringElement javaElement;

    public FileModifier(JavaRefactoringElement javaElement) {
        this.javaElement = javaElement;
    }

    public void refactor() throws IOException {
        write();
    }

    private void write() throws IOException {
        FileUtils.write(javaElement.getFile(), javaElement.getText(), JavaParseWorker.ENCODING);
    }

}
