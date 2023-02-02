package example.todolist.todo;

import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping()
    public ResponseEntity<Void> insertTodo(@RequestBody TodoRequest request) {
        this.todoService.insertTodo(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody TodoUpdateStatusRequest request) {
        this.todoService.updateStatus(id, request);
        return ResponseEntity.ok().build();
    }
}
