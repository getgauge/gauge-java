import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

public class StepImplementation {
    @Step("Say <greeting> to <product name>")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting + ", " + name);
    }

    @Step("Step that takes a table <table>")
    public void stepWithTable(Table table) {
    }

    @Step("A context step which gets executed before every scenario")
    public void contextStep() {
    }
}

