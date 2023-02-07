package example.todolist.user;

import example.todolist.common.ResponseMessage;
import example.todolist.user.domain.LoginUser;
import example.todolist.user.dto.UserRequest;
import example.todolist.user.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseMessage<String> signUp(@RequestBody @Valid UserRequest request) {
        userService.signUp(request);
        return ResponseMessage.ok();
    }

    @DeleteMapping()
    public ResponseMessage<String> withDraw(@AuthenticationPrincipal LoginUser user) {
        userService.withDraw(user.getId(), LocalDateTime.now());
        return ResponseMessage.ok();
    }
}
