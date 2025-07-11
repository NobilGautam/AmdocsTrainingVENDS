package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    public List<String> getTenantPropertyDetails() {
        List<String> result = new ArrayList<>();
        String query = "SELECT t.id AS tenant_id, u.name AS tenant_name, u.phone_number, " +
                       "p.address, p.city, p.property_type, t.rent " +
                       "FROM tenant t " +
                       "JOIN users u ON t.user_id = u.id " +
                       "JOIN property p ON t.property_id = p.id";

        try (Connection con = JDBC.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String line = "Tenant ID: " + rs.getInt("tenant_id") +
                              ", Name: " + rs.getString("tenant_name") +
                              ", Phone: " + rs.getString("phone_number") +
                              ", Property: " + rs.getString("address") + ", " + rs.getString("city") +
                              ", Type: " + rs.getString("property_type") +
                              ", Rent: ₹" + rs.getDouble("rent");
                result.add(line);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        return result;
    }

    public void showPendingPaymentsAndApprove(Scanner sc) {
        String query = "SELECT pay.id, u.name, pay.rent_paid, pay.date_of_payment, pay.rent_for_month " +
                       "FROM payment pay " +
                       "JOIN users u ON pay.user_id = u.id " +
                       "WHERE pay.approved_by_admin = false";

        try (Connection con = JDBC.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

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
            int pid = Integer.parseInt(sc.nextLine());

            if (pid != 0 && ids.contains(pid)) {
                PreparedStatement ps = con.prepareStatement("UPDATE payment SET approved_by_admin = true WHERE id = ?");
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

}
