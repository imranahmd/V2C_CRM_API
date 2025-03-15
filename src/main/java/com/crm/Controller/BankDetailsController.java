package com.crm.Controller;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.model.BankDetails;
import com.crm.model.MerchantBank;
import com.crm.services.BankDetailsService;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.UserDetailsui;
@RestController
public class BankDetailsController {
	@Autowired
	BankDetailsService bankDetails;
	
	SecondCon secondCon = new SecondCon();
	UserDetailsui userDetailsui = new UserDetailsui();

	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(value="/createUpdateBank", produces = "application/json")
	public Map<String, Object> createUpdateBank(@RequestBody String JSONBody) 
	{
		String username = null;

		String ipAddress = null;

		JSONObject Res = new JSONObject();
		JSONObject BankDetails= new JSONObject(JSONBody);
		String BankName= BankDetails.getString("BankName");
		String ID= BankDetails.getString("Id");
		String BankID= BankDetails.getString("BankId");
		String CreatedBy= BankDetails.getString("CreatedBy");
		String ModifiedBy= BankDetails.getString("ModifiedBy");
		
		JSONObject Bankinfo= new JSONObject();
		try {

			Bankinfo = bankDetails.createBank(BankName,ID,BankID,CreatedBy,ModifiedBy);
			System.out.print("Enter in update method:::::::::::::::::::::: ");
			
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			secondCon.insertIntoSecondSchema(username, "Create Update Bank", JSONBody, Bankinfo.toString(), ipAddress);

			

		} catch (Exception e) {
			e.printStackTrace();
			Res.put("Status",false);
			if(e.getMessage().contains("UNIQUE")) {
				Res.put("Message", "Duplicate entry for Bank Name");
			}
			else {
				Res.put("Message", e.getMessage());
			}
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
			
			secondCon.insertIntoSecondSchema(username, "Create Update Bank-Exception", JSONBody, stackTraceString, ipAddress);
			 
			
			return Res.toMap();
		}
		
		return Bankinfo.toMap();
		
	}
	
	
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@GetMapping(value="/getBankList", produces = "application/json")
	public String getBankList() 
	{
		
		String username = null;

		String ipAddress = null;

		/*
		 *  JSONObject BankDetails= new
		 * JSONObject(JSONBody); String BankName= BankDetails.getString("BankName");
		 * String ID= BankDetails.getString("Id");
		 */
		JSONObject Res = new JSONObject();
		String Bankinfo= null;
		try {

			Bankinfo = bankDetails.getBank();
			System.out.print("Enter in update method:::::::::::::::::::::: ");
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			 username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			secondCon.insertIntoSecondSchema(username, "Get Bank List", "Getting all bank list", Bankinfo.toString(), ipAddress);

			
			

		} catch (Exception e) {
			e.printStackTrace();
			Res.put("Status",false);
			if(e.getMessage().contains("UNIQUE")) {
				Res.put("Message", "Data Not Available");
			}
			else {
				Res.put("Message", e.getMessage());
			}
			

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Get Bank List-Exception", "Getting all bank list", stackTraceString, ipAddress); 
			
			return Res.toString();
		}
		
		return Bankinfo;
		
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(value="/deleteBankDetails", produces = "application/json")
	public Map<String, Object> deleteBankBank(@RequestBody String JSONBody) 
	{
		JSONObject Res = new JSONObject();
		JSONObject BankDetails= new JSONObject(JSONBody);
		String ID= BankDetails.getString("Id");;
		String username = null;

		String ipAddress = null;
		JSONObject Bankinfo= new JSONObject();
		try {

			Bankinfo = bankDetails.deleteBank(ID);
			System.out.print("Enter in update method:::::::::::::::::::::: ");
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			 username = userDetails.optString("Username");

			 ipAddress = userDetails.optString("ipAddress");
			secondCon.insertIntoSecondSchema(username, "Delete Bank Details", JSONBody, Bankinfo.toString(), ipAddress);

			
			

		} catch (Exception e) {
			e.printStackTrace();
			Res.put("Status",false);
			if(e.getMessage().contains("UNIQUE")) {
				Res.put("Message", "Duplicate entry for Bank Name");
			}
			else {
				Res.put("Message", e.getMessage());
			}
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Delete Bank Details-Exception", JSONBody, stackTraceString, ipAddress);
			
			return Res.toMap();
		}
		
		return Bankinfo.toMap();
		
	}
	

	
}
