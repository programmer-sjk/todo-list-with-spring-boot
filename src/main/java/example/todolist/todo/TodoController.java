package example.todolist.todo;

import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TodoResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.find(id));
    }

    @GetMapping()
    public ResponseEntity<List<TodoResponse>> findAll() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @PostMapping()
    public ResponseEntity<Void> insertTodo(@RequestBody TodoRequest request) {
        todoService.insertTodo(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody TodoUpdateStatusRequest request) {
        todoService.updateStatus(id, request);
        return ResponseEntity.ok().build();
    }
}
