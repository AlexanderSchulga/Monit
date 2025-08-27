package testClick;

import com.example.monit.OpenForm;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.event.InputEvent;
import javax.swing.*;


public class TestClick extends BaseTestClick {

    private OpenForm op;

    public TestClick(OpenForm openForm) {
        this.op = openForm;
    }

    @Test
    public void testClick() {

        setUp();

        try {
            Robot robot = new Robot();
            robot.delay(2000); // ���� ����� ��� ������ ������������� ����

            // ���� ���� Swing ��������� ���������� �������� GUI
            SwingUtilities.invokeAndWait(() -> {
                // �������� ��������� ����
                Component window = op.window;

                // �������� ���������� ���� �� ������
                Point windowLocation = window.getLocationOnScreen();
                int centerX = windowLocation.x + window.getWidth() / 2;
                int centerY = windowLocation.y + window.getHeight() / 2;

                // ������� � ����� ����
                robot.mouseMove(centerX, centerY);
                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            });

            robot.delay(1000); // ���� �������

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tearDown(); // ��������������� ����� � finally
        }
    }
}
