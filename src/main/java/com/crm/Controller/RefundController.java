package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.UserDetailsui;

@RestController
public class RefundController {
	static Logger log = LoggerFactory.getLogger(RefundController.class);
	@Autowired
	private com.crm.services.RefundTransactionService refundTransService;
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetRaiseRefundTranssaction-List")
	public ResponseEntity<?>  getRefundList(@RequestBody String jsonBody)
	{
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		JSONObject js = new JSONObject(jsonBody);
		Map<String, Object> refundTransaction = null;
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			
			refundTransaction = refundTransService.getRefundTransactionList(js.getString("Paymentsid"), js.getString("merchanTransactionId"), js.getString("merchantId") ,
					js.getString("fromDate") , js.getString("toDate") , js.getString("bankId") , js.getString("custMail") , js.getString("custMobile"),js.getString("spId"));
			
			
			
			secondCon.insertIntoSecondSchema(username, "Get Raise Refund Transsaction-List", jsonBody, refundTransaction.toString(), ipAddress);
			
			
		} catch (JSONException e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Raise Refund Transsaction-List-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
			log.info("Refund List json Error   " + js + e.getMessage());
		} catch (ParseException e) {
			
			e.printStackTrace();
			log.info("Refund List Error   " + e.getMessage());
		}
		return  ResponseEntity.ok(refundTransaction);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/RaiseRefund")
	public ResponseEntity<Object> insertRefundAmt(@RequestBody String jsonBody)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		JSONArray jsonarray = new JSONArray(jsonBody);
		Object raiseRefundList = null;
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			raiseRefundList = refundTransService.createRefundAmtt(jsonarray);
			
			secondCon.insertIntoSecondSchema(username, "Raise Refund", jsonBody, raiseRefundList.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Raise Refund-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			ex.printStackTrace();
			log.info("RaiseRefund Error   " + ex.getMessage());
		}	
		return ResponseEntity.ok().body(raiseRefundList);
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/BulkRefund")
	public ResponseEntity<?> BulkRefundDetails(@RequestParam MultipartFile file, String userId)
	{
			
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		Map<String, String> bulkRefundFileList = new HashMap<>();
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			bulkRefundFileList = refundTransService.initiateBulkRefund(file, userId);
			
			secondCon.insertIntoSecondSchema(username, "Bulk Refund", userId, bulkRefundFileList.toString(), ipAddress);
			
			
		}catch(Exception ex) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Bulk Refund-Exception", userId, stackTraceString, ipAddress);
			
			
			log.info("Error while processing bulk refund ",ex);
			bulkRefundFileList.put("Error","Error while Processing Your Request");
		}
		
		return ResponseEntity.ok().body(bulkRefundFileList);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/DownloadManual-RefundFileFromList")
	public ResponseEntity<?> DownloadManualRefundList(@RequestBody String jsonBody)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		JSONArray jsonarray = new JSONArray(jsonBody);
		Map<String, Object> refundStatusRequest = null;
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			refundStatusRequest = refundTransService.getRefundDownloadFileFomList(jsonarray);
			
			
			secondCon.insertIntoSecondSchema(username, "Download Manual-Refund File From List", jsonBody, refundStatusRequest.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Download Manual-Refund File From List-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			ex.printStackTrace();
			log.info("BulkRefund Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(refundStatusRequest);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/DownloadManual-RefundFile")
	public ResponseEntity<?> DownloadManualRefundFile(@RequestBody String jsonBody)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		JSONObject js = new JSONObject(jsonBody);
		Map<String, Object> refundStatusRequest = null;
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			refundStatusRequest = refundTransService.getRefundDownloadRecordsList(js.getString("Paymentsid"), js.getString("merchanTransactionId"), js.getString("merchantId") ,
					js.getString("fromDate") , js.getString("toDate") , js.getString("spId") , js.getString("custMail") , js.getString("custMobile"), js.getString("count"), 
					js.getInt("pageNo"), js.getInt("Type"), js.getString("SearchBy"), js.getString("refundType") );
			
			
			secondCon.insertIntoSecondSchema(username, "Download Manual-Refund File", jsonBody, refundStatusRequest.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Download Manual-Refund File-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			ex.printStackTrace();
			log.info("BulkRefund Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(refundStatusRequest);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/RefundRequestStatus")
	public ResponseEntity<?> RefundRequestStatus(@RequestBody String jsonBody)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		JSONObject js = new JSONObject(jsonBody);
		Map<String, Object> refundStatusRequest = null;
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			refundStatusRequest = refundTransService.getRefundTransactionStatusList(js.getString("Paymentsid"), js.getString("merchanTransactionId"), js.getString("refundId") ,
					js.getString("fromDate") , js.getString("toDate") , js.getString("refundType") , js.getString("refundStatus") ,  js.getString("count"), 
					js.getInt("pageNo"), js.getInt("Type"), js.getString("SearchBy") );
			
			
			secondCon.insertIntoSecondSchema(username, "Refund Request Status", jsonBody, refundStatusRequest.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Refund Request Status-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			ex.printStackTrace();
			log.info("BulkRefund Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(refundStatusRequest);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/Upload-ManualRefundFile")
	public ResponseEntity<?> RefundUploadManual(@RequestParam MultipartFile file, String userId, String sPID)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		Map<String, Object> uploadManualFile = null;
		try {
			
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			uploadManualFile = refundTransService.uploadManualRefundFile(file, userId, sPID);
			
			
			secondCon.insertIntoSecondSchema(username, "Upload-Manual Refund File", userId, uploadManualFile.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Upload-Manual Refund File-Exception", userId, stackTraceString, ipAddress);
			
			ex.printStackTrace();
			log.info("Upload Manual Refund File Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(uploadManualFile);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/AtomRefund-FromFile")
	public ResponseEntity<?> AtomRefundFromFile(@RequestParam MultipartFile file, String userId)
	{
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		Map<String, Object> atomRefund = null;
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			atomRefund = refundTransService.atomRefundFile(file, userId);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Atom Refund-From File", userId, atomRefund.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Atom Refund-From File-Exception", userId, stackTraceString, ipAddress);
			
			
			
			ex.printStackTrace();
			log.info("Upload Manual Refund File Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(atomRefund);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/RefundRequestStatusMerchant")
	public ResponseEntity<?> RefundRequestStatusMerchant(@RequestBody String jsonBody)
	{
		JSONObject js = new JSONObject(jsonBody);
		Map<String, Object> refundStatusRequest = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			refundStatusRequest = refundTransService.getRefundTransactionStatusListMerchant(js.getString("MerchantId"),js.getString("Paymentsid"), js.getString("merchanTransactionId"), js.getString("refundId") ,
					js.getString("fromDate") , js.getString("toDate") , js.getString("refundType") , js.getString("refundStatus") ,  js.getString("count"), 
					js.getInt("pageNo"), js.getInt("Type"), js.getString("SearchBy") );
			
			
			
			
			secondCon.insertIntoSecondSchema(username, "Refund Request Status Merchant", jsonBody, refundStatusRequest.toString(), ipAddress);
			
		}catch(Exception ex) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Refund Request Status Merchant-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			ex.printStackTrace();
			log.info("BulkRefund Error   " + ex.getMessage());
		}
		
		return ResponseEntity.ok().body(refundStatusRequest);
	}
}
