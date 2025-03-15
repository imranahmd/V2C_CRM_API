package com.crm.restCall;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crm.dto.BankAccountKycDTO;
import com.crm.dto.CINKycDTO;
import com.crm.dto.GSTINKycDTO;
import com.crm.dto.KycDTO;
import com.crm.dto.PANKycDTO;

@Service
public class RestCall {

	@Value("${neutrino.api.user-id}")
	private String userId;
	
	@Value("${neutrino.api.api-key}")
	private String apiKey;
	
	@Value("${neutrino.api.ip-info}")
	private String neutrinoApiURL;
	
	@Value("${ContentType}")
	private String contentType;
	
	@Value("${decentro.api.clientId}")
	private String clientId;
	
	@Value("${decentro.api.clientSecret}")
	private String clientSecret;
	
	@Value("${decentro.api.moduleSecret}")
	private String moduleSecret;
	
	@Value("${decentro.api.providerSecret}")
	private String providerSecret;
	
	  @Value("${bankdetails.verify.url}")
      private String bankdetailsVerifyURL;
	  
	  @Value("${kyc.verify.url}")
      private String kycURL;
	  
	  @Value("${decentro.api.banking.moduleSecret}")
		private String bankingModuleSecret;
	  

      @Value("${ifsc.verify.url}")
      private String ifscVerifyURL;


      @Value("${ifsc.token}")
      private String ifscToken;
      
      @Value("${karza.api.key}")
      private String karzaKey;

      @Value("${karza.kyc.gstin}")
      private String karzaKycGstin;
      
      @Value("${karza.kyc.pan}")
      private String karzaKycPan;
      
      @Value("${karza.verify.bankaccount}")
      private String karzaVerifyAccount;
      
