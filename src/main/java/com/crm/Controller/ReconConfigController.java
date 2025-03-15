package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.Repository.ReconManagementRepo;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.model.TblMstreconconfig;
import com.crm.services.ReconManagementService;
import com.crm.services.UserDetailsui;

@RestController
public class ReconConfigController {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(ReconConfigController.class);

	@Autowired
	private ReconManagementService reconManagementService;
	
	@Autowired
	ReconManagementRepo reconManagementRepo;
	
	@Value("${recon.column.db.name}")
    private String dbNmeValue;
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/getReconConfigList" , produces = "application/json")
	public List<TblMstreconconfig> getReconConfigList(@RequestBody String requst) {
	
		List<TblMstreconconfig> reconConfigs = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			//useraname = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			JSONObject js = new JSONObject(requst);
			//String username = js.getString("username");
			username = js.getString("username");

			log.info("username === " + username);
			
			 reconConfigs = reconManagementService.getReconConfigsAllData(null, null);
				log.info("reconConfigs:::::::::"+reconConfigs);

			log.info("reconConfigs end size::::::" + reconConfigs.size());
			
			
			secondCon.insertIntoSecondSchema(username, "ReconConfig List", requst, reconConfigs.toString(), ipAddress);
			
			return reconConfigs;
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "ReconConfig List-Exception", requst, stackTraceString, ipAddress);
			
			log.info("getting exception on retrive data::::::", e);
		}
		return reconConfigs;
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/addReconConfigData" , produces = "application/json")
	public String addReconConfigData(@RequestBody TblMstreconconfig reconConfigs) {
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		JSONObject response = new JSONObject();
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			String responseData = reconManagementService.addDataInReconConfig(reconConfigs);
			log.info("responseData:::::::::"+responseData);
			if(responseData.equalsIgnoreCase("success")) {
				response.put("status", "true");
				response.put("message", "Saved Recon Config data successfully");
			}
			else {
				response.put("status", "failed");
				response.put("message", "Recon Config data is not saved");
			}
			
			secondCon.insertIntoSecondSchema(username, "Add Recon ConfigData", reconConfigs.toString(), response.toString(), ipAddress);
			return response.toString();
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get User List-Exception", reconConfigs.toString(), stackTraceString, ipAddress);
			
			
			
			log.info("getting exception on retrive data::::::", e);
		}
		return response.toString();
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/editReconConfigData" , produces = "application/json")
	public String editReconConfigData(@RequestBody TblMstreconconfig reconConfigs) {
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
	
		JSONObject response = new JSONObject();
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			String responseData = reconManagementService.editDataInReconConfig(reconConfigs);
			log.info("responseData:::::editReconConfigData::::"+responseData);
			if(responseData.equalsIgnoreCase("success")) {
				response.put("status", "true");
				response.put("message", "Updated Recon Config data successfully");
			}
			else {
				response.put("status", "failed");
				response.put("message", "Recon Config data is not updated");
			}
			
			secondCon.insertIntoSecondSchema(username, "Edit Recon ConfigData", reconConfigs.toString(), response.toString(), ipAddress);
			
			return response.toString();
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Edit Recon ConfigData-Exception", reconConfigs.toString(), stackTraceString, ipAddress);
			
			
			log.info("getting exception on retrive data::::::", e);
		}
		return response.toString();
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value="/getColumnNamesReconConfig" , produces = "application/json")
	public List<String> getColumnNamesReconConfig(@RequestBody String requst) {
	
		List<String> columnNames = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			//username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			JSONObject js = new JSONObject(requst);
			//String username = js.getString("username");
			username = js.getString("username");

			log.info("username ===> " + username);
			log.info("dbNmeValue ===> " + dbNmeValue);
			 columnNames = reconManagementService.getColumnNames(dbNmeValue);
				log.info("columnNames:::::::::"+columnNames);

				
				secondCon.insertIntoSecondSchema(username, "List ColumnNames ReconConfig", requst, columnNames.toString(), ipAddress);
			
			return columnNames;
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "List ColumnNames ReconConfig-Exception", requst, stackTraceString, ipAddress);
			
			log.info("getting exception on retrive data::::::", e);
		}
		return columnNames;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/deleteReconConfig" , produces = "application/json")
	public String deleteReconConfig(@RequestBody String requst) {
	
		JSONObject response = new JSONObject();
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			//username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			JSONObject js = new JSONObject(requst);
			String idVal = js.getString("id");
			log.info("id ===> " + idVal);
			//String username = js.getString("username");
			username = js.getString("username");
			
			
			int idvalue = Integer.valueOf(idVal);
			log.info("username ===> " + username);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			log.info("timestamp:::::::::"+timestamp);
			
			
			String Is_Deleted = "Y";
			int status = reconManagementRepo.deleteReconConfigById(timestamp,username,Is_Deleted,idvalue);
			log.info("status::::::::"+status);
			if (status == 1)
			{
				response.put("status", "true");
				response.put("message", "Recon Config data is deleted !");
				
			} 
			else
			{
				response.put("status", "false");
				response.put("message", "Recon Config data is not deleted !");
			}
			
			log.info("response::::::"+response);
			
			
			secondCon.insertIntoSecondSchema(username, "Delete ReconConfig", requst, response.toString(), ipAddress);
			
			return response.toString();
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete ReconConfig-Exception", requst, stackTraceString, ipAddress);
			
			log.info("getting exception on retrive data::::::", e);
		}
		return response.toString();
	}
}

