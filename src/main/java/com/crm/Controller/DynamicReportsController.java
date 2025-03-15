package com.crm.Controller;

import java.util.ArrayList;

import com.crm.services.UserDetailsui;
import com.crm.ServiceDaoImpl.SecondCon;
import java.util.Map;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.Repository.MerchantRepository;
import com.crm.services.DynamicReportService;
import com.google.gson.Gson;

@RestController
public class DynamicReportsController {

	private static Logger log = LoggerFactory.getLogger(MerchantPayinController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	DynamicReportService dynamicReportService;

	@Autowired
	MerchantRepository Merchantrepo;

//	@CrossOrigin(origins = { "http://localhost:4200" })
//	@PostMapping("/reports")
//	public String getReportsData(@RequestBody String reports) throws Exception {
//		
//		JSONObject js = new JSONObject(reports);
//		int reportType = js.getInt("reportType");
//		int reportId = js.getInt("reportId");
//		String userType = js.getString("userType");
//		String userId = js.getString("userId");
//		String inputParameters = js.getString("inputParameters");
//		ArrayList<String> arrayList = new ArrayList<String>();
//		try {
//			Map<String, Object> resultData = dynamicReportService.getReportsData(reportType,reportId,userType,userId,inputParameters);
//			arrayList = (ArrayList<String>) resultData.get("#result-set-1");
//			if (arrayList.isEmpty()) {
//				return "Data does not exist";
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//			Gson gson = new Gson();
//
//			String jsonArray = gson.toJson(arrayList);
//		return jsonArray.toString();
//	}

	@CrossOrigin(origins = { "https://pg.payfi.co.in" })
	@PostMapping("/reports")
	public String getReportsData(@RequestBody String reports) throws Exception {

		SecondCon secondCon = new SecondCon();

		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

//		secondCon.insertIntoSecondSchema(username, "Report", reports, reports, ipAddress);

		JSONObject js = new JSONObject(reports);
		int reportType = js.getInt("reportType");
		int reportId = js.getInt("reportId");
		String userType = js.getString("userType");
		String userId = js.getString("userId");
		String inputParameters = js.getString("inputParameters");
		ArrayList<String> arrayList = new ArrayList<String>();

		String abc = "merchants/dynamicreport/" + reportId;
		String Menulink = abc;
		log.info("Menulink :::::::: " + Menulink);

		String roleId = Merchantrepo.Getrole(userId);
		log.info("roleId :::::::::::: " + roleId);
		String result = Merchantrepo.getResult(roleId, Menulink);

		if (result.equalsIgnoreCase("1")) {
			try {
				Map<String, Object> resultData = dynamicReportService.getReportsData(reportType, reportId, userType,
						userId, inputParameters);
				arrayList = (ArrayList<String>) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					
					secondCon.insertIntoSecondSchema(username, "Report", reports, "Data does not exist", ipAddress);
					
					return "Data does not exist";
				}

				

				
			} catch (Exception e) {

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();

				secondCon.insertIntoSecondSchema(username, "Report-Exception", stackTraceString, stackTraceString, ipAddress);

				e.printStackTrace();
			}
			Gson gson = new Gson();

			String jsonArray = gson.toJson(arrayList);
			return jsonArray.toString();
		} else {

			JSONObject errorResponse = new JSONObject();

			errorResponse.put("status", "error");
			errorResponse.put("message", "Internal Server Error");

			String jsonResponse = errorResponse.toString();

			log.info(jsonResponse);
			
			
			secondCon.insertIntoSecondSchema(username, "Report", reports, jsonResponse, ipAddress);
			
			return jsonResponse;
		}
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/reseller-invoice-reports")
	public Map<String, Object> getresellerInvoiceReportsData(@RequestBody String reports) throws Exception {
		String username = null;

		String ipAddress =null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		JSONObject js = new JSONObject(reports);
		String reportType = js.getString("reportType");
		String reportId = js.getString("reportId");
		String userType = js.getString("userType");
		String userId = js.getString("userId");
		String inputParameters = js.getString("inputParameters");
		ArrayList<String> arrayList = new ArrayList<String>();
		JSONArray array = new JSONArray();
		JSONObject js1 = new JSONObject();
		Gson gson = new Gson();
		String jsonArray = null;
		try {

			if (reportId.equals("5")) {
				Map<String, Object> resultData = dynamicReportService.getInvoiceReportsData(reportType, reportId,
						userType, userId, inputParameters);
				arrayList = (ArrayList<String>) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {

					js1.put("Status", "No Data found");
				} else {
					js1.put("Data", arrayList);
					jsonArray = gson.toJson(arrayList);
					//
					String data = userDetailsui.getUserDetails();
					JSONObject userDetails = new JSONObject(data);

					 username = userDetails.optString("Username");

					 ipAddress = userDetails.optString("ipAddress");

					secondCon.insertIntoSecondSchema(username, "reseller-invoice-reports", js.toString(), jsonArray, ipAddress);

					
				}
			} else {
				JSONObject item = new JSONObject();
				item.put("Merchant_Name", "NA");
				item.put("Merchant_MID", "NA");
				item.put("Payment_Options", "NA");
				item.put("Transaction_Volume", "NA");
				item.put("Merchant_MDR", "NA");
				item.put("MDR_Volume", "NA");
				item.put("GST_On_MDR", "NA");
				item.put("Merchant_Total_Charges", "NA");
				item.put("Merchant_Net_Settlement", "NA");
				item.put("System_MDR", "NA");
				item.put("Reseller_MDR", "NA");
				item.put("Reseller_Charges", "NA");
				item.put("Reseller_Applicable_Taxes", "NA");
				item.put("Reseller_Total_Charges", "NA");

				array.put(item);
				js1.put("Data", array);
//				jsonArray = gson.toJson(js1.toMap());

			}

		} catch (Exception e) {
			e.printStackTrace();
			

StringWriter sw = new StringWriter();
PrintWriter pw = new PrintWriter(sw);
e.printStackTrace(pw);
String stackTraceString = sw.toString();
			
secondCon.insertIntoSecondSchema(username, "reseller-invoice-reports-Exception", js.toString(), stackTraceString, ipAddress);
		
		}

		return js1.toMap();
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getTransaction-LifeCycleDetails")
	public Map<String, Object> getLifeCycleDetails(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		JSONArray array = new JSONArray();
		JSONObject js1 = new JSONObject();
		Gson gson = new Gson();
		String jsonArray = null;
		String MerchantId = js.getString("merchantId");
		String FromDate = js.getString("fromDate");
		String ToDate = js.getString("toDate");
		// int SpId1= js.get("spId");
		String SpId = String.valueOf(js.get("spId"));
		String LifeCyclecode = js.getString("lifeCycleCode");
		String TransactionId = js.getString("transactionId");
		String RrnNumber = js.getString("rrnNumber");

		ArrayList<String> arrayList = new ArrayList<String>();
		Map<String, Object> resultData = dynamicReportService.getLifeCycleList(MerchantId, FromDate, ToDate, SpId,
				LifeCyclecode, TransactionId, RrnNumber);
		arrayList = (ArrayList<String>) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {

			js1.put("Status", "Data does not exist");
		} else {
			js1.put("Data", arrayList);
			jsonArray = gson.toJson(arrayList);
		}
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();


		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");
		
		String 	jsonResp = gson.toJson(arrayList);
		secondCon.insertIntoSecondSchema(username, "GgetTransaction-LifeCycleDetails", jsonBody, js1.toMap().toString(), ipAddress);

		return js1.toMap();
	}
}
