package com.crm.services;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.MerchantKycDocRepo;
import com.crm.Repository.MerchantMdrRepo;
import com.crm.Repository.ResellerBankAccRepo;
import com.crm.Repository.ResellerRepository;
import com.crm.Repository.UserRepository;
import com.crm.ServiceDaoImpl.CommonServiceDaoImpl;
import com.crm.dto.BusinessTypeDto;
import com.crm.dto.MerchantDocDto;
import com.crm.dto.ResellerBankAccountDto;
import com.crm.dto.ResellerBasicSetupDto;
import com.crm.dto.ResellerDto;
import com.crm.dto.ResellerKycDocDto;
import com.crm.dto.ResellerListDto;
import com.crm.dto.ResellerListPaginationDto;
import com.crm.model.MerchantKycDoc;
import com.crm.model.ResellerBankAcc;
import com.crm.model.ResellerEntity;
import com.crm.model.User;
import com.crm.util.GeneralUtil;


@Service
public class ResellerService {
	 private static final Logger logger = LoggerFactory.getLogger(ResellerService.class);
	 public static String uploadDirectory = "/home/KYCDOCUMENTS/Attachments";
	@Autowired
	private ResellerRepository resellerRepo;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ResellerBankAccRepo resellerBankAccRepo;
	
	@Autowired
	private CommonServiceDaoImpl CommonServiceDaoImpl;
	
	@Autowired
	private MerchantMdrRepo merchantMdrRepo;
	
	@Autowired
	private MerchantKycDocRepo merchantKycDocRepo;
	
 @Autowired
private JdbcTemplate JdbcTemplate;
	

