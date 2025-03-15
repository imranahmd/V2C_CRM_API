package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;


@Data
@Entity
@Table(name = "tbl_merchant_product")
public class MerchantBank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PId")
	private Long id;
	
	@Column(name="merchant_id")
	private String merchantId;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="account_holder")
	private String account_holder;
	
	@Column(name="ifsc_code")
	private String ifscCode;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Column(name="rodt")
	private String rodate;
	
	@Column(name="is_verified")
	private String IsVerified;
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="email_id")
	private String emailId;
	
	/*
	 * @Column(name="escrow") private String escrow;
	 * 
	 * @Column(name="default_id") private String default_id;
	 * 
	 * @Column(name="transaction_type") private String transaction_type;
	 * 
	 * @Column(name="benificiary_code") private String benificiary_code;
	 * 
	 * @Column(name="escrow_bank") private String escrow_bank;
	 */
	
	@Transient
	private String error; 
	
	@Transient
	private String Status; 
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getRodate() {
		return rodate;
	}
	public void setRodate(String rodate) {
		this.rodate = rodate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccount_holder() {
		return account_holder;
	}
	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}
	
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	 
	public String getStatus() {
		return Status;
	}
	public void setStatus(String b) {
		Status = b;
	}
	
	
	

	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getIsVerified() {
		return IsVerified;
	}
	public void setIsVerified(String isVerified) {
		this.IsVerified = isVerified;
	}
	@Override
	public String toString() {
		return "MerchantBank [id=" + id + ", merchantId=" + merchantId + ", productId=" + productId + ", accountNumber="
				+ accountNumber + ", account_holder=" + account_holder + ", ifscCode=" + ifscCode + ", bankName="
				+ bankName + ", rodate=" + rodate + ", IsVerified=" + IsVerified + ", mobileNo=" + mobileNo
				+ ", emailId=" + emailId + ", error=" + error + ", Status=" + Status + "]";
	}
	
	/**/
	
	
	
	
	
	

}
