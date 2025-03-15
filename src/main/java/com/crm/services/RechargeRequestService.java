package com.crm.services;

import java.math.BigDecimal;
import java.sql.CallableStatement;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.Controller.RechargeRequestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class RechargeRequestService {

	@Autowired
	private JdbcTemplate JdbcTemplate;
	private static Logger log = LoggerFactory.getLogger(RechargeRequestController.class);

	public String getRechargeDetails(String txnId,String merchantId, String amt, String account, String utr, String createdBy,
			String serviceType,String IpAddress) throws Exception {

		String responseVal = null;
		log.info("::::::::::::::::::::getRechargeDetails:::::::::::::::::::::::::");
		// log.info("txnID===================================="+txnId);
		log.info("merchantId====================================" + merchantId);
		log.info("amt====================================" + amt);
		log.info("account====================================" + account);
		log.info("utr====================================" + utr);
		log.info("createdBy====================================" + createdBy);
		log.info("IpAddress===================================="+IpAddress);
		// log.info("rodt===================================="+rodt);
//		log.info("isApprove===================================="+isApprove);
		log.info("serviceType====================================" + serviceType);

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		// prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_insertRechargeDetails(?,?,?,?,?,?,?,?,?)}");
				 callableStatement.setString(1, txnId);
				callableStatement.setString(2, merchantId);
				callableStatement.setString(3, amt);
				callableStatement.setString(4, account);
				callableStatement.setString(5, utr);
				callableStatement.setString(6, createdBy);
				// callableStatement.setString(6, createdOn);
				// callableStatement.setString(6, isApprove);
				// callableStatement.setString(7, "N");
				callableStatement.setString(7, serviceType);
				callableStatement.setString(8, IpAddress);
				callableStatement.setString(9, "Initiated");

				log.info("callableStatement:::::::::::::::::" + callableStatement);

				return callableStatement;
			}, prmtrsList);

			log.info("resultData::::::::::::::: " + resultData);
			String resultDatavalue = resultData.get("#update-count-1").toString();
			log.info("resultDatavalue::::::" + resultDatavalue);

			if (resultDatavalue.equalsIgnoreCase("1")) {
				responseVal = "success";
			} else {
				responseVal = "fail";
			}

			return responseVal;
			// return null;
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	// update recharge

	public String getRechargeDetailUpdated(String txnId, String merchantId, String amt, String account, String utr,
			String createdBy, String serviceType) throws Exception {

		String responseVal = null;
		log.info("::::::::::::::::::::getRechargeDetailUpdated:::::::::::::::::::::::::");
		log.info("txnId====================================" + txnId);
		log.info("merchantId====================================" + merchantId);
		log.info("amt====================================" + amt);
		log.info("account====================================" + account);
		log.info("utr====================================" + utr);
		log.info("createdBy====================================" + createdBy);
		// log.info("createdOn===================================="+createdOn);
		// log.info("rodt===================================="+rodt);
//		log.info("isApprove===================================="+isApprove);
		log.info("serviceType====================================" + serviceType);

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_updateRechargeDetails(?,?,?,?,?,?,?,?)}");
				callableStatement.setString(1, txnId);
				callableStatement.setString(2, merchantId);
				callableStatement.setString(3, amt);
				callableStatement.setString(4, account);
				callableStatement.setString(5, utr);
				callableStatement.setString(6, createdBy);
				// callableStatement.setString(6, createdOn);
				// callableStatement.setString(6, isApprove);
				// callableStatement.setString(7, "N");
				callableStatement.setString(7, serviceType);
				callableStatement.setString(8, "Initiated");

				log.info("callableStatement:::::::::::::::::" + callableStatement);

				return callableStatement;
			}, prmtrsList);

			log.info("resultData::::::::::::::: " + resultData);
			String resultDatavalue = resultData.get("#update-count-1").toString();
			log.info("resultDatavalue::::::" + resultDatavalue);

			if (resultDatavalue.equalsIgnoreCase("1")) {
				responseVal = "success";
			} else {
				responseVal = "fail";
			}

			return responseVal;
			// return null;
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	public String getRechargeList(String status, String approval) throws Exception

	{
		String resp = null;
		log.info("getRechargeList::::::::::::::::::::::::::::::::::");

		log.info("status::::::::::::::" + status);
		log.info("approval::::::::::::::" + approval);

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::::::::::::: " + prmtrsList);

		try {

			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getRechargeDetails(?,?)}");

				callableStatement.setString(1, status);
				callableStatement.setString(2, approval);

				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Not Found Data");
				return arrayList.toString();
			}
			System.out.println("arrayList::::::" + arrayList);
		//	JSONArray rs = new JSONArray(arrayList);
			//Gson gson = new Gson();
				//  String jsonArray = gson.toJson(arrayList);

			Gson gson = new GsonBuilder().serializeNulls().create();
			 String jsonArray = gson.toJson(arrayList);
			
			System.out.println("jsonArray::::::" + jsonArray);
			return jsonArray.toString();
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	public String getRechargeUpdated(String txn_id, String amount, String utr_no, String merchant_id, String account_no,
			String createdOn, String status, String isApproved, String remark,String ApproveBy,String ipApprove) throws Exception

	{
		String resp = null;
		log.info("getRechargeUpdated::::::::::::::service::::::::::::::::::::");

		log.info("ipApprove:::::::::::::::::" + ipApprove);
		log.info("txn_id:::::::::::::::::" + txn_id);
		log.info("amount:::::::::::::::::" + amount);
		log.info("utr_no:::::::::::::::::" + utr_no);
		log.info("merchant_id:::::::::::::::::" + merchant_id);
		log.info("account_no:::::::::::::::::" + account_no);
		log.info("createdOn:::::::::::::::::" + createdOn);
		log.info("status:::::::::::::::::" + status);
		log.info("isApproved:::::::::::::::::" + isApproved);
		log.info("remark:::::::::::::::::" + remark);
		log.info("ApproveBy:::::::::::::::::" + ApproveBy);

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

		log.info("ParmVlaues::::::::::::::: " + prmtrsList);

		try {
			log.info("try:::::::::::::::");

			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_getRechargeUpdated(?,?,?,?,?,?,?,?,?,?,?)}");

				callableStatement.setString(1, txn_id);
				callableStatement.setString(2, amount);
				callableStatement.setString(3, utr_no);
				callableStatement.setString(4, merchant_id);
				callableStatement.setString(5, account_no);
				callableStatement.setString(6, createdOn);
				callableStatement.setString(7, status);
				callableStatement.setString(8, isApproved);
				callableStatement.setString(9, remark);
				callableStatement.setString(10, ApproveBy);
				callableStatement.setString(11, ipApprove);

				log.info("callableStatement::::::::::::::::" + callableStatement);
				return callableStatement;
			}, prmtrsList);

			log.info("resultData::::::::::::::: " + resultData);
			String resultDatavalue = resultData.get("#update-count-1").toString();
			log.info("resultDatavalue::::::" + resultDatavalue);

			if (resultDatavalue.equalsIgnoreCase("1")) {
				resp = "success";
			} else {
				resp = "fail";
			}
