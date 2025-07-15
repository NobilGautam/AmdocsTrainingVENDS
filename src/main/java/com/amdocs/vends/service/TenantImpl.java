package com.amdocs.vends.service;

import com.amdocs.vends.bean.Tenant;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.TenantIntf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TenantImpl implements TenantIntf {

    @Override
    public void addTenant(Tenant tenant) {
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("INSERT INTO tenant (user_id, property_id, rent, is_currently_living_there) VALUES ("+tenant.getUserId()+","+tenant.getPropertyId()+","+tenant.getRent()+","+tenant.getCurrentlyLivingThere()+")");
            if (result == 0) {
                System.out.println("Failed to add tenant!");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
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
}
