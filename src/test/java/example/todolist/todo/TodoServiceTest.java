package example.todolist.todo;

import example.todolist.fixture.TodoFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.domain.TodoStatus;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.UserRepository;
import example.todolist.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TodoServiceTest {
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.todoRepository.deleteAll();
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("가장 최근에 작성한 Todo를 조회할 수 있다.")
    void findRecent() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));
        todoRepository.save(TodoFactory.createWithUser("kafka 공부 1장", user));
        todoRepository.save(TodoFactory.createWithUser("kafka 공부 2장", user));
        Todo recentTodo = todoRepository.save(TodoFactory.createWithUser("kafka 공부 3장", user));

        // when
        TodoResponse response = todoService.findRecent(user.getId()).get();

        // then
        assertThat(response.getId()).isEqualTo(recentTodo.getId());
        assertThat(response.getTitle()).isEqualTo(recentTodo.getTitle());
    }

    @Test
    @DisplayName("가장 최근에 작성한 Todo가 없다면 Optional이 반환된다.")
    void findRecentEmpty() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));

        // when
        Optional<TodoResponse> response = todoService.findRecent(user.getId());

        // then
        assertThat(response).isEmpty();
    }

    @Test
    @DisplayName("전체 할일을 조회할 수 있다.")
    void findAll() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));
        Todo todo1 = todoRepository.save(TodoFactory.createWithUser("스프링 세션과 Redis", user));
        Todo todo2 = todoRepository.save(TodoFactory.createWithUser("Elastic Search", user));

        // when
        List<TodoResponse> responses = todoService.findAll(user.getId());

        // then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getId()).isEqualTo(todo1.getId());
        assertThat(responses.get(1).getId()).isEqualTo(todo2.getId());
    }

    @Test
    @DisplayName("할일을 등록할 수 있다.")
    void insert() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));
        TodoRequest request = TodoFactory.createTodoRequest("스프링 로그인 공부");

        // when
        todoService.insertTodo(user.getId(), request);

        // then
        Todo result = todoRepository.findAll().get(0);
        assertThat(result.getTitle()).isEqualTo(request.getTitle());
        assertThat(result.getStatus()).isEqualTo(TodoStatus.TODO);
    }

    @Test
    @DisplayName("할일의 상태를 수정할 수 있다.")
    void updateStatus() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));
        Todo todo = todoRepository.save(TodoFactory.createWithUser("autowired 공부", user));

        // when
        todoService.updateStatus(
                todo.getId(),
                user.getId(),
                TodoFactory.createUpdateStatusRequest(TodoStatus.COMPLETE.name())
        );

        // then
        Todo result = todoRepository.findAll().get(0);
        assertThat(result.getId()).isEqualTo(todo.getId());
        assertThat(result.getStatus()).isEqualTo(TodoStatus.COMPLETE);
    }

    @Test
    @DisplayName("존재하지 않는 할일의 상태를 수정하면 예외가 발생한다.")
    void updateStatusException() {
        // given
        User user = userRepository.save(UserFactory.create("골프 하수"));
        TodoUpdateStatusRequest request = TodoFactory.createUpdateStatusRequest(TodoStatus.COMPLETE.name());

        // when & then
        assertThatThrownBy(() -> todoService.updateStatus(999L, user.getId(), request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Todo 목록이 존재하지 않습니다.");
    }
}
