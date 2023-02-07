package example.todolist.login;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.user.UserRepository;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static example.todolist.user.UserControllerTest.insertUser;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest extends AcceptanceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인을 통해 토큰을 발급받을 수 있다.")
    void generateToken() {
        // given
        insertUser(UserFactory.createUserRequest("골프천재"));

        // when
        LoginResponse response = login(LoginFactory.createLoginRequest());

        // then
        assertThat(response.getAccessToken()).isNotEmpty();
    }

    public static LoginResponse login(LoginRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/login")
                .then().log().all()
                .extract()
                .jsonPath().getObject("data", LoginResponse.class);
    }

    private void withDrawUser(Long id) {
        RestAssured
                .given().log().all()
                .when().delete("/api/users/" + id)
                .then().log().all()
                .extract();
    }
}
