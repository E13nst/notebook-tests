package test;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import page.LoginPage;
import page.MainPage;

import static com.codeborne.selenide.CollectionCondition.size;
import static common.CommonUtils.randomString;

public class UiTodoTest extends BaseUiTest {

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "UI test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Создание нескольких записей и удаление последней")
    @Story(value = "Create todo")
    @Test
    public void testUiCreateSomeTodoAndDeleteLast() {

        int count = 8;
        int length = 20;

        LoginPage loginPage = new LoginPage(BASE_URL);
        loginPage.login(USERNAME_1, PASSWORD);

        MainPage mainPage = new MainPage();

        for (int i = 0; i < count; i++) {
            mainPage.createTodo(randomString(length));
        }

        ElementsCollection descriptionCollection = mainPage.getDescriptions();
        descriptionCollection.shouldHave(size(count));

        mainPage.deleteTodo(count - 1).shouldHave(size(count - 1));
    }
}
