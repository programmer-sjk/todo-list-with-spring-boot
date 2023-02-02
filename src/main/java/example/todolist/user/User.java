package example.todolist.user;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 50, unique = true, nullable = false)
    private String nickname;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 20, unique = true, nullable = false)
    private String phone;
    @ColumnDefault("'NORMAL'")
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime deletedAt;
    private boolean allowMarketing;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getRole() {
        return role;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public boolean isAllowMarketing() {
        return allowMarketing;
    }
}
