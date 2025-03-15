package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.ServiceProviderservice;
import com.crm.services.UserDetailsui;

@RestController
public class ServiceProviderController {

	private static Logger log = LoggerFactory.getLogger(ServiceProviderController.class);

	@Autowired
	ServiceProviderservice SP_service;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Getsplist", produces = "application/json")
	public ResponseEntity<?> GetServiceProviderList(@RequestBody String jsonBody) {
		log.info("entered GetServiceProviderList+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String sp_name = js.getString("sp_name");
		log.info("entered sp_name+++++++============" + sp_name);

		Map<String, Object> Getlist = null;
		try {
			log.info("entered inside try+++++++============");

			Getlist = SP_service.getsplist(sp_name);

			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Get SP List", jsonBody, Getlist.toString(), ipAddress);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(Getlist);
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Getbank", produces = "application/json")
	public ResponseEntity<?> GetbankList(@RequestBody String jsonBody) {
		log.info("entered GetServiceProviderList+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String admin = js.getString("admin");
		Map<String, Object> bankData = null;

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;

		String ipAddress = null;

		try {
			bankData = SP_service.getbank();

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Get Bank", jsonBody, bankData.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
			secondCon.insertIntoSecondSchema(username, "Get Bank-Exception", jsonBody, stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return ResponseEntity.ok().body(bankData);

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Insertsp", produces = "application/json")
	public String InsertserviceProvider(@RequestBody String jsonBody) {
		String ResponseValue = null;
		log.info("entered CreateRole1+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String sp_name = js.getString("sp_name");
		String sp_class_invoker = js.getString("sp_class_invoker");
		String instrumentIds = js.getString("instrumentIds");
		String bankIds = js.getString("bankIds");
		String master_mid = js.getString("master_mid");
		String master_tid = js.getString("master_tid");
		String api_key = js.getString("api_key");
		String udf_1 = js.getString("udf_1");
		String udf_2 = js.getString("udf_2");
		String udf_3 = js.getString("udf_3");
		String udf_4 = js.getString("udf_4");
		String udf_5 = js.getString("udf_5");
		String refund_processor = js.getString("refund_processor");
		String cutoff = js.getString("cutoff");
		String username = null;

		String ipAddress = null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String insertsp = null;
		try {
			insertsp = SP_service.insertrecord_Sp(sp_name, sp_class_invoker, instrumentIds, bankIds, master_mid,
					master_tid, api_key, udf_1, udf_2, udf_3, udf_4, udf_5, refund_processor, cutoff);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			// secondCon.insertIntoSecondSchema(username, "Get SP List", jsonBody, insertsp,
			// ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Insert SP-Exception", jsonBody,
					stackTraceString, ipAddress);
			e.printStackTrace();
		}
		if (insertsp.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "created Successfully.");
			ResponseValue = respons.toString();

			secondCon.insertIntoSecondSchema(username, "Insert SP", jsonBody, ResponseValue, ipAddress);

		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();

			secondCon.insertIntoSecondSchema(username, "Insert SP", jsonBody, ResponseValue, ipAddress);

		}

		return ResponseValue;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Updatesp", produces = "application/json")
	public String Updateserviceprovider(@RequestBody String jsonBody) {
		log.info("entered sp update+++++++============");
		String ResponseValue = null;
		JSONObject js = new JSONObject(jsonBody);
		Long sp_id = js.getLong("sp_id");
		String sp_name = js.getString("sp_name");
		String sp_class_invoker = js.getString("sp_class_invoker");
		String instrumentIds = js.getString("instrumentIds");
		String bankIds = js.getString("bankIds");
		String master_mid = js.getString("master_mid");
		String master_tid = js.getString("master_tid");
		String api_key = js.getString("api_key");
		String udf_1 = js.getString("udf_1");
		String udf_2 = js.getString("udf_2");
		String udf_3 = js.getString("udf_3");
		String udf_4 = js.getString("udf_4");
		String udf_5 = js.getString("udf_5");
		String refund_processor = js.getString("refund_processor");
		String cutoff = js.getString("cutoff");
		String updatespdata = null;
		log.info("entering update role");
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			updatespdata = SP_service.Updatesp(sp_id, sp_name, sp_class_invoker, instrumentIds, bankIds, master_mid,
					master_tid, api_key, udf_1, udf_2, udf_3, udf_4, udf_5, refund_processor, cutoff);
			
			secondCon.insertIntoSecondSchema(username, "Updatesp", jsonBody, ResponseValue, ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Updatesp-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
			log.info("exception :::::::::::::" + e);
		}
		if (updatespdata.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Updated  Successfully.");
			ResponseValue = respons.toString();
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();

		}
		return ResponseValue;
	}

}
