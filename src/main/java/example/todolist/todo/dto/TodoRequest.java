package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;
import jakarta.validation.constraints.NotBlank;

public class TodoRequest {
    @NotBlank
    private String title;

    protected TodoRequest() {}

    public TodoRequest(String title) {
        this.title = title;
    }

    public Todo toEntity() {
        return new Todo(title);
    }

    public String getTitle() {
        return title;
    }
}