      @Value("${karza.verify.ifsc}")
      private String karzaVerifyIFSC;
     
	  
	  
public ResponseEntity<String> sendGetRequest(String req, String url) {
	
	HttpHeaders headers = new HttpHeaders();
	
	headers.set("user-id", userId);
	headers.set("api-key", apiKey);
	headers.setContentType(MediaType.APPLICATION_JSON);
	
	HttpEntity<String> entity = new HttpEntity<String>(headers);
	
	ResponseEntity<String> responseEntity= new RestTemplate().exchange(neutrinoApiURL+"?ip={ip}", HttpMethod.GET, entity, String.class,req);
	return responseEntity;

	}

public ResponseEntity<String> sendPostRequestBankAccout(BankAccountKycDTO dto, String source) {
	if(source.equalsIgnoreCase("KARZA")) {
		HttpHeaders headers = new HttpHeaders();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("x-karza-key", karzaKey);
	    ResponseEntity<String> responseEntity= null;
		
	    if(dto.getConsent()!=null && dto.getIfsc()!=null && dto.getAccountNumber()!=null) {
			 Map<String, String> reqest = new HashMap();
			    reqest.put ("consent", dto.getConsent());
			    reqest.put ("ifsc", dto.getIfsc());
			    reqest.put ("accountNumber", dto.getAccountNumber());

			    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
//				responseEntity= new RestTemplate().exchange(karzaVerifyAccount, HttpMethod.POST, entity, String.class);
				JSONObject json= new JSONObject();
			    json.put("status", false);
				json.put("message", "Please enter valid input");
			    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
					
		}else {
			JSONObject json= new JSONObject();
			json.put("status", false);
			json.put("message", "Please enter valid input");
		    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

		}
	   
	}else if(source.equalsIgnoreCase("DECENTRO")) {
	HttpHeaders headers = new HttpHeaders();
	
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("client_id", clientId);
    headers.set("client_secret", clientSecret);
    headers.set("module_secret", bankingModuleSecret);
    headers.set("provider_secret", providerSecret);
    if(dto.getReference_id()!=null && dto.getPurpose_message()!=null && dto.getTransfer_amount()!=null && dto.getBeneficiary_details()!=null) {

	Map<String, String> beneficiaryDetails= new HashMap<String, String>();
    beneficiaryDetails.put("name", dto.getBeneficiary_details().getName());
    beneficiaryDetails.put("mobile_number", dto.getBeneficiary_details().getMobile_number());
    beneficiaryDetails.put("email_address", dto.getBeneficiary_details().getEmail_address());
    beneficiaryDetails.put("account_number", dto.getBeneficiary_details().getAccount_number());
    beneficiaryDetails.put("ifsc", dto.getBeneficiary_details().getIfsc());


    Map<String, Object>  reqest = new HashMap<String, Object>();
    reqest.put("reference_id", dto.getReference_id());
    reqest.put("purpose_message", dto.getPurpose_message());
    reqest.put("transfer_amount", dto.getTransfer_amount());
    reqest.put("beneficiary_details", beneficiaryDetails);
	
    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
	
//	ResponseEntity<String> responseEntity= new RestTemplate().exchange(bankdetailsVerifyURL, HttpMethod.POST, entity, String.class);
//	return responseEntity;
    JSONObject json= new JSONObject();
	json.put("status", false);
	json.put("message", "Please enter valid input");
    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	
    }else {
		JSONObject json= new JSONObject();
		json.put("status", false);
		json.put("message", "Please enter valid input");
	    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

	}
	}
	return null;
	
}




public ResponseEntity<String> sendPostRequestKyc(KycDTO dto, String source) {
	if(source.equalsIgnoreCase("KARZA")) {
		HttpHeaders headers = new HttpHeaders();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("x-karza-key", karzaKey);
	    ResponseEntity<String> responseEntity= null;
	    
	    if(dto.getConsent()!=null && dto.getGstin()!=null) {
			 Map<String, String> reqest = new HashMap();
			    reqest.put ("consent", dto.getConsent());
			    reqest.put ("gstin", dto.getGstin());

			    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
//				responseEntity= new RestTemplate().exchange(karzaKycGstin, HttpMethod.POST, entity, String.class);
			    JSONObject json= new JSONObject();
				json.put("status", false);
				json.put("message", "Please enter valid input");
			    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

					
		}else if(dto.getConsent()!=null && dto.getPan()!=null) {
			 Map<String, String> reqest = new HashMap();
			    reqest.put ("consent", dto.getConsent());
			    reqest.put ("pan", dto.getPan());

			    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
//				responseEntity= new RestTemplate().exchange(karzaKycPan, HttpMethod.POST, entity, String.class);
			    JSONObject json= new JSONObject();
				json.put("status", false);
				json.put("message", "Please enter valid input");
			    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

					
		}else {
			JSONObject json= new JSONObject();
			json.put("status", false);
			json.put("message", "Please enter valid input");
		    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

		}
	   
	}else if(source.equalsIgnoreCase("DECENTRO")) {
		HttpHeaders headers = new HttpHeaders();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("client_id", clientId);
	    headers.set("client_secret", clientSecret);
	    headers.set("module_secret", moduleSecret);
		
	    if(dto.getReference_id()!=null && dto.getDocument_type()!=null && dto.getId_number()!=null && dto.getConsent()!=null && dto.getConsent_purpose()!=null) {
	    Map<String, String> reqest = new HashMap();
	    reqest.put("reference_id", dto.getReference_id());
	    reqest.put("document_type", dto.getDocument_type());
	    reqest.put("id_number", dto.getId_number());
	    reqest.put ("consent", dto.getConsent());
	    reqest.put ("consent_purpose", dto.getConsent_purpose());

	    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
		
//		ResponseEntity<String> responseEntity= new RestTemplate().exchange(kycURL, HttpMethod.POST, entity, String.class);
//		return responseEntity;
	    
	    JSONObject json= new JSONObject();
		json.put("status", false);
		json.put("message", "Please enter valid input");
	    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
	
	    }else {
			JSONObject json= new JSONObject();
			json.put("status", false);
			json.put("message", "Please enter valid input");
		    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

		}
	    
	}
	
	return null;	
	}




public ResponseEntity<String> sendPostRequestIFSC(BankAccountKycDTO dto, String source) {
	
	if(source.equalsIgnoreCase("KARZA")) {
		HttpHeaders headers = new HttpHeaders();
		
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("x-karza-key", karzaKey);
	    ResponseEntity<String> responseEntity= null;
		
	    if(dto.getConsent()!=null && dto.getIfsc()!=null) {
			 Map<String, String> reqest = new HashMap();
			    reqest.put ("consent", dto.getConsent());
			    reqest.put ("ifsc", dto.getIfsc());

			    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
//				responseEntity= new RestTemplate().exchange(karzaVerifyIFSC, HttpMethod.POST, entity, String.class);
			    JSONObject json= new JSONObject();
			    json.put("status", false);
				json.put("message", "Please enter valid input");
			    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
					
		}else {
			JSONObject json= new JSONObject();
			json.put("status", false);
			json.put("message", "Please enter valid input");
		    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);

		}
	   
//	return responseEntity;
	}else if(source.equalsIgnoreCase("DECENTRO")) {
    HttpEntity<Object> entity=new HttpEntity<Object>(null,null);

    if(dto.getIfsc()!=null) {

//    ResponseEntity<String> responseEntity = new RestTemplate().exchange(ifscVerifyURL+dto.getIfsc()+"?token="+ifscToken, HttpMethod.GET, entity, String.class);
//	return responseEntity;
    	
    	JSONObject json= new JSONObject();
		json.put("status", false);
		json.put("message", "Please enter valid input");
	 return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
    	
	}else {
		JSONObject json= new JSONObject();
		json.put("status", false);
		json.put("message", "Please enter valid input");
	    return new ResponseEntity<String>(json.toString(), HttpStatus.OK);	
	}
	}
	return null;

}
/*public ResponseEntity<String> sendPostRequestGSTIN(GSTINKycDTO dto) {

HttpHeaders headers = new HttpHeaders();

headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("client_id", clientId);
headers.set("client_secret", clientSecret);
headers.set("module_secret", moduleSecret);

Map<String, String> reqest = new HashMap();
reqest.put("reference_id", dto.getReference_id());
reqest.put("document_type", dto.getDocument_type());
reqest.put("id_number", dto.getId_number());
reqest.put ("consent", dto.getConsent());
reqest.put ("consent_purpose", dto.getConsent_purpose());



HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);

ResponseEntity<String> responseEntity= new RestTemplate().exchange(kycURL, HttpMethod.POST, entity, String.class);
return responseEntity;

}*/

