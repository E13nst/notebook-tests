package page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

/**
 * Страница логина
 */
public class LoginPage {

    private final SelenideElement inputUsername = $x("//input[@id='username']");
    private final SelenideElement inputPassword = $x("//input[@id='password']");
    private final SelenideElement button = $x("//button");

    public LoginPage(String url) {
        Selenide.open(url);
    }

    public void login(String username, String password) {
        inputUsername.setValue(username);
        inputPassword.setValue(password);
        button.click();
    }
}
