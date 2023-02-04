package example.todolist.fixture;

import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;

public class UserFactory {
    public static String COMMON_EMAIL = "test@gamil.com";
    public static String COMMON_PASSWORD = "password";

    public static User create(String nickname) {
        return new User.Builder()
                .name("서정국")
                .nickname(nickname)
                .email(COMMON_EMAIL)
                .password(COMMON_PASSWORD)
                .phone("01048932229")
                .role("NORMAL")
                .allowMarketing(true)
                .build();
    }

    public static UserRequest createUserRequest(String nickname) {
        return new UserRequest.Builder()
                .name("서정국")
                .nickname(nickname)
                .email(COMMON_EMAIL)
                .password(COMMON_PASSWORD)
                .phone("01048932229")
                .role("NORMAL")
                .allowMarketing(true)
                .build();
    }
}
