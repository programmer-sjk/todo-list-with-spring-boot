package example.todolist.login.dto;

public class LoginResponse {
    private String accessToken;

    private LoginResponse() {}

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
