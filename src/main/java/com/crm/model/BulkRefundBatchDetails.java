package com.crm.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_BulkRefundBatchDetails")
public class BulkRefundBatchDetails {
	private String batchId;
	private String merchantId;
	private Integer transactionCount;
	private Double refundAmount;
	private String status;
	private String uploadedOn;
	private String modifiedOn;
	private String is_deleted;
	private String file_path;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getTransactionCount() {
		return transactionCount;
	}
	public void setTransactionCount(Integer transactionCount) {
		this.transactionCount = transactionCount;
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
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	

}
