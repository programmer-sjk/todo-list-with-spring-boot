package example.todolist.utils;


import example.todolist.user.dto.UserRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthFactory {
    public static String tokenByUserRequest(UserRequest request) {
        String seed = request.getEmail() + ":" + request.getPassword();
        return Base64.getEncoder().encodeToString(seed.getBytes(StandardCharsets.UTF_8));
    }
}
