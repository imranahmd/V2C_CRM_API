package com.crm.services;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.ParseException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.crm.Repository.BulkCsvMerchantRepo;
import com.crm.Repository.MdrBulkCsvRepo;
import com.crm.Repository.MerchantBankRepo;
import com.crm.Repository.MerchantKycDocRepo;
import com.crm.Repository.MerchantMdrRepo;
import com.crm.Repository.MerchantRepository;
import com.crm.Repository.UserRepository;
import com.crm.ServiceDaoImpl.RMSServiceDaoImpl;
import com.crm.dto.AdditionContact;
import com.crm.dto.BulCsvMerchantMdrDto;
import com.crm.dto.BulkCsvDto;
import com.crm.dto.BulkCsvMerMdrDto;
import com.crm.dto.BulkCsvMerchantDto;
import com.crm.dto.BulkMdrList;
import com.crm.dto.BulkMerchantList;
import com.crm.dto.BusinessTypeDto;
import com.crm.dto.MerchantBasicSetupDto;
import com.crm.dto.MerchantCreationDto;
import com.crm.dto.MerchantDocDto;
import com.crm.dto.MerchantDto;
import com.crm.dto.MerchantPaginationDto;
import com.crm.helper.BulkCsvHelper;
import com.crm.helper.MdrBulkCsvHelper;
import com.crm.model.BulkCsvMdr;
import com.crm.model.BulkCsvMerchant;
import com.crm.model.MerchantBank;
import com.crm.model.MerchantKycDoc;
import com.crm.model.MerchantList;
import com.crm.model.User;
import com.crm.util.GeneralUtil;
import com.crm.util.GenerateRandom;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import freemarker.template.TemplateException;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.MessagingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;



@Service
public class MerchantService {
	 private static final Logger logger = LoggerFactory.getLogger(MerchantService.class);
	 private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	 public static String uploadDirectoryWhileListLogo = "/home/WhiteListLogo";
	@Autowired
	private MerchantRepository merchantRepo;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MerchantBankRepo merchantBankRepo;
	
	@Autowired
	private MerchantKycDocRepo merchantKycDocRepo;
	
	@Autowired
	private BulkCsvMerchantRepo bulkCsvMerchantRepo;
	
	@Autowired
	private MdrBulkCsvRepo mdrBulkCsvRepo;

	@Autowired
	private RMSServiceDaoImpl RMSServiceDaoImpl;
	
	@Autowired
	private MerchantMdrRepo merchantMdrRepo;
	
//	@Autowired
//    private EmailService emailService;
	
 @Autowired
private JdbcTemplate JdbcTemplate;
 
 
    @Value("${file.upload.location}")
	private String fileUploadLocation;
 
 

	public MerchantPaginationDto getMerchantByName(String fromDate, String toDate, String merchantId, String resellerId, String name, int norecord, int pageno, JSONObject json,String sp) {
		MerchantPaginationDto dto = new MerchantPaginationDto();
		dto.setNumberOfRecords(norecord);
		dto.setPageNumber(pageno);
	
		logger.info("::::::::fromDate:"+fromDate);

		logger.info(":::::::toDate:"+toDate);
		logger.info(":::::::merchantId:+"+merchantId);
		logger.info(":::::::resellerId:"+resellerId);
		logger.info("::::::name::"+name);
		logger.info(":::::norecord:::"+norecord);
		logger.info("::::::pageno::"+pageno);
		logger.info("::::sp::::"+sp);
		
//fromDate, toDate, merchantId, resellerId, name , norecord, pageno		
		List<MerchantDto> merchants = new ArrayList<>();
		List<Object[]> dbMerchants = merchantRepo.findByMerchantsByDateNameId(fromDate, toDate, merchantId, resellerId, name , norecord, pageno,sp);
		
		
		logger.info("::::::::::::::dbMerchants:::::::::::::"+dbMerchants.toString());
		JSONArray array = new JSONArray();
		JSONObject js1= new JSONObject();
		
		for (Object[] obj : dbMerchants) {
			MerchantDto merchant = new MerchantDto();
			merchant.setBusinessName((String) obj[3]);
			merchant.setMerchantId((String) obj[1]);
			merchant.setName((String) obj[2]);
			merchant.setId( BigInteger.valueOf(  (Long) obj[0]  ) );
			merchant.setContactperson((String) obj[4]);
			merchant.setContactno((String) obj[5]);
			merchant.setEmailid((String) obj[6]);
			merchant.setCity((String) obj[7]);
			merchant.setState((String) obj[8]);
			merchant.setIsdeleted(((Character) obj[9]).toString());
			merchant.setStatus((String) obj[10]);
			merchant.setCreated_on((String)obj[11]);
			merchant.setDateCorporation_on((String)obj[12]);
			merchant.setRiskApproval((String)obj[13]);
			merchant.setKYCApproval((String)obj[14]);
		    merchant.setBasicSetupApproval((String)obj[15]);
		    merchant.setReseller_Id((String)obj[16]);
		    merchant.setReseller_Name((String)obj[17]);
		    merchant.setSales_lead((String)obj[18]);
		    merchants.add(merchant);
		    
		  }
//		js1.put("", array);
		List<String> merchantMdr = merchantMdrRepo.getrecordCount(fromDate, toDate, merchantId, resellerId, name , norecord, pageno, sp );
		
		logger.info(":::::::::::::::::::::::::::merchantMdr:::"+merchantMdr.toString());
		JSONArray jsonArray =null;
		jsonArray= new JSONArray(merchantMdr);
		JSONObject rec = jsonArray.getJSONObject(0);
		Long noRecords = rec.getLong("TotalRecords");		
//		dto.setColumns(array.toList());
		dto.setMerchants(merchants);
		dto.setTotalRecords(noRecords);
		
		logger.info("dto::::::::::::"+dto);
		return dto;

	}
	
	
	
	

	public List<MerchantDto>  createMerchant(
			MerchantCreationDto creationDto) throws JsonMappingException, JsonProcessingException {
		User user = new User();
		if(creationDto.getMerchantid() != "" ) {
			String merchantId = creationDto.getMerchantid();
			
			user.setMerchantId(merchantId);
		}else {
		String merchantId = merchantRepo.findMerchantId();
		GeneralUtil generalUtil = new GeneralUtil();
		String firstName = creationDto.getMerchantname().contains(" ") ? creationDto.getMerchantname().split(" ")[0]
				: creationDto.getMerchantname();
		String password = generalUtil.crypt(firstName.length()>11?firstName.substring(0,11):firstName+"@"+new SimpleDateFormat("Y").format(new Date()));
				//(firstName + "@2022");
		
		user.setFullName(creationDto.getMerchantname());
		user.setBlocked(false);
		user.setCreatedBy(creationDto.getCreatedBy());
		user.setUserId(merchantId);
		user.setMerchantId(merchantId);
		user.setPassword(password);
		long millis = System.currentTimeMillis();
		user.setCreatedOn(new java.sql.Date(millis));
		user.setEmailId(creationDto.getEmailId());
		user.setGroupId(2);
		user.setRoleId(2);
		user.setIsActive(true);
		user.setIsDeleted('N');
		user.setContactNumber(creationDto.getContactNumber());

		user = userRepository.save(user);
		System.out.println(creationDto);
		}
		List<MerchantDto> merchants = new ArrayList<>();

		System.out.println("creationDto:::::::::value:::::::::::::::::::: "+creationDto.getDateOfIncorporation());

		 String response = null ;
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) creationDto.getAdditional_contact();
		 JSONArray ar=null;
		 
		 
		 JSONObject responsejson=null;
		if (arrayList.isEmpty()) {
			response = arrayList.toString();
			 System.out.println("response::::"+response);
		}
		else {
			System.out.println("arrayList::::::::::::"+arrayList.size());
			Gson gson = new Gson();
			   response = gson.toJson(arrayList);

			  System.out.println("response::::"+response);


		}
		
		String response1 = null ;
		/*
		 * if(creationDto.getWhitelistPath().isEmpty()) { response1 =
		 * arrayList.toString(); System.out.println("response::::"+response1); }else {
		 * Gson gson = new Gson(); response1 =
		 * gson.toJson(creationDto.getWhitelistPath());
		 * System.out.println("whitelistpath::::"+response1); }
		 */
		
		List<Object[]> savedMerchant = 
				merchantRepo.createMerchantByNameEmail(creationDto.getContactperson(), creationDto.getContactNumber(),
						creationDto.getEmailId(), creationDto.getCompanypan(), creationDto.getMerchantname(),
						creationDto.getBusinessname(), creationDto.getBusinesstype(), creationDto.getDateOfIncorporation(),
						creationDto.getMerchantcatagorycode(), creationDto.getMerchantsubcatagory(),
						creationDto.getBusinessmodal(), creationDto.getTurnoverfyear(), creationDto.getMonthlyincome(),
						creationDto.getAvgamtpertransaction(), creationDto.getAuthorisedpan(), creationDto.getNameaspan(),
						creationDto.getRegisteraddress(), creationDto.getPincode(), creationDto.getCity(), creationDto.getState(),
						creationDto.getGstno(), creationDto.getWebsite(), creationDto.getTestaccess(),
						creationDto.getInstruments(), user.getMerchantId(), GenerateRandom.randomAlphaNumeric(32), creationDto.getIsPanVerified(), creationDto.getiResellerId(),
						creationDto.getIsCompanyPanVerify(), creationDto.getiIsGSTVerify(),creationDto.getiCompanyPanVerifyName(),creationDto.getiGSTVerifyName(),
						creationDto.getName_as_perpan(),creationDto.getSource(),creationDto.getMerReturnUrl(),response, creationDto.getSales_lead(),creationDto.getLogoPath(), 
						creationDto.getPartners_type(),creationDto.getBank_id());

		for (Object[] obj : savedMerchant) {
			MerchantDto merchant = new MerchantDto();
			merchant.setBusinessName((String) obj[5]);
			merchant.setMerchantId((String) obj[20]);
			merchant.setName((String) obj[4]);
//			merchant.setId((BigInteger) obj[0]);
			merchant.setContactperson((String) obj[0]);
			merchant.setContactno((String) obj[1]);
			merchant.setEmailid((String) obj[2]);
			//merchant.setCity((String) obj[7]);
			//merchant.setState((String) obj[8]);
			merchant.setIsdeleted("N");
			
			merchant.setLogoPath((String) obj[31]);
			merchants.add(merchant);
		}
		
