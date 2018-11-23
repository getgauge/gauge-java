// Copyright 2015 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package com.thoughtworks.gauge.connection;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.google.protobuf.ProtocolStringList;
import com.thoughtworks.gauge.FileHelper;
import gauge.messages.Messages;
import gauge.messages.Spec;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StubImplementationCodeProcessor implements com.thoughtworks.gauge.processor.IMessageProcessor {
    @Override
    public Messages.Message process(Messages.Message message) {
        ProtocolStringList stubs = message.getStubImplementationCodeRequest().getCodesList();
        String filePath = message.getStubImplementationCodeRequest().getImplementationFilePath();


        File file = new File(filePath);
        Messages.FileDiff fileDiff;

        if (file.exists()) {
            fileDiff = implementInExistingFile(stubs, file);
        } else {
            File fileName = FileHelper.getFileName("", 0);
            fileDiff = implementInNewClass(stubs, fileName);
        }

        return Messages.Message.newBuilder()
                .setMessageId(message.getMessageId())
                .setFileDiff(fileDiff)
                .setMessageType(Messages.Message.MessageType.StubImplementationCodeRequest)
                .build();
    }


    private Messages.FileDiff implementInExistingFile(ProtocolStringList stubs, File file) {
        try {
            if (new FileReader(file).read() != -1) {
                return implementInExistingClass(stubs, file);
            }
            return implementInNewClass(stubs, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Messages.FileDiff implementInNewClass(ProtocolStringList stubs, File file) {
        String className = FileHelper.getClassName(file);
        String contents = getNewClassContents(className, stubs);
        Spec.Span.Builder span = Spec.Span.newBuilder()
                .setStart(0)
                .setStartChar(0)
                .setEnd(0)
                .setEndChar(0);
        Messages.TextDiff textDiff = Messages.TextDiff.newBuilder().setSpan(span).setContent(contents).build();
        return Messages.FileDiff.newBuilder().setFilePath(file.toString()).addTextDiffs(textDiff).build();
    }

    private String getNewClassContents(String className, ProtocolStringList stubs) {
        return  "import com.thoughtworks.gauge.Step;\n\n"
                + "public class "
                + className
                + " {\n"
                + String.join("\n", stubs) + "\n"
                + "}\n";
    }

    private Messages.FileDiff implementInExistingClass(ProtocolStringList stubs, File file) {
        try {
            CompilationUnit compilationUnit = JavaParser.parse(file);
            Range range = compilationUnit.getRange();
            int lastLine = range.end.line - 1;
            int column = range.end.column - 1;
            String contents = String.join("\n", stubs) + "\n";
            Spec.Span.Builder span = Spec.Span.newBuilder()
                    .setStart(lastLine)
                    .setStartChar(column)
                    .setEnd(lastLine)
                    .setEndChar(column);
            Messages.TextDiff textDiff = Messages.TextDiff.newBuilder().setSpan(span).setContent(contents).build();
            return Messages.FileDiff.newBuilder().setFilePath(file.toString()).addTextDiffs(textDiff).build();

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
