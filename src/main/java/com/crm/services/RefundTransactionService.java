package com.crm.services;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.BulkRefundSqlRepo;
import com.crm.Repository.MerchantRepository;
import com.crm.config.VassFileReader;
import com.crm.dto.BulkRefund;
import com.crm.helper.FileUploadHelper;
import com.crm.model.BulkRefundSql;
import com.crm.util.GeneralUtil;
import com.crm.util.GenerateRandom;
import com.google.gson.Gson;

@Service
public class RefundTransactionService {	
	static Logger log = LoggerFactory.getLogger(RefundTransactionService.class);
	public static String uploadDirectory = "/home/BulkRefundsFiles/BulkRefund";
	public static String uploadDirectoryForDownload = "/home/BulkRefundsFiles/ManualDownload";
	public static String uploadDirectoryForUpload = "/home/BulkRefundsFiles/ManualUpload";
	public static String uploadDirectoryForDownloadSBI = "/home/BulkRefundsFiles/ManualDownload/SBI/";
	public static String uploadDirectoryForDownloadSBIZIP = "/home/BulkRefundsFiles/ManualDownload/SBIZIP/";
	public static String PIPE_SYMBOL = "|";	
	
	@Autowired
	BulkRefundSqlRepo bulkRefundSqlRepo;
	
	
	@Autowired
	MerchantRepository merchantrepo;
	
	@Value("${sbi.SBIPGCode}")
	private String SBIPGCode;
	
	@Value("${refund.pay.url}")
	private String refundrequestURL;
	
	
	
	public List<Object> createRefundAmtt(JSONArray jsonarray) {
		String jsonArray = null;
		List<Object> ListResponse = new ArrayList<Object>();
		
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
				String TransId = jsonobject.getString("TransId");
				Double ReqRefundAmt = jsonobject.getDouble("RefundAmt");
				String Merchant_Id = jsonobject.getString("Merchant_Id");
	//			-----
				String refundRequestId = GenerateRandom.randomAlphaNumeric(14);
				BulkRefund bulkrefund = new BulkRefund();
				bulkrefund.setMerchantId(Merchant_Id);
				bulkrefund.setTxnId(TransId);
				bulkrefund.setRefundAmount(ReqRefundAmt);
				bulkrefund.setMerchantRefId(refundRequestId);
	//			JSONObject resp = ValidateTxnId(bulkrefund);
				JSONObject resp = refundOldModule(bulkrefund);
	//			--------
	
				ListResponse.add(resp.toMap());
			}
		
