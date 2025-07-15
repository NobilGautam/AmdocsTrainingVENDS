package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PropertyIntf;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.sql.*;
import java.sql.ResultSet;

public class PropertyImpl implements PropertyIntf {
    Connection connection = JDBC.getConnection();

    @Override
    public void addProperty(Property property) {
        try {
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

    @Override
    public boolean checkPropertyBelongsToUser(Integer userId, Integer propertyId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT p.id AS property_id, p.property_type, p.address, p.city, p.state FROM users u LEFT JOIN property p ON u.id = p.user_id WHERE p.user_id = " + userId + " AND p.id = " + propertyId);
            if (!resultSet.next()) {
                System.out.println("No property of yours exists with property ID: " + propertyId);
                System.out.println("Confirm property details by checking through menu and try again");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void viewPropertyDetails() {
        try (PreparedStatement ps = connection.prepareStatement(
                     "SELECT p.id, p.address, p.city, p.state, t.rent, p.status " +
                             "FROM property p JOIN tenant t ON p.id = t.property_id " +
                             "WHERE t.user_id = ?")) {
            ps.setInt(1, LoggedInUser.getUserId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Property property = new Property();
                property.setPropertyId(rs.getInt("id"));
                property.setAddress(rs.getString("address"));
                property.setCity(rs.getString("city"));
                property.setState(rs.getString("state"));
                property.setRent(rs.getFloat("rent"));
                property.setStatus(rs.getString("status"));
                System.out.println("\nProperty ID: " + property.getPropertyId());
                System.out.println("Address: " + property.getAddress() +
                        ", City: " + property.getCity() +
                        ", State: " + property.getState());
                System.out.println("Rent: Rs." + property.getRent());
                System.out.println("Status: " + property.getStatus());
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error fetching property: " + e.getMessage());
        }
    }
}
