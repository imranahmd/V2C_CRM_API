package com.crm.dto;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_payrequest_details")
public class PaymentRequestList {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    @Column(name = "PayReqId")
	    private String payReqId;

	    @Column(name = "TransactionId")
	    private String TransactionId;
	    
	    @Column(name = "reqdate")
	    private String reqDate;

	    @Column(name = "ClientRefNo")
	    private String clientRefNo;

	    @Column(name = "txnMode")
	    private String txnMode;

	    @Column(name = "account_number")
	    private String accountNumber;

	    @Column(name = "account_Ifsc")
	    private String accountIfsc;

	    @Column(name = "bank_name")
	    private String bankName;

	    @Column(name = "account_holder_name")
	    private String accountHolderName;

	    @Column(name = "beneficiary_name")
	    private String beneficiaryName;

	    @Column(name = "vpa")
	    private String vpa;

	    @Column(name = "txnAmount")
	    private String txnAmount;

	    @Column(name="utr_no")
	    private String utr_no;
	    
	    @Column(name = "adf1")
	    private String adf1;

	    @Column(name = "adf2")
	    private String adf2;

	    @Column(name = "adf3")
	    private String adf3;

	    @Column(name = "adf4")
	    private String adf4;

	    @Column(name = "adf5")
	    private String adf5;

	    @Column(name = "isbeneficiary")
	    private char isBeneficiary;

	    @Column(name = "beneficiarycode")
	    private String beneficiaryCode;

	    @Column(name = "respcode")
	    private String respCode;

	    @Column(name = "respmessage")
	    private String respMessage;

	    @Column(name = "sprespcode")
	    private String spRespCode;

	    @Column(name = "sprespmessage")
	    private String spRespMessage;

	    @Column(name = "Status")
	    private String status;

	    // Constructors, getters, and setters

	    
	    
	    // Default constructo
	  
	    
	    
	    
	    public String getPayReqId() {
	        return payReqId;
	    }

	 
	    
	


		public PaymentRequestList(int id, String payReqId, String transactionId, String reqDate, String clientRefNo,
				String txnMode, String accountNumber, String accountIfsc, String bankName, String accountHolderName,
				String beneficiaryName, String vpa, String txnAmount, String utr_no, String adf1, String adf2,
				String adf3, String adf4, String adf5, char isBeneficiary, String beneficiaryCode, String respCode,
				String respMessage, String spRespCode, String spRespMessage, String status) {
			super();
			this.id = id;
			this.payReqId = payReqId;
			TransactionId = transactionId;
			this.reqDate = reqDate;
			this.clientRefNo = clientRefNo;
			this.txnMode = txnMode;
			this.accountNumber = accountNumber;
			this.accountIfsc = accountIfsc;
			this.bankName = bankName;
			this.accountHolderName = accountHolderName;
			this.beneficiaryName = beneficiaryName;
			this.vpa = vpa;
			this.txnAmount = txnAmount;
			this.utr_no = utr_no;
			this.adf1 = adf1;
			this.adf2 = adf2;
			this.adf3 = adf3;
			this.adf4 = adf4;
			this.adf5 = adf5;
			this.isBeneficiary = isBeneficiary;
			this.beneficiaryCode = beneficiaryCode;
			this.respCode = respCode;
			this.respMessage = respMessage;
			this.spRespCode = spRespCode;
			this.spRespMessage = spRespMessage;
			this.status = status;
		}






		public PaymentRequestList() {
			super();
			// TODO Auto-generated constructor stub
		}

		
		public int getId() {
			return id;
		}



		public void setId(int id) {
			this.id = id;
		}



		public void setPayReqId(String payReqId) {
	        this.payReqId = payReqId;
	    }

	    public String getReqDate() {
	        return reqDate;
	    }

	    public void setReqDate(String reqDate) {
	        this.reqDate = reqDate;
	    }

	    public String getClientRefNo() {
	        return clientRefNo;
	    }

	    public void setClientRefNo(String clientRefNo) {
	        this.clientRefNo = clientRefNo;
	    }

	    public String getTxnMode() {
	        return txnMode;
	    }

	    public void setTxnMode(String txnMode) {
	        this.txnMode = txnMode;
	    }

	    public String getAccountNumber() {
	        return accountNumber;
	    }

	    public void setAccountNumber(String accountNumber) {
	        this.accountNumber = accountNumber;
	    }

