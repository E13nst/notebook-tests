package test;

import api.TodoSteps;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.handler.codec.http.cookie.Cookie;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;

public abstract class BaseUiTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps todoUser2Steps;

    @BeforeAll
    public static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

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
