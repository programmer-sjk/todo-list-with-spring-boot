package example.todolist.todo;

import example.todolist.AcceptanceTest;
import example.todolist.fixture.TodoFactory;
import example.todolist.todo.TodoRepository;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.domain.User;
import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoControllerTest extends AcceptanceTest {
    @Autowired
    private TodoRepository todoRepository;

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

    private void insertTodo(TodoRequest request) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/todos")
                .then().log().all()
                .extract();
    }

    private void updateStatus(Long id, TodoUpdateStatusRequest request) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().patch("/todos/" + id)
                .then().log().all()
                .extract();
    }
}
