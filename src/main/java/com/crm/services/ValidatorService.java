package com.crm.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.crm.Repository.BulkRefundSqlRepo;
import com.crm.SubMerchantClasses.S2SCall;
import com.crm.dto.payout_request.PaymentRequest;
import com.crm.util.GeneralUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


@Service
public class ValidatorService {
	

	@Autowired
	BulkRefundSqlRepo bulkRefundSqlRepo;

	public static void main(String[] args) {
		
		String key="fj0RH5hm2El7FV8yr6ns7UL7qr3Np3km";
		String Mid="M00005353";
		for(int i=0 ;i<1; i++) {
		UUID  ui=UUID.randomUUID();
			
			
	        String jsonInput = "{\n" +
	                "  \"type\": \"TEST\",\n" +
	                "  \"description\": \"TEST TRANSACTION\",\n" +
	                "  \"AuthID\": \"M00005353\",\n" +
	                "  \"paymentRequests\": [\n" +
	                "    {\n" +
	                "      \"amount\": \"40\",\n" +
	                "      \"ClientTxnId\": \""+UUID.randomUUID()+"\",\n" +
	                "      \"txnMode\": \"FT\",\n" +
	                "      \"account_number\": \"1256905\",\n" +
	                "      \"account_Ifsc\": \"HDFC0000398\",\n" +
	                "      \"bank_name\": \"HDFC Bank\",\n" +
	                "      \"account_holder_name\": \"ADESH TIWARI\",\n" +
	                "      \"beneficiary_name\": \"ADESH TIWARI\",\n" +
	                "      \"vpa\": \"NA\",\n" +
	                "      \"adf1\": \"9939858857\",\n" +
	                "      \"adf2\": \"test@mail.com\",\n" +
	                "      \"adf3\": \"NA\",\n" +
	                "      \"adf4\": \"NA\",\n" +
	                "      \"adf5\": \"NA\"\n" +
	                "    },{\n" +
	                "      \"amount\": \"80\",\n" +
	                "      \"ClientTxnId\": \""+UUID.randomUUID()+"\",\n" +
	                "      \"txnMode\": \"FT\",\n" +
	                "      \"account_number\": \"1256905\",\n" +
	                "      \"account_Ifsc\": \"HDFC0000398\",\n" +
	                "      \"bank_name\": \"HDFC Bank\",\n" +
	                "      \"account_holder_name\": \"ADESH TIWARI\",\n" +
	                "      \"beneficiary_name\": \"ADESH TIWARI\",\n" +
	                "      \"vpa\": \"NA\",\n" +
	                "      \"adf1\": \"9939858857\",\n" +
	                "      \"adf2\": \"test@mail.com\",\n" +
	                "      \"adf3\": \"NA\",\n" +
	                "      \"adf4\": \"NA\",\n" +
	                "      \"adf5\": \"NA\"\n" +
	                "    }\n" +
	                "  ]\n" +
	                "}";

	        
	        System.out.print(jsonInput);
			
			  String EncryptValue = GeneralUtil.encrypt(key, key.substring(0, 16),
			  jsonInput.toString()); JSONObject js = new JSONObject(); 
			  
			  js.put("AuthID", Mid); 
			  js.put("EncReq", EncryptValue);
			  
			  
		        System.out.print(js);
				
				  String url="https://uat.epaisaa.com/crmpre/PayoutBulkRaised"; String resp=
				  S2SCall.secureServerCall1(url, js.toString());
				  
				  System.out.print("response:::::::; Msg:::::::::; "+resp);
				 
		}
		
		
			/*
			 * Gson gson = new Gson(); TransactionRequest transactionRequest =
			 * gson.fromJson(jsonInput, TransactionRequest.class);
			 * 
			 * if (transactionRequest != null) { // Validate the transaction request
			 * parameters if (transactionRequest.getPaymentRequests() != null) { for
			 * (PaymentRequest paymentRequest : transactionRequest.getPaymentRequests()) {
			 * if (isValidPaymentRequest(paymentRequest)) {
			 * System.out.println("Payment request is valid: " + paymentRequest); } else {
			 * System.out.println("Invalid payment request: " + paymentRequest); } } } else
			 * { System.out.println("No payment requests found."); } } else {
			 * System.out.println("Invalid transaction request."); }
			 */
	    }

