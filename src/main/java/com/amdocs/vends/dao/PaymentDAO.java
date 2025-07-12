package com.amdocs.vends.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAO {
    public static boolean requestRentPayment(int userId, double rentPaid, String rentForMonth) {
        String sql = "INSERT INTO payments (user_id, rent_paid, date_of_payment, rent_for_month, approved_by_admin) VALUES (?, ?, CURDATE(), ?, 0)";
        try (Connection conn = JDBC.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDouble(2, rentPaid);
            ps.setString(3, rentForMonth);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
