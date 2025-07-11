package com.amdocs.vends.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/amdocstraining", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            if (resultSet.next()) {
                System.out.println("[LOG: Connection established to database]");
            } else {
                System.out.println("[LOG: Connection not established to database]");
            }
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}