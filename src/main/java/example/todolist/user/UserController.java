package example.todolist.user;

import example.todolist.common.ResponseMessage;
import example.todolist.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
