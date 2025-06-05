package testMonitoring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    public void setUp(){
        System.out.println("до");
    }

    @AfterEach
    public void tearDown(){
        System.out.println("после");
    }
}

