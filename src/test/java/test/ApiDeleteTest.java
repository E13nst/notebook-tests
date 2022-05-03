package test;

import api.TodoSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import api.Todo;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class ApiDeleteTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps todoUser2Steps;

    @BeforeAll
    public static void setup() {
        todoUser1Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_1, PASSWORD).getCookies());
        todoUser2Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_2, PASSWORD).getCookies());
    }

    @AfterAll
    public static void teardown() {

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
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "")
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
    @Feature(value = "Create test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("")
    @Story(value = "Create todo without authorization")
    @Test
    public void testCreateTodoUnauthorized() {

        Todo todo = new Todo().randomDescription();

        int sizeBefore = todoUser1Steps.getAllTodoListSize();

        Response response = todoUser1Steps.createTodo(todo);
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
