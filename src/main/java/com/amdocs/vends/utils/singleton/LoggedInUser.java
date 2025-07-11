package com.amdocs.vends.utils.singleton;

import com.amdocs.vends.utils.enums.Role;

public class LoggedInUser {
    private static String name = null;
    private static Integer userId = null;
    private static Role role = null;

    private LoggedInUser() {}

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoggedInUser.name = name;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        LoggedInUser.userId = userId;
    }

    public static void setRole(String role) {
        LoggedInUser.role = Role.valueOf(role);
    }

    public static Role getRole() {
        return role;
    }
}
