package com.thoughtworks.gauge;

class TestImplClass {
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
