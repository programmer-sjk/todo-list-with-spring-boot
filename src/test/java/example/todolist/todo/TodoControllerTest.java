package example.todolist.todo;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.TodoFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
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
import static example.todolist.user.UserControllerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

class TodoControllerTest extends AcceptanceTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("가장 최근에 작성한 Todo를 조회할 수 있다.")
    void findRecent() {
        // given
        String token = createTokenWithUser();
        Todo todo = createTodo("basic vs bearer", token);

        // when
        TodoResponse response = findRecentTodo(token).jsonPath()
                .getList("data", TodoResponse.class)
                .get(0);

        // then
        assertThat(response.getId()).isEqualTo(todo.getId());
    }

    @Test
    @DisplayName("가장 최근에 작성한 Todo가 없다면 204(NO_CONTENT) 상태코드가 반환된다.")
    void findRecentEmpty() {
        // given
        String token = createTokenWithUser();

        // when
        ExtractableResponse<Response> response = findRecentTodo(token);

        // then
        int statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(message).isEqualTo(HttpStatus.NO_CONTENT.getReasonPhrase());
    }

    @Test
    @DisplayName("회원의 전체 할일을 조회할 수 있다.")
    void findAll() {
        // given
        String token = createTokenWithUser();
        Todo todo = createTodo("dto 역할과 범위 공부", token);

        // when
        List<TodoResponse> responses = findAllTodo(token);

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getId()).isEqualTo(todo.getId());
    }

    @Test
    @DisplayName("할일을 등록할 수 있다.")
    void create() {
        // given
        String token = createTokenWithUser();
        TodoRequest request = TodoFactory.createTodoRequest("dto 역할과 범위 공부");

        // when
        createTodo(request, token);

        // then
        Todo todo = todoRepository.findAll().get(0);
        assertThat(todo.getTitle()).isEqualTo("dto 역할과 범위 공부");
    }

    @Test
    @DisplayName("할일을 수정할 수 있다.")
    void updateStatus() {
        // given
        String token = createTokenWithUser();
        Todo todo = createTodo("인텔리제이 코드 변경에 따른 재시작", token);

        // when
        updateStatus(todo.getId(), TodoFactory.createUpdateStatusRequest(TodoStatus.IN_PROGRESS.name()), token);

        // then
        Todo updatedTodo = todoRepository.findAll().get(0);
        assertThat(updatedTodo.getStatus()).isEqualTo(TodoStatus.IN_PROGRESS);
    }

    private ExtractableResponse<Response> findRecentTodo(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .when().get("/api/todos/recent")
                .then().log().all()
                .extract();
    }

    private List<TodoResponse> findAllTodo(String token) {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .when().get("/api/todos")
                .then().log().all()
                .extract()
                .jsonPath().getList("data", TodoResponse.class);
    }

    private void createTodo(TodoRequest request, String token) {
        RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/todos")
                .then().log().all()
                .extract();
    }

    private void updateStatus(Long id, TodoUpdateStatusRequest request, String token) {
        RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken(token))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch(String.format("/api/todos/%d/status", id))
                .then().log().all()
                .extract();
    }

    private Todo createTodo(String title, String token) {
        createTodo(TodoFactory.createTodoRequest(title), token);
        return todoRepository.findAll().get(0);
    }

    private String createTokenWithUser() {
        signUpUser(UserFactory.createUserRequest("천재골퍼"));
        return getLoginToken(LoginFactory.createLoginRequest());
    }
}
