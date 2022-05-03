package test;

import api.TodoSteps;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseUiTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps todoUser2Steps;

    @BeforeAll
    public static void setup() {
        todoUser1Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_1, PASSWORD).getCookies());
        todoUser2Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_2, PASSWORD).getCookies());
    }

    @AfterAll
    public static void teardown() {
        todoUser1Steps.removeAllTodo();
        todoUser2Steps.removeAllTodo();
    }

    @BeforeEach
    public void start(){
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;

        todoUser1Steps.removeAllTodo();
        todoUser2Steps.removeAllTodo();
    }

    @AfterEach
    public void finish() {
        Selenide.closeWebDriver();
    }
}
