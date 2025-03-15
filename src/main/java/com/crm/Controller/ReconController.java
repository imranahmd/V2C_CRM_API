package com.crm.Controller;


import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

import com.crm.Repository.ReconManagementRepo;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.model.ReconRecord;
import com.crm.model.TblMstreconconfig;
import com.crm.model.TblTransactionmaster;

import com.crm.services.ReconManagementService;
import com.crm.services.ReconValidationService;
import com.crm.services.UserDetailsui;
import com.google.gson.Gson;


@RestController
public class ReconController {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReconController.class);

//	 private static final String reconFileUploadLocation = "D:/home/recon/upload";
	// // Localfile path
	private static final String reconFileUploadLocation = "/home/recon/upload";// "/home/recon/upload"; //server
																					// file path

	@Autowired
	ReconManagementRepo reconManagementRepo;

	@Autowired
	com.crm.Repository.tblFileUplaodRepo tblFileUplaodRepo;

	@Autowired
	ReconValidationService reconValidationService;

	

	@Autowired
	private ReconManagementService reconManagementService;

	@Value("${sbi.spIDSBINB}")
	private String spIDSBINBVal;

	@Value("${sbi.refundFolderPath}")
	private String refundFolderPath;

	@Value("${sbi.SBIPGCode}")
	private String SBIPGCode;

	String refundReconFileName = null;
	String refundDatafileName = null;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/uploadreconfile")
	public String uploadFile(@RequestParam(name = "file", required = true) MultipartFile reconFile) {
		// System.out.println("reconFile === " + reconFile);
		String fileDetails = reconManagementService.saveFile(reconFile);
		Gson gson = new Gson();
		String jsonArray = gson.toJson(fileDetails);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Upload Recon File ", "Upload File", jsonArray.toString(), ipAddress);
		
		
		
		
		return jsonArray.toString();
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/deletereconfile")
	public String deleteFile(@RequestBody String fileDetails) {
		ArrayList arrayList = new ArrayList();
		String user= null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
	try {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 user = auth.getName();
		 
		 
		 
		 username = userDetails.optString("Username");
		 ipAddress = userDetails.optString("ipAddress");
		 
		 
		 secondCon.insertIntoSecondSchema(username, "Delete Recon  file ", fileDetails.toString(), user, ipAddress);
		 
		}catch(Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete  Recon file-Exception", fileDetails.toString(), stackTraceString, ipAddress);
	}
	
		
		logger.info("user:::::"+user);
		HashMap<String, String> list = new HashMap();
		JSONObject js = new JSONObject(fileDetails);
		String fileId = js.getString("FileId");
		reconManagementService.deleteFile(fileId, user);
		String status = "File Deleted successfully";
		list.put("message", status);
		arrayList.add(list);
		Gson gson = new Gson();
		String jsonArray = gson.toJson(arrayList);
	
	return jsonArray.toString();
}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/GetReconFileList")
	public String getReconFileList(@RequestBody String fileListInput) {
		// System.out.println("fileListInput === " + fileListInput);
		JSONObject js = new JSONObject(fileListInput);
		String ReconDate = js.getString("ReconDate");
		logger.info("ReconDate === " + ReconDate);
		String reconFileList = reconManagementService.getReconFileList(ReconDate);
		logger.info("File List:::::: === " + ReconDate);
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get Recon File List", fileListInput.toString(), reconFileList.toString(), ipAddress);
		
		
		
		
		
		
		
		
		
		return reconFileList;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/validateReconFile",produces = "application/json")
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
			File filePath = new File(reconFileUploadLocation + "/" + fileName);
			TblMstreconconfig reconConfigs = reconManagementService.getReconConfigs("serviceId", serviceId);
			logger.info("recon changes:::::::::::::::; " + reconConfigs);

			if (reconConfigs != null) {
				String Response = reconValidationService.fileUpload(validteReconInput);
				String[] Value1 = Response.split("#");
				String StatusCode = Value1[0];
				String FileId = Value1[1];
				
				try {
				     fileID = Integer.parseInt(FileId);
				    System.out.println(fileID);
				} catch (NumberFormatException e) {
				    System.out.println("Invalid number format");
				}
				if (StatusCode.equalsIgnoreCase("1")) {

					Message.put("Status", true);
					Message.put("message", "File Uploaded succesfully");
					// message="File Uploaded succesfully";

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
			
			
			secondCon.insertIntoSecondSchema(username, "validate Recon File", validteReconInput.toString(), Message.toString(), ipAddress);
			
			return Message.toString();
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "validate Recon File-Exception", validteReconInput.toString(), stackTraceString, ipAddress);
			
			
			
			
			
			logger.info("Exception Value:::::::::::::::: ", e);
		}
		return Message.toString();

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/startRecon", produces = "application/json")
	public String startRecon(@RequestBody String FileId) throws JSONException, SQLException {
		String message = "";
		String statusCode = null;
		JSONObject response = new JSONObject();

		JSONObject js = new JSONObject(FileId);
//		String ServiceId = js.getString("ServiceId");
//		String ReconDate = js.getString("ReconDate");

		statusCode = reconValidationService.startRecon(js.getString("FileId"));
		message = "Your recon process has been started please check progress report.";
		
		response.put("status", "true");
		response.put("message", message);
		Gson gson = new Gson();
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Start Recon", FileId, response.toString(), ipAddress);
		
		
		
		
		return response.toString();
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getReconProgressReport")
	public String getReconProgressReport(@RequestBody String fileListInput) {
		// System.out.println("fileListInput === " + fileListInput);
		String ResponseValue=null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");
		
		
		
		
		try{
			JSONObject js = new JSONObject(fileListInput);
			String ServiceId = js.getString("ServiceId");
			String ReconDate = js.getString("ReconDate");
			
			
			
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			logger.info("ReconDate === " + ReconDate);
			String ProgressReport = reconManagementService.getReconProgressReport(ServiceId, ReconDate);
			if(ProgressReport.equalsIgnoreCase("Fields does not exist")) {
				JSONObject respons = new JSONObject();
				respons.put("Status", "false");
				respons.put("Message", "Fields does not exist");
				ResponseValue = respons.toString();
			}
			else {
				ResponseValue=ProgressReport;
			}
			
			secondCon.insertIntoSecondSchema(username, "GetRecon Progress Report", fileListInput, ResponseValue.toString(), ipAddress);
			
		}catch(Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Recon Progress Report-Exception", fileListInput, stackTraceString, ipAddress);
			
			
			logger.info("Exception :::  :: "+e);
		}
		
		return ResponseValue;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getReconExceptionReport")
	public String getReconExceptionReport(@RequestBody String fileListInput) {
		// System.out.println("fileListInput === " + fileListInput);
		String ProgressReport = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			JSONObject js = new JSONObject(fileListInput);
			String FileId = js.getString("FileId");

			logger.info("FileId === " + FileId);
			ProgressReport = reconManagementService.getReconExceptionExportReport(FileId);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Get Recon Exception Report", fileListInput, ProgressReport.toString(), ipAddress);
			
			return ProgressReport;
		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Recon Exception Report-Exception", fileListInput, stackTraceString, ipAddress);
			
			
			logger.info("getting exception on retrive data::::::", e);
		}
		return ProgressReport;
	}

	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value="/getReconExceptionReportReconRefund" , produces = "application/json")
	public String getReconExceptionReportReconRefund(@RequestBody String request) {
	
		String reconExceptionReportReconRefund = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			JSONObject js = new JSONObject(request);
			String fdate = js.getString("fdate");
			String tdate = js.getString("tdate");
			String transType = js.getString("transType");

			logger.info("fdate::::::"+fdate+":::tdate:::"+tdate+":::::transType::::::"+transType);
			
			
			 reconExceptionReportReconRefund =reconManagementService.getReconExceptionReportReconRefund(fdate,tdate,transType);
			logger.info("reconExceptionReportReconRefund:::::::::::::"+reconExceptionReportReconRefund);
			if(reconExceptionReportReconRefund.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("^\"|\"$", "").equalsIgnoreCase("Data Not Found")) {
				
				JSONObject response = new JSONObject();
				response.append("Error", "Not Found Data");
				
				reconExceptionReportReconRefund = response.toString();
			}
			
			
			secondCon.insertIntoSecondSchema(username, "Get Recon Exception Report Recon Refund", request, reconExceptionReportReconRefund.toString(), ipAddress);
			
			return reconExceptionReportReconRefund;
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Recon Exception Report Recon Refund", request, stackTraceString, ipAddress);
			
			logger.info("getting exception on retrive data::::::", e);
		}
		return reconExceptionReportReconRefund;
	}

	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getForceReconExceptionReportFinal", produces = "application/json")
	public String getForceReconExceptionReportFinal(@RequestBody String jsonBody) {
		// System.out.println("fileListInput === " + fileListInput);
		String ProgressReport = null;
		int status = 0;

		JSONObject Response = new JSONObject();
		JSONObject request = new JSONObject(jsonBody);
		logger.info("request::::::::" + request);
		String actionBy = request.getString("actionBy");
		String actionName = request.getString("actionName");
		String txnIdval = request.getString("txnId");
		String dropDownValue = request.getString("dropDownValue");
		logger.info("actionBy:::::::" + actionBy);
		logger.info("actionName:::::::" + actionName);
		txnIdval = txnIdval.substring(1);

		txnIdval = txnIdval.substring(0, txnIdval.length() - 1);

		logger.info("txnIdval:::::::" + txnIdval);
		String[] txnId = txnIdval.split(",");
		logger.info("txnId:::::::" + txnId);
		logger.info("txnId::::::" + txnId.length);
		TblTransactionmaster txnMaster = null;
		try {
			String data = "";
			logger.info("inside the try block:::::::::::");

			for (int i = 0; i < txnId.length; i++) {

				if (actionName.equalsIgnoreCase("fail")) {
					logger.info("actionName----->" + actionName + "  ,  txnId ::  " + txnId[i]);
					logger.info("inside the fail condition:::::::::::::::::" + actionName);
					status = 0;
					String transactionId = txnId[i];
					logger.info("transactionId:::::::::" + transactionId);
					txnMaster = reconManagementService.getTxnMaster(Long.parseLong(transactionId));
//					txnMaster = AutoRefundProcessor1.getTxnMaster(Long.parseLong(transactionId));
					logger.info("txnMaster:::::::::" + txnMaster);
					logger.info("bankid----->" + txnMaster.getBankId());
					logger.info("txnamount----->" + txnMaster.getTxnAmount());
					logger.info("surcharge----->" + txnMaster.getSurCharge());

					Double txnAmount1 = txnMaster.getTxnAmount();
					Double totalAmount = txnAmount1 + txnMaster.getSurCharge();
					logger.info("totalAmount === " + totalAmount);
					DecimalFormat df = new DecimalFormat("0.00");
					String fTotalAmount = df.format(totalAmount);
					logger.info("fTotalAmount === " + fTotalAmount);

					if (txnMaster != null) {
						logger.info("txnMaster is not null -----");
						String bankId = txnMaster.getBankId();
						logger.info("bankId::::::::::" + bankId);
						int valu = 1;
						try {
							logger.info("inside the try block ::::::::::::");

							if (bankId.equalsIgnoreCase("1029")) {

								String processorClass = "com.crm.processor.SBIAutoRefundProcessor";

								logger.info("processorClass::::::::::::::" + processorClass);

								if (processorClass != null) {
									logger.info("processorClass is not null ----->");

									String verficationCall = null;

									String userId = txnMaster.getMerchantId();
									logger.info("userId ::::::::::::::::" + userId);
									Double txnAmount = txnMaster.getTxnAmount() + txnMaster.getSurCharge();
									logger.info("txnAmount ::::::::::::::::" + txnAmount);
									String refundAmount = String.valueOf(txnAmount);
									logger.info("verficationCall ::::::::::::::::" + verficationCall);

									if (verficationCall != null && verficationCall.equalsIgnoreCase("Success")) {
										logger.info("inside the verficationCall success::::::");
										reconManagementRepo.insertRefundData(transactionId, refundAmount, userId);
										data = data + "";
										int statusv = reconManagementRepo.updateFailRecon(valu, txnId[i], dropDownValue);
										logger.info("statusv:::::"+statusv);
										status=statusv;
										logger.info("status::::"+status);
										
									} else {
										logger.info("inside the verficationCall failed::::::");
										Response.put("Status", "Failed");
										Response.put("Meassage", "Fail Recon is not updated successfully");
									}

								} else {
									logger.info("processorClass is null ----->");
									valu = 1;
									status = reconManagementRepo.updateFailRecon(valu, txnId[i], dropDownValue);
									logger.info("status:::::::::" + status);
								}
							}
							else {
								logger.info("processorClass is null ----->");
								valu = 1;
								status = reconManagementRepo.updateFailRecon(valu, txnId[i], dropDownValue);
								logger.info("status:::::::::" + status);
							}

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}

					} else {

						logger.info("txnMaster is null ----->");
						int valu = 1;
						int statusv = reconManagementRepo.updateFailRecon(valu, txnId[i], dropDownValue);
						logger.info("statusv:::::::::" + statusv);
						status=statusv;
						logger.info("status:::::::::" + status);
					}

					if (status == 1) {
						Response.put("Status", "Success");
						Response.put("Meassage", "Fail Recon updated successfully");
					} else {
						Response.put("Status", "Failed");
						Response.put("Meassage", "Fail Recon is not updated successfully");
					}
				}

				else if (actionName != null && actionName.equals("force")) {
					logger.info("actionName----->" + actionName + "  ,  txnId ::  " + txnId[i]);
					logger.info("inside the force condition:::::::::::::::::" + actionName);

					int valu = 2;
					status = reconManagementRepo.updateForceRecon(valu, txnId[i], dropDownValue);
					logger.info("status"+status);
					if (status == 1) {
						Response.put("Status", "Success");
						Response.put("Meassage", "Force Recon updated successfully");
					} else {
						Response.put("Status", "Failed");
						Response.put("Meassage", "Force Recon is not updated successfully");
					}
				}

			}

			logger.info("data final " + data);
			if (actionName != null && actionName.equals("fail") && txnMaster != null) {
				logger.info("actionName fail but txnMaster not null:::::::::::");
				if (!data.isEmpty()) {
					logger.info("actionName fail but txnMaster not null::if condition:::::::::");
					Date currentdate = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY.hh.mm.ss");
					String formattedDate = dateFormat.format(currentdate);
					logger.info("formattedDate:::::::"+formattedDate);
					byte[] byteData = null;
					String banID = txnMaster.getBankId();
					String processId = txnMaster.getProcessId();
					logger.info("banID:::::::" + banID+"::::::processId:::::"+processId);
					logger.info("spIDSBINBVal:::::::" + spIDSBINBVal);
					String spIdSBINB = spIDSBINBVal;
					logger.info("spIdSBINB::::" + spIdSBINB);

					if (banID.equals("1029") && processId.equals(spIdSBINB)) {
						logger.info("inside the bank id and equal to sbinb:::::::::");
						String dataForReconRefund = "";
						for (int i = 0; i < txnId.length; i++) {
							String transactionId = txnId[i];
							dataForReconRefund = dataForReconRefund
									+ "";
							logger.info("dataForReconRefund::::::::::" + dataForReconRefund);

						}
						byteData = getSBIRefundFile(data, dataForReconRefund);
						String filePath = refundFolderPath;
						String[] files = { refundDatafileName, refundReconFileName };
						String fileName = zipFilesNew(files);

					}
				} else {
					Response.put("Status", "Failed");
					Response.put("Meassage", "Force Recon is not updated successfully");
				}
			}

		} catch (Exception e) {
			logger.info("getting exception on retrive data::::::", e);
		}
		return Response.toString();
	}

	private byte[] getSBIRefundFile(String refundData, String refundReconData) throws IOException {

		logger.info("refundData ::  " + refundData + "refundReconData " + refundReconData);
		byte[] zip = null;
		String underscore_constant = "_";
		if (!refundReconData.isEmpty()) {
			logger.info("refundFolderPath:::::::::::" + refundFolderPath);
			String filePath = refundFolderPath;
			logger.info("filePath:::::::::::" + filePath);

			// String checkSumForData = objSBIRefundProcessor.getCheckSumForData(dataFile);
			String merchant_code = SBIPGCode;
			String sbi_Refund_constant = "SBI_Refund";

			Date currentdate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY.hh.mm.ss");
			String formattedDate = dateFormat.format(currentdate);
			refundReconFileName = merchant_code + underscore_constant + sbi_Refund_constant + underscore_constant
					+ formattedDate + ".txt";

			// create the upload folder if not exists
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdir();
			}

			File refundFile = new File(filePath, refundReconFileName);
			if (refundFile.exists()) {
				refundFile.delete();
			}
			FileWriter fos = new FileWriter(refundFile);
			fos.write(refundReconData);
			fos.close();

			logger.info("File Name " + refundFile);
		}

		if (!refundData.isEmpty()) {
			String filePath = refundFolderPath;

			File dataFile = new File(filePath, "temp.txt");

			if (dataFile.exists()) {
				dataFile.delete();
			}

			FileWriter fosTemp = new FileWriter(dataFile);
			fosTemp.write(refundData);
			fosTemp.close();

			String checkSumForData = this.getCheckSumForData(dataFile);
			String merchant_code = SBIPGCode;
			refundDatafileName = merchant_code + underscore_constant + checkSumForData + ".txt";

			File refundFile = new File(filePath, refundDatafileName);
			if (refundFile.exists()) {
				refundFile.delete();
			}
			FileWriter fos = new FileWriter(refundFile);
			fos.write(refundData);
			fos.close();

			logger.info("File Name " + refundFile);

			logger.info("refundDatafileName " + refundDatafileName + "refundReconFileName " + refundReconFileName);
			String[] files = { refundDatafileName, refundReconFileName };
			logger.info("files.length " + files.length);
			// Checks to see if the directory contains some files.
			if (files != null && files.length > 0) {
				// Call the zipFiles method for creating a zip stream.
				zip = zipFiles(files);
			}
		}
		return zip;
	}

	public String getCheckSumForData(File file) throws IOException {

		MessageDigest md;
		StringBuffer hexString = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(file);

			// Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;

			// Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				md.update(byteArray, 0, bytesCount);
			}
			;
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);

			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
		return hexString.toString();
	}

	private byte[] zipFiles(String[] files) throws IOException {
		String filePath = refundFolderPath;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		byte bytes[] = new byte[2048];

		for (String fileName : files) {
			FileInputStream fis = new FileInputStream(filePath + "/" + fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);

			zos.putNextEntry(new ZipEntry(fileName));

			int bytesRead;
			while ((bytesRead = bis.read(bytes)) != -1) {
				zos.write(bytes, 0, bytesRead);
			}
			zos.closeEntry();
			bis.close();
			fis.close();
		}
		zos.flush();
		baos.flush();
		zos.close();
		baos.close();
		return baos.toByteArray();
	}

	private String zipFilesNew(String[] filePaths) throws IOException {
		String filePath = refundFolderPath;
		File[] resp = new File[filePaths.length];
		for (int i = 0; i < filePaths.length; i++) {
			File a = new File(filePath + "/" + filePaths[i]);
			resp[i] = a;
		}

		Date currentdate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY.hh.mm.ss");
		String formattedDate = dateFormat.format(currentdate);
		String ZipFileName = "Refund" + formattedDate + ".zip";
		File dest = new File(filePath + "/" + ZipFileName);
		ZipUtil.packEntries(resp, dest);

		logger.info("ZipFileName:::::::::" + ZipFileName);
		return ZipFileName;
	}
	
	
	
}
