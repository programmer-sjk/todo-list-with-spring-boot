package example.todolist.user.dto;

import example.todolist.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phone;
    private String role;
    private Boolean allowMarketing;

    public UserResponse(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.role = user.getRole().name();
        this.allowMarketing = user.isAllowMarketing();
    }
}
