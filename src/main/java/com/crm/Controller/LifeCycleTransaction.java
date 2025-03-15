package com.crm.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.LifeCycleDto;
import com.crm.model.MerchantKycDoc;
import com.crm.services.LifeCycleService;
import com.crm.services.MerchantService;
import com.crm.services.UserDetailsui;
import com.google.gson.Gson;

@RestController
public class LifeCycleTransaction {
	
	@Autowired
	private LifeCycleService lifeCycleService;	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping("/updateTransaction-LifeCycleDetails")
	public Map<String, Object> getLifeCycleTransactionUpdate(@RequestBody List<LifeCycleDto> lifeCycleDto){
		
		JSONObject js1 = new JSONObject();
		
		Map<String, Object> resultData =lifeCycleService.insertLifeCycleDetails(lifeCycleDto);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Update Transaction-Life Cycle Details", lifeCycleDto.toString(), resultData.toString(), ipAddress);
		
		
		return resultData;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/upload-StatusFile")
	public Map<String, Object> uploadFile(@RequestParam MultipartFile uploadFile, String addedBy) {
		JSONObject js1 = new JSONObject();
		
		Map<String, Object> resultData = lifeCycleService.uploadFileStatus(uploadFile, addedBy);

		js1.put("Data", resultData);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Upload-Status File", uploadFile.toString()+"/"+addedBy, js1.toString(), ipAddress);
		
		
		
		
		return js1.toMap();
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/audittrail-List")
	public Map<String, Object> auditTrailList(@RequestBody String jsonBody) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);
		String transactionId = js.getString("trnsactionId");
		Map<String, Object> resultData = lifeCycleService.findAuditTrail(transactionId);
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Audittrail-List", jsonBody, resultData.toString(), ipAddress);
		
		
		
		
		
		
		
		return resultData;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/settle-Claim-Report")
	public Map<String, Object> settleClaimReport(@RequestBody String jsonBody) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);
		String Type = js.getString("Type");
		String SPId = js.getString("SPId");
		String Instrument = js.getString("Instrument");
		String Cycle = js.getString("Cycle");
		String ReconDate = js.getString("ReconDate");
		String UserId = js.getString("UserId");
		Map<String, Object> resultData = lifeCycleService.findSettleClaim(Type,SPId,Instrument,Cycle,ReconDate, UserId);
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Settle-Claim-Report", jsonBody, resultData.toString(), ipAddress);
		
		return resultData;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/settle-Claim-Genrate")
	public Map<String, Object> settleClaimGenrate(@RequestBody String jsonBody) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);
//		String Type = js.getString("Type");
		String SPId = js.getString("SPId");
		String Instrument = js.getString("Instrument");
		String Cycle = js.getString("Cycle");
		String ReconDate = js.getString("ReconDate");
		String UserId = js.getString("UserId");
		Map<String, Object> resultData = lifeCycleService.findSettleClaimGentrate(SPId,Instrument,Cycle,ReconDate, UserId);
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Settle-Claim-Genrate", jsonBody, resultData.toString(), ipAddress);
		
		
		
		return resultData;
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/updateTransaction-BulkLifeCycleDetails")
	public Map<String, Object> geBulktLifeCycleTransactionUpdate(@RequestParam MultipartFile file, String UserId, String remarks, String filePath){
		JSONObject js1 = new JSONObject();
		Map<String, Object> resultData =lifeCycleService.insertBulkLifeCycleDetails(file, UserId, remarks, filePath);
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get User List", file.toString()+"/"+UserId+"/"+remarks+"/"+filePath, resultData.toString(), ipAddress);
		
		return resultData;
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/BulkLifeSearchCycleDetails")
	public Map<String, Object> geBulktLifeSearchCycleTransactionUpdate(@RequestParam MultipartFile file, String Type){
		JSONObject js1 = new JSONObject();
		Map<String, Object> resultData =lifeCycleService.searchBulkLifeCycleDetails(file, Type);
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get User List", file.toString()+"/"+Type, resultData.toString(), ipAddress);
		
		
		return resultData;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/settle-Claim-GenrateT0")
	public Map<String, Object> settleClaimGenrateTO(@RequestBody String jsonBody) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);
//		String Type = js.getString("Type");
		String SPId = js.getString("SPId");
		String Instrument = js.getString("Instrument");
		String Cycle = js.getString("Cycle");
		String ReconDate = js.getString("ReconDate");
		String UserId = js.getString("UserId");
		Map<String, Object> resultData = lifeCycleService.findSettleClaimGentrateTo(SPId,Instrument,Cycle,ReconDate, UserId);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get User List", jsonBody, resultData.toString(), ipAddress);
		
		
		
		
		
		
		return resultData;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/settle-Claim-ReportT0")
	public Map<String, Object> settleClaimReporT0(@RequestBody String jsonBody) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);
		String Type = js.getString("Type");
		String SPId = js.getString("SPId");
		String Instrument = js.getString("Instrument");
		String Cycle = js.getString("Cycle");
		String ReconDate = js.getString("ReconDate");
		String UserId = js.getString("UserId");
		Map<String, Object> resultData = lifeCycleService.findSettleClaimT0(Type,SPId,Instrument,Cycle,ReconDate, UserId);
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "settle-Claim-ReportT0", jsonBody, resultData.toString(), ipAddress);
		
		
		return resultData;
	}
	
}
