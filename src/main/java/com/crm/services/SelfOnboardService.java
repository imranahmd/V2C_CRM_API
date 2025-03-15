package com.crm.services;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.MessagingException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.crm.Repository.MerchantRepository;
import com.crm.Repository.ResellerRepository;
import com.crm.Repository.UserRepository;
import com.crm.dto.SMSDto;
import com.crm.helper.OTPgenrateHelper;
import com.crm.model.User;
import com.crm.util.GeneralUtil;

import freemarker.template.TemplateException;
import graphql.com.google.common.base.Supplier;

@Service
public class SelfOnboardService {
	private final Long expiryInterval = 5L * 60 * 1000;
	
	@Autowired
	private SMSDto sMSDto;	
	
//	@Autowired
//    private EmailService emailService;
	
	@Autowired
	private MerchantRepository merchantRepo;
	
	@Autowired
	private ResellerRepository resellerRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private customUserDetailsService customUserDetailsService;
	
	@Autowired
	private com.crm.helper.JwtHelperUtil JwtHelperUtil;
	
	public Map<String, Object> smsOTPconfigure(String MobileNo, String fullName, String emailId) {
		Map<String, Object> Msg = null ;
		String message = null;
		JSONObject js1 = new JSONObject();
		
		boolean result = MobileNo.matches("[7-9][0-9]{9}");
				
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(emailId);
		
		if(MobileNo != null && fullName != null && emailId != null || MobileNo.equals("") && fullName.equals("") && emailId.equals("")) {
			OTPgenrateHelper oTPgenrateHelper = new OTPgenrateHelper();
			String varMobileotp = oTPgenrateHelper.createRandomOneTimePassword().get();
			String varEmailotp = oTPgenrateHelper.createRandomOneTimePassword().get();
			String sMOOtp = String.valueOf(varMobileotp);
			String sEMOtp = String.valueOf(varEmailotp);
					if (result == true && matcher.matches() == true) {
						boolean userEmail = resellerRepo.findUserByEmail(emailId);
						if(userEmail == false) {
							String  resultSms = smsSend(MobileNo, varMobileotp);
							String  resultEmail = null;
									//emailSend(emailId,fullName , varEmailotp);
							if (resultSms.equals("Success") && resultEmail.equals("Success")) {
								resellerRepo.insertSetailsSelfonBoard(fullName, emailId, MobileNo, varMobileotp, varEmailotp);
								message = "OTP has been sent to your Mobile and Email ID";
								js1.put("Status", "Success");
								js1.put("Message", message);
							}else {
								message = "Problem Occure Sending OTP";
								js1.put("Status", "Error");
								js1.put("Message", message);
							}
						}else {
							message = "Email-Id is already Registered";
							js1.put("Status", "Error");
							js1.put("Message", message);
						}
				}else {
					if (result == false) {
						message = "Contact No is Invalid";
						js1.put("Status", "Error");
						js1.put("Message", message);
					}
					if (matcher.matches() == false) {
						message = "Email Id is Invalid";
						js1.put("Status", "Error");
						js1.put("Message", message);
					}
					if (result == false && matcher.matches() == false) {
						message = "Contact No & Email Id is Invalid";
						js1.put("Status", "Error");
						js1.put("Message", message);
					}
				}
			}else {
				message = "Empty Field Not Accepted";
				js1.put("Status", "Error");
				js1.put("Message", message);
			}
		Msg = js1.toMap();
		return Msg;
	}
	