		return merchants;

	}
	



	public List<MerchantBank> getMerchantBank(String merchantId){
		List<MerchantBank> dbmerchantBank = merchantBankRepo.findByMerchantId(merchantId); 		
		
		
		return dbmerchantBank;
	}
	
	
	
	

	public List<MerchantBank> createMerchantbank(MerchantBank merchantBank) {
		MerchantBank merchantbank = new MerchantBank();
		
		int i = merchantBankRepo.CheckAccount(merchantBank.getProductId(), merchantBank.getMerchantId());
		int j = merchantBankRepo.CheckSameAccount(merchantBank.getAccountNumber(),merchantBank.getMerchantId());
		System.out.println("Check Condtions " +i);
		if(i==0 && j==0) {
			merchantbank.setAccountNumber(merchantBank.getAccountNumber());
			merchantbank.setMerchantId(merchantBank.getMerchantId());
			merchantbank.setProductId(merchantBank.getProductId());
			
			merchantbank.setId(merchantBank.getId());
			merchantbank.setBankName(merchantBank.getBankName());
			merchantbank.setAccount_holder(merchantBank.getAccount_holder());
			merchantbank.setIfscCode(merchantBank.getIfscCode());
			merchantbank.setIsVerified(merchantBank.getIsVerified());
			merchantbank.setRodate(merchantBank.getRodate());
			merchantbank.setMobileNo(merchantBank.getMobileNo());
			merchantbank.setEmailId(merchantBank.getEmailId());
			/*
			 * merchantbank.setError(""); merchantbank.setEscrow(merchantBank.getEscrow());
			 * merchantbank.setDefault_id(merchantBank.getDefault_id());
			 * merchantbank.setTransaction_type(merchantBank.getTransaction_type());
			 * merchantbank.setBenificiary_code(merchantBank.getBenificiary_code());
			 * merchantbank.setEscrow_bank(merchantBank.getEscrow_bank());
			 */
			merchantbank.setStatus("true");
		
			merchantbank = merchantBankRepo.save(merchantbank);
		}else if(merchantBank.getId() != null && merchantBank.getIsVerified().equals("N")) {
			MerchantList MerchantList= merchantRepo.findByMerchantId(merchantBank.getMerchantId());
			System.out.print("Merchant List:::::::::::::::::"+MerchantList.toString());
			//MerchantList.setStatus("Pending");
			MerchantList.setKycApporvel("0");
			merchantRepo.save(MerchantList);
			List<MerchantBank> dbmerchantBank = merchantBankRepo.findByPId(merchantBank.getId());
			merchantbank.setAccountNumber(merchantBank.getAccountNumber());
			merchantbank.setMerchantId(merchantBank.getMerchantId());
			merchantbank.setProductId(merchantBank.getProductId());
			
			merchantbank.setId(merchantBank.getId());
			merchantbank.setBankName(merchantBank.getBankName());
			merchantbank.setAccount_holder(merchantBank.getAccount_holder());
			merchantbank.setIfscCode(merchantBank.getIfscCode());
			merchantbank.setIsVerified(merchantBank.getIsVerified());
			merchantbank.setRodate(merchantBank.getRodate());
			merchantbank.setMobileNo(merchantBank.getMobileNo());
			merchantbank.setEmailId(merchantBank.getEmailId());
			merchantbank.setError("");
			merchantbank.setStatus("true");
			/*
			 * merchantbank.setEscrow(merchantBank.getEscrow());
			 * merchantbank.setDefault_id(merchantBank.getDefault_id());
			 * merchantbank.setTransaction_type(merchantBank.getTransaction_type());
			 * merchantbank.setBenificiary_code(merchantBank.getBenificiary_code());
			 * merchantbank.setEscrow_bank(merchantBank.getEscrow_bank());
			 */
			merchantbank = merchantBankRepo.save(merchantbank);
			/*
			 * try {
			 * logger.info("Updated Account:::::::::::{}"+merchantbank.getMerchantId());
			 * String ReturnValue =
			 * RMSServiceDaoImpl.UpdateApprovelProcess(merchantBank.getMerchantId(),
			 * "Account");
			 * 
			 * logger.info("Updated Account:In::::KycFlag:::::::{}"+ReturnValue);
			 * }catch(Exception e) { e.printStackTrace(); }
			 */
			} else if(merchantBank.getId() != null && merchantBank.getIsVerified().equals("Y")) {
					int checkProductId = merchantBankRepo.CheckAccount(merchantBank.getProductId(), merchantBank.getMerchantId());
					int checkAccount = merchantBankRepo.CheckSameAccount(merchantBank.getAccountNumber(),merchantBank.getMerchantId());
					String isVerified = merchantBankRepo.CheckStatus(merchantBank.getId());
					List<MerchantBank> merchantBanks1 = new ArrayList<>();
					if (!isVerified.equals(merchantBank.getIsVerified())) {
						List<MerchantBank> dbmerchantBank = merchantBankRepo.findByPId(merchantBank.getId());
						merchantbank.setAccountNumber(merchantBank.getAccountNumber());
						merchantbank.setMerchantId(merchantBank.getMerchantId());
						merchantbank.setProductId(merchantBank.getProductId());
						
						merchantbank.setId(merchantBank.getId());
						merchantbank.setBankName(merchantBank.getBankName());
						merchantbank.setAccount_holder(merchantBank.getAccount_holder());
						merchantbank.setIfscCode(merchantBank.getIfscCode());
						merchantbank.setIsVerified(merchantBank.getIsVerified());
						merchantbank.setRodate(merchantBank.getRodate());
						merchantbank.setMobileNo(merchantBank.getMobileNo());
						merchantbank.setEmailId(merchantBank.getEmailId());
						merchantbank.setError("");
						merchantbank.setStatus("true");
						/*
						 * merchantbank.setEscrow(merchantBank.getEscrow());
						 * merchantbank.setDefault_id(merchantBank.getDefault_id());
						 * merchantbank.setTransaction_type(merchantBank.getTransaction_type());
						 * merchantbank.setBenificiary_code(merchantBank.getBenificiary_code());
						 * merchantbank.setEscrow_bank(merchantBank.getEscrow_bank());
						 */
						merchantbank = merchantBankRepo.save(merchantbank);
						try {
							logger.info("Updated Account:::::::::::{}"+merchantbank.getMerchantId());
						 String ReturnValue = RMSServiceDaoImpl.UpdateApprovelProcess(merchantBank.getMerchantId(),"Account");

						 logger.info("Updated Account:In::::KycFlag:::::::{}"+ReturnValue);
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}else {
					
					MerchantBank MerchantBank = new MerchantBank();
					JSONObject js = new JSONObject();
					if(checkProductId > 0) {
						js.put("Error","Product_Id Already Exits");
					}
					if(checkAccount > 0) {
						js.put("Error","Account Number Already Verified");
					}
					MerchantBank.setAccount_holder("");
					MerchantBank.setAccountNumber("");
					MerchantBank.setBankName("");
					long id=0;
					MerchantBank.setId(id);
					MerchantBank.setIfscCode("");
					MerchantBank.setMerchantId("");
					MerchantBank.setProductId("");
					MerchantBank.setRodate("");
					MerchantBank.setError(js.toString());
					MerchantBank.setStatus("false");
					/*
					 * MerchantBank.setEscrow(""); MerchantBank.setDefault_id("");
					 * MerchantBank.setTransaction_type(""); MerchantBank.setBenificiary_code("");
					 * MerchantBank.setEscrow_bank("");
					 */
					merchantBanks1.add(MerchantBank);
					}
					return merchantBanks1;
			
		}else {
			List<MerchantBank> merchantBanks1 = new ArrayList<>();
			MerchantBank MerchantBank = new MerchantBank();
			JSONObject js = new JSONObject();
			if(i>0)
			{
			js.put("Error","Product_Id Already Exits");
			}
			if(j>0)
			{
				js.put("Error","Account Number Already Exits");

			}
			MerchantBank.setAccount_holder("");
			MerchantBank.setAccountNumber("");
			MerchantBank.setBankName("");
			long id=0;
			MerchantBank.setId(id);
			MerchantBank.setIfscCode("");
			MerchantBank.setMerchantId("");
			MerchantBank.setProductId("");
			MerchantBank.setRodate("");
			MerchantBank.setError(js.toString());
			MerchantBank.setStatus("false");
			/*
			 * MerchantBank.setEscrow(""); MerchantBank.setDefault_id("");
			 * MerchantBank.setTransaction_type(""); MerchantBank.setBenificiary_code("");
			 * MerchantBank.setEscrow_bank("");
			 */
			merchantBanks1.add(MerchantBank);
			return merchantBanks1;
		}
		
		List<MerchantBank> merchantBanks = new ArrayList<>();
		 merchantBanks = merchantBankRepo.findByMerchantId(merchantBank.getMerchantId()); 
			
		return merchantBanks;
	}
	
	
	public List<MerchantBank> getMerchantBankPid(Long Pid){
		
		Optional<MerchantBank> merchantBankResponse =  merchantBankRepo.findById(Pid);
		MerchantBank merchantbank = merchantBankResponse.get();
		List<MerchantBank> merchantBanks = new ArrayList<>();

			MerchantBank merchantbankdetails = new MerchantBank();

			merchantbankdetails.setAccountNumber(merchantbank.getAccountNumber());
			merchantbankdetails.setMerchantId(merchantbank.getMerchantId());
			merchantbankdetails.setProductId((merchantbank).getProductId());
			merchantbankdetails.setId((merchantbank).getId());			
			merchantbankdetails.setBankName((merchantbank).getBankName());
			merchantbankdetails.setAccount_holder((merchantbank).getAccount_holder());
			merchantbankdetails.setIsVerified(merchantbank.getIsVerified());
			merchantbankdetails.setIfscCode((merchantbank).getIfscCode());
			merchantbankdetails.setRodate((merchantbank).getRodate());
			/*
			 * merchantbankdetails.setEscrow((merchantbank).getEscrow());
			 * merchantbankdetails.setDefault_id((merchantbank).getDefault_id());
			 * merchantbankdetails.setTransaction_type((merchantbank).getTransaction_type())
			 * ;
			 * merchantbankdetails.setBenificiary_code((merchantbank).getBenificiary_code())
			 * ; merchantbankdetails.setEscrow_bank(merchantbank.getEscrow_bank());
			 */
						merchantBanks.add(merchantbankdetails);

		return merchantBanks;
	}

	
	
	public List<MerchantBank> updateMerchantbank(MerchantBank merchantBank) {
		MerchantBank mb = merchantBankRepo.findById(merchantBank.getId()).get();
		
		 merchantBankRepo.save(merchantBank);	
		
		List<MerchantBank> merchantBanks = new ArrayList<>();
		merchantBanks = merchantBankRepo.findByMerchantId(merchantBank.getMerchantId()); 
		
		return merchantBanks;
	}
	
	
	public List<MerchantBank> deleteMerchantbank(Long Pid) {
		
	
		Optional<MerchantBank> merchantBankdel = merchantBankRepo.findById(Pid);
		MerchantBank merchantbank = merchantBankdel.get();
		 merchantBankRepo.deleteById(Pid);
		
		List<MerchantBank> merchantBanks = new ArrayList<>();
		merchantBanks = merchantBankRepo.findByMerchantId(merchantbank.getMerchantId()); 
		
		return merchantBanks;
	}
	
	public MerchantKycDoc uploadKycDoc(MerchantKycDoc merchantKycDoc){
		
		MerchantKycDoc merchantkycDoc = new MerchantKycDoc();

		List<MerchantDocDto> merchantKycDoc1 = new ArrayList<>();
		
		
		return merchantkycDoc;
	}
	
	
	public List<MerchantKycDoc> insertKycDoc(String MerchantId, String docType){
		
		MerchantKycDoc merchantkycDoc = new MerchantKycDoc();	
		List<BusinessTypeDto> dbbustype = new ArrayList<>();
		List<MerchantKycDoc> btrypedel = merchantKycDocRepo.createUpload(MerchantId, docType);	
		
		

//		for (Object[] obj : btrypedel) {
//			BusinessTypeDto merBasDtoDet = new BusinessTypeDto();
//			merBasDtoDet.setBusinessTypeId((int) obj[3]);
//			merBasDtoDet.setBusinessType((String) obj[1]);
//			merBasDtoDet.setDocument((String) obj[4]);			
//			merBasDtoDet.setDocumentDescription((String) obj[5]);
//			merBasDtoDet.setDocid((int) obj[2]);
//			merBasDtoDet.setKycId((BigInteger) obj[6]);
//			merBasDtoDet.setDocname((String) obj[7]);
//			merBasDtoDet.setDocpath((String) obj[8]);
//			merBasDtoDet.setDocType((String) obj[9]);
//			
//
//			dbbustype.add(merBasDtoDet);
//		}
//		

		return btrypedel;
	}
	

	public List<MerchantDocDto> deleteuploaddoc(MerchantKycDoc merchantKycDoc){
		MerchantKycDoc merchantkycDoc = new MerchantKycDoc();
		merchantkycDoc.setId(merchantKycDoc.getId());
		merchantkycDoc.setDocpath(merchantKycDoc.getDocpath());		
		merchantkycDoc.setMerchantId(merchantKycDoc.getMerchantId());
		
		List<MerchantDocDto> merchantKycDocList = new ArrayList<>();
		
		File file = new File(merchantkycDoc.getDocpath());
			
		try {
			if (file.exists()) {
	        	file.delete();
	        }
			MerchantKycDoc merchantkycdel = merchantKycDocRepo.findById(merchantkycDoc.getId()).get(0);
			merchantKycDocRepo.deleteById(merchantkycdel.getId());
			
			
			List<Object[]> kycdocdtodel = merchantKycDocRepo.getAllList(merchantkycdel.getMerchantId());
			
			for (Object[] obj : kycdocdtodel) {
				MerchantDocDto kycDocDto = new MerchantDocDto();
				kycDocDto.setId((int) obj[0]);
				kycDocDto.setDocName((String) obj[1]);
				kycDocDto.setDocpath((String) obj[2]);
				kycDocDto.setDocType((String) obj[3]);
				kycDocDto.setMerchantId((String) obj[4]);
				kycDocDto.setModifiedBy((String) obj[5]);
				kycDocDto.setModifiedOn((String) obj[6]);
				kycDocDto.setUpdatedBy((String) obj[7]);
				kycDocDto.setUpdateOn((String) obj[8]);		
				merchantKycDocList.add(kycDocDto);
			}
			
			} catch (Exception e) {
			e.printStackTrace();
		}
		
		return merchantKycDocList;
	}
	public List<MerchantBasicSetupDto> setMerchantBasicInfo(String MerchantId,  String isAutoRefund, String hours, String minutes, String isPushUrl,
			String pushUrl, String settlementCycle, String merchantDashboardRefund, String mdDisableRefundCc, String mdDisableRefundDc, String mdDisableRefundNb, String mdDisableRefundUpi,
			String mdDisableRefundWallet, String refundApi, String refundApiDisableCc, String refundApiDisableDc, String refundApiDisableNb, String refundApiDisableUpi, String refundApiDisableWallet,
			String integrationType, String isretryAllowed, String bpsEmailNotification, String bpsSmsNotification,String bpsMailReminder, String reportingcycle, String upi_loader, String upi_intent, String upi_collect,String static_QR,String dynamic_QR,String partnerBy) {
		List<MerchantBasicSetupDto> mBasicSetDtoinfo = new ArrayList<>();
		logger.info("setMerchantBasicInfo:::::::::::::::");

		List<Object[]> dbmBasicSetDtoinfo = merchantRepo.cBasicSetupByMerchantId(MerchantId,  isAutoRefund, hours, minutes, isPushUrl, 
				pushUrl, settlementCycle, merchantDashboardRefund, mdDisableRefundCc, mdDisableRefundDc, mdDisableRefundNb, mdDisableRefundUpi, 
				mdDisableRefundWallet, refundApi, refundApiDisableCc, refundApiDisableDc, refundApiDisableNb, refundApiDisableUpi, refundApiDisableWallet, 
				integrationType, isretryAllowed, bpsEmailNotification, bpsSmsNotification,bpsMailReminder,reportingcycle, upi_loader, upi_intent, upi_collect, static_QR,dynamic_QR,partnerBy);
		logger.info("dbmBasicSetDtoinfo:::::::::::::::");

		for (Object[] obj : dbmBasicSetDtoinfo) {
			MerchantBasicSetupDto merBasDto = new MerchantBasicSetupDto();
			merBasDto.setMerchantId((String) obj[0]);
//			merBasDto.setMerReturnUrl((String) obj[1]);
			merBasDto.setIsAutoRefund((String) obj[2]);			
			merBasDto.setHours((String) obj[3]);
			merBasDto.setMinutes((String) obj[4]);
			merBasDto.setIsPushUrl((String) obj[5]);
			merBasDto.setPushUrl((String) obj[6]);
			merBasDto.setSettlementCycle((String) obj[7]);
			merBasDto.setMerchantDashboardRefund( (Character.toString((Character) obj[8])));
			merBasDto.setMdDisableRefundCc( (Character.toString((Character) obj[9])) );
			merBasDto.setMdDisableRefundDc((Character.toString((Character) obj[10])));
			merBasDto.setMdDisableRefundNb((Character.toString((Character) obj[11])));
			merBasDto.setImdDisableRefundUpi( (Character.toString((Character) obj[12])));
			merBasDto.setImdDisableRefundWallet( (Character.toString((Character) obj[13] )));
			merBasDto.setRefundApi( (Character.toString((Character) obj[14] )));
			merBasDto.setRefundApiDisableCc( (Character.toString((Character) obj[15])));
			merBasDto.setRefundApiDisableDc( (Character.toString((Character) obj[16])));
			merBasDto.setRefundApiDisableNb( (Character.toString((Character) obj[17])));
			merBasDto.setRefundApiDisableUpi( (Character.toString((Character) obj[18] )));
			merBasDto.setRefundApiDisableWallet( (Character.toString((Character) obj[19])));
			merBasDto.setIntegrationType((String) obj[20]);
			String b1 = ((String) obj[21].toString());
			if (b1.equals("true")) {
				b1 = "1";
			}else if (b1.equals("false")) {
				b1 = "0";
			}
			merBasDto.setIsretryAllowed(b1);			
			merBasDto.setIbpsEmailNotification((String) obj[22]);
			merBasDto.setIbpsSmsNotification((String) obj[23]);
			merBasDto.setStatic_QR((String) obj[31]);
			merBasDto.setDynamic_QR((String) obj[32]);

			mBasicSetDtoinfo.add(merBasDto);
		}
		
		
		return mBasicSetDtoinfo;
	}
	
	public List<MerchantBasicSetupDto> getMerchantBasicInfo(String MerchantId) {
		
		List<MerchantBasicSetupDto> mBasicSetDetails = new ArrayList<>();
		
		
		List<Object[]> dbmBasicSetDtoDetails = merchantRepo.findBasicSetupByMerchantId(MerchantId);
		
		for (Object[] obj : dbmBasicSetDtoDetails) {
			MerchantBasicSetupDto merBasDtoDet = new MerchantBasicSetupDto();
			merBasDtoDet.setMerchantId((String) obj[0]);
//			merBasDtoDet.setMerReturnUrl((String) obj[1]);
			merBasDtoDet.setIsAutoRefund((String) obj[2]);			
			merBasDtoDet.setHours((String) obj[3]);
			merBasDtoDet.setMinutes((String) obj[4]);
			merBasDtoDet.setIsPushUrl((String) obj[5]);
			merBasDtoDet.setPushUrl((String) obj[6]);
			merBasDtoDet.setSettlementCycle((String) obj[7]);
			merBasDtoDet.setMerchantDashboardRefund((String) obj[8]);
			merBasDtoDet.setMdDisableRefundCc((String) obj[9]);
			merBasDtoDet.setMdDisableRefundDc((String) obj[10]);
			merBasDtoDet.setMdDisableRefundNb((String) obj[11]);
			merBasDtoDet.setImdDisableRefundUpi((String) obj[12]);
			merBasDtoDet.setImdDisableRefundWallet((String) obj[13]);
			merBasDtoDet.setRefundApi((String) obj[14]);
			merBasDtoDet.setRefundApiDisableCc((String) obj[15]);
			merBasDtoDet.setRefundApiDisableDc((String) obj[16]);
			merBasDtoDet.setRefundApiDisableNb((String) obj[17]);
			merBasDtoDet.setRefundApiDisableUpi((String) obj[18]);
			merBasDtoDet.setRefundApiDisableWallet((String) obj[19]);
			merBasDtoDet.setIntegrationType((String) obj[20]);
			String b1 = ((String) obj[21].toString());
			if (b1.equals("true")) {
				b1 = "1";
			}else if (b1.equals("false")) {
				b1 = "0";
			}
			merBasDtoDet.setIsretryAllowed(b1);			
			merBasDtoDet.setIbpsEmailNotification((String) obj[22]);
			merBasDtoDet.setIbpsSmsNotification((String) obj[23]);
			merBasDtoDet.setIbpsMailReminder((String) obj[24]);
			merBasDtoDet.setReporting_cycle((String) obj[25]);
			merBasDtoDet.setUpi_loader((String) obj[26]);
			merBasDtoDet.setUpi_intent((String) obj[27]);
			merBasDtoDet.setUpi_collect((String) obj[28]);
			merBasDtoDet.setStatic_QR((String) obj[29]);
			merBasDtoDet.setDynamic_QR((String) obj[30]);
			mBasicSetDetails.add(merBasDtoDet);
		}
		
		
		return mBasicSetDetails;
	}
	
	public List<BusinessTypeDto> getKycDocumentList(int businesstypeId, String merchantId){
		
		
		List<BusinessTypeDto> dbbustype = new ArrayList<>();
		
		List<Object[]> btrypedel =  merchantKycDocRepo.findByBussineTypeId(businesstypeId, merchantId);
		 boolean  gstinStatus = merchantKycDocRepo.findGstin(merchantId);
		 ArrayList doclist = merchantKycDocRepo.gstDocumentId();
		for (Object[] obj : btrypedel) {
			BusinessTypeDto merBasDtoDet = new BusinessTypeDto();
			int docid = ((int) obj[2]);
			if (gstinStatus == false) {				
					merBasDtoDet.setBusinessTypeId((int) obj[3]);
					merBasDtoDet.setBusinessType((String) obj[1]);
					merBasDtoDet.setDocument((String) obj[4]);			
					String docdes = ((String) obj[5]);
					String[] splitString = docdes.split("\n");
					merBasDtoDet.setDocumentDescription(splitString);
					merBasDtoDet.setDocid((int) obj[2]);
					merBasDtoDet.setKycId( BigInteger.valueOf(  (Long) obj[6]  ) );
					merBasDtoDet.setDocname((String) obj[7]);
					merBasDtoDet.setDocpath((String) obj[8]);
					merBasDtoDet.setDocType((String) obj[9]);
					merBasDtoDet.setAllow_wavier((String) obj[10]);
					merBasDtoDet.setWavier_date((String) obj[11]);
					merBasDtoDet.setWavier_remark((String) obj[12]);
					merBasDtoDet.setType((String) obj[13]);
					merBasDtoDet.setSubtext((String) obj[14]);
					dbbustype.add(merBasDtoDet);

			}else {
				if (doclist.contains(docid) == false) {
					merBasDtoDet.setBusinessTypeId((int) obj[3]);
					merBasDtoDet.setBusinessType((String) obj[1]);
					merBasDtoDet.setDocument((String) obj[4]);			
					String docdes = ((String) obj[5]);
					String[] splitString = docdes.split("\n");
					merBasDtoDet.setDocumentDescription(splitString);
					merBasDtoDet.setDocid((int) obj[2]);
					merBasDtoDet.setKycId((BigInteger) obj[6]);
					merBasDtoDet.setDocname((String) obj[7]);
					merBasDtoDet.setDocpath((String) obj[8]);
					merBasDtoDet.setDocType((String) obj[9]);
					merBasDtoDet.setAllow_wavier((String) obj[10]);
					merBasDtoDet.setWavier_date((String) obj[11]);
					merBasDtoDet.setWavier_remark((String) obj[12]);
					merBasDtoDet.setType((String) obj[13]);
					merBasDtoDet.setSubtext((String) obj[14]);
					dbbustype.add(merBasDtoDet);
				}				
			}
		}
		
		return dbbustype;
	}
	public void save(MultipartFile file, String createdby) {
	    try {
	    	
	    	List<BulkCsvMerchantDto> BulkDto = BulkCsvHelper.csvToMstmerchant(file.getInputStream());
	    	bulkCsvMerchantRepo.deleteAll();
	    	
	    	for (BulkCsvMerchantDto obj : BulkDto) {
	    		BulkCsvMerchant bulks = new BulkCsvMerchant();
	    		bulks.setContact_person((String) obj.getContact_person());
	    		bulks.setContact_number((String) obj.getContact_number());
	    		bulks.setEmail_id((String) obj.getEmail_id());
	    		bulks.setCompanyPAN((String) obj.getCompanyPAN());
	    		bulks.setMerchant_name((String) obj.getMerchant_name());
	    		bulks.setBusiness_name((String) obj.getBusiness_name());
	    		bulks.setBusinessType((String) obj.getBusinessType());
	    		bulks.setMerchant_category_code((String) obj.getMerchant_category_code());
	    		bulks.setMerchant_sub_category((String) obj.getMerchant_sub_category());
	    		bulks.setBusinessModel((String) obj.getBusinessModel());
	    		bulks.setTurnoverinlastFinancialYear((String) obj.getTurnoverinlastFinancialYear());
	    		bulks.setExpectedMonthlyIncome((String) obj.getExpectedMonthlyIncome());
	    		bulks.setAverageAmountPerTransaction((String) obj.getAverageAmountPerTransaction());
	    		bulks.setAuthorisedSignatoryPAN((String) obj.getAuthorisedSignatoryPAN());
	    		bulks.setNameAsPerPAN((String) obj.getNameAsPerPAN());
	    		bulks.setGSTINNo((String) obj.getGSTINNo());
	    		bulks.setMer_return_url((String) obj.getMer_return_url());
	    		bulks.setMer_website_url(obj.getMer_website_url());
	    		bulks.setReseller_id((String) obj.getReseller_id());
	    		bulks.setIsTestAccess((String) obj.getIsTestAccess());				
	    		bulks.setIs_auto_refund((String) obj.getIs_auto_refund());
	    		bulks.setHours((String) obj.getHours());
	    		bulks.setMinutes((String) obj.getMinutes());
	    		bulks.setIs_push_url((String) obj.getIs_push_url());
	    		bulks.setPush_url((String) obj.getPush_url());
	    		bulks.setSettlement_cycle((String) obj.getSettlement_cycle());
	    		bulks.setIntegration_type((String) obj.getIntegration_type());
	    		bulks.setIsretryAllowed((String) obj.getIsretryAllowed());
	    		bulks.setIbps_email_notification((String) obj.getIbps_email_notification());
	    		bulks.setIbps_sms_notification((String) obj.getIbps_sms_notification());
	    		bulks.setIbps_mail_remainder((String) obj.getIbps_mail_remainder());
	    		bulks.setReporting_cycle((String) obj.getReporting_cycle());
	    		bulks.setMerchant_dashboard_refund((String) obj.getMerchant_dashboard_refund());
	    		bulks.setMd_disable_refund_cc((String) obj.getMd_disable_refund_cc());
	    		bulks.setMd_disable_refund_dc((String) obj.getMd_disable_refund_dc());
	    		bulks.setMd_disable_refund_nb((String) obj.getMd_disable_refund_nb());
	    		bulks.setMd_disable_refund_upi((String) obj.getMd_disable_refund_upi());
	    		bulks.setMd_disable_refund_wallet((String) obj.getMd_disable_refund_wallet());
	    		bulks.setRefund_api((String) obj.getRefund_api());
	    		bulks.setRefund_api_disable_cc((String) obj.getRefund_api_disable_cc());
	    		bulks.setRefund_api_disable_dc((String) obj.getRefund_api_disable_dc());
	    		bulks.setRefund_api_disable_nb((String) obj.getRefund_api_disable_nb());
	    		bulks.setRefund_api_disable_upi((String) obj.getRefund_api_disable_upi());
	    		bulks.setRefund_api_disable_wallet((String) obj.getRefund_api_disable_wallet());
	    		
	    		bulks.setSr_no(obj.getSr_no());
	    		bulkCsvMerchantRepo.save(bulks);
			}
	    	
	    	
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }
	
	public List<BulkMerchantList> getVerifyCsvData(String createdby){		
		
		List<BulkCsvMerchant> btrypedel =  bulkCsvMerchantRepo.findAll();		
		
//		reset		
		String allverifedStatus ="";
		String reset =null;		
		String msgSuccess = "";

		merchantRepo.updateAllVerification(reset);
		
		for (BulkCsvMerchant obj : btrypedel) {
			String errorContactname = "";
			String errorContactno = "";
			String errorEmailid ="";
			String errorCompanypan = "";
			String errorGststatus = "";
			String errorMerchantname = "";
			String errorBusinessName = "";
			String errorMerreturnurl = "";
			String errorWebsiteUrl = "";
			String errorPushurl = "";
			String errorDuplicate = "";
			
			BulkCsvDto bulksave = new BulkCsvDto();
			Long recId = (obj.getId());

			String contactperson=(obj.getContact_person());			
			if(contactperson.equals("") || contactperson == null) {
				errorContactname = "Contact Person cannot be null, ";

			}
			
			String contactnumber=(obj.getContact_number());
			boolean result = contactnumber.matches("[0-9]+");
			int vallengh = contactnumber.length();
			if(result == false) {				

				 errorContactno = "Contact Number " + contactnumber + " not in right format, ";

			}else if(vallengh < 10){

				 errorContactno = "Contact Number " + contactnumber +  " Length Less than 10, ";	

			}
			
			String emailid= (obj.getEmail_id());
			String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(emailid);
			
			if (matcher.matches() == false) {

				 errorEmailid = "Email Id " + emailid +" is Invalid, ";

			}
			
			String companypan = (obj.getCompanyPAN());
			String panCardPattern = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
			boolean panstatus= Pattern.matches(panCardPattern, companypan);
			if (panstatus == false) {

				 errorCompanypan = "Company Pan no " + companypan +" - is not valid, ";	

			}
			
			String gstin = (obj.getGSTINNo());
			String gstinno = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}";
			boolean gststatus = Pattern.matches(gstinno, gstin);
			if (gststatus == false) {

				 errorGststatus = "GSTIN NO. " + gstin +" - is not valid, ";	

			}
			
			String merchantName=(obj.getMerchant_name());			
			if(merchantName.equals("") || merchantName == null) {

				 errorMerchantname = "Merchant Name cannot be null, ";

			}
			
			String businessName=(obj.getBusiness_name());			
			if(businessName.equals("") || businessName == null) {

				 errorBusinessName ="Business Name cannot be null, ";

			}
			
			String merretunurl = (obj.getMer_return_url());
			String pushurl = (obj.getPush_url());
			String websiteurl = (obj.getMer_website_url());
			String patternUrl = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
						
			if(merretunurl.equals("") || merretunurl == null) {

				 errorMerreturnurl = "Merchant Return Url is Blank, ";
			}else{
				boolean merretunurlstat = Pattern.matches(patternUrl, merretunurl);
				if (merretunurlstat == false) {

					 errorMerreturnurl = "Merchant Return Url not valid, ";	

				}
			} 
			 if (websiteurl.equals("") || websiteurl == null) {

				 errorWebsiteUrl = "Website Url is Blank, ";	
				
			}else {
				boolean websiteurlstat = Pattern.matches(patternUrl, websiteurl);
				if (websiteurlstat == false) {

					 errorWebsiteUrl = "Website Url is not valid, ";	

				}
			}
			 if (pushurl.equals("") || pushurl == null) {

					 errorPushurl = "Push URL is Blank, ";	
				
				}else {
					boolean pushurlstat = Pattern.matches(patternUrl, pushurl);
					if (pushurlstat == false) {

						 errorPushurl = "Push URL is not valid, ";	

					} 
				} 

			String verificationStatus = merchantRepo.findVerification(recId);
			if (verificationStatus == null) {
				String Contactno = (obj.getContact_number());
				String Email = (obj.getEmail_id());
				String Companypan = (obj.getCompanyPAN());
			
				boolean findContactno = merchantRepo.findByContactNumber(Contactno);
				boolean findEmailid = merchantRepo.findByEmailId(Email);
				boolean findCompanyPan = merchantRepo.findByCompanyPAN(Companypan);
				if (findContactno == true || findEmailid == true || findCompanyPan == true) {
					if(findContactno == true) {
						
						errorDuplicate=  " Duplicate Records Found for Contact No. " + Contactno;				
						
					}
					if(findEmailid == true) {
						
						errorDuplicate=  " Duplicate Records Found for Email-Id " + Email;				
						
					}
					if(findCompanyPan == true) {
						
						errorDuplicate=  " Duplicate Records Found for Company PAN " + Companypan;				
						
					}
					if(findContactno == true || findEmailid == true) {
						
						errorDuplicate=  " Duplicate Records Found for Contact No. " + Contactno + " & Email-Id " + Email;				
						
					}
					if(findCompanyPan == true || findEmailid == true) {
						
						errorDuplicate=  " Duplicate Records Found for Company PAN " + Companypan + " & Email-Id " + Email;				
						
					}
					if(findContactno == true || findCompanyPan == true) {
						
						errorDuplicate=  " Duplicate Records Found for Contact No. " + Contactno + " & Company PAN " + Companypan;				
						
					}
					if(findContactno == true || findEmailid == true || findCompanyPan == true) {
						
						errorDuplicate=  " Duplicate Records Found for Contact No. " + Contactno + " & Email-Id " + Email + " & Company PAN " + Companypan;				
						
					}
					msgSuccess ="";
				}else if (errorContactname != "" || errorContactno != "" || errorEmailid != "" || errorCompanypan != "" ||errorGststatus != "" || errorMerchantname != "" || errorBusinessName != "" || errorMerreturnurl != "" || errorWebsiteUrl != "" || errorPushurl != "") {
					msgSuccess ="";
				}else {
					String maxsize= "0";
					String merchantId = merchantRepo.findMerchantId();
		    		User user = new User();
		    		MerchantCreationDto creationDto = new MerchantCreationDto();
		    		GeneralUtil generalUtil = new GeneralUtil();
		    		String firstName = ((String) obj.getMerchant_name()).contains(" ") ? ((String) obj.getMerchant_name()).split(" ")[0]
		    				: ((String) obj.getMerchant_name());
		    		String password = generalUtil.crypt(firstName + "@2022");
		    		
		    		user.setFullName((String) obj.getMerchant_name());
		    		user.setBlocked(false);
		    		user.setCreatedBy(createdby);
		    		user.setUserId(merchantId);
		    		user.setMerchantId(merchantId);
		    		user.setPassword(password);
		    		long millis = System.currentTimeMillis();
		    		user.setCreatedOn(new java.sql.Date(millis));
		    		user.setEmailId((String) obj.getEmail_id());
		    		user.setGroupId(2);
		    		user.setRoleId(2);
		    		user.setIsActive(true);
		    		user.setIsDeleted('N');
		    		user.setContactNumber((String) obj.getContact_number());
								
					bulksave.setContact_person((String) obj.getContact_person());
					bulksave.setContact_number((String) obj.getContact_number());
					bulksave.setEmail_id((String) obj.getEmail_id());
					bulksave.setCompanyPAN((String) obj.getCompanyPAN());
					bulksave.setMerchant_name((String) obj.getMerchant_name());
					bulksave.setBusiness_name((String) obj.getBusiness_name());
					bulksave.setBusinessType((String) obj.getBusinessType());
					bulksave.setMerchant_category_code((String) obj.getMerchant_category_code());
					bulksave.setMerchant_sub_category((String) obj.getMerchant_sub_category());
					bulksave.setBusinessModel((String) obj.getBusinessModel());
					bulksave.setTurnoverinlastFinancialYear((String) obj.getTurnoverinlastFinancialYear());
					bulksave.setExpectedMonthlyIncome((String) obj.getExpectedMonthlyIncome());
					bulksave.setAverageAmountPerTransaction((String) obj.getAverageAmountPerTransaction());
					bulksave.setAuthorisedSignatoryPAN((String) obj.getAuthorisedSignatoryPAN());
					bulksave.setNameAsPerPAN((String) obj.getNameAsPerPAN());
					bulksave.setGSTINNo((String) obj.getGSTINNo());
					bulksave.setMer_return_url((String) obj.getMer_return_url());
					bulksave.setReseller_id((String) obj.getReseller_id());
					bulksave.setIsTestAccess((String) obj.getIsTestAccess());				
					bulksave.setIs_auto_refund((String) obj.getIs_auto_refund());
					bulksave.setHours((String) obj.getHours());
					bulksave.setMinutes((String) obj.getMinutes());
					bulksave.setIs_push_url((String) obj.getIs_push_url());
					bulksave.setPush_url((String) obj.getPush_url());
					bulksave.setSettlement_cycle((String) obj.getSettlement_cycle());
					bulksave.setIntegration_type((String) obj.getIntegration_type());
					bulksave.setIsretryAllowed((String) obj.getIsretryAllowed());
					bulksave.setIbps_email_notification((String) obj.getIbps_email_notification());
					bulksave.setIbps_sms_notification((String) obj.getIbps_sms_notification());
					bulksave.setIbps_mail_remainder((String) obj.getIbps_mail_remainder());
					bulksave.setReporting_cycle((String) obj.getReporting_cycle());
					bulksave.setMerchant_dashboard_refund((String) obj.getMerchant_dashboard_refund());
					bulksave.setMd_disable_refund_cc((String) obj.getMd_disable_refund_cc());
					bulksave.setMd_disable_refund_dc((String) obj.getMd_disable_refund_dc());
					bulksave.setMd_disable_refund_nb((String) obj.getMd_disable_refund_nb());
					bulksave.setMd_disable_refund_upi((String) obj.getMd_disable_refund_upi());
					bulksave.setMd_disable_refund_wallet((String) obj.getMd_disable_refund_wallet());
					bulksave.setRefund_api((String) obj.getRefund_api());
					bulksave.setRefund_api_disable_cc((String) obj.getRefund_api_disable_cc());
					bulksave.setRefund_api_disable_dc((String) obj.getRefund_api_disable_dc());
					bulksave.setRefund_api_disable_nb((String) obj.getRefund_api_disable_nb());
					bulksave.setRefund_api_disable_upi((String) obj.getRefund_api_disable_upi());
					bulksave.setRefund_api_disable_wallet((String) obj.getRefund_api_disable_wallet());
					
					user = userRepository.save(user);
					bulkCsvMerchantRepo.createMerchantByCsv(merchantId, bulksave.getMerchant_name(), bulksave.getBusiness_name(), bulksave.getContact_person()
							, bulksave.getEmail_id(), bulksave.getMer_return_url(), bulksave.getIsretryAllowed(), bulksave.getIs_auto_refund(),bulksave.getHours(), bulksave.getMinutes()
							, bulksave.getIs_push_url(), bulksave.getIntegration_type(), bulksave.getMer_website_url(), bulksave.getMerchant_category_code(), bulksave.getMerchant_sub_category()
							, bulksave.getReseller_id(), bulksave.getContact_number(), bulksave.getPush_url(), bulksave.getIbps_mail_remainder(), bulksave.getMerchant_dashboard_refund(), bulksave.getMd_disable_refund_cc()
							, bulksave.getMd_disable_refund_dc(), bulksave.getMd_disable_refund_nb(), bulksave.getMd_disable_refund_upi(), bulksave.getMd_disable_refund_wallet(),bulksave.getRefund_api(), bulksave.getRefund_api_disable_cc()
							, bulksave.getRefund_api_disable_dc(), bulksave.getRefund_api_disable_nb(), bulksave.getRefund_api_disable_upi(), bulksave.getRefund_api_disable_wallet(), bulksave.getIbps_email_notification(), bulksave.getIbps_sms_notification()
							, bulksave.getSettlement_cycle(),bulksave.getBusinessType(),bulksave.getBusinessModel(), bulksave.getTurnoverinlastFinancialYear(), bulksave.getExpectedMonthlyIncome()
							, bulksave.getAverageAmountPerTransaction(),bulksave.getAuthorisedSignatoryPAN(), bulksave.getNameAsPerPAN(), bulksave.getGSTINNo(), bulksave.getIsTestAccess(), bulksave.getCompanyPAN(),bulksave.getReporting_cycle(), maxsize);
					
					String Srno = (obj.getSr_no());
					msgSuccess = "Records Save with Merchant Id  " + merchantId  ;
					
				}
						
			}
			String uploadStatus = "";
			allverifedStatus = errorContactname + errorContactno + errorEmailid + errorCompanypan + errorGststatus + errorMerchantname + errorBusinessName + errorMerreturnurl + errorWebsiteUrl + errorPushurl +	errorDuplicate + msgSuccess ;
			if (msgSuccess != "") {
				uploadStatus = "Success";
				merchantRepo.updateVerification(allverifedStatus, uploadStatus, recId );
			}else {
				uploadStatus = "Failed";
				merchantRepo.updateVerification(allverifedStatus, uploadStatus, recId );
			}
		}
		List<BulkMerchantList> bulktablelist = new ArrayList<>();
		List<Object[]> bulktablelist1 =  merchantRepo.listAllRecords();
		for (Object[] obj : bulktablelist1) {
			BulkMerchantList merBasDtoDet = new BulkMerchantList();
			merBasDtoDet.setMerchantName((String) obj[1]);			
			merBasDtoDet.setResellerId((String) obj[43]);
			merBasDtoDet.setSrNo((String) obj[45]);
			merBasDtoDet.setUploadStatus((String) obj[46]);
			merBasDtoDet.setRemarks((String) obj[47]);
		merBasDtoDet.setMerchantId((String) obj[48]);
			bulktablelist.add(merBasDtoDet);
			
		}
		
		
		return bulktablelist;
	}

public List<BulkMdrList> createMerchantMdrBulkCsv(){
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		List<BulkCsvMdr> mdrbulkData =  mdrBulkCsvRepo.findAll();
		String mdrverifedStatus ="";
		String mdrreset =null;
		String mdrSuccess = "";
		merchantRepo.updateMdrVerification(mdrreset);
//		Gson gson = new Gson();
//		String jsonList = gson.toJson(mdrbulkData);
		
//		List<Object[]> resultListmdr = mdrBulkCsvRepo.createUpload(jsonList);

		for (BulkCsvMdr obj : mdrbulkData) {
			String erroraggMdr = "";
			String errorresminmdr = "";
			String errorresmaxmdr ="";
			String errorresminamt = "";
			String errorresmaxamt = "";
			String errorresellerMdr = "";
			String errorminResellerMdr = "";
			String errormaxResellerMdr = "";
			String errorbaseRate = "";
			String errorminbaseRate = "";
			String errormaxbaseRate = "";
			String errorDuplicate = "";
			String errorSPId = "";
			String errorBankId = "";
			String errorMdrType = "";
			String errorBankMdrType = "";
			String errorResellerMdrType = "";
			String errorSurcharge = "";
			String errorPayout = "";
			String errorPayout_escrow = "";
			Long recId = (obj.getId());
			String aggMdr=(obj.getAggrMdr());			
			String minmdr=(obj.getMinMdr());
			String maxmdr=(obj.getMaxMdr());
			String minamt=(obj.getMinAmt());
			String maxamt=(obj.getMaxAmt());
			String resellerMdr=(obj.getResellerMdr());
			String baseRate=(obj.getBaseRate());
			String minBaseRate=(obj.getMinBaseRate());
			String maxBaseRate=(obj.getMaxBaseRate());
			String minResellerMdr=(obj.getMinResellerMdr());
			String maxResellerMdr=(obj.getMaxResellerMdr());
			
			BulCsvMerchantMdrDto mdrbulksave = new BulCsvMerchantMdrDto();
			String pettern ="^[0-9]{1,8}(,[0-9]{8})*(([\\\\\\\\.,]{1}[0-9]*)|())$";
			boolean resaggMdr = aggMdr.matches(pettern);
			boolean resminmdr = minmdr.matches(pettern);
			boolean resmaxmdr = maxmdr.matches(pettern);
			boolean resminamt = minamt.matches(pettern);
			boolean resmaxamt = maxamt.matches(pettern);
			boolean resresellerMdr = resellerMdr.matches(pettern);
			boolean resbaseRate = baseRate.matches(pettern);
			boolean resminBaseRate = minBaseRate.matches(pettern);
			boolean resmaxBaseRate = maxBaseRate.matches(pettern);
			boolean resminResellerMdr = minResellerMdr.matches(pettern);
			boolean resmaxResellerMdr = maxResellerMdr.matches(pettern);
			
			if(resbaseRate == false) {
				errorbaseRate= " Use Numeric Value in Base Rate ,";
			}
			if(resminBaseRate == false) {				
				errorminbaseRate = " Use Numeric Value in Min Base Amount ,";
			}else if (resminBaseRate == true) {
				Double iminbaseRate=Double. parseDouble(minBaseRate);
				Double imaxbaseRate=Double. parseDouble(maxBaseRate);
				if (iminbaseRate > imaxbaseRate) {
					errorminbaseRate = " Min Base Rate value cannot be greater to Max Base Rate Value ,";
				}
			}
			if(resmaxBaseRate == false) {				
				errormaxResellerMdr = " Use Numeric Value in Max Base Amount ,";
			}else if (resmaxBaseRate == true) {
				Double iminbaseRate=Double. parseDouble(minBaseRate);
				Double imaxbaseRate=Double. parseDouble(maxBaseRate);
				if (iminbaseRate > imaxbaseRate) {
					errormaxResellerMdr = " Max MDR value cannot be lesser to Min MDR Value ,";
				}
			}
			
			
			if(resresellerMdr == false) {
				errorresellerMdr= " Use Numeric Value in Reseller MDR ,";
			}
			if(resminResellerMdr == false) {				
				errorminResellerMdr = " Use Numeric Value in Min MDR ,";
			}else if (resminResellerMdr == true) {
				Double iresellerminmdr=Double. parseDouble(minResellerMdr);
				Double iresellermaxmdr=Double. parseDouble(maxResellerMdr);
				if (iresellerminmdr > iresellermaxmdr) {
					errorminResellerMdr = " Min Reseller MDR value cannot be greater to Max Reseller MDR Value ,";
				}
			}
			if(resmaxResellerMdr == false) {				
				errormaxResellerMdr = " Use Numeric Value in Max MDR ,";
			}else if (resmaxmdr == true) {
				Double iresellerminmdr=Double. parseDouble(minResellerMdr);
				Double iresellermaxmdr=Double. parseDouble(minResellerMdr);
				if (iresellerminmdr > iresellermaxmdr) {
					errormaxResellerMdr = " Max MDR value cannot be lesser to Min MDR Value ,";
				}
			}
			
			if(resaggMdr == false) {
				erroraggMdr= " Use Numeric Value in Aggregator MDR ,";
			}
			if(resminmdr == false) {				
				errorresminmdr = " Use Numeric Value in Min MDR ,";
			}else if (resminmdr == true) {
				Double iminmdr=Double. parseDouble(minmdr);
				Double imaxmdr=Double. parseDouble(maxmdr);
				if (iminmdr > imaxmdr) {
					errorresminmdr = " Min MDR value cannot be greater to Max MDR Value ,";
				}
			}
			if(resmaxmdr == false) {				
				errorresmaxmdr = " Use Numeric Value in Max MDR ,";
			}else if (resmaxmdr == true) {
				Double iminmdr=Double. parseDouble(minmdr);
				Double imaxmdr=Double. parseDouble(maxmdr);
				if (iminmdr > imaxmdr) {
					errorresmaxmdr = " Max MDR value cannot be lesser to Min MDR Value ,";
				}
			}
			if(resminamt == false) {
				errorresminamt=" Use Numeric Value in Min Amount ,";
			}else if (resminamt == true) {
				Double iminamt=Double. parseDouble(minamt);
				Double imaxamt=Double. parseDouble(maxamt);
				if (iminamt > imaxamt) {
					errorresminamt = " Min Amount Value cannot be greater to Max Amount Value ,";
				}
			}
			if(resmaxamt == false) {				
				errorresmaxamt = " Use Numeric Value in Max Amount ,";
			}else if (maxamt.length() < 4 || maxamt.length() > 8 ) {
				errorresmaxamt = " Use Numeric Value minimum 4 digit or maximum 8 digit in Max Amount ,";
			}else if (resmaxamt == true) {
				Double iminamt=Double. parseDouble(minamt);
				Double imaxamt=Double. parseDouble(maxamt);
				if (iminamt > imaxamt) {
					errorresmaxamt = " Max Amount value cannot be lesser to Min Amount Value ,";
				}
			}
			String spID= mdrBulkCsvRepo.findSpId(obj.getSpId());
			String SPID= "";
			if(!spID.equals("")) {
				SPID =spID;
			}else if(spID == null) {
				errorSPId = " Delete Blank Rows , ";
			}else {
				errorSPId = " Given SP Name  not matched, ";
			}
			
			String BankID= mdrBulkCsvRepo.findBankId(SPID,obj.getBankId());
			String BANKID= "";
			if(!BankID.equals("0") ) {
				BANKID =BankID;
			}else {
				errorBankId = " Given Bank Name not matched with SP Name,  ";
				
			}
			String MdrType= "";
			if (obj.getMdrType().equalsIgnoreCase("Percentage")) {	    		
				MdrType= "1";
    		}else if(obj.getMdrType().equalsIgnoreCase("Flat")){
    			MdrType= "2";
    		}else {
    			errorMdrType = " MDR Type Should be Percentage Or Flat, ";
    		}
			String BankMdrType="";
			if(obj.getBankMdrType().equalsIgnoreCase("Percentage")) {
				BankMdrType="1";
    		}else if(obj.getBankMdrType().equalsIgnoreCase("Flat")) {
    			BankMdrType="2";
    		}else {
    			errorBankMdrType = " Bank MDR Type Should be Percentage Or Flat, ";
    		}
			
			String ResellerMdrType="";
			if(obj.getResellerMdrType().equalsIgnoreCase("Percentage")) {
				ResellerMdrType="1";	
    		}else if (obj.getResellerMdrType().equalsIgnoreCase("Flat")) {
    			ResellerMdrType="2";
    		}else {
    			errorResellerMdrType = " Reseller MDR Type Should be Percentage Or Flat, ";
    		}
			
			String Surcharge ="";
			if(obj.getSurcharge().equalsIgnoreCase("Yes")) {
				Surcharge="1";
    		}else if(obj.getSurcharge().equalsIgnoreCase("No")) {
    			Surcharge="0";
    		}else {
    			errorSurcharge = " Surcharge Value should be in Yes or No, ";
    		}
    		String payout = obj.getPayout();
    		String PAYout= "";
    		if(payout.equalsIgnoreCase("PAY")) {
    			PAYout="0";
    		}else if(payout.equalsIgnoreCase("Bank")) {
    			PAYout="1";
    		}else if(payout.equalsIgnoreCase("PAY+Bank")) {
    			PAYout="2";
    		}else {
    			errorPayout = " Payout Value should be PAY or Bank or PAY+Bank, ";
    		}
    		String PayouEscrow = obj.getPayout_escrow();
    		String PayouEsc = "";
    		if(PayouEscrow.equalsIgnoreCase("HDFC")) {
    			PayouEsc="HDFC";
    		}else if(PayouEscrow.equalsIgnoreCase("YesBank")) {
    			PayouEsc="YesBank";
    		
    		}else {
    			errorPayout_escrow = " Payout Escrow Value should be HDFC or YesBank,  ";
    		}
    		
			String merchantId =(obj.getMerchantId());
			int spid =Integer.parseInt(SPID);
			String instrumentId =(obj.getInstrumentId());
			String bankId =(BANKID);
			boolean findmdrdup = mdrBulkCsvRepo.findByMercBankInstSpid(merchantId, spid, instrumentId, bankId);
			if(findmdrdup == true) {
				String Spname = merchantRepo.findspName(spid);
				String bankName = merchantRepo.findBankName(bankId);
				errorDuplicate=  " Duplicate Records Found for Merchant Id " + merchantId + " , SP Id " + Spname + " , Instrument Id " + instrumentId + " , Bank Id " + bankName;
				mdrSuccess="";
			}else if (erroraggMdr != "" || errorresminmdr != "" || errorresmaxmdr != "" || errorresminamt !="" || errorresmaxamt !="" || errorresellerMdr !="" || errorminResellerMdr !="" || errormaxResellerMdr !="" || errorSPId !="" || errorBankId !="" || errorMdrType !="" || errorBankMdrType !="" || errorResellerMdrType !="" || errorSurcharge !="" || errorPayout !="" || errorPayout_escrow !="") {
				mdrSuccess ="";
			}else {
				mdrbulksave.setMerchant_id(obj.getMerchantId());
				mdrbulksave.setSp_id(Integer.parseInt(SPID));
				mdrbulksave.setInstrument_id(obj.getInstrumentId());
				mdrbulksave.setBank_id(BANKID);
				mdrbulksave.setMin_amt(Double. parseDouble(obj.getMinAmt()));
				mdrbulksave.setMax_amt(Double. parseDouble(obj.getMaxAmt()));
				mdrbulksave.setMdr_type(MdrType);
				mdrbulksave.setAggr_mdr(Double. parseDouble(obj.getAggrMdr()));
				mdrbulksave.setReseller_mdr(Double. parseDouble(obj.getResellerMdr()));
				mdrbulksave.setBase_rate(Double. parseDouble(obj.getBaseRate()));
				mdrbulksave.setMin_base_rate(Double. parseDouble(obj.getMinBaseRate()));
				mdrbulksave.setMax_base_rate(Double. parseDouble(obj.getMaxBaseRate()));
				mdrbulksave.setMin_mdr(Double. parseDouble(obj.getMinMdr()));
				mdrbulksave.setMax_mdr(Double. parseDouble(obj.getMaxMdr()));
				mdrbulksave.setMid(obj.getMid());
				mdrbulksave.setTid(obj.getTid());
				mdrbulksave.setChannel_id(obj.getChannelId());
				String startTime =(obj.getStartDate());
				String endTime =(obj.getEndDate());
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-mm-yy");
				String newFormatttedDate = null ;
				String newFormatttedDate2 = null ;
				try {
					Date parsedDate = simpleDateFormat2.parse(startTime);
					Date parsedDate2 = simpleDateFormat2.parse(endTime);
					simpleDateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
					newFormatttedDate = simpleDateFormat2.format(parsedDate);
					newFormatttedDate2 = simpleDateFormat2.format(parsedDate2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mdrbulksave.setStart_date(newFormatttedDate);
				mdrbulksave.setEnd_date(newFormatttedDate2);
				mdrbulksave.setPrefrences(obj.getPrefrences());
				mdrbulksave.setSurcharge(Integer.parseInt(Surcharge));
				mdrbulksave.setPayout(Integer.parseInt(PAYout));
				mdrbulksave.setCard_variant_name(obj.getCardVariantName());
				mdrbulksave.setNetwork(obj.getNetwork());
				mdrbulksave.setInstrument_brand(Integer.parseInt(obj.getInstrumentBrand()));
				mdrbulksave.setBank_mdr_type(BankMdrType);
				mdrbulksave.setMin_reseller_mdr(obj.getMinResellerMdr());
				mdrbulksave.setMax_reseller_mdr(obj.getMaxResellerMdr());
				mdrbulksave.setReseller_mdr_type(ResellerMdrType);
				mdrbulksave.setPayout_escrow(obj.getPayout_escrow());
				
				mdrBulkCsvRepo.createMerchantMdrByCsv(mdrbulksave.getMerchant_id(),mdrbulksave.getSp_id(), mdrbulksave.getBank_id(), mdrbulksave.getInstrument_id(),
						mdrbulksave.getMin_amt(), mdrbulksave.getMax_amt(), mdrbulksave.getMdr_type(), mdrbulksave.getAggr_mdr(),mdrbulksave.getReseller_mdr(), mdrbulksave.getBase_rate()
						, mdrbulksave.getMin_base_rate(), mdrbulksave.getMax_base_rate(),mdrbulksave.getMin_mdr(), mdrbulksave.getMax_mdr(), mdrbulksave.getMid(), mdrbulksave.getTid()
						, mdrbulksave.getChannel_id(), mdrbulksave.getStart_date(), mdrbulksave.getEnd_date(), mdrbulksave.getPrefrences(), mdrbulksave.getSurcharge(), mdrbulksave.getPayout()
						, mdrbulksave.getCard_variant_name(), mdrbulksave.getNetwork(), mdrbulksave.getInstrument_brand(), mdrbulksave.getBank_mdr_type(), mdrbulksave.getMin_reseller_mdr()
						, mdrbulksave.getMax_reseller_mdr(), mdrbulksave.getReseller_mdr_type(),mdrbulksave.getPayout_escrow() );
				
				mdrSuccess = "Records Save with Merchant Id  " + merchantId  ;
				
			}		
			String uploadStatus = "";
			mdrverifedStatus = erroraggMdr + errorresminmdr + errorresmaxmdr + errorresminamt + errorresmaxamt + errorresellerMdr + errorminResellerMdr + errormaxResellerMdr + errorDuplicate + errorSPId + errorBankId + errorMdrType + errorBankMdrType + errorResellerMdrType + errorSurcharge + errorPayout + errorPayout_escrow + mdrSuccess ;
			if (mdrSuccess != "") {
				uploadStatus = "Success";
				mdrBulkCsvRepo.updateMdrVerification(mdrverifedStatus, uploadStatus, recId );
				
			}else {
				uploadStatus = "Failed";
				mdrBulkCsvRepo.updateMdrVerification(mdrverifedStatus, uploadStatus, recId );
			}
			
		}
		List<BulkMdrList> bulktablelist = new ArrayList<>();
		List<Object[]> mdrecords = mdrBulkCsvRepo.mdrlistAllRecords();
		for(Object[] obj: mdrecords) {
		BulkMdrList mdrBulkString = new BulkMdrList();
		mdrBulkString.setMerchantId((String)obj[17]);
		mdrBulkString.setSrNo((String) obj[2]);
		mdrBulkString.setRemarks((String)obj[1]);
		mdrBulkString.setUploadStatus((String)obj[33]);
		bulktablelist.add(mdrBulkString);
		
		}
		
		return bulktablelist;
		
	}
	
	public Map<String, Object> saveMDR(MultipartFile file) {
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
	    try {
	    	
	    	List<BulkCsvMerMdrDto> BulkDto = MdrBulkCsvHelper.csvToMstmerchantMdr(file.getInputStream());
	    	mdrBulkCsvRepo.deleteAll();
	    	
	    	for (BulkCsvMerMdrDto obj : BulkDto) {
	    		BulkCsvMdr MdrBulks = new BulkCsvMdr();
	    		MdrBulks.setSrNo(obj.getSr_no());
	    		MdrBulks.setMerchantId(obj.getMerchant_id());
	    		MdrBulks.setSpId(obj.getSp_id());
	    		MdrBulks.setBankId(obj.getBank_id());
	    		MdrBulks.setInstrumentId(obj.getInstrument_id());
	    		MdrBulks.setMinAmt(obj.getMin_amt());
	    		MdrBulks.setMaxAmt(obj.getMax_amt());
	    		MdrBulks.setMdrType(obj.getMdr_type());
	    		MdrBulks.setAggrMdr(obj.getAggr_mdr());
	    		MdrBulks.setResellerMdr(obj.getReseller_mdr());
	    		MdrBulks.setBaseRate(obj.getBase_rate());
	    		MdrBulks.setMinBaseRate(obj.getMin_base_rate());
	    		MdrBulks.setMaxBaseRate(obj.getMax_base_rate());
	    		MdrBulks.setMinMdr(obj.getMin_mdr());
	    		MdrBulks.setMaxMdr(obj.getMax_mdr());
	    		MdrBulks.setMid(obj.getMid());
	    		MdrBulks.setTid(obj.getTid());
	    		MdrBulks.setChannelId(obj.getChannel_id());
	    		MdrBulks.setStartDate(obj.getStart_date());
	    		MdrBulks.setEndDate(obj.getEnd_date());
	    		MdrBulks.setPrefrences(obj.getPrefrences());
	    		MdrBulks.setSurcharge(obj.getSurcharge());
	    		MdrBulks.setPayout(obj.getPayout());
	    		MdrBulks.setCardVariantName(obj.getCard_variant_name());
	    		MdrBulks.setNetwork(obj.getNetwork());
	    		MdrBulks.setInstrumentBrand(obj.getInstrument_brand());
	    		MdrBulks.setBankMdrType(obj.getBank_mdr_type());
	    		MdrBulks.setMinResellerMdr(obj.getMin_reseller_mdr());
	    		MdrBulks.setMaxResellerMdr(obj.getMax_reseller_mdr());
	    		MdrBulks.setResellerMdrType(obj.getReseller_mdr_type());
	    		MdrBulks.setPayout_escrow(obj.getPayout_escrow());
	    		
	    		mdrBulkCsvRepo.save(MdrBulks);
			}
	    	js1.put("Status", "Success");
	    	js1.put("MessageUpload", "File Upload Successfully ");
	    	
	    } catch (Exception e) {
//	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	      	js1.put("Status", "Error");
	    	js1.put("MessageUpload", "fail to store csv data: " + e.getMessage());
	    }
	    Msg = js1.toMap();
		return Msg;
	  }
	
	
	public ArrayList GetCreationDetails(String Mid) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_MerchantCreationDetails(?)}");
            callableStatement.setString(1, Mid);
			return callableStatement;
		}, prmtrsList);
		ArrayList<String> arrayList = new ArrayList();
		arrayList = (ArrayList<String>) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList;
		}
		

		System.out.println("arrayList::::::::::::::: "+arrayList); 
		
		JSONArray arrayjson = new JSONArray(arrayList);
		System.out.println("arrayjson::::::"+arrayjson);

