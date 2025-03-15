package com.crm.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.dto.MerchantBasicSetupDto;
import com.crm.model.MerchantList;
import com.crm.model.RefundTransaction;

public interface MerchantRepository extends JpaRepository<MerchantList, Long> {
//	@Query(value = "call pro_MerchantList(:fromDate, :toDate, :merchantId, :merchantName);", nativeQuery = true)
	@Query(value = "call pro_MerchantListV2(:fromDate, :toDate, :merchantId, :resellerId, :merchantName, :norecord, :pageno, :sp);", nativeQuery = true)
	List<Object[]> findByMerchantsByDateNameId(String fromDate, String toDate, String merchantId, String resellerId, String merchantName , int norecord, int pageno,String sp);


	@Query(value = "call pro_GetMerchantId();", nativeQuery = true)
	String findMerchantId();

	@Query(value = "select ROLEID from tbl_mstuser where USERID= :userId", nativeQuery = true)
	String Getrole(String userId);

	@Query(value = "SELECT CASE WHEN EXISTS ( SELECT 1 FROM tbl_mstrolemenupermission T1 INNER JOIN tbl_mstmenu T2 ON T2.MenuId = T1.MenuId WHERE T1.roleId = :roleId AND T2.MenuLink = :menulink ) THEN 1 ELSE 0 END AS Result; ", nativeQuery = true)
	String getResult(String roleId, String menulink);
//MIthi
	@Transactional
	@Query(value = "call pro_MerchantCreation(:contactPerson, :contactNumber, :emailId, :companypan, :merchantname, :businessname, :businesstype, :DateofIncroporation, :merchantcatagorycode, :merchantsubcatagory, :businessmodal,"
			+ " :turnoverfyear, :monthlyincome, :avgamtpertransaction, :authorisedpan, :nameaspan, :registeraddress, :pincode, :city, :State, :gstno, :website, :testaccess, :instruments, :merchantid,:transactionkey, :isPanVerified, "
			+ ":iResellerId, :iIsCompanyPanVerify, :iIsGSTVerify, :iCompanyPanVerifyName, :iGSTVerifyName, :name_as_perpan,:Source ,:MerReturn_url ,:additional_contact ,:sales_lead,:logoPath,:partners_type,:bank_id);", nativeQuery = true)
	List<Object[]> createMerchantByNameEmail(String contactPerson, String contactNumber, String emailId,
			String companypan, String merchantname, String businessname, int businesstype, String DateofIncroporation,
			String merchantcatagorycode, String merchantsubcatagory, String businessmodal, String turnoverfyear,
			String monthlyincome, String avgamtpertransaction, String authorisedpan, String nameaspan,
			String registeraddress, String pincode, String city, String State, String gstno, String website,
			String testaccess, String instruments, String merchantid, String transactionkey, Character isPanVerified,
			String iResellerId,Character iIsCompanyPanVerify,Character iIsGSTVerify,String iCompanyPanVerifyName,
			String iGSTVerifyName, String name_as_perpan ,String Source,String MerReturn_url, String additional_contact, 
			String sales_lead, String logoPath, String partners_type, String bank_id);// 28 values + add 1 more parametere for the POI
	
	@Transactional
	@Query(value = "call pro_MerchantBasicSetup(:MerchantId, :isAutoRefund, :hours, :minutes, :isPushUrl, :pushUrl, :settlementCycle,"
			+ "	:merchantDashboardRefund, :mdDisableRefundCc, :mdDisableRefundDc, :mdDisableRefundNb, :mdDisableRefundUpi,"
			+ "	:mdDisableRefundWallet, :refundApi, :refundApiDisableCc,:refundApiDisableDc,:refundApiDisableNb, :refundApiDisableUpi, :refundApiDisableWallet,"
			+ " :integrationType, :isretryAllowed, :bpsEmailNotification, :bpsSmsNotification, :bpsMailReminder, :reportingcycle, :upi_loader, :upi_intent, :upi_collect, :static_QR, :dynamic_QR,:partnerBy);", nativeQuery = true)

