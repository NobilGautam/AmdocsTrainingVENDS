package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PropertyIntf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
}
