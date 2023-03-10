package example.todolist.login;

import example.todolist.common.ResponseMessage;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
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
