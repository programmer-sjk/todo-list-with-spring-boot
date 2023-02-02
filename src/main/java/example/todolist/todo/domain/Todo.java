package example.todolist.todo.domain;

import example.todolist.common.BaseEntity;
import jakarta.persistence.*;

@Entity
public class Todo extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 20)
    private String title;
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    protected Todo() {}

    public Todo(String title) {
        this.title = title;
        this.status = TodoStatus.TODO;
    }

    public void updateStatus(String status) {
        this.status = TodoStatus.valueOf(status);
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
}