//			log.info("resultData::::::::::::::: " + resultData);
//			ArrayList arrayList = new ArrayList();
//			arrayList = (ArrayList) resultData.get("#update-count-1");
//			if (arrayList.isEmpty()) {
//				arrayList.add("Not Found Data");
//				return arrayList.toString();
//			}
//			log.info("arrayList::::::"+arrayList);
//			JSONArray rs = new JSONArray(arrayList);
//			Gson gson = new Gson();
//				  String jsonArray = gson.toJson(arrayList);

			// System.out.println("rs::::::"+rs);
			return resp;
		} catch (Exception e1) {
			log.info("e1:::::::::::::::" + e1);
			e1.printStackTrace();
			throw new Exception();

			// e1.printStackTrace();
		}
	}

	
	
	public String getAllRList() throws Exception

	{
		String resp = null;
		log.info("getRechargeList::::::::::::::::::::::::::::::::::");

		// log.info("status::::::::::::::"+status);
		// log.info("approval::::::::::::::"+approval);

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		// prmtrsList.add(new SqlParameter(Types.VARCHAR));
		// prmtrsList.add(new SqlParameter(Types.VARCHAR));

		// log.info("ParmVlaues::::::::::::::: " + prmtrsList);

		try {

			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getAllRechargeDetails()}");

				// callableStatement.setString(1, status);
				// callableStatement.setString(2, approval);
				log.info("callableStatement:" + callableStatement);
				return callableStatement;
			}, prmtrsList);

			log.info("resultData::::::::::::::: " + resultData);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Not Found Data");
				return arrayList.toString();
			}
			System.out.println("arrayList::::::" + arrayList);
			JSONArray rs = new JSONArray(arrayList);
//		Gson gson = new Gson();
//			  String jsonArray = gson.toJson(arrayList);

			System.out.println("rs::::::" + rs);
			return rs.toString();
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	
	//delete
	public String getRechargeDetailDeleted(String txnId,String isApproved) throws Exception {

		String responseVal = null;
		log.info("::::::::::::::::::::getRechargeDetailUpdated:::::::::::::::::::::::::");
		log.info("txnId====================================" + txnId);
		
		log.info("isApproved====================================" + isApproved);
		

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		

		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		try {

			if(!isApproved.equalsIgnoreCase("N"))
				
			{
				log.info("if is Approve is not N::::::::::::::::::::::;");
				log.info("isApproved::::::::::::::"+isApproved);
				responseVal = "fail";
			}
			else
			{
				log.info("else::::::::::::::::isApproved:"+isApproved);
				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call pro_deleteRecharge(?,?)}");
					callableStatement.setString(1, txnId);
					callableStatement.setString(2, isApproved);
					
					
					log.info("callableStatement:::::::::::::::::" + callableStatement);

					return callableStatement;
				}, prmtrsList);

				log.info("resultData::::::::::::::: " + resultData);
				String resultDatavalue = resultData.get("#update-count-1").toString();
				log.info("resultDatavalue::::::" + resultDatavalue);

				if (resultDatavalue.equalsIgnoreCase("1")) {
					responseVal = "success";
				} else {
					responseVal = "fail";
				}
			}
			

			return responseVal;
			// return null;
		} catch (Exception e1) {
			throw new Exception();
		}
	}
	
	
	public String getuserList() throws Exception

	{
		log.info("::::::::::::::::::::::::::getuserList::::::::::::::::::::::::::::::::::");
		String resp = null;
		log.info("getRechargeUpdated::::::::::::::service::::::::::::::::::::");

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();

		log.info("ParmVlaues:::::::::::::::" + prmtrsList);

		try {
			log.info("try:::::::::::::::");

			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_GetUserList()}");

				log.info("callableStatement::::::::::::::::" + callableStatement);
				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Not Found Data");
				return arrayList.toString();
			}
			System.out.println("arrayList::::::" + arrayList);
			JSONArray rs = new JSONArray(arrayList);
