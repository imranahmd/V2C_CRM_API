package com.crm.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_BulkRefund")
public class BulkRefund {
	
	private String batchId;
	private String txnId;
	private Double refundAmount;
	private String status;
	private String err_message;
	private String uploadedOn;
	private String modifiedOn;
	private String is_deleted; 
	private String merchantRefId;
	private String merchantId;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErr_message() {
		return err_message;
	}
	public void setErr_message(String err_message) {
		this.err_message = err_message;
	}
	public String getUploadedOn() {
		return uploadedOn;
	}
	public void setUploadedOn(String uploadedOn) {
		this.uploadedOn = uploadedOn;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getMerchantRefId() {
		return merchantRefId;
	}
	public void setMerchantRefId(String merchantRefId) {
		this.merchantRefId = merchantRefId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	
	
}