	public ResellerDto  createUpdateReseller(ResellerDto resellerReqDto) {

		String operationType=null;
		String resellerId = null;
		if( resellerReqDto.getResellerId() != null && resellerReqDto.getResellerId() !="NA" && !resellerReqDto.getResellerId().isBlank() && !resellerReqDto.getResellerId().isEmpty())		
		{
			
			Optional<ResellerEntity> resellerIdcheck = resellerRepo.findByResellerId(resellerReqDto.getResellerId()); 
			
			logger.info("createUpdateReseller :: !resellerIdcheck.isEmpty() {} resellerIdcheck.size()>0 {}",resellerIdcheck.isEmpty(),resellerIdcheck.isPresent());
			
			if(resellerIdcheck.isPresent() && resellerIdcheck.isPresent())
			{
				operationType = "update";
			}
			else
			{
				operationType="create";
			}
			
		}
		
		if(operationType== null || operationType.equalsIgnoreCase("create"))
		{
			
			operationType="create"; // in case of null 
			resellerId = resellerRepo.getGeneratedResellerId();
			GeneralUtil generalUtil = new GeneralUtil();
			String firstName = resellerReqDto.getResellerName().contains(" ") ? resellerReqDto.getResellerName().split(" ")[0]
					: resellerReqDto.getResellerName();
			String password = generalUtil.crypt(firstName.length()>11?firstName.substring(0,11):firstName+"@"+new SimpleDateFormat("Y").format(new Date()));
					//(firstName + "@2022");
			User user = new User();
			user.setFullName(resellerReqDto.getResellerName());
			user.setBlocked(false);
			user.setCreatedBy(resellerReqDto.getCreatedBy());
			user.setUserId(resellerId);
			user.setMerchantId(resellerId); // resellerId
			user.setPassword(password);
			long millis = System.currentTimeMillis();
			user.setCreatedOn(new java.sql.Date(millis));
			user.setEmailId(resellerReqDto.getEmailId());
			user.setGroupId(2);  // need to confirm groupId
			user.setRoleId(26);   // roleId  23 for local system and while commiting we need to put 26 and commit the code
			user.setIsActive(true);
			user.setIsDeleted('N');
			user.setContactNumber(resellerReqDto.getContactNumber());
			// user = userRepository.save(user);
			userRepository.save(user);
			
			resellerReqDto.setResellerId(resellerId);
			resellerReqDto.setIsDeleted("N");
			resellerReqDto.setRemark("NEW USER");
			logger.info("New Generated resellerId : {} ",resellerId);
		}
	

		List<Object[]> savedUpdateReseller = 
				resellerRepo.createUpdateReseller(resellerReqDto.getResellerId(), resellerReqDto.getResellerName(),
						resellerReqDto.getContactPerson(), resellerReqDto.getEmailId(), resellerReqDto.getContactNumber(),
						resellerReqDto.getLegalName(), resellerReqDto.getBrandName(), resellerReqDto.getBusinessType(),
						resellerReqDto.getDateOfIncorporation(), resellerReqDto.getBusinessCategory(),
						resellerReqDto.getSubCategory(), resellerReqDto.getBusinessModel(), resellerReqDto.getTurnoverLastFinancialYear(),
						resellerReqDto.getTurnoverMonthlyExpeced(), resellerReqDto.getAvgAmountPerTxn(), resellerReqDto.getRegisteredAddress(),
						resellerReqDto.getPinCode(), resellerReqDto.getCity(), resellerReqDto.getState(), resellerReqDto.getRemark(),
						resellerReqDto.getInvoiceCycle(), resellerReqDto.getIntegrationType(), resellerReqDto.getIsDeleted(),
						resellerReqDto.getCreatedBy(),operationType, resellerReqDto.getSales_lead());
		
		 	
		if(!savedUpdateReseller.isEmpty())
		{
		
				for (Object[] obj : savedUpdateReseller) {
					resellerReqDto.setId(((BigInteger) obj[0]));   // id
					resellerReqDto.setCreatedOn(((java.sql.Timestamp) obj[25]).toString().substring(0, 19));
				}
				
				if(operationType.equalsIgnoreCase("create"))
				{
				resellerReqDto.setStatus("true");
				resellerReqDto.setRespMessage("Reseller created successfully "+resellerReqDto.getResellerId());
				}
				
				else
				{
					resellerReqDto.setStatus("true");
					resellerReqDto.setRespMessage("Reseller updated successfully "+resellerReqDto.getResellerId());
				}
				
		
		}
		
		else 
		{
			resellerReqDto.setStatus("true");
			resellerReqDto.setRespMessage("There is an issue while adding or updating the data");
		}
		
		logger.info("output while return operationType :: {} :: resellerId : {}  :: reqDto : {}",operationType,resellerId,resellerReqDto.toString());
		return resellerReqDto;

	}
	

	
	public ResellerBankAccountDto createResellerBankDetails(ResellerBankAcc resellerBank) {
		
		// need to check already present (confirm with ujwal)
		
		ResellerBankAccountDto resellerbankAccountDto =new ResellerBankAccountDto();
		Optional<ResellerBankAcc> resellerBanks;
			try {
				long countActiveAcc = resellerBankAccRepo.findByResellerIdIsActiveIsDeleted(resellerBank.getResellerId(),"N","Y");
				
						if(countActiveAcc == 0)
						{
						long millis = System.currentTimeMillis();
						resellerBank.setIsActive("Y");
						resellerBank.setIsDeleted("N");
						resellerBank.setCreatedOn(new java.sql.Timestamp(millis).toString().substring(0, 19));
						resellerBank.setRodt(new java.sql.Timestamp(millis).toString().substring(0, 19));
						
						// resellerBankAcc = resellerBankAccRepo.save(resellerBank);
						resellerBankAccRepo.save(resellerBank);
						
						
						resellerBanks = resellerBankAccRepo.findByResellerId(resellerBank.getResellerId(),"N","Y");
						
						if(resellerBanks.isPresent())
						{
							resellerbankAccountDto.setId(resellerBanks.get().getId());
							resellerbankAccountDto.setResellerId(resellerBanks.get().getResellerId());
							resellerbankAccountDto.setAccountHolderName(resellerBanks.get().getAccountHolderName());
							resellerbankAccountDto.setAccountNumber(resellerBanks.get().getAccountNumber());
							resellerbankAccountDto.setBankName(resellerBanks.get().getBankName());
							resellerbankAccountDto.setIfscCode(resellerBanks.get().getIfscCode());
							
							resellerbankAccountDto.setIsActive(resellerBanks.get().getIsActive());
							resellerbankAccountDto.setIsDeleted(resellerBanks.get().getIsDeleted());
							resellerbankAccountDto.setApprovedBy(resellerBanks.get().getApprovedBy());
							resellerbankAccountDto.setApprovedOn(resellerBanks.get().getApprovedOn());
							resellerbankAccountDto.setCreatedBy(resellerBanks.get().getApprovedBy());
							resellerbankAccountDto.setCreatedOn(resellerBanks.get().getCreatedOn());
							resellerbankAccountDto.setRodt(resellerBanks.get().getRodt());
							
							resellerbankAccountDto.setStatus(true);
							resellerbankAccountDto.setRespMessage("Account details has been added successfully..");
						}
						
						else
						{
							resellerbankAccountDto.setStatus(false);
							resellerbankAccountDto.setRespMessage("Data has been not inserted ");
						}
						
					}
				else
				{
					resellerbankAccountDto.setStatus(false);
					resellerbankAccountDto.setRespMessage("Active Account is already present therefore you can't add more account");
				}
				
			} 
		
		catch (Exception e) {
			
			resellerbankAccountDto.setStatus(false);
			resellerbankAccountDto.setRespMessage("Data has been not inserted "+e.getMessage());
			logger.info("ResellerService.java :: createResellerBankDetails() :: Getting exception :: {}",e.getMessage());
			e.printStackTrace();
		}
		
		logger.info("ResellerService.java :: createResellerBankDetails : resellerbankAccountDto :: {}",resellerbankAccountDto.toString());	
		return resellerbankAccountDto;
	}
	
	
	// need to check
	public ResellerBankAccountDto updateResellerBankDetails(ResellerBankAcc resellerBankAcc) {
		
		logger.info("Request Data :: resellerBankAcc string : {}",resellerBankAcc.toString());
		
		ResellerBankAccountDto resellerBankAccountDto = new ResellerBankAccountDto();
		
		try {
			
			long countActiveAcc = resellerBankAccRepo.findByResellerIdIsActiveIsDeleted(resellerBankAcc.getResellerId(),"N","Y");
			
			if(countActiveAcc>0)
			{
			
			Long id = resellerBankAccRepo.findIdByResellerIdIsActiveIsDeleted(resellerBankAcc.getResellerId(),"N","Y");
			
			logger.info("For update unique : Id : {}",id);
			if(id.toString() != null && id > 0)
			{
			resellerBankAcc.setId(id);
			ResellerBankAcc rb = resellerBankAccRepo.findById(resellerBankAcc.getId()).get();
			
			// Below param has not come from front end hence updated as null therefore putting the previous value
			
			resellerBankAcc.setCreatedOn(rb.getCreatedOn());
			resellerBankAcc.setCreatedBy(rb.getCreatedBy());
			resellerBankAcc.setApprovedBy(rb.getApprovedBy());
			resellerBankAcc.setApprovedOn(rb.getApprovedOn());
			
			long millis = System.currentTimeMillis();
			resellerBankAcc.setRodt(new java.sql.Timestamp(millis).toString().substring(0, 19));
			
			resellerBankAcc = resellerBankAccRepo.save(resellerBankAcc);	
			
			Optional<ResellerBankAcc> resellerBanks = resellerBankAccRepo.findByResellerId(resellerBankAcc.getResellerId(),"N","Y"); 
			
			
			if(resellerBanks.isPresent())
			{
				resellerBankAccountDto.setId(resellerBanks.get().getId());
				resellerBankAccountDto.setResellerId(resellerBanks.get().getResellerId());
				resellerBankAccountDto.setAccountHolderName(resellerBanks.get().getAccountHolderName());
				resellerBankAccountDto.setAccountNumber(resellerBanks.get().getAccountNumber());
				resellerBankAccountDto.setBankName(resellerBanks.get().getBankName());
				resellerBankAccountDto.setIfscCode(resellerBanks.get().getIfscCode());
				
				resellerBankAccountDto.setIsActive(resellerBanks.get().getIsActive());
				resellerBankAccountDto.setIsDeleted(resellerBanks.get().getIsDeleted());
				resellerBankAccountDto.setApprovedBy(resellerBanks.get().getApprovedBy());
				resellerBankAccountDto.setApprovedOn(resellerBanks.get().getApprovedOn());
				resellerBankAccountDto.setCreatedBy(resellerBanks.get().getApprovedBy());
				resellerBankAccountDto.setCreatedOn(resellerBanks.get().getCreatedOn());
				resellerBankAccountDto.setRodt(resellerBanks.get().getRodt());
				
				resellerBankAccountDto.setStatus(true);
				resellerBankAccountDto.setRespMessage("Account details has been updated successfully..");
			}
			
			else
			{
				resellerBankAccountDto.setStatus(false);
				resellerBankAccountDto.setRespMessage("Data has been not updated due to backend DB Error ");
			}
			
			
			
			
			logger.info("ResellerService.java :: updateResellerBankDetails : resellerBanks :: {}",resellerBanks.toString()); 
			return resellerBankAccountDto;
			
			}
			
			else
			{
				resellerBankAccountDto.setStatus(false);
				resellerBankAccountDto.setRespMessage("Not found active account for update against this resellerId"+resellerBankAcc.getResellerId());
			}
		}
			else
			{
				resellerBankAccountDto.setStatus(false);
				resellerBankAccountDto.setRespMessage("Not found active account for update against this resellerId"+resellerBankAcc.getResellerId());
			
			}
		}
		
	
		catch (Exception e) {
			resellerBankAccountDto.setStatus(false);
			resellerBankAccountDto.setRespMessage("Getting Exception while updateing the data : "+resellerBankAcc.getResellerId()+", Message "+e.getMessage());
		}
		
		
		return resellerBankAccountDto;
	}



