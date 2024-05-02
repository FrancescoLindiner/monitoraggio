package com.progetto.monitoraggio;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        String connectionURL = "jdbc:mysql://localhost:3306/ProgettoWSDA?useLegacyDatetimeCode=false&serverTimezone=Europe/Rome";
        Connection connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionURL, "root", "root");
        } catch (Exception exc) {
            connection = null;
        }
        return connection;
    }
}
