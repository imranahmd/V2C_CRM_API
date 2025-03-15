package com.crm.Controller;

import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.UserDetailsui;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;

import org.apache.coyote.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.crm.dto.lBPSPaginationDto;
import com.crm.services.ChargeBackAdminService;

@RestController
public class ChargeBackAdminController {
	private static Logger log = LoggerFactory.getLogger(ChargeBackAdminController.class);

	static final int BUFFER = 2048;
	@Autowired
	private ChargeBackAdminService chargebackadminservice;

	@Value("${chargeback.zip.file.store}")
	private String chargebackZipFile;

	@Value("${chargeback.remove.files}")
	private String chargebackRemoveFiles;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getChargeBackDataForRaised", produces = "application/json")
	public String getbyList(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String username = null;

		String ipAddress = null;

		String txnId = js.getString("txnId");
		String bankRefNo = js.getString("bankRefNo");
		String mid = js.getString("mid");
		String fdate = js.getString("fdate");
		String toDate = js.getString("todate");

		log.info("txnID====================================" + txnId);
		log.info("MID====================================" + mid);
		log.info("fdate====================================" + fdate);
		log.info("todate====================================" + toDate);

		String chargeBacks = null;
		try {

			chargeBacks = chargebackadminservice.getChargeBack(txnId, bankRefNo, mid, fdate, toDate);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			secondCon.insertIntoSecondSchema(username, "Get ChargeBackData For Raised", jsonBody, chargeBacks,
					ipAddress);

			if (chargeBacks.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Not Found Data")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");

				chargeBacks = response.toString();
			}

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Get ChargeBackData For Raised-Exception", jsonBody,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return chargeBacks;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "Raisedchargebackinsert", produces = "application/json")
	public String insertChargeback(@RequestParam MultipartFile[] files, String chargeBackId, String merchantId,
			String txnId, String amount, String remarks, String bankLastDate, String merchLastDate,
			String UpfrontNonUpfront, String MerchUpfrontNonUpfront) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;

		String ResponseValue = null;
		ZipOutputStream zos = null;
		BufferedInputStream bis = null;
		String ChargeBackId = chargeBackId;
		String MerchantId = merchantId;
		String TxnId = txnId;
		String Amount = amount;
		String Remarks = remarks;
		String BankLastDate = bankLastDate;
		String MerchLastDate = merchLastDate;
		String MerchUpfrontNonUpfronts = MerchUpfrontNonUpfront;
		String insertchargebacks = null;

		// Parse the date strings into LocalDate objects
		LocalDate dateA = LocalDate.parse(MerchLastDate);
		LocalDate dateB = LocalDate.parse(BankLastDate);
		log.info("MerchLastDate  :::" + dateA);
		log.info("BankLastDate  :::" + dateB);

		if (dateB.isBefore(dateA)) {
			log.info("fail msg ");
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Bank Cut-Off Date must be greater than Merchant Cut-Off Date.");
			ResponseValue = respons.toString();
		} else {
			try {

				log.info("ChargeBackId::::" + ChargeBackId + ":::MerchantId:::" + MerchantId + ":::::TxnId:::" + TxnId
						+ "::::Amount::::" + Amount + "::::Remarks::::" + Remarks + "::::BankLastDate::::"
						+ BankLastDate + "::::MerchLastDate:::::" + MerchLastDate + "::::MerchUpfrontNonUpfronts::::"
						+ MerchUpfrontNonUpfronts);

				// upload multiple files and make it zip
				List<String> fileNames = new ArrayList<>();
				log.info("files:::::::::::" + files);
				Arrays.asList(files).stream().forEach(file -> {
					chargebackadminservice.uploadFile(file);
					fileNames.add(file.getOriginalFilename());
				});
				log.info("fileNames:::::" + fileNames.get(0));

				if (fileNames.get(0).equalsIgnoreCase("faildToNam") || fileNames.get(0).equalsIgnoreCase("")) {
					// The files array is null or empty ------> insert data without file
					log.info("No files uploaded.:::::::::::");

					String zipName = "No Document";

					insertchargebacks = chargebackadminservice.saveRaisedchargeback(chargeBackId, merchantId, txnId,
							amount, remarks, bankLastDate, merchLastDate, UpfrontNonUpfront, zipName,
							MerchUpfrontNonUpfront);

					log.info("insertchargebacks::::::" + insertchargebacks);
				} else {
					// The files array is not null and contains at least one file
					log.info("Files uploaded::::::::: " + files.length);

					// code---------

					List<String> filess = new ArrayList<String>();

					for (int i = 0; i < fileNames.size(); i++) {
						log.info("fileNames:::::" + fileNames.get(i));
//					filess.add("D:/home/abc/" + fileNames.get(i));

						File fileval = new File(chargebackRemoveFiles);

						if (!fileval.exists()) {
							fileval.mkdirs();
						}

						filess.add(chargebackRemoveFiles + fileNames.get(i));
					}

					// create random name for zip
					// create a string of all characters
					String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

					// create random string builder
					StringBuilder sb = new StringBuilder();

					// create an object of Random class
					Random random = new Random();

					// specify length of random string
					int length = 7;

					for (int i = 0; i < length; i++) {

						// generate random index number
						int index = random.nextInt(alphabet.length());

						// get character specified by index
						// from the string
						char randomChar = alphabet.charAt(index);

						// append the character to string builder
						sb.append(randomChar);
					}

					String randomString = sb.toString();
					log.info("Random String is: " + randomString);
					String zipName = randomString + ".zip";
					log.info("zipName:::::" + zipName);
					zipFiles1(filess, zipName);

					insertchargebacks = chargebackadminservice.saveRaisedchargeback(chargeBackId, merchantId, txnId,
							amount, remarks, bankLastDate, merchLastDate, UpfrontNonUpfront, zipName,
							MerchUpfrontNonUpfront);

					log.info("insertchargebacks::::::" + insertchargebacks);

					String data = userDetailsui.getUserDetails();
					JSONObject userDetails = new JSONObject(data);

					username = userDetails.optString("Username");

					ipAddress = userDetails.optString("ipAddress");

					//secondCon.insertIntoSecondSchema(username, "Raised chargeback insert", "Raise Chargeback",
					//		insertchargebacks, ipAddress);

				}

			} catch (Exception e) {

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();

				secondCon.insertIntoSecondSchema(username, "Raised chargeback insert-Exception", "Exception",
						stackTraceString, ipAddress);

				e.printStackTrace();
			}
			if (insertchargebacks.equals("success")) {
				JSONObject respons = new JSONObject();
				respons.put("Status", "success");
				respons.put("Reason", "Chargeback Raised Successfully.");
				
				secondCon.insertIntoSecondSchema(username, "Raised chargeback insert", "ChargeBack","Chargeback Raised Successfully",
						 ipAddress);
				ResponseValue = respons.toString();
				
				
			} else {
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "Oops, something went wrong!");
				
				secondCon.insertIntoSecondSchema(username, "Raised chargeback insert","Chargeback", "Oops, something went wrong!",
						 ipAddress);
				ResponseValue = respons.toString();

			}
		}

