//package com.crm.services;
//
//import freemarker.core.ParseException;
//import freemarker.template.Configuration;
//import freemarker.template.MalformedTemplateNameException;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateNotFoundException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import com.crm.Repository.MerchantRepository;
//import com.crm.dto.Mail;
//import com.crm.util.GeneralUtil;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import com.crm.Repository.ResellerRepository;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Service
//public class EmailService {
//
//	@Autowired
//	private MerchantRepository merchantRepo;
//
//	@Autowired
//	private JavaMailSender emailSender;
//	
////	@Autowired
////    private JavaMailSender emailSender;
//
//	@Autowired
//	private Configuration freemarkerConfig;
//
//	@Autowired
//	private ResellerRepository resellerRepo;
//
//	@Value("${mailFrom}")
//	private String mailFrom;
//
//	@Value("${mailCc1}")
//	private String mailCc1;
//
//	@Value("${mailCc2}")
//	private String mailCc2;
//
//	@Value("${mailCc3}")
//	private String mailCc3;
//
//	@Value("${mailSubject}")
//	private String mailSubject;
//
//	@Value("${emailTemplateMerchant}")
//	private String emailTemplateMerchant;
//
//	@Value("${emailTemplateReseller}")
//	private String emailTemplateReseller;
//
//	@Value("${emailTemplateResellerMerchant}")
//	private String emailTemplateResellerMerchant;
//
//	@Value("${emailTemplateForgetPassword}")
//	private String emailTemplateForgetPassword;
//
//	@Value("${urlforreset}")
//	private String urlforreset;
//
//	@Value("${mailForgetSubject}")
//	private String mailForgetSubject;
//
//	@Value("${emailTemplatteResetPassword}")
//	private String emailTemplatteResetPassword;
//
//	@Value("${emailTemplateOTPSignUp}")
//	private String emailTemplateOTPSignUp;
//
//	@Value("${mailOTPSubject}")
//	private String mailOTPSubject;
//
//	@Value("${emailTemplateUserCreation}")
//	private String emailTemplateUserCreation;
//
//	@Value("${mailUserCreationSubject}")
//	private String mailUserCreationSubject;
//
//	public void sendSimpleMessage(String merchantId, String merchantName, String emailId, String merReturnUrl,
//			String transactionKey, String password, String resellerId, String resellerEmailId, String resellerStatus)
//			throws MessagingException, IOException, TemplateException {
//
//		Mail mail = new Mail();
//		mail.setFrom(mailFrom);
//		mail.setcC1(mailCc1);
//		mail.setcC2(mailCc2);
//		mail.setcC3(mailCc3);
//		mail.setSubject(mailSubject + " " + merchantName);
//
//		Map<String, Object> model = new HashMap<>();
//		model.put("MID", merchantId);
//		model.put("Name", merchantName);
//		model.put("ReturnUrL", merReturnUrl);
//		model.put("EncyptKey", transactionKey);
//		model.put("Password", GeneralUtil.decrypt(password));
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//
//		Template t;
//		if (resellerId != null && !resellerId.isBlank()) {
//			if ("Active".equalsIgnoreCase(resellerStatus.trim())) {
//				mail.setTo(resellerEmailId);
//				model.put("RID", resellerId);
//				t = freemarkerConfig.getTemplate(emailTemplateResellerMerchant);
//			} else {
//				mail.setTo(emailId);
//				t = freemarkerConfig.getTemplate(emailTemplateMerchant);
//			}
//		} else {
//			mail.setTo(emailId);
//			t = freemarkerConfig.getTemplate(emailTemplateMerchant);
//		}
//
//		mail.setModel(model);
//		String[] cc = { mail.getcC1(), mail.getcC2(), mail.getcC3() };
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//		helper.setCc(cc);
//		helper.addInline("logo.png", new ClassPathResource("payfiLogo.png"));
//		helper.addInline("icons8-twitter-48.png", new ClassPathResource("icons8-twitter-48.png"));
//		helper.addInline("icons8-linkedin-48.png", new ClassPathResource("icons8-linkedin-48.png"));
//
//		emailSender.send(message);
//		merchantRepo.updateEmailTriggerFlag("1", merchantId);
//	}
//
//	// old method
//	public void sendSimpleMessageOld(String merchantId, String merchantName, String emailId, String merReturnUrl,
//			String transactionKey, String password, String resellerId, String resellerEmailId, String resellerStatus)
//			throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//
//		mail.setFrom(mailFrom);
//		mail.setcC1(mailCc1);
//		mail.setcC2(mailCc2);
//		mail.setcC3(mailCc3);
//		mail.setSubject(mailSubject + " " + merchantName);
//
//		Map model = new HashMap();
//		model.put("MID", merchantId);
//		model.put("Name", merchantName);
//		model.put("ReturnUrL", merReturnUrl);
//		model.put("EncyptKey", transactionKey);
//		model.put("Password", GeneralUtil.decrypt(password));
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//
//		Template t = null;
//		if (resellerId != null && !resellerId.isBlank()) {
//			if (resellerStatus.trim().equalsIgnoreCase("Active")) {
//				mail.setTo(resellerEmailId);
//				model.put("RID", resellerId);
//				t = freemarkerConfig.getTemplate(emailTemplateResellerMerchant);
//			}
//		} else {
//			mail.setTo(emailId);
//			t = freemarkerConfig.getTemplate(emailTemplateMerchant);
//		}
//
//		mail.setModel(model);
//		String cc[] = { mail.getcC1(), mail.getcC2(), mail.getcC3() };
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//		System.out.println(cc[0] + " " + cc[1]);
//		helper.setCc(cc);
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		helper.addInline("icons8-twitter-48.png", new ClassPathResource("icons8-twitter-48.png"));
//		helper.addInline("icons8-linkedin-48.png", new ClassPathResource("icons8-linkedin-48.png"));
//
//		emailSender.send(message);
//		merchantRepo.updateEmailTriggerFlag("1", merchantId);
//	}
//
//	public void sendresellerMessage(String resellerId, String resellerName, String emailId, String merReturnUrl,
//			String password, String transactionKey) throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//
//		mail.setFrom(mailFrom);
//		mail.setcC1(mailCc1);
//		mail.setcC2(mailCc2);
//		mail.setcC3(mailCc3);
//		mail.setSubject(mailSubject + " " + resellerName);
//
//		Map model = new HashMap();
//		model.put("RID", resellerId);
//		model.put("Name", resellerName);
//		model.put("ReturnUrL", merReturnUrl);
//		model.put("EncyptKey", transactionKey);
//		model.put("Password", GeneralUtil.decrypt(password));
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//
//		Template t = null;
//		if (resellerId != null && !resellerId.isBlank()) {
//			mail.setTo(emailId);
//			model.put("RID", resellerId);
//			t = freemarkerConfig.getTemplate(emailTemplateReseller);
//		} else {
//			t = freemarkerConfig.getTemplate(emailTemplateMerchant);
//		}
//
//		mail.setModel(model);
//		String cc[] = { mail.getcC1(), mail.getcC2(), mail.getcC3() };
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//		System.out.println(cc[0] + " " + cc[1] + " " + cc[2]);
//		helper.setCc(cc);
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		helper.addInline("icons8-twitter-48.png", new ClassPathResource("icons8-twitter-48.png"));
//		helper.addInline("icons8-linkedin-48.png", new ClassPathResource("icons8-linkedin-48.png"));
//
//		emailSender.send(message);
//		String emailTrigger = "1";
//		resellerRepo.updemailTrigger(resellerId, emailTrigger);
//	}
//
//	public void sendForgetPassword(String userEmail, UUID UUID_ID, String FullName, String UserId, String strDate,
//			String strDate1, String TradeName, String RoleType)
//			throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//		mail.setFrom(mailFrom);
//		mail.setSubject(mailForgetSubject);
//		String urlReset = null;
//		if (RoleType == "Aggregator") {
//			urlReset = urlforreset + "aggregator/#/auth/forgot-password?token=" + UUID_ID;
//		} else if (RoleType == "Merchant") {
//			urlReset = urlforreset + "merchant/#/auth/forgot-password?token=" + UUID_ID;
//		} else if (RoleType == "Admin") {
//			urlReset = urlforreset + "preprod/#/auth/forgot-password?token=" + UUID_ID;
//		}
//		mail.setFrom(mailFrom);
//		Map model = new HashMap();
//		model.put("fullname", TradeName);
//		model.put("urlreset", urlReset);
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//		Template t = null;
//		t = freemarkerConfig.getTemplate(emailTemplateForgetPassword);
//		mail.setTo(userEmail);
//		mail.setModel(model);
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		emailSender.send(message);
//		String uu_id = UUID_ID.toString();
//		resellerRepo.insertTrail(UserId, FullName, strDate1, uu_id);
//	}
//
//	public void sendresetPasswordInternal(String userEmail, String TradeName)
//			throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//		mail.setFrom(mailFrom);
//		mail.setSubject(mailForgetSubject);
//		String urlReset = null;
//
//		mail.setFrom(mailFrom);
//		Map model = new HashMap();
//		model.put("fullname", TradeName);
//		model.put("urlreset", urlReset);
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//		Template t = null;
//		t = freemarkerConfig.getTemplate(emailTemplatteResetPassword);
//		mail.setTo(userEmail);
//		mail.setModel(model);
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		emailSender.send(message);
//
//	}
//
//	public void selfonboardEmail(String userEmail, String fullname, String Otp)
//			throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//		mail.setFrom(mailFrom);
//		mail.setSubject(mailOTPSubject);
//		String urlReset = null;
//
//		mail.setFrom(mailFrom);
//		Map model = new HashMap();
//		model.put("fullname", fullname);
//		model.put("otp", Otp);
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//		Template t = null;
//		t = freemarkerConfig.getTemplate(emailTemplateOTPSignUp);
//		mail.setTo(userEmail);
//		mail.setModel(model);
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		emailSender.send(message);
//
//	}
//
//	public void selfonboardUserCreation(String userEmail, String fullname, String mid)
//			throws MessagingException, IOException, TemplateException {
//		Mail mail = new Mail();
//		System.out.println(mailFrom);
//		mail.setFrom(mailFrom);
//		mail.setSubject(mailUserCreationSubject);
//		String urlReset = null;
//
//		mail.setFrom(mailFrom);
//		Map model = new HashMap();
//		model.put("fullname", fullname);
//		model.put("MID", mid);
//
//		MimeMessage message = emailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//				StandardCharsets.UTF_8.name());
//		Template t = null;
//		t = freemarkerConfig.getTemplate(emailTemplateUserCreation);
//		mail.setTo(userEmail);
//		mail.setModel(model);
//		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
//		helper.setTo(mail.getTo());
//		helper.setText(html, true);
//		helper.setSubject(mail.getSubject());
//		helper.setFrom(mail.getFrom());
//
//		helper.addInline("logo.png", new ClassPathResource("logo.png"));
//		emailSender.send(message);
//
//	}
//
//}