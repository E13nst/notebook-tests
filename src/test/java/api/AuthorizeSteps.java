package api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class AuthorizeSteps {

    private final RequestSpecification requestSpec;

    private Properties getUserProperties(String username, String password) {
        Properties properties = new Properties();
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        return properties;
    }

    public AuthorizeSteps(String host, int port) {

        this.requestSpec = new RequestSpecBuilder()
                .setBaseUri(host)
                .setPort(port)
                .setAccept(ContentType.ANY)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    @Step(value = "User login")
    public Response login(String username, String password) {

        return given()
                .spec(requestSpec)
                .body(getUserProperties(username, password))
                .post(EndPoints.login);
    }

    @Step(value = "User logout")
    public Response logout(Map<String, String> cookies) {

        return given()
                .spec(requestSpec)
                .cookies(cookies)
                .post(EndPoints.logout);
    }

    @Step(value = "Get cookies")
    public Map<String, String> getCookies(String username, String password) {

        return given()
                .spec(requestSpec)
                .body(getUserProperties(username, password))
                .post(EndPoints.login)
                .getCookies();
    }
}
