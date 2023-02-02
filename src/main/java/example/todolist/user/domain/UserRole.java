package example.todolist.user.domain;

import io.micrometer.common.util.StringUtils;

public enum UserRole {
    NORMAL, ADMIN;

    public static UserRole value(String name) {
        if (StringUtils.isBlank(name)) {
            return UserRole.NORMAL;
        }

        return UserRole.valueOf(name);
    }
}
