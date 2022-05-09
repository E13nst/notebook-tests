package page;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница логина
 */
public class LoginPage {

    public LoginPage(String url) {
        Selenide.open(url);
    }

    public void login(String username, String password) {
        $x("//input[@id='username']").setValue(username);
        $x("//input[@id='password']").setValue(password);
        $x("//button").click();
    }
}