//		Gson gson = new Gson();
//			  String jsonArray = gson.toJson(arrayList);

			System.out.println("rs::::::" + rs);
			return rs.toString();
		} catch (Exception e1) {
			throw new Exception();
		}
	}
	
	//status
			public String getTOFTxnList(String PGTxnId,String MTxnId,String AuthId,String FromDate, String ToDate,String VPA) throws Exception

			{
				String resp = null;
				log.info("getTOFTxnList::::::::::::::::::::::::::::::::::");

				//log.info("status::::::::::::::" + status);
				//log.info("approval::::::::::::::" + approval);
		if(PGTxnId.equalsIgnoreCase("")) {
			PGTxnId="0";
		}
				BigDecimal pgtxnID = new BigDecimal(PGTxnId);
				
				List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));

				log.info("ParmVlaues::::::::::::::: " + prmtrsList);

				try {

					Map<String, Object> resultData = JdbcTemplate.call(connection -> {
						CallableStatement callableStatement = connection.prepareCall("{call pro_getTOFTxnDetails(?,?,?,?,?,?)}");

						callableStatement.setBigDecimal(1, pgtxnID);
						callableStatement.setString(2, MTxnId);
						//callableStatement.setString(3, Status);
						callableStatement.setString(3, AuthId);
						callableStatement.setString(4, FromDate);
						callableStatement.setString(5, ToDate);
						callableStatement.setString(6, VPA);

						log.info("callableStatement:::::::::::::"+callableStatement);
						
						return callableStatement;
					}, prmtrsList);

					System.out.print("resultData::::::::::::::: " + resultData);
					ArrayList arrayList = new ArrayList();
					arrayList = (ArrayList) resultData.get("#result-set-1");
					if (arrayList.isEmpty()) {
						arrayList.add("Not Found Data");
						return arrayList.toString();
					}
					System.out.println("arrayList::::::" + arrayList);
				//	JSONArray rs = new JSONArray(arrayList);
					//Gson gson = new Gson();
						//  String jsonArray = gson.toJson(arrayList);

					Gson gson = new GsonBuilder().serializeNulls().create();
					 String jsonArray = gson.toJson(arrayList);
					
					System.out.println("jsonArray::::::" + jsonArray);
					return jsonArray.toString();
				} catch (Exception e1) {
					throw new Exception();
				}
			}

			//
			public String getTOFUpdateTxnList(String AuthId,String PGTxnId,String Status,String RRN,String amt, String MtxnId,String MProcId) throws Exception

			{
				String responseVal = null;
				String resp = null;
				log.info("getTOFTxnList::::::::::::::::::::::::::::::::::");

				//log.info("status::::::::::::::" + status);
				//log.info("approval::::::::::::::" + approval);

				BigDecimal pgtxnID = new BigDecimal(PGTxnId);
				
				List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));
				prmtrsList.add(new SqlParameter(Types.VARCHAR));

				prmtrsList.add(new SqlParameter(Types.VARCHAR));

				
				log.info("ParmVlaues::::::::::::::: " + prmtrsList);

				try {

					Map<String, Object> resultData = JdbcTemplate.call(connection -> {
						CallableStatement callableStatement = connection.prepareCall("{call pro_updateTOtxn(?,?,?,?,?,?,?)}");

						callableStatement.setString(1, AuthId);
						callableStatement.setString(2, PGTxnId);
						callableStatement.setString(3, Status);
						callableStatement.setString(4, RRN);
						callableStatement.setString(5, amt);
						callableStatement.setString(6, MProcId);
						callableStatement.setString(7, MtxnId);
						log.info("callableStatement:::::::::::::"+callableStatement);
						
						return callableStatement;
					}, prmtrsList);

					log.info("resultData::::::::::::::: " + resultData);
					String resultDatavalue = resultData.get("#update-count-1").toString();
					log.info("resultDatavalue::::::" + resultDatavalue);

					if (resultDatavalue.equalsIgnoreCase("0")) {
						responseVal = "success";
					} else {
						responseVal = "fail";
					}
				
				

				return responseVal;
				} catch (Exception e1) {
					throw new Exception();
				}
			}

	

}
