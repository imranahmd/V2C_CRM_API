package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.services.BankStatementService;
import com.crm.services.UserDetailsui;
import com.crm.util.S2SCall;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class Header {
	@JsonProperty("TranID")
	private String tranID;
	@JsonProperty("Corp_ID")
	private String corpID;

	@JsonProperty("Approver_ID")
	private String approverID;

	public String getTranID() {
		return tranID;
	}

	public void setTranID(String tranID) {
		this.tranID = tranID;
	}

	public String getCorpID() {
		return corpID;
	}

	public void setCorpID(String corpID) {
		this.corpID = corpID;
	}

	public String getApproverID() {
		return approverID;
	}

	public void setApproverID(String approverID) {
		this.approverID = approverID;
	}

}

class Body {
	@JsonProperty("Acc_No")
	private String accNo;
	@JsonProperty("Tran_Type")
	private String tranType;
	@JsonProperty("From_Dt")
	private String fromDt;
	@JsonProperty("Pagination_Details")
	private Pagination_Details pagination_Details;
	@JsonProperty("To_Dt")
	private String toDt;

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getFromDt() {
		return fromDt;
	}

	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	public Pagination_Details getPagination_Details() {
		return pagination_Details;
	}

	public void setPagination_Details(Pagination_Details pagination_Details) {
		this.pagination_Details = pagination_Details;
	}

	public String getToDt() {
		return toDt;
	}

	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

}

class Pagination_Details {
	@JsonProperty("Last_Balance")
	private Last_Balance last_Balance;
	@JsonProperty("Last_Pstd_Date")
	private String last_Pstd_Date;
	@JsonProperty("Last_Txn_Date")
	private String last_Txn_Date;
	@JsonProperty("Last_Txn_Id")
	private String last_Txn_Id;
	@JsonProperty("Last_Txn_SrlNo")
	private String last_Txn_SrlNo;

	public Last_Balance getLast_Balance() {
		return last_Balance;
	}

	public void setLast_Balance(Last_Balance last_Balance) {
		this.last_Balance = last_Balance;
	}

	public String getLast_Pstd_Date() {
		return last_Pstd_Date;
	}

	public void setLast_Pstd_Date(String last_Pstd_Date) {
		this.last_Pstd_Date = last_Pstd_Date;
	}

	public String getLast_Txn_Date() {
		return last_Txn_Date;
	}

	public void setLast_Txn_Date(String last_Txn_Date) {
		this.last_Txn_Date = last_Txn_Date;
	}

	public String getLast_Txn_Id() {
		return last_Txn_Id;
	}

	public void setLast_Txn_Id(String last_Txn_Id) {
		this.last_Txn_Id = last_Txn_Id;
	}

	public String getLast_Txn_SrlNo() {
		return last_Txn_SrlNo;
	}

	public void setLast_Txn_SrlNo(String last_Txn_SrlNo) {
		this.last_Txn_SrlNo = last_Txn_SrlNo;
	}

}

class Last_Balance {
	@JsonProperty("Amount_Value")
	private String amount_Value;
	@JsonProperty("Currency_Code")
	private String currency_Code;

	public String getAmount_Value() {
		return amount_Value;
	}

	public void setAmount_Value(String amount_Value) {
		this.amount_Value = amount_Value;
	}

	public String getCurrency_Code() {
		return currency_Code;
	}

	public void setCurrency_Code(String currency_Code) {
		this.currency_Code = currency_Code;
	}

}

class Signature {
	@JsonProperty("Signature")
	private String signature;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}

class Request {
	@JsonProperty("Acc_Stmt_DtRng_Req")
	private AccStmtDtRngReq accStmtDtRngReq;

	public Request(AccStmtDtRngReq accStmtDtRngReq) {
		this.accStmtDtRngReq = accStmtDtRngReq;
	}

	public AccStmtDtRngReq getAccStmtDtRngReq() {
		return accStmtDtRngReq;
	}

	public void setAccStmtDtRngReq(AccStmtDtRngReq accStmtDtRngReq) {
		this.accStmtDtRngReq = accStmtDtRngReq;
	}

}

class AccStmtDtRngReq {
	@JsonProperty("Header")
	private Header header;
	@JsonProperty("Body")
	private Body body;
	@JsonProperty("Signature")
	private Signature signature;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

}

@RestController
public class BankStatementController {
	private static Logger log = LoggerFactory.getLogger(BankStatementController.class);

	@Value("${BankStatement.RBL_SALE_URL}")
	private String RBL_SALE_URL;

	@Value("${BankStatement.Key}")
	private String Key;

	@Value("${BankStatement.Path}")
	private String Path;

	@Value("${BankStatement.clientId}")
	private String clientId;

