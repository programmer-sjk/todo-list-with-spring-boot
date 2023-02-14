package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;
import example.todolist.user.domain.User;
import jakarta.validation.constraints.NotBlank;

public class TodoRequest {
    @NotBlank
    private String title;

    private TodoRequest() {}

    public TodoRequest(String title) {
        this.title = title;
    }

    public Todo toEntity(User user) {
        return new Todo(title, user);
    }

    public String getTitle() {
        return title;
    }
}
