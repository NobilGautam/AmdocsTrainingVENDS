package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.bean.Tenant;
import com.amdocs.vends.bean.User;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.UserIntf;
import com.amdocs.vends.utils.PasswordUtil;
import com.amdocs.vends.utils.enums.PropertyType;
import com.amdocs.vends.utils.enums.Role;
import com.amdocs.vends.utils.exceptions.DuplicateUsernameException;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserImpl implements UserIntf {

    Scanner scanner = new Scanner(System.in);
    PropertyImpl propertyService = new PropertyImpl();
    TenantImpl tenantService = new TenantImpl();
    PaymentImpl paymentService = new PaymentImpl();
    LeaveRequestImpl leaveService = new LeaveRequestImpl();

    public void showAdminHomepage() {
        System.out.println("Welcome, Mr. " + LoggedInUser.getName());
        System.out.println();
        do {
            System.out.println("1. Add Property");
            System.out.println("2. Add Tenant");
            System.out.println("3. Show Properties and Tenants");
            System.out.println("4. Payment Approval Requests");
            System.out.println("5. Leave Approval Requests");
            System.out.println("6. Logout");
            System.out.print("Enter your choice between number 1 to 6: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addProperty(propertyService);
                    break;
                case 2:
                    addTenant(tenantService);
                    break;
                case 3:
                	propertyService.showPropertyDetails();
                    break;
                case 4:
                    paymentService.showPendingPaymentsAndApprove();
                    break;
                case 5:
                    leaveService.approveLeaveRequest();
                    break;
                case 6:
                    logout();
                    break;
                default: System.exit(0);
            }
        } while (true);
    }

    private void addTenant(TenantImpl tenantService) {
        Integer userId;
        String name;
        String username;
        String role = Role.TENANT.getValue();
        String passwordHash;
        String phoneNumber;
        Integer propertyId;
        Float rent;
        scanner.nextLine();
        System.out.println("Enter name of tenant: ");
        name = scanner.nextLine();
        System.out.println("Enter username of tenant: ");
        username = scanner.nextLine();
        System.out.println("Enter temporary password of tenant: ");
        passwordHash = scanner.nextLine();
        System.out.println("Enter phone number of tenant: ");
        phoneNumber = scanner.nextLine();
        User user = new User(role, name, username, passwordHash, true, phoneNumber);
        try {
            userId = addUser(user);
        } catch (DuplicateUsernameException e) {
            throw new RuntimeException(e);
        }
        if (userId == -1) {
            showAdminHomepage();
            return;
        }
        Boolean isCurrentlyLivingThere = true;
        System.out.println("Enter property Id: ");
        propertyId = scanner.nextInt();
        boolean isValidProperty = propertyService.checkPropertyBelongsToUser(LoggedInUser.getUserId(), propertyId);
        if (!isValidProperty) {
            showAdminHomepage();
            return;
        }
        System.out.println("Enter rent: ");
        rent = scanner.nextFloat();
        Tenant tenant = new Tenant(userId, propertyId, rent, isCurrentlyLivingThere);
        tenantService.addTenant(tenant);
        System.out.println("Added tenant successfully!");
    }

    private void addProperty(PropertyImpl propertyService) {
        Integer userId = LoggedInUser.getUserId();
        String propertyType;
        String address;
        String city;
        String state;
        String status = "active";
        System.out.println("Select Property Type:");
        System.out.println("1. Villa    2. Flat    3. Plotted House");
        System.out.print("Enter choice between 1-3: ");
        int propertyTypeChoice = scanner.nextInt();
        if (propertyTypeChoice == 1) {
            propertyType = PropertyType.VILLA.getValue();
        } else if (propertyTypeChoice == 2) {
            propertyType = PropertyType.FLAT.getValue();
        } else if (propertyTypeChoice == 3) {
            propertyType = PropertyType.PLOTTED_HOUSE.getValue();
        } else {
            System.out.println("Wrong choice!");
            return;
        }
        System.out.println("Enter Address: ");
        scanner.nextLine();
        address = scanner.nextLine();
        System.out.println("Enter City: ");
        city = scanner.nextLine();
        System.out.println("Enter State: ");
        state = scanner.nextLine();
        Property property = new Property(userId, propertyType, address, city, state, status);
        propertyService.addProperty(property);
        System.out.println("Added property successfully!");
    }

    @Override
    public Integer addUser(User user) throws DuplicateUsernameException {
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE username = '" + user.getUsername()+"'");
            if (resultSet.next()) {
                throw new DuplicateUsernameException("Username already exists!");
            }
            int result = statement.executeUpdate("INSERT INTO users (role, name, username, password_hash, first_login, phone_number) VALUES ('"+user.getRole()+"','"+user.getName()+"','"+user.getUsername()+"','"+user.getPasswordHash()+"',"+user.getFirstLogin()+",'"+user.getPhoneNumber()+"')");
            if (result == 0) {
                System.out.println("Failed to create user in database!");
            }
            resultSet = statement.executeQuery("SELECT MAX(id) FROM users");
            while(resultSet.next()) {
                return Integer.parseInt(resultSet.getString(1));
            }
        } catch (Exception e) {
            System.err.println("Failed to create user in database! Reason: "  + e.getMessage());
        }
        return -1;
    }

    @Override
    public void signup() {
        Connection connection = JDBC.getConnection();
        try {
            Statement stmt = connection.createStatement();
            scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine();
            String role = Role.ADMIN.getValue();
            String hashedPassword = PasswordUtil.hashPassword(password);
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (role, name, username, password_hash, first_login, phone_number) VALUES (?, ?, ?, ?, true, ?)");
            ps.setString(1, role);
            ps.setString(2, name);
            ps.setString(3, username);
            ps.setString(4, hashedPassword);
            ps.setString(5, phone);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("[Sign Up Successful]");
            } else {
                System.out.println(" Failed to sign up.");
            }
            ps.close();
            stmt.close();
            MainClass.main(new String[]{});
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public boolean login() {
        boolean successfulLogin = false;
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Connection connection = JDBC.getConnection();

        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hashedInput = PasswordUtil.hashPassword(password);
                String storedHash = rs.getString("password_hash");

                if (hashedInput.equals(storedHash)) {
                    System.out.println("[Login Successful]");
                    successfulLogin = true;
                    LoggedInUser.setUserId(Integer.parseInt(rs.getString("id")));
                    LoggedInUser.setName(rs.getString("name"));
                    LoggedInUser.setRole(rs.getString("role").toUpperCase());

                    if (rs.getBoolean("first_login") && LoggedInUser.getRole() == Role.TENANT) {
                        System.out.println("(First time login)");
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        String newHash = PasswordUtil.hashPassword(newPassword);

                        PreparedStatement updatePs = connection.prepareStatement(
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
            return successfulLogin;
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        return successfulLogin;
    }

    @Override
    public void logout() {
        LoggedInUser.logout();
        MainClass.main(new String[]{});
        System.out.println(" Logged out successfully.");
    }
}