//		System.out.println("additional::::::::::::::: "+additional); 
	
		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public ArrayList getRemarks(String Type, String Otype, String Mid, String AppType) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_RemarkList(?,?,?,?)}");
            callableStatement.setString(1, Type);
            callableStatement.setString(2, Otype);
            callableStatement.setString(3, Mid);
            callableStatement.setString(4, AppType);


			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList;
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	}
	public Map<String, Object> CheckMerchantBank(String productId, String Account)
	{
		JSONObject js = new JSONObject();
	int i = merchantBankRepo.CheckProductId(productId);
	int j = merchantBankRepo.CheckGlobalAccount(Account);
	
	System.out.println("Check Condtions " +i);
	if(i<=0 && j<=0) {
	js.put("Message", "Checked");
	js.put("Status", "true");	
	}else
	{
		if(i>0)
		{
		js.put("Message","Product_Id Already Exits");
		js.put("Status", "false");	
		}
		if(j>0)
		{
		js.put("Message","Account Number Already Exits");
		js.put("Status", "false");
		}
	}
	
	return js.toMap();
}	
	
	public String getInstrumentType(String InstrumentId,String MerchantId, String SpID,String BankId,String BrandId,String App_Status,String App_Remark,String AddedBy, String Id) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call Prod_InstrumentActivationStatus(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, MerchantId);
            callableStatement.setString(2, InstrumentId);
            callableStatement.setString(3, SpID);
            callableStatement.setString(4, BankId);
            callableStatement.setString(5, BrandId);
            callableStatement.setString(6, App_Status);
            callableStatement.setString(7, App_Remark);
            callableStatement.setString(8, AddedBy);
            callableStatement.setString(9, Id);

			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList.toString();
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	}
	
	public String getInstrumentType1Yes(String InstrumentId,String MerchantId, String SpID,String BankId,String BrandId,String App_Status,String App_Remark,String AddedBy, String Id, String merVirtualAdd) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call Prod_InstrumentActivationStatusYes(?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, MerchantId);
            callableStatement.setString(2, InstrumentId);
            callableStatement.setString(3, SpID);
            callableStatement.setString(4, BankId);
            callableStatement.setString(5, BrandId);
            callableStatement.setString(6, App_Status);
            callableStatement.setString(7, App_Remark);
            callableStatement.setString(8, AddedBy);
            callableStatement.setString(9, Id);
            callableStatement.setString(10, merVirtualAdd);
			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList.toString();
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	}
	public String getInstrumentList(String MerchantId) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_InstrumentGetList(?,?)}");
            callableStatement.setString(1, MerchantId);
            callableStatement.setString(2, "");

			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList.toString();
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	}
	
	public String DeleteInstrument(String Id)
	{
		String id="1";
		String Mid="";
		 merchantRepo.InstrimentList(Mid,Id);
		return id;
	}
	public static void main(String[] args)
	{
		String firstName="Seema";
		String password = GeneralUtil.crypt(firstName + "@2022");
		
		System.out.print("password "+password);

	}
	
