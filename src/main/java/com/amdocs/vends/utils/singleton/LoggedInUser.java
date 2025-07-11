package com.amdocs.vends.utils.singleton;

public class LoggedInUser {
    private static String name = null;

    private LoggedInUser() {}

    private static String getName() {
        return name;
    }

    private static void setName(String name) {
        LoggedInUser.name = name;
    }
}
