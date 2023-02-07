package example.todolist.login.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @DisplayName("이름은 빈 값일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void validateUsername(String input) {
        // given
        LoginRequest request = new LoginRequest(input, "password");

        // when
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        ConstraintViolation<LoginRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("username"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }

    @DisplayName("패스워드는 빈 값일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void validatePassword(String input) {
        // given
        LoginRequest request = new LoginRequest("username", input);

        // when
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);
        ConstraintViolation<LoginRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("password"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }
}
