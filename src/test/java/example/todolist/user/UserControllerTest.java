package example.todolist.user;

import example.todolist.AcceptanceTest;
import example.todolist.common.ResponseMessage;
import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import example.todolist.utils.AuthFactory;
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
    @DisplayName("회원을 등록할 수 있다.")
    void insert() {
        // when
        insertUser(UserFactory.createUserRequest("골프천재"));

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getNickname()).isEqualTo("골프천재");
    }

    @Test
    @DisplayName("회원은 탈퇴할 수 있다.")
    void withDraw() {
        // given
        User user = userRepository.save(UserFactory.create("천재골퍼"));

        // when
        withDrawUser(user.getId(), getLoginToken(LoginFactory.createLoginRequest()));

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getDeletedAt()).isNotNull();
    }

    @Test
    @DisplayName("로그인이 안 된 회원은 탈퇴할 수 없다.")
    void notLoginUserWithDraw() {
        // given
        User user = userRepository.save(UserFactory.create("천재골퍼"));

        // when
        ResponseMessage<String> response = withDrawUser(user.getId(), "empty").as(ResponseMessage.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static void insertUser(UserRequest request) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/users")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> withDrawUser(Long id, String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/api/users/" + id)
                .then().log().all()
                .extract();
    }
}
