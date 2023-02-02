package example.todolist.user.domain;

import example.todolist.fixture.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    @DisplayName("회원이 탈퇴하게 되면 deletedAt에 현재 시간이 들어간다.")
    void withDraw() {
        // given
        User user = UserFactory.create("천재골퍼");
        LocalDateTime now = LocalDateTime.now();

        // when
        user.withDraw(now);

        // then
        assertEquals(user.getDeletedAt(), now);
    }
}
