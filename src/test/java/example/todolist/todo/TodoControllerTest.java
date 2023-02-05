package example.todolist.todo;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.TodoFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
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

import static example.todolist.user.UserControllerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

class TodoControllerTest extends AcceptanceTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("가장 최근에 작성한 Todo를 조회할 수 있다.")
    void findRecent() {
        // given
        Todo todo = createTodoWithLoginUser("basic vs bearer");

        // when
        TodoResponse response = findRecentTodo().jsonPath()
                .getObject("data", TodoResponse.class);

        // then
        assertThat(response.getId()).isEqualTo(todo.getId());
    }

    @Test
    @DisplayName("가장 최근에 작성한 Todo가 없다면 204(NO_CONTENT) 상태코드가 반환된다.")
    void findRecentEmpty() {
        // given
        createLoginUser();

        // when
        ExtractableResponse<Response> response = findRecentTodo();

        // then
        int statusCode = response.jsonPath().getInt("statusCode");
        String message = response.jsonPath().getString("message");

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(message).isEqualTo(HttpStatus.NO_CONTENT.getReasonPhrase());
    }

    @Test
    @DisplayName("멤버의 전체 할일을 조회할 수 있다.")
    void findAll() {
        // given
        Todo todo = createTodoWithLoginUser("dto 역할과 범위 공부");

        // when
        List<TodoResponse> responses = findAllTodo();

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getId()).isEqualTo(todo.getId());
    }

    @Test
    @DisplayName("할일을 등록할 수 있다.")
    void insert() {
        // when
        Todo todo = createTodoWithLoginUser("dto 역할과 범위 공부");

        // then
        assertThat(todo.getTitle()).isEqualTo("dto 역할과 범위 공부");
    }

    @Test
    @DisplayName("할일을 수정할 수 있다.")
    void updateStatus() {
        // given
        Todo todo = createTodoWithLoginUser("인텔리제이 코드 변경에 따른 재시작");

        // when
        updateStatus(todo.getId(), TodoFactory.createUpdateStatusRequest(TodoStatus.IN_PROGRESS.name()));

        // then
        Todo updatedTodo = todoRepository.findAll().get(0);
        assertThat(updatedTodo.getStatus()).isEqualTo(TodoStatus.IN_PROGRESS);
    }

    private ExtractableResponse<Response> findRecentTodo() {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken())
                .when().get("/api/todos/recent")
                .then().log().all()
                .extract();
    }

    private List<TodoResponse> findAllTodo() {
        return RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken())
                .when().get("/api/todos")
                .then().log().all()
                .extract()
                .jsonPath().getList("data", TodoResponse.class);
    }

    private void insertTodo(TodoRequest request) {
        RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/api/todos")
                .then().log().all()
                .extract();
    }

    private void updateStatus(Long id, TodoUpdateStatusRequest request) {
        RestAssured
                .given().log().all()
                .header("Authorization", AuthFactory.createLoginToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/api/todos/" + id)
                .then().log().all()
                .extract();
    }

    private Todo createTodoWithLoginUser(String title) {
        createLoginUser();
        insertTodo(TodoFactory.createTodoRequest(title));
        return todoRepository.findAll().get(0);
    }

    private void createLoginUser() {
        UserRequest request = UserFactory.createUserRequest("천재골퍼");
        insertUser(request);
    }
}
