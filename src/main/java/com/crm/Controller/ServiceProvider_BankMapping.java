package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

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
import com.crm.services.ServiceProviderBankMappingService;
import com.crm.services.UserDetailsui;

@RestController
public class ServiceProvider_BankMapping {
	private static Logger log = LoggerFactory.getLogger(ServiceProvider_BankMapping.class);

	@Autowired
	ServiceProviderBankMappingService SPM_service;
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/Getbankspmlist", produces = "application/json")	
	public ResponseEntity<?> GetbankmapperList(@RequestBody String jsonBody) {
		log.info("entered GetServiceProviderList+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String bank_id=js.getString("bank_id");
		log.info("entered bank_id+++++++============"+bank_id);

		Map<String, Object> Getlist=null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			log.info("entered inside try+++++++============");

			Getlist=SPM_service.getbankspmlist(bank_id);
			
			secondCon.insertIntoSecondSchema(username, "Get banks pm list", jsonBody, Getlist.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get  banks pm list-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
	return ResponseEntity.ok().body(Getlist);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/Getbankcategoryspmlist", produces = "application/json")	
	public ResponseEntity<?> GetbankcategorymapperList(@RequestBody String jsonBody) {
		log.info("entered GetServiceProviderList+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String bank_id=js.getString("bank_id");
		log.info("entered bank_id+++++++============"+bank_id);

		Map<String, Object> Getlist=null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			log.info("entered inside try+++++++============");

			Getlist=SPM_service.getbankcategoryspmlist(bank_id);
			
			
			secondCon.insertIntoSecondSchema(username, "Get bank categorys pm list", jsonBody, Getlist.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get bank categorys pm list", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
	return ResponseEntity.ok().body(Getlist);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/InsertspBankCategoryMapping", produces = "application/json")
	public String createServiceProviderBankCategoryMapping(@RequestBody String jsonBody) {
		String ResponseValue=null;
		log.info("entered createServiceProviderBankCategoryMapping+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String sp_id=js.getString("sp_id");
		String mid=js.getString("mid");
		String bank_id=js.getString("bank_id");
//		String bank_id=null;
//		if(bank_id == null) {
//			bank_id="FINPG";
//		}else {
//			bank_id=js.getString("bank_id");
//		}
		String categoryId=js.getString("categoryId");
		String min_mdr_amt=js.getString("min_mdr_amt");
		String base_rate=js.getString("base_rate");
		String mdr_type=js.getString("mdr_type");
		String Instrument_id=js.getString("Instrument_id");
		String Min_amt=js.getString("Min_amt");
		String Max_amt=js.getString("Max_amt");
		String createdOn=js.getString("createdOn");
		String CreatedBy=js.getString("CreatedBy");
		String modifiedOn=js.getString("modifiedOn");
		
		log.info("entered createServiceProviderBankCategoryMapping  22222222222+++++++============");


		String insertsp=null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		log.info("sp_id  ::"+sp_id+"mid  ::"+mid+"bank_id  ::"+bank_id+"categoryId  ::"+categoryId+"min_mdr_amt  ::"+min_mdr_amt+"base_rate  ::"+base_rate+"mdr_type  ::"+mdr_type+"Instrument_id  ::"+Instrument_id+"Min_amt  ::"+Min_amt+"Max_amt  ::"+Max_amt+"createdOn  ::"+createdOn+"CreatedBy  ::"+CreatedBy+"modifiedOn  ::"+modifiedOn);
		try {
			
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			log.info("entered in try+++++++============");

			insertsp=SPM_service.insertbankcategory(sp_id,mid,bank_id,categoryId,min_mdr_amt,base_rate,mdr_type,Instrument_id,Min_amt,Max_amt,createdOn,CreatedBy,modifiedOn);
			
			
			secondCon.insertIntoSecondSchema(username, "Insert sp Bank Category Mapping", jsonBody, ResponseValue, ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Insert sp Bank Category Mapping-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		if (insertsp.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "created Successfully.");
			ResponseValue = respons.toString();
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();

		}

		return ResponseValue;
	}
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/GetMerchantidList", produces = "application/json")	
	public ResponseEntity<?> GetMerchantIds(@RequestBody String jsonBody) {
		log.info("entered GetMerchantIds+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String admin=js.getString("admin");
		//log.info("entered bank_id+++++++============"+bank_id);

		Map<String, Object> Getlist=null;
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			log.info("entered inside try+++++++============");

			Getlist=SPM_service.getMerchantIds();
			
			secondCon.insertIntoSecondSchema(username, "Get Merchant id List", jsonBody, Getlist.toString(), ipAddress);
			
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Merchant id List-Exception", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}
		
	return ResponseEntity.ok().body(Getlist);
	}
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/GetspID_NAMEList", produces = "application/json")	
	public ResponseEntity<?> GetSPId_Name(@RequestBody String jsonBody) {
		log.info("entered vGetSPId_Name+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String admin=js.getString("admin");
		//log.info("entered bank_id+++++++============"+bank_id);

		Map<String, Object> Getlist=null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			log.info("entered inside try+++++++============");

			Getlist=SPM_service.getspIdName();
			
			
			secondCon.insertIntoSecondSchema(username, "Get sp ID_NAME List", jsonBody, Getlist.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get sp ID_NAME List", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
	return ResponseEntity.ok().body(Getlist);
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/GetcategoryIdList", produces = "application/json")	
	public ResponseEntity<?> GetcategoryId(@RequestBody String jsonBody) {
		log.info("entered vGetSPId_Name+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		log.info("entered json+++++++============");

		String admin=js.getString("admin");
		//log.info("entered bank_id+++++++============"+bank_id);

		Map<String, Object> Getlist=null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			log.info("entered inside try+++++++============");

			Getlist=SPM_service.getcategoryId();
			
			
			secondCon.insertIntoSecondSchema(username, "Get category Id List", jsonBody, Getlist.toString(), ipAddress);
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get category Id List", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}
		
	return ResponseEntity.ok().body(Getlist);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/UpdatespBankCategoryMapping", produces = "application/json")	
	public String UpdateserviceproviderCategoryMapping (@RequestBody String jsonBody) {
		log.info("entered sp UpdateserviceproviderCategoryMapping+++++++============");
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String id=js.getString("id");
		String sp_id=js.getString("sp_id");
		String mid=js.getString("mid");
		//String bank_id=js.getString("bank_id");
		String categoryId=js.getString("categoryId");
		String min_mdr_amt=js.getString("min_mdr_amt");
		String base_rate=js.getString("base_rate");
		String mdr_type=js.getString("mdr_type");
		String instrument_id=js.getString("instrument_id");
		//String bank_id=null;
//		if(instrument_id.equals("CC")||instrument_id.equals("DC")||instrument_id.equals("UPI")||instrument_id.equals("IMPS")||instrument_id.equals("PP")) {
//			bank_id="FINPG";
//		}else if(bank_id == null) {
//			bank_id="FINPG";
//		}else {
//			bank_id=js.getString("bank_id");
//		}
		String bank_id=js.getString("bank_id");

		String Min_amt=js.getString("Min_amt");
		String Max_amt=js.getString("Max_amt");
		String createdOn=js.getString("createdOn");
		String CreatedBy=js.getString("CreatedBy");
		String modifiedOn=js.getString("modifiedOn");
		
		String updatespmdata = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		log.info("entering update role");
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			updatespmdata=SPM_service.UpdatespmbankcategoryMapping(id,sp_id,mid,bank_id,categoryId,min_mdr_amt,base_rate,mdr_type,instrument_id,Min_amt,Max_amt,createdOn,CreatedBy,modifiedOn);
			
			
			secondCon.insertIntoSecondSchema(username, "Update sp Bank Category Mapping", jsonBody, ResponseValue, ipAddress);
			
			
		}catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update sp Bank  Category Mapping-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
			log.info("exception :::::::::::::"+e);
		}
		if (updatespmdata.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Updated  Successfully.");
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
	@PostMapping(value = "/Deletespmbankcategory", produces = "application/json")	
	public String DeletespmbankcategoryMapping (@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		
		Long id=js.getLong("id");
		String deletedata = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			deletedata=SPM_service.deletesp_bank_category(id);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Delete spm bank category", jsonBody, ResponseValue, ipAddress);
			
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete spm bank category-Exception", jsonBody, stackTraceString, ipAddress);
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
	@PostMapping(value = "/InsertspBankMapping", produces = "application/json")
	public String createSPBankMapping(@RequestBody String jsonBody) {
		String ResponseValue=null;
		log.info("entered createServiceProviderBankMapping+++++++============");
		JSONObject js = new JSONObject(jsonBody);
		String sp_id=js.getString("sp_id");
		//String bank_id=js.getString("bank_id");
//		String bank_id=null;
//		if(bank_id == null) {
//			bank_id="FinPG";
//		}else {
//			bank_id=js.getString("bank_id");
//		}
		String bank_id=js.getString("bank_id");
		String sp_bank_id=js.getString("sp_bank_id");
		String base_rate=js.getString("base_rate");
		String mdr_type=js.getString("mdr_type");
		String preference=js.getString("preference");
		String channel_id=js.getString("channel_id");
		String instrument_id=js.getString("instrument_id");
		String min_amt=js.getString("min_amt");
		String max_amt=js.getString("max_amt");
		
		log.info("entered createServiceProviderBankMapping  22222222222+++++++============");


		String insertspm=null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			log.info("entered in try+++++++============");

			insertspm=SPM_service.insertbankmapping(sp_id,bank_id,sp_bank_id,base_rate,mdr_type,preference,channel_id,instrument_id,min_amt,max_amt);
			secondCon.insertIntoSecondSchema(username, "Insert sp Bank Mapping", jsonBody, ResponseValue, ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Insert sp Bank Mapping-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}
		if (insertspm.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "created Successfully.");
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
	@PostMapping(value = "/UpdatespBankMapping", produces = "application/json")	
	public String UpdateserviceproviderBankMapping (@RequestBody String jsonBody) {
		log.info("entered sp UpdateserviceproviderBankMapping+++++++============");
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		String id=js.getString("id");
		String sp_id=js.getString("sp_id");
		//String bank_id=js.getString("bank_id");
		String sp_bank_id=js.getString("sp_bank_id");
		String base_rate=js.getString("base_rate");
		String mdr_type=js.getString("mdr_type");
		String preference=js.getString("preference");
		String channel_id=js.getString("channel_id");
		String instrument_id=js.getString("instrument_id");
	//	String bank_id=null;
//		if(instrument_id.equals("CC")||instrument_id.equals("DC")||instrument_id.equals("UPI")||instrument_id.equals("IMPS")||instrument_id.equals("PP")) {
//			bank_id = "FINPG";
//		}else if (bank_id == null) {
//			bank_id = "FINPG";
//		}else {
//			 bank_id =js.getString("bank_id");
//			}
		String bank_id=js.getString("bank_id");

		String min_amt=js.getString("min_amt");
		String max_amt=js.getString("max_amt");
		
		String updatespmdata = null;
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
			updatespmdata=SPM_service.UpdatespmbankMapping(id,sp_id,bank_id,sp_bank_id,base_rate,mdr_type,preference,channel_id,instrument_id,min_amt,max_amt);
			secondCon.insertIntoSecondSchema(username, "Update sp Bank Mapping", jsonBody, ResponseValue, ipAddress);
		}catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update sp Bank Mapping-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
			log.info("exception :::::::::::::"+e);
		}
		if (updatespmdata.equals("success")) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Updated  Successfully.");
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
	@PostMapping(value = "/Deletespbankmapping", produces = "application/json")	
	public String DeletespmbankMapping (@RequestBody String jsonBody) {
		String ResponseValue=null;
		JSONObject js = new JSONObject(jsonBody);
		
		Long id=js.getLong("id");
		String deletedata = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			deletedata=SPM_service.deletesp_bank_mapping(id);
			secondCon.insertIntoSecondSchema(username, "Delete sp bank mapping", jsonBody, ResponseValue, ipAddress);
		}catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete sp bank mapping-Exception", jsonBody, stackTraceString, ipAddress);
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
}
