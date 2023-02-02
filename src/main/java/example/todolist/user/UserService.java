package example.todolist.user;

import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void insertUser(UserRequest request) {
        userRepository.save(request.toEntity());
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
}
