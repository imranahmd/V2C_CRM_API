package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.Repository.MerchantRepository;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.RechargeRequestService;
import com.crm.services.UserDetailsui;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class RechargeRequestController {
	
	private static Logger log = LoggerFactory.getLogger(RechargeRequestController.class);

	@Autowired
	private RechargeRequestService rechargeReqservice;
	

	@Autowired
	private MerchantRepository merchantRepo;
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getRechargeReq", produces = "application/json")
	public String getbyList(@RequestBody String jsonBody,HttpServletRequest request) {
		
		  String ipAddress = request.getHeader("X-Forwarded-For");//request.getRemoteAddr();
		
		log.info("ipAddress:::::::::::::::::::::::::::::::::;"+ipAddress);
		JSONObject js = new JSONObject(jsonBody);
		log.info("json js:::::::::::::::::"+js.toString());

		
		String txnId = js.getString("txn_id");
		String merchantId = js.getString("merchant_id");
		String amount = js.getString("amount");
		String accNo = js.getString("account_no");
		String utrNo = js.getString("utr_no");
		String createdBy = js.getString("created_by");
		//String createdOn = js.getString("created_on");
		
		String createdOn =null;
		//String rodt = js.getString("rodt");
		//String isApproved = js.getString("isApproved");
		String serviceType = js.getString("service_type");
		
		String ResponseValue = null;
		
		log.info("txnID====================================" + txnId);
		log.info("merchantId====================================" + merchantId);
		log.info("amount====================================" + amount);
		log.info("accNo====================================" + accNo);
		log.info("utrNo====================================" + utrNo);
		log.info("createdBy====================================" + createdBy);
		log.info("createdOn====================================" + createdOn);
	//	log.info("rodt====================================" + rodt);
		//log.info("isApproved====================================" + isApproved);
		log.info("serviceType====================================" + serviceType);
		boolean utrAvail=false;
		String recharge = null;
		try {
			//boolean utrAvail = merchantRepo.findUtrNumber(utrNo);
			if(txnId.equalsIgnoreCase(""))
			{
				 utrAvail = merchantRepo.findSameUtrNumber(utrNo);
			}
			else
			{
				 utrAvail = merchantRepo.findUtrNumber(utrNo,txnId);
			}
			
			if(utrAvail == true)
			{
				log.info("Inside the if condition UTR is there");
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "This UTR Already Available!");
				ResponseValue = respons.toString();
				
			}
				else 
				{
					log.info("Inside the else condition");
				

			recharge = rechargeReqservice.getRechargeDetails(txnId,merchantId,amount,accNo,utrNo,createdBy,serviceType,ipAddress);

			log.info("recharge::::::::::::::::::"+recharge);

			if (recharge.equals("success")) {
				JSONObject respons = new JSONObject();
				respons.put("Status", "success");
				if(txnId.equalsIgnoreCase(""))
				{
					respons.put("Reason", "Recharge Details Inserted Successfully.");
				}
				else
				{
					respons.put("Reason", "Recharge Details Updated Successfully.");
				}
				//respons.put("Reason", "Recharge details inserted Successfully.");
				ResponseValue = respons.toString();
			} else {
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "Oops, Something Went Wrong!");
				ResponseValue = respons.toString();

			}
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseValue;

	}

	//update Recharge
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getRechargeUpdate", produces = "application/json")
	public String getRechUpdate(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);
		log.info("json js:::::::::::::::::"+js.toString());

		String txnId =js.getString("txn_id");
		String merchantId = js.getString("merchant_id");
		String amount = js.getString("amount");
		String accNo = js.getString("account_no");
		String utrNo = js.getString("utr_no");
		String createdBy = js.getString("created_by");
		//String createdOn = js.getString("created_on");
		



		String createdOn =null;
		//String rodt = js.getString("rodt");
		//String isApproved = js.getString("isApproved");
		String serviceType = js.getString("service_type");
		
		String ResponseValue = null;
		
		log.info("txnID====================================" + txnId);
		log.info("merchantId====================================" + merchantId);
		log.info("amount====================================" + amount);
		log.info("accNo====================================" + accNo);
		log.info("utrNo====================================" + utrNo);
		log.info("createdBy====================================" + createdBy);
		log.info("createdOn====================================" + createdOn);
	//	log.info("rodt====================================" + rodt);
		//log.info("isApproved====================================" + isApproved);
		log.info("serviceType====================================" + serviceType);

		String recharge = null;
		try {
			boolean utrAvail = merchantRepo.findSameUtrNumber(utrNo);
			
			if(utrAvail == true)
			{
				log.info("Inside the if condition UTR is there");
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "This UTR Already Exist!");
				ResponseValue = respons.toString();
				
			}
				else 
				{
					log.info("Inside the else condition");
				

			recharge = rechargeReqservice.getRechargeDetailUpdated(txnId,merchantId,amount,accNo,utrNo,createdBy,serviceType);

			log.info("recharge::::::::::::::::::"+recharge);

			if (recharge.equals("success")) {
				JSONObject respons = new JSONObject();
				respons.put("Status", "success");
				respons.put("Reason", "Recharge Details Updated Successfully.");
				ResponseValue = respons.toString();
			} else {
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "Oops, Something Went Wrong!");
				ResponseValue = respons.toString();

			}
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseValue;

	}

	
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getRechargeList", produces = "application/json")
	public String getList(@RequestBody String jsonBody) {

		log.info("getRechargeList:::::::::::::::::::");
		String responseVal=null;
		
		JSONObject js = new JSONObject(jsonBody);
		
		log.info("js:::::::::::::::::"+js);
		
		String status = js.getString("status");
		String approve = js.getString("IsApprove");
		
		log.info("status:::::::::::::::::"+status);
		log.info("approve:::::::::::::::::"+approve);
		
		String list=null;
		try 
		{
			list = rechargeReqservice.getRechargeList(status,approve);
			log.info("list::::::::::::::"+list);
			if (list.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Not Found Data")) {

				JSONObject response = new JSONObject();
				//response.append("Error", "No Data Found!");
				//response.put("Status", "fail");
				//response.put("Reason", "Oops, something went wrong!");
				
				response.put("Status", "fail");
				response.put("Reason", "No Data Found!");

				list = response.toString();
			}

		}
		catch(Exception e) 
		{
		e.printStackTrace();	
			
		}
		
		log.info("list:::::::::::::::::::::::::"+list);
	return list;
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getApproval", produces = "application/json")
	public String getApprove(@RequestBody String jsonBody,HttpServletRequest request) {

		  String ipApprove = request.getHeader("X-Forwarded-For");//request.getRemoteAddr();
		
		log.info("getRechargeList:::::::::::::::::::");
		String responseVal=null;
		
		JSONObject js = new JSONObject(jsonBody);
		
		log.info("ipApprove::::::::::::::::"+ipApprove);
		
		log.info("js:::::::::::::::::"+js);
		String txn_id = js.getString("txn_id");
		String amount = js.getString("amount");
		String utr_no = js.getString("utr_no");
		String merchant_id = js.getString("merchant_id");
		String account_no = js.getString("account_no");
		String createdOn = js.getString("createdOn");
		String status = js.getString("status");
		String isApproved = js.getString("isApproved");
		String remark = js.getString("remark");
		String ApproveBy = js.getString("approvedBy");
		
		
		log.info("txn_id:::::::::::::::::"+txn_id);
		log.info("amount:::::::::::::::::"+amount);
		log.info("utr_no:::::::::::::::::"+utr_no);
		log.info("merchant_id:::::::::::::::::"+merchant_id);
		log.info("account_no:::::::::::::::::"+account_no);
		log.info("createdOn:::::::::::::::::"+createdOn);
		log.info("status:::::::::::::::::"+status);
		log.info("isApproved:::::::::::::::::"+isApproved);
		log.info("remark:::::::::::::::::"+remark);
	    log.info("ApproveBy:::::::::::::::::"+ApproveBy);
		
		String resp=null;
		try 
		{
			resp = rechargeReqservice.getRechargeUpdated(txn_id,amount,utr_no,merchant_id,account_no,createdOn,status,isApproved,remark,ApproveBy,ipApprove);
			log.info("resp::::::::::::::"+resp);
		
			if (resp.equalsIgnoreCase("success")) {
				
				log.info("Inside if success:::::::::::::::::::::");
				JSONObject respons = new JSONObject();
				respons.put("Status", "success");
				respons.put("Reason", "Recharge Status Updated Successfully.");
				responseVal = respons.toString();
			} else {
				log.info("Inside if success:::::::::::::::::::::");
				JSONObject respons = new JSONObject();
				respons.put("Status", "fail");
				respons.put("Reason", "Oops, Something Went Wrong!");
				responseVal = respons.toString();

			}
			log.info("::::::::::after response put::::::::::");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseVal;

	}
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "getRList", produces = "application/json")
	public String getRList(@RequestBody String jsonBody) {

		log.info("getRList:::::::::::::::::::");
		String responseVal=null;
		
		JSONObject js = new JSONObject(jsonBody);
		
		log.info("js:::::::::::::::::"+js);
		
		//String status = js.getString("status");
		//String approve = js.getString("IsApprove");
		
	//	log.info("status:::::::::::::::::"+status);
		//.info("approve:::::::::::::::::"+approve);
		
		String list=null;
		try 
		{
			list = rechargeReqservice.getAllRList();
			log.info("list::::::::::::::"+list);
			if (list.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Not Found Data")) {

				JSONObject response = new JSONObject();
				//response.append("Error", "No Data Found!");
				//response.put("Status", "fail");
				//response.put("Reason", "Oops, something went wrong!");
				
				response.put("Status", "fail");
				response.put("Reason", "No Data Found!");

				list = response.toString();
			}

		}
		catch(Exception e) 
		{
		e.printStackTrace();	
			
		}
		
		log.info("list:::::::::::::::::::::::::"+list);
	return list;
	}
	
	

	//Delete Recharge
			@CrossOrigin(origins = { "http://localhost:4200" })
			@PostMapping(value = "getRechargeDeleted", produces = "application/json")
			public String getRechDelete(@RequestBody String jsonBody) {

				JSONObject js = new JSONObject(jsonBody);
				log.info("json js:::::::::::::::::"+js.toString());

				String txnId =js.getString("txn_id");
				String isApproved=js.getString("isApproved"); 

				
				String ResponseValue = null;
				
				log.info("txnID====================================" + txnId);
				log.info("isApproved====================================" + isApproved);

				String recharge = null;
				try {
				

					recharge = rechargeReqservice.getRechargeDetailDeleted(txnId,isApproved);

					log.info("recharge::::::::::::::::::"+recharge);

					if (recharge.equals("success")) {
						JSONObject respons = new JSONObject();
						respons.put("Status", "success");
						respons.put("Reason", "Recharge Details Deleted Successfully.");
						ResponseValue = respons.toString();
					} else {
						JSONObject respons = new JSONObject();
						respons.put("Status", "fail");
						respons.put("Reason", "Oops, Something Went Wrong!");
						ResponseValue = respons.toString();

					}
					
				
				}catch (Exception e) {
					e.printStackTrace();
				}

				return ResponseValue;

			}


	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping(value = "/getUserList", produces = "application/json")
	public String getUserList(@RequestBody String jsonBody) {

		
		
		log.info("getRechargeList:::::::::::::::::::");
		String responseVal=null;
		
		JSONObject js = new JSONObject(jsonBody);
		
		String admin = js.getString("Admin");
		log.info("js:::::::::::::::::"+js);
		 SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = null;

			String ipAddress = null;
				
	
		
		String list=null;
		try 
		{
			 username = userDetails.optString("Username");

			 ipAddress = userDetails.optString("ipAddress");
			 
			list = rechargeReqservice.getuserList();
			log.info("list::::::::::::::"+list);
			if (list.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
					.equalsIgnoreCase("Not Found Data")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");
				//response.append("Status", "fail");
				//response.append("Message", "Not Found Data!");

				list = response.toString();
				secondCon.insertIntoSecondSchema(username, "Get User List", jsonBody, list, ipAddress);
				log.info("======<<<===<<<====<<<<"+list);
			}
			  
				
			
			

		}
		catch(Exception e) 
		{
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Get User List-Exception", jsonBody, stackTraceString, ipAddress);

			
		e.printStackTrace();	
			
		}
		
		log.info("list======<<<===<<<====<<<<"+list);
	return list;
	}

	//Status Update
	

		@CrossOrigin(origins = { "http://localhost:4200" })
		@PostMapping(value = "getTOFTxnList")
		public String getTOFTxnList(@RequestBody String jsonBody) {

			log.info("getTOFTxnList::::::::::::::::::: "+jsonBody);
			String responseVal=null;
			
			JSONObject js = new JSONObject(jsonBody);
			
			log.info("js:::::::::::::::::"+js);
			

			
			String PGTxnId = js.getString("PGTxnId");
			String MTxnId = js.getString("MTxnId");
			//String Status = js.getString("Status");
			String AuthId = js.getString("AuthId");
			String FromDate = js.getString("FromDate");
			String ToDate = js.getString("ToDate");
			String VPA = js.optString("VPA");
			
			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();
						
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = null;
			String ipAddress = null;
			
			
			
			String list=null;
			try 
			{
				list = rechargeReqservice.getTOFTxnList(PGTxnId,MTxnId,AuthId,FromDate,ToDate,VPA);
				log.info("list::::::::::::::"+list);
				
				
				username = userDetails.optString("Username");
				ipAddress = userDetails.optString("ipAddress");
				
				if (list.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("^\"|\"$", "")
						.equalsIgnoreCase("Not Found Data")) {

					JSONObject response = new JSONObject();
					//response.append("Error", "No Data Found!");
					//response.put("Status", "fail");
					//response.put("Reason", "Oops, something went wrong!");
					
					response.put("Status", "fail");
					response.put("Reason", "No Data Found!");
					
					

					list = response.toString();
					secondCon.insertIntoSecondSchema(username, "Get Txn List", jsonBody, list, ipAddress);
				}

			}
			catch(Exception e) 
			{
				
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
				
				secondCon.insertIntoSecondSchema(username, "Get User List-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();	
				
			}
			
			log.info("list:::::::::::::::::::::::::"+list);
		return list;
		}
		
		
		//2nd -update
		
		@CrossOrigin(origins = { "http://localhost:4200" })
		@PostMapping(value = "updateTOFList", produces = "application/json")
		public String updateTOFList(@RequestBody String jsonBody) {

			String responseVal=null;
			log.info("getTOFTxnList:::::::::::::::::::");
			//String responseVal=null;
			
			JSONObject js = new JSONObject(jsonBody);
			
			log.info("js:::::::::::::::::"+js);
			String AuthId = js.getString("AuthId");
			String PGTxnId = js.getString("PGTxnId");
			String Status = js.getString("Status");
			String RRN = js.getString("RRN");
			String amt = js.getString("amt");
			String MtxnId=js.getString("MTxnId");
			String MProcId=js.getString("MProcId");
			
			log.info("PGTxnId::::::::::::::::"+PGTxnId);
			
			
			String list=null;
			
			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);
			String username = null;
			String ipAddress = null;
			
			try 
			{
				
				username = userDetails.optString("Username");

				ipAddress = userDetails.optString("ipAddress");
				
				list = rechargeReqservice.getTOFUpdateTxnList(AuthId,PGTxnId,Status,RRN,amt,MtxnId,MProcId);
				log.info("list::::::::::::::"+list);
				
				if (list.equals("success")) {
					JSONObject respons = new JSONObject();
					respons.put("Status", "success");
					respons.put("Reason", "Status Updated Successfully.");
					responseVal = respons.toString();
					secondCon.insertIntoSecondSchema(username, "Update List", jsonBody, responseVal.toString(), ipAddress);
				} else {
					JSONObject respons = new JSONObject();
					respons.put("Status", "fail");
					respons.put("Reason", "Oops, something went wrong!");
					responseVal = respons.toString();
					secondCon.insertIntoSecondSchema(username, "Update List", jsonBody, responseVal.toString(), ipAddress);

				}

				


			}
			catch(Exception e) 
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
										
				secondCon.insertIntoSecondSchema(username, "Update list-Exception", jsonBody, stackTraceString, ipAddress);
				
			
			e.printStackTrace();	
				
			}
			
			log.info("list:::::::::::::::::::::::::"+list);
		return responseVal;
		}

	
}
