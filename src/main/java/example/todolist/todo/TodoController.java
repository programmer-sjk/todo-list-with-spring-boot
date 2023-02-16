package example.todolist.todo;

import example.todolist.common.PageResponse;
import example.todolist.common.ResponseMessage;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.domain.LoginUser;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/recent")
    public ResponseMessage<TodoResponse> findRecent(@AuthenticationPrincipal LoginUser user) {
        return todoService.findRecent(user.getId())
                .map(ResponseMessage::ok)
                .orElseGet(ResponseMessage::noContent);
    }

    @GetMapping()
    public PageResponse<List<TodoResponse>> findAll(@AuthenticationPrincipal LoginUser user, Pageable pageable) {
        return todoService.findAll(user.getId(), pageable);
    }

    @PostMapping()
    public ResponseMessage<String> createTodo(
            @AuthenticationPrincipal LoginUser user,
            @RequestBody @Valid TodoRequest request
    ) {
        todoService.createTodo(user.getId(), request);
        return ResponseMessage.ok();
    }

    @PatchMapping("/{id}/status")
    public ResponseMessage<String> updateStatus(
            @PathVariable Long id,
            @AuthenticationPrincipal LoginUser user,
            @RequestBody @Valid TodoUpdateStatusRequest request
    ) {
        todoService.updateStatus(id, user.getId(), request);
        return ResponseMessage.ok();
    }
}
