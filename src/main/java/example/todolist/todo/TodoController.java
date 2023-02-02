package example.todolist.todo;

import example.todolist.common.ResponseMessage;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
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
    public ResponseMessage<String> insertTodo(@RequestBody TodoRequest request) {
        todoService.insertTodo(request);
        return ResponseMessage.ok();
    }

    @PatchMapping("/{id}")
    public ResponseMessage<String> updateStatus(@PathVariable Long id, @RequestBody TodoUpdateStatusRequest request) {
        todoService.updateStatus(id, request);
        return ResponseMessage.ok();
    }
}
