package test;

//import com.sun.net.httpserver.Request;

import api.AuthorizeSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class ApiLoginTest extends BaseTest {

    @BeforeEach
    public void start(){

    }

    @AfterEach
    public void finish() {

    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Tests for task 6")
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
    @Feature(value = "Tests for task 6")
    @Severity(SeverityLevel.BLOCKER)
    @Description("In this test we will login with correct credentials then log out")
    @Story(value = "Test for logout with correct credentials")
    @Test
    public void testLogout(){

        Response response = authorizeSteps.logout(authorizeSteps.login(USERNAME_1, PASSWORD).cookies());

        response.then().assertThat().statusCode(200)
                .body("status", equalTo("SUCCESS"));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Tests for task 6")
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "Test for logout with incorrect credentials")
    @Test
    public void testLogoutWithBadPassword() {

        Response response = authorizeSteps.login(USERNAME_1, BAD_PASSWORD);

        response.then()
                .assertThat()
                .statusCode(401);

        Assertions.assertNotNull(response.getCookie("Authorization"));
        Assertions.assertEquals("unset", response.getCookie("Authorization"));
    }
}
