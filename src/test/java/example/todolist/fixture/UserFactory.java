package example.todolist.fixture;

import example.todolist.user.domain.User;
import example.todolist.user.domain.UserRole;
import example.todolist.user.dto.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFactory {
    public static String COMMON_PHONE = "0104893229";
    public static String COMMON_PASSWORD = "password";

    public static User create(String nickname) {
        return new User.Builder()
                .name("서정국")
                .nickname(nickname)
                .email("test@gamil.com")
                .password(new BCryptPasswordEncoder().encode(COMMON_PASSWORD))
                .phone(COMMON_PHONE)
                .role(UserRole.ROLE_USER.name())
                .allowMarketing(true)
                .build();
    }

    public static User createAdmin(String nickname) {
        return new User.Builder()
                .name("서정국")
                .nickname(nickname)
                .email("test@gamil.com")
                .password(new BCryptPasswordEncoder().encode(COMMON_PASSWORD))
                .phone(COMMON_PHONE)
                .role(UserRole.ROLE_ADMIN.name())
                .allowMarketing(true)
                .build();
    }

    public static UserRequest createUserRequest(String nickname) {
        return new UserRequest.Builder()
                .name("서정국")
                .nickname(nickname)
                .email("test@gamil.com")
                .password(COMMON_PASSWORD)
                .phone(COMMON_PHONE)
                .role(UserRole.ROLE_USER.name())
                .allowMarketing(true)
                .build();
    }
}
