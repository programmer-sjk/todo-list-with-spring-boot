package example.todolist.user.dto;

import example.todolist.user.domain.User;

public class UserResponse {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phone;
    private String role;
    private Boolean allowMarketing;

    private UserResponse() {}

    public UserResponse(User user) {
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.role = user.getRole().name();
        this.allowMarketing = user.isAllowMarketing();
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

    public Boolean getAllowMarketing() {
        return allowMarketing;
    }
}
