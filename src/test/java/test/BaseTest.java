package test;

import api.AuthorizeSteps;

public abstract class BaseTest {

    protected static final String HOST = "http://localhost";
    protected static final int PORT = 7844;
    protected static final String BASE_URL = HOST + ":" + PORT;

    protected static final String USERNAME_1 = "john_dow@some.domaine.com";
    protected static final String USERNAME_2 = "simon_dow@some.domaine.com";
    protected static final String USERNAME_UNREGISTERED = "unregistered@some.domaine.com";
    protected static final String PASSWORD = "123456789";
    protected static final String BAD_PASSWORD = "1234";

    protected static final AuthorizeSteps authorizeSteps = new AuthorizeSteps(HOST, PORT);
}
