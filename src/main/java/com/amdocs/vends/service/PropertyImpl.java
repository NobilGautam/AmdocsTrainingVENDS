package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PropertyIntf;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PropertyImpl implements PropertyIntf {
    @Override
    public void addProperty(Property property) {
        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate("INSERT INTO property (user_id, property_type, address, city, state, status) VALUES ('" + property.getUserId() + "','" + property.getPropertyType() + "','" + property.getAddress() + "','" + property.getCity() + "','" + property.getState() + "','" + property.getStatus() + "')");
            if (result == 0) {
                System.out.println("Failed to add property!");
            }
        } catch (Exception e) {
            System.out.println("Failed to add property! Reason: " + e);
        }
    }
    
    public void showPropertyDetails() {
        try {
            Connection con = JDBC.getConnection();
            String query = "SELECT p.id AS property_id, p.address, p.city, p.state, p.property_type, " +
                           "t.id AS tenant_id, u.name AS tenant_name, t.rent, t.is_currently_living_there " +
                           "FROM property p " +
                           "LEFT JOIN tenant t ON p.id = t.property_id " +
                           "LEFT JOIN users u ON t.user_id = u.id " +
                           "WHERE p.user_id = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, LoggedInUser.getUserId());

            ResultSet rs = ps.executeQuery();

            System.out.println("-------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-15s %-10s %-10s %-20s %-10s %-10s\n", 
                "PID", "Address", "City", "State", "Type", "Tenant Name", "Rent", "Living?");
            System.out.println("-------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-15s %-10s %-10s %-20s %-10.2f %-10s\n",
                    rs.getInt("property_id"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("property_type"),
                    rs.getString("tenant_name") != null ? rs.getString("tenant_name") : "N/A",
                    rs.getFloat("rent"),
                    rs.getBoolean("is_currently_living_there") ? "Yes" : "No"
                );
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("[ERROR: Failed to fetch property-tenant details] " + e.getMessage());
        }
    }

	

}
