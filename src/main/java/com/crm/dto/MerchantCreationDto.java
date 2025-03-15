package com.crm.dto;

import java.math.BigInteger;
import java.util.List;


public class MerchantCreationDto {
	
	private String contactperson;
	private String contactNumber;
	private String companypan;
	private String merchantname;
	private String businessname;
	private int businesstype;
	private String creationdate;
	private String merchantcatagorycode;
	private String merchantsubcatagory;
	private String businessmodal;
	private String turnoverfyear;
	private String monthlyincome;
	private String avgamtpertransaction;
	private String authorisedpan;
	private String nameaspan;
	private String registeraddress;
	private String pincode;
	private String city;
	private String State;
	private String gstno;
	private String website;
	private String testaccess;
	private String instruments;
	private String merchantid;
	private String transactionkey;
	private String emailId;
	private String createdBy;
	private String iResellerId;
	private Character isPanVerified;
	private String dateOfIncorporation;
	private String merReturnUrl;
	private String source="admin";
	private Character isCompanyPanVerify;
	private Character iIsGSTVerify;
	private String iCompanyPanVerifyName;
	private String iGSTVerifyName;
	private String name_as_perpan;
	private String sales_lead;
	private String partners_type;
	private String bank_id;
	private List whitelistPath;
	
	
	private List<AdditionContact> additional_contact;
	
	private String logoPath;
	

	
//	private String maxtokensize;
	
	public String getiResellerId() {
		return iResellerId;
	}
	public void setiResellerId(String iResellerId) {
		this.iResellerId = iResellerId;
	}
	public Character getIsPanVerified() {
		return isPanVerified;
	}
	public void setIsPanVerified(Character isPanVerified) {
		this.isPanVerified = isPanVerified;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	private BigInteger id;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getCompanypan() {
		return companypan;
	}
	public void setCompanypan(String companypan) {
		this.companypan = companypan;
	}
	public String getMerchantname() {
		return merchantname;
	}
	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public int getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(int businesstype) {
		this.businesstype = businesstype;
	}

	public String getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}
	
	public String getMerchantcatagorycode() {
		return merchantcatagorycode;
	}
	
