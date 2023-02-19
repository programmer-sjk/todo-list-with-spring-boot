package example.todolist.todo.dto;

import example.todolist.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TodoResponse {
    private Long id;
    private String title;
    private String status;

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.status = todo.getStatus().name();
    }
}
