package example.todolist.util;

import example.todolist.fixture.UserFactory;

public class AuthFactory {
    public static String createLoginToken() {
        return "Bearer " + new JwtTokenUtil().generateToken(UserFactory.COMMON_PHONE);
    }

    public static String createLoginToken(String token) {
        return "Bearer " + token;
    }
}
