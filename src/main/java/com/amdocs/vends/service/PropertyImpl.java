package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.PropertyIntf;

import java.sql.Connection;
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
}
