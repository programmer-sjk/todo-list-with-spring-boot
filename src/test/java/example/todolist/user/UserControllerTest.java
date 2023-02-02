package example.todolist.user;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.UserFactory;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AcceptanceTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자를 등록할 수 있다.")
    void insert() {
        // when
        insertUser(UserFactory.createUserRequest("골프천재"));

        // then
        List<User> users = userRepository.findAll();
        assertThat(users.get(0).getNickname()).isEqualTo("골프천재");
    }

    private void insertUser(UserRequest request) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/users")
                .then().log().all()
                .extract();
    }
}
