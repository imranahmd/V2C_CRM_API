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
import com.crm.model.Response;
import com.crm.services.MerchantPayinService;
import com.crm.services.UserDetailsui;

@RestController
public class MerchantPayinController {
	
	private static Logger log = LoggerFactory.getLogger(MerchantPayinController.class);

	@Autowired
	MerchantPayinService merchantpayinService;
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/insertpayoutmdr", produces = "application/json")
	public ResponseEntity<Response> CreateRole1(@RequestBody String jsonBody) {
		log.info("entered insertpayoutmdr+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String id=js.getString("id");
		String merchant_id=js.getString("merchant_id");
		String sp_id=js.getString("sp_id");
		String mop=js.getString("mop");
		String aggr_mdr_Type=js.getString("aggr_mdr_Type");
		String aggr_mdr=js.getString("aggr_mdr");
		String base_mdr_Type=js.getString("base_mdr_Type");
		String base_mdr=js.getString("base_mdr");
		String start_date=js.getString("start_date");
		String end_date=js.getString("end_date");
		String mid=js.getString("mid");
		String tid=js.getString("tid");
		String min_amt=js.getString("min_amt");
		String max_amt=js.getString("max_amt");
		String MDR_apply_Type=js.getString("MDR_apply_Type");
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		

		log.info("entered 22 insertpayoutmdr+++++++============");
        int Id = Integer.parseInt(id);

		Response insertrecord=null;
		try {
			
			

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			
			
			
			
			
			
			log.info("entered insertpayoutmdr try+++++++============");

			insertrecord=merchantpayinService.insertMerchant_payoutmdrRecord(merchant_id,sp_id,mop,aggr_mdr_Type,aggr_mdr,base_mdr_Type,base_mdr,start_date,end_date,mid,tid,min_amt,max_amt,Id,MDR_apply_Type);
			
			
			secondCon.insertIntoSecondSchema(username, "Update Payout MDR", jsonBody, insertrecord.toString(), ipAddress);
			
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Update Payout MDR-Exception",jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return  ResponseEntity.ok().body(insertrecord);
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/DeletebyId", produces = "application/json")	
	public String DeleteMerchantpayoutbyid(@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String Id=js.getString("Id");
		String deletedata=null;
		log.info("id::"+Id);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		try {
			log.info("id  try::"+Id);
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			

			deletedata=merchantpayinService.DeleteByID(Id);
			
			
			secondCon.insertIntoSecondSchema(username, "Delete by ID", jsonBody, deletedata.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Delete by ID-Exception",jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		if (deletedata.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Deleted Successfully.");
			ResponseValue = respons.toString();
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();

			
			
			
		}
		return ResponseValue;
		}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Getpayoutmdr", produces = "application/json")	
	public ResponseEntity<?> getMerchantPayout(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String merchantId=js.getString("merchantId");
		Map<String, Object> Reportdata = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			
			
			
		Reportdata=merchantpayinService.getPayoutmdr(merchantId);
		
		
		secondCon.insertIntoSecondSchema(username, " List Payout MDR", jsonBody, Reportdata.toString(), ipAddress);
		}catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "List-Payout-MDR-Exception",jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().body(Reportdata);
	}
	
}
