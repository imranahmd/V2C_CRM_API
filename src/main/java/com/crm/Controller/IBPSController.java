package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Calendar;
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
import jakarta.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.Repository.lBPSRepo;
import com.crm.ServiceDaoImpl.IBPSSMS;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.helper.Response;
import com.crm.services.UserDetailsui;
import com.crm.services.lBPSService;
import com.crm.util.GenerateRandom;
import com.external.api.BitlyAPI;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import freemarker.template.Template;


@RestController
public class IBPSController{
	private static Logger log = LoggerFactory.getLogger(IBPSController.class);
	private final String OPTIONAL_TIME_PATTERN = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])( (0[01-9]|[1][0-4]|[2][0-3]):(0[01-9]|[12345][0-9]):(0[01-9]|[12345][0-9]))?";
	@Autowired
	private lBPSService lbpsService;

	 @Autowired
	  private Configuration freemarkerConfig;
	
	@Autowired
	private lBPSRepo LbpsRepo;

	@Value("${emailTemplatelbps}")
    private String emailTemplatelbps;
	
	@Value("${lbps.mailFrom}")
	private String LBPS_mailFrom;

	@Value("${lbps.longURL}")
	private String longURL1;

	@Value("${lbps.smtpPass}")
	private String MailPass;
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/CreateInvoice")
	public String CreateInvoice(@RequestBody String jsonBody) throws Exception {
		log.info("inside the LBPS::::::::");
		log.info("jsonBody::::::::" + jsonBody);
		JSONObject js = new JSONObject(jsonBody);
		log.info("js::::::" + js);
		
		String emailID = js.getString("email_id");
		String contactNo = js.getString("contact_number");
		
		
		String emailNotify = js.getString("Mail_Notification");
		String smsNotify = js.getString("SMS_Notification");
		
		String merchantId = js.getString("merchant_id");
		String invoiceNo = js.getString("invoice_no");
		String AmountV = js.getString("amount");
		String date_time = js.getString("date_time");
		String valid_upto = js.getString("valid_upto");
		String cust_name = js.getString("cust_name");
		String remarks = js.getString("remarks");
		String status = js.getString("status");
		String CreatedOn = js.getString("CreatedOn");
		String createdBy = js.getString("CreatedBy");
		String ModifiedOn = js.getString("ModifiedOn");
		String ModifiedBy = js.getString("ModifiedBy");

		BigDecimal isValidAmount = new BigDecimal(AmountV);
		String IBPSBodyText = "";
		String emailSubject = "";
		String email_id = LBPS_mailFrom;
		String ResponseValue = null;

		if (isValidAmount.compareTo(BigDecimal.ZERO) <= 0) {
			log.info("Amount is zero");

			JSONObject respons = new JSONObject();
			respons.put("Status", "failure");
			respons.put("Reason", "Amount cannot be zero!");
			ResponseValue = respons.toString();

		} else {

			String existingIbpsRecords = LbpsRepo.existingInvoiceOrNot(invoiceNo, merchantId);
			log.info("existingIbpsRecords:::::" + existingIbpsRecords);

			if (existingIbpsRecords.equalsIgnoreCase("true")) {

				log.info("Invoice no already exists");
				JSONObject respons = new JSONObject();
				respons.put("Status", "failure");
				respons.put("Reason",
						"An invoice already exists with given invoice number. Please try another invoice number.");
				ResponseValue = respons.toString();
			} else {

				String merchantData = lbpsService.getMerchantDetails(merchantId);
				log.info("merchantData::::::::::::" + merchantData);

				JSONArray arrayjson = new JSONArray(merchantData);
//				log.info("arrayjson::::::::::::" + arrayjson);
				JSONObject obj = arrayjson.getJSONObject(0);
//				log.info("object:::::" + obj);
				log.info("<<--Merchant Name >> " + obj.getString("merchant_name"));
				String randomString = GenerateRandom.randomAlphaNumericIBPS(10);
				String payment_link_id = "lbp_" + randomString;
				String longURL = longURL1 + "?payLinkId=" + payment_link_id;
				URL url = new URL(longURL);
				log.info(">>>>url address full  " + url);
				BitlyAPI bitly = new BitlyAPI();
				bitly.setLongurl(longURL);
				String shortURL = bitly.getShortenUrl();

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date parsedDate = dateFormat.parse(date_time);
				Timestamp date_time1 = new java.sql.Timestamp(parsedDate.getTime());
				log.info("date_time1>>> " + date_time1);
				Date parsedDate1 = dateFormat.parse(CreatedOn);
				Timestamp CreatedOn1 = new java.sql.Timestamp(parsedDate1.getTime());
				log.info("CreatedOn1>>> " + CreatedOn1);
				Date parsedDate2 = dateFormat.parse(ModifiedOn);
				Timestamp ModifiedOn1 = new java.sql.Timestamp(parsedDate2.getTime());
				log.info("ModifiedOn1>>> " + ModifiedOn1);
				String result = lbpsService.createPaymentLink(invoiceNo, merchantId, AmountV, date_time1, valid_upto,
						cust_name, remarks, emailID, contactNo, shortURL, status, CreatedOn1, createdBy,
						ModifiedOn1, merchantId, payment_link_id, "");
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
					
					log.info("emailNotify::"+emailNotify+"::::smsNotify:::"+smsNotify);
					if (lbpsService.notificationType(obj, smsNotify, emailNotify).equalsIgnoreCase("SMS")) {
						
						log.info("only sms::::::");
						sendSms(js, obj, longURL);
					}
					if (lbpsService.notificationType(obj, smsNotify, emailNotify).equalsIgnoreCase("EMAIL")) {
						sendEmail(obj, js, emailSubject, IBPSBodyText, isOtherMerchant,payment_link_id);
						log.info("only email::::::");
					}
					if (lbpsService.notificationType(obj, smsNotify, emailNotify).equalsIgnoreCase("BOTH")) {
						log.info("both sms and email:::::::::");
						sendSms(js, obj, longURL);
						sendEmail(obj, js, emailSubject, IBPSBodyText, isOtherMerchant,payment_link_id);
					}
					JSONObject respons = new JSONObject();
					respons.put("Status", "success");
					respons.put("Reason","Invoice added successfully!");
					ResponseValue = respons.toString();
				} else {
					JSONObject respons = new JSONObject();
					respons.put("Status", "failure");
					respons.put("Reason", "Oops, something went wrong!");
					ResponseValue = respons.toString();

				}

			}
		}

		return ResponseValue;
	}


	public void sendEmail(JSONObject mstMerchant, JSONObject ibpsForm, String emailSubject, String emailTextBody,
			boolean isOtherMerchant, String payment_link_id) {
		log.info("inside sendEmail()");
		int status = 0;
		//GeneralService generalService = new GeneralServiceImpl();
		log.info("Sending an email...");
		if (isOtherMerchant) {
			emailSubject = "Payment Request from " + mstMerchant.getString("business_name").toUpperCase();
			String merchName = mstMerchant.getString("business_name").toUpperCase();
			Map<String, String> valuesMap = getValueMap(ibpsForm, mstMerchant.getString("MerchantId"), merchName, payment_link_id);
			emailTextBody = getBodyText(valuesMap);
		}
		status = sendMail(
				ibpsForm.getString("email_id"), emailSubject,
				ibpsForm.getString("email_id"), emailTextBody);
		if (status == 1) {
			log.info("Email send successfully");
		} else {
			log.info("Unable to send email");
		}
		log.info("End of sendEmail()");
	}
	
	
	public String getBodyText(Map<String, String> valuesMap) {
		log.info("Inside getBodyText()");
		try {
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		//	cfg.setServletContextForTemplateLoading(servletContext, "/Templates/EmailTemplates/");
			cfg.setDefaultEncoding("UTF-8");
			Template template;
			template = freemarkerConfig.getTemplate(emailTemplatelbps);
			//template = cfg.getTemplate("IbpsPaymentTemplate.ftl");
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
	
	public HashMap<String, String> getValueMap(JSONObject ibpsForm, String merchantId, String merchName, String payment_link_id) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String AmountV = ibpsForm.getString("amount");
		log.info("AmountV:::::"+AmountV);

		double dk = Double.valueOf(AmountV);
		log.info("AmountV:::::"+AmountV);
		DecimalFormat convertedAmount = new DecimalFormat("#.00");
		log.info("convertedAmount:::::"+convertedAmount.format(dk));
		
		resultMap.put("merName", merchName);
		resultMap.put("amount", convertedAmount.format(dk));
		resultMap.put("custName",ibpsForm.getString("cust_name"));
		resultMap.put("remarks", ibpsForm.getString("remarks"));
		resultMap.put("invoiceNo", ibpsForm.getString("invoice_no"));
		resultMap.put("expiryDate", getOrdinalDate(ibpsForm.getString("valid_upto")));
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
	            .appendText(ChronoField.DAY_OF_MONTH, ordinalNumbers)
	            .appendPattern(" MMMM, yyyy")
	            .toFormatter();
	    return dayOfMonthFormatter;
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

	public void sendSms(JSONObject ibpsData, JSONObject merchantData, String longURL1) throws UnsupportedEncodingException {
		String custName = ibpsData.getString("cust_name") ;
		String AmountV = ibpsData.getString("amount");
		BigDecimal isValidAmount = new BigDecimal(AmountV);
		Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
		String convertedAmount = format.format(isValidAmount);
		convertedAmount = new String(convertedAmount.getBytes(),"UTF-8").replaceAll("[^\\x00-\\x7F]", "").toUpperCase().replace("RS", "");
		String longURL = longURL1;
		BitlyAPI bitly = new BitlyAPI();
//		jyoti------------------------------------------
		log.info("resend payment::::"+longURL1); //resend
		String lss = longURL;
		int len = lss.length();
		String lssss= lss.substring(15);
		int lene = lssss.length();
		
		log.info("len size ---------------------------"+ len +" "+ lssss + " "+lene); // create);
		
		bitly.setLongurl(longURL);
		 if(lene>7) {
		IBPSSMS sms = new IBPSSMS();
		String smsText = "Dear " + custName + ", " + merchantData.getString("business_name").toUpperCase()
				+ " has requested payment for INR " + convertedAmount + ". " + "You can pay through this link: "
				+ bitly.getShortenUrl() + ". Team PAYFI.";

		log.info("smsText>>> " + smsText);
		log.info("Sending SMS...");
		 try {
	 
			long contactNo =  Long.parseLong(ibpsData.getString("contact_number"));
			sms.sendSMS(smsText, String.valueOf(contactNo));
		} catch (Exception e) {
			log.info("SMS could not send :: " + e.getMessage());
		}
		log.info("Sms sent successfully");
	}else {
     log.info("--------------------------1");
		IBPSSMS sms = new IBPSSMS();
		String smsText = "Dear " + custName + ", " + merchantData.getString("business_name").toUpperCase()
				+ " has requested payment for INR " + convertedAmount + ". " + "You can pay through this link: "
				+ longURL + ". Team PAY.";

		log.info("smsText>>> " + smsText);
		log.info("Sending SMS...");
		 
		try {
			long contactNo = Long.parseLong(ibpsData.getString("contact_number"));
			sms.sendSMS(smsText, String.valueOf(contactNo));
		} catch (Exception e) {
			log.info("SMS could not send :: " + e.getMessage());
		}
		log.info("Sms sent successfully");
	
}
}
	
	
	 public int sendMail(String to,String subject, String from, String bodyText)
	    {
	    	System.out.println("INsisde Sendmail");
			String user =  LBPS_mailFrom;
			String pass =  MailPass;
			
			
			System.out.println("user" + user +" pass" + pass);
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
			
			//Session session = Session.getDefaultInstance(properties);
			Session session = Session.getDefaultInstance(properties, 
				    new jakarta.mail.Authenticator(){
				        protected PasswordAuthentication getPasswordAuthentication() {
				            return new PasswordAuthentication(
				                user, pass);// Specify the User name and the PassWord
				        }
				    });

			// compose the message
			try {
				MimeMessage message = new MimeMessage(session);
				// message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						to));
				message.setSubject(subject);
				message.setContent(bodyText,"text/html");

				Transport.send(message);
				status = 1;
				System.out.println("message sent successfully....");

			} catch (MessagingException mex) {
				status = -1;
				mex.printStackTrace();
			}
			return status;
		}
	  

	 
	 public static void main(String args[]) {
		 String AmountV = "5";
		 double dk = Double.valueOf(AmountV);
			log.info("AmountV:::::"+AmountV);
			DecimalFormat numberFormat = new DecimalFormat("#.00");
			log.info("numberFormat:::::"+numberFormat.format(dk));
//			BigDecimal isValidAmount = new BigDecimal(AmountV);
//			log.info("isValidAmount:::::"+isValidAmount);
//			Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
//			String convertedAmount1 = format.format(isValidAmount);
//			log.info("convertedAmount1:::::"+convertedAmount1);
//			String uppserSt=uppercase(convertedAmount1);
//			log.info("uppserSt:::::"+uppserSt);
//			convertedAmount1= convertedAmount1.toUpperCase();
//			log.info("convertedAmount1:::::"+convertedAmount1);
//			log.info("convertedAmount1:::::"+convertedAmount1);
//			convertedAmount1=convertedAmount1.replace("RS", "");
//			log.info("convertedAmount1:::::"+convertedAmount1);
//			String convertedAmount2 = format.format(isValidAmount).toUpperCase();
//			log.info("convertedAmount2:::::"+convertedAmount2);
			//String convertedAmount3 = format.format(isValidAmount).toUpperCase().replace("RS", "");
			
			
			
	 }
	 
	 public static String uppercase(String str) {
		 char c [] = str.toCharArray();
		 
		 for(int i = 0; i<c.length; i++) {
			 if(c[i] >= 'a' && c[i] <= 'z') {
				 c[i] -= 32;
			 }
		 }
		return new String(c);
		 
	 }
	 

		@CrossOrigin(origins = { "http://localhost:4200" })
		@PostMapping(value = "/DisableSMSAndMail", produces = "application/json")
		public ResponseEntity<Object> TxnAmtVolume(@RequestBody String jsonBody) throws Exception {

			JSONObject resultFinal = null;
			Response res = new Response();
			Gson abcv = new Gson();
			String message = "";
			
			
			SecondCon secondCon = new SecondCon();
			UserDetailsui userDetailsui = new UserDetailsui();
						
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = null;
			String ipAddress = null;
			
			
			
			try {
				
				username = userDetails.optString("Username");
				ipAddress = userDetails.optString("ipAddress");
				
				log.info("jsonBody::::::::" + jsonBody);
				JSONObject js = new JSONObject(jsonBody);
				log.info("js::::::" + js);
				String merchantId =js.getString("Merchant_id");
				
				String merchantData = lbpsService.getMerchantDetails(merchantId);
				log.info("merchantData::::::::::::" + merchantData);

				JSONArray arrayjson = new JSONArray(merchantData);
//				log.info("arrayjson::::::::::::" + arrayjson);
				JSONObject obj = arrayjson.getJSONObject(0);
//				log.info("object:::::" + obj);
				log.info("<<--Merchant Name >> " + obj.getString("merchant_name"));
				String smsNotification = obj.getString("ibps_sms_notification").toString();
				String mailNotification = obj.getString("ibps_email_notification").toString();
				
				log.info("smsNotification::::::"+smsNotification+"mailNotification::::::"+mailNotification);
				resultFinal = new JSONObject();
				resultFinal.put("mailNotification", mailNotification);
				resultFinal.put("smsNotification", smsNotification);
				
				message = "Get Notification Successfully !";
				
				res.setStatus(true);
				res.setMessage(message);
				res.setResult(resultFinal);
				
				
				secondCon.insertIntoSecondSchema(username, "Disable SMS And Mail", jsonBody, res.toString(), ipAddress);

			} catch (Exception e) {
				
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
										
				secondCon.insertIntoSecondSchema(username, "Disable SMS And Mail-Exception", jsonBody, stackTraceString, ipAddress);
				
				log.info("e::::::", e);
				message = "Error occured : " + e.getMessage();
				res.setStatus(false);
				res.setMessage(message);

			}


			return ResponseEntity.ok(abcv.toJson(res));

		}
	 
}
