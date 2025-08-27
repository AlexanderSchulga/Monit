package testHideMonit;

import com.example.monit.OpenForm;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class TestHide extends BaseTestHide{

    private OpenForm op;

    public TestHide(OpenForm openForm) {
        this.op = openForm;
    }

    @Test
    public void testHide(){

        setUp();
        try {
            Robot robot = new Robot();
            robot.delay(2000); // Даем время для полной инициализации окна

            // Получаем координаты пункта меню
            SwingUtilities.invokeAndWait(() -> {

                Component window = op.window;
                if (((OpenForm) window).contextMenu != null && ((OpenForm) window).contextMenu.isShowing()) {
                    // Находим пункт "Свернуть"
                    for (Component comp : ((OpenForm) window).contextMenu.getComponents()) {
                        if (comp instanceof JMenuItem) {
                            JMenuItem item = (JMenuItem) comp;
                            if ("Свернуть".equals(item.getText())) {
                                // Получаем координаты пункта меню на экране
                                Point itemLocation = item.getLocationOnScreen();
                                int itemCenterX = itemLocation.x + item.getWidth() / 2;
                                int itemCenterY = itemLocation.y + item.getHeight() / 2;

                                // Кликаем на пункт меню
                                robot.mouseMove(itemCenterX, itemCenterY);
                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                break;
                            }
                        }
                    }
                }
            });

            robot.delay(1000); // Ждем реакции

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            tearDown(); // Гарантированный вызов в finally
        }

    }

}