/*public ResponseEntity<String> sendPostRequestPAN(PANKycDTO dto) {

	HttpHeaders headers = new HttpHeaders();
	
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("client_id", clientId);
    headers.set("client_secret", clientSecret);
    headers.set("module_secret", moduleSecret); 
    
    Map<String, String> reqest = new HashMap<String, String>();
    reqest.put("reference_id", dto.getReference_id());
    reqest.put("document_type", dto.getDocument_type());
    reqest.put("id_number", dto.getId_number());
    reqest.put ("consent", dto.getConsent());
    reqest.put ("consent_purpose", dto.getConsent_purpose());


	
    HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);
	
	ResponseEntity<String> responseEntity= new RestTemplate().exchange(kycURL, HttpMethod.POST, entity, String.class);
	
	System.out.println(responseEntity);
	return responseEntity;
} */

/*public ResponseEntity<String> sendPostRequestCIN(CINKycDTO dto) {

HttpHeaders headers = new HttpHeaders();

headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("client_id", clientId);
headers.set("client_secret", clientSecret);
headers.set("module_secret", moduleSecret);

Map<String, String> reqest = new HashMap<String, String>();
reqest.put("reference_id", dto.getReference_id());
reqest.put("document_type", dto.getDocument_type());
reqest.put("id_number", dto.getId_number());
reqest.put ("consent", dto.getConsent());
reqest.put ("consent_purpose", dto.getConsent_purpose());



HttpEntity<Object> entity=new HttpEntity<>(reqest,headers);

ResponseEntity<String> responseEntity= new RestTemplate().exchange(kycURL, HttpMethod.POST, entity, String.class);

System.out.println(responseEntity);
return responseEntity;

}*/
}
