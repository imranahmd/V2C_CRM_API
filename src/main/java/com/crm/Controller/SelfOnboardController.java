package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.MerchantPaginationDto;
import com.crm.helper.JwtHelperUtil;
import com.crm.services.SelfOnboardService;
import com.crm.services.UserDetailsui;

@RestController
public class SelfOnboardController {
	@Autowired
	private SelfOnboardService selfOnboardService;
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/get-Signup")
	public ResponseEntity<?> getSignup(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String MobileNo = js.getString("mobile");
		String fullName = js.getString("fullName");
		String emailId = js.getString("emailId");
		Map<String, Object> SignUpResponse= selfOnboardService.smsOTPconfigure(MobileNo,fullName,emailId);
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get-Signup", jsonBody, SignUpResponse.toString(), ipAddress);
		
		
		
		
		return ResponseEntity.ok(SignUpResponse);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/verified-OTP")
	public ResponseEntity<?> verifyOTP(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String getMobileOTP = js.getString("mobileOTP");
		String getEmailOTP = js.getString("emailOTP");
		Map<String, Object> SignUpResponse= selfOnboardService.getOTPVerified(getMobileOTP, getEmailOTP );
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Verified-OTP", jsonBody, SignUpResponse.toString(), ipAddress);
		
		
		
		return ResponseEntity.ok(SignUpResponse);
	}
	
	@PostMapping("/create-passwordSelf")
	public ResponseEntity<?> createpasswordSelf(@RequestBody String jsonBody, @RequestHeader String Authorization) {
		JSONObject js = new JSONObject(jsonBody);
		JSONObject js1 = new JSONObject();
		String messageCtr = null;
		String newpassword = js.getString("newpassword");
		String JwtToken=null;
		Map<String, Object> responseMsg = null;
		JwtHelperUtil generalUtil = new JwtHelperUtil();
		JwtToken = Authorization.substring(7);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			String userId =generalUtil.extractUsername(JwtToken);
			 responseMsg= selfOnboardService.createPasswordSelf(newpassword,userId);
			 
			 secondCon.insertIntoSecondSchema(username, "Create-passwordSelf", jsonBody, responseMsg.toString(), ipAddress);
			 
		}catch(Exception e) {
			e.printStackTrace();
			messageCtr = "Your Token Is Not Matching";
			js1.put("Status", "Error");
			js1.put("Message", messageCtr);
			responseMsg=js1.toMap();
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Create-passwordSelf", jsonBody, stackTraceString, ipAddress);
		}		
		return ResponseEntity.ok(responseMsg);
		
	}

}
