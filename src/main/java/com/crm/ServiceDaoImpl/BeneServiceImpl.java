package com.crm.ServiceDaoImpl;

import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.dto.payout_request.PaymentRequest;
import com.crm.services.BenefieryService;
import com.google.gson.Gson;

@Service
public class BeneServiceImpl {
	private static Logger log = LoggerFactory.getLogger(BeneServiceImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String AddBeneBeneficery(String merchant_id,String account_num,String retype_accountnum,String ifsc_code,String bank_name,String account_holder,String mobile_no,String email_id) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());
		log.info("Add AddBeneBeneficery ::::::::::::" + retype_accountnum);

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> GetBeneList = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_AddBeneDetails(?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, merchant_id);
			callableStatement.setString(2, account_num);
			callableStatement.setString(3, ifsc_code);
			callableStatement.setString(4, bank_name);
//			callableStatement.setDate(5, dateDB);
			callableStatement.setString(5, account_holder);
			callableStatement.setString(6, mobile_no);
			callableStatement.setString(7, email_id);
			callableStatement.setString(8, retype_accountnum);



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) GetBeneList.get("#result-set-1");
		log.info("Response from db leve::::::::::::::::::"+arrayList);
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
	
}
	public String IndussPayResponseAddBene(String Merchant_Id,String BeneId,String Account,String paymentstatus,String statsu) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> GetBeneList = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_indussPayResponse(?,?,?,?,?)}");
			callableStatement.setString(1, Merchant_Id);
			callableStatement.setString(2, BeneId);
			callableStatement.setString(3, Account);
			callableStatement.setString(4, paymentstatus);
//			callableStatement.setDate(5, dateDB);
			callableStatement.setString(5, statsu);
		



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) GetBeneList.get("#result-set-1");
		log.info("Response from db leve::::::::::::::::::"+arrayList);
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public String CreatePayoutRequestIn(PaymentRequest Req,String RequestNo) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

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

		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> GetBeneList = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_createPayRequest(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, RequestNo);
			callableStatement.setString(2, Req.getClientTxnId());
			callableStatement.setString(3, Req.getTxnMode());
			callableStatement.setString(4, Req.getAccount_number());
			callableStatement.setString(5, Req.getAccount_Ifsc());
			callableStatement.setString(6, Req.getBank_name());
			callableStatement.setString(7, Req.getAccount_holder_name());
			callableStatement.setString(8, Req.getBeneficiary_name());
			callableStatement.setString(9, Req.getVpa());
			callableStatement.setString(10, Req.getAdf1());
			callableStatement.setString(11, Req.getAdf2());
			callableStatement.setString(12, Req.getAdf3());
			callableStatement.setString(13, Req.getAdf4());
			callableStatement.setString(14, Req.getAdf5());
			callableStatement.setString(15, Req.getAmount());


			
		



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) GetBeneList.get("#result-set-1");
		log.info("Response from db leve::::::::::::::::::"+arrayList);
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
			log.info("Response from db leve:::::::: :::::::::"+jsonArray);

		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public String RaisedPayoutBulk(String Merchant_Id,String Customer_Id,String ClientRefId,String Amount,String idescription,String txn_mode) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> GetBeneList = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_RaisedPayoutRequest(?,?,?,?,?,?)}");
			callableStatement.setString(1, Merchant_Id);
			callableStatement.setString(2, Customer_Id);
			callableStatement.setString(3, ClientRefId);
			callableStatement.setString(4, Amount);
//			callableStatement.setDate(5, dateDB);
			callableStatement.setString(5, idescription);
			callableStatement.setString(6, txn_mode);



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) GetBeneList.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}

	public String GetBeneList(String merchant_id) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = jdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_GetBeneList(?)}");
			callableStatement.setString(1, merchant_id);
			
			



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	}
}
