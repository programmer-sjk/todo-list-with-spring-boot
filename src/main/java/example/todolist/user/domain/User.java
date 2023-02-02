package example.todolist.user.domain;

import example.todolist.common.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

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

    public void withDraw(LocalDateTime deletedAt) {
        if (this.deletedAt != null) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        this.deletedAt = deletedAt;
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
