package com.crm.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;


@Repository
public class MerchantMdrRepo {


	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	

	public List<String> getDataByMerchantId(String merchantId) {
		
		 List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
			
		    Map<String, Object> resultData = jdbcTemplate.call(connection -> {
	            CallableStatement callableStatement = connection.prepareCall("{call pro_getMerchantMdrList(?)}");
	            callableStatement.setString(1, merchantId);
	            return callableStatement;
	        }, parameters);
			
		    ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-1");
		    
		    
		    if(arrayList.isEmpty()) return null;
		    	
		    	

		    return arrayList;
	}
	
	public List<String> getrecordCount(String fromDate, String toDate, String merchantId, String resellerId, String name, int norecord, int pageno, String sp) {
		
		 List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
			
		    Map<String, Object> resultData = jdbcTemplate.call(connection -> {
	            CallableStatement callableStatement = connection.prepareCall("{call pro_MerchantListV2(?,?,?,?,?,?,?,?)}");
	            callableStatement.setString(1, fromDate);
	            callableStatement.setString(2, toDate);
	            callableStatement.setString(3, merchantId);
	            callableStatement.setString(4, resellerId);
	            callableStatement.setString(5, name);
	            callableStatement.setInt(6, norecord);
	            callableStatement.setInt(7, pageno);
	            callableStatement.setString(8, sp);
	            return callableStatement;
	        }, parameters);
			
		    ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");
		    
		    
		    if(arrayList.isEmpty()) return null;
		    	
		    

		    return arrayList;
	}
	
	public List<String> getrecordCountRefund(String merchantId, String fromDate, String toDate,String id,String bankId,String custMobile,String custMail,int pageNumber,int pageRecords,String txnId) {
		
		 List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
			
		    Map<String, Object> resultData = jdbcTemplate.call(connection -> {
	            CallableStatement callableStatement = connection.prepareCall("{call pro_GetRefundApplyListFiltersV2(?,?,?,?,?,?,?,?,?,?)}");
	            callableStatement.setString(1, merchantId);
	            callableStatement.setString(2, fromDate);
	            callableStatement.setString(3, toDate);
	            callableStatement.setString(4, id);
	            callableStatement.setString(5, bankId);
	            callableStatement.setString(6, custMobile);
	            callableStatement.setString(7, custMail);
	            callableStatement.setInt(8, pageNumber);
	            callableStatement.setInt(9, pageRecords);
	            callableStatement.setString(10, txnId);
	            return callableStatement;
	        }, parameters);
			
		    ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");
		    
		    
		    if(arrayList.isEmpty()) return null;
		    	
		    

		    return arrayList;
	}
	public List<String> getrecordResellerCount(String fromDate, String toDate, String merchantId, String name, int norecord, int pageno) {
		
		 List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
			
		    Map<String, Object> resultData = jdbcTemplate.call(connection -> {
	            CallableStatement callableStatement = connection.prepareCall("{call pro_ResellerList(?,?,?,?,?,?)}");
	            callableStatement.setString(1, fromDate);
	            callableStatement.setString(2, toDate);
	            callableStatement.setString(3, merchantId);	            
	            callableStatement.setString(4, name);
	            callableStatement.setInt(5, norecord);
	            callableStatement.setInt(6, pageno);
	            return callableStatement;
	        }, parameters);
			
		    ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");
		    
		    
		    if(arrayList.isEmpty()) return null;
		    	
		    

		    return arrayList;
	}
}
