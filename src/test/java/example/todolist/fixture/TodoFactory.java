package example.todolist.fixture;

import example.todolist.todo.domain.Todo;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.domain.User;

public class TodoFactory {
    public static Todo create(String title) {
        return new Todo(title);
    }

    public static Todo createWithUser(String title, User user) {
        return new Todo(title, user);
    }

    public static TodoRequest createTodoRequest(String title) {
        return new TodoRequest(title);
    }

    public static TodoUpdateStatusRequest createUpdateStatusRequest(String status) {
        return new TodoUpdateStatusRequest(status);
    }
}