	    private static boolean isValidPaymentRequest(PaymentRequest paymentRequest) {
	        // Perform validation for each parameter in the payment request
	        // Add your validation logic here
	        // For example, check if required fields are present and have valid values
	        return paymentRequest.getAmount() != null &&
	                paymentRequest.getClientTxnId() != null &&
	                paymentRequest.getTxnMode() != null &&
	                paymentRequest.getAccount_number() != null &&
	                paymentRequest.getAccount_Ifsc() != null &&
	                paymentRequest.getBank_name() != null &&
	                paymentRequest.getAccount_holder_name() != null &&
	                paymentRequest.getBeneficiary_name() != null &&
	                paymentRequest.getVpa() != null &&
	                paymentRequest.getAdf1() != null &&
	                paymentRequest.getAdf2() != null;
	    }
	


public boolean ValidRequest(String jsonInput)
{
	boolean IsValid=false;
	  Gson gson = new Gson();
      TransactionRequest transactionRequest = gson.fromJson(jsonInput, TransactionRequest.class);
int i=0;
      if (transactionRequest != null) {
          // Validate the transaction request parameters
    	//  GetAmtValidation(jsonInput,"");
          if (transactionRequest.getPaymentRequests() != null) {
              for (PaymentRequest paymentRequest : transactionRequest.getPaymentRequests()) {
                  if (isValidPaymentRequest(paymentRequest)) {
                	
                      System.out.println("Payment request is valid: " + paymentRequest);
                  } else {
                	  i++;
                      System.out.println("Invalid payment request: " + paymentRequest);
                  }
              }
              
              if(i>0)
              {
            	  IsValid=false;
              }else {
            	  IsValid=true;
              }
          } else {
              System.out.println("No payment requests found.");
          }
      } else {
          System.out.println("Invalid transaction request.");
      }
      return IsValid;
}

public boolean GetAmtValidation(String json,String Mid)
{
	Gson gson = new Gson();
	JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
	JsonArray paymentRequests = jsonObject.getAsJsonArray("paymentRequests");
boolean IvalidMap=true;
/*
 * double minAmount = Double.MAX_VALUE; double maxAmount = Double.MIN_VALUE;
 */
	for (JsonElement element : paymentRequests) {
	    JsonObject paymentRequest = element.getAsJsonObject();
	    String TxnMode=paymentRequest.get("txnMode").getAsString();
	    double amount = paymentRequest.get("amount").getAsDouble();
  
	    System.out.print("TransactionMOde::::::::::::::: "+TxnMode);

	    System.out.print("Amount::::::::::::::::::::::::: "+amount);
	    
		String GetSpId =bulkRefundSqlRepo.GetSpId(Mid, TxnMode);

		if(GetSpId!=null)
		{
		    System.out.print(":::::::::::return true:::::::::::::: "+amount);

		}else {
			
		    System.out.print("Return False value beacause Somthine went wrong in Request amount::::::::::::::::::::: "+amount);

			IvalidMap=false;
		}
		
		/*
		 * if (amount < minAmount) { minAmount = amount; }
		 * 
		 * if (amount > maxAmount) { maxAmount = amount; }
		 */
	}
	
	
	
	/* Gson gson = new Gson();
     JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
     JsonArray paymentRequests = jsonObject.getAsJsonArray("paymentRequests");

     int minAmount = Integer.MAX_VALUE;
     int maxAmount = Integer.MIN_VALUE;

     for (JsonElement element : paymentRequests) {
         JsonObject paymentRequest = element.getAsJsonObject();
         int amount = paymentRequest.get("amount").getAsInt();

         if (amount < minAmount) {
             minAmount = amount;
         }

         if (amount > maxAmount) {
             maxAmount = amount;
         }*/
     

     System.out.println("Boolean Value::::::::::::: "+IvalidMap);
     System.out.println("Maximum amount: ");
	return IvalidMap;

 
}

	class TransactionRequest {
	    private String type;
	    private String description;
	    private String AuthID;
	    private PaymentRequest[] paymentRequests;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getAuthID() {
			return AuthID;
		}
		public void setAuthID(String authID) {
			AuthID = authID;
		}
		public PaymentRequest[] getPaymentRequests() {
			return paymentRequests;
		}
		public void setPaymentRequests(PaymentRequest[] paymentRequests) {
			this.paymentRequests = paymentRequests;
		}

	    // Getters and setters
	}
	    
	
}