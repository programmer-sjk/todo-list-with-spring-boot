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

class TodoRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("title은 빈 값일 수 없다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    void validateTitle(String input) {
        // given
        TodoRequest request = new TodoRequest(input);

        // when
        Set<ConstraintViolation<TodoRequest>> violations = validator.validate(request);
        ConstraintViolation<TodoRequest> violation = violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals("title"))
                .findFirst()
                .get();

        // then
        assertThat(violation.getMessage()).isEqualTo("공백일 수 없습니다");
    }
}