	public void setMerchantcatagorycode(String merchantcatagorycode) {
		this.merchantcatagorycode = merchantcatagorycode;
	}
	public String getMerchantsubcatagory() {
		return merchantsubcatagory;
	}
	public void setMerchantsubcatagory(String merchantsubcatagory) {
		this.merchantsubcatagory = merchantsubcatagory;
	}
	public String getBusinessmodal() {
		return businessmodal;
	}
	public void setBusinessmodal(String businessmodal) {
		this.businessmodal = businessmodal;
	}
	public String getTurnoverfyear() {
		return turnoverfyear;
	}
	public void setTurnoverfyear(String turnoverfyear) {
		this.turnoverfyear = turnoverfyear;
	}
	public String getMonthlyincome() {
		return monthlyincome;
	}
	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
	}
	public String getAvgamtpertransaction() {
		return avgamtpertransaction;
	}
	public void setAvgamtpertransaction(String avgamtpertransaction) {
		this.avgamtpertransaction = avgamtpertransaction;
	}
	public String getAuthorisedpan() {
		return authorisedpan;
	}
	public void setAuthorisedpan(String authorisedpan) {
		this.authorisedpan = authorisedpan;
	}
	public String getNameaspan() {
		return nameaspan;
	}
	public void setNameaspan(String nameaspan) {
		this.nameaspan = nameaspan;
	}
	public String getRegisteraddress() {
		return registeraddress;
	}
	public void setRegisteraddress(String registeraddress) {
		this.registeraddress = registeraddress;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getGstno() {
		return gstno;
	}
	public void setGstno(String gstno) {
		this.gstno = gstno;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getTestaccess() {
		return testaccess;
	}
	public void setTestaccess(String testaccess) {
		this.testaccess = testaccess;
	}
	public String getInstruments() {
		return instruments;
	}
	public void setInstruments(String instruments) {
		this.instruments = instruments;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getTransactionkey() {
		return transactionkey;
	}
	public void setTransactionkey(String transactionkey) {
		this.transactionkey = transactionkey;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}
	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}
	
	public String getMerReturnUrl() {
		return merReturnUrl;
	}
	public void setMerReturnUrl(String merReturnUrl) {
		this.merReturnUrl = merReturnUrl;
	}
	
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Character getIsCompanyPanVerify() {
		return isCompanyPanVerify;
	}
	public void setIsCompanyPanVerify(Character isCompanyPanVerify) {
		this.isCompanyPanVerify = isCompanyPanVerify;
	}
	public Character getiIsGSTVerify() {
		return iIsGSTVerify;
	}
	public void setiIsGSTVerify(Character iIsGSTVerify) {
		this.iIsGSTVerify = iIsGSTVerify;
	}
	public String getiCompanyPanVerifyName() {
		return iCompanyPanVerifyName;
	}
	public void setiCompanyPanVerifyName(String iCompanyPanVerifyName) {
		this.iCompanyPanVerifyName = iCompanyPanVerifyName;
	}
	public String getiGSTVerifyName() {
		return iGSTVerifyName;
	}
	public void setiGSTVerifyName(String iGSTVerifyName) {
		this.iGSTVerifyName = iGSTVerifyName;
	}
	public String getName_as_perpan() {
		return name_as_perpan;
	}
	public void setName_as_perpan(String name_as_perpan) {
		this.name_as_perpan = name_as_perpan;
	}
	public String getSales_lead() {
		return sales_lead;
	}
	public void setSales_lead(String sales_lead) {
		this.sales_lead = sales_lead;
	}
	public List<AdditionContact> getAdditional_contact() {
		return additional_contact;
	}
	public void setAdditional_contact(List<AdditionContact> additional_contact) {
		this.additional_contact = additional_contact;
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
		public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
	
	public List getWhitelistPath() {
		return whitelistPath;
	}
	public void setWhitelistPath(List whitelistPath) {
		this.whitelistPath = whitelistPath;
	}
	@Override
	public String toString() {
		return "MerchantCreationDto [contactperson=" + contactperson + ", contactNumber=" + contactNumber
				+ ", companypan=" + companypan + ", merchantname=" + merchantname + ", businessname=" + businessname
				+ ", businesstype=" + businesstype + ", creationdate=" + creationdate + ", merchantcatagorycode="
				+ merchantcatagorycode + ", merchantsubcatagory=" + merchantsubcatagory + ", businessmodal="
				+ businessmodal + ", turnoverfyear=" + turnoverfyear + ", monthlyincome=" + monthlyincome
				+ ", avgamtpertransaction=" + avgamtpertransaction + ", authorisedpan=" + authorisedpan + ", nameaspan="
				+ nameaspan + ", registeraddress=" + registeraddress + ", pincode=" + pincode + ", city=" + city
				+ ", State=" + State + ", gstno=" + gstno + ", website=" + website + ", testaccess=" + testaccess
				+ ", instruments=" + instruments + ", merchantid=" + merchantid + ", transactionkey=" + transactionkey
				+ ", emailId=" + emailId + ", createdBy=" + createdBy + ", iResellerId=" + iResellerId
				+ ", isPanVerified=" + isPanVerified + ", dateOfIncorporation=" + dateOfIncorporation
				+ ", merReturnUrl=" + merReturnUrl + ", source=" + source + ", isCompanyPanVerify=" + isCompanyPanVerify
				+ ", iIsGSTVerify=" + iIsGSTVerify + ", iCompanyPanVerifyName=" + iCompanyPanVerifyName
				+ ", iGSTVerifyName=" + iGSTVerifyName + ", name_as_perpan=" + name_as_perpan + ", sales_lead="
				+ sales_lead + ", partners_type=" + partners_type + ", bank_id=" + bank_id + ", whitelistPath="
				+ whitelistPath + ", additional_contact=" + additional_contact + ", logoPath=" + logoPath + ", id=" + id
				+ ", getiResellerId()=" + getiResellerId() + ", getIsPanVerified()=" + getIsPanVerified()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getId()=" + getId() + ", getContactperson()="
				+ getContactperson() + ", getContactNumber()=" + getContactNumber() + ", getCompanypan()="
				+ getCompanypan() + ", getMerchantname()=" + getMerchantname() + ", getBusinessname()="
				+ getBusinessname() + ", getBusinesstype()=" + getBusinesstype() + ", getCreationdate()="
				+ getCreationdate() + ", getMerchantcatagorycode()=" + getMerchantcatagorycode()
				+ ", getMerchantsubcatagory()=" + getMerchantsubcatagory() + ", getBusinessmodal()="
				+ getBusinessmodal() + ", getTurnoverfyear()=" + getTurnoverfyear() + ", getMonthlyincome()="
				+ getMonthlyincome() + ", getAvgamtpertransaction()=" + getAvgamtpertransaction()
				+ ", getAuthorisedpan()=" + getAuthorisedpan() + ", getNameaspan()=" + getNameaspan()
				+ ", getRegisteraddress()=" + getRegisteraddress() + ", getPincode()=" + getPincode() + ", getCity()="
				+ getCity() + ", getState()=" + getState() + ", getGstno()=" + getGstno() + ", getWebsite()="
				+ getWebsite() + ", getTestaccess()=" + getTestaccess() + ", getInstruments()=" + getInstruments()
				+ ", getMerchantid()=" + getMerchantid() + ", getTransactionkey()=" + getTransactionkey()
				+ ", getEmailId()=" + getEmailId() + ", getDateOfIncorporation()=" + getDateOfIncorporation()
				+ ", getMerReturnUrl()=" + getMerReturnUrl() + ", getSource()=" + getSource()
				+ ", getIsCompanyPanVerify()=" + getIsCompanyPanVerify() + ", getiIsGSTVerify()=" + getiIsGSTVerify()
				+ ", getiCompanyPanVerifyName()=" + getiCompanyPanVerifyName() + ", getiGSTVerifyName()="
				+ getiGSTVerifyName() + ", getName_as_perpan()=" + getName_as_perpan() + ", getSales_lead()="
				+ getSales_lead() + ", getAdditional_contact()=" + getAdditional_contact() + ", getPartners_type()="
				+ getPartners_type() + ", getBank_id()=" + getBank_id() + ", getLogoPath()=" + getLogoPath()
				+ ", getWhitelistPath()=" + getWhitelistPath() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