	public ResellerListPaginationDto GetCreationDetails(String fromDate, String toDate, String merchantId, String name, int norecord, int pageno) {
		
		ResellerListPaginationDto dto = new ResellerListPaginationDto();
		dto.setNumberOfRecords(norecord);
		dto.setPageNumber(pageno);
		List<ResellerListDto> resellerViewDetails = new ArrayList<>();
		
		List<Object[]> viewReseller = resellerRepo.findByResellerByDateNameId(fromDate, toDate, merchantId, name , norecord, pageno);
		
		
		if(!viewReseller.isEmpty())
		{
		for (Object[] obj : viewReseller) {
			ResellerListDto resellerList = new ResellerListDto();
			resellerList.setId((BigInteger) obj[0]);
			resellerList.setResellerId((String) obj[1]);
			resellerList.setResellerName((String) obj[2]);
			resellerList.setContactPerson((String) obj[3]);
			resellerList.setContactNumber((String) obj[4]);
			resellerList.setEmailId((String) obj[5]);
			resellerList.setBusinessType((String) obj[6]);
			resellerList.setState((String) obj[7]);
			resellerList.setCity((String) obj[8]);
			resellerList.setCreatedOn((String) obj[10]);
			resellerList.setStatus((String) obj[11]);
			resellerList.setDateofIncroporation((String) obj[12]);
			resellerList.setKyc_Approvel((String) obj[13]);
			resellerList.setSales_lead((String) obj[14]);
			
			resellerViewDetails.add(resellerList);
		}
		
		}
		List<String> merchantMdr = merchantMdrRepo.getrecordResellerCount(fromDate, toDate, merchantId, name , norecord, pageno );
		JSONArray jsonArray =null;
		jsonArray= new JSONArray(merchantMdr);
		JSONObject rec = jsonArray.getJSONObject(0);
		Long noRecords = rec.getLong("TotalRecords");
		
		dto.setResellers(resellerViewDetails);
		dto.setTotalRecords(noRecords);
		logger.info("ResellerService.java :: GetCreationDetails : resellerBanks :: {}",resellerViewDetails.toString());
		return dto;
	}
	

