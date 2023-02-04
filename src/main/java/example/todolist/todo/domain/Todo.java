package example.todolist.todo.domain;

import example.todolist.common.BaseEntity;
import example.todolist.user.domain.User;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
public class Todo extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    @Comment("Todo 제목")
    @Column(length = 20)
    private String title;
    @Comment("Todo 상태")
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @ManyToOne
    private User user;

    protected Todo() {}

    public Todo(String title) {
        this.title = title;
        this.status = TodoStatus.TODO;
    }

    public Todo(String title, User user) {
        this(title);
        setUser(user);
    }

    public void updateStatus(String status) {
        this.status = TodoStatus.valueOf(status);
    }

    private void setUser(User user) {
        this.user = user;
        user.addTodo(this);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
