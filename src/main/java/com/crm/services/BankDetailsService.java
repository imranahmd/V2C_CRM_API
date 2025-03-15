package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import aj.org.objectweb.asm.Type;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.Controller.BankDetailsController;
import com.crm.Controller.MerchantController;
import com.crm.Repository.BankDetailsRepository;
import com.crm.model.BankDetails;

@Service
public class BankDetailsService {
	@Autowired
	BankDetailsRepository BankDetails;
	private static final Logger log = LoggerFactory.getLogger(BankDetailsController.class);
	@Autowired
	private JdbcTemplate JdbcTemplate;

	public JSONObject createBank(String BankName, String ID, String BankID, String CreatedBy, String ModifiedBy) {

		int BankRepo = BankDetails.insertUpdateBank(BankName, ID, BankID, CreatedBy, ModifiedBy);
		log.info("BankRepo::::::" + BankRepo);
		JSONObject js = new JSONObject();
		if (BankRepo == 0) {
			js.put("Status", true);
			js.put("Message", "Inserted Successfully");
		} else {
			js.put("Status", true);
			js.put("Message", "Updated Successfully");
		}
		/*
		 * BankDetails BankData = new BankDetails();
		 */
		return js;

	}

	public String getBank() {
//	List<Object> BankRepo = BankDetails.getBankDetails( );
//	Gson gson = new Gson();
//	 String jsonCartList = gson.toJson(BankRepo);
//	 log.info("jsonCartList:::::::::::",jsonCartList);
//	 java.lang.reflect.Type type = new TypeToken<List<Object>>(){}.getType();
//
// 	 List<Object> prodList = gson.fromJson(jsonCartList,type  );
//
//	 // print your List<Product>
//	 System.out.println("prodList: " + prodList.get(0));
//    log.info("bankepo:::::::::"+BankRepo.toString());
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();

		JSONObject Res = new JSONObject();
		String result =null;
		System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getBankDetails()}");

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
//			Gson gson = new Gson();
//				  String jsonArray = gson.toJson(arrayList);
			result = rs.toString();
			System.out.println("rs::::::" + rs);
			return rs.toString();
		} catch (Exception e1) {
			Res.put("Status", false);
			Res.put("Message", "Data Not Aavailable");
			result = Res.toString();
//				throw new Exception();
		}
	return result;
	}

	public JSONObject deleteBank(String iD) {
		int BankRepo = BankDetails.getBank( iD);
		log.info("BankRepo::::::" + BankRepo);
		JSONObject js = new JSONObject();
		if (BankRepo == 1) {
			js.put("Status", true);
			js.put("Message", "Delete Successfully");
		} else {
			js.put("Status", false);
			js.put("Message", "Unable to Delete");
		}
		/*
		 * BankDetails BankData = new BankDetails();
		 */
		return js;

	}
}
