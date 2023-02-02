package example.todolist.todo.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TodoUpdateStatusRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("status는 빈 값일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void validateTitle(String input) {
        // given
        TodoUpdateStatusRequest request = new TodoUpdateStatusRequest(input);

        // when
        Set<ConstraintViolation<TodoUpdateStatusRequest>> violations = validator.validate(request);
        ConstraintViolation<TodoUpdateStatusRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("status"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }
}
