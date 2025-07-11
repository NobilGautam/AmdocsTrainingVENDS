package com.amdocs.vends.utils.singleton;

public class LoggedInUser {
    private static String name = null;
    private static Integer userId = null;

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
}
