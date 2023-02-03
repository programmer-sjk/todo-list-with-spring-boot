package example.todolist.fixture;

import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;

public class UserFactory {
    public static User create(String nickname) {
        return new User.Builder()
                .username("서정국")
                .nickname(nickname)
                .email("test@gamil.com")
                .password("password")
                .phone("01048932229")
                .role("NORMAL")
                .allowMarketing(true)
                .build();
    }

    public static UserRequest createUserRequest(String nickname) {
        return new UserRequest.Builder()
                .name("서정국")
                .nickname(nickname)
                .email("test@gamil.com")
                .password("password")
                .phone("01048932229")
                .role("NORMAL")
                .allowMarketing(true)
                .build();
    }
}
