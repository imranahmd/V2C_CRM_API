package com.crm.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Table(name = "tbl_transactionmaster")
public class TransactionMaster {

	@Id
	private String id;

	@Column(name = "txn_id", length = 45)
	private String txn_id;

	@Column(name = "merchant_id", length = 45)
	private String merchantId;

	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "process_id", length = 45)
	private String processId;

	@Column(name = "bank_id", length = 45)
	private String bankId;

	@Column(name = "txn_amount", precision = 12, scale = 2)
	private BigDecimal txnAmount;

	@Column(name = "card_details", length = 100)
	private String cardDetails;

	@Column(name = "card_name", columnDefinition = "String default NA", length = 45)
	private String cardName;

	@Column(name = "card_type", length = 245)
	private String cardType;

	@Column(name = "instrument_id", length = 12)
	private String instrumentId;

	@Column(name = "sur_charge", precision = 12, scale = 2)
	private BigDecimal surCharge;

	@Column(name = "return_url", length = 200)
	private String returnUrl;

	@Column(name = "mobile_no", length = 45)
	private String mobileNo;

	@Column(name = "email_id", length = 45)
	private String emailId;

	@Column(name = "udf1", length = 45)
	private String udf1;

	@Column(name = "udf2", length = 45)
	private String udf2;

	@Column(name = "udf3", length = 45)
	private String udf3;

	@Column(name = "udf4", length = 250)
	private String udf4;

	@Column(name = "udf5", length = 45)
	private String udf5;

	@Column(name = "resp_status", length = 10)
	private int respStatus;

	@Column(name = "resp_date_time")
	private String respDateTime;

	@Column(name = "service_txn_id", length = 45)
	private String serviceTxnId;

	@Column(name = "service_rrn", length = 45)
	private String serviceRrn;

	@Column(name = "service_auth_id", length = 45)
	private String serviceAuthId;

	@Column(name = "recon_status", length = 45)
	private String reconStatus;

	@Column(name = "service_id", length = 45)
	private String serviceId;

	@Column(name = "trans_status", length = 10)
	private String transStatus;

	@Column(name = "resp_message", length = 100)
	private String respMessage;

	@Column(name = "error_code", length = 15)
	private String errorCode;

	@Column(name = "sp_error_code", length = 15)
	private String spErrorCode;

	@Column(name = "remote_ip", length = 45)
	private String remoteIp;

	@Column(name = "rms_score", length = 10)
	private String rmsScore;

	@Column(name = "rms_reason", length = 2000)
	private String rmsReason;

	@Column(name = "Uploadedon")
	private Date uploadedOn;

	@Column(name = "Uploadedby", length = 100)
	private String uploadedBy;

	@Column(name = "Modified_On")
	private String modifiedOn;

	@Column(name = "Modified_By", length = 30)
	private String modifiedBy;

	@Column(name = "Is_Deleted", columnDefinition = "char(1) default N")
	private char isDeleted;

	@Column(name = "rodt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date rodt;

	@Column(name = "sp_call_timestamp")
	private LocalDateTime spCallTimestamp;

	@Column(name = "inquiry_status", columnDefinition = "String default NA", length = 45)
	private String inquiryStatus;

	@Column(name = "bqr_merchant_pan")
	private String bqrMerchantPan;

	// added on 21-02-23
	@Column(name = "reseller_Id", length = 70)
	private String reseller_Id;
	
	@Column(name = "reseller_txn_Id", length = 70)
	private String reseller_txnId;
	
	@Column(name = "udf6", length = 350)
	private String udf6;
	
	public String getReseller_Id() {
		return reseller_Id;
	}

	public void setReseller_Id(String reseller_Id) {
		this.reseller_Id = reseller_Id;
	}

	public String getReseller_txnId() {
		return reseller_txnId;
	}

	public void setReseller_txnId(String reseller_txnId) {
		this.reseller_txnId = reseller_txnId;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}
	//====================================================
	public String getBqrMerchantPan() {
		return bqrMerchantPan;
	}

	public void setBqrMerchantPan(String bqrMerchantPan) {
		this.bqrMerchantPan = bqrMerchantPan;
	}

	public TransactionMaster() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(BigDecimal txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(String cardDetails) {
		this.cardDetails = cardDetails;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public BigDecimal getSurCharge() {
		return surCharge;
	}

	public void setSurCharge(BigDecimal surCharge) {
		this.surCharge = surCharge;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
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

	public int getRespStatus() {
		return respStatus;
	}

	public void setRespStatus(int respStatus) {
		this.respStatus = respStatus;
	}

	public String getRespDateTime() {
		return respDateTime;
	}

	public void setRespDateTime(String respDateTime) {
		this.respDateTime = respDateTime;
	}

	public String getServiceTxnId() {
		return serviceTxnId;
	}

	public void setServiceTxnId(String serviceTxnId) {
		this.serviceTxnId = serviceTxnId;
	}

	public String getServiceRrn() {
		return serviceRrn;
	}

	public void setServiceRrn(String serviceRrn) {
		this.serviceRrn = serviceRrn;
	}

	public String getServiceAuthId() {
		return serviceAuthId;
	}

	public void setServiceAuthId(String serviceAuthId) {
		this.serviceAuthId = serviceAuthId;
	}

	public String getReconStatus() {
		return reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getRespMessage() {
		return respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSpErrorCode() {
		return spErrorCode;
	}

	public void setSpErrorCode(String spErrorCode) {
		this.spErrorCode = spErrorCode;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getRmsScore() {
		return rmsScore;
	}

	public void setRmsScore(String rmsScore) {
		this.rmsScore = rmsScore;
	}

	public String getRmsReason() {
		return rmsReason;
	}

	public void setRmsReason(String rmsReason) {
		this.rmsReason = rmsReason;
	}

	public Date getUploadedOn() {
		return uploadedOn;
	}

	public void setUploadedOn(Date uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
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

	public char getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(char isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getRodt() {
		return rodt;
	}

	public void setRodt(Date rodt) {
		this.rodt = rodt;
	}

	public LocalDateTime getSpCallTimestamp() {
		return spCallTimestamp;
	}

	public void setSpCallTimestamp(LocalDateTime spCallTimestamp) {
		this.spCallTimestamp = spCallTimestamp;
	}

	public String getInquiryStatus() {
		return inquiryStatus;
	}

	public void setInquiryStatus(String inquiryStatus) {
		this.inquiryStatus = inquiryStatus;
	}

	@Override
	public String toString() {
		return "TransactionMaster{" +
				"id=" + id +
				", txnId='" + id + '\'' +
				", merchantId='" + merchantId + '\'' +
				", dateTime=" + dateTime +
				", processId='" + processId + '\'' +
				", bankId='" + bankId + '\'' +
				", txnAmount=" + txnAmount +
				", cardDetails='" + cardDetails + '\'' +
				", cardName='" + cardName + '\'' +
				", cardType='" + cardType + '\'' +
				", instrumentId='" + instrumentId + '\'' +
				", surCharge=" + surCharge +
				", returnUrl='" + returnUrl + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", emailId='" + emailId + '\'' +
				", udf1='" + udf1 + '\'' +
				", udf2='" + udf2 + '\'' +
				", udf3='" + udf3 + '\'' +
				", udf4='" + udf4 + '\'' +
				", udf5='" + udf5 + '\'' +
				", respStatus=" + respStatus +
				", respDateTime=" + respDateTime +
				", serviceTxnId='" + serviceTxnId + '\'' +
				", serviceRrn='" + serviceRrn + '\'' +
				", serviceAuthId='" + serviceAuthId + '\'' +
				", reconStatus='" + reconStatus + '\'' +
				", serviceId='" + serviceId + '\'' +
				", transStatus='" + transStatus + '\'' +
				", respMessage='" + respMessage + '\'' +
				", errorCode='" + errorCode + '\'' +
				", spErrorCode='" + spErrorCode + '\'' +
				", remoteIp='" + remoteIp + '\'' +
				", rmsScore='" + rmsScore + '\'' +
				", rmsReason='" + rmsReason + '\'' +
				", uploadedOn=" + uploadedOn +
				", uploadedBy='" + uploadedBy + '\'' +
				", modifiedOn=" + modifiedOn +
				", modifiedBy='" + modifiedBy + '\'' +
				", isDeleted=" + isDeleted +
				", rodt=" + rodt +
				", spCallTimestamp=" + spCallTimestamp +
				", inquiryStatus='" + inquiryStatus + '\'' +
				'}';
	}
}
