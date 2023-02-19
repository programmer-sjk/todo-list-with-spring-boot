package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;
import example.todolist.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoRequest {
    @NotBlank
    private String title;

    public TodoRequest(String title) {
        this.title = title;
    }

    public Todo toEntity(User user) {
        return new Todo(title, user);
    }
}
