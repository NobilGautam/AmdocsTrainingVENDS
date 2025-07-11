package com.amdocs.vends.service;

import com.amdocs.vends.bean.Tenant;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.interfaces.TenantIntf;

import java.sql.Connection;
import java.sql.Statement;

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
}
