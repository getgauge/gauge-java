/*----------------------------------------------------------------
 *  Copyright (c) ThoughtWorks, Inc.
 *  Licensed under the Apache License, Version 2.0
 *  See LICENSE.txt in the project root for license information.
 *----------------------------------------------------------------*/
package com.thoughtworks.gauge;

public class TestStepImplClass {
    @Step("hello world")
    public void helloWorld() {

    }

    @Step("hello world <param0>")
    public int helloWorldWithOneParam(int i) {
        return 0;
    }

    @Step("a step with <param0> and <table>")
    public Table helloWorldWithTwoParams(String a, Table table) {
        return null;
    }

    @Step({"first step name with name <a>", "second step name with <b>"})
    public Table aliasMethod(String a) {
        return null;
    }
}
