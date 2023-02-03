package example.todolist.user.dto;

import example.todolist.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    protected UserRequest() {}

    public UserRequest(Builder builder) {
        this.name = builder.name;
        this.nickname = builder.nickname;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.role = builder.role;
        this.allowMarketing = builder.allowMarketing;
    }

    public User toEntity(String password) {
        return new User.Builder()
                .username(name)
                .nickname(nickname)
                .email(email)
                .password(password)
                .phone(phone)
                .role(role)
                .allowMarketing(allowMarketing)
                .build();
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public boolean getAllowMarketing() {
        return allowMarketing;
    }

    public static class Builder {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String phone;
        private String role;
        private Boolean allowMarketing;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder allowMarketing(Boolean allowMarketing) {
            this.allowMarketing = allowMarketing;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }
}
