package example.todolist.user.dto;

import example.todolist.user.domain.User;
import example.todolist.user.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserRequest {
    private String name;
    @NotBlank
    private String nickname;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phone;
    private String role;
    @NotNull
    private Boolean allowMarketing;

    public User toEntity(String password) {
        return User.builder()
                .name(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .phone(phone)
                .role(UserRole.value(role))
                .allowMarketing(allowMarketing)
                .build();
    }
}
