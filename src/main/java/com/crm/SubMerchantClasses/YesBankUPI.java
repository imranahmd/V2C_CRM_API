package com.crm.SubMerchantClasses;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.web.bind.annotation.RestController;

import com.crm.Repository.MerchantKycDocRepo;
import com.crm.Repository.MerchantRepository;
import com.crm.dto.MerchantBasicSetupDto;
import com.crm.dto.MerchantCreationDto;
import com.crm.services.MerchantService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@RestController
public class YesBankUPI {
	
	@Autowired
	private MerchantRepository merchantRepo;

	 @Autowired
	 private JdbcTemplate JdbcTemplate;
	 
	 @Autowired
		private MerchantService merchantService;
	 
	 @Value("${yesbank.suMerchantURL}")
		private String yesBankWrapperURL;
	
	private static final Logger log = LoggerFactory.getLogger(YesBankUPI.class);
//	private static String yesBankWrapperURL = "https://domain/pay/YesSubMerchant";
	public String getSubMerchantDetailsInsert(String MerchantId, String sp_id, String instrument, String merVirtualAdd, String App_Status) throws Exception {
//		String statusDataVal = null;
		
		
		StringBuilder statusDataVal = new StringBuilder();
		try{
		
			 
			String Mcc = merchantRepo.findMCCForYesank(MerchantId);	
			log.info("Mcc::::::"+Mcc);
			
		log.info("MerchantId:::"+MerchantId+"::sp_id::"+sp_id+"::instrument::"+instrument);
		
			
		String Response=merchantService.getMerchntDataForYesB(MerchantId);	
		log.info("Response::::::::::::"+Response);
	
		JSONArray arrayjson = new JSONArray(Response);
//		log.info("arrayjson::::::::::::"+arrayjson);
		JSONObject obj = arrayjson.getJSONObject(0);
		log.info("object:::::"+obj);
		
			
			String stateVal= obj.get("state").toString();
			log.info("stateVal:::::::"+stateVal);
			String stateValD = merchantRepo.findstateValD(stateVal);	
			
			log.info("stateValD:::"+stateValD);
			
			//get value for businessType 
			String businessType = obj.get("BusinessType").toString();
			log.info("businessType::::::::"+businessType);
			
			String businessTypeVal = merchantRepo.findbusinessTypeVal(businessType);
			
			log.info("businessTypeVal::::"+businessTypeVal);
			//end businessType
			
			if ((stateValD == null && businessTypeVal == null && Mcc == null)
					|| (stateValD != null && businessTypeVal == null && Mcc == null)
					|| (stateValD == null && businessTypeVal != null && Mcc == null)
					|| (stateValD == null && businessTypeVal == null && Mcc != null)
					|| (stateValD == null && businessTypeVal != null && Mcc != null)
					|| (stateValD != null && businessTypeVal == null && Mcc != null)
					|| (stateValD != null && businessTypeVal != null && Mcc == null)) {				
			
				log.info("inside the if condition:::::::::::");
				
				statusDataVal.append("Please Verify The ");
				
				if(stateValD == null || stateValD == ""){				
					
					log.info("inside the stateValD condition:::::::::::");
					statusDataVal.append("State Name, ");
					log.info("statusDataVal:::"+statusDataVal);
				}
				
				if(businessTypeVal == null || businessTypeVal == ""){
					log.info("inside the businessTypeVal condition:::::::::::");
					statusDataVal.append(" business Type,");
					log.info("businessTypeVal:::"+businessTypeVal);
				}
				if (Mcc == null || Mcc =="") {
					log.info("inside the Mcc condition:::::::::::");
					statusDataVal.append(" Mcc");
					log.info("statusDataVal:::"+statusDataVal);
				}
			}
			
			
			else {
				log.info("inside the else condition:::::::::::");
//				statusDataVal = "2";
				//generate the random number for requestId
				
				
				StringBuilder sb = new StringBuilder();
				String alphabet = "0123456789";
				
				Random random = new Random();
				int length = 5;
				for(int i = 0; i < length; i++) {
					int index = random.nextInt(alphabet.length());
					char randomChar = alphabet.charAt(index);
					sb.append(randomChar);
				}

				String randomString = sb.toString();
				log.info("randomString:::"+randomString);
				//end the random number for requestId
				
				
				
				JSONObject requestjson = new JSONObject();
				requestjson.put("pgMerchantId", "YES0000010716862");
				requestjson.put("action", "C");
				requestjson.put("mebussname", obj.get("business_name").toString());
				requestjson.put("subMerchantId", "PAY001");
				requestjson.put("merVirtualAdd", merVirtualAdd);
				requestjson.put("awlmcc", Mcc);
				requestjson.put("requestUrl1", "https://domain/pay/ResHandlerYESUPI");
				requestjson.put("requestUrl2", "https://domain/pay/ResHandlerYESUPI");
				requestjson.put("integrationType", "WEBAPI");
				requestjson.put("panNo", obj.get("CompanyPAN").toString());
				requestjson.put("cntEmail", obj.get("email_id").toString());
				requestjson.put("strEmailId", obj.get("email_id").toString());
				requestjson.put("gstn", obj.get("GSTINNo").toString());
				requestjson.put("meBussntype", businessTypeVal);
				requestjson.put("pdayTxnCount", "99999");
				requestjson.put("pdayTxnLmt", "1000000"); 
				requestjson.put("pdayTxnAmt", "99999");
				requestjson.put("strCntMobile", obj.get("contact_number").toString());
				requestjson.put("extMID", "MSASA113");
				requestjson.put("extTID", "D32SDS");
				requestjson.put("add", obj.get("address").toString());
				requestjson.put("city", obj.get("city").toString());
				requestjson.put("state", stateValD);
				requestjson.put("requestId", randomString);
				requestjson.put("addinfo1", obj.get("pincode").toString());
				requestjson.put("addinfo2", "addinfo2");
				requestjson.put("addinfo3", "addinfo3");
				requestjson.put("addinfo4", "addinfo4");
				requestjson.put("addinfo5", "addinfo5");
				requestjson.put("addinfo6", "addinfo6");
				requestjson.put("addinfo7", "addinfo7");
				requestjson.put("addinfo8", "addinfo8");
				requestjson.put("addinfo9", "addinfo9");
				requestjson.put("addinfo10", "addinfo10");
				requestjson.put("sms", "N");
				requestjson.put("email", "N");
				requestjson.put("merchantGenre", "ONLINE");
				
				 log.info("requestjson::::::::::"+requestjson.toString().replaceAll("\"", "\'").replaceAll("null",""));
				 log.info("yesBankWrapperURL:::::::"+yesBankWrapperURL);
				 
				 String requestVal = requestjson.toString().replaceAll("\"", "\'").replaceAll("null","");
				 log.info("requestVal:::::"+requestVal);
//				 String sData = S2SCall.secureServerCall1(yesBankWrapperURL,requestVal);
//				 log.info("sData::::::::::::: " + sData.toString());
//				 String sData = "{\r\n"
//				 		+ "  \"mebussname\": \"RSTRL CORPORATE SOLUTION PRIVATE LIMITED\",\r\n"
//				 		+ "  \"pgMerchantId\": \"YES0000010716862\",\r\n"
//				 		+ "  \"action\": \"C\",\r\n"
//				 		+ "  \"statusDesc\": \"SUCCESS\",\r\n"
//				 		+ "  \"status\": \"SUCCESS\",\r\n"
//				 		+ "  \"merVirtualAdd\": \"RSTRLCORP@yesbank\",\r\n"
//				 		+ "  \"crtDate\": \"29-11-22\",\r\n"
//				 		+ "  \"requestId\": \"17995\",\r\n"
//				 		+ "  \"subMerchantId\": \"YES0000010754565\",\r\n"
//				 		+ "  \"loginaccess\": \"Y\"\r\n"
//				 		+ "}";
//				 log.info("sData::::::::::::: " + sData.toString());
//				 
//				 
				 
				 String sData = """
						 {
						     "mebussname": "",
						     "pgMerchantId": "YES0000010716862",
						     "action": "C",
						     "statusDesc": "Request Id already exists",
						     "status": "FAILURE",
						     "merVirtualAdd": "",
						     "crtDate": "2022-11-17 15:46:18",
						     "requestId": "17995",
						     "subMerchantId": "",
						     "loginaccess": ""
						 }\
						 """;
					 log.info("sData::::::::::::: " + sData.toString());
				 JSONObject responsejson = new JSONObject(sData);
//				
				 if(responsejson != null) {
					 
					 if(responsejson.getString("status").equalsIgnoreCase("SUCCESS")) {
						 log.info("inside the if success condition");
						 statusDataVal.append("2");
						 String StatusValD = "SUCCESS";
						 String statusData = merchantRepo.saveDataYBSubMerchant(MerchantId,sp_id,instrument,StatusValD,requestjson.toString(), responsejson.toString());
						
					 }

					 else {
						 statusDataVal.append(responsejson.getString("statusDesc").toString());
						 log.info("inside the else failed condition");
 
						
					 }
				 
				 }
				
			}
			
		}
	
	
		catch(Exception e){
		log.info("Exception occured "+e);
		
		}
		log.info("statusDataVal::::"+statusDataVal);
		return statusDataVal.toString();
	}
}