package com.amdocs.vends.utils.singleton;

public class LoggedInUser {

    private static String name = null;
    private static String username = null;
    private static String role = null;

    private LoggedInUser() {} // Private constructor to prevent instantiation

    public static String getName() {
        return name;
    }
    public static void logout() {
        name = null;
        role = null;
    }

    public static void setName(String name) {
        LoggedInUser.name = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        LoggedInUser.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        LoggedInUser.role = role;
    }

    public static void clear() {
        name = null;
        username = null;
        role = null;
    }

    public static boolean isLoggedIn() {
        return username != null;
    }
}

