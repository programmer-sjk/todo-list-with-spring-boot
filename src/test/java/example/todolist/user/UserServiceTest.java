package example.todolist.user;

import example.todolist.fixture.UserFactory;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자를 등록할 수 있다.")
    void insert() {
        // given
        UserRequest request = UserFactory.createUserRequest("골프천재");

        // when
        userService.insertUser(request);

        // then
        User result = userRepository.findAll().get(0);
        assertThat(result.getNickname()).isEqualTo(request.getNickname());
    }
}
