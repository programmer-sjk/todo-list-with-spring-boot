package example.todolist.utils;

import example.todolist.fixture.UserFactory;
import example.todolist.util.JwtTokenUtil;

public class AuthFactory {
    public static String createLoginToken() {
        return "Bearer " + new JwtTokenUtil().generateToken(UserFactory.COMMON_PHONE);
    }
}
