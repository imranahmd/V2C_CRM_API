package com.crm.dto;

import java.math.BigInteger;

public class ResellerListDto {
	private BigInteger id;
	private String resellerId;
	private String resellerName;
	private String contactPerson;
	private String emailId;
	private String contactNumber;
	private String businessType;
	private String city;
	private String state;
	private String createdOn;
	private String DateofIncroporation;
	private String Kyc_Approvel;
	private String status;
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
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
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
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getDateofIncroporation() {
		return DateofIncroporation;
	}
	public void setDateofIncroporation(String dateofIncroporation) {
		DateofIncroporation = dateofIncroporation;
	}
	public String getKyc_Approvel() {
		return Kyc_Approvel;
	}
	public void setKyc_Approvel(String kyc_Approvel) {
		Kyc_Approvel = kyc_Approvel;
	}
	
	public String getSales_lead() {
		return sales_lead;
	}
	public void setSales_lead(String sales_lead) {
		this.sales_lead = sales_lead;
	}
	@Override
	public String toString() {
		return "ResellerListDto [id=" + id + ", resellerId=" + resellerId + ", resellerName=" + resellerName
				+ ", contactPerson=" + contactPerson + ", emailId=" + emailId + ", contactNumber=" + contactNumber
				+ ", businessType=" + businessType + ", city=" + city + ", state=" + state + ", createdOn=" + createdOn
				+ ", getId()=" + getId() + ", getResellerId()=" + getResellerId() + ", getResellerName()="
				+ getResellerName() + ", getContactPerson()=" + getContactPerson() + ", getEmailId()=" + getEmailId()
				+ ", getContactNumber()=" + getContactNumber() + ", getBusinessType()=" + getBusinessType()
				+ ", getCity()=" + getCity() + ", getState()=" + getState() + ", getCreatedOn()=" + getCreatedOn()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
