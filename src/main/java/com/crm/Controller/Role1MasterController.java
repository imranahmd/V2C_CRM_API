package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.json.JSONArray;
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
import com.crm.services.Role1Masterservice;
import com.crm.services.UserDetailsui;
@RestController
public class Role1MasterController {

	
	private static Logger log = LoggerFactory.getLogger(Role1MasterController.class);

	@Autowired
	Role1Masterservice role1service;

	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/Getrole1list", produces = "application/json")	
	public ResponseEntity<?> Getrole1List(@RequestBody String jsonBody) {
		log.info("entered Getrole1List+++++++============");
		
		  SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);
			String username = null;

			String ipAddress = null;
		
		
		JSONObject js = new JSONObject(jsonBody);
		String admin=js.getString("admin");
		Map<String, Object> role1Data=null;
		 try {
			 
				 username = userDetails.optString("Username");

				 ipAddress = userDetails.optString("ipAddress");
			 role1Data=role1service.getrole1list();
			 
			 
			 secondCon.insertIntoSecondSchema(username, "Get Role List", jsonBody, role1Data.toString(), ipAddress);
				
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			 
			 secondCon.insertIntoSecondSchema(username, "Get Role List-Exception", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
			
			
		}

		return ResponseEntity.ok().body(role1Data);
		
	}
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/createrole1", produces = "application/json")
	public String CreateRole1(@RequestBody String jsonBody) {
		String ResponseValue=null;
		log.info("entered CreateRole1+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String RoleName=js.getString("RoleName");
		String RoleDescription=js.getString("RoleDescription");
		String StatusRole=js.getString("StatusRole");
		String insertRole1=null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			log.info("ROLENAME::::" + RoleName + ":::roleDescriptionId:::" + RoleDescription + ":::::status:::" + StatusRole);
			insertRole1=role1service.createrole(RoleName,RoleDescription,StatusRole);
			
			
			
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Create role1-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
		if (insertRole1.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "created Successfully.");
			ResponseValue = respons.toString();
			secondCon.insertIntoSecondSchema(username, "Create role1", jsonBody, ResponseValue, ipAddress);
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();
			secondCon.insertIntoSecondSchema(username, "Create role1", jsonBody, ResponseValue, ipAddress);

		}

		return ResponseValue;
}
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Updaterole1", produces = "application/json")	
	public String Updaterole1 (@RequestBody String jsonBody) {
		log.info("entered Updaterole1+++++++============");
		
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String Rolename=js.getString("Rolename");
		String RoleDescriptionId=js.getString("RoleDescriptionId");
		String RoleId=js.getString("RoleId");
		String Status=js.getString("Status");
		log.info("ROLENAME::::::"+Rolename+"ROLEDESCRIPTION:::::::"+RoleDescriptionId+"ROLEID:::::::::"+RoleId+"Status::::::::::::::::"+Status);
		String updateroledata = null;
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
			
			
		updateroledata=role1service.Updaterole(Rolename,RoleDescriptionId,RoleId,Status);
		
		
			
			
		}catch (Exception e) {
			
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update role1-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
			log.info("exception :::::::::::::"+e);
		}
		if (updateroledata.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Updated  Successfully.");
			ResponseValue = respons.toString();
			secondCon.insertIntoSecondSchema(username, "Update role1", jsonBody, ResponseValue, ipAddress);
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();
			secondCon.insertIntoSecondSchema(username, "Update role1", jsonBody, ResponseValue, ipAddress);

		}
		return ResponseValue;
	}
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/DeleteRole1", produces = "application/json")	
	public String Deleterole1 (@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String RoleId=js.getString("RoleId");
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
			
			
			
			
			deletedata=role1service.Deleterole(RoleId);
			
			secondCon.insertIntoSecondSchema(username, "Delete Role1 ", jsonBody, ResponseValue.toString(), ipAddress);
			
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete Role1-Exception", jsonBody, stackTraceString, ipAddress);
			
			
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
	@PostMapping(value = "/Geteditreportlist", produces = "application/json")	
	public String GetReportdata(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String role=js.getString("role");
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
			
			
		Reportdata=role1service.getRecordById(role);
		
		
		
		secondCon.insertIntoSecondSchema(username, "Get edit report list", jsonBody, Reportdata.toString(), ipAddress);
		
		}catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get edit report list-Exception", jsonBody, stackTraceString, ipAddress);
			
			
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
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Getrolemenu", produces = "application/json")	
	public ResponseEntity<?> Getroledata(@RequestBody String jsonBody) {
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
			
		Reportdata=role1service.getroleById();
		
		
		secondCon.insertIntoSecondSchema(username, "Get role menu", jsonBody, Reportdata.toString(), ipAddress);
		}catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get role menu-Exception", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}
		
		
		return ResponseEntity.ok().body(Reportdata);
	}
	
	
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/insertrolemenu", produces = "application/json")
	public String InsertrolemenuPermission(@RequestBody String jsonBody) {
		String ResponseValue=null;
		log.info("entered CreateRole1+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		JSONArray  data=js.getJSONArray("data");
		log.info("data::::::::"+data);
		String insertrolemenumap=null;
		log.info("entered insertrolemenumap+++++++============");
		
		
		
		
		SecondCon secondCon = new SecondCon();
		//UserDetailsui userDetailsui = new UserDetailsui();
					
		//String dataa = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		

		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			log.info("entered insertrolemenumap in try +++++++============");

			insertrolemenumap=role1service.Insertrolemenupermission(data);
			
			
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Insert role menu-Exception", jsonBody.toString(), stackTraceString, ipAddress);
			
			e.printStackTrace();
		}
		if (insertrolemenumap.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "created Successfully.");
			
			
			ResponseValue = respons.toString();
			log.info("=========><<=========:::"+ResponseValue);
			secondCon.insertIntoSecondSchema(username, "Insert role menu", jsonBody.toString(), ResponseValue, ipAddress);
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();
			log.info("=========><<=========:::"+ResponseValue);
			secondCon.insertIntoSecondSchema(username, "Insert role menu", jsonBody, ResponseValue, ipAddress);

		}

		return ResponseValue;
}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/Getroledata", produces = "application/json")	
	public ResponseEntity<?> getRoleList(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String role=js.getString("role");
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
			
			
		Reportdata=role1service.getrolelist(role);
		
		
		secondCon.insertIntoSecondSchema(username, "Get role data", jsonBody, Reportdata.toString(), ipAddress);
		}catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get role data-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().body(Reportdata);
	}
	


}
