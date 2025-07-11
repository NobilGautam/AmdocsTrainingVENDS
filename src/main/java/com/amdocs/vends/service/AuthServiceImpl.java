
package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.utils.PasswordUtil;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.sql.*;
import java.util.Scanner;

public class AuthServiceImpl {

    Scanner sc = new Scanner(System.in);

    public void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Connection con = JDBC.getConnection(); 

        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedInput = PasswordUtil.hashPassword(password);
                String storedHash = rs.getString("password_hash");

                if (hashedInput.equals(storedHash)) {
                    System.out.println("[Login Successful]");
                    LoggedInUser.setName(rs.getString("name"));
                    LoggedInUser.setRole(rs.getString("role"));

                    if (rs.getBoolean("first_login")) {
                        System.out.println("(First time login)");
                        System.out.print("Enter new password: ");
                        String newPassword = sc.nextLine();
                        String newHash = PasswordUtil.hashPassword(newPassword);

                        PreparedStatement updatePs = con.prepareStatement(
                                "UPDATE users SET password_hash=?, first_login=false WHERE username=?");
                        updatePs.setString(1, newHash);
                        updatePs.setString(2, username);
                        updatePs.executeUpdate();
                        updatePs.close();

                        System.out.println("[Password Updated Successfully]");
                    }
                } else {
                    System.out.println(" Incorrect password.");
                }
            } else {
                System.out.println(" User not found.");
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    public void signup() {
        Connection con = JDBC.getConnection();
        try {
            boolean allowSignup = false;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            int userCount = 0;
            if (rs.next()) {
                userCount = rs.getInt(1);
            }

            if (userCount == 0) {
                allowSignup = true;
            } else if (LoggedInUser.isLoggedIn() && LoggedInUser.getRole().equalsIgnoreCase("owner")) {
                allowSignup = true; 
            }

            if (!allowSignup) {
                System.out.println(" Only owner can sign up users.");
                return;
            }

            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            System.out.print("Enter phone number: ");
            String phone = sc.nextLine();

            String role = (userCount == 0) ? "owner" : "tenant";
            String hashedPassword = PasswordUtil.hashPassword(password);

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users (role, name, username, password_hash, first_login, phone_number) VALUES (?, ?, ?, ?, true, ?)");
            ps.setString(1, role);
            ps.setString(2, name);
            ps.setString(3, username);
            ps.setString(4, hashedPassword);
            ps.setString(5, phone);

            int result = ps.executeUpdate();
            ps.close();

            if (result > 0) {
                System.out.println("[Sign Up Successful]");
            } else {
                System.out.println(" Failed to sign up.");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    public void logout() {
        LoggedInUser.logout();
        System.out.println(" Logged out successfully.");
    }
}
