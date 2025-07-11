package com.amdocs.vends.utils.singleton;

public class LoggedInUser {
    private static String name = null;

    private LoggedInUser() {}

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LoggedInUser.name = name;
    }

    public static void clear() {
        name = null;
    }
}