		List<Object[]> cBasicSetupByMerchantId(@Param("MerchantId")String MerchantId,@Param("isAutoRefund") String isAutoRefund,@Param("hours")String hours,@Param("minutes")String minutes,@Param("isPushUrl")String isPushUrl,
				@Param("pushUrl")String pushUrl, @Param("settlementCycle")String settlementCycle, @Param("merchantDashboardRefund")String merchantDashboardRefund,@Param("mdDisableRefundCc")String mdDisableRefundCc, @Param("mdDisableRefundDc")String mdDisableRefundDc, @Param("mdDisableRefundNb")String mdDisableRefundNb, @Param("mdDisableRefundUpi")String mdDisableRefundUpi,
				@Param("mdDisableRefundWallet")String mdDisableRefundWallet, @Param("refundApi")String refundApi, @Param("refundApiDisableCc")String refundApiDisableCc, @Param("refundApiDisableDc")String refundApiDisableDc, @Param("refundApiDisableNb")String refundApiDisableNb, @Param("refundApiDisableUpi")String refundApiDisableUpi, @Param("refundApiDisableWallet")String refundApiDisableWallet,
				@Param("integrationType")String integrationType, @Param("isretryAllowed")String isretryAllowed, @Param("bpsEmailNotification")String bpsEmailNotification, @Param("bpsSmsNotification")String bpsSmsNotification, @Param("bpsMailReminder")String bpsMailReminder, @Param("reportingcycle")String reportingcycle, @Param("upi_loader")String upi_loader, @Param("upi_intent")String upi_intent, @Param("upi_collect")String upi_collect,@Param("static_QR")String static_QR,@Param("dynamic_QR")String dynamic_QR,@Param("partnerBy")String partnerBy);//24 value
	
		
		@Query(value = "call pro_MerchantBasicSetupDetails(:MerchantId);", nativeQuery = true)

			List<Object[]> findBasicSetupByMerchantId(String MerchantId );
		
			@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_mstmerchant where contact_number=? ;", nativeQuery = true)
			boolean findByContactNumber(String contact_number);
		
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_mstmerchant where email_id=? ;", nativeQuery = true)
			boolean findByEmailId(String email_id);
		
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_mstmerchant where companypan=? ;", nativeQuery = true)
			boolean findByCompanyPAN(String companyPAN);
		
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_merchantbulkuploadcsv set remarks = :isverifiy, upload_status = :uploadStatus where Id= :recId", nativeQuery = true)
		void updateVerification(@Param ("isverifiy") String isverifiy , @Param ("uploadStatus") String uploadStatus , @Param ("recId") Long recId  );
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_merchantbulkuploadcsv set remarks = :isverifiy", nativeQuery = true)
		void updateAllVerification(@Param ("isverifiy") String isverifiy);
		
		
		@Query(value = "select remarks from tbl_merchantbulkuploadcsv where Id= :recId", nativeQuery = true)
		String findVerification(Long recId  );
		
		@Query(value = "call pro_InstrumentGetList(:Mid,:Id);", nativeQuery = true)
		String InstrimentList(String Mid,String Id);
		
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_mdrbulkuploadcsv set remarks = :isverifiy", nativeQuery = true)
		void updateMdrVerification(@Param ("isverifiy") String isverifiy);
		
		@Query(value = "select * from tbl_merchantbulkuploadcsv", nativeQuery = true)
		List<Object[]> listAllRecords ();
		
		@Query(value = "select * from tbl_mstmerchant where MerchantId= :MerchantId", nativeQuery = true)
		List<Object[]> findByMerchant(String  MerchantId  );
		
		@Query(value = "select refund_processor from tbl_mstserviceprovider where sp_id = :processId", nativeQuery = true)
		String findProcessor(String processId);

		@Query(value = "SELECT sum(RefundAmt) FROM tbl_transactionrefund where TransId=:transId AND Merchant_Id = :merchant_Id group by TransId, Merchant_Id;", nativeQuery = true)
		RefundTransaction findBycustomId(String transId, String merchant_Id);

