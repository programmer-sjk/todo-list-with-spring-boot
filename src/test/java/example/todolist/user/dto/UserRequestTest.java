package example.todolist.user.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("별명은 빈 값일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void validateNickname(String input) {
        // given
        UserRequest request = new UserRequest.Builder()
                .nickname(input)
                .build();

        // when
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        ConstraintViolation<UserRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("nickname"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }

    @DisplayName("이메일 형식이 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", "test@", "@gmail.com"})
    void validateEmail(String input) {
        // given
        UserRequest request = new UserRequest.Builder()
                .email(input)
                .build();

        // when
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        ConstraintViolation<UserRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("email"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
    }

    @Test
    @DisplayName("마케팅 활용 동의는 필수 값이다.")
    void validateAllowMarketing() {
        // given
        UserRequest request = new UserRequest.Builder().build();

        // when
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(request);
        ConstraintViolation<UserRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("allowMarketing"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("널이어서는 안됩니다");
    }
}
