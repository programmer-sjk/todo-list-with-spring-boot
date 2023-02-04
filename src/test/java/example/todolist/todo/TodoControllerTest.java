package example.todolist.todo;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.TodoFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.UserRepository;
import example.todolist.user.dto.UserRequest;
import example.todolist.utils.AuthFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static example.todolist.user.UserControllerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

class TodoControllerTest extends AcceptanceTest {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("특정 할일을 조회할 수 있다.")
    void find() {
        // given
        Todo todo = todoRepository.save(TodoFactory.create("dto 역할과 범위 공부"));

        // when
        TodoResponse response = findTodo(todo.getId());

        // then
        assertThat(response.getId()).isEqualTo(todo.getId());
    }

    @Test
    @DisplayName("전체 할일을 조회할 수 있다.")
    void findAll() {
        // given
        Todo todo1 = todoRepository.save(TodoFactory.create("dto 역할과 범위 공부"));
        Todo todo2 = todoRepository.save(TodoFactory.create("Entity는 dto를 알아도 될까?"));

        // when
        List<TodoResponse> responses = findAllTodo();

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getId()).isEqualTo(todo1.getId());
        assertThat(responses.get(1).getId()).isEqualTo(todo2.getId());
    }

    @Test
    @DisplayName("할일을 등록할 수 있다.")
    void insert() {
        // when
        insertTodo(TodoFactory.createTodoRequest("dto 역할과 범위 공부"));

        // then
        Todo todo = todoRepository.findAll().get(0);
        assertThat(todo.getTitle()).isEqualTo("dto 역할과 범위 공부");
    }

    @Test
    @DisplayName("할일을 수정할 수 있다.")
    void updateStatus() {
        // given
        Todo todo = todoRepository.save(TodoFactory.create("인텔리제이 코드 변경에 따른 재시작"));

        // when
        updateStatus(todo.getId(), TodoFactory.createUpdateStatusRequest(TodoStatus.IN_PROGRESS.name()));

        // then
        Todo updatedTodo = todoRepository.findAll().get(0);
        assertThat(updatedTodo.getStatus()).isEqualTo(TodoStatus.IN_PROGRESS);
    }

    private TodoResponse findTodo(Long id) {
        return RestAssured
                .given().log().all()
                .header("Authorization", createAuthToken())
                .when().get("/todos/" + id)
                .then().log().all()
                .extract()
                .jsonPath().getObject("data", TodoResponse.class);
    }

    private List<TodoResponse> findAllTodo() {
        return RestAssured
                .given().log().all()
                .header("Authorization", createAuthToken())
                .when().get("/todos")
                .then().log().all()
                .extract()
                .jsonPath().getList("data", TodoResponse.class);
    }

    private void insertTodo(TodoRequest request) {
        RestAssured
                .given().log().all()
                .header("Authorization", createAuthToken())
                .header("Authorization", "Basic ")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/todos")
                .then().log().all()
                .extract();
    }

    private void updateStatus(Long id, TodoUpdateStatusRequest request) {
        RestAssured
                .given().log().all()
                .header("Authorization", createAuthToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/todos/" + id)
                .then().log().all()
                .extract();
    }

    private String createAuthToken() {
        UserRequest request = UserFactory.createUserRequest("천재골퍼");
        insertUser(request);
        return "Basic " + AuthFactory.tokenByUserRequest(request);
    }
}
