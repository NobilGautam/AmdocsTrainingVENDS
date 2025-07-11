package com.amdocs.vends.dao;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.bean.Tenant;
import java.sql.*;

public class PropertyDAO {

    public Property getPropertyByTenantUserId(int userId) {
        Property property = null;
        // Tenant tenant = null;
        try (Connection conn = JDBC.getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT p.id, p.address, p.city, p.state, t.rent, p.status " +
                                "FROM property p JOIN tenant t ON p.id = t.property_id " +
                                "WHERE t.user_id = ?")) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                property = new Property();
                property.setPropertyId(rs.getInt("id"));
                property.setAddress(rs.getString("address"));
                property.setCity(rs.getString("city"));
                property.setState(rs.getString("state"));
                property.setRent(rs.getFloat("rent"));
                property.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching property: " + e.getMessage());
        }
        return property;
    }
}
