package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "tbl_mstmerchant")
public class MerchantList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;

	@Column(name= "MerchantId")
	private String merchantId;

	@Column(name= "transaction_key")
	private String transaction_key;

	
	@Column(name = "merchant_name")
	private String merchantName;

	@Column(name = "status")
	private String status;
	
	@Column(name = "res_fieldsdetails")
	private String resFieldsDetails;
	
	@Column(name = "kyc_approvel")
	private String kycApporvel;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResFieldsDetails() {
		return resFieldsDetails;
	}

	public void setResFieldsDetails(String resFieldsDetails) {
		this.resFieldsDetails = resFieldsDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	
	public String getKycApporvel() {
		return kycApporvel;
	}

	public void setKycApporvel(String kycApporvel) {
		this.kycApporvel = kycApporvel;
	}
	
	

	public String getTransaction_key() {
		return transaction_key;
	}

	public void setTransaction_key(String transaction_key) {
		this.transaction_key = transaction_key;
	}

	@Override
	public String toString() {
		return "MerchantList [id=" + id + ", merchantId=" + merchantId + ", transaction_key=" + transaction_key
				+ ", merchantName=" + merchantName + ", status=" + status + ", resFieldsDetails=" + resFieldsDetails
				+ ", kycApporvel=" + kycApporvel + "]";
	}



}
