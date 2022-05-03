package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница логина
 */
public class MainPage {

    private final SelenideElement input = $x("//input");
    private final SelenideElement createButton = $x("//div[@class='create-form']/button");

    private final ElementsCollection deleteButtonCollection = $$x("//div[@class='single-todo']/button");
    private final ElementsCollection descriptionCollection = $$x("//div[@class='todo-description']");

    public void createTodo(String description) {
        input.setValue(description);
        createButton.click();
    }

    public ElementsCollection deleteTodo(int index) {
        deleteButtonCollection.get(index).click();
        return descriptionCollection;
    }

    public ElementsCollection getDescriptions() {
        return descriptionCollection;
    }


}
