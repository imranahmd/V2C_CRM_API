package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.http.HttpRequest;
import com.crm.services.UserDetailsui;
import com.crm.ServiceDaoImpl.SecondCon; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.servlet.http.HttpServletRequest;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.redis.core.Cursor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.crm.dto.MerchantIdDto;
import com.crm.dto.TransactionMaster;
import com.crm.helper.JwtHelperUtil;
import com.crm.model.MerchantList;
import com.crm.services.RmsConfigServices;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;



/*@CrossOrigin(origins = {"http://localhost:4200"})
*/@RestController
public class RmsConfigController {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(RmsConfigController.class);

	@Autowired
	private RmsConfigServices RmsConfigServices;

	
	
	//  @Autowired MongoTemplate mongoTemplate;
	 
	
	@Autowired
	private com.crm.services.RmsMisService RmsMisService;
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/get-merchany-by-name")
	public List<MerchantList> getListById(@RequestBody String name) {
		JSONObject js = new JSONObject(name);
		String merchantName = js.getString("name");
		List<MerchantList> menus = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			
			menus = RmsConfigServices.getMerchantByName(merchantName);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Get-merchany-by-name", name, menus.toString(), ipAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}
	

	@GetMapping("/get-all-menus")
	public Map<String, List<String>> getAllMenus(@RequestBody String name) {
		JSONObject js = new JSONObject(name);
		String mid = js.getString("name");
		Map<String, List<String>> menus = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			menus = RmsConfigServices.getAllMenus(mid);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Get-all-menus", name, menus.toString(), ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get-all-menus-Exception", name, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
		return menus;
	}
	
	
	@GetMapping("/get-all-merchants")
	public List<MerchantList> getAllMerchants(@RequestBody String name) {
		JSONObject js = new JSONObject(name);
		String mid = js.getString("name");
		List<MerchantList> allMerchants = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			allMerchants = RmsConfigServices.getAllMerchants();
			
			
			
			secondCon.insertIntoSecondSchema(username, "Get-all-merchants", name, allMerchants.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get-all-merchants-Exception", name, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return allMerchants;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetRmsFiled")
	public ResponseEntity<?> GetRmsFeilds(@RequestBody String type) throws Exception {
		
		
		
		 SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			  
		

		
		JSONObject js = new JSONObject(type);
		logger.info("Mid Values:::::::::::::::::::: "+js.getString("Mid"));
//type =1 for global field value
//type =3 for common filed
// get		
		String Result = RmsConfigServices.GetRmsFileds(js.getString("type"),js.getString("Mid"));
		 JSONArray jsonArray = new JSONArray(Result);
			JSONObject js4 = new JSONObject();

		 js4.put("Fields", jsonArray);

		if(js.getString("type").equalsIgnoreCase("3"))
		{
		String type1="2";
		 
		 String get1= Result.replace("[","");
			String get2= get1.replace("]", "");
			JSONObject js2 = new JSONObject();
		 
			/*
			 * String GetBeneficer =
			 * RmsConfigServices.GetRmsFileds(type1,js.getString("Mid"));
			 * 
			 * JSONArray jsonArray1 = new JSONArray(GetBeneficer);
			 * js4.put("Beneficernce",jsonArray1);
			 */
		
			/*
			 * String get1= GetBeneficer.replace("[",""); String get2= get1.replace("]",
			 * ""); JSONObject js2 = new JSONObject(get2);
			 */
		/*
		 * int js3 = js2.getInt("id"); if(js3==5) { return "hello"; }
		 */
		}
		
		
		secondCon.insertIntoSecondSchema(username, "Get Rms Filed", type,(js4.toMap().toString()),ipAddress);
		
		return ResponseEntity.ok(js4.toMap());
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/Create_BlockPin")
	public ResponseEntity<?> InserBlocPin(@RequestBody String GlobalValue) throws Exception {
		/*
		 * JSONObject js2 = new JSONObject(GlobalValue); String pincodes=
		 * js2.getString("Pincodes"); String CountryCode= js2.getString("Country_code");
		 * String Ip_BlackList= js2.getString("IP_Blacklist");
		 */

		String type="4";
				String Result = RmsConfigServices.GetRmsFileds(type,GlobalValue);
		 JSONArray jsonArray = new JSONArray(Result);
		 String get1= Result.replace("[","");
			String get2= get1.replace("]", "");
			JSONObject js12 = new JSONObject(get2);
			
			
			
			
			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();
						
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = null;
			String ipAddress = null;

			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");




			secondCon.insertIntoSecondSchema(username, "Create_BlockPin", GlobalValue, (js12.toMap().toString()), ipAddress);
			
			
			
			
			
			
			
		return ResponseEntity.ok(js12.toMap());
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@GetMapping("/GetBlockPin")
	public ResponseEntity<?> GetBlockPin() throws Exception {		
		String type="0";
				String Result = RmsConfigServices.GetRmsFileds(type,"val");
		 JSONArray jsonArray = new JSONArray(Result);
		 String get1= Result.replace("[","");
			String get2= get1.replace("]", "");
			JSONObject js2 = new JSONObject(get2);
			
			
			
			
			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();
						
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = null;
			String ipAddress = null;

			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");


			secondCon.insertIntoSecondSchema(username, "Get Block Pin","GetBlockPin", (js2.toMap().toString()), ipAddress);
			
		return ResponseEntity.ok(js2.toMap());
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetRiskAlertDetails")
	public ResponseEntity<?> GetRiskAlertDetails(@RequestBody String request) throws Exception {
//type =1 for global field value
//type =3 for common filed
// get		
		JSONObject Data = new JSONObject(request);
        String from=Data.getString("From");
        String ToDate= Data.getString("ToDate");
        String Merchant_Id = Data.getString("Mid");
        String RiskCode = Data.getString("RiskCode");
        String RiskStage = Data.getString("RiskStage");
        String RiskFlag = Data.getString("RiskFlag");
        int pageRecords = Data.getInt("pageRecords");
        int pageNumber = Data.getInt("pageNumber");
		String type="0";
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");
		
		
		
		
		
		
		List<String> Result = RmsConfigServices.GetRiskAlert(from,ToDate, Merchant_Id, RiskCode, RiskStage, RiskFlag,pageRecords, pageNumber);
		List<String> Resulttotl = RmsConfigServices.GetRiskAlerttotal(from,ToDate, Merchant_Id, RiskCode, RiskStage, RiskFlag,pageRecords, pageNumber);
				/*
				 *; String get1=
				 * Result.replace("[",""); String get2= get1.replace("]", ""); JSONObject js2 =
				 * new JSONObject(get2);
				 */
				JSONArray jsonArray =null;
				JSONArray jsonArray1 =null;
				jsonArray1= new JSONArray(Resulttotl);
				JSONObject rec = jsonArray1.getJSONObject(0);
				Long noRecords = rec.getLong("TotalRecords");
				JSONObject resp= new JSONObject();
				if(Result!=null) {
					 jsonArray= new JSONArray(Result);
					 
					 resp.put("numberOfRecords", pageRecords);
					 resp.put("pageNumber", pageNumber);
					 resp.put("Details", jsonArray);
					 resp.put("TotalRecords", noRecords);

				}else {
					  resp.put("status", false);
					  resp.put("message", "No data found");

				}
				
				
				
				secondCon.insertIntoSecondSchema(username, "Get Risk Alert Details", request, resp.toString(), ipAddress);
				
		return ResponseEntity.ok(resp.toString());
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetRiskActionLogs")
	public ResponseEntity<?> GetRiskActionLogs(@RequestBody String request) throws Exception {
//type =1 for global field value
//type =3 for common filed
// get		
		JSONObject Data = new JSONObject(request);
        String from=Data.getString("From");
        String ToDate= Data.getString("ToDate");
        String Merchant_Id = Data.getString("Mid");
        String RiskCode = Data.getString("RiskCode");
        String Action = Data.getString("Action");
        int pageRecords = Data.getInt("pageRecords");
        int pageNumber = Data.getInt("pageNumber");
        
        
        
        
        SecondCon secondCon = new SecondCon();
        UserDetailsui userDetailsui = new UserDetailsui();
        			
        String data = userDetailsui.getUserDetails();
        JSONObject userDetails = new JSONObject(data);

        String username = null;
        String ipAddress = null;

        username = userDetails.optString("Username");
        ipAddress = userDetails.optString("ipAddress");
        
        
        
        
        
        
		String type="0";
		List<String> Result = RmsConfigServices.RiskActionlogs(from,ToDate, Merchant_Id, RiskCode, Action,pageRecords, pageNumber);
		List<String> Resultactiontotal = RmsConfigServices.RiskActionlogsTotal(from,ToDate, Merchant_Id, RiskCode, Action,pageRecords, pageNumber);
			JSONArray jsonArray =null;
			JSONArray jsonArray1 =null;
			jsonArray1= new JSONArray(Resultactiontotal);
			JSONObject rec = jsonArray1.getJSONObject(0);
			Long noRecords = rec.getLong("TotalRecords");
			JSONObject resp= new JSONObject();
			if(Result!=null) {
				 jsonArray= new JSONArray(Result);
				 
				 resp.put("numberOfRecords", pageRecords);
				 resp.put("pageNumber", pageNumber);
				 resp.put("Details", jsonArray);
				 resp.put("TotalRecords", noRecords);
	
			}else {
				  resp.put("status", false);
				  resp.put("message", "No data found");
	
			}	
			
			
			secondCon.insertIntoSecondSchema(username, "Get Risk Action Logs", request, resp.toString(), ipAddress);
			
		return ResponseEntity.ok(resp.toString());
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/AddRmsConfig")
	public String AddConfigData(@RequestBody String fields, @RequestHeader String Authorization) throws JSONException, Exception {
		
		 SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			  
		

		
		String value=null;
		String JwtToken=null;
		int i=0;
		JSONObject js = new JSONObject(fields);
		String Mid=js.getString("Mid");
		String Mcc_code = js.getString("Mcc");
		String RiskType = js.getString("RiskType");
		JwtToken = Authorization.substring(7);
		
		String AddedBy=JwtHelperUtil.extractUsername(JwtToken);
		js.remove("Mid");
		js.remove("Mcc");
		js.remove("RiskType");
		JSONObject js1 = new JSONObject();

		
		System.out.println("Print AddedBy value "+AddedBy);
		i= RmsConfigServices.InserRmsValue(js.toString(),Mid,Mcc_code,RiskType,AddedBy);
		
		System.out.println("Print I value "+i);
		if(i==1)
		{
			//String  vb= ''{"value": "Data Inserted Succssefully"}";
			js1.put("Msg","Data Inserted Succssefully");
			js1.put("Status","1");
			logger.info("Rms Data Inserted Succssefully ");
		}else
		{
			value="Failed insert";
			js1.put("Msg","Data Not Succssefully");
			js1.put("Status","0");
			logger.info("Failed  Data Inserted ");
		}
		
		
		secondCon.insertIntoSecondSchema(username, "Add Rms Config", fields, js1.toString(), ipAddress);
		
		return js1.toString();
	}
	
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/GetConfigData")
	public Object GetRmsConfidData(@RequestBody String name) {
		JSONObject js = new JSONObject(name);
		String mid = js.getString("name");
		Gson gson = new Gson();
		  String jsonArray =null;
		String allMerchants = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			allMerchants = RmsConfigServices.GetRMSDataByMerchant(mid);
			
			
			secondCon.insertIntoSecondSchema(username, "Get Config Data", name, allMerchants.toString(), ipAddress);

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Config Data-Exception", name, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return allMerchants.replace("\\", "");
	}
	
	
	public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
	    JSONArray jsonArray = new JSONArray(jsonArrayStr);
	    return IntStream.range(0, jsonArray.length())
	      .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
	      .collect(Collectors.toList());
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/getRiskTransaction")
	public Object getRmsData(@RequestBody String Filter) {
		JSONObject js = new JSONObject(Filter);
		String Mid= js.getString("Mid");
		String From = js.getString("From");
		String ToDate= js.getString("ToDate");
		String RiskCode = js.getString("RiskCode");
		String RiskFlag = js.getString("RiskFlag");
		String RiskStage = js.getString("RiskStage");
	//	TransactionMaster Tm = RmsMisService.getGroceryItemByName(mid);
		System.out.print("Value 1 st step:::::::::::::::::::");
	//	TransactionMaster Tm = RmsMisService.getGroceryItemByName(mid);
		List<TransactionMaster> Tm = RmsMisService.findStudentsByProperties(From, ToDate, Mid, RiskCode, RiskStage, RiskFlag);
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get Risk Transaction", Filter, Tm.toString(), ipAddress);
		
		return Tm;
		
		
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/getRiskMid")
public ResponseEntity<?> riskMid(String mid) {
		
		
		logger.info("Enter api"+mid);

		
		
		List<Map<String, Object>> responseMsg = null;
		logger.info("Enter api 1");

		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		logger.info("userDetails api"+userDetails);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			logger.info("Enter api service");

			responseMsg = RmsMisService.showAllGroceryItems1();
			logger.info("Enter responseMsg"+responseMsg);
			
			secondCon.insertIntoSecondSchema(username, "Get Risk Mid", mid, responseMsg.toString(), ipAddress);
			logger.info("Enter done1");
		}catch(Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Risk Mid-Exception", mid, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
			logger.info("exception 2");
		} 

		return ResponseEntity.ok(responseMsg);
	}
	
	@CrossOrigin(origins = {"http://localhost:4200",})
	@PostMapping("/getRiskLevelData")
	public Object getRiskLevelData(@RequestBody String Merchant_id)
	{
		JSONObject js = new JSONObject(Merchant_id);
		Map<String, Object> RiskDataLevel = RmsMisService.getRiskLevelData(js.getString("MerchantId"));
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get Risk Level Data", Merchant_id, RiskDataLevel.toString(), ipAddress);
		
		
		
		
		
		return RiskDataLevel;
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200",})
	@PostMapping("/getRiskCountData")
	public Object getRiskCount(@RequestBody String Fields)
	{
		JSONObject js = new JSONObject(Fields);
		Map<String, Object> RiskDataLevel = RmsMisService.getRiskCountData(js.getString("MerchantId"),js.getString("level"));
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		secondCon.insertIntoSecondSchema(username, "Get Risk Count Data", Fields, RiskDataLevel.toString(), ipAddress);
		
		
		
		return RiskDataLevel;
	}
	
	@CrossOrigin(origins = {"http://localhost:4200",})
	@PostMapping("/GetRiskTransaction")
	public Object getRiskCountData(@RequestBody String Fields)
	{

		logger.info("GetRiskTransaction::::::::::::::::::::::::::::::::::::::::");
		Map<String, Object> responseMsg = null;
		JSONObject js = new JSONObject(Fields);
		logger.info("::::::::::::::::::::::::::::::::::::::::::::");
		
		logger.info("::::::::::::::::::::::::::::::::::::::::::::");
		String MerchantId=js.getString("MerchantId");
		String level = js.getString("level");
		String Code = js.getString("RiskCode");
		logger.info("::::::::::::::::::::::::::::::::::::::::::::");
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			responseMsg = RmsMisService.GetRiskTransaction(level,MerchantId,Code);
			
			secondCon.insertIntoSecondSchema(username, "Get Risk Transaction", Fields, responseMsg.toString(), ipAddress);
		}catch(Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Risk Transaction-Exception", Fields, stackTraceString, ipAddress);
			
		}
		return ResponseEntity.ok(responseMsg);
	}
}

