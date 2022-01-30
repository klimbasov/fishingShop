package com.jwd.fShop.domain;

public enum Role {
    UNREGISTERED(0, ""),
    USER(2, "user"),
    ADMIN(8, "admin");

    private final int priority;
    private final String alias;

    Role(int priority, String alias) {
        this.priority = priority;
        this.alias = alias;
    }

    public static Role getRole(int priority) {
        Role role = UNREGISTERED;
        switch (priority) {
            case 2:
                role = USER;
                break;
            case 8:
                role = ADMIN;
                break;
            default:
                break;
        }
        return role;
    }

    public int getPriority() {
        return priority;
    }

    public String getAlias() {
        return alias;
    }
}