	public List<ResellerBankAcc> GetViewListOfBankAccOfReseller() {
		
		List<ResellerBankAcc> bankAccViewDetails = new ArrayList<>();
		
		List<Object[]> viewListReseller = resellerBankAccRepo.getAllList("N","Y");
		
		
		if(!viewListReseller.isEmpty())
		{
		for (Object[] obj : viewListReseller) {
			ResellerBankAcc resellerList = new ResellerBankAcc();
			resellerList.setId(((BigInteger) obj[0]).longValue());   // id
			resellerList.setResellerId(((String) obj[1]));
			resellerList.setAccountNumber(((String) obj[2]));
			resellerList.setAccountHolderName(((String) obj[3]));
			resellerList.setIfscCode(((String) obj[4]));
			resellerList.setBankName(((String) obj[5]));
			resellerList.setIsActive(((String) obj[6]));
			resellerList.setIsDeleted(((String) obj[7]));
			resellerList.setCreatedOn(((java.sql.Timestamp) obj[8]).toString().substring(0, 19));
			resellerList.setCreatedBy(((String) obj[9]));
			resellerList.setApprovedBy(((String) obj[10]));
			
			  bankAccViewDetails.add(resellerList);
		}
		
		}
		
		logger.info("ResellerService.java :: GetViewListOfBankAccOfReseller : resellerBanks :: {}",bankAccViewDetails.toString());
		return bankAccViewDetails;
	}
	


	
		public ResellerDto getResellerPersonalDetailByResellerId(String resellerId){
		
			Optional<ResellerEntity> resellerDetails = resellerRepo.findByResellerId(resellerId); // resellerRepo.findById(id);
			
			ResellerDto resellerPersonaldetails = new ResellerDto();
			if (resellerDetails.isPresent())
			{
				resellerPersonaldetails.setId(resellerDetails.get().getId());   // id
				resellerPersonaldetails.setResellerId(resellerDetails.get().getResellerId());
				resellerPersonaldetails.setResellerName(resellerDetails.get().getResellerName());
				resellerPersonaldetails.setContactPerson(resellerDetails.get().getContactPerson());
				resellerPersonaldetails.setEmailId(resellerDetails.get().getEmailId());
				resellerPersonaldetails.setContactNumber(resellerDetails.get().getContactNumber());
				resellerPersonaldetails.setLegalName(resellerDetails.get().getLegalName());
				resellerPersonaldetails.setBrandName(resellerDetails.get().getBrandName());
				resellerPersonaldetails.setBusinessType(resellerDetails.get().getBusinessType());
				resellerPersonaldetails.setDateOfIncorporation(resellerDetails.get().getDateOfIncorporation().toString());
				resellerPersonaldetails.setBusinessCategory(resellerDetails.get().getBusinessCategory());
				resellerPersonaldetails.setSubCategory(resellerDetails.get().getSubCategory());
				resellerPersonaldetails.setBusinessModel(resellerDetails.get().getBusinessModel());
				
				resellerPersonaldetails.setTurnoverLastFinancialYear(resellerDetails.get().getTurnoverLastFinancialYear());
				resellerPersonaldetails.setTurnoverMonthlyExpeced(resellerDetails.get().getTurnoverMonthlyExpeced());
				resellerPersonaldetails.setAvgAmountPerTxn(resellerDetails.get().getAvgAmountPerTxn());
				resellerPersonaldetails.setRegisteredAddress(resellerDetails.get().getRegisteredAddress());
				resellerPersonaldetails.setPinCode(resellerDetails.get().getPinCode());
				resellerPersonaldetails.setCity(resellerDetails.get().getCity());
				resellerPersonaldetails.setState(resellerDetails.get().getState());
				resellerPersonaldetails.setRemark(resellerDetails.get().getRemark());
				resellerPersonaldetails.setInvoiceCycle(resellerDetails.get().getInvoiceCycle());
				resellerPersonaldetails.setIsDeleted(resellerDetails.get().getIsDeleted());
				
				resellerPersonaldetails.setCreatedBy(resellerDetails.get().getCreatedBy());
				resellerPersonaldetails.setCreatedOn(resellerDetails.get().getCreatedOn().toString());
				resellerPersonaldetails.setApprovedBy(resellerDetails.get().getApprovedBy());
				if(resellerDetails.get().getApprovedBy() != null && !resellerDetails.get().getApprovedBy().isBlank() && !resellerDetails.get().getApprovedBy().isEmpty())
				{
					resellerPersonaldetails.setApprovedOn(resellerDetails.get().getApprovedOn().toString());
				}
				
				resellerPersonaldetails.setRodt(resellerDetails.get().getRodt().toString());
				resellerPersonaldetails.setStatus(resellerDetails.get().getStatus());
				resellerPersonaldetails.setKyc_approvel(resellerDetails.get().getKyc_approvel());
				resellerPersonaldetails.setOprations_approvel(resellerDetails.get().getOprations_approvel());
				resellerPersonaldetails.setRisk_approvel(resellerDetails.get().getRisk_approvel());
				resellerPersonaldetails.setRespMessage("Personal Detail found of reseller");
				resellerPersonaldetails.setSales_lead(resellerDetails.get().getSales_lead());
							
			}
			
			else
			{
				
				resellerPersonaldetails.setStatus("true");
				resellerPersonaldetails.setRespMessage("ResellerId not found for get personal details of reseller");
			}
			logger.info("getResellerPersonalDetailByResellerId :: resellerId : {}",resellerId);
			logger.info("ResellerService.java :: getResellerPersonalDetailByResellerId :: {} ",resellerPersonaldetails.toString());
		return resellerPersonaldetails;
	}
	
		
		
		
		public ResellerBankAccountDto getResellerBankDetailByResellerId(String resellerId){
			
			Optional<ResellerBankAcc> resellerBankDetails =  resellerBankAccRepo.findByResellerId(resellerId,"N","Y");
			
			ResellerBankAccountDto resellerbankAccountDto =new ResellerBankAccountDto();
			
			
			if(resellerBankDetails.isPresent())
			{
				resellerbankAccountDto.setId(resellerBankDetails.get().getId());
				resellerbankAccountDto.setResellerId(resellerBankDetails.get().getResellerId());
				resellerbankAccountDto.setAccountHolderName(resellerBankDetails.get().getAccountHolderName());
				resellerbankAccountDto.setAccountNumber(resellerBankDetails.get().getAccountNumber());
				resellerbankAccountDto.setBankName(resellerBankDetails.get().getBankName());
				resellerbankAccountDto.setIfscCode(resellerBankDetails.get().getIfscCode());
				
				resellerbankAccountDto.setIsActive(resellerBankDetails.get().getIsActive());
				resellerbankAccountDto.setIsDeleted(resellerBankDetails.get().getIsDeleted());
				resellerbankAccountDto.setApprovedBy(resellerBankDetails.get().getApprovedBy());
				resellerbankAccountDto.setApprovedOn(resellerBankDetails.get().getApprovedOn());
				resellerbankAccountDto.setCreatedBy(resellerBankDetails.get().getApprovedBy());
				resellerbankAccountDto.setCreatedOn(resellerBankDetails.get().getCreatedOn());
				resellerbankAccountDto.setRodt(resellerBankDetails.get().getRodt());
				resellerbankAccountDto.setStatus(true);
				resellerbankAccountDto.setRespMessage("Account details has been fetched");
			}
			
			else
			{
				resellerbankAccountDto.setStatus(false);
				resellerbankAccountDto.setRespMessage("Account details has been not found or not active");
			}
			
			
			logger.info("ResellerService.java :: getResellerBankDetailPid :: {} ",resellerbankAccountDto.toString());
		return resellerbankAccountDto;
	}


