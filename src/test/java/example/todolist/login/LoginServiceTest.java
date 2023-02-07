package example.todolist.login;

import example.todolist.fixture.LoginFactory;
import example.todolist.fixture.UserFactory;
import example.todolist.login.dto.LoginRequest;
import example.todolist.login.dto.LoginResponse;
import example.todolist.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @DisplayName("계정 정보가 일치하면 accessToken이 발급된다.")
    void generateToken() {
        // given
        userRepository.save(UserFactory.create("천재 골퍼"));
        LoginRequest request = LoginFactory.createLoginRequest();

        // when
        LoginResponse response = loginService.generateToken(request);

        // then
        assertThat(response.getAccessToken()).isNotEmpty();
    }

    @Test
    @DisplayName("멤버가 존재하지 않으면 예외가 발생한다.")
    void generateTokenException() {
        // given
        LoginRequest request = LoginFactory.createLoginRequest();

        // when & then
        assertThatThrownBy(() -> loginService.generateToken(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("계정 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("패스워드가 틀리면 예외가 발생한다.")
    void generateTokenPasswordException() {
        // given
        userRepository.save(UserFactory.create("천재 골퍼"));
        LoginRequest request = LoginFactory.createLoginRequest("01011112222");

        // when & then
        assertThatThrownBy(() -> loginService.generateToken(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("계정 정보가 일치하지 않습니다.");
    }
}
