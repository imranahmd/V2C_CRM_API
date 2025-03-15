package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_merchantbulkuploadcsv")
public class BulkCsvMerchant {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "Id")
	private Long Id;

	@Column(name = "merchant_name")
	private String merchant_name;
	
	@Column(name = "contact_person")
	private String contact_person;
	
	@Column(name = "contact_number")
	private String contact_number;
	
	@Column(name = "email_id")
	private String email_id;
	
	@Column(name = "companypan")
	private String CompanyPAN;
	
	@Column(name = "business_name")
	private String business_name;
	
	@Column(name = "business_type")
	private String BusinessType;
	
	@Column(name = "merchant_category_code")
	private String merchant_category_code;
	
	@Column(name = "merchant_sub_category")
	private String merchant_sub_category;
	
	@Column(name = "business_model")
	private String BusinessModel;
	
	@Column(name = "turnoverinlast_financial_year")
	private String TurnoverinlastFinancialYear;
	
	@Column(name = "expected_monthly_income")
	private String ExpectedMonthlyIncome;
	
	@Column(name = "average_amount_per_transaction")
	private String AverageAmountPerTransaction;
	
	@Column(name = "authorised_signatorypan")
	private String AuthorisedSignatoryPAN;
	
	@Column(name = "name_as_perpan")
	private String NameAsPerPAN;
	
	@Column(name = "gstinno")
	private String GSTINNo;
	
	@Column(name = "mer_website_url")
	private String mer_website_url;
	
	@Column(name = "reseller_id")
	private String reseller_id;
	
	@Column(name = "is_test_access")
	private String IsTestAccess;
	
	@Column(name = "mer_return_url")
	private String mer_return_url;
	
	@Column(name = "is_auto_refund")
	private String is_auto_refund;
	
	@Column(name = "hours")
	private String hours;
	
	@Column(name = "minutes")
	private String minutes;
	
	@Column(name = "is_push_url")
	private String is_push_url;
	
	@Column(name = "push_url")
	private String push_url;
	
	@Column(name = "settlement_cycle")
	private String settlement_cycle;
	
	@Column(name = "integration_type")
	private String integration_type;
	
	@Column(name = "isretry_allowed")
	private String isretryAllowed;
	
	@Column(name = "ibps_email_notification")
	private String ibps_email_notification;
	
	@Column(name = "ibps_sms_notification")
	private String ibps_sms_notification;
	
	@Column(name = "ibps_mail_remainder")
	private String ibps_mail_remainder;
	
	@Column(name = "reporting_cycle")
	private String Reporting_cycle;
	
	@Column(name = "merchant_dashboard_refund")
	private String merchant_dashboard_refund;
	
	@Column(name = "md_disable_refund_cc")
	private String md_disable_refund_cc;
	
	@Column(name = "md_disable_refund_dc")
	private String md_disable_refund_dc;
	
	@Column(name = "md_disable_refund_nb")
	private String md_disable_refund_nb;
	
	@Column(name = "md_disable_refund_upi")
	private String md_disable_refund_upi;
	
	@Column(name = "md_disable_refund_wallet")
	private String md_disable_refund_wallet;
	
	@Column(name = "refund_api")
	private String refund_api;
	
	@Column(name = "refund_api_disable_cc")
	private String refund_api_disable_cc;
	
	@Column(name = "refund_api_disable_dc")
	private String refund_api_disable_dc;
	
	@Column(name = "refund_api_disable_nb")
	private String refund_api_disable_nb;
	
	@Column(name = "refund_api_disable_upi")
	private String refund_api_disable_upi;
	
	@Column(name = "refund_api_disable_wallet")
	private String refund_api_disable_wallet;
	
	@Column(name = "upload_status")
	private String upload_status;

	@Column(name = "sr_no")
	private String sr_no;
	
	@Column(name = "remarks")
	private String remarks;

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public String getContact_person() {
		return contact_person;
	}

	public void setContact_person(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getCompanyPAN() {
		return CompanyPAN;
	}

	public void setCompanyPAN(String companyPAN) {
		CompanyPAN = companyPAN;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public String getBusinessType() {
		return BusinessType;
	}

	public void setBusinessType(String businessType) {
		BusinessType = businessType;
	}

	public String getMerchant_category_code() {
		return merchant_category_code;
	}

	public void setMerchant_category_code(String merchant_category_code) {
		this.merchant_category_code = merchant_category_code;
	}

	public String getMerchant_sub_category() {
		return merchant_sub_category;
	}

	public void setMerchant_sub_category(String merchant_sub_category) {
		this.merchant_sub_category = merchant_sub_category;
	}

	public String getBusinessModel() {
		return BusinessModel;
	}

	public void setBusinessModel(String businessModel) {
		BusinessModel = businessModel;
	}

	public String getTurnoverinlastFinancialYear() {
		return TurnoverinlastFinancialYear;
	}

	public void setTurnoverinlastFinancialYear(String turnoverinlastFinancialYear) {
		TurnoverinlastFinancialYear = turnoverinlastFinancialYear;
	}

	public String getExpectedMonthlyIncome() {
		return ExpectedMonthlyIncome;
	}

	public void setExpectedMonthlyIncome(String expectedMonthlyIncome) {
		ExpectedMonthlyIncome = expectedMonthlyIncome;
	}

	public String getAverageAmountPerTransaction() {
		return AverageAmountPerTransaction;
	}

	public void setAverageAmountPerTransaction(String averageAmountPerTransaction) {
		AverageAmountPerTransaction = averageAmountPerTransaction;
	}

	public String getAuthorisedSignatoryPAN() {
		return AuthorisedSignatoryPAN;
	}

	public void setAuthorisedSignatoryPAN(String authorisedSignatoryPAN) {
		AuthorisedSignatoryPAN = authorisedSignatoryPAN;
	}

	public String getNameAsPerPAN() {
		return NameAsPerPAN;
	}

	public void setNameAsPerPAN(String nameAsPerPAN) {
		NameAsPerPAN = nameAsPerPAN;
	}

	public String getGSTINNo() {
		return GSTINNo;
	}

	public void setGSTINNo(String gSTINNo) {
		GSTINNo = gSTINNo;
	}

	public String getMer_website_url() {
		return mer_website_url;
	}

	public void setMer_website_url(String mer_website_url) {
		this.mer_website_url = mer_website_url;
	}

	public String getReseller_id() {
		return reseller_id;
	}

	public void setReseller_id(String reseller_id) {
		this.reseller_id = reseller_id;
	}

	public String getIsTestAccess() {
		return IsTestAccess;
	}

	public void setIsTestAccess(String isTestAccess) {
		IsTestAccess = isTestAccess;
	}

	public String getMer_return_url() {
		return mer_return_url;
	}

	public void setMer_return_url(String mer_return_url) {
		this.mer_return_url = mer_return_url;
	}

	public String getIs_auto_refund() {
		return is_auto_refund;
	}

	public void setIs_auto_refund(String is_auto_refund) {
		this.is_auto_refund = is_auto_refund;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}

	public String getIs_push_url() {
		return is_push_url;
	}

	public void setIs_push_url(String is_push_url) {
		this.is_push_url = is_push_url;
	}

	public String getPush_url() {
		return push_url;
	}

	public void setPush_url(String push_url) {
		this.push_url = push_url;
	}

	public String getSettlement_cycle() {
		return settlement_cycle;
	}

	public void setSettlement_cycle(String settlement_cycle) {
		this.settlement_cycle = settlement_cycle;
	}

	public String getIntegration_type() {
		return integration_type;
	}

	public void setIntegration_type(String integration_type) {
		this.integration_type = integration_type;
	}

	public String getIsretryAllowed() {
		return isretryAllowed;
	}

	public void setIsretryAllowed(String isretryAllowed) {
		this.isretryAllowed = isretryAllowed;
	}

	public String getIbps_email_notification() {
		return ibps_email_notification;
	}

	public void setIbps_email_notification(String ibps_email_notification) {
		this.ibps_email_notification = ibps_email_notification;
	}

	public String getIbps_sms_notification() {
		return ibps_sms_notification;
	}

	public void setIbps_sms_notification(String ibps_sms_notification) {
		this.ibps_sms_notification = ibps_sms_notification;
	}

	public String getIbps_mail_remainder() {
		return ibps_mail_remainder;
	}

	public void setIbps_mail_remainder(String ibps_mail_remainder) {
		this.ibps_mail_remainder = ibps_mail_remainder;
	}

	public String getReporting_cycle() {
		return Reporting_cycle;
	}

	public void setReporting_cycle(String reporting_cycle) {
		Reporting_cycle = reporting_cycle;
	}

	public String getMerchant_dashboard_refund() {
		return merchant_dashboard_refund;
	}

	public void setMerchant_dashboard_refund(String merchant_dashboard_refund) {
		this.merchant_dashboard_refund = merchant_dashboard_refund;
	}

	public String getMd_disable_refund_cc() {
		return md_disable_refund_cc;
	}

	public void setMd_disable_refund_cc(String md_disable_refund_cc) {
		this.md_disable_refund_cc = md_disable_refund_cc;
	}

	public String getMd_disable_refund_dc() {
		return md_disable_refund_dc;
	}

	public void setMd_disable_refund_dc(String md_disable_refund_dc) {
		this.md_disable_refund_dc = md_disable_refund_dc;
	}

	public String getMd_disable_refund_nb() {
		return md_disable_refund_nb;
	}

	public void setMd_disable_refund_nb(String md_disable_refund_nb) {
		this.md_disable_refund_nb = md_disable_refund_nb;
	}

	public String getMd_disable_refund_upi() {
		return md_disable_refund_upi;
	}

	public void setMd_disable_refund_upi(String md_disable_refund_upi) {
		this.md_disable_refund_upi = md_disable_refund_upi;
	}

	public String getMd_disable_refund_wallet() {
		return md_disable_refund_wallet;
	}

	public void setMd_disable_refund_wallet(String md_disable_refund_wallet) {
		this.md_disable_refund_wallet = md_disable_refund_wallet;
	}

	public String getRefund_api() {
		return refund_api;
	}

	public void setRefund_api(String refund_api) {
		this.refund_api = refund_api;
	}

	public String getRefund_api_disable_cc() {
		return refund_api_disable_cc;
	}

	public void setRefund_api_disable_cc(String refund_api_disable_cc) {
		this.refund_api_disable_cc = refund_api_disable_cc;
	}

	public String getRefund_api_disable_dc() {
		return refund_api_disable_dc;
	}

	public void setRefund_api_disable_dc(String refund_api_disable_dc) {
		this.refund_api_disable_dc = refund_api_disable_dc;
	}

	public String getRefund_api_disable_nb() {
		return refund_api_disable_nb;
	}

	public void setRefund_api_disable_nb(String refund_api_disable_nb) {
		this.refund_api_disable_nb = refund_api_disable_nb;
	}

	public String getRefund_api_disable_upi() {
		return refund_api_disable_upi;
	}

	public void setRefund_api_disable_upi(String refund_api_disable_upi) {
		this.refund_api_disable_upi = refund_api_disable_upi;
	}

	public String getRefund_api_disable_wallet() {
		return refund_api_disable_wallet;
	}

	public void setRefund_api_disable_wallet(String refund_api_disable_wallet) {
		this.refund_api_disable_wallet = refund_api_disable_wallet;
	}

	

	public String getUpload_status() {
		return upload_status;
	}

	public void setUpload_status(String upload_status) {
		this.upload_status = upload_status;
	}

	public String getSr_no() {
		return sr_no;
	}

	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	
}
