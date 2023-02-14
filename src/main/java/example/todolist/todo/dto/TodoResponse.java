package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;

public class TodoResponse {
    private Long id;
    private String title;
    private String status;

    private TodoResponse() {}

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.status = todo.getStatus().name();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
