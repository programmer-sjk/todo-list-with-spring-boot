package example.todolist.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
public class User {
    @Id @GeneratedValue
    private Long id;
    @Column(length = 20)
    private String name;
    @Column(length = 50)
    private String nickname;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(length = 20)
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime deletedAt;
}