	public Map<String, Object> getOTPVerified(String getMobileOTP, String getEmailOTP) {
		Map<String, Object> Msg = null ;
		String message = null;
		JSONObject js1 = new JSONObject();
		
		List<Object[]> getUsrinfo = resellerRepo.findbyOtp(getMobileOTP, getEmailOTP);
		
		if(!getUsrinfo.isEmpty()) {
			for(Object[] obj: getUsrinfo) 
            {
				String FullName = (String) obj[1];
				String emailId = (String) obj[2];
				String contactNo = (String) obj[3];
				Date createdon = (Date) obj[6];
				String vereified = (String) obj[7];
				LocalDateTime now = LocalDateTime.now();
                Timestamp timestamp = Timestamp.valueOf(now);   
                long difference = timestamp.getTime()- createdon.getTime();
                Duration duration = Duration.ofMillis(difference);
                long seconds = duration.getSeconds();
                long MM = seconds / 60;
                if(MM >= 0 && MM <=3) {
                	if (vereified.equals("0")) {
                		String merchantId = merchantRepo.findMerchantId();
                		GeneralUtil generalUtil = new GeneralUtil();
                		String firstName = FullName.contains(" ") ? FullName.split(" ")[0]: FullName;
                		String password = generalUtil.crypt(firstName + "@2023");
                		User user = new User();
                		user.setFullName(FullName);
                		user.setBlocked(false);
                		user.setCreatedBy("Self");
                		user.setUserId(merchantId);
                		user.setMerchantId(merchantId);
                		user.setPassword(password);
                		long millis = System.currentTimeMillis();
                		user.setCreatedOn(new java.sql.Date(millis));
                		user.setEmailId(emailId);
                		user.setGroupId(2);
                		user.setRoleId(2);
                		user.setIsActive(true);
                		user.setIsDeleted('N');
                		user.setContactNumber(contactNo);
                		user = userRepository.save(user);
                		String maxtoken = "0";
                		String status = "Self-Initiated";
                		Date date = Calendar.getInstance().getTime();                		
                		merchantRepo.createMerchantSelf(merchantId, FullName, emailId, contactNo, status, date, maxtoken);                		
             			String verified = "1";
             			merchantRepo.updateOTPverified(getMobileOTP, verified);
             			String  resultEmail = null;
             					//emailSendonCreation(emailId,FullName, merchantId);
             			UserDetails userMer=this.customUserDetailsService.loadUserByUsername(merchantId);
             			String token = this.JwtHelperUtil.generateToken(userMer);
                		 message = "OTP Verified";
                		 js1.put("Status", "Success");
 						 js1.put("Message", message);
 						 js1.put("data", merchantId);
 						 js1.put("Authorization", token);
                	 }else if(vereified.equals("1")) {
                		 message = "Your Account Already Verified";
                		 js1.put("Status", "Error");
             			 js1.put("Message", message);
                	 }else if(vereified.equals("2")) {
                		 message = "Your OTP Has Been Expired";
                		 js1.put("Status", "Error");
             			 js1.put("Message", message);
                	 }
                }else {
                	 message = "Your OTP Has Been Expired";
                	 js1.put("Status", "Error");
         			 js1.put("Message", message);
                	 if (vereified.equals("1") || vereified.equals("0") ){
	                	 String verified = "2";
	                	 merchantRepo.updateOTPverified(getMobileOTP, verified);                
                	}
                }
              }
			
		}else {
			boolean mOTPVerfication = resellerRepo.findbyMOtp(getMobileOTP);
			boolean eOTPVerfication = resellerRepo.findbyEOtp(getEmailOTP);
			message = "";
			if (mOTPVerfication == false) {
				message = "Contact Number Verification OTP Is Wrong ";				
			}
			if (eOTPVerfication == false) {
				message = message + " E-Mail Verification OTP Is Wrong";				
			}
			
			js1.put("Status", "Error");
			js1.put("Message", message);
		}
		
		Msg = js1.toMap();
		return Msg;
	}
	
	
	public  Map<String, Object> createPasswordSelf(String newpassword, String JwtToken){
		Map<String, Object> Msg = null ;
		String message = null;
		JSONObject js1 = new JSONObject();
		GeneralUtil generalUtil = new GeneralUtil();
		List<Object[]> userId =  merchantRepo.findUserIdById(JwtToken);

		if(userId !=null) {	
			if (JwtToken !=null) {
				String passwordChangeEn = generalUtil.crypt(newpassword);
				merchantRepo.updateResetPass(JwtToken, passwordChangeEn);
					message = "Your Password Created Successfully and Your Merchant Id is " + JwtToken;
					js1.put("Status", "Success");
					js1.put("Message", message);
					
				}else {
					message = "Your Credential Is Not Matching";
					js1.put("Status", "Error");
					js1.put("Message", message);
				}
		}
		
		Msg = js1.toMap();
		return Msg;
	 }
	
	public String smsSend(String MobileNo, String getOtp){
		RestTemplate restTemplate = new RestTemplate();
		String message = null;
		String templat = getOtp + " is the OTP to complete the signup with Pay";		
		MultiValueMap qp = queryParams(MobileNo, templat ,sMSDto.getSMSTempId());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		URI builder = UriComponentsBuilder.fromUriString(sMSDto.getSMSUrl()).queryParams(qp).build().toUri();
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(builder, HttpMethod.GET, entity, String.class);
		if(response.getStatusCodeValue() == 200 ) {
			message ="Success";
		}else {
			message ="Error";
		}
		return message;
	}
	
	public MultiValueMap<String, String > queryParams(String mobileNum, String msgBody, String tempId){
		MultiValueMap<String, String> smsParam = new LinkedMultiValueMap<>();		
		smsParam.add("username", sMSDto.getSMSusername());
		smsParam.add("password", sMSDto.getSMSpassword());
		smsParam.add("sender", sMSDto.getSMSsenderId());
		smsParam.add("type", sMSDto.getSMSType());
		smsParam.add("mobile", mobileNum);
		smsParam.add("message", msgBody);
		smsParam.add("peid", sMSDto.getSMSPeId());
		smsParam.add("tempid", sMSDto.getSMSTempId());		
		return smsParam;
	}
	
//	public String emailSend(String emailId, String fullName, String otp) {
//		String message = null;
//		try {
//			emailService.selfonboardEmail(emailId, fullName, otp );
//			message ="Success";
//		} catch (MessagingException e) {
//			message ="Error";
//			e.printStackTrace();
//		} catch (IOException e) {
//			message ="Error";
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			message ="Error";
//			e.printStackTrace();
//		}
//		
//		return message; 
//	}
	
//	public String emailSendonCreation(String emailId, String fullName, String Mid) {
//		String message = null;
//		try {
//			emailService.selfonboardUserCreation(emailId, fullName, Mid);
//			message ="Success";
//		} catch (MessagingException e) {
//			message ="Error";
//			e.printStackTrace();
//		} catch (IOException e) {
//			message ="Error";
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			message ="Error";
//			e.printStackTrace();
//		}
//		
//		return message; 
//	}
}