		@Query(value = "select Id,txn_Id,merchant_id,date_time,txn_amount,service_rrn,instrument_id, bank_id, process_id from tbl_transactionmaster where Id = :transId and merchant_id = :merchant_Id" , nativeQuery = true)
		RefundTransaction findBytxnIdandMerchantId(String transId, String merchant_Id);
		
		//public Optional<MerchantList> findByMerchantIdAndStatus(String merchantId,String status);
		
		public Optional<MerchantList> getMerchantListByMerchantIdAndStatus(String merchantId,String status);
		
		
		MerchantList findByMerchantId(String merchantId);

		
		@Query(value = "select mcc_code from merchant_master_data where mid = :Mid", nativeQuery = true)
		String findMCCForYesank(String Mid);

		
		@Query(value = "select codes from tbl_mststatecodeval where states = :stateVal", nativeQuery = true)
		String findstateValD(String stateVal);
		
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS emailvalid FROM tbl_mstuser where EmailId = :validuserId", nativeQuery = true)
		boolean findByEmail(String validuserId);
		
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS USERID FROM tbl_mstuser where USERID = :validuserId", nativeQuery = true)
		boolean findByuserId(String validuserId);
		
		@Query(value = "SELECT * FROM tbl_forgetpass_trail where uu_id = :tokenValidation", nativeQuery = true)
		List<Object[]> findByuuId(String tokenValidation);
		
		@Query(value = "SELECT USERID FROM tbl_mstuser where EmailId = :validuserId", nativeQuery = true)
		String findUserIdByEmail(String validuserId);
		
		@Query(value = "SELECT password FROM tbl_mstuser where USERID = :userId", nativeQuery = true)
		String findUserIdByPass(String userId);
		
		@Query(value = "SELECT USERID, FullName, EmailId  FROM tbl_mstuser where USERID= :validuserId and Is_Deleted='N'", nativeQuery = true)
		List<Object[]> findUserIdById(String validuserId);
		
		@Query(value = "select business_name from tbl_mstmerchant where MerchantId= :validuserId", nativeQuery = true)
		String findUserIdByMId(String validuserId);
		
		@Query(value = "select brand_name from tbl_reseller_personal_details where reseller_id= :validuserId", nativeQuery = true)
		String findUserIdByRId(String validuserId);
		
		@Query(value = "select Codes from tbl_mstbusinesstypeval where businessType = :businessType", nativeQuery = true)
		String findbusinessTypeVal(String businessType);
		
		
		@Query(value = "call pro_SubmerchantInsertData(:MerchantId, :sp_id, :instrument, :App_Status, :requestjson, :responsejson);", nativeQuery = true)
		String saveDataYBSubMerchant(String MerchantId,String sp_id, String instrument,String App_Status,String requestjson, String responsejson);

		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_mstmerchant set email_cred_triggered = :emailTrigger where MerchantId= :mid", nativeQuery = true)
		void updateEmailTriggerFlag(@Param ("emailTrigger") String emailTrigger, @Param ("mid") String mid );
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_mstuser set password = :passwordChange where USERID= :UserId", nativeQuery = true)
		void updateResetPass(@Param ("UserId") String UserId, @Param ("passwordChange") String passwordChange );
		
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_forgetpass_trail set verified = :verfication where uu_id= :tokenValidation", nativeQuery = true)
		void updateverified(@Param ("tokenValidation") String tokenValidation, @Param ("verfication") String verfication );
		
		@Transactional
		@Modifying
		@Query(value = "UPDATE tbl_selfonboard set verified = :verfication where otp_num_Mobile= :otp", nativeQuery = true)
		void updateOTPverified(@Param ("otp") String otp, @Param ("verfication") String verfication );

