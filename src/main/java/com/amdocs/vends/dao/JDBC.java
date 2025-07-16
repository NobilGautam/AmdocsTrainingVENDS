package com.amdocs.vends.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

public class JDBC {
    private static final Logger logger = Logger.getLogger(JDBC.class.getName());
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
                logger.info("Connection established to database");
            } else {
                logger.warning("Connection not established to database");
            }
            return connection;
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return null;
    }
}