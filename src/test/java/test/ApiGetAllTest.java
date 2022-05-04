package test;

import api.TodoSteps;
import io.qameta.allure.*;
import api.Todo;
import org.junit.jupiter.api.*;

import java.util.List;

public class ApiGetAllTest extends BaseTest {

    private static TodoSteps todoUser1Steps;
    private static TodoSteps todoUser2Steps;

    @BeforeAll
    public static void setup() {
        todoUser1Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_1, PASSWORD).getCookies());
        todoUser2Steps = new TodoSteps(HOST, PORT, authorizeSteps.login(USERNAME_2, PASSWORD).getCookies());
    }

    @AfterAll
    public static void teardown() {
        todoUser1Steps.removeAllTodo();
        todoUser2Steps.removeAllTodo();
    }

    @BeforeEach
    public void start(){
        todoUser1Steps.removeAllTodo();
        todoUser2Steps.removeAllTodo();
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Получение списка всех записей пользователя, проверка, что записи недоступны для второго пользователя")
    @Story(value = "Get all todos")
    @Test
    public void testGetTodoList() {

        int count = 10;

        // создадим несколько записей под первым пользователем
        List<Todo> createdTodoList = todoUser1Steps.createTodos(count);
        // получим список записей первого пользователя
        List<Todo> getAllTodoList = todoUser1Steps.getAllTodoList();
        // убедимся, что количество записей в списке соответствует
        Assertions.assertEquals(count, getAllTodoList.size());
        // убедимся, что списки созданных и полученных записей совпадают
        Assertions.assertIterableEquals(createdTodoList, getAllTodoList);
        // Убедимся, что из под другого пользователя записи недоступны
        Assertions.assertEquals(0, todoUser2Steps.getAllTodoListSize());
    }

    @Epic("TESTING FOR automation-interview-task-1.0 tasks")
    @Feature(value = "Todo test")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Получение списка всех записей пользователя, проверка, что записи недоступны без авторизации")
    @Story(value = "Get all todos unauthorized")
    @Test
    public void testGetTodoListUnauthorized() {

        int count = 10;

        // создадим несколько записей под первым пользователем
        List<Todo> createdTodoList = todoUser1Steps.createTodos(count);
        // получим список записей первого пользователя
        List<Todo> getAllTodoList = todoUser1Steps.getAllTodoList();
        // убедимся, что количество записей в списке соответствует
        Assertions.assertEquals(count, getAllTodoList.size());
        // убедимся, что списки созданных и полученных записей совпадают
        Assertions.assertIterableEquals(createdTodoList, getAllTodoList);
        // Убедимся, что из под другого пользователя записи недоступны
        Assertions.assertEquals(0, todoUser2Steps.getAllTodoListSize());
    }
}
