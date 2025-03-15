package com.crm.model;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_reseller_personal_details")
public class ResellerEntity {
	
	@Id
	@Column(name = "id")
	private BigInteger id;
	
	@Column(name="reseller_id")
	private String resellerId;
	
	@Column(name = "reseller_name")
	private String resellerName;
	
	@Column(name = "contact_person")
	private String contactPerson;
	
	@Column(name = "email_id")
	private String emailId;
	
	@Column(name = "contact_number")
	private String contactNumber;
	
	@Column(name = "legal_name")
	private String legalName;
	
	@Column (name = "brand_name")
	private String brandName;
	
	@Column(name = "business_type")
	private String businessType;
	
	@Column(name="date_of_incorporation")
	private LocalDateTime dateOfIncorporation;
	
	@Column(name ="business_category")
	private String businessCategory;
	
	@Column(name="sub_category")
	private String subCategory;
	
	@Column(name="business_model")
	private String businessModel;
	
	@Column(name="turnover_last_financial_year")
	private String turnoverLastFinancialYear;
	
	@Column(name="turnover_monthly_expeced")
	private String turnoverMonthlyExpeced;
	
	@Column(name="avg_amount_per_txn")
	private String avgAmountPerTxn;
	
	
	@Column(name="registered_address")
	private String registeredAddress;
	
	@Column(name="pin_code")
	private String pinCode;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="invoice_cycle")
	private String invoiceCycle;
	
	@Column(name="integration_type")
	private String integrationType;
	
	@Column(name="return_url")
	private String returnUrl;
	
	@Column(name="is_deleted")
	private String isDeleted;
	
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_on")
	private Timestamp createdOn;
	
	@Column(name="approved_by")
	private String approvedBy;
	
	@Column(name="approved_on")
	private Timestamp approvedOn;
	
	@Column(name="rodt")
	private Timestamp rodt;

	@Column(name="status")
	private String status;
	
	@Column(name="updated_on")
	private String updated_on;
	
	@Column(name="risk_approvel")
	private String risk_approvel;
	
	@Column(name="kyc_approvel")
	private String kyc_approvel;
	
	@Column (name="oprations_approvel")
	private String oprations_approvel;
	
	@Column (name="sales_lead")
	private String sales_lead;
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getResellerId() {
		return resellerId;
	}

	public void setResellerId(String resellerId) {
		this.resellerId = resellerId;
	}

	public String getResellerName() {
		return resellerName;
	}

	public void setResellerName(String resellerName) {
		this.resellerName = resellerName;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public LocalDateTime getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(LocalDateTime dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(String businessModel) {
		this.businessModel = businessModel;
	}

	public String getTurnoverLastFinancialYear() {
		return turnoverLastFinancialYear;
	}

	public void setTurnoverLastFinancialYear(String turnoverLastFinancialYear) {
		this.turnoverLastFinancialYear = turnoverLastFinancialYear;
	}

	public String getTurnoverMonthlyExpeced() {
		return turnoverMonthlyExpeced;
	}

	public void setTurnoverMonthlyExpeced(String turnoverMonthlyExpeced) {
		this.turnoverMonthlyExpeced = turnoverMonthlyExpeced;
	}

	public String getAvgAmountPerTxn() {
		return avgAmountPerTxn;
	}

	public void setAvgAmountPerTxn(String avgAmountPerTxn) {
		this.avgAmountPerTxn = avgAmountPerTxn;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInvoiceCycle() {
		return invoiceCycle;
	}

	public void setInvoiceCycle(String invoiceCycle) {
		this.invoiceCycle = invoiceCycle;
	}

	public String getIntegrationType() {
		return integrationType;
	}

	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getApprovedOn() {
		return approvedOn;
	}

	public void setApprovedOn(Timestamp approvedOn) {
		this.approvedOn = approvedOn;
	}

	public Timestamp getRodt() {
		return rodt;
	}

	public void setRodt(Timestamp rodt) {
		this.rodt = rodt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}

	public String getRisk_approvel() {
		return risk_approvel;
	}

	public void setRisk_approvel(String risk_approvel) {
		this.risk_approvel = risk_approvel;
	}

	public String getKyc_approvel() {
		return kyc_approvel;
	}

	public void setKyc_approvel(String kyc_approvel) {
		this.kyc_approvel = kyc_approvel;
	}

	public String getOprations_approvel() {
		return oprations_approvel;
	}

	public void setOprations_approvel(String oprations_approvel) {
		this.oprations_approvel = oprations_approvel;
	}

	public String getSales_lead() {
		return sales_lead;
	}

	public void setSales_lead(String sales_lead) {
		this.sales_lead = sales_lead;
	}	
	
	
	
}