public List<MerchantKycDoc> insertKycOtherDoc(String MerchantId, String docType){
		
		MerchantKycDoc merchantkycDoc = new MerchantKycDoc();	
		List<BusinessTypeDto> dbbustype = new ArrayList<>();
		List<MerchantKycDoc> btrypedel = merchantKycDocRepo.uploadKycOtherDocs(MerchantId, docType);	
		
		

//		for (Object[] obj : btrypedel) {
//			BusinessTypeDto merBasDtoDet = new BusinessTypeDto();
//			merBasDtoDet.setBusinessTypeId((int) obj[3]);
//			merBasDtoDet.setBusinessType((String) obj[1]);
//			merBasDtoDet.setDocument((String) obj[4]);			
//			merBasDtoDet.setDocumentDescription((String) obj[5]);
//			merBasDtoDet.setDocid((int) obj[2]);
//			merBasDtoDet.setKycId((BigInteger) obj[6]);
//			merBasDtoDet.setDocname((String) obj[7]);
//			merBasDtoDet.setDocpath((String) obj[8]);
//			merBasDtoDet.setDocType((String) obj[9]);
//			
//
//			dbbustype.add(merBasDtoDet);
//		}
//		

		return btrypedel;
	}


public void updateFiledStatus(String merchnatId, String type) {
	
	logger.info("before::::::::::::"+type+"::::::: Updating Value"+merchnatId);

	try {
		String status = "Re-Initiated";
		//Optional<MerchantList> merchant = merchantRepo.findByMerchantIdAndStatus(merchnatId, status);
		Optional<MerchantList> merchant=merchantRepo.getMerchantListByMerchantIdAndStatus(merchnatId,status);
		logger.info("Merchant is present "+merchant.isPresent());
		if(merchant.isPresent())
		{
			MerchantList merchantU = merchant.get();
			Map<String, String> data = new HashMap<String,String>();
			
			if(StringUtils.isNotBlank(merchantU.getResFieldsDetails()))
			data = mapper.readValue(merchantU.getResFieldsDetails(), Map.class);
			logger.info("Merchant::::::::::::::::::::::; data"+data); 
			if (type.equalsIgnoreCase("Kyc")) 
			{
				data.put("Kyc", "1");
				if (data.containsKey("account") && data.get("account").equals("1"))
					merchantU.setStatus("Initiated");
			}
			else if (type.equalsIgnoreCase("Account")) {
				data.put("account","1");
				
				if (data.containsKey("Kyc") && data.get("Kyc").equals("1"))
					merchantU.setStatus("Initiated");
			} 
			
			String updatedjson = mapper.writeValueAsString(data);
logger.info("Json Value Updated::::::::::::"+updatedjson);
merchantU.setResFieldsDetails(updatedjson);
			merchantRepo.save(merchantU);
			logger.info("After Updating Value");
		}

	} catch (Exception e) {
		e.printStackTrace();
		logger.info("Exception Value:::::::::::::::",e);

	}
}

