package com.crm.Controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.PayoutRepository;
import com.crm.dto.PayoutStatusdto;
import com.crm.services.PayoutServiceV1;

@RestController
public class PayoutGenerateControllerV1 {
	
	private static Logger log = LoggerFactory.getLogger(PayoutGenerateControllerV1.class);

	String downloadPayoutFile = "/home/KYCDOCUMENTS/PayoutGenerate1/";
	String fileLocationBulk = "/home/KYCDOCUMENTS/PayoutSettlement/";
	
	@Autowired
	private PayoutRepository payoutRepository;

	@Autowired
	private PayoutServiceV1 payoutService;
	
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "payoutGenerateV1", produces = "application/json")
	public Map<String, Object> payoutGenerate(@RequestBody String jsonBody) {
		
		Map<String, Object> resultData = null;
		JSONObject response = new JSONObject();
		JSONObject js = new JSONObject(jsonBody);

		String payoutEscrow = js.getString("payoutEscrow");
		String transactionDate = js.getString("txn_Date");
		String username = js.getString("username");
		
		log.info("payoutEscrow====================================" + payoutEscrow);
		log.info("transactionDate====================================" + transactionDate);
		log.info("username====================================" + username);
		
		try{
			

			String Is_ForceRecon = "P";

			String i = payoutRepository.ExceptionList(Is_ForceRecon);
			log.info("------------------------------Count List------" + i);
			
			if (i == null || i.equalsIgnoreCase("0") || i.equalsIgnoreCase("null")) {

				java.sql.Date date = java.sql.Date.valueOf(transactionDate);

				List<Object> findByProperty = payoutRepository.findByProperty(date, payoutEscrow);
				log.info("findByProperty::::::::" + findByProperty.size());
				
				if (payoutEscrow.equalsIgnoreCase("HDFC")) {
					
					
					if (findByProperty.size() > 0) {
						
						String statusCheck = payoutRepository.checkStatusVal(date, payoutEscrow);
						log.info("statusCheck::::::::::" + statusCheck);

						if (statusCheck.equalsIgnoreCase("false")) {
							response.put("ResponseMessage", "You can download payout files after some time.");

							resultData = response.toMap();
						} else {
							// data exsit then get file name and location
							resultData = payoutService.getPayoutStatusData(date, payoutEscrow);
						}

					}
					else {
						// else for the = 0
						log.info("insert a entry in payoutStatus::::");
						int status = 0;
						
						List<Object[]> savedPayoutStatus = payoutRepository.saveData(date, payoutEscrow, status);
						log.info("PayoutService.java  ::::  Started ::");
						PayoutStatusdto payoutStatusdto = new PayoutStatusdto();
						
						for (Object[] obj : savedPayoutStatus) {

							payoutStatusdto.setId((Integer) obj[0]);
							log.info("PayoutService.java  ::::  Started :0:" + obj[0]);
							log.info("PayoutService.java  ::::  Started :1:" + obj[1]);
							log.info("PayoutService.java  ::::  Started :2:" + obj[2]);
							log.info("PayoutService.java  ::::  Started :3:" + obj[3]);
							log.info("PayoutService.java  ::::  Started :4:" + obj[4]);
							log.info("PayoutService.java  ::::  Started :5:" + obj[5]);

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String strDate = sdf.format(obj[1]);

							java.util.Date abc = sdf.parse(strDate);
							java.sql.Date sqlStartDate = new java.sql.Date(abc.getTime());

							log.info("PayoutService.java  ::::  Started :5:::" + abc);
							log.info("PayoutService.java  ::::  Started :5:::" + sqlStartDate);
							payoutStatusdto.setDateTime(sqlStartDate);

							payoutStatusdto.setTransactionwisePayoutPath((String) obj[2]);

							payoutStatusdto.setMerchantwisePayoutPath((String) obj[3]);
							payoutStatusdto.setStatus((boolean) obj[4]);
							payoutStatusdto.setPayoutEscrow((String) obj[5]);
							
						
						}
						if (payoutStatusdto.getId() > 0) {
							String filePath = downloadPayoutFile;
							log.info("filePath::::" + filePath);
						JSONObject	responseSave = payoutService.payoutProcess(username, filePath, payoutEscrow, payoutStatusdto);
						log.info("responseSave::::::"+responseSave);
						
						response.put("ResponseMessage", "You can download payout files after some time.");
						resultData = response.toMap();
						log.info("resultData::::::"+resultData);
						}
						else {
							response.put("stauts", "Failed");
							response.put("error", "Error Occure while processing you request.Try after some time.");
							resultData = response.toMap();
						}
//						resultData = save(date, payoutEscrow, status, username);
						log.info("resultData::::::" + resultData);

					}
				}
				// yes bank
				else {
					if (findByProperty.size() > 0) {
						String statusCheck = payoutRepository.checkStatusVal(date, payoutEscrow);
						log.info("statusCheck::::::::::" + statusCheck);

						if (statusCheck.equalsIgnoreCase("false")) {
							response.put("ResponseMessage", "You can download payout files after some time.");

							resultData = response.toMap();
						} else {
							// data exsit then get file name and location
							resultData = payoutService.getPayoutStatusData(date, payoutEscrow);
						}
					}
					else {
						log.info("insert a entry in YBL payoutStatus::::");

						log.info("insert a entry in payoutStatus::::");
						int status = 0;

						
						
						List<Object[]> savedPayoutStatus = payoutRepository.saveData(date, payoutEscrow, status);
						log.info("PayoutService.java  ::::  Started ::");
						PayoutStatusdto payoutStatusdto = new PayoutStatusdto();
						
						for (Object[] obj : savedPayoutStatus) {

							payoutStatusdto.setId((Integer) obj[0]);
							log.info("PayoutService.java  ::::  Started :0:" + obj[0]);
							log.info("PayoutService.java  ::::  Started :1:" + obj[1]);
							log.info("PayoutService.java  ::::  Started :2:" + obj[2]);
							log.info("PayoutService.java  ::::  Started :3:" + obj[3]);
							log.info("PayoutService.java  ::::  Started :4:" + obj[4]);
							log.info("PayoutService.java  ::::  Started :5:" + obj[5]);

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String strDate = sdf.format(obj[1]);

							java.util.Date abc = sdf.parse(strDate);
							java.sql.Date sqlStartDate = new java.sql.Date(abc.getTime());

							log.info("PayoutService.java  ::::  Started :5:::" + abc);
							log.info("PayoutService.java  ::::  Started :5:::" + sqlStartDate);
							payoutStatusdto.setDateTime(sqlStartDate);

							payoutStatusdto.setTransactionwisePayoutPath((String) obj[2]);

							payoutStatusdto.setMerchantwisePayoutPath((String) obj[3]);
							payoutStatusdto.setStatus((boolean) obj[4]);
							payoutStatusdto.setPayoutEscrow((String) obj[5]);
							
						
						}
						if (payoutStatusdto.getId() > 0) {
							String filePath = downloadPayoutFile;
							log.info("filePath::::" + filePath);
						JSONObject	responseSave = payoutService.payoutProcess(username, filePath, payoutEscrow, payoutStatusdto);
						log.info("responseSave::::::"+responseSave);
						
						response.put("ResponseMessage", "You can download payout files after some time.");
						resultData = response.toMap();
						log.info("resultData::::::"+resultData);
						}
						else {
							response.put("stauts", "Failed");
							response.put("error", "Error Occure while processing you request.Try after some time.");
							resultData = response.toMap();
						}
//						resultData = save(date, payoutEscrow, status, username);
						log.info("resultData::::::" + resultData);

					}
				}
				
			}
			else {
				// i else
				response.put("stauts", "Failed");
				response.put("error", "Please Clear The Exception");

				resultData = (Map<String, Object>) resultData.put("Data", response);
			}
			
		}
		catch(Exception e) {
			log.info("Exception::::::::"+e);
			e.printStackTrace();
		}
		
		
		return resultData;
	}
	

	//settlement api for single update
	
	// Code for the settlement - single entry update code
		@CrossOrigin(origins = { "http://localhost:4200" })
		@PostMapping(value = "payoutSettlementUTRV1", produces = "application/json")
		public String payoutSettlementUTR(@RequestBody String jsonBody) {

			JSONObject js = new JSONObject(jsonBody);
			JSONObject response = new JSONObject();

			String utrNo = js.getString("utrNo");
			String payoutRefId = js.getString("payoutRefId");
			String userName = js.getString("userName");

			log.info("utrNo:::::" + utrNo + ":::payoutRefId::" + payoutRefId + "::::userName:::::" + userName);

			try {
				String updateURTNo = payoutService.updateURTNo(utrNo, payoutRefId, userName);

				log.info("updateURTNo:::::::" + updateURTNo);

				if (updateURTNo.equalsIgnoreCase("success")) {
					response.put("Status", "Success");
					response.put("Message", "UTR Number Updated Successfully.");

				} else {
					response.put("Status", "Failed");
					response.put("Message", "Failed to update UTR Number.");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response.toString();

		}
	
		// bulk file upload
		@CrossOrigin(origins = { "http://localhost:4200" })
		@PostMapping(value = "bulkUploadSettlementFileV1", produces = "application/json")
		public String bulkUploadSettlementV1(@RequestParam MultipartFile file, String userName) {
			int i = 1;
			int N = 1;
			JSONObject response = new JSONObject();
			StringBuilder ab = new StringBuilder();

			try {

				String fileName = file.getOriginalFilename();
				log.info("fileName:::::::::::::" + fileName);

//				String fileLocationBulk = "D:/home/PayoutSettelement/";

				File fileval = new File(fileLocationBulk);

				if (!fileval.exists()) {
					fileval.mkdirs();
				}

				File filePath = new File(fileLocationBulk + fileName);
				log.info("filePath:::::::" + filePath);

				payoutService.storeFile(file, fileLocationBulk);

				File newFile = new File(fileLocationBulk + fileName);
				log.info("newFile::::::::::" + newFile);
				if (newFile.length() == 0) {

					log.info("File is empty ...");
					response.put("Status", "Failed");
					response.put("Message", "File Can Not Empty.");
					ab.append(response.toString() + ",");

				} else {

					log.info("File is not empty ...");

					FileInputStream fstream = new FileInputStream(fileLocationBulk + fileName);
					DataInputStream in = new DataInputStream(fstream);
					log.info("in::::::::" + in);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));

					String line = null;

					try {
						while ((line = br.readLine()) != null) {
							char onlyCommas = 1;

							for (int j = 0; j < line.length(); j++) {
								onlyCommas = line.charAt(j);
								if (onlyCommas == ',') {
									N++;
								}
							}

							N = N - 1;
							if (N < 6) {
								log.info("I is less than 8 value");

								response.put("Status", "Failed");
								response.put("Message", "File Format wrong.");

								ab.append(response.toString() + ",");

							} else {
								log.info("inside the else --> ");

								String tmp[] = line.split(",");
								String PgRefrenceNumber = tmp[7].trim();
								String UTRNumber = tmp[10].trim();

								if (PgRefrenceNumber.equalsIgnoreCase("") || UTRNumber.equalsIgnoreCase("")) {

									log.info(" Enter in else");
									log.info("date>>>>" + i + ">>>>>Blank>>>>>>>> " + PgRefrenceNumber
											+ "<><><><><><><<><><<" + UTRNumber);

									response.put("Status", "Failed");

									if (PgRefrenceNumber.equalsIgnoreCase("") && !UTRNumber.equalsIgnoreCase("")) {
										response.put("Message", "Please Check The PgRefrenceNumber.");

									} else if (!PgRefrenceNumber.equalsIgnoreCase("") && UTRNumber.equalsIgnoreCase("")) {
										response.put("Message", "Please Check The UTRNumber.");

									} else {
										response.put("Message", "Please Check The PgRefrenceNumber Or UTRNumber.");

									}
									response.put("PgRefrenceNumber", PgRefrenceNumber);
									response.put("UTRNumber", UTRNumber);
									ab.append(response.toString() + ",");

									i++;

								}

								else {
									log.info("date>>>>>>>>>>>>> " + PgRefrenceNumber + "<><><><><><><<><><<" + UTRNumber);

									String updateURTNo = payoutService.updateURTNo(UTRNumber, PgRefrenceNumber, userName);
									log.info("updateURTNo:::::::" + updateURTNo);

									if (updateURTNo.equalsIgnoreCase("success")) {
										response.put("Status", "Success");
										response.put("Message", "Bulk UTR Number Updated Successfully.");
										response.put("PgRefrenceNumber", PgRefrenceNumber);
										response.put("UTRNumber", UTRNumber);
										ab.append(response.toString() + ",");

									} else {
										response.put("Status", "Failed");
										response.put("Message", "Failed to update UTR Number.");
										response.put("PgRefrenceNumber", PgRefrenceNumber);
										response.put("UTRNumber", UTRNumber);
										ab.append(response.toString() + ",");

									}
								}
							}

						}
					} catch (Exception ex) {
						log.info("ex::::::::::::" + ex);
					}

				}

			} catch (Exception e) {

				log.info("Exception::::::::::" + e);
			}
			log.info("response::::::::" + response);

			String response1 = ab.toString();
			response1 = response1.substring(0, response1.length() - 1);
			log.info("response1::::::::" + response1);
			String result = "[" + response1.toString() + "]";

//			String result = "[" + ab.toString() + "]";
			return result;

		}

	
}
