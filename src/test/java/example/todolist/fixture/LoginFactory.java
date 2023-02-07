package example.todolist.fixture;

import example.todolist.login.dto.LoginRequest;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;

public class LoginFactory {
    public static LoginRequest createLoginRequest() {
        return new LoginRequest(UserFactory.COMMON_PHONE, UserFactory.COMMON_PASSWORD);
    }

    public static LoginRequest createLoginRequest(String password) {
        return new LoginRequest(UserFactory.COMMON_PHONE, password);
    }
}
