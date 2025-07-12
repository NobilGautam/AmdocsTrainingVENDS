package com.amdocs.vends.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaveRequestDAO {
    public static boolean createLeaveRequest(int userId) {
        String sql = "INSERT INTO leave_requests (user_id, request_date, status, approved_by_admin) VALUES (?, CURDATE(), 'Pending', 0)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                updateTenantStatus(userId, false);
                updatePropertyStatusByTenant(userId, "Vacant");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void updateTenantStatus(int userId, boolean isLivingThere) {
        String sql = "UPDATE tenant SET is_currently_living_there = ? WHERE user_id = ?";
        // Execute update with appropriate parameters
    }

    private static void updatePropertyStatusByTenant(int userId, String status) {
        String sql = "UPDATE property SET status = ? WHERE id = (SELECT property_id FROM tenant WHERE user_id = ?)";
        // Execute update with appropriate parameters
    }

}
