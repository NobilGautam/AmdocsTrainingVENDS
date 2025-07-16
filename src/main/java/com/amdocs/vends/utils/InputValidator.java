package com.amdocs.vends.utils;

public class InputValidator {
    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }
    public static boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    }
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }
} 