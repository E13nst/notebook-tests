package test;

import api.Todo;
import api.TodoSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class ApiCreateTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps unauthorizedSteps;

    @BeforeAll
    public static void setup() {
        todoUser1Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_1, PASSWORD).getCookies());
        unauthorizedSteps = new TodoSteps(HOST, PORT, new HashMap<>());
    }

    @AfterAll
    public static void teardown() {
        todoUser1Steps.removeAllTodo();
    }


    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Create test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "Create todo")
    @Test
    public void testCreateTodo() {

        String description = "Test description";

        int sizeBefore = todoUser1Steps.getAllTodoListSize();
        Response response = todoUser1Steps.createTodo(new Todo(description));

        response
                .then().assertThat().statusCode(200)
                .body("status", equalTo("SUCCESS"))
                .body("todo.description", equalTo(description));

        Todo todo = response.jsonPath().getObject("todo", Todo.class);

        List<Todo> todoList = todoUser1Steps.getAllTodoList();

        Assertions.assertEquals(++sizeBefore, todoList.size());
        Assertions.assertTrue(todoList.contains(todo));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Create test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "Create todo without authorization")
    @Test
    public void testCreateTodoUnauthorized() {

        Todo todo = new Todo().randomDescription();

        int sizeBefore = todoUser1Steps.getAllTodoListSize();

        Response response = unauthorizedSteps.createTodo(todo);
        // Todo Здесь не разобрался - при выполнении create запроса без cookie получаю код 302 вместо 401
        response.then().assertThat().statusCode(401);

        Assertions.assertEquals(sizeBefore, todoUser1Steps.getAllTodoListSize());
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Create test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "Create todo without description field")
    @Test
    public void testCreateWithoutDescriptionField() {

        Todo todo = new Todo();

        int sizeBefore = todoUser1Steps.getAllTodoListSize();

        // Todo Здесь тоже возвращается код 500, ожидал увидеть 403
        Response response = todoUser1Steps.createTodo(todo);
        response.then().assertThat().statusCode(403);

        Assertions.assertEquals(sizeBefore, todoUser1Steps.getAllTodoListSize());
    }

}
