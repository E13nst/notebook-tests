package api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import test.EndPoints;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthorizeSteps {

    private final RequestSpecification requestSpec;

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

        //        cookies = response.getCookies();

        return given()
                .spec(requestSpec)
                .body(new User(username, password))
                .post(EndPoints.login);
    }

    @Step(value = "User logout")
    public Response logout(Map<String, String> cookies) {

        return given()
                .spec(requestSpec)
                .cookies(cookies)
                .post(EndPoints.logout);
    }
}
