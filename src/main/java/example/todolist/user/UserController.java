package example.todolist.user;

import example.todolist.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Void> insertUser(@RequestBody @Valid UserRequest request) {
        this.userService.insertUser(request);
        return ResponseEntity.ok().build();
    }
}