public String getMerchntDataForYesB(String Mid) throws Exception {
	// TODO Auto-generated method stub
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	try {
		System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
	Map<String, Object> resultData = JdbcTemplate.call(connection -> {
		CallableStatement callableStatement = connection.prepareCall("{call pro_MerchantDetailForYesBank(?)}");
        callableStatement.setString(1, Mid);
       
		return callableStatement;
	}, prmtrsList);
	ArrayList arrayList = new ArrayList();
	arrayList = (ArrayList) resultData.get("#result-set-1");
	if (arrayList.isEmpty()) {
		arrayList.add("Not Found Data");
		
	}
//	logger.info("arrayList::::::"+arrayList);
	JSONArray jsArray = new JSONArray(arrayList);
//	logger.info("jsArray::::"+jsArray);
//	System.out.print("arrayList::::::::::"+arrayList.toString());
	return jsArray.toString();
} catch (Exception e1) {
	throw new Exception();
}
}

public String getYesUPIDeatils(String MerchantId,String SpID,String InstrumentId) throws Exception {
	// TODO Auto-generated method stub
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	try {
		System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
	Map<String, Object> resultData = JdbcTemplate.call(connection -> {
		CallableStatement callableStatement = connection.prepareCall("{call pro_GetSubmerchantReponse(?,?,?)}");
        callableStatement.setString(1, MerchantId);
        callableStatement.setString(2, SpID);
        callableStatement.setString(3, InstrumentId);

		return callableStatement;
	}, prmtrsList);
	ArrayList arrayList = new ArrayList();
	arrayList = (ArrayList) resultData.get("#result-set-1");
	if (arrayList.isEmpty()) {
		arrayList.add("Not Found Data");
		return arrayList.toString();
	}
	

	return arrayList.toString();
} catch (Exception e1) {
	throw new Exception();
}
}