		@Transactional
		@Modifying
		@Query(value = """
				INSERT INTO tbl_mstmerchant
				(MerchantId,
				contact_person,
				merchant_name,
				email_id,
				contact_number,
				status,
				created_on,
				max_token_size
				) values (:merchantId, :fullName, :fullName, :emailId, :contactNo, :status, :strDate, :token)""", nativeQuery = true)
		void createMerchantSelf(String merchantId, String fullName, String emailId, String contactNo, String status, Date strDate, String token);
		
		
		@Query(value = "SELECT Id FROM pa_uat.tbl_transactionmaster where txn_Id=:transId and merchant_id=:merchant_Id ;", nativeQuery = true)
		String fintransction(String transId, String merchant_Id);

		@Query(value = "SELECT sp_name FROM tbl_mstserviceprovider where sp_id=:spid ;", nativeQuery = true)
		String findspName(int spid);

		@Query(value = "Select bankName from tbl_mstpgbank where bankId=:bankId ;", nativeQuery = true)
		String findBankName(String bankId);
		
		@Query(value = "SELECT merchant_id FROM tbl_transactionmaster where txn_id= :txnId", nativeQuery = true)
		String findtransctionById(String txnId);


		@Transactional
		@Modifying
		@Query(value = "INSERT INTO bulk_refund_batch_details (batch_id, merchant_id, transaction_count, refund_amount, status, is_deleted, file_path) values (:batch_id, :merchant_id, :transaction_count, :refund_amount, :status, :is_deleted, :file_path)", nativeQuery = true)
		void updateTableRecords(String batch_id, String merchant_id, Integer transaction_count, Double refund_amount, String status, Integer is_deleted, String file_path);

		@Query(value = "SELECT recon_status FROM tbl_transactionmaster where txn_id= :txnId", nativeQuery = true)
		String findByProperty(String txnId);

		@Query(value = "Select USERID,FullName,cast(T1.ROLEID as char)as ROLEID,ROLENAME,password,EmailId,ContactNo from tbl_mstuser T1 inner join tbl_mstrole T2 On  T1.ROLEID = T2.ROLEID where T1.Is_Deleted='N' order by USERID desc;", nativeQuery = true)
		List<Object[]> listAllRecordsUser();
		
		@Query(value = "CALL pro_insertUser(:userId,:fullName,:emailId,:contactNo,:roleId,:password1);",nativeQuery = true)
		int insertUser(String userId, String fullName, String emailId, String contactNo, String roleId,
				String password1);
		
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_rechargeDetails where utr_no=? and txn_id!=? and isApproved in ('N') ;", nativeQuery = true)
		boolean findUtrNumber(String Utr,String txnId);
		
		//SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_rechargeDetails where utr_no='1234' and isApproved in ('R','A') ;
		@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_rechargeDetails where utr_no=? ;", nativeQuery = true)
		boolean findSameUtrNumber(String contact_number);
		
		@Query(value = "call pro_GetRefundApplyListFiltersV2(:merchantId, :fromDate, :toDate, :id, :bankId, :custMobile, :custMail,"
				+ "	 :txnId, :spId);", nativeQuery = true)
		
		List<Object[]> refundAmtList(String merchantId, String fromDate, String toDate, String id, String bankId,
				String custMobile, String custMail, String txnId, String spId);
		
		@Query(value = "select merchant_id,txn_Id from tbl_transactionmaster where Id= :txnId", nativeQuery = true)
		List<Object[]> getTxnDetailsById(long txnId);
		
		@Query(value = "SELECT Id FROM tbl_transactionmaster where txn_Id=:transId ;", nativeQuery = true)
		String fintransction(String transId);
		
		 @Query(value = "SELECT merchant_id FROM tbl_transactionmaster where txn_Id=:transId ;", nativeQuery = true)
			String fintransctionMid(String transId);

		 @Query(value = "Select transaction_key from tbl_mstmerchant where MerchantId='M00005353';", nativeQuery = true)
		 	String getIdpayout();

		@Transactional
		@Modifying
		@Query(value = "update tbl_tspsettlementsummry set PayoutRaised = '1',PayoutId = :Id where MerchantId='M00005353' and ServiceProviderId='76' and SettClaimId= :SettClaimId ", nativeQuery = true)
		int updateIdpayout(String Id,String SettClaimId);
		
			
		
}
