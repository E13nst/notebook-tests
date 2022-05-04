package test;

import api.Todo;
import api.TodoSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class ApiDeleteTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps todoUser2Steps;
    private static TodoSteps unauthorizedSteps;

    @BeforeAll
    public static void setup() {
        todoUser1Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_1, PASSWORD).getCookies());
        todoUser2Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_2, PASSWORD).getCookies());
        unauthorizedSteps = new TodoSteps(HOST, PORT, new HashMap<>());
    }

    @BeforeEach
    public void start(){
        int count = 3;
        for (int i = 0; i < count; i++) {
            todoUser1Steps.createTodo(new Todo().randomDescription());
        }
    }

    @AfterEach
    public void finish() {
        todoUser1Steps.removeAllTodo();
        todoUser2Steps.removeAllTodo();
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Удаление записи")
    @Story(value = "Delete todo")
    @Test
    public void testDeleteTodo() {

        List<Todo> list = todoUser1Steps.getAllTodoList();
        Todo todo = list.get(0);

        Response response = todoUser1Steps.removeTodo(todo.getId());

        response
                .then().assertThat().statusCode(200)
                .body("status", equalTo("SUCCESS"));

        List<Todo> todoList = todoUser1Steps.getAllTodoList();

        Assertions.assertEquals(list.size() - 1, todoList.size());
        Assertions.assertFalse(todoList.contains(todo));
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка удаления без авторизации")
    @Story(value = "Delete todo without authorization")
    @Test
    public void testDeleteTodoUnauthorized() {

        List<Todo> list = todoUser1Steps.getAllTodoList();
        Todo todo = list.get(0);

        Response response = unauthorizedSteps.removeTodo(todo.getId());
        // Todo Здесь не разобрался - при выполнении create запроса без cookie получаю код 302 вместо 401
        response.then().assertThat().statusCode(401);
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Повторное удаление записи, которая уже была удалена")
    @Story(value = "Delete nonexistent todo")
    @Test
    public void testDeleteNonexistentTodo() {

        List<Todo> list = todoUser1Steps.getAllTodoList();
        Todo todo = list.get(0);

        todoUser1Steps.removeTodo(todo.getId())
                .then().assertThat().statusCode(200)
                .body("status", equalTo("SUCCESS"));

        todoUser1Steps.removeTodo(todo.getId())
                .then().assertThat().statusCode(401);
    }
}
