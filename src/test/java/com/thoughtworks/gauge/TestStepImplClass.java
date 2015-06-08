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

package com.thoughtworks.gauge;

public class TestStepImplClass {
    @Step("hello world")
    public void helloWorld() {

    }

    @Step("hello world <param0>")
    public int helloWorld(int i) {
        return 0;
    }

    @Step("a step with <param0> and <table>")
    public Table helloWorld(String a, Table table) {
        return null;
    }

    @Step({"first step name with name <a>", "second step name with <b>"})
    public Table aliasMethod(String a) {
        return null;
    }
}