	@Value("${BankStatement.clientSecret}")
	private String clientSecret;

	@Autowired
	private BankStatementService bankStatementService;

//	@CrossOrigin(origins = { "http://localhost:4200" })
//	@PostMapping(value = "getBankAccountStatement", produces = "application/json")
//	public String getProcessingList(@RequestBody String json,@RequestHeader String Authorization) {
//		// Create an instance of AccData and set its attributes
//		String response = null;
//		String ResponseValue=null;
//		
//		
//		
//		SecondCon secondCon = new SecondCon();
//		UserDetailsui userDetailsui = new UserDetailsui();
//					
//		String data = userDetailsui.getUserDetails();
//		JSONObject userDetails = new JSONObject(data);
//
//		String username = null;
//		String ipAddress = null;
//		
//		try {
//			
//			username = userDetails.optString("Username");
//			ipAddress = userDetails.optString("ipAddress");
//			
//			String auth = Authorization.substring(7);
//			
//log.info("auth value:::::::::::::::::::::::::::::"+auth);
//
//			// generate txn_id random
//
//			// create a string of all characters
//			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
//
//			// create random string builder
//			StringBuilder sb = new StringBuilder();
//
//			// create an object of Random class
//			Random random = new Random();
//
//			// specify length of random string
//			int length = 7;
//
//			for (int i = 0; i < length; i++) {
//
//				// generate random index number
//				int index = random.nextInt(alphabet.length());
//
//				// get character specified by index
//				// from the string
//				char randomChar = alphabet.charAt(index);
//
//				// append the character to string builder
//				sb.append(randomChar);
//			}
//
//			String TranID = sb.toString();
//			log.info("TranID random generated :: " + TranID);
//
//
//			// set the value
//
//			Header header = new Header();
//			
//			Body body = new Body();
//
//
//			Pagination_Details pagination_Details = new Pagination_Details();
//
//
//			body.setPagination_Details(pagination_Details);
//
//			Signature signature = new Signature();
//
//			AccStmtDtRngReq accStmtDtRngReq = new AccStmtDtRngReq();
//			accStmtDtRngReq.setHeader(header);
//			accStmtDtRngReq.setBody(body);
//			accStmtDtRngReq.setSignature(signature);
//
//			log.info("accStmtDtRngReq::::::::" + accStmtDtRngReq);
//			Request request = new Request(accStmtDtRngReq);
//			log.info("request::::::::" + request);
//			ObjectMapper objectMapper1 = new ObjectMapper();
//			String json1 = objectMapper1.writeValueAsString(request);
//
//			
//			JSONObject js = new JSONObject(json);
//			String From_Dt =	js.getString("From_Dt");
//			String To_Dt =	js.getString("To_Dt");
//			String Tran_Type =	js.getString("Tran_Type");
//			
//			log.info("TranID:::::::::::::::::::::"+TranID);
//			log.info("From_Dt::::::::::::::::::::"+From_Dt);
//			log.info("To_Dt::::::::::::::::::::::"+To_Dt);
//			log.info("Tran_Type:::::::::::::::::::"+Tran_Type);
//			
//		String jsonStr = convertJsonToString(TranID,From_Dt,To_Dt,Tran_Type);
//		
//		log.info("jsonStr:::::::::::::::::::::::::::::::::"+jsonStr);
//			
//			
//			
//			log.info("RBL_SALE_URL::" + RBL_SALE_URL + "::Key::" + Key + "::Path::" + Path + "::clientId::" + clientId
//					+ "::clientSecret::" + clientSecret);
//			// S2S call for BankStatement
//		//	https://epaisaa.com/Api/getBankAccountStatement
////			String url="https://epaisaa.com/Api/getBankAccountStatement";
//		
////			response = S2SCall.ServerToServerCallProcess(url, jsonStr,auth);
//			 response = S2SCall.restapcall(jsonStr, RBL_SALE_URL, Key, Path,clientId,clientSecret);
//			 log.info("response::::::::::::::::::::::::::::::::::::::::::::::::::::::::" + response);
//				if (response.equalsIgnoreCase("") || response.equalsIgnoreCase("null")) {
//					JSONObject respons = new JSONObject();
//					respons.put("error", "No Data Found");
//					ResponseValue = respons.toString();
//
//				}
//
//				else {
//					ObjectMapper objectMapperres = new ObjectMapper();
//					JsonNode jsonNoderes = objectMapperres.readTree(response);
//					if (jsonNoderes.get("Acc_Stmt_DtRng_Res").get("Header").get("Status").asText()
//							.equalsIgnoreCase("SUCCESS")) {
//						ResponseValue = response;
//						
//						String bankStatementData = bankStatementService.saveBankStatement(TranID,From_Dt,To_Dt,To_Dt, jsonStr, response);
//						log.info("bankStatementData:::::" + bankStatementData);
//					} else {
//						JSONObject respons = new JSONObject();
//						respons.put("error", "No Data Found");
//						ResponseValue = respons.toString();
//					}
//				}
//			// log.info("response::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+response);
//		
//			log.info("BankStatementController :: response :: " + response);
//
//			log.info("TranID::call for save::::::::;"+TranID);
//			log.info("From_Dt::::::::::;"+From_Dt);
//			log.info("To_Dt::::::::::;"+To_Dt);
//			log.info("To_Dt::::::::::;"+To_Dt);
//			log.info("json1::::::::::;"+json1);
//			log.info("response::::::::::;"+response);
//			String bankStatementData = bankStatementService.saveBankStatement(TranID,From_Dt,To_Dt,To_Dt, jsonStr, response);
//			log.info("bankStatementData:::::" + bankStatementData);
//			
//			secondCon.insertIntoSecondSchema(username, "Get Bank Account Statement", json, response, ipAddress);
//
//		} catch (Exception e) {
//			
//			StringWriter sw = new StringWriter();
//			PrintWriter pw = new PrintWriter(sw);
//			e.printStackTrace(pw);
//			String stackTraceString = sw.toString();
//									
//			secondCon.insertIntoSecondSchema(username, "Get Bank Account Statement-Exception", json, stackTraceString, ipAddress);
//			e.printStackTrace();
//		}
//		return response;
//	}

	
	public static String convertJsonToString(String tranID, String From_Dt,String To_Dt,String Tran_Type ) throws JsonProcessingException {
		//log.info("json::::::::::::::::::::::"+json);
		log.info("tranID:::::::::::::::::::::::"+tranID);
		log.info("From_Dt::::::::::::::::::::::"+From_Dt);
		log.info("To_Dt:::::::::::::::::::::::::"+To_Dt);
		log.info("Tran_Type:::::::::::::::::::::"+Tran_Type);
		
		
		String hs="{\r\n"
				+ "  \"Acc_Stmt_DtRng_Req\": {\r\n"
				+ "    \"Header\": {\r\n"
				+ "      \"TranID\": \""+tranID+"\",\r\n"
				+ "      \"Corp_ID\": \"REWARDOOPR\",\r\n"
				+ "      \"Approver_ID\": \"A001\"\r\n"
				+ "    },\r\n"
				+ "    \"Body\": {\r\n"
				+ "      \"Acc_No\": \"409888835010\",\r\n"
				+ "      \"Tran_Type\": \""+Tran_Type+"\",\r\n"
				+ "      \"From_Dt\": \""+From_Dt+"\",\r\n"
				+ "      \"Pagination_Details\": {\r\n"
				+ "        \"Last_Balance\": {\r\n"
				+ "          \"Amount_Value\": \"\",\r\n"
				+ "          \"Currency_Code\": \"\"\r\n"
				+ "        },\r\n"
				+ "        \"Last_Pstd_Date\": \"\",\r\n"
				+ "        \"Last_Txn_Date\": \"\",\r\n"
				+ "        \"Last_Txn_Id\": \"\",\r\n"
				+ "        \"Last_Txn_SrlNo\": \"\"\r\n"
				+ "      },\r\n"
				+ "      \"To_Dt\": \""+To_Dt+"\"\r\n"
				+ "    },\r\n"
				+ "    \"Signature\": {\r\n"
				+ "      \"Signature\": \"Signature\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}";
		
		
		log.info("hs::::::::::::::::::::::::::;;"+hs);
		return hs;
      
    }

	
	@PostMapping(value = "getBankStatementData", produces = "application/json")
	public String getbyList(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);

	//	String txn_id = js.getString("txn_id");
		String To_Dt = js.getString("To_Dt");
		String From_Dt = js.getString("From_Dt");
		String Tran_Type = js.getString("Tran_Type");
		
	//	log.info("txn_id====================================" + txn_id);

		String bankStatementData = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			//bankStatementData = bankStatementService.getbankStatementData(txn_id);

			bankStatementData = bankStatementService.getbankStatementData(From_Dt,To_Dt,Tran_Type);
			
			if (bankStatementData.equalsIgnoreCase("fail")) {

				JSONObject response = new JSONObject();
				response.append("Error", "No Data Found!");
				bankStatementData = response.toString();
			}
			secondCon.insertIntoSecondSchema(username, "Get Bank Statement Data", jsonBody, bankStatementData, ipAddress);

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Bank Statement Data-Exception", jsonBody, stackTraceString, ipAddress);
			e.printStackTrace();
		}

		return bankStatementData;

	}
}
