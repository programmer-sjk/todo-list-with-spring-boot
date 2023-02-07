package example.todolist.login;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import example.todolist.user.UserRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static example.todolist.user.UserControllerTest.signUpUser;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest extends AcceptanceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("로그인을 통해 토큰을 발급받을 수 있다.")
    void generateToken() {
        // given
        signUpUser(UserFactory.createUserRequest("골프천재"));

        // when
        LoginResponse response = login(LoginFactory.createLoginRequest());

        // then
        assertThat(response.getAccessToken()).isNotEmpty();
    }

    private static LoginResponse login(LoginRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/login")
                .then().log().all()
                .extract()
                .jsonPath().getObject("data", LoginResponse.class);
    }

    public static String getLoginToken(LoginRequest request) {
        return login(request).getAccessToken();
    }
}