		public Optional<ResellerEntity> deleteResellerPersonaDetail(BigInteger pid) {
			
			// if delete is required then we need to soft delete not delete parmanently
			
			Optional<ResellerEntity> resellerPerDetaildel = resellerRepo.findById(pid);
			
			ResellerEntity resellerData= resellerPerDetaildel.get();
			resellerRepo.deleteById(pid);
			
			
			Optional<ResellerEntity> resellerEntity = resellerRepo.findByResellerId(resellerData.getResellerId()); 
			
			return resellerEntity;
		}
		
		public List<ResellerBankAccountDto> deleteResellerBankAcc(BigInteger Pid, String resellerId) {
			
			String is_deleted =("Y");
			String is_active = ("N");
			
			resellerBankAccRepo.deleteReselletBank(is_deleted, is_active, Pid );
			List<Object[]> resellerBankDetails =  resellerBankAccRepo.getListByResellerId(resellerId,"N","Y");
			List<ResellerBankAccountDto> bankAccViewDetails = new ArrayList<>();
			if(!resellerBankDetails.isEmpty())
			{
				
				for (Object[] obj : resellerBankDetails) {
					ResellerBankAccountDto resellerList = new ResellerBankAccountDto();
					resellerList.setId((Long.valueOf(obj[0].toString())));;   // id
					resellerList.setResellerId(((String) obj[1]));
					resellerList.setAccountNumber(((String) obj[2]));
					resellerList.setAccountHolderName(((String) obj[3]));
					resellerList.setIfscCode(((String) obj[4]));
					resellerList.setBankName(((String) obj[5]));
					resellerList.setIsActive(((String) obj[6]));
					resellerList.setIsDeleted(((String) obj[7]));
					resellerList.setCreatedOn(((java.sql.Timestamp) obj[8]).toString().substring(0, 19));
					resellerList.setCreatedBy(((String) obj[9]));
					resellerList.setApprovedOn(((java.sql.Timestamp) obj[10]).toString().substring(0, 19));
					resellerList.setApprovedBy(((String) obj[11]));
					resellerList.setStatus(true);
					resellerList.setRespMessage("Account details has been fetched");
					  bankAccViewDetails.add(resellerList);
				}
						
				
			}
			
			else
			{
				ResellerBankAccountDto resellerList = new ResellerBankAccountDto();
				resellerList.setStatus(false);
				resellerList.setRespMessage("Account details has been not found or not active");
			}
			
			
			logger.info("ResellerService.java :: getResellerBankDetailPid :: {} ",bankAccViewDetails.toString());	
			
			
			return bankAccViewDetails;
		}
		
