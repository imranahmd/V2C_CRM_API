package com.crm.ServiceDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crm.Controller.BankDetailsController;
import com.crm.util.DatabaseUtil;

public class SecondCon {
	private static final Logger log = LoggerFactory.getLogger(SecondCon.class);

    public void insertIntoSecondSchema(String username, String action, String json, String jsonData, String ip) {
    	
    	log.info("insertIntoSecondSchema:::::::::::::::::::::::::::::::::::::::::::");
      //  String sql = "INSERT INTO tbl_audit_tail (user_name, action_name, audit_data, ip_address,updated_date) VALUES (?, ?, ?, ?,NOW())";
       
        String sql = "INSERT INTO tbl_audit_tail (user_name, action_name, audit_data, data_new, ip_address, updated_date) VALUES (?, ?, ?, ?, ?, NOW())";

        
        log.info("::::::::::::::::::::::::::::::::::::::::sql:::"+sql);
        // Use try-with-resources to ensure resources are closed automatically
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, action);
            preparedStatement.setString(3, json);
            preparedStatement.setString(4, jsonData);
            preparedStatement.setString(5, ip);
            log.info("::::::::::::::::::::::::::::::::::::::::sql:::"+sql);
            log.info("::::::::::::::::::::::::::::::::::::::::preparedStatement:::"+preparedStatement.toString());
            // Execute the update
            preparedStatement.executeUpdate();
            System.out.println("Insert successful");
            
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately
            e.printStackTrace(); // You might want to use a logger here
        }
    }
}
