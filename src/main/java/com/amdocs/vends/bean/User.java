package com.amdocs.vends.bean;

public class User {
    private String role;
    private String name;
    private String username;
    private String passwordHash;
    private Boolean firstLogin;
    private String phoneNumber;
    public User() {}
    public User(String role, String name, String username, String passwordHash, Boolean firstLogin, String phoneNumber) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstLogin = firstLogin;
        this.phoneNumber = phoneNumber;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public Boolean getFirstLogin() {
        return firstLogin;
    }
    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
