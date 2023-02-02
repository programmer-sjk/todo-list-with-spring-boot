package example.todolist.todo;

import example.todolist.fixture.TodoFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.UserRepository;
import example.todolist.user.UserService;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoServiceTest {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        this.todoRepository.deleteAll();
    }

    @Test
    @DisplayName("할일을 등록할 수 있다.")
    void insert() {
        // given
        TodoRequest request = TodoFactory.createTodoRequest("스프링 로그인 공부");

        // when
        todoService.insertTodo(request);

        // then
        Todo result = todoRepository.findAll().get(0);
        assertThat(result.getTitle()).isEqualTo(request.getTitle());
        assertThat(result.getStatus()).isEqualTo(TodoStatus.TODO);
    }

    @Test
    @DisplayName("할일의 상태를 수정할 수 있다.")
    void updateStatus() {
        // given
        Todo todo = todoRepository.save(TodoFactory.create("autowired 공부"));

        // when
        todoService.updateStatus(todo.getId(), TodoFactory.createUpdateStatusRequest(TodoStatus.COMPLETE.name()));

        // then
        Todo result = todoRepository.findAll().get(0);
        assertThat(result.getId()).isEqualTo(todo.getId());
        assertThat(result.getStatus()).isEqualTo(TodoStatus.COMPLETE);
    }

    @Test
    @DisplayName("존재하지 않는 할일의 상태를 수정하면 예외가 발생한다.")
    void updateStatusException() {
        // given
        TodoUpdateStatusRequest request = TodoFactory.createUpdateStatusRequest(TodoStatus.COMPLETE.name());

        // when & then
        assertThatThrownBy(() -> todoService.updateStatus(999L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Todo 목록이 존재하지 않습니다.");
    }
}