		Gson gson = new Gson();
		jsonArray = gson.toJson(ListResponse);
		return ListResponse;

	}

	public Map<String,Object> getRefundTransactionList(String id, String txnId, String merchantId, String fromDate, String toDate,
			String bankId, String custMail, String custMobile , String spId) throws ParseException {
		Map<String, Object> Msg = null ;
		String strDate = "";
		String endDate = "";
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		String Query = "";
		 List<Object[]> resultdata = merchantrepo.refundAmtList(merchantId, fromDate, toDate, id, bankId, custMobile, custMail, txnId, spId);
		 if(!resultdata.isEmpty()) {
			 for(Object[] obj:resultdata) {
				 JSONObject item = new JSONObject();
				 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				 String string  = dateFormat.format(obj[6]);
				 item.put("TransactionId", obj[0].toString());
				 item.put("TransactionAmount", obj[1]);
				 item.put("MerchantId", obj[2]);
				 item.put("TxnId", obj[3]);
				 item.put("ReconStatus", obj[7]);
				 item.put("BankId", obj[9]);
				 item.put("BalanceAmount", obj[10]);
				 item.put("TransactionDate", string);
				 item.put("ServiceRRN", obj[8]);
				 item.put("ProcessId", obj[11]);
				 item.put("MobileNo", obj[4]);
				 item.put("EmailId", obj[5]);
				 array.put(item);
			 }

			 js1.put("Status", "Success");
			 js1.put("Message", "Data Found");
			 js1.put("data", array);
		 }else {
			 js1.put("Status", "Error");
			 js1.put("Message", "No Data found");
		 }
		Msg = js1.toMap();
		return Msg;
	}
	
	
	public Map<String,String> initiateBulkRefund(MultipartFile file, String userId)throws IOException
	{
		Map<String,String> response = new LinkedHashMap<>();
		
		if(file !=null) {
			String fileName = file.getOriginalFilename();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
				
				String filesPath = uploadDirectory + File.separator ;
				Path path = Path.of(filesPath,fileName);
				String filesnames = filesPath + fileName;
				File newFile = new File(filesnames);
				newFile.deleteOnExit();
				
				Files.write(path, file.getBytes());
				
				List<List<String>> rows = FileUploadHelper.readDataFromXLS(newFile);
				log.info("Bulk refund request received with record size : {}",(rows == null?"Error in File":(rows.size()-1)));
				
				if (rows != null && rows.size()>1) {
					
					
					int transactionCount = 0;
					double refundAmount = 0.0;
					String refundBatchId = UUID.randomUUID().toString().replaceAll("-","");
					
					List<BulkRefundSql> bulkRefundList = new ArrayList<>();
					
					for (int i=1; i< rows.size(); i++)
					{
						BulkRefundSql bulkrefund = null;
						
						Object dataObj = getBulkRefundFileRecord(rows.get(i),i);
						
						if(dataObj == null)
							continue;
						else if(dataObj instanceof String)
						{
							log.info("Error received for record no :{} with error as :{}",i,dataObj);
							response.put(String.valueOf(i),String.valueOf(dataObj));
						}
						else if(dataObj instanceof BulkRefundSql sql)
						{
							bulkrefund = sql;
						}
						
						if(bulkrefund != null)
						{
							String merchantTxnId = null;
							List<Object[]> txnDetails = merchantrepo.getTxnDetailsById(Long.valueOf(bulkrefund.getTxn_id()));
							if(txnDetails == null)
							{
								response.put(String.valueOf(i),"Transaction Id Does Not Exist");
							}
							else {
								for(Object[] obj: txnDetails) 
								{
									bulkrefund.setMerchantId((String)obj[0]);
									merchantTxnId = (String)obj[1];
								}
							
								bulkrefund.setBatch_id(refundBatchId);
								
								String refundStatus = refundBulkModule(bulkrefund, merchantTxnId);
								if(refundStatus != null && refundStatus.equals("1"))
								{
									transactionCount++;
									refundAmount +=(bulkrefund.getAmount());
									
									bulkrefund.setError_message("Refund Raised Successfully");
									bulkRefundList.add(bulkrefund);
									response.put(String.valueOf(i), bulkrefund.getError_message());
								}
								else if(refundStatus != null)
									response.put(String.valueOf(i),refundStatus);
								else
									response.put(String.valueOf(i), "Unable to Process Refund Request");
							}
						}
						
					}
					if(transactionCount>0)
					{
						merchantrepo.updateTableRecords(refundBatchId, userId, transactionCount, refundAmount, "Uploaded", 0, filesnames );
						log.info("update Batch   " + refundBatchId + userId + transactionCount + refundAmount  +  "Uploaded" + filesnames);
						bulkRefundSqlRepo.saveAll(bulkRefundList);
						log.info("update Bulk List " + bulkRefundList);
					}
				}
				else
					response.put("Error","File does not contain any record(s)");
			}
			else
				response.put("Error","Invalid File Uploaded");
		}
		else
			response.put("Error","Invalid Request");
		
		
		return response;
	}
	
	private Object getBulkRefundFileRecord(List<String> dataRows,int recordCount) 
	{
		BulkRefundSql bulkRefund = null;
		StringBuilder error = new StringBuilder();
		
		if (dataRows.size() >= 2) {
			
			log.info("For record No : {} Txn ID:{} and RefundAmount:{} as per file", recordCount,dataRows.get(0), dataRows.get(1));
			
			if(StringUtils.isBlank(dataRows.get(0))|| StringUtils.isBlank(dataRows.get(1)))
				error.append("Transaction Id Or Refund Amount is Missing");
			else {
				
				bulkRefund = new BulkRefundSql();
				if(dataRows.get(0).contains("'")) {
					bulkRefund.setTxn_id(dataRows.get(0).substring(0).trim());
				}else {
					bulkRefund.setTxn_id(dataRows.get(0).trim());
				}

				try {
					Long.valueOf(bulkRefund.getTxn_id());
				}
				catch(Exception e)
				{
					error.append("Invalid Transaction Id");
				}
				try {
					double refundAmt = Double.valueOf(dataRows.get(1));
					
					if(refundAmt<=0)
						error.append("Invalid Refund Amount");
					
					bulkRefund.setAmount(refundAmt);
				}
				catch(Exception ee)
				{
					if(error.length()>1)
						error.append(" and Refund Amount");
					else
						error.append("Invalid Refund Amount");
					
				}
				
			}
		}
		else
			error.append("Invalid Data Record");
		
		log.info("For record no :{} Error size is :{}",recordCount,error.length());
		
		if(error.length()<= 1 && bulkRefund != null)
		{
			bulkRefund.setStatus("UPLOADED");
			bulkRefund.setMerchantRefId(UUID.randomUUID().toString().replaceAll("-",""));
			bulkRefund.setIs_deleted(0);
			return bulkRefund;
		}
		
		return error.toString();
	}
	
	private JSONObject refundOldModule(BulkRefund bulkRefund) {
		
		String TransId= bulkRefund.getTxnId();
		String Merchant_Id = bulkRefund.getMerchantId();
		Double ReqRefundAmt = bulkRefund.getRefundAmount();
		String refundRequestId = bulkRefund.getMerchantRefId();
	
		String error_code = "";
		String respMessage = "";
		String refundType = "";
		
		JSONObject requestData = new JSONObject();
		requestData.put("txnId", TransId);
		requestData.put("refundAmount", ReqRefundAmt);
		requestData.put("addedBy", Merchant_Id);
		requestData.put("refundRequestId", refundRequestId);
		
//		String transaction = merchantrepo.fintransction(TransId, Merchant_Id);
		String transaction = merchantrepo.fintransction(TransId);
		String mId = null;
		log.info("requestData   " + requestData.toString());
		log.info("transaction   " + transaction);
		if (transaction != null) {
//			String merchantId = transaction.getMerchantId();
			String encryptkey = null;
			
			mId = merchantrepo.fintransctionMid(TransId);
			log.info("mId:::::::::"+mId);
			List<Object[]> merchantdet = merchantrepo.findByMerchant(mId);
			if (merchantdet != null && merchantdet.size() > 0) {
				for (Object[] obj : merchantdet) {
					encryptkey = ((String) obj[17]);
				}

			}
			log.info("mId:::::::::"+mId);
			String encData = null;
			

			if (encryptkey.length() == 8 || encryptkey.length() == 16) {
					encData = GeneralUtil.getEncData(requestData.toString(), encryptkey);
			} 
			else{
					encData = GeneralUtil.encrypt(encryptkey, encryptkey.substring(0, 16), requestData.toString());
			}	
				
			JSONObject encyptData = new JSONObject();
			encyptData.put("merchantId", mId);
			encyptData.put("encData", encData);
			System.out.println("encyptRequestData" + encyptData);
			String propertyValue = null;
			
			//				propertyValue = VassFileReader.getPropertyValue("refundrequestURL");
							propertyValue = refundrequestURL;
			
							log.info("URL For Refund  " + propertyValue);

			String refundRequestCall = null;
			if (propertyValue != null) {
				refundRequestCall = this.refundRequestCall(propertyValue, encyptData.toString());
				log.info("refundRequestCall response " + refundRequestCall);
			}
			
			String respCode=null;

			if (refundRequestCall != null) {
				try {
					JSONObject responseData = new JSONObject(refundRequestCall);
					log.info("responseData::::::::::::"+responseData);
					respCode = (String) responseData.get("resp_code");
					error_code =respCode;
					if (respCode.equals("RF000")) {

						respMessage="Refund initiated successfully";
						refundType = (String) responseData.get("refund_type_status");

					} else {

						respMessage=(String) responseData.get("resp_message");
						refundType = (String) responseData.get("refund_type_status");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage(), e.getMessage());
				}
			}else {
//				 String message = (String) responseData.get("resp_message");
				respMessage="Unable to raise a refund";
				refundType = "NA";
			}			
		}else {
			respMessage="Transaction Not Found In Given Transaction Id";
			refundType = "NA";
		}
		
		JSONObject resp = new JSONObject();
		resp.put("error_code", error_code);
		resp.put("respMessage", respMessage);
		resp.put("refundType", refundType);
		resp.put("MerchantId", mId);
		resp.put("Id", transaction);
		return resp;
		
	}
	

	private String refundBulkModule(BulkRefundSql bulkRefund,String merchantTxnId) {
		
		String response = null;
		
		try {
			JSONObject requestData = new JSONObject();
			requestData.put("txnId", merchantTxnId);
			requestData.put("refundAmount", String.valueOf(bulkRefund.getAmount()));
			requestData.put("addedBy", "BulkRefund");
			requestData.put("refundRequestId", bulkRefund.getMerchantRefId());
		
			String encryptkey = null;
			List<Object[]> merchantdet = merchantrepo.findByMerchant(bulkRefund.getMerchantId());
		
			if (merchantdet != null && merchantdet.size() > 0) {
				for (Object[] obj : merchantdet) {
					encryptkey = ((String) obj[17]);
				}
			}

			String encData = null;
		
			if (encryptkey.length() == 8 || encryptkey.length() == 16) {
				encData = GeneralUtil.getEncData(requestData.toString(), encryptkey);
			}
			else{
				encData = GeneralUtil.encrypt(encryptkey, encryptkey.substring(0, 16), requestData.toString());
			}	
				
			JSONObject encyptData = new JSONObject();
			encyptData.put("merchantId", bulkRefund.getMerchantId());
			encyptData.put("encData", encData);

			log.info("Bulk refund for txn id :{} with enc data:{}",bulkRefund.getTxn_id(),encData);
		
//			String propertyValue =  VassFileReader.getPropertyValue("refundrequestURL");
			String propertyValue =  refundrequestURL;
			log.info("URL for posting refund :{}",propertyValue);

			String refundRequestCall = refundRequestCall(propertyValue, encyptData.toString());

			log.info("Refun Api response for txn id :{} is :{} ",bulkRefund.getTxn_id(),refundRequestCall);
			
			String respCode = null;

			if (refundRequestCall != null) {
				JSONObject responseData = new JSONObject(refundRequestCall);
				respCode = responseData.getString("resp_code");

				if (respCode.equals("RF000")) {
					response = "1";
				} 
				else {
					response = responseData.getString("resp_message");
				}
			}
			else 
				response = "Error while Initiating Refund";
		
		}catch(Exception e)
		{
			log.error("Error while processing refund for txn id :{}",bulkRefund.getTxn_id(),e);
			response = "Error while Initiating Refund Request";
		}
		
		return response;
	}
	
	private String refundRequestCall(String requestURL, String data) {
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;

		log.info("refundRequestCall::: : Posting URL : " + requestURL);

		try {
			URL obj = new URL(requestURL);
			// HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
			con.addRequestProperty("Content-Length", data.getBytes().length + "");
			con.setDoOutput(true);
			con.connect();
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			// log.info(new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date())+" :::
			// Data length :: " + data.getBytes().length);
			wr.write(data);
			wr.flush();
			log.info("refundRequestCallwrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrL : " + con.getResponseCode() );

			respString = new StringBuffer();

			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				log.info("refundRequestCall :: HTTP OK");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null) {
					respString.append(line);
				}

				br.close();
				log.info("refundRequestCall :: Response : " + respString.toString());
				return respString.toString();
			}
		} catch (Exception e) {
			log.info("refundRequestCallError Occurred while Processing Request : ", e.getMessage(),e);

		}

		return null;
	}
	
	public Map<String,Object> getRefundDownloadRecordsList(String id, String txnId, String merchantId, String fromDate, String toDate,
			String spId, String custMail, String custMobile, String count, int pageNo, int Type, String searchBy, String refundType  ) throws ParseException {
		Map<String, Object> Msg = null ;
		String strDate = "";
		String endDate = "";
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		
		BigInteger Id = BigInteger.ZERO;
		if (!id.equals("")) {
			 Id =new BigInteger(id.toString());
		}
		
		try {
			List<Map<String, Object>> resultdata = bulkRefundSqlRepo.refundDownloadStatusList(merchantId, fromDate, toDate, Id, txnId, custMail,  custMobile, count, spId, pageNo, Type, searchBy, refundType );
				if(!resultdata.isEmpty()) {
					 js1.put("Status", "Success");
					 js1.put("Message", "Data Found");
					 js1.put("data", resultdata);
				}else {
					 js1.put("Status", "Error");
					 js1.put("Message", "No Data found");
					 js1.put("data", "");
				}
		}catch(Exception e) {
			 js1.put("Status", "Error");
			 js1.put("data", "Error Found");
			log.info("Refund Transaction Status ::::::::::::::::::{} "+e.getMessage());
		}

		Msg = js1.toMap();
		return Msg;
	}

	public Map<String, Object> getRefundTransactionStatusList(String id, String txnId, String refundId, String fromDate, String toDate,
			String refundType, String refundStatus, String count, int pageNo, int Type, String searchBy) {
		Map<String, Object> Msg = null ;
		String strDate = "";
		String endDate = "";
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();	
		
		try {
			List<Map<String, Object>> resultdata = bulkRefundSqlRepo.refundTransactionStatus( fromDate, toDate, id, txnId, refundId,  refundType, refundStatus, count, pageNo, Type, searchBy );
		
			if(!resultdata.isEmpty()) {
				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", resultdata);
			}else {
				 js1.put("Status", "Error");
				 js1.put("Message", "No Data found");
				 js1.put("data", "");
			}
		}catch(Exception e) {
			 js1.put("Status", "Error");
			 js1.put("data", "Error Found");
			log.info("Refund Transaction Status ::::::::::::::::::{} "+e.getMessage());
		}

		Msg = js1.toMap();
		return Msg;
	}

	public Map<String, Object> getRefundDownloadFileFomList(JSONArray jsonarray) {
		JSONObject js1 = new JSONObject();
		Map<String, Object> Msg = null;
		ArrayList<Tuple> list = new ArrayList<Tuple>();
		JSONObject js = new JSONObject();
		String UserId = null;

		ArrayList<String> numbers = new ArrayList<>();
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
			String RefundId = jsonobject.getString("refundId");
			UserId = jsonobject.getString("userId");
			try {

				String processId = bulkRefundSqlRepo.getProcessId(RefundId);
				log.info("processId:::::" + processId);

				if (processId.equalsIgnoreCase("14")) {
					String IdVal = bulkRefundSqlRepo.getiDVal(RefundId);
					numbers.add(IdVal);

				} else {
					List<Tuple> refundinfo = bulkRefundSqlRepo.getrefundInfo(RefundId);
					if (!refundinfo.isEmpty()) {
						list.addAll(refundinfo);
					} else {
						js1.put("Status", "Error");
						js1.put("Message", "No Data found");
					}
				}

			} catch (Exception e) {
				log.info("Refund Manual File Download  ::::::::::::::::::{} " + e.getMessage());
			}
		}

		log.info("numbers::::::::::" + numbers);
		String refundCheckSumDataSBI = "";
		String manualFileDataSBI = "";

		if (!numbers.isEmpty()) {

			for (String number : numbers) {

				List<Object[]> transList = bulkRefundSqlRepo.listDataRefund(number);
				for (Object[] obj : transList) {

					String serviceTrnsId = (String) obj[0];
					String merchantTxnID = (String) obj[1];
					String refund_RequestId = (String) obj[2];
					System.out.println("obj[3]:::::" + obj[3]);
					BigDecimal bigdecimal = new BigDecimal(obj[3].toString());
					String refundAmt = bigdecimal.toString();
					Timestamp timestamp = (Timestamp) obj[4];
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String date_time = sdf.format(timestamp);

					log.info("DateTime as String: " + date_time);
					Timestamp timestamp1 = (Timestamp) obj[5];
					String added_on = sdf.format(timestamp1);
					log.info("added_on as String: " + added_on);
					String Added_By = (String) obj[6];
					BigDecimal bigdecimal1 = new BigDecimal(obj[7].toString());
					String txn_amount = bigdecimal1.toString();

					log.info("serviceTrnsId:::" + serviceTrnsId + ":::merchantTxnID:::::" + merchantTxnID
							+ ":::refund_RequestId::" + refund_RequestId + "::refundAmt::" + refundAmt
							+ ":::date_time:::" + date_time + "::added_on:::" + added_on + ":::Added_By:::" + Added_By);
					refundCheckSumDataSBI = refundCheckSumDataSBI + serviceTrnsId + PIPE_SYMBOL + merchantTxnID
							+ PIPE_SYMBOL + refund_RequestId + PIPE_SYMBOL + refundAmt + "\n";

					SimpleDateFormat formatter = new SimpleDateFormat("YYMMDD");
					String formattedDate = formatter.format(timestamp1);
					System.out.println("format:::" + formattedDate);

					SimpleDateFormat formatterRef = new SimpleDateFormat("YYMMDD");
					String formattedRefundDate = formatterRef.format(timestamp1);
					System.out.println("formattedRefundDate:::" + formattedRefundDate);

					if (Added_By.equalsIgnoreCase("Admin")) {

						manualFileDataSBI = manualFileDataSBI + "99" + PIPE_SYMBOL + formattedDate + PIPE_SYMBOL + ""
								+ PIPE_SYMBOL + serviceTrnsId + PIPE_SYMBOL + refundAmt + PIPE_SYMBOL + "\n";
					} else {
						manualFileDataSBI = manualFileDataSBI + "20" + PIPE_SYMBOL + formattedDate + PIPE_SYMBOL
								+ formattedRefundDate + PIPE_SYMBOL + serviceTrnsId + PIPE_SYMBOL + refundAmt
								+ PIPE_SYMBOL + txn_amount + "\n";
					}

				}

				System.out.println(number);
			}

			// for txt file code

			try {
//					String fileNAmeD=createSBIRefundZipFile(refundCheckSumDataSBI,manualFileDataSBI);
				JSONObject fileNAmeD = createSBIRefundZipFile(refundCheckSumDataSBI, manualFileDataSBI);
				log.info("fileNAmeD:::::::::::" + fileNAmeD);
				if (fileNAmeD.get("Status").equals("Success")) {
					String filename = fileNAmeD.getString("FileName");
					String fileLocation = fileNAmeD.getString("FilePath");
					js1.put("Status", "Success");
					js1.put("Message", fileNAmeD.getString("Message"));
					js1.put("FileName", filename);
					js1.put("FilePath", fileLocation);

				} else {
					log.info("Refund SBI Download :::else:::::::::::::::{} ");
					js1.put("Status", "Error");
					js1.put("Message", "File Not Generated");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else {
			// for manual file
			js = createxlsxFile(list, UserId);
			if (js.get("Status").equals("Success")) {
				String filename = js.getString("FileName");
				String fileLocation = js.getString("FilePath");

				js1.put("Status", "Success");
				js1.put("Message", js.getString("Message"));
				js1.put("FileName", filename);
				js1.put("FilePath", fileLocation);

			} else {
				log.info("Refund Manual Download :::else:::::::::::::::{} ");
				js1.put("Status", "Error");
				js1.put("Message", "File Not Generated");
			}
		}
		

		
		Msg = js1.toMap();
		return Msg;
	}
	
	public JSONObject createxlsxFile(List<Tuple> list, String userId) {
		JSONObject js1 = new JSONObject();
		StringBuilder filesname = new StringBuilder();
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("data");
		XSSFRow hrow = sheet.createRow(0);
		XSSFRow vrow = sheet.createRow(1);
		int rowCount = 0;
		int rowCounthead = 0;        

		for(Tuple value : list) {
			int columnCount = -1;
            int columnCounthead = -1;
			Row row = sheet.createRow(++rowCount);
			Row rowhead = sheet.createRow(rowCounthead);
			List<TupleElement<?>> elements = value.getElements();
		    for (TupleElement<?> element : elements ) {
		    	Cell vcell = row.createCell(++columnCount);
		        vcell.setCellValue("" + value.get(element.getAlias()));
		        Cell hcell = rowhead.createCell(++columnCounthead);
		        hcell.setCellValue(element.getAlias());
		    }

		}
		
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        String strDatewospc = strDate.replaceAll("\\s", "");
        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
		String fileOrgname = (strDatewosp +".xlsx");
		String filesname1 = uploadDirectoryForDownload + "/" + userId +"/";
		fileOrgname= fileOrgname.replaceAll("\\s", "");
		String nameFileconvert =  "Manual_Refund"+"_"+fileOrgname;
		File file = new File(filesname1);
		if (!file.exists()) {            
            file.mkdirs();            
        }	
		try {
			Path path = Path.of(filesname1,nameFileconvert);
			filesname.append(nameFileconvert);
			String DocRef = filesname1 + nameFileconvert;
			FileOutputStream outputStream = new FileOutputStream(DocRef);
			workBook.write(outputStream);
			log.info("File Create :::Name:::::::::::::::{} "+ DocRef);
			js1.put("Status", "Success");
			js1.put("Message", "File Created and Uploaded ");
			js1.put("FilePath", DocRef);
			js1.put("FileName", nameFileconvert);
		} catch (Exception e) {
			log.info("Error in File Create :::Name:::::::::::::::{} "+ e.getMessage());
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("Message", "File Not Created " + e.getMessage());
		}
		
		return js1;
		
	}

	public Map<String, Object> uploadManualRefundFile(MultipartFile file, String userId, String sPID) {
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, Object> Msg = null;
		String FilexlName = file.getOriginalFilename();
		File filexl = new File(FilexlName);
		boolean resultFlag = true;
		String errorTXNIds = "";
		if (file != null) {
			String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
			log.info("File Name ::    " + FilexlName);
			log.info("File Name extension  ::    " + extension);
			String filesPathtxt = uploadDirectoryForUpload + "/";
			File newFiletxt = new File(uploadDirectoryForUpload + "/" + FilexlName);

			if (!newFiletxt.exists()) {
				newFiletxt.mkdirs();
			}
			if (!("").equals(filesPathtxt)) {

				if (newFiletxt.exists()) {
					newFiletxt.delete();
				}
			}
			Path pathtxt = Path.of(filesPathtxt, FilexlName);
			String filesnamestxt = filesPathtxt + FilexlName;
			try {
				Files.write(pathtxt, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
				log.info("File Write Error   " + filesnamestxt);
			}
			if (extension.equalsIgnoreCase("txt") && sPID.equalsIgnoreCase("14")) {
				JSONObject item = new JSONObject();
				try {
					log.info("Helooooooooooooo am in tryyy block ");
					// csv file containing data
//					File file1 = getFilePath(file);
//					log.info("............................................." + file);
					// create BufferedReader to read csv file
					BufferedReader br = new BufferedReader(new FileReader(newFiletxt));
					String strLine = "";
					StringTokenizer st = null;
					int lineNumber = 0, tokenNumber = 0;
//					strLine = br.readLine();

					// read comma separated file line by line
					while ((strLine = br.readLine()) != null) {
						if (lineNumber == 0) {
							lineNumber++;
							continue;
						}

						// break comma separated line using ","
						st = new StringTokenizer(strLine, ",");
						log.info("st:::::::" + st.toString());
						Map<Integer, String> resultSet = new HashMap<Integer, String>();
						while (st.hasMoreTokens()) {
							tokenNumber++;
//							System.out.println("Line # #######+" + lineNumber +" Token # " + tokenNumber + ",Token : "+ st.nextToken());
							resultSet.put(tokenNumber, st.nextToken());
						}
						log.info("resultSet::::::::" + resultSet.toString());
						String refundFlag = resultSet.get(7);
						String remark = resultSet.get(8);
						String isApproveFlag = null;
						String refundId = resultSet.get(5); // Sequence No.
						String txnId = resultSet.get(2); // Merchant Ref. No.
						String isProcessed = null;
						String refundStatus = null;
						

						log.info("::refundFlag::::::" + refundFlag + "::remark:::" + remark + ":::isApproveFlag:::::"
								+ isApproveFlag + ":::refundId:::" + refundId + "::::::txnId::" + txnId
								+ ":::isProcessed::::" + isProcessed + "::::refundStatus::::" + refundStatus);
						if (refundFlag.equalsIgnoreCase("Success")) {
							isApproveFlag = "Y";
							isProcessed = "Y";
							refundStatus = "Success";
						} else {
							isApproveFlag = "N";
							isProcessed = "N";
							refundStatus = "Failed";
						}

						log.info("data updated remark=" + remark + "|isApproveFlag=" + isApproveFlag + "orderId" + txnId
								+ "|RefundId=" + refundId + "|isProcessed=" + isProcessed + "|refundStatus="
								+ refundStatus);
						String requestStatus = "";
						String txnDtO = null;
						String findByProperty = merchantrepo.findByProperty(txnId);
						if (findByProperty != null || !findByProperty.isEmpty()) {
//							txnDtO = (TblTransactionmaster) findByProperty.get(0);
							String reconStatus = findByProperty;
							if (reconStatus.equalsIgnoreCase("NRNS")) {
								requestStatus = "Success";
							} else {
								requestStatus = "Intiated";
							}
						}
						Timestamp TimeSpan = new Timestamp(new Date().getTime());
						int updateRefundStatus = bulkRefundSqlRepo.updateRefundStatusForManual(refundStatus,
								requestStatus, isApproveFlag, refundId, refundStatus, remark, TimeSpan);
//						int updateRefundStatus = updateRefundStatus(refundStatus, requestStatus, isApproveFlag,
//								refundId, remark);

						if (updateRefundStatus == 0) {
							errorTXNIds = refundId + ", " + errorTXNIds;
						}
						// reset token number
						tokenNumber = 0;

					}
					br.close();
					if (errorTXNIds.equals("")) {
						String message = "File has been uploaded succesfully";
						item.put("Status", "Success");
						item.put("Message", message);
					} else {
						String message = "Error Occure while updating refund Ids "
								+ errorTXNIds.substring(0, errorTXNIds.length() - 1) + ".";
						item.put("Status", "Error");
						item.put("Message", message);
					}

				} catch (Exception e) {
					String message = "Exception while reading file: " + e.getMessage();
					log.info("Exception while reading file: " + e.getMessage());
					item.put("Status", "Error");
					item.put("Message", message);
				}
				array.put(item);
				js1.put("Status", "Success");
				js1.put("Data", array);
			} else if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
				String filesPath = uploadDirectoryForUpload + "/";
				File newFile = new File(uploadDirectoryForUpload + "/" + FilexlName);

				if (!newFile.exists()) {
					newFile.mkdirs();
				}
				if (!("").equals(filesPath)) {

					if (newFile.exists()) {
						newFile.delete();
					}
				}
				Path path = Path.of(filesPath, FilexlName);
				String filesnames = filesPath + FilexlName;
				try {
					Files.write(path, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
					log.info("File Write Error   " + filesnames);
				}
				List<List<String>> rows = FileUploadHelper.readDataFromXLS(newFile);
				rows.remove(0);
				for (List<String> row : rows) {
					JSONObject item = new JSONObject();
					String refundRequestId = row.get(3);
					String requestStatus = row.get(4);
					String refundRemark = "Refund is Processed";
					String refundStatus = null;
					String isApproveFlag = "N";
					boolean validFile = true;
					if (refundRequestId != null && requestStatus != null) {
						if (requestStatus.equalsIgnoreCase("Failed")) {
							refundRemark = row.get(5);
							refundStatus = "Pending";
							validFile = validFile && true;
						} else if (requestStatus.equalsIgnoreCase("Success")) {
							refundStatus = "Success";
							isApproveFlag = "Y";
							validFile = validFile && true;
						} else {
							String message = "Invalid Status value in the file";
							item.put("Status", "Error");
							item.put("Message", message);
							validFile = validFile && false;
						}

						if (validFile) {
							Timestamp TimeSpan = new Timestamp(new Date().getTime());
							int updateRefundStatus = bulkRefundSqlRepo.updateRefundStatusForManual1(refundStatus,
									requestStatus, isApproveFlag, refundRequestId, refundStatus, refundRemark, userId,
									TimeSpan);
//							int updateRefundStatus = updateRefundStatusForManual(refundStatus, requestStatus,
//									isApproveFlag, refundRequestId, refundStatus, refundRemark);

							log.info("resultFlag" + resultFlag);
							log.info("updateRefundStatus  " + updateRefundStatus);
							boolean isRefund = true;
							log.info("resultFlag " + resultFlag);
							if (updateRefundStatus == 0) {
								isRefund = false;
								errorTXNIds = refundRequestId + "," + errorTXNIds;
							}
							resultFlag = resultFlag && isRefund;

							log.info("errorTXNIds " + errorTXNIds);
							log.info("resultFlag " + resultFlag);
							if (resultFlag) {
								String message = "Record has been uploaded succesfully";
								item.put("Status", "Success");
								item.put("Message", message);
							} else {
								String message = "Error Occure while updating refund Ids "
										+ errorTXNIds.substring(0, errorTXNIds.length() - 1) + ".";
								item.put("Status", "Error");
								item.put("Message", message);
							}
						}
					} else {
						String message = "Invalid Row Data";
						item.put("Message", message);
					}
					array.put(item);
				}
				js1.put("Status", "Success");
				js1.put("Data", array);
			}
		} else {
			js1.put("Status", "Error");
			js1.put("Message", "File Not Found ");
		}
		Msg = js1.toMap();
		return Msg;
	}

	public Map<String, Object> atomRefundFile(MultipartFile file, String userId) {
		JSONObject js1 = new JSONObject();
		Map<String, Object> Msg = null ;
		String FilexlName = file.getOriginalFilename();
		File filexl = new File(FilexlName);
		JSONArray array = new JSONArray();
		try {
			if (file != null) {
				String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
				log.info("File  extension :::  " + extension);
				log.info("File name=" + file.getOriginalFilename());

				if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
					String filesPath = uploadDirectoryForUpload + "/" ;
					File newFile = new File(uploadDirectoryForUpload + "/" + FilexlName );
					
					if (!newFile.exists()) {            
						newFile.mkdirs();            
			        }
					if(!("").equals(filesPath)){
						
						if (newFile.exists()) {
							newFile.delete();
						}
					}
					Path path = Path.of(filesPath,FilexlName);
					String filesnames = filesPath + FilexlName;
					try {
						Files.write(path, file.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
						log.info("File Write Error   " + filesnames);
					}
					
					List<List<String>> rows = FileUploadHelper.readDataFromXLS(newFile);
					Map<Integer, String> validationMessageMap = null;
					log.info("File Data Rows   " + rows);
					if (rows != null) {
						int Datasize = rows.size();
						int transactionCount = 0;
//						RefundService refundService = new RefundServiceImpl();
						validationMessageMap = new HashMap<Integer, String>();
						String atomTxnId = null, bankRefNo = null, merchantTxnId = null, refundAmount = null;
						for (int i = 1; i < Datasize; i++) {
							JSONObject item = new JSONObject();
							List<String> dataRows = rows.get(i);
							if (!dataRows.isEmpty() && dataRows.size() > 13) {
								atomTxnId = dataRows.get(2);
								merchantTxnId = dataRows.get(6);
								bankRefNo = dataRows.get(13);
								refundAmount = dataRows.get(15);
								Double ReqRefundAmt = Double.parseDouble(refundAmount);
								int resp = bulkRefundSqlRepo.updateTxnMaster(atomTxnId, merchantTxnId, bankRefNo, "Ok");
								if (resp == 1) {
									String MerchantId = merchantrepo.findtransctionById(atomTxnId);
									String refundRequestId = GenerateRandom.randomAlphaNumeric(14);
									BulkRefund bulkrefund = new BulkRefund();
									bulkrefund.setMerchantId(MerchantId);
									bulkrefund.setTxnId(atomTxnId);
									bulkrefund.setRefundAmount(ReqRefundAmt);
									bulkrefund.setMerchantRefId(refundRequestId);
						//			JSONObject resp = ValidateTxnId(bulkrefund);
									JSONObject response = refundOldModule(bulkrefund);
									log.info("Response form updateTxn Master   " + response);
//									String refundResponse = RefundManager.initiateRefund(merchantTxnId, refundAmount);
									if (response.get("respMessage").equals("Refund initiated successfully")) {
										int resp1= bulkRefundSqlRepo.updateTxnMaster(atomTxnId, merchantTxnId, bankRefNo, "F");
									}
									String srno = String.valueOf(i);
									item.put(srno,response.get("respMessage"));
								} else {
									String srno = String.valueOf(i);
									item.put(srno, "Invalid Transaction Id");
								}
							} else {
								String srno = String.valueOf(i);
								item.put(srno, "Invalid number of columns");
							}
							array.put(item);
							js1.put("Status", "Successs");
							js1.put("Data", array);
						}
						
					} else {
						String Message= "No rows found!!!";
						js1.put("Status", "Error");
						js1.put("Message", Message);
					}
				} else {
					String Message= "Wrong File Format!!! File Format should be xlsx/xls";
					js1.put("Status", "Error");
					js1.put("Message", Message);
				}
			}
		} catch (Exception e) {
			String Message= "Error occurred while reading file" + e.getMessage();
			log.error(e.getMessage(), e);
			js1.put("Status", "Error");
			js1.put("Message", Message);
		}
		Msg=js1.toMap();
		return Msg;
	}
	public Map<String, Object> getRefundTransactionStatusListMerchant(String merchantId, String id, String txnId, String refundId, String fromDate, String toDate,
			String refundType, String refundStatus, String count, int pageNo, int Type, String searchBy) {
		Map<String, Object> Msg = null ;
		String strDate = "";
		String endDate = "";
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();	
		
		try {
			List<Map<String, Object>> resultdata = bulkRefundSqlRepo.refundTransactionStatusMerchant( merchantId, fromDate, toDate, id, txnId, refundId,  refundType, refundStatus, count, pageNo,  searchBy, Type );
		
			if(!resultdata.isEmpty()) {
				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", resultdata);
			}else {
				 js1.put("Status", "Error");
				 js1.put("Message", "No Data found");
				 js1.put("data", "");
			}
		}catch(Exception e) {
			 js1.put("Status", "Error");
			 js1.put("data", "Error Found");
			log.info("Refund Transaction Status ::::::::::::::::::{} "+e.getMessage());
		}

		Msg = js1.toMap();
		return Msg;
	}
	
	public JSONObject createSBIRefundZipFile(String refundCheckSumDataSBI, String manualFileDataSBI)
			throws IOException {
		log.info("refundData ::  "+refundCheckSumDataSBI + "manualFileDataSBI " + manualFileDataSBI);
		JSONObject fileName = null;
		
		String refundReconFileName = null,refundDatafileName= null;
		String underscore_constant ="_";
		
		File directory = new File(uploadDirectoryForDownloadSBI);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		log.info("directory::::::" + directory);
		for (File file : Objects.requireNonNull(directory.listFiles())) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		if (!manualFileDataSBI.isEmpty()) 
		{
			String merchant_code = SBIPGCode;
			String sbi_Refund_constant = "SBI_Refund";
			
			Date currentdate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY.hh.mm.ss");
			String formattedDate =  dateFormat.format(currentdate);
			refundReconFileName = merchant_code + underscore_constant + sbi_Refund_constant + underscore_constant + formattedDate + ".txt";
			
			
			// create the upload folder if not exists
			File folder = new File(uploadDirectoryForDownloadSBI);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File refundFile = new File(uploadDirectoryForDownloadSBI, refundReconFileName);
//			if (refundFile.exists()) {
//				refundFile.delete();
//			}
//			if (!refundFile.exists()) {
//				refundFile.mkdirs();
//			}
			FileWriter fos = new FileWriter(refundFile);
			fos.write(manualFileDataSBI);
			fos.close();

			log.info("File Name "+refundFile);
		}

		if (!refundCheckSumDataSBI.isEmpty()) 
		{
			String datatempfile = uploadDirectoryForDownloadSBI+"tempFolder" + "/";
			File dataFile1 = new File(datatempfile);
			if (!dataFile1.exists()) {
				dataFile1.mkdirs();
			}
			File dataFile = new File(datatempfile, "temp.txt");
			
//			if (dataFile.exists()) 
//			{
//				dataFile.delete();
//			}
//			if (!dataFile.exists()) {
//				dataFile.mkdir();
//			}
			
			FileWriter fosTemp = new FileWriter(dataFile);
			fosTemp.write(refundCheckSumDataSBI);
			fosTemp.close();
			
			String checkSumForData = this.getCheckSumForData(dataFile);
			String merchant_code = SBIPGCode;
			refundDatafileName = merchant_code + underscore_constant + checkSumForData + ".txt";
			
			File refundFile = new File(uploadDirectoryForDownloadSBI, refundDatafileName);
//			if (refundFile.exists()) {
//				refundFile.delete();
//			}
			FileWriter fos = new FileWriter(refundFile);
			fos.write(refundCheckSumDataSBI);
			fos.close();

			log.info("File Name "+refundFile);
			
			log.info("refundDatafileName "+refundDatafileName + "refundReconFileName " +refundReconFileName);
            String[] files = {refundDatafileName,refundReconFileName};

            log.info("files.length "+files.length);
            // Checks to see if the directory contains some files.
            Date currentdate = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY.hh.mm.ss");
			String formattedDate = dateFormat.format(currentdate);
			String ZipFileName = "Refund" + formattedDate + ".zip";
			log.info("zipFiles " + ZipFileName);
			
			log.info("files.length " + files.length);
			List<String> filess = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				log.info("fileNames:::::" + files[i]);
//				filess.add("D:/home/abc/" + fileNames.get(i));
				
				 File fileval = new File(uploadDirectoryForDownloadSBI);

					if (!fileval.exists()) {
						fileval.mkdirs();
					}
				filess.add(uploadDirectoryForDownloadSBI + files[i]);
			}
			
            if (files != null && files.length > 0) {
                // Call the zipFiles method for creating a zip stream.
              fileName= zipFiles1(filess, ZipFileName);
            }
            
		}
		
		return fileName;
	}
	
	public String getCheckSumForData(File file) throws IOException {

		MessageDigest md;
		StringBuffer hexString = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			FileInputStream fis = new FileInputStream(file);

			// Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;

			// Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				md.update(byteArray, 0, bytesCount);
			}
			;
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);

			}
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		}
		return hexString.toString();
	}
	
	public JSONObject zipFiles1(List<String> files, String zipName) {

		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		JSONObject js1 = new JSONObject();
		String DocRef = null;

		try {
			File dataFile = new File(uploadDirectoryForDownloadSBI+"tempFolder" + "/", "temp.txt");
			
			if (dataFile.exists()) 
			{
				dataFile.delete();
			}
//			String filedatatemp =uploadDirectoryForDownloadSBI+"tempFolder1" + "/";
//			File dataFile = new File(filedatatemp,"temp.txt");
			File fileval = new File(uploadDirectoryForDownloadSBIZIP);

			if (!fileval.exists()) {
				fileval.mkdirs();
			}
			
//			DocRef = uploadDirectoryForDownloadSBI + zipName;

			DocRef = uploadDirectoryForDownloadSBIZIP + zipName;

			
//              fos = new FileOutputStream("D:/home/chargebackDocumentAdmin/"+zipName);
			fos = new FileOutputStream(uploadDirectoryForDownloadSBIZIP  + zipName);

			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			for (String filePath : files) {
				File input = new File(filePath);
				fis = new FileInputStream(input);
				ZipEntry ze = new ZipEntry(input.getName());
				log.info("Zipping the file: " + input.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
			}
			zipOut.close();
			log.info("Done... Zipped the files...");

			// remove the files from the directory

//              File directory = new File("D:/home/abc/");
			
			
//			File directory = new File(uploadDirectoryForDownloadSBI + "tempFolder" + "/");
//			if (!directory.exists()) {
//				directory.mkdirs();
//			}
//			log.info("directory::::::" + directory);
//
//			for (File file : Objects.requireNonNull(directory.listFiles())) {
//				if (!file.isDirectory()) {
//					file.delete();
//				}
//			}


			js1.put("Status", "Success");
			js1.put("Message", "File Created and Uploaded ");
			js1.put("FilePath", DocRef);
			js1.put("FileName", zipName);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("Message", "File Not Created " + e.getMessage());
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("Message", "File Not Created " + e.getMessage());
		
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
		log.info("js:::::::::" + js1.toString());
		return js1;
	}

}
