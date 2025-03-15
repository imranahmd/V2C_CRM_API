package com.crm.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BankStatementService {

	private static Logger log = LoggerFactory.getLogger(BankStatementService.class);

	
	@Value("${spring.datasource.url}")
	private String dbURL;

	@Value("${spring.datasource.username}")
	private String dbName;

	@Value("${spring.datasource.password}")
	private String dbPass;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	public String saveBankStatement(String txnId,String from_date,String to_date,String tran_type, String request, String response) {
		String responseVal = null;

		log.info("saveBankStatement::::::::::::::::::::");
		log.info("txnId:::::::::::::::::"+txnId);
		log.info("from_date:::::::::::::::::"+from_date);
		log.info("to_date:::::::::::::::::"+to_date);
		log.info("tran_type:::::::::::::::::"+tran_type);
		
		try (Connection connection = DriverManager.getConnection(dbURL, dbName, dbPass)) {
			log.info("driverClassName:::::::::::::" + driverClassName);
			log.info("dbPass:::::::::::::::::" + dbPass);
			log.info("dbName:::::::::::::" + dbName);
			log.info("dbURL:::::::::::" + dbURL);
			 Timestamp datetimeValue = new Timestamp(System.currentTimeMillis());
			 System.out.print("datetimeValue:::::"+datetimeValue);

			String sql = "INSERT INTO tbl_mstBankStatement (txn_id,from_date,to_date,tran_type, request_JSON,response_JSON,rodt) VALUES (?,?,?,?,CAST(? AS JSON),CAST(? AS JSON),?)";
			
			log.info("query:::::::::::::::::::::::::::::::"+sql);
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, txnId);
				preparedStatement.setString(2, from_date);
				preparedStatement.setString(3, to_date);
				preparedStatement.setString(4, tran_type);
				
				
				preparedStatement.setString(5, request);
				preparedStatement.setString(6, response);
				preparedStatement.setTimestamp(7, datetimeValue);
				int rowsAffected = preparedStatement.executeUpdate();
				log.info("rowsAffected::::::" + rowsAffected);

				if (rowsAffected == 1) {
					responseVal = "success";
				} else {
					responseVal = "fail";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseVal;

	}


	
	public String getbankStatementData(String fromDate,String toDate,String tranType) throws Exception {
		String jsonValue = null;
		 try (Connection connection = DriverManager.getConnection(dbURL, dbName, dbPass)) {
	           // String sql = "SELECT response_JSON FROM tbl_mstBankStatement WHERE txn_id = ?";
	         
			  String sql = "SELECT response_JSON FROM tbl_mstBankStatement WHERE from_date = ? and to_date=? and tran_type=?";

			 
			 try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	               // preparedStatement.setString(1, txn_id); 
	                preparedStatement.setString(1, fromDate); 
	                preparedStatement.setString(2, toDate); 
	                preparedStatement.setString(3, tranType); 
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                    if (resultSet.next()) {
	                         jsonValue = resultSet.getString("response_JSON");
	                        log.info("JSON Column Data: " + jsonValue);
	                        
	                    } else {
	                        log.info("No Data Found!");
	                        jsonValue="fail";
	                    }
	                }
	            }
	            return jsonValue;
	        } 
		catch (Exception e1) {
			log.info("e1:::::::::"+e1);
			throw new Exception();
		}
	}
}
