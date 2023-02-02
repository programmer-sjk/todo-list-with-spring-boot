package example.todolist.user;

public class UserRequest {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String phone;
    private String role;
    private boolean allowMarketing;

    public User toEntity() {
        return new User(name, nickname, email, password, phone, role, allowMarketing);
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
}
