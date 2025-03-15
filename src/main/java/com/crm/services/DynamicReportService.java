package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;


@Service
public class DynamicReportService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	public Map<String, Object> getReportsData(int reportType, int reportId, String userType, String userId,
			String inputParameters) throws Exception {

		System.out.println("reportType = " + reportType);
		System.out.println("reportId = " + reportId);
		System.out.println("userType = " + userType);
		System.out.println("userId = " + userId);
		System.out.println("inputParameters = " + inputParameters);
		Map<String, Object> resultData = new HashedMap<>();
		try {
			List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
			prmtrsList.add(new SqlParameter(Types.INTEGER));
			prmtrsList.add(new SqlParameter(Types.BIGINT));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));
			

			resultData = jdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_DynamicReports(?,?,?,?,?)}");

				callableStatement.setInt(1, Integer.valueOf(reportType));
				callableStatement.setLong(2, Long.valueOf(reportId));
				callableStatement.setString(3, userType);
				callableStatement.setString(4, userId);
				callableStatement.setString(5, inputParameters);
				return callableStatement;
			}, prmtrsList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultData;
	}
	
	public Map<String, Object> getInvoiceReportsData(String reportType,String reportId,String userType,String userId,String inputParameters) throws Exception {
		System.out.println("reportType = "+reportType);
		System.out.println("reportId = "+reportId);
		System.out.println("userType = "+userType);
		System.out.println("userId = "+userId);
		System.out.println("inputParameters = "+inputParameters);
		Map<String, Object> resultData = new HashedMap<>();
		try {
			List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
			prmtrsList.add(new SqlParameter(Types.INTEGER));
			prmtrsList.add(new SqlParameter(Types.BIGINT));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));
			prmtrsList.add(new SqlParameter(Types.VARCHAR));

			if(reportId.equals("1")) {
				resultData = jdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection.prepareCall("{call pro_DynamicInvoiceReports(?,?,?,?,?)}");
					 callableStatement.setInt(1, Integer.valueOf(reportType) );
						callableStatement.setLong(2, Long.valueOf(reportId));
						callableStatement.setString(3, userType);
						callableStatement.setString(4, userId);
						callableStatement.setString(5, inputParameters);
					 return callableStatement;
				}, prmtrsList);
				
			}
				if(reportId.equals("5")) {
					resultData = jdbcTemplate.call(connection -> {
						CallableStatement callableStatement = connection.prepareCall("{call pro_DynamicInvoiceReports(?,?,?,?,?)}");
						callableStatement.setInt(1, Integer.valueOf(reportType) );
						callableStatement.setLong(2, Long.valueOf(reportId));
						callableStatement.setString(3, userType);
						callableStatement.setString(4, userId);
						callableStatement.setString(5, inputParameters);
						 return callableStatement;
					}, prmtrsList);
			}	
			
			
			/*resultData = jdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_DynamicReports(?,?,?,?,?)}");
			*/	//System.out.println(callableStatement);
				
				
				
			
			

		} catch (Exception e) {
			e.printStackTrace();
		} 
			//System.out.println("resultData size = "+resultData.size());
			
			
			return resultData;
	}
	public Map<String, Object> getLifeCycleList(String MerchantId, String FromDate, String ToDate, String SpId, String LifeCyclecode, String TransactionId, String RrnNumber){
		Map<String, Object> resultData = new HashedMap<>();
		try {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		resultData = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_displaytranslifecycle(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, MerchantId );
			callableStatement.setString(2, FromDate);
			callableStatement.setString(3, ToDate);
			callableStatement.setString(4, SpId);
			callableStatement.setString(5, LifeCyclecode);
			callableStatement.setString(6, TransactionId);
			callableStatement.setString(7, RrnNumber);
			
			System.out.println("callableStatement:::"+callableStatement.toString());
			 return callableStatement;
		}, prmtrsList);
		}catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("\n\n:::::::::::::::::resultData:"+resultData);
		return resultData;
	}
}
