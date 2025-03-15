package com.crm.Controller;

import java.io.BufferedOutputStream;

import java.io.PrintWriter;
import java.io.StringWriter;
import com.crm.ServiceDaoImpl.SecondCon;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.SubMerchantClasses.YesBankUPI;
import com.crm.dto.BulCsvMerchantMdrDto;
import com.crm.dto.BulkCsvDto;
import com.crm.dto.BulkMdrList;
import com.crm.dto.BulkMerchantList;
import com.crm.dto.BusinessTypeDto;
import com.crm.dto.MerchantBasicSetupDto;
import com.crm.dto.MerchantCreationDto;
import com.crm.dto.MerchantDocDto;
import com.crm.dto.MerchantDto;
import com.crm.dto.MerchantPaginationDto;
import com.crm.dto.UploadDocDetailsDto;
import com.crm.dto.UploadDocDto;
import com.crm.model.BulkCsvMerchant;
import com.crm.model.MerchantBank;
import com.crm.model.MerchantKycDoc;
//import com.crm.services.EmailService;
import com.crm.services.MerchantService;
import com.crm.util.GeneralUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.crm.helper.BulkCsvHelper;
import com.crm.helper.JwtHelperUtil;
import com.crm.helper.MdrBulkCsvHelper;
import com.crm.helper.Response;
import com.crm.message.ResponseMessage;

import com.crm.services.UserDetailsui;

@RestController
public class MerchantController {
	private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			false);
	private static final Logger log = LoggerFactory.getLogger(MerchantController.class);
	public static String uploadDirectory = "/home/KYCDOCUMENTS";
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private com.crm.services.RmsConfigServices RmsConfigServices;

	@Autowired
	private YesBankUPI yesBankUPI;

	@Value("${yesbank.spID}")
	private String yesBankSP_ID;

