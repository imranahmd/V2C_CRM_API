package com.crm.dto;

import java.util.List;

public class InduspayAddBeneResponse {

	
	private String status;
	private String message;
	private String timestamp;
	private List<ListAddedBene> data;
	
	
	
	
	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public String getTimestamp() {
		return timestamp;
	}




	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}




	public List<ListAddedBene> getData() {
		return data;
	}




	public void setData(List<ListAddedBene> data) {
		this.data = data;
	}




	@Override
	public String toString() {
		return "InduspayAddBeneResponse [status=" + status + ", message=" + message + ", timestamp=" + timestamp
				+ ", data=" + data + "]";
	}




	public class ListAddedBene{
		private String beneficiaryId;
		private String beneficiaryName;
		private String accountNumber;
		private String ifscCode;
		private String bankName;
		private String paymentStatus;
		private String status;
		private String name;
		private String email;
		private String mobileNumber;
		public String getBeneId() {
			return beneficiaryId;
		}
		public void setBeneId(String beneId) {
			this.beneficiaryId = beneId;
		}
		public String getBeneficiaryName() {
			return beneficiaryName;
		}
		public void setBeneficiaryName(String beneficiaryName) {
			this.beneficiaryName = beneficiaryName;
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
		public String getPaymentStatus() {
			return paymentStatus;
		}
		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getMobileNumber() {
			return mobileNumber;
		}
		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}
		@Override
		public String toString() {
			return "ListAddedBene [beneId=" + beneficiaryId + ", beneficiaryName=" + beneficiaryName + ", accountNumber="
					+ accountNumber + ", ifscCode=" + ifscCode + ", bankName=" + bankName + ", paymentStatus="
					+ paymentStatus + ", status=" + status + ", name=" + name + ", email=" + email + ", mobileNumber="
					+ mobileNumber + "]";
		}
		
		
		

	}
}
