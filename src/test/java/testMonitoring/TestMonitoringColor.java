package testMonitoring;

import com.example.monit.OpenForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class TestMonitoringColor extends BaseTest{

    @Test
    public void testColorLabelForeground() {

        OpenForm op = new OpenForm();
        op.openFrame();
        JTextField label1 = (JTextField) op.window.getContentPane().getComponent(1);
        //Color expectedResult = Color.RED;
        Color expectedResult = Color.GREEN;
        Color actualResult = label1.getForeground();

        Assertions.assertEquals(expectedResult, actualResult);
        System.out.println("process");
    }
}

