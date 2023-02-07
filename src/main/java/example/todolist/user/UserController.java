package example.todolist.user;

import example.todolist.common.ResponseMessage;
import example.todolist.user.dto.UserRequest;
import example.todolist.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseMessage<List<UserResponse>> findAll() {
        return ResponseMessage.ok(userService.findAll());
    }

    @PostMapping()
    public ResponseMessage<String> insertUser(@RequestBody @Valid UserRequest request) {
        userService.insertUser(request);
        return ResponseMessage.ok();
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<String> withDraw(@PathVariable Long id) {
        userService.withDraw(id, LocalDateTime.now());
        return ResponseMessage.ok();
    }
}