		return ResponseValue;
	}

	// method call for the zip

	public void zipFiles1(List<String> files, String zipName) {

		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		try {

//              fos = new FileOutputStream("D:/home/chargebackDocumentAdmin/"+zipName);
			File fileval = new File(chargebackZipFile);

			if (!fileval.exists()) {
				fileval.mkdirs();
			}

			fos = new FileOutputStream(chargebackZipFile + zipName);

			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			for (String filePath : files) {
				File input = new File(filePath);
				fis = new FileInputStream(input);
				ZipEntry ze = new ZipEntry(input.getName());
				log.info("Zipping the file: " + input.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
			}
			zipOut.close();
			log.info("Done... Zipped the files...");

			// remove the files from the directory

//              File directory = new File("D:/home/abc/");
			File directory = new File(chargebackRemoveFiles);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			log.info("directory::::::" + directory);

			for (File file : Objects.requireNonNull(directory.listFiles())) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "chargeBackProcessinglist", produces = "application/json")
	public String getProcessingList(@RequestBody String jsonBody) {
		JSONArray ar = null;
		JSONObject js = new JSONObject(jsonBody);
		String MerchantId = js.getString("merchantId");
		String txnId = js.getString("txnId");
		String cbStatus = js.getString("cbStatus");
		String fromDate = js.getString("fromDate");
		String toDate = js.getString("toDate");

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;

		String process = null;
		try {
			log.info("MerchantId:::" + MerchantId + "::::txnId::::" + txnId + "::::cbStatus:::::" + cbStatus
					+ "::::fromDate::::" + fromDate + "::::toDate::::" + toDate);
			process = chargebackadminservice.getChargeBackProcesssing(MerchantId, txnId, cbStatus, fromDate, toDate);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "ChargeBack Processing List", jsonBody, jsonBody, ipAddress);

			log.info("process::::::::::" + process);
			if (process.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Data Not Found")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");

				process = response.toString();
			}
		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "ChargeBack Processing List-Exception", process,
					stackTraceString, ipAddress);
			e.printStackTrace();
		}
		return process;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "UpdateChargeBackData", produces = "application/json")
	public String updateProcess(@RequestBody String jsonArrayStr, String key) {

		String ResponseValue = null;
		StringBuilder ab = new StringBuilder();

		JSONArray jsonArray = new JSONArray(jsonArrayStr);
		log.info("jsonArray===================" + jsonArray.toString());
		log.info("lenth===================" + jsonArray.length());

		log.info("jsonArray===================" + jsonArray);

		JSONObject response = new JSONObject();
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String username = null;

		String ipAddress = null;

		log.info("jsonArray.length():::::::::" + jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {

			String cbId = jsonArray.getJSONObject(i).getString("cbId").toString();
			String action = jsonArray.getJSONObject(i).getString("action").toString();
			String comments = jsonArray.getJSONObject(i).getString("comments").toString();
			String merchLastDate = jsonArray.getJSONObject(i).getString("merchLastDate").toString();
			String bankLastDate = jsonArray.getJSONObject(i).getString("bankLastDate").toString();
			String TxnId = jsonArray.getJSONObject(i).getString("TxnId").toString();

			try {
				String UpdatecbProcess = null;

				UpdatecbProcess = chargebackadminservice.UpdatechargeBack(cbId, action, comments, merchLastDate,
						bankLastDate, TxnId);
				String data = userDetailsui.getUserDetails();
				JSONObject userDetails = new JSONObject(data);

				username = userDetails.optString("Username");

				ipAddress = userDetails.optString("ipAddress");

				

				if (UpdatecbProcess.equals("success")) {
					response.put("Status", "success");
					response.put("Reason", "Records Updated Successfully.");
					response.put("Chargeback ID", cbId);
					response.put("Transaction ID", TxnId);
					
					secondCon.insertIntoSecondSchema(username, "Update ChargeBack Data", jsonArrayStr, "Records Updated Successfully.",
							ipAddress);
					ab.append(response.toString());
				} else {
					response.put("Status", "fail");
					response.put("Reason", "Oops, something went wrong!");
					response.put("Chargeback ID", cbId);
					response.put("Transaction ID", TxnId);
					
					secondCon.insertIntoSecondSchema(username, "Update ChargeBack Data", jsonArrayStr, "Oops, something went wrong!",
							ipAddress);
					ab.append(response.toString());

				}

			} catch (Exception e) {

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();

				secondCon.insertIntoSecondSchema(username, "Update ChargeBack Data-Exception", "Getting all bank list",
						stackTraceString, ipAddress);
				e.printStackTrace();
			}

		}
		log.info("response::::::::" + response);

		String result = "[" + ab.toString() + "]";

		return result;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "DownloadChargeBackDocs", produces = "application/json")
	public String getCbDocDetails(@RequestBody String jsonBody) {
		JSONArray ar = null;
		JSONObject js = new JSONObject(jsonBody);
		String txnId = js.getString("txnId");
		String cbId = js.getString("cbId");
		String merchantId = js.getString("merchantId");
		String Fdate = js.getString("Fdate");
		String ToDate = js.getString("ToDate");
		log.info("txnId===================" + txnId);
		log.info("cbId===================" + cbId);
		log.info("merchantId===================" + merchantId);
		log.info("Fdate===================" + Fdate);
		log.info("ToDate===================" + ToDate);

		String CbDetails = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			CbDetails = chargebackadminservice.chargeBackDocData(txnId, cbId, merchantId, Fdate, ToDate);

			log.info("CbDetails::::::::::" + CbDetails);
			if (CbDetails.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Data Not Found")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");

				CbDetails = response.toString();
				
				secondCon.insertIntoSecondSchema(username, "Download Charge Back Docs", jsonBody, CbDetails, ipAddress);
			}

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Download Charge Back Docs", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}

		return CbDetails;

	}

	@GetMapping("downloadZipFile")
	public StreamingResponseBody getSteamingFile(@RequestParam String name, HttpServletResponse response)
			throws IOException {

		response.setContentType("application/zip");

		response.setHeader("Content-Disposition", "attachment; filename= " + name);
//		InputStream inputStream = new FileInputStream(new File(chargebackZipFile+"/"+name));
		InputStream inputStream = new FileInputStream(new File(chargebackZipFile + name));

		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				outputStream.write(data, 0, nRead);
			}
		};

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getChargebackReportData", produces = "application/json")
	public String getChargebackReportData(@RequestBody String jsonBody) {
		JSONArray ar = null;
		JSONObject js = new JSONObject(jsonBody);
		String merchantId = js.getString("mid");
		String txnId = js.getString("txnId");
		String Fdate = js.getString("fdate");
		String ToDate = js.getString("todate");
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		String chargebackDatas = null;
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			chargebackDatas = chargebackadminservice.getChargebackReportData(txnId, merchantId, Fdate, ToDate);

			log.info("chargebackDatas::::::::::" + chargebackDatas);
			if (chargebackDatas.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Data Not Found")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");

				chargebackDatas = response.toString();
			}
			secondCon.insertIntoSecondSchema(username, "Get Chargeback Report Data", jsonBody, chargebackDatas, ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Chargeback Report Data", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}

		return chargebackDatas;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "adminActionBasedonDropDown", produces = "application/json")
	public String adminActionBasedonDropDown(@RequestBody String jsonBody) {
		JSONArray ar = null;
		JSONObject js = new JSONObject(jsonBody);
		String statusD = js.getString("statusD");

		log.info("statusD===================" + statusD);
		JSONArray jsonArray = new JSONArray();
		JSONObject jk = new JSONObject();
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			if (statusD.equalsIgnoreCase("RFD")) {

				JSONObject object1 = new JSONObject();
				object1.put("key", "RAD");
				object1.put("Value", "Request for Additional Document");
				jsonArray.put(object1);

				JSONObject object2 = new JSONObject();
				object2.put("key", "CBA");
				object2.put("Value", "Dispute Acknowledged & Refund Initiated");
				jsonArray.put(object2);

				JSONObject object3 = new JSONObject();
				object3.put("key", "CBR");
				object3.put("Value", "Dispute Rejected");
				jsonArray.put(object3);
				secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, jsonArray.toString(), ipAddress);

			}

			else if (statusD.equalsIgnoreCase("RAD")) {

				JSONObject object1 = new JSONObject();
				object1.put("key", "RAD");
				object1.put("Value", "Request for Additional Document");
				jsonArray.put(object1);

				JSONObject object2 = new JSONObject();
				object2.put("key", "CBA");
				object2.put("Value", "Dispute Acknowledged & Refund Initiated");
				jsonArray.put(object2);

				JSONObject object3 = new JSONObject();
				object3.put("key", "CBR");
				object3.put("Value", "Dispute Rejected");
				jsonArray.put(object3);
				secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, jsonArray.toString(), ipAddress);

			} else if (statusD.equalsIgnoreCase("DSM")) {

				JSONObject object1 = new JSONObject();
				object1.put("key", "RAD");
				object1.put("Value", "Request for Additional Document");
				jsonArray.put(object1);

				JSONObject object2 = new JSONObject();
				object2.put("key", "DAA");
				object2.put("Value", "Document Acknowledged");
				jsonArray.put(object2);
				secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, jsonArray.toString(), ipAddress);
			}

			else if (statusD.equalsIgnoreCase("DAA")) {

				JSONObject object1 = new JSONObject();
				object1.put("key", "DSB");
				object1.put("Value", "Document Sent to Bank");
				jsonArray.put(object1);
				secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, jsonArray.toString(), ipAddress);
			} else if (statusD.equalsIgnoreCase("DSB")) {

				JSONObject object1 = new JSONObject();
				object1.put("key", "CBA");
				object1.put("Value", "Dispute Acknowledged & Refund Initiated");
				jsonArray.put(object1);

				JSONObject object2 = new JSONObject();
				object2.put("key", "CBR");
				object2.put("Value", "Dispute Rejected");
				jsonArray.put(object2);
				secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, jsonArray.toString(), ipAddress);
			}

			log.info("jsonArray:::::::" + jsonArray.toString());
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Admin Action Based On DropDown", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}

		return jsonArray.toString();

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getComments", produces = "application/json")
	public String getChargebackcomment(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);

		String txnId = js.getString("txnId");

		log.info("txnID====================================" + txnId);

		String chargeBacks = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		try {
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			chargeBacks = chargebackadminservice.getCBcomment(txnId);

			if (chargeBacks.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Not Found Data")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");

				chargeBacks = response.toString();
				secondCon.insertIntoSecondSchema(username, "Get Comments", jsonBody, chargeBacks, ipAddress);
			}

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Comments", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}

		return chargeBacks;

	}

}
