package com.crm.Controller;

import java.util.ArrayList;

import com.crm.services.UserDetailsui;
import com.crm.ServiceDaoImpl.SecondCon;
import java.util.HashMap;
import java.util.Map;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.crm.helper.FileUploadHelper;
import com.crm.model.RefreshToken;
import com.crm.model.TokenReq;
import com.crm.model.UserRequest;
import com.crm.services.customUserDetailsService;
import com.google.gson.Gson;
import com.crm.services.CommonService;

@RestController

public class CommonController {

	@Autowired
	private com.crm.helper.JwtHelperUtil JwtHelperUtil;
	@Autowired
	private customUserDetailsService customUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManger;

	@Autowired
	private FileUploadHelper FileUploadHelper;
	
	@Autowired
	private CommonService CommonService;
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetMerchant/")
	public String GetMerchantByName(@RequestBody String name) throws Exception
	{	
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");

		
		
		JSONObject js = new JSONObject(name);
		String mid =  js.getString("name");
		
		
		
		secondCon.insertIntoSecondSchema(username, "Get Merchant List", name,CommonService.GetMerchantList(mid), ipAddress);
		
		System.out.println("======>.>>"+CommonService.GetMerchantList(mid));
		
		return CommonService.GetMerchantList(mid);
	}
	
	
	/*
	 * @CrossOrigin(origins =
	 * {"http://localhost:4200"})
	 * 
	 * @PostMapping("/FileUpload") public ResponseEntity<?>
	 * FileUpload(@RequestParam("File") MultipartFile multipart) throws Exception {
	 * 
	 * try {
	 * 
	 * if(multipart.isEmpty()) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error"); }else
	 * { boolean f = FileUploadHelper.upload(multipart); if(f) { return
	 * ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path(
	 * "/upload/").path(multipart.getOriginalFilename()).toUriString()); } }
	 * 
	 * }catch(Exception e) { e.printStackTrace(); } return ResponseEntity.ok(null);
	 * }
	 */
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetDropdown")
	public ResponseEntity<?> GetCommonDropdown(@RequestBody String jsonBody) {
		
		
		  SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			//secondCon.insertIntoSecondSchema(username, "Get Dropdown", jsonBody, jsonBody, ipAddress);
		
		
		
		JSONObject js = new JSONObject(jsonBody);
		String Type = js.getString("Type");
		String Value = js.getString("Value");		
		ArrayList DropDown=null;
		 Gson gson = new Gson();
		 Map map = new HashMap<>();
		 
		 JSONObject respBody = new JSONObject();
		try {

			 DropDown = CommonService.getDropDown(Type,Value);
			
			 respBody.put("Type", Type);
			 respBody.put("Value", Value);
			 
			secondCon.insertIntoSecondSchema(username, "Get Dropdown"+Type, jsonBody, DropDown.toString(), ipAddress);
				
			 
		} catch (Exception e) {
			e.printStackTrace();
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Get Dropdown-Exception", jsonBody, stackTraceString, ipAddress);
		}
		
		return ResponseEntity.ok(DropDown);
	}
	
	
	
	
}
