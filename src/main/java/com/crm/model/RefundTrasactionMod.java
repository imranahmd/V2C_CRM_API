package com.crm.model;

import jakarta.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_transactionmaster")
public class RefundTrasactionMod {
	@Id  
	  
	  private String id;
	  private String txnId;
	  private String merchantId;
	  private String dateTime;
	  private String processId;
	  private String amount;
	  private String productId;
	  private String returnUrl;
	  private String custMobile;
	  private String custMail;
	  private String udf1;
	  private String udf2;
	  private String udf3;
	  private String udf4;
	  private String udf5;
	  private String reconstatus;
	  private String instrumentId;
	  private String serviceRRN;
	  private String serviceTxnId;
	  private String respStatus;
	  private String transStatus;
	  private String hostAddress;
	  private String surcharge;
	  private String respDateTime;
	  private String modifiedOn;
	  private String modifiedBy;
	  private String serviceAuthId;
	  private String respMessage;
	  private String atrn;
	  private String bankId;
	  private String cardDetail;
	  private String cardType;
	  private String uploadBy;
	  private String cardDetails;
	  private String errorCode;
	  private String respDatTime;
	  private String rmsReason;
	  private String rmsScore;
	  private String spErrorCode;
	  private String inquiryStatus;
	  private String spCallTimestamp;
	  private String rmsflag;
	  private String rms_code;
	  private String rms_level;
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
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCustMobile() {
		return custMobile;
	}
	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}
	public String getCustMail() {
		return custMail;
	}
	public void setCustMail(String custMail) {
		this.custMail = custMail;
	}
	public String getUdf1() {
		return udf1;
	}
	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}
	public String getUdf2() {
		return udf2;
	}
	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}
	public String getUdf3() {
		return udf3;
	}
	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}
	public String getUdf4() {
		return udf4;
	}
	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}
	public String getUdf5() {
		return udf5;
	}
	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}
	public String getReconstatus() {
		return reconstatus;
	}
	public void setReconstatus(String reconstatus) {
		this.reconstatus = reconstatus;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getServiceRRN() {
		return serviceRRN;
	}
	public void setServiceRRN(String serviceRRN) {
		this.serviceRRN = serviceRRN;
	}
	public String getServiceTxnId() {
		return serviceTxnId;
	}
	public void setServiceTxnId(String serviceTxnId) {
		this.serviceTxnId = serviceTxnId;
	}
	public String getRespStatus() {
		return respStatus;
	}
	public void setRespStatus(String respStatus) {
		this.respStatus = respStatus;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public String getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}
	public String getRespDateTime() {
		return respDateTime;
	}
	public void setRespDateTime(String respDateTime) {
		this.respDateTime = respDateTime;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getServiceAuthId() {
		return serviceAuthId;
	}
	public void setServiceAuthId(String serviceAuthId) {
		this.serviceAuthId = serviceAuthId;
	}
	public String getRespMessage() {
		return respMessage;
	}
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	public String getAtrn() {
		return atrn;
	}
	public void setAtrn(String atrn) {
		this.atrn = atrn;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCardDetail() {
		return cardDetail;
	}
	public void setCardDetail(String cardDetail) {
		this.cardDetail = cardDetail;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getUploadBy() {
		return uploadBy;
	}
	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}
	public String getCardDetails() {
		return cardDetails;
	}
	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getRespDatTime() {
		return respDatTime;
	}
	public void setRespDatTime(String respDatTime) {
		this.respDatTime = respDatTime;
	}
	public String getRmsReason() {
		return rmsReason;
	}
	public void setRmsReason(String rmsReason) {
		this.rmsReason = rmsReason;
	}
	public String getRmsScore() {
		return rmsScore;
	}
	public void setRmsScore(String rmsScore) {
		this.rmsScore = rmsScore;
	}
	public String getSpErrorCode() {
		return spErrorCode;
	}
	public void setSpErrorCode(String spErrorCode) {
		this.spErrorCode = spErrorCode;
	}
	public String getInquiryStatus() {
		return inquiryStatus;
	}
	public void setInquiryStatus(String inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}
	public String getSpCallTimestamp() {
		return spCallTimestamp;
	}
	public void setSpCallTimestamp(String spCallTimestamp) {
		this.spCallTimestamp = spCallTimestamp;
	}
	public String getRmsflag() {
		return rmsflag;
	}
	public void setRmsflag(String rmsflag) {
		this.rmsflag = rmsflag;
	}
	public String getRms_code() {
		return rms_code;
	}
	public void setRms_code(String rms_code) {
		this.rms_code = rms_code;
	}
	public String getRms_level() {
		return rms_level;
	}
	public void setRms_level(String rms_level) {
		this.rms_level = rms_level;
	}
	  
	  
	  
}
