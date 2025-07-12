package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PaymentIntf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentImpl implements PaymentIntf {
    Scanner scanner = new Scanner(System.in);
    Connection connection = JDBC.getConnection();

    @Override
    public void showPendingPaymentsAndApprove() {
        String query = "SELECT pay.id, u.name, pay.rent_paid, pay.date_of_payment, pay.rent_for_month " +
                "FROM payments pay " +
                "JOIN users u ON pay.user_id = u.id " +
                "WHERE pay.approved_by_admin = false";

        try (
             Statement stmt = connection.createStatement();
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
}
