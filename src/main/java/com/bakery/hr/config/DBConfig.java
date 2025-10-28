package com.bakery.hr.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    private static final String URL  = "jdbc:mysql://localhost:3306/bakery_hr?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "devuser";   // or "root"
    private static final String PASS = "Mypassword@02";   // your password

    static {
        // Optional with modern drivers, but safe for older setups
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } 
        catch (ClassNotFoundException e) { throw new RuntimeException("MySQL driver not found", e); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
