package example.todolist.user.domain;

import example.todolist.common.BaseEntity;
import example.todolist.todo.domain.Todo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "Users")
public class User extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    @Comment("사용자 이름")
    @Column(length = 20)
    private String name;
    @Comment("사용자 별명")
    @Column(length = 50, unique = true, nullable = false)
    private String nickname;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Comment("사용자 핸드폰 번호")
    @Column(length = 20, unique = true, nullable = false)
    private String phone;

    @Comment("사용자 권한")
    @ColumnDefault("'NORMAL'")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Comment("사용자 탈퇴 시 날짜시간")
    private LocalDateTime deletedAt;
    @Comment("마케팅 활용동의 여부")
    @Column(nullable = false)
    private boolean allowMarketing;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true )
    private final List<Todo> todos = new ArrayList<>();

    public void withDraw(LocalDateTime deletedAt) {
        if (this.deletedAt != null) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        this.deletedAt = deletedAt;
    }

    public void addTodo(Todo todo) {
        if (!this.todos.contains(todo)) {
            this.todos.add(todo);
        }
    }
}
