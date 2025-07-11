package com.amdocs.vends.utils.singleton;

public class LoggedInUser {
    private static int userId;
    private static String name;
    private static String role;

    private LoggedInUser() {}

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setName(String n) {
        name = n;
    }

    public static String getName() {
        return name;
    }

    public static void setRole(String r) {
        role = r;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return role != null;
    }

    public static void logout() {
        userId = 0;
        name = null;
        role = null;
    }
}