public Map<String, Object> forgetPasswordService(String validuserId, String authString) {
	Map<String, Object> Msg = null ;
	String message = null;
	JSONObject js1 = new JSONObject();
		boolean validaggregatorID = merchantRepo.findByuserId(validuserId);
		if(validaggregatorID == true) {			
			String first = validuserId.substring(0, 2);
			List<Object[]> userId =  merchantRepo.findUserIdById(validuserId);
					
			if(userId !=null) {				
				for(Object[] obj: userId) 
	            {
					 String TradeName= null;
					 String RoleType= null;
					if(first.equals("R0")) {
						TradeName = merchantRepo.findUserIdByRId(validuserId);
						RoleType = "Aggregator";
					}else if(first.equals("M0")) {
						TradeName = merchantRepo.findUserIdByMId(validuserId);
						RoleType = "Merchant";
					}else {
						TradeName = (String) obj[1];
						RoleType = "Admin";
					}
	                String UserId = (String) obj[0];
	                String FullName = (String) obj[1];
//	                String TradeName = (String) obj[3];
	                String emailId = (String) obj[2];	                
	                GeneralUtil generalUtil = new GeneralUtil();
	                UUID UUID1ID= UUID.randomUUID();
	                Date date = Calendar.getInstance().getTime();  
	                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	                Date newDate = DateUtils.addHours(date, 24);
	                String strDate = dateFormat.format(date);
	                String strDate1 = dateFormat.format(newDate);
	                if(emailId == "" || emailId == null) {
	                	message = "Your Registered Email-Id Not Found";
						js1.put("Status", "Error");
						js1.put("Message", message);
	                }else {
		                try {
		                	String UserType = generalUtil.isUserMatching(authString);
		                	if (RoleType.equals(UserType)) {
//		                		emailService.sendForgetPassword(emailId, UUID1ID, FullName, UserId, strDate, strDate1, TradeName, RoleType );
							
								message = "Email Send to Your Registered Email-Id";
								js1.put("Status", "Success");
								js1.put("Message", message);
		                	}else {
		                		message = "Invalid User Found";
								js1.put("Status", "Error");
								js1.put("Message", message);
		                	}
						} catch (Exception e) {						
							e.printStackTrace();
						}	
	                }
	            }				
			}
		}else {
			message = "Invalid User Found ";
			js1.put("Status", "Error");
			js1.put("Message", message);
		}	
		Msg = js1.toMap();		
	return Msg;
}

