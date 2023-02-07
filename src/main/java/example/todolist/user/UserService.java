package example.todolist.user;

import example.todolist.configure.PasswordEncoder;
import example.todolist.user.domain.LoginUser;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import example.todolist.user.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void insertUser(UserRequest request) {
        String password = PasswordEncoder.encode(request.getPassword());
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
    public UserDetails loadUserByUsername(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버가 존재하지 않습니다."));

        return new LoginUser(user);
    }
}
