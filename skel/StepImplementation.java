import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

public class StepImplementation {
    @Step("Say <greeting> to <product name>")
    public void helloWorld(String greeting, String name) {
        System.out.println(greeting + ", " + name);
    }

    @Step("Step that takes a table <table>")
    public void stepWithTable(Table table) {
        System.out.println(table.getColumnNames());

        for (TableRow tableRow : table.getTableRows()) {
            System.out.println(tableRow.getCell("Product") + " " + tableRow.getCell("Description"));
        }
    }

    @Step("A context step which gets executed before every scenario")
    public void contextStep() {
    }
}
