package com.crm.dto;

import jakarta.persistence.Column;

public class BulkBatchSql {
	
	private String batch_id;
	private String merchant_id;
	private Integer transaction_count;
	private Double refund_amount;
	private String status;	
	private Integer is_deleted;
	private String file_path;
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public Integer getTransaction_count() {
		return transaction_count;
	}
	public void setTransaction_count(Integer transaction_count) {
		this.transaction_count = transaction_count;
	}
	public Double getRefund_amount() {
		return refund_amount;
	}
	public void setRefund_amount(Double refund_amount) {
		this.refund_amount = refund_amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(Integer is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
}
