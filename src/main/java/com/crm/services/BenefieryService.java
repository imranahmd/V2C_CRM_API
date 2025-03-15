package com.crm.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crm.Repository.BulkRefundSqlRepo;
import com.crm.Repository.PayoutRequestRepository;
import com.crm.ServiceDaoImpl.BeneServiceImpl;
import com.crm.dto.BeneFiledsDto;
import com.crm.dto.BeneFiledsDto.BeneList;
import com.crm.dto.BusinessException;
import com.crm.dto.IndussPayPayoutRaised;
import com.crm.dto.IndussPayPayoutRaised.PaymentList;
import com.crm.dto.PaymentRequestList;
import com.crm.dto.PayoutBulkStatusRequest;
import com.crm.dto.PayoutBulkStatusRequest.Data;
import com.crm.dto.payout_request;
import com.crm.dto.payout_request.PaymentRequest;
import com.crm.model.Payrequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class BenefieryService {
	private static Logger log = LoggerFactory.getLogger(BenefieryService.class);

	@Autowired
	BeneServiceImpl beneServiceImpl;

	@Autowired
	PayoutRequestRepository payoutRequestRepository;

	@Autowired
	ValidatorService validatorService;
	
	@Autowired
	com.crm.Repository.PayouStatusReposiotry PayouStatusReposiotry;
	
	@Autowired
	BulkRefundSqlRepo bulkRefundSqlRepo;

	
	public String AddBene(String json) throws Exception {
		log.info("Add bene 2");
		JSONObject js = new JSONObject(json);
		String merchant_id = js.getString("merchant_id");
		String account_num = js.getString("account_num");
		String retype_accountnum=js.getString("retype_accountnum");
		String ifsc_code = js.getString("ifsc_code");
		String bank_name = js.getString("bank_name");
		String account_holder = js.getString("account_holder");
		String mobile_no = js.getString("mobile_no");
		String email_id = js.getString("email_id");
		//String Id = js.getString("Id");
		log.info("Add AddBeneBeneficery 2" + retype_accountnum);

		return beneServiceImpl.AddBeneBeneficery(merchant_id, account_num,retype_accountnum, ifsc_code, bank_name, account_holder,
				mobile_no, email_id);
	}

	public String GetBeneList(String json) throws Exception {
		JSONObject js = new JSONObject(json);
		
		String merchant_id = js.getString("merchant_id");

		
		return beneServiceImpl.GetBeneList(merchant_id);
	}


//	public JSONObject addOrValidateService(String DecReq, String Mid) throws Exception {
//		Gson gson = new Gson();
//		BeneFiledsDto BeneDetails = gson.fromJson(DecReq, BeneFiledsDto.class);
//
//		for (BeneList BeneList : BeneDetails.getBeneListDto()) {
//			try {
//				String merchant_id = BeneList.getMerchantId();
//				String account_num = BeneList.getAccount_number();
//				String ifsc_code = BeneList.getIfsc_code();
//				String bank_name = BeneList.getBank_name();
//				String account_holder = BeneList.getAccount_holder();
//				String mobile_no = BeneList.getMobile_no();
//				String email_id = BeneList.getEmail_Id();
//				String Id = "0";// BeneList.getId();
//				validateBeneficiaryDetails(BeneList);
//
//				beneServiceImpl.AddBeneBeneficery(merchant_id, account_num, ifsc_code, bank_name, account_holder,
//						mobile_no, email_id, Id);
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		
//		return ;
//	}

	public JSONObject PayoutRaiseBulk(String DecReq, String Mid) throws Exception {
		Gson gson = new Gson();
		JSONObject Resp = new JSONObject();
		IndussPayPayoutRaised payoutReq = gson.fromJson(DecReq, IndussPayPayoutRaised.class);

		for (PaymentList BeneList : payoutReq.getPaymentRequests()) {
			try {

				beneServiceImpl.RaisedPayoutBulk(Mid, BeneList.getCustomerId(), BeneList.getClientRefNo(),
						BeneList.getAmount(), payoutReq.getDescription(), BeneList.getTxnMode());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	
		return Resp;
	}

	public String CheckWalletBalance(String Mid, String Type) {
		log.info("If Type == Wallet:::{}{}" + Type);
		String totalBalance = null;
		JSONObject BalanceResponse = new JSONObject();
		if (Type.equalsIgnoreCase("WALLET")) {
			totalBalance = bulkRefundSqlRepo.CheckWalletdetails(Mid);

			log.info("Inside If conditions::::::{}{}{}Total Balance::::::" + totalBalance);
			BalanceResponse.put("AvailableBalance", totalBalance);
			BalanceResponse.put("AuthID", Mid);
			BalanceResponse.put("Status", "Total Balance fechted sucessfully");
			BalanceResponse.put("Type", Type);

		} else {
			BalanceResponse.put("AuthID", Mid);
			BalanceResponse.put("Status", "Type not found!!");
		}
		return BalanceResponse.toString();

	}

	public String PayoutStatusApi(String Mid, String ReqData) {

		log.info("If Type == Wallet:Status::{}{}" + ReqData);

		String totalBalance = null;
		JSONObject StatsuResponse = new JSONObject();
		JSONObject FinalResponse = new JSONObject();

		String HasValue = null;
		JSONArray Jre = new JSONArray();
		Gson gson = new Gson();
		JSONObject Resp = new JSONObject();
		// JSONObject Resp = new JSONObject();

		PayoutBulkStatusRequest payoutBulkStatus = gson.fromJson(ReqData, PayoutBulkStatusRequest.class);

		for (Data d : payoutBulkStatus.getRequesData()) {
			if (HasValue == null) {
				HasValue = "ClientTxnId" + "|" + d.getClientTxnId();
			} else {
				HasValue = HasValue.concat("#ClientTxnId" + "|" + d.getClientTxnId() + "#");
			}
		}

		List<PaymentRequestList> GetResDeatisl = PayouStatusReposiotry.getPayoutTransDeatils(HasValue);
		if (GetResDeatisl.size() > 0) {
			JSONArray jr= new JSONArray();
			StatsuResponse.put("Status", "SUCCESS");
			StatsuResponse.put("RespCode", "200");
			StatsuResponse.put("RespMessage", "Transaction Successfully Fetched!!");

			for (int k = 0; k <GetResDeatisl.size(); k++) {
				JSONObject temreq= new JSONObject();
				
				temreq.put("utr", GetResDeatisl.get(k).getUtr_no());
				temreq.put("Amount", GetResDeatisl.get(k).getTxnAmount());
				temreq.put("TransId", GetResDeatisl.get(k).getTransactionId());
				temreq.put("ClientTxnId", GetResDeatisl.get(k).getClientRefNo());
				temreq.put("transStatus", GetResDeatisl.get(k).getStatus());
				temreq.put("RespMessage", GetResDeatisl.get(k).getRespMessage());


				jr.put(temreq);
			}

			StatsuResponse.put("ResponseData", jr);
		}

		return StatsuResponse.toString();


		/*
		 * log.info("Inside If conditions::::::{}{}{}Status::::::"+totalBalance);
		 * StatsuResponse.put("Status", "SUCCESS"); StatsuResponse.put("AuthID", Mid);
		 * FinalResponse.put("Payout_Status", GetResDeatisl.get("Payout_status"));
		 * FinalResponse.put("UTR", "NA"); FinalResponse.put("ClientRefNo",
		 * GetResDeatisl.get("ClientRefId")); FinalResponse.put("Payout_Date",
		 * GetResDeatisl.get("Payout_date")); Jre.put(FinalResponse);
		 * StatsuResponse.put("Data", Jre); return StatsuResponse.toString();
		 */

	}

	public void validateBeneficiaryDetails(BeneList beneficiaryDto) {

		if (beneficiaryDto.getMobile_no() == null || beneficiaryDto.getMobile_no().isEmpty()) {
			throw new BusinessException("AB101", "Please Enter Valid Mobile No");
		}

		if (beneficiaryDto.getEmail_Id() == null || beneficiaryDto.getEmail_Id().isEmpty()
				|| !Pattern.compile("^[A-Za-z0-9._+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
						.matcher(beneficiaryDto.getEmail_Id()).matches()
				|| beneficiaryDto.getEmail_Id().length() > 320) {
			throw new BusinessException("AB102", "Please Enter Valid EmailId");
		}

	}

	public JSONObject PayoutRaisedAll(String decReq, String Mid) throws Exception {
		Gson gson = new Gson();
		JSONObject Jr = new JSONObject();
		JSONObject response = new JSONObject();
		JSONArray Array = new JSONArray();
		String Random= new SimpleDateFormat("yyDDDHHmmsss").format(new Date());
		if(validatorService.GetAmtValidation(decReq,Mid)) {
		//if(CheckSpValidation(decReq,Mid)) {
		double SumAmount = calculateSumAmount(decReq);
		Map<String, Object> WalletDetails = bulkRefundSqlRepo.GetWalletDeatils(Mid,SumAmount,"123");
if(WalletDetails.get("IsApplicable").toString().equalsIgnoreCase("Y") && WalletDetails.get("ErroCode").toString().equalsIgnoreCase("200") ) {
		payout_request Request = gson.fromJson(decReq, payout_request.class);
		String randomNumber = "REW10" + "%04d".formatted((int) (ThreadLocalRandom.current().nextDouble() * 10000))+Random;
		/* if(randomNumber.equalsIgnoreCase(randomNumber)) */
		Payrequest pr = new Payrequest();
		
		pr.setMID(Request.getAuthID());
		pr.setPayReqId(randomNumber);
		pr.setRequestType("API");
		pr.setDescriptions(Request.getDescription());
		pr.setRequestJson(decReq);
		pr.setResCode("201");
		pr.setResMessage("Request Initiated");
		pr.setRequestdate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		pr.setCreatedBy("API");
		pr.setStatus("PENDING");
		
		payoutRequestRepository.save(pr);
		//:Amount between min_amt and max_amt
		for (PaymentRequest Req : Request.getPaymentRequests()) {
			String GetSpId =bulkRefundSqlRepo.GetSpId(Mid, Req.getTxnMode());
		
			pr.setPspid(GetSpId);
			String Response = beneServiceImpl.CreatePayoutRequestIn(Req, randomNumber);
			ObjectMapper ob = new ObjectMapper();
			
			

			JsonNode ResponseError = ob.readTree(Response);
			JSONArray ArrayResponse = new JSONArray(Response);
			JSONObject Res = new JSONObject();
			if (ResponseError.get(0).get("ResCode").asText().equalsIgnoreCase("400")) {
				response.put("Status", "Failed");
				response.put("RespMessage", ResponseError.get(0).get("RespMassge").asText());
				response.put("RespCode", ResponseError.get(0).get("ResCode").asText());
				response.put("ResponseData", Array);
			} else {
				Res.put("ClientTxnId", Req.getClientTxnId());
				Res.put("TransId",ResponseError.get(0).get("transId").asText());
				Res.put("Amount", Req.getAmount());
				Res.put("utr", "NA");
				Res.put("transStatus", "0");
				Array.put(Res);
				response.put("Status", "Pending");
				response.put("RespMessage", "Transfer request pending at the bank");
				response.put("RespCode", "201");
				response.put("PayReqId", randomNumber);
				response.put("ResponseData", Array);

			}
		}
		payoutRequestRepository.save(pr);

}else {
	response.put("Status", WalletDetails.get("Reqstatus").toString());
	response.put("RespMessage", WalletDetails.get("ErroMsg").toString());
	response.put("RespCode", WalletDetails.get("ErroCode").toString());
	response.put("ResponseData", Array);
	
}
		}else {
			response.put("Status", "FAILED");
			response.put("RespMessage", "Invalid Transaction Request");
			response.put("RespCode","400");
			response.put("ResponseData", Array);
			
		}
		/*
		 * }else { response.put("Status", "FAILED"); response.put("RespMessage",
		 * "Transaction Request should be proper Amount / and TxnMode");
		 * response.put("RespCode","400"); response.put("ResponseData", Array); }
		 */
		/*
		 * response.put("Status", "SUCCESS"); response.put("RespMassage",
		 * "Kindly wait some time!!"); response.put("RespCode", "200");
		 */

		return response;

	}
	
	  private static double calculateSumAmount(String json) {
	        double sum = 0.0;

	        JSONObject jsonObject = new JSONObject(json);
	        JSONArray paymentRequests = jsonObject.getJSONArray("paymentRequests");

	        for (int i = 0; i < paymentRequests.length(); i++) {
	            JSONObject paymentRequest = paymentRequests.getJSONObject(i);
	            String amountStr = paymentRequest.getString("amount");
	            double amount = Double.parseDouble(amountStr);
	            sum += amount;
	        }

	        return sum;
	    }


	  private boolean CheckSpValidation(String json,String Mid) {
	        double sum = 0.0;
boolean IsValid=true;

	        JSONObject jsonObject = new JSONObject(json);
	        JSONArray paymentRequests = jsonObject.getJSONArray("paymentRequests");

	      
	       if(txnModeValidation(json))
	       {
	    	  
	       }else {
	    	   IsValid=false;
	       }
	          
	        return IsValid;
	    }
	  
	  
	  
	  public static boolean txnModeValidation(String Json)
	  {
			Set<String> txnModes = new HashSet<>();
  boolean IsValid=true;
  Gson gson = new Gson();
  JsonObject jsonObject = gson.fromJson(Json, JsonObject.class);
  JsonArray paymentRequests = jsonObject.getAsJsonArray("paymentRequests");

  boolean isTxnModeSame = true;
  String previousTxnMode = null;

  for (JsonElement element : paymentRequests) {
      JsonObject paymentRequest = element.getAsJsonObject();
      String txnMode = paymentRequest.get("txnMode").getAsString();

      if (previousTxnMode == null) {
          previousTxnMode = txnMode;
      } else if (!previousTxnMode.equals(txnMode)) {
          isTxnModeSame = false;
          break;
      }
  }

  if (isTxnModeSame) {
      System.out.println("Transaction mode is the same for all payment requests.");
  } else {
	  IsValid=false;
      System.out.println("Transaction mode is different for at least one payment request.");
  }
return isTxnModeSame;

	  
}
	  
	  
/*
 * public static void main(String [] args) { String Random= new
 * SimpleDateFormat("yyDDDHHmmsss").format(new Date());
 * System.out.print(Random); }
 */
}
