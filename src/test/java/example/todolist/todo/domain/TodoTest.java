package example.todolist.todo.domain;

import example.todolist.fixture.TodoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TodoTest {
    @Test
    @DisplayName("Todo가 생성되면 기본 상태는 할일(TODO)이다.")
    void defaultStatus() {
        // when
        Todo todo = TodoFactory.create("NotEmpty, NotBlank, NotNull Validation 차이 공부");

        // then
        assertThat(todo.getStatus()).isEqualTo(TodoStatus.TODO);
    }

    @Test
    @DisplayName("Todo의 상태를 변경할 수 있다.")
    void updateStatus() {
        // given
        Todo todo = TodoFactory.create("DTO와 VO 차이점 공부");

        // when
        todo.updateStatus(TodoStatus.IN_PROGRESS.name());

        // then
        assertThat(todo.getStatus()).isEqualTo(TodoStatus.IN_PROGRESS);
    }
}
