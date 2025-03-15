package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.UserlistDTO;
import com.crm.services.UserDetailsui;
import com.crm.services.UserMasterService;
@RestController
public class UserMasterController {
	private static Logger log = LoggerFactory.getLogger(UserMasterController.class);

	@Autowired
	UserMasterService user_service;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/insertuser", produces = "application/json")
	public String InsertUser(@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject Res = new JSONObject();

		log.info("entered Insertuser+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String userId=js.getString("userId");
		String fullName=js.getString("fullName");
		String emailId=js.getString("emailId");
		String contactNo=js.getString("contactNo");
		String roleId=js.getString("roleId");
		String password=js.getString("password");
		 
		JSONObject userinfo= new JSONObject();
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			userinfo=user_service.insertrecord_User(userId,fullName,emailId,contactNo,roleId,password);
			
			secondCon.insertIntoSecondSchema(username, "Insert user", jsonBody, userinfo.toString(), ipAddress);
		log.info("userinfo:::::::::::"+userinfo.toString());
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Insert user-Exception", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
			
			}
		
		return userinfo.toString();
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Updateuser", produces = "application/json")
	public String UpdateUser(@RequestBody String jsonBody) {
		String ResponseValue=null;
		log.info("entered Insertuser+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String userId=js.getString("userId");
		String fullName=js.getString("fullName");
		String emailId=js.getString("emailId");
		String contactNo=js.getString("contactNo");
		String roleId=js.getString("roleId");
		String password=js.getString("password");
		
		

		String Updateuser=null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			Updateuser=user_service.updaterecord_User(userId,fullName,emailId,contactNo,roleId,password);
			
			
			secondCon.insertIntoSecondSchema(username, "Update user", jsonBody, ResponseValue, ipAddress);
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update user-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		if (Updateuser.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "updated Successfully.");
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
	@PostMapping(value = "/deleteUser", produces = "application/json")	
	public String Deleteuser (@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String USERID=js.getString("USERID");
		String deletedata=null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			deletedata=user_service.Deleteuser(USERID);
			secondCon.insertIntoSecondSchema(username, "Delete User", jsonBody, ResponseValue, ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete User-Exception", jsonBody, stackTraceString, ipAddress);
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
	@PostMapping(value = "/getuserlist", produces = "application/json")	
	public List<UserlistDTO> GetUserdata(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String admin=js.getString("admin");
		List<UserlistDTO> Reportdata = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
		Reportdata=user_service.getUserData();
		
		secondCon.insertIntoSecondSchema(username, "Get user list", jsonBody, Reportdata.toString(), ipAddress);
		
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get user list-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}
		
		return Reportdata;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/getroledorpdown", produces = "application/json")	
	public String GetRoleName_Id(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String admin=js.getString("admin");
		String Reportdata = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
		Reportdata=user_service.getRoleIdName();
		
		secondCon.insertIntoSecondSchema(username, "Get role dorpdown", jsonBody, Reportdata, ipAddress);
		
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get role dorpdown-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}
		if (Reportdata.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
				.equalsIgnoreCase("Not Found Data")) {

			JSONObject response = new JSONObject();
			response.append("Error", "Data Not Found");

			Reportdata = response.toString();
		
		
	}
		return Reportdata;
	}
}
