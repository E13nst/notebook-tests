package test;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.Matchers.equalTo;

public class ApiLoginTest extends BaseTest {

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Login test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("In this test we will login with correct credentials.")
    @Story(value = "Test for login with correct credentials")
    @Test
    public void testUserLogin() {

        Response response = authorizeSteps.login(USERNAME_1, PASSWORD);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("SUCCESS"));

        Assertions.assertNotNull(response.getCookie("Authorization"));
        Assertions.assertNotEquals("unset", response.getCookie("Authorization"));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Login test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("In this test we will login with correct credentials then log out")
    @Story(value = "Test for logout with correct credentials")
    @Test
    public void testLogout(){

        Response response = authorizeSteps.logout(authorizeSteps.login(USERNAME_1, PASSWORD).cookies());

        response.then().assertThat().statusCode(200)
                .body("status", equalTo("SUCCESS"));

        Assertions.assertNotNull(response.getCookie("Authorization"));
        Assertions.assertEquals("unset", response.getCookie("Authorization"));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Login test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка залогиниться с неправильным, пустым и отсутствующим полем пароля")
    @Story(value = "Login with unregistered empty and null password")
    @NullAndEmptySource
    @ValueSource(strings = { USERNAME_UNREGISTERED })
    @ParameterizedTest
    public void testLoginWithBadPassword(String password) {

        Response response = authorizeSteps.login(USERNAME_1, password);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("FAILED"));

        Assertions.assertNull(response.getCookie("Authorization"));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Login test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка залогиниться с неправильным, пустым и отсутствующим полем имени")
    @Story(value = "Login with unregistered empty and null username")
    @NullAndEmptySource
    @ValueSource(strings = { USERNAME_UNREGISTERED })
    @ParameterizedTest
    public void testLoginWithBadUsername(String username) {

        Response response = authorizeSteps.login(username, PASSWORD);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("FAILED"));

        Assertions.assertNull(response.getCookie("Authorization"));
    }
}
