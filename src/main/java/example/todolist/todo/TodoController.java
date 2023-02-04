package example.todolist.todo;

import example.todolist.common.ResponseMessage;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.domain.LoginUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseMessage<TodoResponse> find(@PathVariable Long id) {
        return ResponseMessage.ok(todoService.find(id));
    }

    @GetMapping()
    public ResponseMessage<List<TodoResponse>> findAll() {
        return ResponseMessage.ok(todoService.findAll());
    }

    @PostMapping()
    public ResponseMessage<String> insertTodo(
            @AuthenticationPrincipal LoginUser user,
            @RequestBody TodoRequest request
    ) {
        todoService.insertTodo(user.getId(), request);
        return ResponseMessage.ok();
    }

    @PatchMapping("/{id}")
    public ResponseMessage<String> updateStatus(@PathVariable Long id, @RequestBody TodoUpdateStatusRequest request) {
        todoService.updateStatus(id, request);
        return ResponseMessage.ok();
    }
}
