package com.crm.dto;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDateTime;

public class ResellerDto {
	
	private BigInteger id;
	private String resellerId;
	private String resellerName;
	private String contactPerson;
	private String emailId;
	private String contactNumber;
	private String legalName;
	private String brandName;
	private String businessType;
	private String dateOfIncorporation;
	private String businessCategory;
	private String subCategory;
	private String businessModel;
	private String turnoverLastFinancialYear;
	private String turnoverMonthlyExpeced;
	private String avgAmountPerTxn;
	private String registeredAddress;
	private String pinCode;
	private String city;
	private String state;
	private String remark;
	private String invoiceCycle;
	private String integrationType;
	private String returnUrl;
	private String createdBy;
	private String createdOn;
	private String approvedBy;
	private String approvedOn;
	private String rodt;
	private String isDeleted;
	private String status;
	private String updated_on;
	private String risk_approvel;
	private String kyc_approvel;
	private String oprations_approvel;
	private String emailTrigger;
	private String sales_lead;
	/*
	 * private boolean status;
	 */	private String respMessage;
	
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
	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}
	public void setDateOfIncorporation(String dateOfIncorporation) {
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getApprovedOn() {
		return approvedOn;
	}
	public void setApprovedOn(String approvedOn) {
		this.approvedOn = approvedOn;
	}
	
	public String getRespMessage() {
		return respMessage;
	}
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	public String getRodt() {
		return rodt;
	}
	public void setRodt(String rodt) {
		this.rodt = rodt;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	public String getEmailTrigger() {
		return emailTrigger;
	}
	public void setEmailTrigger(String emailTrigger) {
		this.emailTrigger = emailTrigger;
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
	@Override
	public String toString() {
		return "ResellerDto [id=" + id + ", resellerId=" + resellerId + ", resellerName=" + resellerName
				+ ", contactPerson=" + contactPerson + ", emailId=" + emailId + ", contactNumber=" + contactNumber
				+ ", legalName=" + legalName + ", brandName=" + brandName + ", businessType=" + businessType
				+ ", dateOfIncorporation=" + dateOfIncorporation + ", businessCategory=" + businessCategory
				+ ", subCategory=" + subCategory + ", businessModel=" + businessModel + ", turnoverLastFinancialYear="
				+ turnoverLastFinancialYear + ", turnoverMonthlyExpeced=" + turnoverMonthlyExpeced
				+ ", avgAmountPerTxn=" + avgAmountPerTxn + ", registeredAddress=" + registeredAddress + ", pinCode="
				+ pinCode + ", city=" + city + ", state=" + state + ", remark=" + remark + ", invoiceCycle="
				+ invoiceCycle + ", integrationType=" + integrationType + ", returnUrl=" + returnUrl + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", approvedBy=" + approvedBy + ", approvedOn=" + approvedOn
				+ ", rodt=" + rodt + ", isDeleted=" + isDeleted + ", status=" + status + ", respMessage=" + respMessage
				+ "]";
	}
	
}
