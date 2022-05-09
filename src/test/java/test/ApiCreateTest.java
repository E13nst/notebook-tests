package test;

import api.Todo;
import api.TodoSteps;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static common.CommonUtils.randomString;
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

    public static Stream<String> argsProviderFactory() {
        return Stream.of(
                "English text description",
                "Текстовое описание на русском языке",
                "英文文字說明",
                "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his\n" +
                        " bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head\n" +
                        " a little he could see...",
                "FOO’); DROP TABLE USERS"
        );
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Создание одной новой записи и проверка корректности результата в списка")
    @Story(value = "Create one new todo")
    @ParameterizedTest()
    @MethodSource("argsProviderFactory")
    public void testCreateTodo(String description) {

        Allure.addAttachment("description", description != null ? description : "null");

        int sizeBefore = todoUser1Steps.getAllTodoListSize();
        Response response = todoUser1Steps.createTodo(Todo.builder().description(description).build());

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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка создать запись неавторизированным пользователем")
    @Story(value = "Create todo without authorization")
    @Test
    public void testCreateTodoUnauthorized() {

        Todo todo = Todo.builder().description(randomString(20)).build();

        int sizeBefore = todoUser1Steps.getAllTodoListSize();

        Response response = unauthorizedSteps.createTodo(todo);
        // Todo При выполнении create запроса без cookie получаю код 302 вместо ожидаемого 401, возможно я что-то упустил
        response.then().assertThat().statusCode(401);

        Assertions.assertEquals(sizeBefore, todoUser1Steps.getAllTodoListSize());
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Create test")
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Попытка создать пустую запись и create без поля description")
    @Story(value = "Create todo without description and empty field")
    @NullAndEmptySource
    @ParameterizedTest
    public void testCreateWithoutDescriptionField(String description) {

        Todo todo = Todo.builder().description(description).build();

        int sizeBefore = todoUser1Steps.getAllTodoListSize();

        // Todo Здесь тоже возвращается код 500, ожидал увидеть 403
        Response response = todoUser1Steps.createTodo(todo);
        response.then().assertThat().statusCode(403);

        Assertions.assertEquals(sizeBefore, todoUser1Steps.getAllTodoListSize());
    }
}