public Map<String, Object> resetPasswordService(String tokenValidation, String password) {
	Map<String, Object> Msg = null ;
	String message = null;
	JSONObject js1 = new JSONObject();
			String passwordChange = password;
			GeneralUtil generalUtil = new GeneralUtil();
			Pattern UUID_REGEX = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
			Matcher matcher = UUID_REGEX.matcher(tokenValidation);
			if(matcher.matches() == true) {
				List<Object[]> userId = merchantRepo.findByuuId(tokenValidation);
				if(userId !=null) {				
					for(Object[] obj: userId) 
			            {
			                String UserId = (String) obj[1];
			                Date createdon = (Date) obj[3];
			                Date expireon = (Date) obj[4];
			                String vereified = (String) obj[5];	                
			                LocalDateTime now = LocalDateTime.now();
			                Timestamp timestamp = Timestamp.valueOf(now);   
			                long difference = timestamp.getTime()- createdon.getTime();
			                int hours =(int) difference/(1000*60*60);
			                Duration duration = Duration.ofMillis(difference);
			                long seconds = duration.getSeconds();
			                long HH = seconds / 3600;
			                 if (HH >= 0 && HH <=24) {
			                	 if (vereified.equals("0")) {	      
			                		String dboldpass = merchantRepo.findUserIdByPass(UserId);
			                		String olduserPass = generalUtil.decrypt(dboldpass);
			             			String passwordChangeEn = generalUtil.crypt(passwordChange);
			             			if(!olduserPass.equals(password)) {
			             				String verified = "1";
			             				merchantRepo.updateResetPass(UserId, passwordChangeEn);
			             				merchantRepo.updateverified(tokenValidation, verified);
			             				message = "Your Password Reset Successfully";
			             				js1.put("Status", "Success");
			             				js1.put("Message", message);
			             			}else {
			             				 message = "New Password Should Not The Same As The Previous One";
				                		 js1.put("Status", "Error");
				 						 js1.put("Message", message);
			             			}
			                	 }else if(vereified.equals("1")) {
			                		 message = "Your Link Already Verified";
			                		 js1.put("Status", "Error");
			             			 js1.put("Message", message);
			                	 }else if(vereified.equals("2")) {
			                		 message = "Your Link Has Been Expired";
			                		 js1.put("Status", "Error");
			             			 js1.put("Message", message);
			                	 }
			                 }else {
			                	 message = "Your Link Has Been Expired";
			                	 js1.put("Status", "Error");
		             			 js1.put("Message", message);
			                	 if (vereified.equals("1") || vereified.equals("0") ){
				                	 String verified = "2";
				                	 merchantRepo.updateverified(tokenValidation, verified);
			                	 }
			                 }
			            }
				}else {
					message = "Aggregator Id " + tokenValidation +" Is Invalid ";
					js1.put("Status", "Error");
	    			 js1.put("Message", message);
				}
			}else {
				message = "Token Id " + tokenValidation +" Is Invalid ";
				js1.put("Status", "Error");
				js1.put("Message", message);  
		}
			Msg = js1.toMap();
			
	return Msg;
 }

