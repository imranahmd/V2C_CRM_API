package com.crm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlParameter;


import java.sql.CallableStatement;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderservice {
	private static Logger log = LoggerFactory.getLogger(ServiceProviderservice.class);
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	public Map<String, Object> getsplist(String sp_name) {
		log.info("entered getsplist1+++++++============");

		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spData ;
		log.info("entered getsplist2+++++++============");

		spData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_getserviceproviderbysp_name(?)}");
				callableStatement.setString(1, sp_name);
				return callableStatement;
		}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spData.isEmpty()) {
			Object obj=spData.get("#result-set-1");
			 js1.put("Status", "Success");
			 js1.put("Message", "Data Found");
			 js1.put("data", obj);
		 }else {
			 js1.put("Status", "Error");
			 js1.put("Message", "Data Not Found");
		 }
	Msg = js1.toMap();
	return Msg;
	}

	public Map<String, Object> getbank() {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> bankData ;
			bankData =JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call GetBankID_Name()}");
				return callableStatement;
			}, prmtrsList); 
			if(!bankData.isEmpty()) {
				Object obj=bankData.get("#result-set-1");
				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", obj);
			 }else {
				 js1.put("Status", "Error");
				 js1.put("Message", "Data Not Found");
			 }
		Msg = js1.toMap();
		return Msg;
		
		}
	
	
	public String insertrecord_Sp(String sp_name,String sp_class_invoker,String instrumentIds,String bankIds,String master_mid,String master_tid,String api_key,String udf_1,String udf_2,String udf_3,String udf_4,String udf_5,String refund_processor,String cutoff) {
		String responseVal=null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		Map<String,Object> resultData;
		try {
		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_insertserviceprovider(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, sp_name);
			callableStatement.setString(2, sp_class_invoker);
			callableStatement.setString(3, instrumentIds);
			callableStatement.setString(4, bankIds);
			callableStatement.setString(5, master_mid);
			callableStatement.setString(6, master_tid);
			callableStatement.setString(7, api_key);
			callableStatement.setString(8, udf_1);
			callableStatement.setString(9, udf_2);
			callableStatement.setString(10, udf_3);
			callableStatement.setString(11, udf_4);
			callableStatement.setString(12, udf_5);
			callableStatement.setString(13, refund_processor);
			callableStatement.setString(14, cutoff);

			return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		System.out.print("roledata::::::::::::::: " + resultData);
		Object val = resultData.get("#inserted-set-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		if (s2 != "0") {
			responseVal = "success";

		} else {
			responseVal = "fail";
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseVal;
	}
	
	public String Updatesp(Long sp_id,String sp_name,String sp_class_invoker,String instrumentIds,String bankIds,String master_mid,String master_tid,String api_key,String udf_1,String udf_2,String udf_3,String udf_4,String udf_5,String refund_processor,String cutoff) {
		String responseVal=null;
		
	
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		Map<String,Object> resultData;
		try {
		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_UpdateServiceProvider(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setLong(1,sp_id);
			callableStatement.setString(2, sp_name);
			callableStatement.setString(3, sp_class_invoker);
			callableStatement.setString(4, instrumentIds);
			callableStatement.setString(5, bankIds);
			callableStatement.setString(6, master_mid);
			callableStatement.setString(7, master_tid);
			callableStatement.setString(8, api_key);
			callableStatement.setString(9, udf_1);
			callableStatement.setString(10, udf_2);
			callableStatement.setString(11, udf_3);
			callableStatement.setString(12, udf_4);
			callableStatement.setString(13, udf_5);
			callableStatement.setString(14, refund_processor);
			callableStatement.setString(15, cutoff);

			return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		System.out.print("roledata::::::::::::::: " + resultData);
		Object val = resultData.get("#inserted-set-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		if (s2 != "0") {
			responseVal = "success";
		
					} else {
			responseVal = "fail";
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseVal;
	}
	
	
}
