package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
@Service
public class ServiceProviderBankMappingService {

	private static Logger log = LoggerFactory.getLogger(ServiceProviderBankMappingService.class);

	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	public Map<String, Object> getbankspmlist(String bank_id) {
		log.info("entered getbankspmlist+++++++============");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spmData ;
		log.info("entered getbankspmlis2t+++++++============");

		spmData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_GetBank_MapperList(?)}");
				callableStatement.setString(1, bank_id);
				return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spmData.isEmpty()) {
			Object obj=spmData.get("#result-set-1");
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

	public Map<String, Object> getbankcategoryspmlist(String bank_id) {
		log.info("entered getbankcategoryspmlist+++++++============");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spmData ;
		log.info("entered getbankcategoryspmlist2+++++++============");

		spmData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_GetBank_Category_MapperList(?)}");
				callableStatement.setString(1, bank_id);
				return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spmData.isEmpty()) {
			Object obj=spmData.get("#result-set-1");
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

	public String insertbankcategory(String sp_id, String mid, String bank_id, String categoryId,
			String min_mdr_amt, String base_rate, String mdr_type, String Instrument_id, String Min_amt,
			String Max_amt, String createdOn, String CreatedBy, String modifiedOn) {
		String responseVal=null;
		log.info("entered insertbankcategory+++++++============");

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
		
		Map<String,Object> resultData;
		try {
			log.info("entered insertbankcategory   try +++++++============");

		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_InsertServiceProviderBankCategoryMapping(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, sp_id);
			callableStatement.setString(2, bank_id);
			callableStatement.setString(3, categoryId);
			callableStatement.setString(4, min_mdr_amt);
			callableStatement.setString(5, base_rate);
			callableStatement.setString(6, mdr_type);
			callableStatement.setString(7, Instrument_id);
			callableStatement.setString(8,Min_amt);
			callableStatement.setString(9, Max_amt);
			callableStatement.setString(10, createdOn);
			callableStatement.setString(11, CreatedBy);
			callableStatement.setString(12, modifiedOn);
			callableStatement.setString(13, mid);
			

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

	public Map<String, Object> getMerchantIds() {
		log.info("entered getbankcategoryspmlist+++++++============");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spmData ;
		log.info("entered getbankcategoryspmlist2+++++++============");

		spmData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_MerchantIdList()}");
				
				return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spmData.isEmpty()) {
			Object obj=spmData.get("#result-set-1");
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

	public Map<String, Object> getspIdName() {
		log.info("entered getspIdName+++++++============");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spmData ;
		log.info("entered getspIdName+++++++============");

		spmData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_SP_Id_Name()}");
				
				return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spmData.isEmpty()) {
			Object obj=spmData.get("#result-set-1");
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

	public Map<String, Object> getcategoryId() {
		log.info("entered getcategoryId+++++++============");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		Map<String, Object> spmData ;
		log.info("entered getcategoryId2+++++++============");

		spmData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_getcategoryIdList()}");
				
				return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		if(!spmData.isEmpty()) {
			Object obj=spmData.get("#result-set-1");
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

	public String UpdatespmbankcategoryMapping(String id, String sp_id, String mid, String bank_id, String categoryId,
			String min_mdr_amt, String base_rate, String mdr_type, String instrument_id, String min_amt, String max_amt,
			String createdOn, String createdBy, String modifiedOn) {
		String responseVal=null;
		log.info("entered UpdatespmbankcategoryMapping+++++++============");

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
			log.info("entered UpdatespmbankcategoryMapping   try +++++++============");

		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_UpdateSPBankCategory(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, id);
			callableStatement.setString(2, sp_id);
			callableStatement.setString(3, bank_id);
			callableStatement.setString(4, categoryId);
			callableStatement.setString(5, min_mdr_amt);
			callableStatement.setString(6, base_rate);
			callableStatement.setString(7, mdr_type);
			callableStatement.setString(8, instrument_id);
			callableStatement.setString(9,min_amt);
			callableStatement.setString(10, max_amt);
			callableStatement.setString(11, createdOn);
			callableStatement.setString(12, createdBy);
			callableStatement.setString(13, modifiedOn);
			callableStatement.setString(14, mid);
			
			

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

	public String deletesp_bank_category(Long id) {
		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> resultData ;
		try {
		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_delete_sp_bank_category_mappingbyId(?)}");
				callableStatement.setLong(1, id);
				
				return callableStatement;
		}, prmtrsList);
		
		
		System.out.print("resultData::::::::::::::: " + resultData);
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

	public String insertbankmapping(String sp_id, String bank_id, String sp_bank_id, String base_rate, String mdr_type, String preference, String channel_id, String instrument_id, String min_amt,
			String max_amt) {
		String responseVal=null;
		log.info("entered insertbankmapping+++++++============");

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
		
		
		Map<String,Object> resultData;
		try {
			log.info("entered insertbankmapping   try +++++++============");

		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_insert_sp_bank_mapping(?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, sp_id);
			callableStatement.setString(2, bank_id);
			callableStatement.setString(3, sp_bank_id);
			callableStatement.setString(4, base_rate);
			callableStatement.setString(5, mdr_type);
			callableStatement.setString(6, preference);
			callableStatement.setString(7, channel_id);
			callableStatement.setString(8, instrument_id);
			callableStatement.setString(9,min_amt);
			callableStatement.setString(10, max_amt);
			
			

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

	public String UpdatespmbankMapping(String id, String sp_id, String bank_id, String sp_bank_id, String base_rate,
			String mdr_type, String preference, String channel_id, String instrument_id, String min_amt, String max_amt) {
		String responseVal=null;
		log.info("entered UpdatespmbankcategoryMapping+++++++============");

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
		
		Map<String,Object> resultData;
		try {
			log.info("entered UpdatespmbankcategoryMapping   try +++++++============");

		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_UpdateServiceProviderMapping(?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, id);
			callableStatement.setString(2, sp_id);
			callableStatement.setString(3, bank_id);
			callableStatement.setString(4, sp_bank_id);
			callableStatement.setString(5, base_rate);
			callableStatement.setString(6, mdr_type);
			callableStatement.setString(7, preference);
			callableStatement.setString(8, channel_id);
			callableStatement.setString(9, instrument_id);
			callableStatement.setString(10,min_amt);
			callableStatement.setString(11, max_amt);
			
			
			

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

	public String deletesp_bank_mapping(Long id) {
		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> resultData ;
		try {
		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_delete_sp_bank__mappingbyId(?)}");
				callableStatement.setLong(1, id);
				
				return callableStatement;
		}, prmtrsList);
		
		
		System.out.print("resultData::::::::::::::: " + resultData);
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
