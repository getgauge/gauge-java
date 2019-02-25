package test.files.formatted;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

public class StepImpl {
    @Step("Say <hello> to <world>")
    public String helloWorld(String greeting, int name) {
    }

    @Step("step <a> and a table <table>")
    public void stepWithTable(float a, Table table) {
    }

    @Step("A step with no params")
    public void someStepStep() {
    }

    @Step("Tell <greeting> to <name>")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting + ", " + name);
    }

    @Step("Tell <greeting> <name>")
    public void helloWorld(String greeting, String argName) {
    }

    @Step("† ‡ µ ¢ step with <Û> and <į>")
    public void stepWith(String a, String b) {
    }

    @Step("A step with comments")
    public void someStepWithComments() {
        //comment1
        //comment2
        /*
                    comment3
                    comment4
         */
        /*
                comment6
                    comment7
                        comment8
         */
        System.out.println("");
    }

    @Step("A step with newLine")
    public void someStepStep() {
        System.out.println("\n");
    }

    @Step("A step with \\")
    public void stepWithDualBackSlashes() {
    }

    @Step("A step with /")
    public void stepWithCommonSlash() {
    }

    @Step("A step with //")
    public void stepWithCommonDualSlashes() {
    }

    @Step("A step with |")
    public void stepWithPipeline() {
    }

    @Step("A step 123")
    public void stepWithTab() {
    }
    
    @Step("A step with ` ~ = + ? # $ % ^ ! & * ( ) : ; , . - _ [ ]")
    public void stepWithSpecialChars() {
    }

    @Step({"A step defined with alias syntax"})
    public void stepDefinedWithAliasSyntax() {
    }
}
