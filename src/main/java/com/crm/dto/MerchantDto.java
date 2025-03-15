package com.crm.dto;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

public class MerchantDto {
	
	
	private String name;
	private String merchantId;
	private String businessName;
	private String contactperson;
	private BigInteger id;
	private String contactno;
	private String emailid;
	private String state;
	private String city;
	private String isdeleted;
	private String Status;
	private String Created_on;
	private String DateCorporation_on;
	private String RiskApproval;
	private String KYCApproval;
	private String BasicSetupApproval;
	private String Reseller_Id;
	private String Reseller_Name;
	private String sales_lead;
	private String partners_type;
	private String bank_id;
	
	
	private String logoPath;
	
	public String getName() {
		return name;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getCreated_on() {
		return Created_on;
	}
	public void setCreated_on(String obj) {
		Created_on = obj;
	}
	public String getDateCorporation_on() {
		return DateCorporation_on;
	}
	public void setDateCorporation_on(String dateCorporation_on) {
		DateCorporation_on = dateCorporation_on;
	}
	public String getRiskApproval() {
		return RiskApproval;
	}
	public void setRiskApproval(String riskApproval) {
		RiskApproval = riskApproval;
	}
	public String getKYCApproval() {
		return KYCApproval;
	}
	public void setKYCApproval(String kYCApproval) {
		KYCApproval = kYCApproval;
	}
	public String getBasicSetupApproval() {
		return BasicSetupApproval;
	}
	public void setBasicSetupApproval(String basicSetupApproval) {
		BasicSetupApproval = basicSetupApproval;
	}
	public String getReseller_Id() {
		return Reseller_Id;
	}
	public void setReseller_Id(String reseller_Id) {
		Reseller_Id = reseller_Id;
	}
	public String getReseller_Name() {
		return Reseller_Name;
	}
	public void setReseller_Name(String reseller_Name) {
		Reseller_Name = reseller_Name;
	}
	public String getSales_lead() {
		return sales_lead;
	}
	public void setSales_lead(String sales_lead) {
		this.sales_lead = sales_lead;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getPartners_type() {
		return partners_type;
	}
	public void setPartners_type(String partners_type) {
		this.partners_type = partners_type;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	@Override
	public String toString() {
		return "MerchantDto [name=" + name + ", merchantId=" + merchantId + ", businessName=" + businessName
				+ ", contactperson=" + contactperson + ", id=" + id + ", contactno=" + contactno + ", emailid="
				+ emailid + ", state=" + state + ", city=" + city + ", isdeleted=" + isdeleted + ", Status=" + Status
				+ ", Created_on=" + Created_on + ", DateCorporation_on=" + DateCorporation_on + ", RiskApproval="
				+ RiskApproval + ", KYCApproval=" + KYCApproval + ", BasicSetupApproval=" + BasicSetupApproval
				+ ", Reseller_Id=" + Reseller_Id + ", Reseller_Name=" + Reseller_Name + ", sales_lead=" + sales_lead
				+ ", partners_type=" + partners_type + ", bank_id=" + bank_id + ", logoPath=" + logoPath + "]";
	}
	
	
	

}
