package example.todolist.login;

import example.todolist.common.ResponseMessage;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import example.todolist.user.UserService;
import example.todolist.user.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public ResponseMessage<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = loginService.generateToken(request);
        return ResponseMessage.ok(response);
    }
}
