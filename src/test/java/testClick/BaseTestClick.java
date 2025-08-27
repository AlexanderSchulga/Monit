package testClick;

import com.example.monit.OpenForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTestClick {

    //@BeforeEach//предусловие
    public void setUp(){

        System.out.println("Начало теста с открытием меню");
    }

    // @AfterEach//постусловие
    public void tearDown(){
        System.out.println("Вызвал меню");
    }
}
