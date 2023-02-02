package example.todolist.user;

import example.todolist.fixture.UserFactory;
import example.todolist.user.domain.User;
import example.todolist.user.dto.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @DisplayName("회원을 등록할 수 있다.")
    void insert() {
        // given
        UserRequest request = UserFactory.createUserRequest("골프천재");

        // when
        userService.insertUser(request);

        // then
        User result = userRepository.findAll().get(0);
        assertThat(result.getNickname()).isEqualTo(request.getNickname());
    }

    @Test
    @DisplayName("회원은 탈퇴할 수 있다.")
    void withDraw() {
        // given
        User user = userRepository.save(UserFactory.create("천재 골퍼"));
        LocalDateTime now = LocalDateTime.of(2023, 2, 3, 12, 18, 0, 0);

        // when
        userService.withDraw(user.getId(), now);

        // then
        User result = userRepository.findAll().get(0);
        assertEquals(result.getDeletedAt(), now);
    }

    @Test
    @DisplayName("존재하지 않는 회원탈퇴시 예외가 발생한다.")
    void withDrawException() {
        // when & then
        assertThatThrownBy(() -> userService.withDraw(999L, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }
}
