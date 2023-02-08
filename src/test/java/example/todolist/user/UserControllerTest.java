package example.todolist.user;

import example.todolist.AcceptanceTest;
import example.todolist.common.ResponseMessage;
import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import example.todolist.user.dto.UserResponse;
import example.todolist.util.AuthFactory;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static example.todolist.login.LoginControllerTest.getLoginToken;
import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends AcceptanceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ADMIN 권한은 전체 유저를 조회할 수 있다.")
    void findAll() {
        // given
        userRepository.save(UserFactory.createAdmin("관리자"));
        String token = getLoginToken(LoginFactory.createLoginRequest());

        // when
        List<UserResponse> responses = findAll(token)
                .jsonPath()
                .getList("data", UserResponse.class);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getNickname()).isEqualTo("관리자");
    }

    @Test
    @DisplayName("일반 USER 권한은 전체 유저를 조회할 수 없다.")
    void userAuthorityFindAllFail() {
        // given
        userRepository.save(UserFactory.create("지나가던 행인"));
        String token = getLoginToken(LoginFactory.createLoginRequest());

        // when
        ResponseMessage<String> response = findAll(token).as(ResponseMessage.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
        assertThat(response.getMessage()).isEqualTo("Access Denied");
    }

    @Test
    @DisplayName("회원을 등록할 수 있다.")
    void signUp() {
        // when
        signUpUser(UserFactory.createUserRequest("골프천재"));

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getNickname()).isEqualTo("골프천재");
    }

    @Test
    @DisplayName("회원은 탈퇴할 수 있다.")
    void withDraw() {
        // given
        userRepository.save(UserFactory.create("천재골퍼"));

        // when
        withDrawUser(getLoginToken(LoginFactory.createLoginRequest()));

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("로그인이 안 된 회원은 탈퇴할 수 없다.")
    void notLoginUserWithDraw() {
        // given
        userRepository.save(UserFactory.create("천재골퍼"));

        // when
        ResponseMessage<String> response = withDrawUser("empty").as(ResponseMessage.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    private ExtractableResponse<Response> findAll(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/users")
                .then().log().all()
                .extract();
    }

    public static void signUpUser(UserRequest request) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/users")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> withDrawUser(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/users")
                .then().log().all()
                .extract();
    }
}