public  Map<String, Object> resetPasswordServiceInternal(String oldpassword, String newpassword, String JwtToken){
	Map<String, Object> Msg = null ;
	String message = null;
	JSONObject js1 = new JSONObject();
	GeneralUtil generalUtil = new GeneralUtil();
	List<Object[]> userId =  merchantRepo.findUserIdById(JwtToken);
	String first = JwtToken.substring(0, 2);
	String UserId = null;
	String FullName = null;
	String TradeName= null;
	String emailId = null;
	if(userId !=null) {				
		for(Object[] obj: userId) 
        {
			if(first.equals("R0")) {
				TradeName = merchantRepo.findUserIdByRId(JwtToken);				
			}else if(first.equals("M0")) {
				TradeName = merchantRepo.findUserIdByMId(JwtToken);				
			}else {
				TradeName = (String) obj[1];				
			}
             UserId = (String) obj[0];
             FullName = (String) obj[1];
             emailId = (String) obj[2];
        }
	}
	if (JwtToken !=null) {
		String dboldpass = merchantRepo.findUserIdByPass(JwtToken);
		String olduserPass = generalUtil.decrypt(dboldpass);

		if(olduserPass.equals(oldpassword)) {
			String passwordChangeEn = generalUtil.crypt(newpassword);
			merchantRepo.updateResetPass(JwtToken, passwordChangeEn);
				try {
				//	emailService.sendresetPasswordInternal(emailId, TradeName );
				} catch (Exception e) {				
					e.printStackTrace();
				}
			message = "Your Password Reset Successfully";
			js1.put("Status", "Success");
			js1.put("Message", message);
			
		}else {
			message = "Current Password Is Not Matching";
			js1.put("Status", "Error");
			js1.put("Message", message);
		}
		
	}
	Msg = js1.toMap();
	return Msg;
 }

public String getTxnAmtVolume(String iType, String iUserID,String iDate) throws Exception {

	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	try {
		System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_TxnAmtVolume(?,?,?)}");
			callableStatement.setString(1, iType);
			callableStatement.setString(2, iUserID);
			callableStatement.setString(3, iDate);

			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");

		}
//		logger.info("arrayList::::::"+arrayList);
		JSONArray jsArray = new JSONArray(arrayList);
//		logger.info("jsArray::::"+jsArray.toString());
//	System.out.print("arrayList::::::::::"+arrayList.toString());
		return jsArray.toString();
	} catch (Exception e1) {
		throw new Exception();
	}
}



public Map<String, Object> uploadFileStatus(MultipartFile imageFile, String addedBy, String merchantId) {
	Map<String, Object> Msg = null ;
	StringBuilder filesname = new StringBuilder();
	String FilexlName = imageFile.getOriginalFilename();
	File filexl = new File(FilexlName);
	String jsonArray = null;
	JSONObject js1 = new JSONObject();
	if(imageFile !=null) {
		String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
//		if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx") 
//				|| extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg")) {
			String MerchantId = merchantId;
			logger.info("MerchantId:::::::::"+MerchantId);
			Date date = Calendar.getInstance().getTime();  
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
	        String strDate = dateFormat.format(date);  
	        String strDatewospc = strDate.replaceAll("\\s", "");
	        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
			String fileOrgname = imageFile.getOriginalFilename();
			String filesname1 = fileUploadLocation+ "/LogoFolder"+ "/" +addedBy+"/"+ MerchantId +"/";
			logger.info("filesname1::::::::"+filesname1);
			fileOrgname= fileOrgname.replaceAll("\\s", "");
			String nameFileconvert =  strDatewosp +"_"+fileOrgname;
			logger.info("nameFileconvert:::::::::"+nameFileconvert);
			File file = new File(filesname1);
			if (!file.exists()) {            
	            file.mkdirs();            
	        }	
			try {
				Path path = Path.of(filesname1,nameFileconvert);
				filesname.append(nameFileconvert);
				Files.write(path, imageFile.getBytes());
				String DocRef = filesname1 + nameFileconvert;
				js1.put("Status", "Success");
				js1.put("Message", "File Uploaded ");
				js1.put("DocPath", DocRef);
				
				logger.info("js1.toMap():::::"+js1.toMap().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}			
	}else {
		js1.put("Status", "Error");
		js1.put("Message", "File Not Found ");
	}
	Msg = js1.toMap();
	return Msg;
}

public Map<String, Object> uploadFileLogoWhitelist(MultipartFile imageFile, String addedBy, String merchantId) {
	Map<String, Object> Msg = null ;
	StringBuilder filesname = new StringBuilder();
	String FilexlName = imageFile.getOriginalFilename();
	File filexl = new File(FilexlName);
	String jsonArray = null;
	JSONObject js1 = new JSONObject();
	if(imageFile !=null) {
		String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
//		if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx") 
//				|| extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg")) {
			String MerchantId = merchantId;
			logger.info("MerchantId:::::::::"+MerchantId);
			Date date = Calendar.getInstance().getTime();  
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
	        String strDate = dateFormat.format(date);  
	        String strDatewospc = strDate.replaceAll("\\s", "");
	        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
			String fileOrgname = imageFile.getOriginalFilename();
			String filesname1 = uploadDirectoryWhileListLogo + "/" + addedBy + "/" + MerchantId +"/";
			logger.info("filesname1::::::::"+filesname1);;
			fileOrgname= fileOrgname.replaceAll("\\s", "");
			String nameFileconvert =  strDatewosp +"_"+fileOrgname;
			logger.info("nameFileconvert:::::::::"+nameFileconvert);
			File file = new File(filesname1);
			if (!file.exists()) {            
	            file.mkdirs();            
	        }	
			try {
				Path path = Path.of(filesname1,nameFileconvert);
				filesname.append(nameFileconvert);
				Files.write(path, imageFile.getBytes());
				String DocRef = filesname1 + nameFileconvert;
				js1.put("Status", "Success");
				js1.put("Message", "File Uploaded ");
				js1.put("DocPath", DocRef);
				
				logger.info("js1.toMap():::::"+js1.toMap().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}			
	}else {
		js1.put("Status", "Error");
		js1.put("Message", "File Not Found ");
	}
	Msg = js1.toMap();
	return Msg;
}

}
