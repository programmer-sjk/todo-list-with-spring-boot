package example.todolist.user.domain;

import io.micrometer.common.util.StringUtils;

public enum UserRole {
    ROLE_USER, ROLE_ADMIN;

    public static UserRole value(String name) {
        if (StringUtils.isBlank(name)) {
            return UserRole.ROLE_USER;
        }

        return UserRole.valueOf(name);
    }
}