	    public String getAccountIfsc() {
	        return accountIfsc;
	    }

	    public void setAccountIfsc(String accountIfsc) {
	        this.accountIfsc = accountIfsc;
	    }

	    public String getBankName() {
	        return bankName;
	    }

	    public void setBankName(String bankName) {
	        this.bankName = bankName;
	    }

	    public String getAccountHolderName() {
	        return accountHolderName;
	    }

	    public void setAccountHolderName(String accountHolderName) {
	        this.accountHolderName = accountHolderName;
	    }

	    public String getBeneficiaryName() {
	        return beneficiaryName;
	    }

	    public void setBeneficiaryName(String beneficiaryName) {
	        this.beneficiaryName = beneficiaryName;
	    }

	    public String getVpa() {
	        return vpa;
	    }

	    public void setVpa(String vpa) {
	        this.vpa = vpa;
	    }

	    public String getTxnAmount() {
	        return txnAmount;
	    }

	    public void setTxnAmount(String txnAmount) {
	        this.txnAmount = txnAmount;
	    }

	    public String getAdf1() {
	        return adf1;
	    }

	    public void setAdf1(String adf1) {
	        this.adf1 = adf1;
	    }

	    public String getAdf2() {
	        return adf2;
	    }

	    public void setAdf2(String adf2) {
	        this.adf2 = adf2;
	    }

	    public String getAdf3() {
	        return adf3;
	    }

	    public void setAdf3(String adf3) {
	        this.adf3 = adf3;
	    }

	    public String getAdf4() {
	        return adf4;
	    }

	    public void setAdf4(String adf4) {
	        this.adf4 = adf4;
	    }

	    public String getAdf5() {
	        return adf5;
	    }

	    public void setAdf5(String adf5) {
	        this.adf5 = adf5;
	    }

	    public char isBeneficiary() {
	        return isBeneficiary;
	    }

	    public void setBeneficiary(char beneficiary) {
	        isBeneficiary = beneficiary;
	    }

	    public String getBeneficiaryCode() {
	        return beneficiaryCode;
	    }

	    public void setBeneficiaryCode(String beneficiaryCode) {
	        this.beneficiaryCode = beneficiaryCode;
	    }

	    public String getRespCode() {
	        return respCode;
	    }

	    public void setRespCode(String respCode) {
	        this.respCode = respCode;
	    }

	    public String getRespMessage() {
	        return respMessage;
	    }

	    public void setRespMessage(String respMessage) {
	        this.respMessage = respMessage;
	    }

	    public String getSpRespCode() {
	        return spRespCode;
	    }

	    public void setSpRespCode(String spRespCode) {
	        this.spRespCode = spRespCode;
	    }

	    public String getSpRespMessage() {
	        return spRespMessage;
	    }

	    public void setSpRespMessage(String spRespMessage) {
	        this.spRespMessage = spRespMessage;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    
	    
		public String getTransactionId() {
			return TransactionId;
		}

		public void setTransactionId(String transactionId) {
			TransactionId = transactionId;
		}

		
		
		public String getUtr_no() {
			return utr_no;
		}






		public void setUtr_no(String utr_no) {
			this.utr_no = utr_no;
		}






		@Override
		public String toString() {
			return "PaymentRequestList [id=" + id + ", payReqId=" + payReqId + ", TransactionId=" + TransactionId
					+ ", reqDate=" + reqDate + ", clientRefNo=" + clientRefNo + ", txnMode=" + txnMode
					+ ", accountNumber=" + accountNumber + ", accountIfsc=" + accountIfsc + ", bankName=" + bankName
					+ ", accountHolderName=" + accountHolderName + ", beneficiaryName=" + beneficiaryName + ", vpa="
					+ vpa + ", txnAmount=" + txnAmount + ", utr_no=" + utr_no + ", adf1=" + adf1 + ", adf2=" + adf2
					+ ", adf3=" + adf3 + ", adf4=" + adf4 + ", adf5=" + adf5 + ", isBeneficiary=" + isBeneficiary
					+ ", beneficiaryCode=" + beneficiaryCode + ", respCode=" + respCode + ", respMessage=" + respMessage
					+ ", spRespCode=" + spRespCode + ", spRespMessage=" + spRespMessage + ", status=" + status + "]";
		}






		






	
}
