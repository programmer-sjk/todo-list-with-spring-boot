package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;

public class TodoRequest {
    private String title;

    public Todo toEntity() {
        return new Todo(title);
    }

    public String getTitle() {
        return title;
    }
}
