package com.crm.model;

import jakarta.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_transactionmaster")
public class RefundTransaction {
	 @Id
	 private String id;
	 private String txnId;
	 private String merchantId;
	 private String dateTime;
	 private String amount;
	 private String serviceRRN;
	 private String instrumentId;	 
	 private String bankId;
	 private String processId;
	 private String reconStatus;
	
	 
	 public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getServiceRRN() {
		return serviceRRN;
	}
	public void setServiceRRN(String serviceRRN) {
		this.serviceRRN = serviceRRN;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getbankId() {
		return bankId;
	}
	public void setbankId(String bankId) {
		this.bankId = bankId;
	}
	public String getReconStatus() {
		return reconStatus;
	}
	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}
		 

}
