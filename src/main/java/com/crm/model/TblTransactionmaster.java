package com.crm.model;


import java.sql.Timestamp;


public class TblTransactionmaster implements java.io.Serializable{

	// Fields

	private Long id;
	private String orderId;
	private String merchantId;
	private Timestamp dateTime;
	private String processId;
	private Double txnAmount;
	private Double surCharge;
	private String returnUrl;
	private String mobileNo;
	private String emailId;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private String udf5;
	private Integer respStatus;
	private Timestamp respDateTime;
	private Timestamp rodt;
	private String serviceTxnId;
	private String serviceRrn;
	private String serviceAuthId;
	private String reconStatus;
	private String serviceId;
	private String transStatus;
	private String respMessage;
	private String instrumentId;
	private String remoteIp;
	private Timestamp uploadedOn;
	private String uploadedBy;
	private Timestamp modifiedOn;
	private String modifiedBy;
	private String isDeleted;
	private String bankId;
	private String cardDetails;
	private String cardType;
	private String errorCode;
	private String spErrorCode;
	private String rmsScore;
	private String rmsReason;
	private String modified_By;

	public String getModified_By() {
		return modified_By;
	}

	public void setModified_By(String modified_By) {
		this.modified_By = modified_By;
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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	// Constructors
	 public String getCardDetails() {
		    return this.cardDetails;
	 }
	 
	 public void setCardDetails(String cardDetails) {
	    this.cardDetails = cardDetails;
	  }

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	/** default constructor */
	public TblTransactionmaster() {
	}

	/** minimal constructor */
	public TblTransactionmaster(Long id, Timestamp rodt) {
		this.id = id;
		this.rodt = rodt;
	}

	/** full constructor */
	public TblTransactionmaster(Long id, Timestamp dateTime,
			String processId, Double txnAmount, Double surCharge,
			String returnUrl, String mobileNo, String emailId, String udf1,
			String udf2, String udf3, String udf4, String udf5,
			Integer respStatus, Timestamp respDateTime, Timestamp rodt,
			String serviceTxnId, String serviceRrn, String serviceAuthId,
			String reconStatus, String serviceId, String transStatus,
			String respMessage, String instrumentId, String remoteIp,
			Timestamp uploadedOn, String uploadedBy, Timestamp modifiedOn,
			String modifiedBy, String isDeleted) {
		this.id = id;
		this.dateTime = dateTime;
		this.processId = processId;
		this.txnAmount = txnAmount;
		this.surCharge = surCharge;
		this.returnUrl = returnUrl;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.udf1 = udf1;
		this.udf2 = udf2;
		this.udf3 = udf3;
		this.udf4 = udf4;
		this.udf5 = udf5;
		this.respStatus = respStatus;
		this.respDateTime = respDateTime;
		this.rodt = rodt;
		this.serviceTxnId = serviceTxnId;
		this.serviceRrn = serviceRrn;
		this.serviceAuthId = serviceAuthId;
		this.reconStatus = reconStatus;
		this.serviceId = serviceId;
		this.transStatus = transStatus;
		this.respMessage = respMessage;
		this.instrumentId = instrumentId;
		this.remoteIp = remoteIp;
		this.uploadedOn = uploadedOn;
		this.uploadedBy = uploadedBy;
		this.modifiedOn = modifiedOn;
		this.modifiedBy = modifiedBy;
		this.isDeleted = isDeleted;
	}

	// Property accessors

	public Long getId() {
		return this.id;
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

	public Timestamp getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getProcessId() {
		return this.processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Double getTxnAmount() {
		return this.txnAmount;
	}

	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	public Double getSurCharge() {
		return this.surCharge;
	}

	public void setSurCharge(Double surCharge) {
		this.surCharge = surCharge;
	}

	public String getReturnUrl() {
		return this.returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUdf1() {
		return this.udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return this.udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return this.udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return this.udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public String getUdf5() {
		return this.udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public Integer getRespStatus() {
		return this.respStatus;
	}

	public void setRespStatus(Integer respStatus) {
		this.respStatus = respStatus;
	}

	public Timestamp getRespDateTime() {
		return this.respDateTime;
	}

	public void setRespDateTime(Timestamp respDateTime) {
		this.respDateTime = respDateTime;
	}

	public Timestamp getRodt() {
		return this.rodt;
	}

	public void setRodt(Timestamp rodt) {
		this.rodt = rodt;
	}

	public String getServiceTxnId() {
		return this.serviceTxnId;
	}

	public void setServiceTxnId(String serviceTxnId) {
		this.serviceTxnId = serviceTxnId;
	}

	public String getServiceRrn() {
		return this.serviceRrn;
	}

	public void setServiceRrn(String serviceRrn) {
		this.serviceRrn = serviceRrn;
	}

	public String getServiceAuthId() {
		return this.serviceAuthId;
	}

	public void setServiceAuthId(String serviceAuthId) {
		this.serviceAuthId = serviceAuthId;
	}

	public String getReconStatus() {
		return this.reconStatus;
	}

	public void setReconStatus(String reconStatus) {
		this.reconStatus = reconStatus;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTransStatus() {
		return this.transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getRespMessage() {
		return this.respMessage;
	}

	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}

	public String getInstrumentId() {
		return this.instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getRemoteIp() {
		return this.remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Timestamp getUploadedOn() {
		return this.uploadedOn;
	}

	public void setUploadedOn(Timestamp uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getUploadedBy() {
		return this.uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Timestamp getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("TblTransactionmaster { \n");
		sb.append("id ="+ id +"\n");
		sb.append("merchantId = " + merchantId + "\n");
		sb.append("dateTime = "+ dateTime +"\n");
		sb.append("processId = "+ processId +" \n");
		sb.append("txnAmount = "+ txnAmount + "\n");
		sb.append("surCharge =" + surCharge + "\n");
		sb.append("returnUrl = "+ returnUrl +"\n");
		sb.append("mobileNo = " + mobileNo + "\n");
		sb.append("emailId = " + emailId + "\n");
		sb.append("mobileNo = " + mobileNo + "\n");
		sb.append("udf1 = " + udf1 + "\n");
		sb.append("udf2 = " + udf2 + "\n");
		sb.append("udf3 = " + udf3 + "\n");
		sb.append("udf4 = " + udf4 + "\n");
		sb.append("udf5 = " + udf5 + "\n");
		sb.append("respStatus = " + respStatus + "\n");
		sb.append("respDateTime = " + respDateTime + "\n");
		sb.append("rodt = " + rodt + "\n");
		sb.append("serviceTxnId = " + serviceTxnId + "\n");
		sb.append("serviceRrn = " + serviceRrn + "\n");
		sb.append("serviceAuthId = " + serviceAuthId + "\n");
		sb.append("reconStatus = " + reconStatus + "\n");
		sb.append("serviceId = " + serviceId + "\n");
		sb.append("transStatus = " + transStatus + "\n");
		sb.append("respMessage = " + respMessage + "\n");
		sb.append("instrumentId = " + instrumentId + "\n");
		sb.append("remoteIp = " + remoteIp + "\n");
		sb.append("uploadedOn = " + uploadedOn + "\n");
		sb.append("uploadedBy = " + uploadedBy + "\n");
		sb.append("modifiedOn = " + modifiedOn + "\n");
		sb.append("modifiedBy = " + modifiedBy + "\n");
		sb.append("isDeleted = " + isDeleted + "\n");
		sb.append("}\n");
		return sb.toString();
	}
}
