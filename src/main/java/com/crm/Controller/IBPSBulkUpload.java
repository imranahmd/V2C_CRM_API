package com.crm.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.lBPSRepo;
import com.crm.ServiceDaoImpl.IBPSSMS;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.BulkInvoiceList;
import com.crm.dto.BulkMdrList;
import com.crm.dto.BulkXlsInvoiceDto;
import com.crm.dto.bulkXlxInvoice;
import com.crm.helper.MdrBulkCsvHelper;
import com.crm.helper.Response;
import com.crm.helper.lbpsBulkHelper;
import com.crm.services.UserDetailsui;
import com.crm.services.lBPSService;
import com.crm.util.GenerateRandom;
import com.external.api.BitlyAPI;
import com.google.gson.Gson;
import com.ibm.icu.util.Calendar;

import freemarker.template.Configuration;
import freemarker.template.Template;

@RestController
public class IBPSBulkUpload {
	private static Logger log = LoggerFactory.getLogger(IBPSBulkUpload.class);
	private final String OPTIONAL_TIME_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])( (0[01-9]|[1][0-4]|[2][0-3]):(0[01-9]|[12345][0-9]):(0[01-9]|[12345][0-9]))?";
	@Autowired
	private lBPSService lbpsService;

	@Autowired
	private lBPSRepo iBPSRepo;

	@Autowired
	private Configuration freemarkerConfig;

	@Value("${lbps.longURL}")
	private String longURL1;

	@Value("${emailTemplatelbps}")
	private String emailTemplatelbps;

	@Value("${lbps.smtpPass}")
	private String MailPass;

	@Value("${lbps.mailFrom}")
	private String LBPS_mailFrom;

	@CrossOrigin(origins = { "http://localhost:4200"})
	 @PostMapping(value = "/CreateBulkInvoice", produces = "application/json")
	public ResponseEntity<Object> CreateBulkInvoiceXlx(@RequestParam MultipartFile file, String MerchantId,
			String status) {

		 Response res = new Response();
	        Gson abcv = new Gson();
		
		String message = "";
		log.info("MerchantId:::::::" + MerchantId + "::::::status::::" + status);
		List<BulkInvoiceList> listofbulkInvoice = null;
		log.info("inside the CreateBulkInvoice:::::");
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		if (lbpsBulkHelper.hasXlsFormat(file)) {
			try {
				
				username = userDetails.optString("Username");
				ipAddress = userDetails.optString("ipAddress");

				saveInvoice(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				 listofbulkInvoice = createInvoicerBulkXls(MerchantId, status);
				log.info(":::::::::sucess:::::::::");
				
				res.setStatus(true);
                res.setMessage(message);
                res.setResult(listofbulkInvoice);
               // log.info("abcv:::::"+abcv.toJson(res));
                
                secondCon.insertIntoSecondSchema(username, "Create Bulk Invoice", file.toString()+"/"+MerchantId+"/"+status, res.toString(), ipAddress);
				
			} catch (Exception e) {
				
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
										
				secondCon.insertIntoSecondSchema(username, "Create Bulk Invoice", file.toString()+"/"+MerchantId+"/"+status, stackTraceString, ipAddress);
				
				// log.info("e::::::",e);
	                message = "Could not upload the file: " + file.getOriginalFilename() + "! " + e.getMessage();
	                res.setStatus(false);
	                res.setMessage(message);
		
			}
		}

		return ResponseEntity.ok(abcv.toJson(res));

	}

	
	
	
	
	public void saveInvoice(MultipartFile file) {
		try {
			log.info("date formmate");
			// JdbcTemplate.execute("truncate tbl_invoice_xlx");

			// ---TRUNCATE TABLE tbl_invoice_xlx;
			List<BulkXlsInvoiceDto> BulkDto = lbpsBulkHelper.xlsBulkData(file.getInputStream());
			log.info("date formmate1");
			try {
				lbpsService.truncateTblinvoicexlx();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (BulkXlsInvoiceDto obj : BulkDto) {
				bulkXlxInvoice bUlkXlxInvoice = new bulkXlxInvoice();
				bUlkXlxInvoice.setInvoiceNumber(obj.getInvoiceNumber());
				bUlkXlxInvoice.setAmount(Double.toString(obj.getAmount()));

				log.info("date formmate::::::::" + String.valueOf(obj.getValidUpTo()));
				String datstr = String.valueOf(obj.getValidUpTo());
				DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				java.util.Date date = formatter.parse(datstr);
				DateFormat df = new SimpleDateFormat("dd-MM-YYYY");
				String outputDate = df.format(date);
				System.out.println(":::::outputDate::::" + outputDate);

				bUlkXlxInvoice.setValidUpTo(outputDate);
				bUlkXlxInvoice.setRemarks(obj.getRemarks());
				bUlkXlxInvoice.setName(obj.getName());
				bUlkXlxInvoice.setEmailId(obj.getEmailId());
				bUlkXlxInvoice.setMobile(obj.getMobile());
				bUlkXlxInvoice.setEmailNotification(obj.getEmailNotification());
				bUlkXlxInvoice.setsMSNotification(obj.getsMSNotification());

				lbpsService.insertData(bUlkXlxInvoice);
			}

			log.info("BulkDto::::::" + BulkDto);
		} catch (IOException | ParseException e) {
			throw new RuntimeException("fail to store Xlsx data: " + e.getMessage());
		}
	}

	public List<BulkInvoiceList> createInvoicerBulkXls(String merchatId, String status) {
		try {
			String invoiceSuccess = "";
			String invoiceverifedStatus = "";
			List<bulkXlxInvoice> invoices = new ArrayList<>();

			List<Object[]> invoiceBulkData = iBPSRepo.getXlsRedordIBPS();

			for (Object[] obj : invoiceBulkData) {
				bulkXlxInvoice bUlkXlxInvoice = new bulkXlxInvoice();
				bUlkXlxInvoice.setId((int) obj[0]);
				bUlkXlxInvoice.setInvoiceNumber((String) obj[1]);
				bUlkXlxInvoice.setAmount((String) obj[2]);
				bUlkXlxInvoice.setValidUpTo((String) obj[3]);
				bUlkXlxInvoice.setRemarks((String) obj[4]);
				bUlkXlxInvoice.setName((String) obj[5]);
				bUlkXlxInvoice.setEmailId((String) obj[6]);
				bUlkXlxInvoice.setMobile(Long.valueOf((String) obj[7]));
				bUlkXlxInvoice.setEmailNotification((String) obj[8]);
				bUlkXlxInvoice.setsMSNotification((String) obj[9]);

				invoices.add(bUlkXlxInvoice);
			}

			for (bulkXlxInvoice obj : invoices) {

				String errorInvoiceNo = "";
				String errorAmount = "";
				String errorValidUpTo = "";
				String errorRemarks = "";
				String errorName = "";
				String errorEailId = "";
				String errorMobileNo = "";
				String errorEmailNotify = "";
				String errorSmsNotify = "";
				String errorDuplicate = "";

				String id = String.valueOf(obj.getId());
				String invoceNo = (obj.getInvoiceNumber());
				String amountV = (obj.getAmount());
				String validUpto = (obj.getValidUpTo());
				String remarks = (obj.getRemarks());
				String name = (obj.getName());
				String emailID = (obj.getEmailId());
				String mobileNo = (String.valueOf(obj.getMobile()));
				String emailNotify = (obj.getEmailNotification());
				String smsNotify = (obj.getsMSNotification());

				String petternInvoceNo = "^[a-zA-Z0-9 ]*$";
				String petternAmount = "^[+-]?(\\d*\\.)?\\d+$";
				String petternValidUpTo = "\\d{2}-\\d{2}-\\d{4}";
				String petternRemarks = "";
				String petternName = "^[a-zA-Z0-9 ]*$";
				String petternEmailId = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
				String petternMobileNo = "^[0-9]{10}$";

				boolean resinvoceNo = invoceNo.matches(petternInvoceNo);
				boolean resAmount = amountV.matches(petternAmount);
				boolean resValidupTo = validUpto.matches(petternValidUpTo);
				boolean resname = name.matches(petternName);
				boolean resemailID = emailID.matches(petternEmailId);
				boolean resmobileNo = mobileNo.matches(petternMobileNo);
				boolean reemailNotify = emailNotify.equalsIgnoreCase("Y") || emailNotify.equalsIgnoreCase("N");

				boolean resmsNotify = smsNotify.equalsIgnoreCase("Y") || smsNotify.equalsIgnoreCase("N");

				if (resinvoceNo == false) {
					errorInvoiceNo = " Invoice Number not Proper, ";
				} else if (resinvoceNo == true) {
					String iInvoiceNo = invoceNo;
				}

				if (resAmount == false) {
					errorAmount = " Amount not Proper, ";
				} else if (resAmount == true) {
					String iAmount = amountV;
				}

				if (resValidupTo == false) {
					errorValidUpTo = " ValidUpTo not Proper, ";
				} else if (resValidupTo == true) {

					
					 DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				        Date date0 = new Date();
				        String currentDate = sdf.format(date0);
				        
				        
				        Date date1 = sdf.parse(validUpto);
				        Date date2 = sdf.parse(currentDate);
				        System.out.println("date1::::::"+date1);
				        System.out.println("date2::::::::"+date2);
					
					if ((date1.after(date2)) || (date1.equals(date2))) {
					
						log.info("Date 1 comes after Date 2");
						String ivalidUpto = validUpto;
					} 
					else {
						log.info("Please Write the ValidUpTo Date Equal or Greater Then to Current Date ! ");
						errorValidUpTo = " Please Write the ValidUpTo Date Equal or Greater Then to Current Date, ";
					}

				
				}

				if (resname == false) {
					errorName = " Name not Proper, ";
				} else if (resname == true) {
					String iname = name;
				}

				if (resemailID == false) {
					errorEailId = " Email Id not Proper, ";
				} else if (resemailID == true) {
					String iemailID = emailID;
				}

				if (resmobileNo == false) {
					errorMobileNo = " Mobile Number not Proper, ";
				} else if (resmobileNo == true) {
					String imobileNo = mobileNo;
				}

				if (reemailNotify == false) {

					errorEmailNotify = " Please Write (Y or N) For the Email Notification, ";
				} else if (reemailNotify == true) {

					if (emailNotify.equalsIgnoreCase("Y")) {

						emailNotify = "1";
					} else if (emailNotify.equalsIgnoreCase("N")) {
						emailNotify = "0";
					}
					String iemailNotify = emailNotify;
				}

				if (resmsNotify == false) {
					errorSmsNotify = " Please Write (Y or N) For the SMS Notification, ";
				} else if (resmsNotify == true) {

					if (smsNotify.equalsIgnoreCase("Y")) {

						smsNotify = "1";
					} else if (smsNotify.equalsIgnoreCase("N")) {
						smsNotify = "0";
					}
					String ismsNotify = smsNotify;
				}

				boolean findinvoiceNoDup = iBPSRepo.findinvoiceNoDupBymerchInvocN(merchatId, invoceNo);
				if (findinvoiceNoDup == true) {
					errorDuplicate = " Duplicate Records Found for Invoice Number " + invoceNo;
					invoiceSuccess = "";
				} else if (errorInvoiceNo != "" || errorAmount != "" || errorValidUpTo != "" || errorName != ""
						|| errorEailId != "" || errorMobileNo != "" || errorEmailNotify != "" || errorSmsNotify != "") {
					invoiceSuccess = "";
				} else {

					String IBPSBodyText = "";
					String emailSubject = "";
					// get merchnt data from mstmerchant table
					String merchantData = lbpsService.getMerchantDetails(merchatId);
//					log.info("merchantData::::::::::::" + merchantData);

					JSONArray arrayjson = new JSONArray(merchantData);
//				log.info("arrayjson::::::::::::" + arrayjson);
					JSONObject merchatData = arrayjson.getJSONObject(0);
//				log.info("object:::::" + obj);
					log.info("<<--Merchant Name >> " + merchatData.getString("merchant_name"));
					String randomString = GenerateRandom.randomAlphaNumericIBPS(10);
					String payment_link_id = "lbp_" + randomString;
					String longURL = longURL1 + "?payLinkId=" + payment_link_id;
					URL url = new URL(longURL);
					log.info(">>>>url address full  " + url);
					BitlyAPI bitly = new BitlyAPI();
					bitly.setLongurl(longURL);
					String shortURL = bitly.getShortenUrl();

					// insert data in invoice table
					String result = lbpsService.createInvoiceByXls(invoceNo, merchatId, amountV, validUpto, remarks,
							name, emailID, mobileNo, shortURL, status, merchatId, merchatId, payment_link_id, "");
					log.info("result>>> " + result);

					boolean isOtherMerchant = true;

					if (result.equals("success")) {

						if (emailNotify == "") {
							emailNotify = "N";
						}
						if (emailNotify.equals("1")) {
							emailNotify = "Y";
						}
						if (emailNotify.equals("0")) {
							emailNotify = "N";
						}
						if (smsNotify == "") {
							smsNotify = "N";
						}
						if (smsNotify.equals("1")) {
							smsNotify = "Y";
						}
						if (smsNotify.equals("0")) {
							smsNotify = "N";
						}

						log.info("emailNotify::" + emailNotify + "::::smsNotify:::" + smsNotify);
						if (lbpsService.notificationType(merchatData, smsNotify, emailNotify).equalsIgnoreCase("SMS")) {

							log.info("only sms::::::");
							sendSms(obj, merchatData, longURL);
						}
						if (lbpsService.notificationType(merchatData, smsNotify, emailNotify)
								.equalsIgnoreCase("EMAIL")) {
							sendEmail(merchatData, obj, emailSubject, IBPSBodyText, isOtherMerchant, payment_link_id);
							log.info("only email::::::");
						}
						if (lbpsService.notificationType(merchatData, smsNotify, emailNotify)
								.equalsIgnoreCase("BOTH")) {
							log.info("both sms and email:::::::::");
							sendSms(obj, merchatData, longURL);
							sendEmail(merchatData, obj, emailSubject, IBPSBodyText, isOtherMerchant, payment_link_id);
						}

					}

					invoiceSuccess = "Invoice Records Save Successfully !  ";

				}
				invoiceverifedStatus = errorInvoiceNo + errorAmount + errorValidUpTo + errorName + errorEailId
						+ errorMobileNo + errorEmailNotify + errorSmsNotify + errorDuplicate + invoiceSuccess;
				String uploadStatus = "";
				if (invoiceSuccess != "") {
					log.info("inside the success:::::::::::: ");
					//invoiceverifedStatus = "Invoice Uploaded Successfully !";
					uploadStatus = "Success";
					lbpsService.updateInvoiceVerification(invoiceverifedStatus, uploadStatus, obj.getId());
				} else {
					uploadStatus = "Failed";
					lbpsService.updateInvoiceVerification(invoiceverifedStatus, uploadStatus, obj.getId());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BulkInvoiceList> bulktablelist = new ArrayList<>();
		List<Object[]> mdrecords = iBPSRepo.invoicelistAllRecords();
		for (Object[] obj : mdrecords) {
			BulkInvoiceList invoiceBulkString = new BulkInvoiceList();
			invoiceBulkString.setInvoiceNumber((String) obj[1]);
			invoiceBulkString.setSrNo(String.valueOf(obj[0]));
			invoiceBulkString.setRemarks((String) obj[11]);
			invoiceBulkString.setUploadStatus((String) obj[10]);
			bulktablelist.add(invoiceBulkString);

		}

		return bulktablelist;

	}

	public void sendSms(bulkXlxInvoice ibpsData, JSONObject merchantData, String longURL1)
			throws UnsupportedEncodingException {
		String custName = ibpsData.getName();
		String AmountV = ibpsData.getAmount();
		BigDecimal isValidAmount = new BigDecimal(AmountV);
		Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		String convertedAmount = format.format(isValidAmount);
		convertedAmount = new String(convertedAmount.getBytes(), "UTF-8").replaceAll("[^\\x00-\\x7F]", "").toUpperCase()
				.replace("RS", "");
		String longURL = longURL1;
		BitlyAPI bitly = new BitlyAPI();
//		jyoti------------------------------------------
		log.info("resend payment::::" + longURL1); // resend
		String lss = longURL;
		int len = lss.length();
		String lssss = lss.substring(15);
		int lene = lssss.length();

		log.info("len size ---------------------------" + len + " " + lssss + " " + lene); // create);

		bitly.setLongurl(longURL);
		if (lene > 7) {
			IBPSSMS sms = new IBPSSMS();
			String smsText = "Dear " + custName + ", " + merchantData.getString("business_name").toUpperCase()
					+ " has requested payment for INR " + convertedAmount + ". " + "You can pay through this link: "
					+ bitly.getShortenUrl() + ". Team PAYFI.";

			log.info("smsText>>> " + smsText);
			log.info("Sending SMS...");
			try {

				long contactNo = ibpsData.getMobile();
				sms.sendSMS(smsText, String.valueOf(contactNo));
			} catch (Exception e) {
				log.info("SMS could not send :: " + e.getMessage());
			}
			log.info("Sms sent successfully");
		} else {
			log.info("--------------------------1");
			IBPSSMS sms = new IBPSSMS();
			String smsText = "Dear " + custName + ", " + merchantData.getString("business_name").toUpperCase()
					+ " has requested payment for INR " + convertedAmount + ". " + "You can pay through this link: "
					+ longURL + ". Team PAY.";

			log.info("smsText>>> " + smsText);
			log.info("Sending SMS...");

			try {
				long contactNo = ibpsData.getMobile();
				sms.sendSMS(smsText, String.valueOf(contactNo));
			} catch (Exception e) {
				log.info("SMS could not send :: " + e.getMessage());
			}
			log.info("Sms sent successfully");

		}
	}

	public void sendEmail(JSONObject mstMerchant, bulkXlxInvoice ibpsForm, String emailSubject, String emailTextBody,
			boolean isOtherMerchant, String payment_link_id) {
		log.info("inside sendEmail()");
		int status = 0;
		// GeneralService generalService = new GeneralServiceImpl();
		log.info("Sending an email...");
		if (isOtherMerchant) {
			emailSubject = "Payment Request from " + mstMerchant.getString("business_name").toUpperCase();
			String merchName = mstMerchant.getString("business_name").toUpperCase();
			Map<String, String> valuesMap = getValueMap(ibpsForm, mstMerchant.getString("MerchantId"), merchName,
					payment_link_id);
			emailTextBody = getBodyText(valuesMap);
		}
		status = sendMail(ibpsForm.getEmailId(), emailSubject, ibpsForm.getEmailId(), emailTextBody);
		if (status == 1) {
			log.info("Email send successfully");
		} else {
			log.info("Unable to send email");
		}
		log.info("End of sendEmail()");
	}

	public HashMap<String, String> getValueMap(bulkXlxInvoice ibpsForm, String merchantId, String merchName,
			String payment_link_id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String AmountV = ibpsForm.getAmount();
		log.info("AmountV:::::" + AmountV);

		double dk = Double.valueOf(AmountV);
		log.info("AmountV:::::" + AmountV);
		DecimalFormat convertedAmount = new DecimalFormat("#.00");
		log.info("convertedAmount:::::" + convertedAmount.format(dk));

		resultMap.put("merName", merchName);
		resultMap.put("amount", convertedAmount.format(dk));
		resultMap.put("custName", ibpsForm.getName());
		resultMap.put("remarks", ibpsForm.getRemarks());
		resultMap.put("invoiceNo", ibpsForm.getInvoiceNumber());
		resultMap.put("expiryDate", getOrdinalDate(ibpsForm.getValidUpTo()));
		BitlyAPI bitly = new BitlyAPI();
		String payLink = longURL1 + "?payLinkId=" + payment_link_id;
		bitly.setLongurl(payLink);
		resultMap.put("paymentUrl", bitly.getShortenUrl());
		String pattern = "MM/dd/yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		Date today = Calendar.getInstance().getTime();
		String todayAsString = df.format(today);
		resultMap.put("timeStamp", todayAsString);
		resultMap.put("onePLogo", "https://domain/Merchant/assets/images/PAY.png");
		resultMap.put("twitterLogo", "https://domain/Merchant/assets/images/twitter.png");
		resultMap.put("linkedInLogo", "https://domain/Merchant/assets/images/linkedin.png");

		
		return resultMap;
	}

	public String getOrdinalDate(String dateToConvert) {
		log.info("Inside getOrdinalDate()");

		if (dateToConvert.matches(OPTIONAL_TIME_PATTERN)) {
			dateToConvert = getConvertedDate(dateToConvert, true);
		}
		if (dateToConvert.contains(":")) {
			dateToConvert = dateToConvert.substring(0, dateToConvert.indexOf(" ")).trim();
		}
		DateTimeFormatter dayOfMonthFormatter = getOrdinalDateFormatter();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String convertedDate = LocalDate.parse(dateToConvert, formatter).format(dayOfMonthFormatter);
		log.info("convertedDate: {}", convertedDate);
		return convertedDate;
	}

	private final String TIME_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])( (0[01-9]|[1][0-4]|[2][0-3]):(0[01-9]|[12345][0-9]):(0[01-9]|[12345][0-9]))";

	public String getConvertedDate(String dateString, boolean reverseDate) {
		try {
			if (!dateString.matches(TIME_PATTERN)) {
				dateString += " 23:59:59";
			}
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String convertedDate = "";
			if (reverseDate) {
				date = format1.parse(dateString);
				convertedDate = format2.format(date);
			} else {
				date = format2.parse(dateString);
				convertedDate = format1.format(date);
			}
			return convertedDate;
		} catch (Exception e) {
			log.error("Error occured inside getConvertedDate()", e);
		}
		return null;
	}

	public static DateTimeFormatter getOrdinalDateFormatter() {
		HashMap<Long, String> ordinalNumbers = new HashMap<>(42);
		ordinalNumbers.put(1L, "1st");
		ordinalNumbers.put(2L, "2nd");
		ordinalNumbers.put(3L, "3rd");
		ordinalNumbers.put(21L, "21st");
		ordinalNumbers.put(22L, "22nd");
		ordinalNumbers.put(23L, "23rd");
		ordinalNumbers.put(31L, "31st");
		for (long d = 1; d <= 31; d++) {
			ordinalNumbers.putIfAbsent(d, "" + d + "th");
		}
		DateTimeFormatter dayOfMonthFormatter = new DateTimeFormatterBuilder()
				.appendText(ChronoField.DAY_OF_MONTH, ordinalNumbers).appendPattern(" MMMM, yyyy").toFormatter();
		return dayOfMonthFormatter;
	}

	public String getBodyText(Map<String, String> valuesMap) {
		log.info("Inside getBodyText()");
		try {
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
			// cfg.setServletContextForTemplateLoading(servletContext,
			// "/Templates/EmailTemplates/");
			cfg.setDefaultEncoding("UTF-8");
			Template template;
			template = freemarkerConfig.getTemplate(emailTemplatelbps);
			// template = cfg.getTemplate("IbpsPaymentTemplate.ftl");
			StringWriter out = new StringWriter();
			template.process(valuesMap, out);
			log.info("Template text::" + out.getBuffer().toString());
			out.flush();
			log.info("Successfully got template text");
			return out.getBuffer().toString();
		} catch (Exception e) {
			log.error("Error ocured inside getBodyText()::" + e.getMessage());
		}
		return null;
	}

	public int sendMail(String to, String subject, String from, String bodyText) {
		System.out.println("INsisde Sendmail");
		String user = LBPS_mailFrom;
		String pass = MailPass;

		System.out.println("user" + user + " pass" + pass);
		int status = 0;
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.user", user);
		properties.setProperty("mail.smtp.password", pass);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "false");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.port", "465");

		// Session session = Session.getDefaultInstance(properties);
		Session session = Session.getDefaultInstance(properties, new jakarta.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, pass);// Specify the User name and the PassWord
			}
		});

		// compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			// message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(bodyText, "text/html");

			Transport.send(message);
			status = 1;
			System.out.println("message sent successfully....");

		} catch (MessagingException mex) {
			status = -1;
			mex.printStackTrace();
		}
		return status;
	}

}
