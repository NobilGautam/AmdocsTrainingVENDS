package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PaymentIntf;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentImpl implements PaymentIntf {
    Scanner scanner = new Scanner(System.in);
    Connection connection = JDBC.getConnection();

    @Override
    public void showPendingPaymentsAndApprove() {
        String query = "WITH owner_properties AS (\n" +
                "    SELECT * FROM property p WHERE p.user_id = ?\n" +
                "),\n" +
                "owner_tenants AS (\n" +
                "    SELECT t.user_id FROM owner_properties op JOIN tenant t ON t.property_id = op.id\n" +
                "),\n" +
                "owner_tenants_with_name AS (\n" +
                "    SELECT ot.user_id, u.name FROM owner_tenants ot JOIN users u ON u.id = ot.user_id\n" +
                ")\n" +
                "SELECT \n" +
                "    pay.id, otwn.name, pay.rent_paid, pay.date_of_payment, pay.rent_for_month\n" +
                "FROM \n" +
                "    payments pay \n" +
                "JOIN \n" +
                "    owner_tenants_with_name otwn ON pay.user_id = otwn.user_id;\n";

        try (
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, LoggedInUser.getUserId());
            ResultSet rs = stmt.executeQuery();
            List<Integer> ids = new ArrayList<>();
            System.out.println("\n--- Pending Rent Payments ---");

            while (rs.next()) {
                int id = rs.getInt("id");
                ids.add(id);
                System.out.println("Payment ID: " + id +
                        ", Tenant: " + rs.getString("name") +
                        ", Amount: ₹" + rs.getDouble("rent_paid") +
                        ", Month: " + rs.getString("rent_for_month") +
                        ", Date: " + rs.getDate("date_of_payment"));
            }

            if (ids.isEmpty()) {
                System.out.println("No pending payments to approve.");
                return;
            }

            System.out.print("Enter Payment ID to approve (or 0 to skip): ");
            int pid = Integer.parseInt(scanner.nextLine());

            if (pid != 0 && ids.contains(pid)) {
                PreparedStatement ps = connection.prepareStatement("UPDATE payment SET approved_by_admin = true WHERE id = ?");
                ps.setInt(1, pid);
                ps.executeUpdate();
                System.out.println("Payment ID " + pid + " approved.");
            } else if (pid != 0) {
                System.out.println("Invalid Payment ID.");
            }

        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void payRent() {
        System.out.print("Enter Rent Amount: Rs.");
        float rentPaid = scanner.nextFloat();
        scanner.nextLine();

        System.out.print("Enter month in words(e.g. July, August): ");
        String rentMonth = scanner.nextLine().toLowerCase();

        // 2. Insert payment request into the database
        boolean success = false;
        String sql = "INSERT INTO payments (user_id, rent_paid, date_of_payment, rent_for_month, approved_by_admin) VALUES (?, ?, CURDATE(), ?, 0)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, LoggedInUser.getUserId());
            ps.setDouble(2, rentPaid);
            ps.setString(3, rentMonth);
            int rows = ps.executeUpdate();
            success = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        if (success) {
            System.out.println("[Request Sent for Approval]");
        } else {
            System.out.println("Failed to send payment request. Please try again.");
        }
    }
}