//	@Autowired
//	private EmailService emailService;

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/get-merchant")
	public MerchantPaginationDto getListById(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		String merchantName = js.getString("name");
		String merchantId = js.getString("mid");
		String resellerId = js.has("rid") ? js.getString("rid") : "";
		String fromDate = js.getString("startDate");
		String toDate = js.getString("endDate");
		int norecord = js.getInt("pageRecords");
		int pageno = js.getInt("pageNumber");
		String sp = js.getString("sp");

		log.info(":::::fromDate::" + fromDate);
		log.info("::::toDate::::" + toDate);
		log.info("::::::::merchantId:::::::" + merchantId);
		log.info("::::::::::resellerId::::::::" + resellerId);
		log.info("::::;merchantName::::::::::::" + merchantName);
		log.info(":::::::norecord:::::::" + norecord);
		log.info("pageno::::::::::::" + pageno);

		log.info(":::::::::sp:::::::" + sp);
		log.info("");
		log.info("");
		log.info("");
//fromDate, toDate, merchantId, resellerId, merchantName, norecord, pageno, js1		
		JSONObject js1 = new JSONObject();
		try {
			if (js.has("columns") && js.get("columns") != null) {
				js1 = js.getJSONObject("columns");
				log.info("::::::::::;js1::::::::::::" + js1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MerchantPaginationDto merchants = null;

		log.info("::::::::::;js1::::2::::::::" + js1);
		try {
			merchants = merchantService.getMerchantByName(fromDate, toDate, merchantId, resellerId, merchantName,
					norecord, pageno, js1, sp);

			log.info("::::::::::merchants:DTO:::::::" + merchants.toString());

			secondCon.insertIntoSecondSchema(username, "get-merchant", jsonBody,
					"Merchant Details fetched Successfully", ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Get Bank List-Exception", "Getting all bank list",
					stackTraceString, ipAddress);
			e.printStackTrace();
		}
		return merchants;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/create-merchant")
	public List<MerchantDto> postMerchantData(@RequestBody MerchantCreationDto merchantDto) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		List<MerchantDto> MerchantiInfo = null;
		try {
//			log.info("merchantDto:::::"+merchantDto.getAdditional_contact());

			MerchantiInfo = merchantService.createMerchant(merchantDto);

			log.info("\n::::::MerchantiInfo:::::::::::" + MerchantiInfo.toString());

			secondCon.insertIntoSecondSchema(username, "create-merchant", merchantDto.toString(),
					"Merchant Details Updated Successfully", ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "create-merchant-Exception", stackTraceString, stackTraceString,
					ipAddress);

			e.printStackTrace();
		}
		return MerchantiInfo;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/get-merchantbank")
	public List<MerchantBank> getListbyMerchantId(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String merchantId = js.getString("merchantid");
		List<MerchantBank> ListBank = null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;
		try {

			ListBank = merchantService.getMerchantBank(merchantId);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Get-Merchant Bank", jsonBody, ListBank.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Get-Merchant Bank-Exception", jsonBody, stackTraceString,
					ipAddress);
			e.printStackTrace();
		}

		return ListBank;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/create-merchant-bank")
	public List<MerchantBank> postMerchantBankData(@RequestBody MerchantBank merchantBank) {
		List<MerchantBank> merchantBankinfo = null;

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;
		try {
			merchantBankinfo = merchantService.createMerchantbank(merchantBank);
			System.out.print("Enter in update method:::::::::::::::::::::: ");

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "create-merchant-bank", merchantBank.toString(),
					merchantBankinfo.toString(), ipAddress);

			merchantService.updateFiledStatus(merchantBank.getMerchantId(), "Account");

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "create-merchant-bank-Exception", merchantBank.toString(),
					stackTraceString, ipAddress);
			e.printStackTrace();
		}
		return merchantBankinfo;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PutMapping("/update-merchant-bank")
	public List<MerchantBank> postMerchantBankUpdt(@RequestBody MerchantBank merchantBank) {// TODO
		List<MerchantBank> merchantBankupdtinfo = null;

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");
		try {
			merchantBankupdtinfo = merchantService.updateMerchantbank(merchantBank);

			secondCon.insertIntoSecondSchema(username, "update-merchant-bank", merchantBank.toString(),
					merchantBankupdtinfo.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "update-merchant-bank-Exception", merchantBank.toString(),
					stackTraceString, ipAddress);

			e.printStackTrace();
		}
		return merchantBankupdtinfo;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/get-merchantbank-byid")
	public List<MerchantBank> getListbyPId(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		Long Pid = js.getLong("Pid");
		List<MerchantBank> BankDetails = null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;

		try {

			BankDetails = merchantService.getMerchantBankPid(Pid);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "get-merchantbank-byid", jsonBody, BankDetails.toString(),
					ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "get-merchantbank-byid-Exception", jsonBody, stackTraceString,
					ipAddress);
			e.printStackTrace();
		}

		return BankDetails;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/delete-merchantbank-byid")
	public List<MerchantBank> deleteMerchantBank(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		Long Pid = js.getLong("Pid");
		List<MerchantBank> merchantBankdelinfo = null;

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String username = null;

		String ipAddress = null;

		try {
			merchantBankdelinfo = merchantService.deleteMerchantbank(Pid);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "delete-merchantbank-byid", jsonBody,
					merchantBankdelinfo.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "delete-merchantbank-byid", jsonBody, stackTraceString,
					ipAddress);
			e.printStackTrace();
		}
		return merchantBankdelinfo;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload-image/")
	public List<MerchantKycDoc> uploadImage(@RequestParam MultipartFile imageFile, String merchantId,
			String docType, String docId) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		StringBuilder filesname = new StringBuilder();
		String MerchantId = merchantId;
		String DocType = docType;
		String DocId = docId;
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);
		String strDatewospc = strDate.replaceAll("\\s", "");
		String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
		String fileOrgname = imageFile.getOriginalFilename();
		String filesname1 = uploadDirectory + "/" + MerchantId + "/";
		fileOrgname = fileOrgname.replaceAll("\\s", "");
		String nameFileconvert = DocId + "_" + strDatewosp + "_" + fileOrgname;
		File file = new File(filesname1);
		if (!file.exists()) {
			file.mkdirs();
		}
		List<MerchantKycDoc> merchantKycDocList = new ArrayList<>();
		MerchantKycDoc kycdto = null;

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;

		String ipAddress = userDetails.optString("ipAddress");

		try {
			Path path = Path.of(filesname1, nameFileconvert);
			filesname.append(nameFileconvert);
			Files.write(path, imageFile.getBytes());
			String DocRef = filesname1 + nameFileconvert;
			MerchantKycDoc merchantKycDoc = new MerchantKycDoc();

			merchantKycDoc.setDocName(nameFileconvert);
			merchantKycDoc.setDocType(DocType);
			merchantKycDoc.setDocpath(DocRef);
			merchantKycDoc.setMerchantId(MerchantId);
			merchantKycDoc.setModifiedBy(MerchantId);
			long millis = System.currentTimeMillis();
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(millis));
			merchantKycDoc.setModifiedOn(timeStamp);
			merchantKycDoc.setUpdatedBy(MerchantId);
			merchantKycDoc.setUpdateOn(timeStamp);
			merchantKycDocList.add(merchantKycDoc);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "upload-image", filesname1, merchantKycDocList.toString(),
					ipAddress);

		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "upload-image-Exception", filesname1, stackTraceString,
					ipAddress);

			e.printStackTrace();
		}

		return merchantKycDocList;

	}

	// ----------
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload-KycdocsV2/")
	public List<MerchantKycDoc> uploadImageV2(@RequestBody UploadDocDto uploadDoc) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		secondCon.insertIntoSecondSchema(username, "Uploading KYC DOCS", "KYC DOC", "KYC DOC", ipAddress);

		String MerchantId = uploadDoc.getMerchantId();

		Map<String, UploadDocDetailsDto> docTypes = new HashMap<>();

		int i = 0;
		for (UploadDocDetailsDto dto : uploadDoc.getDocType()) {
			docTypes.put(String.valueOf(i), dto);
			i++;
		}

		List<MerchantKycDoc> kycdto = null;
		try {
			String DocType = mapper.writeValueAsString(docTypes);
			MerchantKycDoc merchantKycDoc = new MerchantKycDoc();
			kycdto = merchantService.insertKycDoc(MerchantId, DocType);
			System.out.print("Enter in update method:Kyc::::::::::::::::::::: ");
			merchantService.updateFiledStatus(MerchantId, "Kyc");

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Uploading KYC DOCS-Exception", stackTraceString,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}
		return kycdto;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload-Kycdocs")
	public List<MerchantKycDoc> uploadImage(@RequestParam String merchantId, String docType) {

		String MerchantId = merchantId;
		String DocType = docType;
		List<MerchantKycDoc> kycdto = null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String username = null;

		String ipAddress = null;

		try {

			MerchantKycDoc merchantKycDoc = new MerchantKycDoc();
			kycdto = merchantService.insertKycDoc(MerchantId, DocType);
			System.out.print("Enter in update method:Kyc::::::::::::::::::::: ");

			merchantService.updateFiledStatus(merchantId, "Kyc");

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "upload-Kycdocs", docType, kycdto.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "upload-Kycdocs-Exception", docType, stackTraceString,
					ipAddress);

			e.printStackTrace();
		}

		return kycdto;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/delete-upload-image")
	public List<MerchantDocDto> deleteImage(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		int Id = js.getInt("id");
//		String docName = js.getString("docname");
		String path = js.getString("path");
		String MerchantId = js.getString("merchantId");
		List<MerchantDocDto> kycdto = null;
		MerchantKycDoc merchantKycDoc = new MerchantKycDoc();

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String username = null;

		String ipAddress = null;

		try {

//			merchantKycDoc.setDocName(docName);
			merchantKycDoc.setId(Id);
			merchantKycDoc.setDocpath(path);
			merchantKycDoc.setMerchantId(MerchantId);
			kycdto = merchantService.deleteuploaddoc(merchantKycDoc);

			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "delete-upload-image", jsonBody, kycdto.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "delete-upload-image-Exception", jsonBody, stackTraceString,
					ipAddress);

			e.printStackTrace();
		}

		return kycdto;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/update-Merchant-BasicSetup")
	public List<MerchantBasicSetupDto> updByMerchantId(@RequestBody String jsonBody) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		JSONObject js = new JSONObject(jsonBody);
		String merchantId = js.getString("merchantid");
		String merReturnUrl = js.getString("merReturnUrl");
		String isAutoRefund = js.getString("isAutoRefund");
		String hours = js.getString("hours");
		String minutes = js.getString("minutes");
		String isPushUrl = js.getString("isPushUrl");
		String pushUrl = js.getString("pushUrl");
		String settlementCycle = js.getString("settlementCycle");
		String merchantDashboardRefund = js.getString("merchantDashboardRefund");
		String mdDisableRefundCc = js.getString("mdDisableRefundCc");
		String mdDisableRefundDc = js.getString("mdDisableRefundDc");
		String mdDisableRefundNb = js.getString("mdDisableRefundNb");
		String mdDisableRefundUpi = js.getString("mdDisableRefundUpi");
		String mdDisableRefundWallet = js.getString("mdDisableRefundWallet");
		String refundApi = js.getString("refundApi");
		String refundApiDisableCc = js.getString("refundApiDisableCc");
		String refundApiDisableDc = js.getString("refundApiDisableDc");
		String refundApiDisableNb = js.getString("refundApiDisableNb");
		String refundApiDisableUpi = js.getString("refundApiDisableUpi");
		String refundApiDisableWallet = js.getString("refundApiDisableWallet");
		String integrationType = js.getString("integrationType");
		String isretryAllowed = js.getString("isretryAllowed");
		String bpsEmailNotification = js.getString("bpsEmailNotification");
		String bpsSmsNotification = js.getString("bpsSmsNotification");
		String bpsMailReminder = js.getString("ibpsMailReminder");
		String Reporting_cycle = js.getString("reporting_cycle");
		String upi_loader = js.getString("upi_loader");
		String upi_intent = js.getString("upi_intent");
		String upi_collect = js.getString("upi_collect");
		String static_QR = js.getString("static_QR");
		String dynamic_QR = js.getString("dynamic_QR");
		String partnerBy = js.getString("partnerBy");
		log.info("merBasicSetup");
		List<MerchantBasicSetupDto> merBasicSetup = null;
		try {
			log.info("merBasicSetup::::::::::::::");

			merBasicSetup = merchantService.setMerchantBasicInfo(merchantId, isAutoRefund, hours, minutes, isPushUrl,
					pushUrl, settlementCycle, merchantDashboardRefund, mdDisableRefundCc, mdDisableRefundDc,
					mdDisableRefundNb, mdDisableRefundUpi, mdDisableRefundWallet, refundApi, refundApiDisableCc,
					refundApiDisableDc, refundApiDisableNb, refundApiDisableUpi, refundApiDisableWallet,
					integrationType, isretryAllowed, bpsEmailNotification, bpsSmsNotification, bpsMailReminder,
					Reporting_cycle, upi_loader, upi_intent, upi_collect, static_QR, dynamic_QR, partnerBy);

			secondCon.insertIntoSecondSchema(username, "update-Merchant-BasicSetup", jsonBody, merBasicSetup.toString(),
					ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Update-Merchant-BasicSetup-Exception", jsonBody,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return merBasicSetup;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getMerchant-BasicSetupbyId")
	public List<MerchantBasicSetupDto> getByMerchantId(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);
		String merchantId = js.getString("merchantid");

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;

		String ipAddress = null;

		List<MerchantBasicSetupDto> merBasicSetupDetails = null;
		try {

			merBasicSetupDetails = merchantService.getMerchantBasicInfo(merchantId);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "getMerchant-BasicSetupbyId", jsonBody,
					merBasicSetupDetails.toString(), ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "getMerchant-BasicSetupbyId-Exception", jsonBody,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return merBasicSetupDetails;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/download-uploadimage")
	@ResponseBody
	public String getuploadImage(@RequestParam String urlfile, String docname, HttpServletResponse resp)
			throws IOException {

//		JSONObject js = new JSONObject(jsonBody);		
//		int businesstypeId = js.getInt("urlfile");
		String urlfile1 = urlfile;
		String fileName = docname;
		Path path = Path.of(urlfile1);
		File file = new File(urlfile1);

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		try {
			if (file.exists()) {
				if (fileName.indexOf(".doc") > -1)
					resp.setContentType("application/msword");
				if (fileName.indexOf(".docx") > -1)
					resp.setContentType("application/msword");
				if (fileName.indexOf(".xls") > -1)
					resp.setContentType("application/vnd.ms-excel");
				if (fileName.indexOf(".csv") > -1)
					resp.setContentType("application/vnd.ms-excel");
				if (fileName.indexOf(".ppt") > -1)
					resp.setContentType("application/ppt");
				if (fileName.indexOf(".pdf") > -1)
					resp.setContentType("application/pdf");
				if (fileName.indexOf(".zip") > -1)
					resp.setContentType("application/zip");

				String headerskey = "Content-Disposition";
				String headervalue = "attachment; filename=\"" + path.getFileName();
				resp.setHeader("Content-Transfer-Encoding", "binary");
				resp.setHeader(headerskey, headervalue);
				BufferedOutputStream outputstream = new BufferedOutputStream(resp.getOutputStream());
				FileInputStream fil = new FileInputStream(urlfile1);
				int len;
				byte[] buf = new byte[1024];
				while ((len = fil.read(buf)) > 0) {
					outputstream.write(buf, 0, len);
				}
				outputstream.close();
				resp.flushBuffer();

			}

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "download-uploadimage", urlfile + " " + docname, "Success",
					ipAddress);
		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "download-uploadimage-Exception", urlfile + " " + docname,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return "Success";

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/download-uploadfiles/")
	@ResponseBody
	public Object getuploadFiles(@RequestParam String urlfile, String docname, HttpServletResponse resp)
			throws IOException {
//		JSONObject js = new JSONObject(jsonBody);		
//		int businesstypeId = js.getInt("urlfile");
		String urlfile1 = urlfile;
		String fileName = docname;
		Path path = Path.of(urlfile1);
		File file = new File(urlfile1);
		byte[] fileContent = Files.readAllBytes(file.toPath());
//		byte[] encoded = Base64.encodeBase64(FileUtils.readAllBytes(file));

		JSONObject js = new JSONObject();
		js.put("Data", Base64.getEncoder().encodeToString(fileContent));

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");

		secondCon.insertIntoSecondSchema(username, "download-uploadfiles", urlfile + " " + docname, js.toString(),
				ipAddress);

		return js.toMap();

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/MerchantCreationDetails", produces = "application/json")
	public ArrayList MerchantCreationDetails(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String Mid = js.getString("Mid");
		ArrayList MerchantDetails = null;

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		try {
			MerchantDetails = merchantService.GetCreationDetails(Mid);
			log.info("MerchantDetails::::::" + MerchantDetails);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "MerchantCreationDetails", jsonBody, MerchantDetails.toString(),
					ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "MerchantCreationDetails-Exception", jsonBody, stackTraceString,
					ipAddress);
			e.printStackTrace();
		}
		return MerchantDetails;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/ChangeMerchantStatus")
	public String ChangeMerchantStatus(@RequestBody String jsonBody) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		// secondCon.insertIntoSecondSchema(username, "Change Merchant Status",
		// jsonBody,jsonBody,ipAddress);

		JSONObject js = new JSONObject(jsonBody);
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String Date = dateFormat.format(date);
		String Mid = js.getString("Mid");
		String Status = js.getString("Status");
		String Remark = js.getString("Remark");
		String Type = js.getString("Approvel_type");
		String Added_By = js.getString("Added_By");

		String MerchantDetails = null;
		try {
			if (Status.equalsIgnoreCase("Suspended") && Type.equalsIgnoreCase("Suspended")) {
				Date = js.getString("Date");
			}

			MerchantDetails = RmsConfigServices.Merchant_Status_change(Mid, Status, Remark, Type, Date, Added_By);
			// System.out.println("Mid:::"+Mid+"Status:::"+Status+"Type:::"+Type);
			log.info("MerchantDetails:::::::::::::::::" + MerchantDetails);

			log.info("MerchantDetails:::::::::::::::::" + MerchantDetails.toString());

			secondCon.insertIntoSecondSchema(username, "Change Merchant Status", jsonBody, MerchantDetails, ipAddress);

			System.out.println("Mid {}" + Mid + "Status {}" + Status + "Type {}" + Type);
			if (Mid != null && !Mid.isBlank() && Status.equalsIgnoreCase("Active") && Type.isBlank()) {
				log.info("MerchantDetails:::" + MerchantDetails);

				if (MerchantDetails != null) {
					JSONArray jsonArray = new JSONArray(MerchantDetails);

					log.info("jsonArray:::::::::::" + jsonArray.toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1 = jsonArray.getJSONObject(i);
						String MerchantId = jsonObject1.optString("MerchantId");
						String merchantName = jsonObject1.optString("merchant_name");
						String emailId = jsonObject1.optString("email_id");
						String merReturnUrl = jsonObject1.optString("mer_return_url");
						String transactionKey = jsonObject1.optString("transaction_key");
						String password = jsonObject1.optString("password");
						String resellerId = jsonObject1.optString("reseller_id");
						String resellerEmailId = jsonObject1.optString("reseller_email_id");
						String oldStatus = jsonObject1.optString("oldStatus");
						String emailTrigger = jsonObject1.optString("emailTrigger");
						String resellerStatus = jsonObject1.optString("reseller_status");

						log.info("jsonObject1:::" + jsonObject1);

						log.info("emailTrigger:::" + emailTrigger);

						if (emailTrigger.equalsIgnoreCase("0") && oldStatus.equalsIgnoreCase("Pending")) {

							log.info("emailTrigger:::" + emailTrigger);
							log.info("MerchantId:::" + MerchantId);
							log.info("merchantName:::" + merchantName);
							log.info("emailId:::" + emailId);
							log.info("merReturnUrl:::" + merReturnUrl);
							log.info("transactionKey:::" + transactionKey);
							log.info("password:::" + password);
							log.info("resellerId:::" + resellerId);
							log.info("resellerEmailId:::" + resellerEmailId);
							log.info("resellerStatus:::" + resellerStatus);

//							emailService.sendSimpleMessage(MerchantId, merchantName, emailId, merReturnUrl,
//									transactionKey, password, resellerId, resellerEmailId, resellerStatus);
						}
					}

				}
			}

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Change Merchant Status-Exception", stackTraceString,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}
		return MerchantDetails;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getKyc-DocumentList/")
	public List<BusinessTypeDto> getDocumentList(@RequestBody String jsonBody) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = userDetails.optString("Username");

		String ipAddress = userDetails.optString("ipAddress");

		JSONObject js = new JSONObject(jsonBody);
		int businesstypeId = js.getInt("businesstypeId");
		String merchantId = js.getString("merchantId");

		List<BusinessTypeDto> bTypeDto = Collections.emptyList();
		try {

			bTypeDto = merchantService.getKycDocumentList(businesstypeId, merchantId);
			secondCon.insertIntoSecondSchema(username, "Get KYC DocumentList", jsonBody, bTypeDto.toString(),
					ipAddress);

		} catch (Exception e) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();

			secondCon.insertIntoSecondSchema(username, "Get KYC DocumentList-Exception", stackTraceString,
					stackTraceString, ipAddress);

			e.printStackTrace();
		}

		return bTypeDto;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/create-merchant-bulkupload")
	public ResponseEntity<ResponseMessage> bulkupload(@RequestParam MultipartFile file, String createdby) {
		String message = "";

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		if (BulkCsvHelper.hasCSVFormat(file)) {
			try {
				merchantService.save(file, createdby);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();

				username = userDetails.optString("Username");

				ipAddress = userDetails.optString("ipAddress");

				secondCon.insertIntoSecondSchema(username, "create-merchant-bulkupload", "Bulk File Upload", message,
						ipAddress);

				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";

				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();

				secondCon.insertIntoSecondSchema(username, "create-merchant-bulkupload-Exception",
						"Bulk File Upload " + message, stackTraceString, ipAddress);

				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/Verify-CreateMerchantRecords")
	public List<BulkMerchantList> getVerifyCsvData(@RequestBody String jsonBody) {

		JSONObject js = new JSONObject(jsonBody);
		String user = js.getString("username");

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		List<BulkMerchantList> bTypeDto = null;
		try {
			bTypeDto = merchantService.getVerifyCsvData(user);

			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Get KYC DocumentList", jsonBody, bTypeDto.toString(), ipAddress);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bTypeDto;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/create-merchantmdr-bulkupload")
	public List<BulkMdrList> createMerchantMdrBulkCsv(@RequestParam MultipartFile file) {
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject();
		Map<String, Object> MsgFile = null;
		Map<String, Object> Msg = null;
		String message = "";
		List<BulkMdrList> listofMDR = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		if (MdrBulkCsvHelper.hasCSVFormat(file)) {
			try {
				MsgFile = merchantService.saveMDR(file);

				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				log.info("Status MDR Bulk File upload :::success " + MsgFile);
				listofMDR = merchantService.createMerchantMdrBulkCsv();
				log.info("Status MDR Bulk File upload :::success " + listofMDR);
				js1.put("Message Upload file", MsgFile);
				
				username = userDetails.optString("Username");

				ipAddress = userDetails.optString("ipAddress");

				secondCon.insertIntoSecondSchema(username, "create-merchantmdr-bulkupload", message, listofMDR.toString(), ipAddress);

				

			} catch (Exception e) {
				
				
				
				message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e.getMessage();
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
							
				secondCon.insertIntoSecondSchema(username, "create-merchantmdr-bulkupload-Exception", message, stackTraceString, ipAddress);
				
				log.info("Status MDR Bulk File upload exception " + message);
			}
		}

		return listofMDR;

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/CheckAccountBank")
	public Map<String, Object> CheckMerchantBank(@RequestBody String Fields) {
		JSONObject js = new JSONObject(Fields);
		Map<String, Object> Response = merchantService.CheckMerchantBank(js.getString("Product_Id"),
				js.getString("Account"));
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");

		secondCon.insertIntoSecondSchema(username, "CheckAccountBank", Fields, Response.toString(), ipAddress);
		
		return Response;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/GetRemarks")
	public ArrayList GetRemarks(@RequestBody String RequestParam) throws Exception {
		JSONObject js = new JSONObject(RequestParam);
		String iType = js.getString("Type");
		String Otype = js.getString("ModuleType");
		String iMerchantId = js.getString("merchant_id");
		String AppType = js.getString("AppType");
		ArrayList Response = merchantService.getRemarks(iType, Otype, iMerchantId, AppType);

		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");

		secondCon.insertIntoSecondSchema(username, "GetRemarks", RequestParam, Response.toString(), ipAddress);
		return Response;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/GetInstrumentActivation")
	public String InstrumentActivation(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String InstrumentId = js.getString("InstrumentId");
		String MerchantId = js.getString("MerchantId");
		String SpID = js.getString("SpID");
		String BankId = js.getString("BankId");
		String BrandId = js.getString("BrandId");
		String App_Status = js.getString("App_Status");
		String App_Remark = js.getString("App_Remark");
		String AddedBy = js.getString("AddedBy");
		String Id = js.getString("Id");

		String MerchantDetails = null;
		String statusDataVal = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		try {

			if (SpID.equalsIgnoreCase(yesBankSP_ID) && InstrumentId.equalsIgnoreCase("UPI")) {

				String merVirtualAdd = js.getString("MerVirtualAdd");

				log.info("inside the if condition:for yes bank upi::::::" + merVirtualAdd);
				statusDataVal = yesBankUPI.getSubMerchantDetailsInsert(MerchantId, SpID, InstrumentId, merVirtualAdd,
						App_Status);
				log.info("statusDataVal:::::::" + statusDataVal);
				if (statusDataVal.equalsIgnoreCase("2")) {
					MerchantDetails = merchantService.getInstrumentType1Yes(InstrumentId, MerchantId, SpID, BankId,
							BrandId, App_Status, App_Remark, AddedBy, Id, merVirtualAdd);
				}

				else {
					JSONObject requestjson = new JSONObject();
					requestjson.put("statusDataVal", statusDataVal);
					MerchantDetails = requestjson.toString();
				}

			} else {
				log.info("inside the else condition");
				MerchantDetails = merchantService.getInstrumentType(InstrumentId, MerchantId, SpID, BankId, BrandId,
						App_Status, App_Remark, AddedBy, Id);
			}
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Get Instrument Activation", jsonBody, MerchantDetails, ipAddress);

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Get Instrument Activation-Exception", jsonBody, stackTraceString, ipAddress);


			
			e.printStackTrace();
		}
		return MerchantDetails;
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/GetYesBankResponse")
	public ResponseEntity<?> getMerchantMdrlistByMID(@RequestBody String reqBody) {
		JSONObject obj = null;
		JSONObject object1 = null;
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		try {
			JSONObject jsre = new JSONObject(reqBody);
			String MerchantId = jsre.getString("MerchantId");
			String SpID = jsre.getString("SpID");
			String InstrumentId = jsre.getString("InstrumentId");

			log.info("MerchantId::::" + MerchantId + "::::SpID::::" + SpID + "::InstrumentId:::" + InstrumentId);

			String Response = merchantService.getYesUPIDeatils(MerchantId, SpID, InstrumentId);

			Response = Response.replaceAll("=", ":");
			log.info("Response:::::::" + Response);

			JSONArray arrayjson = new JSONArray(Response);

			object1 = arrayjson.getJSONObject(0);
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "GetYesBankResponse", reqBody, object1.toString(), ipAddress);

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "GetYesBankResponse-Exception", reqBody, stackTraceString, ipAddress);

			log.info("Exception:::::::" + e);
		}
		return ResponseEntity.ok(object1.toMap());
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/GetInstrumentList")
	public String InstrumrntList(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String MerchantId = js.getString("MerchantId");
		String MerchantInstrumentType = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		try {
			MerchantInstrumentType = merchantService.getInstrumentList(MerchantId);
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "InstrumentList", jsonBody, MerchantInstrumentType.toString(), ipAddress);
			
			
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "InstrumentList-Exception", jsonBody, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}
		return MerchantInstrumentType;
	}

	@PostMapping("/DeleteGetInstrument")
	public Map<String, Object> DeleteInstrumrnt(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String MerchantId = js.getString("MerchantId");
		String id = js.getString("Id");
		JSONObject js1 = new JSONObject();

		String MerchantInstrumentType = null;
		try {
			MerchantInstrumentType = merchantService.DeleteInstrument(id);
			js1.put("Status", "1");
			MerchantInstrumentType = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return js1.toMap();
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload-Otherdocs")
	public List<MerchantKycDoc> uploadOtherDocs(@RequestParam String merchantId, String docType) {

		String MerchantId = merchantId;
		String DocType = docType;
		List<MerchantKycDoc> kycdto = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		try {

			MerchantKycDoc merchantKycDoc = new MerchantKycDoc();
			kycdto = merchantService.insertKycOtherDoc(MerchantId, DocType);
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Upload doc", "Uploading docs for "+merchantId, kycdto.toString(), ipAddress);

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Upload-Otherdocs-Exception", "Uploading docs "+merchantId, stackTraceString, ipAddress);
			
			e.printStackTrace();
		}

		return kycdto;

	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestBody String jsonBody,
			@RequestHeader("x-request-src") String authString) {
		JSONObject js = new JSONObject(jsonBody);
		String ValiduserId = js.getString("userId");
		Map<String, Object> responseMsg = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		try {
			log.info("Forget Password userId :::::----------------------------------- " + ValiduserId);
			log.info("Forget Password Header :::::----------------------------------- " + authString);
			responseMsg = merchantService.forgetPasswordService(ValiduserId, authString);
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");

			secondCon.insertIntoSecondSchema(username, "Forget Password", jsonBody, responseMsg.toString(), ipAddress);
			
		} catch (Exception e) {
			log.info("Error in Forget Password :::::----------------------------------- " + e.getMessage());
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Forget-Password-Exception",jsonBody, stackTraceString, ipAddress);
			
		}
		return ResponseEntity.ok(responseMsg);

	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String tokenValidation = js.getString("token");
		String passwordChange = js.getString("password");
		Map<String, Object> responseMsg = merchantService.resetPasswordService(tokenValidation, passwordChange);
		JSONObject js1 = new JSONObject();
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");
		
		
		secondCon.insertIntoSecondSchema(username, "Reset Password", jsonBody, responseMsg.toString(), ipAddress);
		
		
		return ResponseEntity.ok(responseMsg);

	}

	@PostMapping("/reset-password-internal")
	public ResponseEntity<?> resetPasswordInternal(@RequestBody String jsonBody, @RequestHeader String Authorization) {
		JSONObject js = new JSONObject(jsonBody);
		String oldpassword = js.getString("oldpassword");
		String newpassword = js.getString("newpassword");
		String JwtToken = null;
		JwtHelperUtil generalUtil = new JwtHelperUtil();
		JwtToken = Authorization.substring(7);
		String userId = generalUtil.extractUsername(JwtToken);
		Map<String, Object> responseMsg = merchantService.resetPasswordServiceInternal(oldpassword, newpassword,
				userId);
		JSONObject js1 = new JSONObject();
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		username = userDetails.optString("Username");

		ipAddress = userDetails.optString("ipAddress");
		
		secondCon.insertIntoSecondSchema(username, "Reset Password Internal", jsonBody, responseMsg.toString(), ipAddress);
		
		
		return ResponseEntity.ok(responseMsg);

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(value = "/TxnAmtVolume", produces = "application/json")
	public ResponseEntity<Object> TxnAmtVolume(@RequestBody String jsonBody) throws Exception {

		Response res = new Response();

		String message = "";
		JSONObject resultData = null;
		JSONObject resultData1 = null;
		JSONObject resultFinal = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		
		try {
			log.info("inside the TxnAmtVolume:::::");
			log.info("jsonBody::::::::" + jsonBody);
			JSONObject js = new JSONObject(jsonBody);
			log.info("js::::::" + js);

			String iType = js.getString("iType");
			String iUserID = js.getString("iUserID");
			String iDate = js.getString("iDate");

			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;

			date = (Date) formatter.parse(iDate);
			String outputDate = df.format(date);
			System.out.println(":::::outputDate::::" + outputDate);

			String resp = null;

			resp = merchantService.getTxnAmtVolume(iType, iUserID, outputDate);
			JSONArray jsArray = new JSONArray(resp);

			List<String> list = new ArrayList<>();
			List<String> list1 = new ArrayList<>();
			List<String> list2 = new ArrayList<>();
			List<String> list3 = new ArrayList<>();

			Map<String, String> Trans_vol = new HashMap<String, String>();
			Map<String, String> Active_Merch = new HashMap<String, String>();
			Map<String, String> Trans_count = new HashMap<String, String>();

			for (int i = 0; i < jsArray.length(); i++) {
				JSONObject jsonObject1 = jsArray.getJSONObject(i);
				String value1 = jsonObject1.optString("Transaction_Count");
				String value2 = jsonObject1.optString("Active_Merchant");
				String value3 = jsonObject1.optString("Date_Value");
				String value4 = jsonObject1.optString("Transaction_volume");

				list.add(value1);
				list2.add(value3);
				list3.add(value4);
				list1.add(value2);
			}

			log.info("list2::::" + list2.size());

			// trans_volumn
			for (int i = 0; i < list2.size() && i < list3.size(); i++) {
				Trans_vol.put(list2.get(i), list3.get(i));

			}
			log.info("Trans_vol:::::::::::" + Trans_vol.toString());

			// active merchant hash map
			for (int i = 0; i < list2.size() && i < list1.size(); i++) {
				Active_Merch.put(list2.get(i), list1.get(i));

			}
			log.info("Active_Merch:::::::::::" + Active_Merch);

			// trans_count hash map
			for (int i = 0; i < list2.size() && i < list.size(); i++) {
				Trans_count.put(list2.get(i), list.get(i));

			}

			log.info("Trans_count:::::::::::" + Trans_count);

			JSONObject trn_vol = new JSONObject(Trans_vol);
			JSONObject active_merch = new JSONObject(Active_Merch);
			JSONObject trn_count = new JSONObject(Trans_count);
			log.info("trn_vol:::::" + trn_vol);

			// get cuurent data

			String curr_trns_vol = "";
			String curr_Trans_count = "";
			String curr_Active_Merch = "";

			if (Trans_vol.containsKey(outputDate)) {

				curr_trns_vol = Trans_vol.get(outputDate);

			}
			log.info("curr_trns_vol::::" + curr_trns_vol);

			if (Trans_count.containsKey(outputDate)) {

				curr_Trans_count = Trans_count.get(outputDate);

			}
			log.info("curr_Trans_count::::" + curr_Trans_count);

			if (Active_Merch.containsKey(outputDate)) {

				curr_Active_Merch = Active_Merch.get(outputDate);

			}
			log.info("curr_Active_Merch::::" + curr_Active_Merch);

			resultData1 = new JSONObject();
			resultData1.put("current_Date", outputDate);
			resultData1.put("current_TranV", curr_trns_vol);
			resultData1.put("current_ac_Merchant", curr_Active_Merch);
			resultData1.put("current_TranCount", curr_Trans_count);
			log.info("resultData1:::::" + resultData1);

			resultData = new JSONObject();
			resultData.put("Transaction_volume", trn_vol);
			resultData.put("Active_Merchant", active_merch);
			resultData.put("Transaction_Count", trn_count);

			log.info("resultData:::::" + resultData);

			resultFinal = new JSONObject();
			resultFinal.put("Current_Data", resultData1);
			resultFinal.put("results", resultData);

			message = "Data For Dashboard Analytics !";

			res.setStatus(true);
			res.setMessage(message);
			res.setResult(resultData);
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			
			secondCon.insertIntoSecondSchema(username, "TxnAmtVolume", jsonBody, resultFinal.toString(), ipAddress);
			

		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "TxnAmtVolume-Exception",jsonBody, stackTraceString, ipAddress);
			

			log.info("e::::::", e);
			message = "Error occured : " + e.getMessage();
			res.setStatus(false);
			res.setMessage(message);

		}

		return ResponseEntity.ok(resultFinal.toString());

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/uploadLogo")
	public Map<String, Object> uploadFile(@RequestParam MultipartFile uploadLogo, String addedBy,
			String merchantId) {
		JSONObject js1 = new JSONObject();
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;
		
		
		

		try {
			Map<String, Object> resultData = merchantService.uploadFileStatus(uploadLogo, addedBy, merchantId);

			js1.put("Data", resultData);
			
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			
			
			secondCon.insertIntoSecondSchema(username, "Upload Logo", merchantId, js1.toString(), ipAddress);
			

			log.info("js1.toMap()::::::::" + js1.toMap());

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "TxnAmtVolume-Exception",merchantId, stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
		}
		return js1.toMap();
	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload-whitelistLogo")
	public Map<String, Object> uploadFileLogoWhitelist(@RequestParam MultipartFile uploadLogo,
			String addedBy, String merchantId) {
		JSONObject js1 = new JSONObject();
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);
		String username = null;
		String ipAddress = null;

		try {
			Map<String, Object> resultData = merchantService.uploadFileLogoWhitelist(uploadLogo, addedBy, merchantId);

			js1.put("Data", resultData);
			
			username = userDetails.optString("Username");

			ipAddress = userDetails.optString("ipAddress");
			
			
			secondCon.insertIntoSecondSchema(username, "Upload-whitelistLogo", merchantId, js1.toString(), ipAddress);

			log.info("js1.toMap()::::::::" + js1.toMap());

		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
						
			secondCon.insertIntoSecondSchema(username, "Upload-whitelistLogo-Exception",merchantId, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
		return js1.toMap();
	}

}
