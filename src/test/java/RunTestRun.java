import com.example.monit.OpenForm;
import org.junit.jupiter.api.Test;
import testClick.TestClick;
import testHideMonit.TestHide;

public class RunTestRun {

    @Test
    void runTest() {
        OpenForm op = new OpenForm();
        op.openFrame();

        TestClick cli = new TestClick(op);
        cli.testClick();

        TestHide hi = new TestHide(op);
        hi.testHide();

    }
}