		public List<ResellerKycDocDto> insertKycDoc(String ResellerId, String docType){		
			
			List<Object[]> btrypedel = resellerRepo.createUpload(ResellerId, docType);
			List<ResellerKycDocDto> kycuploaddetails = new ArrayList<>();
			
			for (Object[] obj : btrypedel) {
				ResellerKycDocDto kycdocdet = new ResellerKycDocDto();
				kycdocdet.setId((int) obj[0]);
				kycdocdet.setDocName((String) obj[1]);
				kycdocdet.setDocpath((String) obj[2]);
				kycdocdet.setDocType((String) obj[3]);
				kycdocdet.setResellerId((String) obj[4]);
				kycdocdet.setModifiedBy((String) obj[5]);
				kycdocdet.setUpdatedBy((String) obj[7]);
				
				kycuploaddetails.add(kycdocdet);
				
			}
			
			return kycuploaddetails;
		}
		
		public List<BusinessTypeDto> getKycDocumentList(int businesstypeId, String merchantId){
			
			
			List<BusinessTypeDto> dbbustype = new ArrayList<>();
			List<Object[]> btrypedel =  resellerRepo.findByBussineTypeId(businesstypeId, merchantId);
			 ArrayList doclist = merchantKycDocRepo.gstDocumentId();

			for (Object[] obj : btrypedel) {
				BusinessTypeDto merBasDtoDet = new BusinessTypeDto();
				int docid = ((int) obj[2]);
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
			
			return dbbustype;
		}
		public List<ResellerKycDocDto> deleteuploaddoc(ResellerKycDocDto merchantKycDoc){
			MerchantKycDoc merchantkycDoc = new MerchantKycDoc();
			merchantkycDoc.setId(merchantKycDoc.getId());
			merchantkycDoc.setDocpath(merchantKycDoc.getDocpath());		
			merchantkycDoc.setMerchantId(merchantKycDoc.getResellerId());
			
			List<ResellerKycDocDto> resellerKycDocList = new ArrayList<>();
			
			File file = new File(merchantkycDoc.getDocpath());
				
			try {
				if (file.exists()) {
		        	file.delete();
		        }
				List<Object[]> resellerkycdel = resellerRepo.findById(merchantkycDoc.getId());
				int docId;
				String ResellerId=null;
				for (Object[] obj : resellerkycdel) {
					docId=(int) obj[0];
					ResellerId =(String) obj[4];
					resellerRepo.deleteById(docId);
				}
				
//				List<Object[]> kycdocdtodel = resellerRepo.getAllListResellerKyc(merchantKycDoc.getResellerId());
//				
//				for (Object[] obj : kycdocdtodel) {
//					ResellerKycDocDto kycDocDto = new ResellerKycDocDto();
//					kycDocDto.setId((int) obj[0]);
//					kycDocDto.setDocName((String) obj[1]);
//					kycDocDto.setDocpath((String) obj[2]);
//					kycDocDto.setDocType((String) obj[3]);
//					kycDocDto.setResellerId((String) obj[4]);
//					kycDocDto.setModifiedBy((String) obj[5]);
//					kycDocDto.setModifiedOn((String) obj[6]);
//					kycDocDto.setUpdatedBy((String) obj[7]);
//					kycDocDto.setUpdateOn((String) obj[8]);		
//					resellerKycDocList.add(kycDocDto);
//				}
				
				} catch (Exception e) {
				e.printStackTrace();
			}
			
			return resellerKycDocList;
		}
		public List<ResellerBasicSetupDto> setResellerBasicInfo(String ressellerId, String returnUrl, String isAutoRefund, String hours, String minutes, String isPushUrl,
				String pushUrl, String settlementCycle, String resellerDashboardRefund, String rsDisableRefundCc, String rsDisableRefundDc, String rsDisableRefundNb, String rsDisableRefundUpi,
				String rsDisableRefundWallet, String refundApi, String refundApiDisableCc, String refundApiDisableDc, String refundApiDisableNb, String refundApiDisableUpi, String refundApiDisableWallet,
				String integrationType, String isretryAllowed, String bpsEmailNotification, String bpsSmsNotification,String bpsEmailReminder, String reportCycling) {
			
			List<ResellerBasicSetupDto> rBasicSetDtoinfo = new ArrayList<>();
			
			
			List<Object[]> dbrBasicSetDtoinfo = resellerRepo.cBasicSetupByResellerId(ressellerId, returnUrl, isAutoRefund, hours, minutes, isPushUrl, 
					pushUrl, settlementCycle, resellerDashboardRefund, rsDisableRefundCc, rsDisableRefundDc, rsDisableRefundNb, rsDisableRefundUpi, 
					rsDisableRefundWallet, refundApi, refundApiDisableCc, refundApiDisableDc, refundApiDisableNb, refundApiDisableUpi, refundApiDisableWallet, 
					integrationType, isretryAllowed, bpsEmailNotification, bpsSmsNotification, bpsEmailReminder, reportCycling);
			
			for (Object[] obj : dbrBasicSetDtoinfo) {
				ResellerBasicSetupDto resBasDto = new ResellerBasicSetupDto();
				resBasDto.setRessellerId((String) obj[0]);
				resBasDto.setReturnUrl((String) obj[1]);
				resBasDto.setIsAutoRefund((String) obj[2]);			
				resBasDto.setHours((String) obj[3]);
				resBasDto.setMinutes((String) obj[4]);
				resBasDto.setIsPushUrl((String) obj[5]);
				resBasDto.setPushUrl((String) obj[6]);
				resBasDto.setSettlementCycle((String) obj[7]);
				resBasDto.setResellerDashboardRefund((String) obj[8]);
				resBasDto.setRsDisableRefundCc((String) obj[9]);
				resBasDto.setRsDisableRefundDc((String) obj[10]);
				resBasDto.setRsDisableRefundNb((String) obj[11]);
				resBasDto.setRsDisableRefundUpi((String) obj[12]);
				resBasDto.setRsDisableRefundWallet((String) obj[13]);
				resBasDto.setRefundApi((String) obj[14]);
				resBasDto.setRefundApiDisableCc((String) obj[15]);
				resBasDto.setRefundApiDisableDc((String) obj[16]);
				resBasDto.setRefundApiDisableNb((String) obj[17]);
				resBasDto.setRefundApiDisableUpi((String) obj[18]);
				resBasDto.setRefundApiDisableWallet((String) obj[19]);
				resBasDto.setIntegrationType((String) obj[20]);
				resBasDto.setIsretryAllowed((String) obj[21].toString());			
				resBasDto.setIbpsEmailNotification((String) obj[22]);
				resBasDto.setIbpsSmsNotification((String) obj[23]);
				resBasDto.setIbpsEmailReminder((String) obj[24]);
				resBasDto.setReportCycling((String) obj[25]);

				rBasicSetDtoinfo.add(resBasDto);
			}
			
			
			return rBasicSetDtoinfo;
		}
		
		public String Reseller_Status_change(String Status, String Remark, String Rid, String Type,String Date, String AddedBy) throws Exception {
			return CommonServiceDaoImpl.UpdateResellerStatus(Status,Remark, Rid,Type,Date,AddedBy);

		}
		
		public Map<String, Object> getFinanacialYear(String date) {
			Map<String, Object> Msg = null ;
			String message = null;
			String fYearPast = null;
			String fYear = null;
			JSONObject js1 = new JSONObject();
			JSONArray array = new JSONArray();
		    YearMonth startYearMonth = YearMonth.now().minusYears(1);
		    java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
		    java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();
		    YearMonth startYearMonthNow = YearMonth.now();
		    java.time.LocalDate startOfMonthDateNow = startYearMonthNow.atDay(1);    
		    java.time.LocalDate endOfMonthDateNow   = startYearMonthNow.atEndOfMonth();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    String startnow = startOfMonthDateNow.format(formatter);
		    String startPast = startOfMonthDate.format(formatter);			
				List<Object[]> financialYear = resellerRepo.getFinancialYear(startPast, startnow);
				if(!financialYear.isEmpty()) {
					for(Object[] obj:financialYear) {
						JSONObject item = new JSONObject();
						String mStr = new SimpleDateFormat("dd-MM-yyyy").format(obj[2]);
						String mEtr = new SimpleDateFormat("dd-MM-yyyy").format(obj[3]);					
						item.put("Financial_Year", obj[0]);
						item.put("Financial_MonthW", obj[1]);
						item.put("Financial_Start", mStr);
						item.put("Financial_end", mEtr);
						array.put(item);
					}			
						js1.put("Status", "Success");
						js1.put("data", array);
					}else {
						js1.put("Status", "Error");
					}
				Msg = js1.toMap();
			return Msg;
		}



		public Map<String, Object> getFinanacialMonth(String fYear) {
			Map<String, Object> Msg = null ;
			String message = null;
			JSONObject js1 = new JSONObject();
			JSONArray array = new JSONArray();
			YearMonth startYearMonth = YearMonth.now().minusYears(2);
		    java.time.LocalDate startOfMonthDate = startYearMonth.atDay(1);    
		    java.time.LocalDate endOfMonthDate   = startYearMonth.atEndOfMonth();	    
		    
		    YearMonth startYearMonthNow = YearMonth.now().minusMonths(1);
		    java.time.LocalDate startOfMonthDateNow = startYearMonthNow.atDay(1);    
		    java.time.LocalDate endOfMonthDateNow   = startYearMonthNow.atEndOfMonth();
		    int year = Calendar.getInstance().get(Calendar.YEAR);

		    int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		    String financiyalYearFrom="";
		    String financiyalYearTo="";
		    if (month < 4) {
		    	financiyalYearFrom= (year-2) + "-04-01";
		        financiyalYearTo=(year) + "-03-31";
		    } else {
		    	financiyalYearFrom= (year-1) + "-04-01";
		        financiyalYearTo=(year) + "-03-31";
		    }
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    String startnow = startOfMonthDateNow.format(formatter);
		    String startPast = startOfMonthDate.format(formatter);
			List<Object[]> financialMonthData = resellerRepo.getFinancialMonth(financiyalYearFrom, startnow);
			if(!financialMonthData.isEmpty()) {
				for(Object[] obj:financialMonthData) {
					JSONObject item = new JSONObject();	
					String mStr = new SimpleDateFormat("dd-MM-yyyy").format(obj[1]);
					String mEtr = new SimpleDateFormat("dd-MM-yyyy").format(obj[2]);
					item.put("Month_Name", obj[0]);	
					item.put("Month_Start", mStr);
					item.put("Month_End", mEtr);
					array.put(item);
				}
					js1.put("Status", "Success");
					js1.put("data", array);
				}else {
					js1.put("Status", "Error");
			}
			Msg = js1.toMap();
			
			return Msg;
		}



		public Map<String, Object> getFinanacialMonthDt(String fMonth) {
			Map<String, Object> Msg = null ;
			String message = null;
			JSONObject js1 = new JSONObject();
			JSONArray array = new JSONArray();
			List<Object[]> financialMonthData = resellerRepo.getFinancialMonthDated(fMonth);
			if(!financialMonthData.isEmpty()) {
				for(Object[] obj:financialMonthData) {
					JSONObject item = new JSONObject();
					String mStr = new SimpleDateFormat("dd-MM-yyyy").format(obj[0]);
					String mEtr = new SimpleDateFormat("dd-MM-yyyy").format(obj[1]);
					
					item.put("Month Start", mStr);
					item.put("Month End",mEtr);
					array.put(item);
				}
					js1.put("Status", "Success");
					js1.put("data", array);
				}else {
					js1.put("Status", "Error");
			}
			Msg = js1.toMap();
			
			return Msg;
		}
		
		public Map<String, Object> getAttachmentUploafFile(MultipartFile imageFile , String MerchantId, String AddedBy) {
			Map<String, Object> Msg = null ;
			String jsonArray = null;
			JSONObject js1 = new JSONObject();	
			StringBuilder filesname = new StringBuilder();
			String merchantId = MerchantId;
			String User = AddedBy;
			String fileOrgname = imageFile.getOriginalFilename();
			String extension = fileOrgname.substring(fileOrgname.lastIndexOf(".") + 1);
			boolean obj = extension.endsWith("JPG") || extension.endsWith("JPEG") || extension.endsWith("pdf") || extension.endsWith("jpg") || extension.endsWith("PDF") || extension.endsWith("bmp")
					|| extension.endsWith("BMP") || extension.endsWith("png") || extension.endsWith("PNG") || extension.endsWith("docx") || extension.endsWith("xlsx") || extension.endsWith("doc") || extension.endsWith("xls");
			if(obj == true ) {
				Date date = Calendar.getInstance().getTime();  
		        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		        String strDate = dateFormat.format(date);  
		        String strDatewospc = strDate.replaceAll("\\s", "");
		        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");				
				String filesname1 = uploadDirectory + "/" + merchantId +"/";
				fileOrgname= fileOrgname.replaceAll("\\s", "");
				String nameFileconvert = strDatewosp +"_"+fileOrgname;
				File file = new File(filesname1);
				if (!file.exists()) {            
		            file.mkdirs();            
		        }
				
				try {
					Path path = Path.of(filesname1,nameFileconvert);
					filesname.append(nameFileconvert);
					Files.write(path, imageFile.getBytes());
					String DocRef = filesname1 + nameFileconvert;
					logger.info("attachment upload Success  ::::::::::::::::::{} "+DocRef);
				
					merchantKycDocRepo.inserttablattachment(merchantId,fileOrgname, DocRef,User);
					logger.info("attachment upload Success data insert ::::::::::::::::::{} "+DocRef);
					List<Map<String, Object>> resuldata= merchantKycDocRepo.getalldatabymerchant(merchantId);
					js1.put("Data", resuldata);
					js1.put("Status", "Success");
					js1.put("Message", "File Uploaded SuccessFully");
					logger.info("attachment upload Get data  ::::::::::::::::::{} "+resuldata);
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("attachment upload Error  ::::::::::::::::::{} "+e.getMessage());
					js1.put("Status", "Error");
					js1.put("Message", "File Uploaded Error");
				}
			}else {
				js1.put("Status", "Error");
				js1.put("Message", "invalid File Format");				
			}
				Msg = js1.toMap();
				return Msg;
			
		}

		public Map<String, Object> getAttachmentUploafList(String merchantId) {
			Map<String, Object> Msg = null ;
			String jsonArray = null;
			JSONObject js1 = new JSONObject();	
			List<Map<String, Object>> resuldata= merchantKycDocRepo.getalldatabymerchant(merchantId);
			if(!resuldata.isEmpty()) {
				js1.put("Data", resuldata);
				js1.put("Status", "Success");
				
			}else {
				js1.put("Status", "Error");								
			}
				Msg = js1.toMap();
			
			return Msg;
		}
}
