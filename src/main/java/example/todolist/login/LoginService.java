package example.todolist.login;

import example.todolist.configure.PasswordEncoder;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import example.todolist.user.UserRepository;
import example.todolist.user.domain.LoginUser;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import example.todolist.util.JwtTokenUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public LoginService(UserRepository userRepository, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public LoginResponse generateToken(LoginRequest request) {
        User user = authenticate(request);
        return new LoginResponse(jwtTokenUtil.generateToken(user.getPhone()));
    }

    private User authenticate(LoginRequest request) {
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("계정 정보가 일치하지 않습니다."));

        if (!PasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("계정 정보가 일치하지 않습니다.");
        }

        return user;
    }
}
