package com.amdocs.vends.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import com.amdocs.vends.utils.LogUtil;

public class JDBC {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            connection = connectToDatabase();
        }
        return connection;
    }

    private JDBC() {}

    private static Connection connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/amdocstraining", "root", "Nobil@0711");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            if (resultSet.next()) {
                LogUtil.info("Connection established to database");
            } else {
                LogUtil.warn("Connection not established to database");
            }
            return connection;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
        }
        return null;
    }
}