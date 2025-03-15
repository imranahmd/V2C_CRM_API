package com.crm.Controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.sound.midi.Track;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.model.ReconRecord;
import com.crm.model.TblMstreconconfig;
import com.crm.services.ReconManagementService;
import com.crm.services.ReconManagementServiceV1;
import com.crm.services.ReconValidationServiceV1;
import com.crm.services.UserDetailsui;
import com.google.gson.Gson;

@RestController
public class ReconControllerV1 {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReconControllerV1.class);

//	private static final String reconFileUploadLocation = "D:\\home\\recon\\upload";// "/home/recon/upload"; //server
	private static final String reconFileUploadLocation = "/home/recon/upload";// "/home/recon/upload"; //server


	@Autowired
	private ReconManagementServiceV1 reconManagementService;

	@Autowired
	private ReconValidationServiceV1 reconValidationService;

	@Autowired
	com.crm.Repository.tblFileUplaodRepo tblFileUplaodRepo;



	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/validateReconFileV1")
	public String validateReconFile(@RequestBody String validteReconInput) throws Exception {
		String message = null;
		JSONObject Message = new JSONObject();
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		

		try {

			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			int fileID=0;
			// JSONObject Message = new JSONObject();
			JSONObject js = new JSONObject(validteReconInput);
			// System.out.println("validteReconInput === " + validteReconInput);
			String serviceId = js.getString("serviceId");
			String ReconDate = js.getString("ReconDate");
			String fileName = js.getString("fileName");
			logger.info("serviceId::::::"+serviceId+"::::ReconDate::::"+ReconDate+":::fileName:::"+fileName);
			File filePath = new File(reconFileUploadLocation + "/" + fileName);
			TblMstreconconfig reconConfigs = reconManagementService.getReconConfigs("serviceId", serviceId);
			logger.info("recon changes:::::::::::::::; " + reconConfigs);

			if (reconConfigs != null) {
				String Response = reconValidationService.fileUpload(validteReconInput);
				String[] Value1 = Response.split("#");
				String StatusCode = Value1[0];
				String FileId = Value1[1];
				if (StatusCode.equalsIgnoreCase("1")) {

					Message.put("Status", true);
					Message.put("message", "File Uploaded succesfully");
					// message="File Uploaded succesfully";
					
					try {
					     fileID = Integer.parseInt(FileId);
					    System.out.println(fileID);
					} catch (NumberFormatException e) {
					    System.out.println("Invalid number format");
					}
					Optional<ReconRecord> tblfileUploadstatus1 = tblFileUplaodRepo.findById(fileID);// new
																									// ReconRecord();
					ReconRecord tblfileUploadstatus = tblfileUploadstatus1.get();
					reconValidationService.ValidReconDetails(reconConfigs, tblfileUploadstatus, filePath);
				} else {
					Message.put("Status", false);
					Message.put("message", FileId);
				}
			} else {
				Message.put("Status", false);
				Message.put("message", "Recon Configuration not found against the selected SP/Bank.");
			}
			
			
			secondCon.insertIntoSecondSchema(username, "validate Recon FileV1", validteReconInput, Message.toString(), ipAddress);
			
			return Message.toString();
		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "validate Recon FileV1-Exception", validteReconInput, stackTraceString, ipAddress);
			
			
			logger.info("Exception Value:::::::::::::::: ", e);
		}
		return Message.toString();

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/startReconV1", produces = "application/json")
	public String startRecon(@RequestBody String FileId) throws JSONException, SQLException {
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		
		
		String message = "";
		String statusCode = null;
		JSONObject js = new JSONObject(FileId);
		JSONObject response = new JSONObject();
//		String ServiceId = js.getString("ServiceId");
//		String ReconDate = js.getString("ReconDate");

		statusCode = reconValidationService.startRecon(js.getString("FileId"));
		message = "Your recon process has been started please check progress report.";
		
		response.put("status", "true");
		response.put("message", message);
		
		Gson gson = new Gson();
		
		
		secondCon.insertIntoSecondSchema(username, "Start Recon V1", FileId,response.toString() , ipAddress);
		
		return response.toString();
	}

	
}
