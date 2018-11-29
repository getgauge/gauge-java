import com.thoughtworks.gauge.Step;
import static org.assertj.core.api.Assertions.assertThat;

public class StepTest {

    @Step("new step")
    public void newstep() {
        assertThat(2).isEqualTo(2);
    }
}