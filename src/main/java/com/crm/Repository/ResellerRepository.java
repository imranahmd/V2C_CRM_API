package com.crm.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.crm.dto.ResellerDto;
import com.crm.model.ResellerEntity;

import jakarta.transaction.Transactional;


@EnableJpaRepositories
public interface ResellerRepository extends JpaRepository<ResellerEntity, BigInteger> {
	
	@Query(value = "SELECT * FROM tbl_reseller_kyc_upload_doc where id= ?;", nativeQuery = true)
	List<Object[]> findById(int Id);
	
	
	@Query(value = "SELECT * FROM tbl_reseller_kyc_upload_doc where reseller_id= ?;", nativeQuery = true)

	List<Object[]> getAllListResellerKyc(String MerchantId );
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tbl_reseller_kyc_upload_doc where id= ?;", nativeQuery = true)

	void deleteById(int id );
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tbl_reseller_personal_details set email_cred_triggered=:emailTrigger where reseller_id=:resellerId", nativeQuery = true)	
	void updemailTrigger(String resellerId, String emailTrigger);

	@Query(value = "call pro_GetResellerId();", nativeQuery = true)
	String getGeneratedResellerId();


	@Transactional
	@Query(value = "call pro_ResellerCreateUpdate(:resellerId, :resellertName, :contactPerson, :emailId, :contactNumber, :legalName, "
			+ ":brandName, :businessType, :dateOfIncorporation, :businessCategory, :subCategory,"
			+ " :businessModel, :turnoverLastFinancialYear, :turnoverMonthlyExpeced, :avgAmountPerTxn, :registeredAddress, "
			+ ":pincode, :city, :state, :remark, :invoiceCycle, :integrationType, :isDeleted, :createdBy, :operationType, :sales_lead);", nativeQuery = true)
	List<Object[]> createUpdateReseller(String resellerId, String resellertName, String contactPerson,
			String emailId, String contactNumber, String legalName, String brandName, String businessType,
			String dateOfIncorporation, String businessCategory, String subCategory, 
			String businessModel,
			String turnoverLastFinancialYear, String turnoverMonthlyExpeced, String avgAmountPerTxn, String registeredAddress,
			String pincode, String city, String state, String remark, String invoiceCycle,
			String integrationType, String isDeleted, String createdBy, String operationType, String sales_lead);// 25 values

	
	@Query(value = "SELECT * FROM tbl_reseller_personal_details where is_deleted= ?;", nativeQuery = true)
	List<Object[]> getAllList(String isDeleted );

	@Query(value = "SELECT * from  tbl_reseller_personal_details where reseller_id= :resellerId;", nativeQuery = true)
	ResellerDto findByRId(@Param("resellerId") String resellerId);

	//ResellerDto findByRId(String resellerId);
	
	Optional<ResellerEntity> findByResellerId(String resellerId);

	@Query(value = "call pro_ResellerInsertDocument(:merchantId, :docType);", nativeQuery = true)
	List<Object[]> createUpload(String merchantId, String docType);

	@Query(value = "call pro_GetResellerDocumentList(:businesstypeId, :resellerId);", nativeQuery = true)

	List<Object[]> findByBussineTypeId(int businesstypeId, String resellerId );
	
	@Query(value = "call pro_ResellerList(:fromDate, :toDate, :merchantId, :merchantName, :norecord, :pageno);", nativeQuery = true)
	List<Object[]> findByResellerByDateNameId(String fromDate, String toDate, String merchantId, String merchantName , int norecord, int pageno);
	
	@Query(value = "call pro_ResellerBasicSetup(:ressellerId, :returnUrl, :isAutoRefund, :hours, :minutes, :isPushUrl, :pushUrl, :settlementCycle,"
			+ "	:resellerDashboardRefund, :rsDisableRefundCc, :rsDisableRefundDc, :rsDisableRefundNb, :rsDisableRefundUpi,"
			+ "	:rsDisableRefundWallet, :refundApi, :refundApiDisableCc,:refundApiDisableDc,:refundApiDisableNb, :refundApiDisableUpi, :refundApiDisableWallet,"
			+ " :integrationType, :isretryAllowed, :bpsEmailNotification, :bpsSmsNotification, :bpsEmailReminder, :reportCycling);", nativeQuery = true)

