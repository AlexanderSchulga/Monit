package testHideMonit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTestHide {

    // @BeforeEach//предусловие
    public void setUp(){
        System.out.println("Скрываю в трей");
    }



    // @AfterEach//постусловие
    public void tearDown(){
        System.out.println("Тест прошел, приложение закрывается");
    }
}
