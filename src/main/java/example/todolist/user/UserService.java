package example.todolist.user;

import example.todolist.user.domain.LoginUser;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void insertUser(UserRequest request) {
        String password = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.toEntity(password));
    }

    @Transactional
    public void withDraw(Long id, LocalDateTime now) {
        User user = findById(id);
        user.withDraw(now);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return null;
        }
        return new LoginUser(user);
    }
}
