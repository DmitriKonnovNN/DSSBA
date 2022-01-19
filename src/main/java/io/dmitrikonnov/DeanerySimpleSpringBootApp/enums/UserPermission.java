package io.dmitrikonnov.DeanerySimpleSpringBootApp.enums;

public enum UserPermission {
    TOPIC_READ ("topic:read"),
    TOPIC_WRITE ("topic:write"),
    COURSE_READ ("course:read"),
    COURSE_WRITE ("course:write"),
    TOPIC_ALL ("topic:all"),
    COURSE_ALL("course:all"),
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