		List<Object[]> cBasicSetupByResellerId(String ressellerId, String returnUrl, String isAutoRefund, String hours, String minutes, String isPushUrl,
				String pushUrl, String settlementCycle, String resellerDashboardRefund, String rsDisableRefundCc, String rsDisableRefundDc, String rsDisableRefundNb, String rsDisableRefundUpi,
				String rsDisableRefundWallet, String refundApi, String refundApiDisableCc, String refundApiDisableDc, String refundApiDisableNb, String refundApiDisableUpi, String refundApiDisableWallet,
				String integrationType, String isretryAllowed, String bpsEmailNotification, String bpsSmsNotification,String bpsEmailReminder, String reportCycling );

		
		@Transactional
		@Modifying
		@Query(value = "INSERT INTO tbl_forgetpass_trail\r\n"
				+ "(userid,\r\n"
				+ "user_name,\r\n"
				
				+ "expired_on,\r\n"
				+ "uu_id\r\n"
				+ ") values (:userId, :fullName, :strDate1, :uu_id)", nativeQuery = true)
		void insertTrail(String userId, String fullName,  String strDate1, String uu_id);

		@Transactional
		@Modifying
		@Query(value = "INSERT INTO tbl_selfonboard\r\n"
				+ "(fullname,\r\n"
				+ "email_Id,\r\n"
				
				+ "contact_no,\r\n"
				+ "otp_num_Mobile,\r\n"
				+ "otp_num_email\r\n"
				+ ") values (:fullName, :emailId, :mobileNo, :sOtp, :eOtp)", nativeQuery = true)
		void insertSetailsSelfonBoard(String fullName, String emailId, String mobileNo, String sOtp, String eOtp);
		
		@Query(value="SELECT * FROM tbl_selfonboard where otp_num_Mobile=:getMobileOTP and otp_num_email=:getEmailOTP", nativeQuery = true)
		List<Object[]> findbyOtp(String getMobileOTP, String getEmailOTP);
		
		@Query(value="SELECT if(COUNT(*)>0,'true','false') AS MOtpvalid FROM tbl_selfonboard where otp_num_Mobile=:getMobileOTP ", nativeQuery = true)
		boolean findbyMOtp(String getMobileOTP);
		
		@Query(value="SELECT if(COUNT(*)>0,'true','false') AS emailOtpvalid FROM tbl_selfonboard where otp_num_email=:getEmailOTP", nativeQuery = true)
		boolean findbyEOtp(String getEmailOTP);
		
		@Query(value="SELECT if(COUNT(*)>0,'true','false') AS emailvalid FROM tbl_selfonboard where email_Id=:emailId and verified='1'", nativeQuery = true)
		boolean findUserByEmail(String emailId);
		
		@Query(value="SELECT fname, fmonth_period, fstart_period, fend_period FROM tbl_financial_month inner join tbl_financial_year on tbl_financial_month.fid=tbl_financial_year.Fid where fmonth_start between :startPast and :startnow group by fname", nativeQuery = true)
		List<Object[]> getFinancialYear(String startPast, String startnow);

		@Query(value="SELECT fmonth_name, fmonth_start,fmonth_end FROM tbl_financial_month inner join tbl_financial_year on tbl_financial_month.fid=tbl_financial_year.Fid where fmonth_start between :startPast and :startnow ", nativeQuery = true)
		List<Object[]> getFinancialMonth(String startPast, String startnow);		

		@Query(value="select fmonth_start,fmonth_end from tbl_financial_month where fmonth_name=:fMonth", nativeQuery = true)
		List<Object[]> getFinancialMonthDated(String fMonth);
		
}
