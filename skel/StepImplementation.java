import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import java.lang.Object;
import java.lang.String;
import java.util.List;

public class StepImplementation {
    @Step("Say <greeting> to <product name>")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting + ", " + name);
    }

    @Step("Step that takes a table <table>")
    public void stepWithTable(Table table) {
        for (String columns : table.getColumnNames()) {
            System.out.println(columns);
        }

        for (List<String> rows : table.getRows()) {
            System.out.println(rows);
        }
    }

    @Step("A context step which gets executed before every scenario")
    public void contextStep() {
    }
}

