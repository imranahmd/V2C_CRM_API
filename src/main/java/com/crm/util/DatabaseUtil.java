package com.crm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    // Method to create a connection
    public static Connection getConnection() throws SQLException {
      //String url = "jdbc:mysql://20.0.3.200:3306/payfiaudit?useSSL=false";
      //String username = "payfi-uat-user";
      //String password = "sdyrj2MEo5b7@b65sAD";
      
      // String url = "jdbc:mysql://localhost:3306/payfiaudit?useSSL=false&allowPublicKeyRetrieval=true";
      // String username = "root";
      // String password = "password";

        String url = "jdbc:mysql://35.154.88.220:3306/payfiaudit?useSSL=false";
        String username = "newuser";
        String password = "^ciE(46P0e$A";
//
//    	String url = "jdbc:mysql://30.0.139.41:3306/payfiaudit?useSSL=false";
//    	String username = "payfi-prod-user";
//    	String password = "kiuhaeuf7@b6kbefaERD"; 
    	
        // Load the MySQL driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure this matches your MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        // Establish and return the connection
        return DriverManager.getConnection(url, username, password);
    }
}
