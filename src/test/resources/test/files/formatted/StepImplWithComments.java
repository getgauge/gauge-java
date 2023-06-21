package test.files.formatted;

import com.thoughtworks.gauge.Step;

public class StepImpl {
    @Step("A step with comments")
    public void someStepWithComments() {
        // comment1
        // comment2
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
        // comment9
        // comment10
        /*
                    comment11
                    comment12
         */
        /*
                comment13
                    comment14
                        comment15
         */
    }
}