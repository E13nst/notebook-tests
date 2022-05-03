package test;

import api.AuthorizeSteps;
import api.TodoSteps;
import api.User;

public abstract class BaseTest {

    protected static final String HOST = "http://localhost";
    protected static final int PORT = 7844;
    protected static final String BASE_URL = HOST + ":" + PORT;

    protected static final String USERNAME_1 = "john_dow@some.domaine.com";
    protected static final String USERNAME_2 = "simon_dow@some.domaine.com";
    protected static final String PASSWORD = "123456789";
    protected static final String BAD_PASSWORD = "1234";

//    protected static final User user1 = new User(USERNAME_1, PASSWORD);
//    protected static final User user2 = new User(USERNAME_2, PASSWORD);

    protected static final AuthorizeSteps authorizeSteps = new AuthorizeSteps(HOST, PORT);

//    protected static TodoSteps user1Steps = new TodoSteps(HOST, user1);
//    protected static TodoSteps user2Steps = new TodoSteps(HOST, user2);
//    protected static TodoSteps unauthorizedSteps = new TodoSteps(HOST, new User());

//    protected static final class EndPoints {
//        public static final String login = "/api/login";
//        public static final String logout = "/api/logout";
//        public static final String create = "/api/create";
//        public static final String getAll = "/api/getAll";
//        public static final String remove = "/api/remove";
//    }
}
