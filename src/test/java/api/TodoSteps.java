package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class TodoSteps {

    private final RequestSpecification requestSpec;

    private final Map<String, String> cookies;

    public TodoSteps(String host, int port, Map<String, String> cookies) {

        this.cookies = cookies;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(host)
                .setPort(port)
                .setAccept(ContentType.ANY)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    @Step(value = "Create new todo ang get result")
    public Response createTodo(Todo todo) {

        return given()
                .spec(requestSpec)
                .body(todo)
                .cookies(cookies)
                .post(EndPoints.create);
    }

    @Step(value = "Remove todo ang get response")
    public Response removeTodo(int id) {

        Todo todo = new Todo(id);

        return given()
                .spec(requestSpec)
                .body(todo)
                .cookies(cookies)
                .post(EndPoints.remove);
    }

    @Step(value = "Get all todo")
    public Response getAllTodo() {

        return given()
                .spec(requestSpec)
                .cookies(cookies)
                .get(EndPoints.getAll);
    }

    @Step(value = "Get all todo list")
    public List<Todo> getAllTodoList() {

        Response response = getAllTodo();
        response.then().assertThat().statusCode(200);
        return response.jsonPath().getList("todoList", Todo.class);
    }

    @Step(value = "Get all todo list size")
    public int getAllTodoListSize() {

        int size = getAllTodoList().size();
        Allure.addAttachment("List size", String.valueOf(size));
        return size;
    }

    @Step(value = "Create {} todos")
    public List<Todo> createTodos(int count) {

        List<Todo> todoList = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            Response response = createTodo(new Todo().randomDescription());
            Todo todo = response.jsonPath().getObject("todo", Todo.class);
            todoList.add(todo);
        }

        return todoList;
    }

    @Step(value = "Remove all todo")
    public void removeAllTodo() {

        getAllTodoList().forEach(t -> removeTodo(t.getId()));
    }
}
