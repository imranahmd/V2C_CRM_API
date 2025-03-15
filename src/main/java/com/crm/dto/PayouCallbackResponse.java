package com.crm.dto;

import java.util.List;

public class PayouCallbackResponse {

	private String signature;
	private List<Data2> data;
	
	
	
	public String getSignature() {
		return signature;
	}



	public void setSignature(String signature) {
		this.signature = signature;
	}



	public List<Data2> getData() {
		return data;
	}



	public void setData(List<Data2> data) {
		this.data = data;
	}



	public class Data2 {
		private String transactionStatus;
		private String utr;
		private String clientTxnId;
		private String statusDescription;
		private String batchId;
		public String getTransactionStatus() {
			return transactionStatus;
		}
		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
		public String getUtr() {
			return utr;
		}
		public void setUtr(String utr) {
			this.utr = utr;
		}
		public String getClientTxnId() {
			return clientTxnId;
		}
		public void setClientTxnId(String clientTxnId) {
			this.clientTxnId = clientTxnId;
		}
		public String getStatusDescription() {
			return statusDescription;
		}
		public void setStatusDescription(String statusDescription) {
			this.statusDescription = statusDescription;
		}
		public String getBatchId() {
			return batchId;
		}
		public void setBatchId(String batchId) {
			this.batchId = batchId;
		}
		
		
		
		
	}
}
