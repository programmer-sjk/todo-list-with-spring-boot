package example.todolist.utils;

import example.todolist.fixture.UserFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthFactory {
    public static String createLoginToken() {
        String seed = UserFactory.COMMON_EMAIL + ":" + UserFactory.COMMON_PASSWORD;
        return "Basic " + Base64.getEncoder().encodeToString(seed.getBytes(StandardCharsets.UTF_8));
    }
}
