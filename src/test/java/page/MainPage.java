package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Главная страница
 */
public class MainPage {

    public void createTodo(String description) {
        $x("//input").setValue(description);
        $x("//div[@class='create-form']/button").click();
    }

    public ElementsCollection deleteTodo(int index) {
        $$x("//div[@class='single-todo']/button").get(index).click();
        return $$x("//div[@class='todo-description']");
    }

    public ElementsCollection getDescriptions() {
        return $$x("//div[@class='todo-description']");
    }

    public boolean exists() {
        return $x("//div[@id='main-page']").exists();
    }


}
