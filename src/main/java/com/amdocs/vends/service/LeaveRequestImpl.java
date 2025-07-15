package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.LeaveRequestIntf;
import com.amdocs.vends.utils.enums.RequestStatus;
import com.amdocs.vends.utils.singleton.LoggedInUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LeaveRequestImpl implements LeaveRequestIntf {
    Scanner scanner = new Scanner(System.in);
    Connection connection = JDBC.getConnection();

    @Override
    public void approveLeaveRequest() {
        String query = "SELECT lr.id, u.name AS tenant_name, p.address, lr.request_date, lr.status " +
                "FROM leave_requests lr " +
                "JOIN tenant t ON lr.user_id = t.user_id " +
                "JOIN users u ON t.user_id = u.id " +
                "JOIN property p ON t.property_id = p.id " +
                "WHERE lr.status = 'PENDING' AND lr.approved_by_admin = 0";
        try (
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            List<Integer> requestIds = new ArrayList<>();
            System.out.println("\n--- Pending Leave Requests ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                requestIds.add(id);
                System.out.println("Request ID: " + id +
                        ", Tenant: " + rs.getString("tenant_name") +
                        ", Property: " + rs.getString("address") +
                        ", Date: " + rs.getDate("request_date"));
            }
            if (requestIds.isEmpty()) {
                System.out.println("No pending leave requests.");
                return;
            }
            System.out.print("Enter Request ID to approve (or 0 to skip): ");
            int idToApprove = Integer.parseInt(scanner.nextLine());
            if (idToApprove != 0 && requestIds.contains(idToApprove)) {
                PreparedStatement approvePs = connection.prepareStatement(
                        "UPDATE leave_requests SET status = 'APPROVED', approved_on = CURRENT_DATE, approved_by_admin = 1 WHERE id = ?"
                );
                approvePs.setInt(1, idToApprove);
                approvePs.executeUpdate();
                PreparedStatement updateTenant = connection.prepareStatement(
                        "UPDATE tenant SET is_currently_living_there = 0 " +
                                "WHERE user_id = (SELECT user_id FROM leave_requests WHERE id = ?)"
                );
                updateTenant.setInt(1, idToApprove);
                updateTenant.executeUpdate();
                System.out.println("Leave request approved and tenant status updated.");
            } else if (idToApprove != 0) {
                System.out.println("Invalid request ID.");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    @Override
    public void vacateProperty() {
        System.out.print("Confirm Vacate Property? [Y/N]: ");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Vacate request cancelled.");
            return;
        }
        boolean requestSuccess = false;
        String sql = "INSERT INTO leave_requests (user_id, request_date, status, approved_by_admin) VALUES (?, CURDATE(), ?, 0)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, LoggedInUser.getUserId());
            ps.setString(2, RequestStatus.PENDING.getValue());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                sql = "UPDATE tenant SET is_currently_living_there = ? WHERE user_id = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, 0);
                ps.setInt(2, LoggedInUser.getUserId());
                ps.executeUpdate();
                //todo: add exception handling for above ps statement
                requestSuccess = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (requestSuccess) {
            System.out.println("[Leave Request Submitted]");
        } else {
            System.out.println("Failed to submit leave request. Please try again.");
        }
    }
}
