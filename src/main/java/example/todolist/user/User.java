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

    protected User() {}

    public User(Builder builder) {
        this.name = builder.name;
        this.nickname = builder.nickname;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.role = UserRole.value(builder.role);
        this.allowMarketing = builder.allowMarketing;
    }

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

    public static class Builder {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String phone;
        private String role;
        private boolean allowMarketing;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder role(String role) {
            this.role = role;
            return this;
        }
        public Builder allowMarketing(boolean allowMarketing) {
            this.allowMarketing = allowMarketing;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
