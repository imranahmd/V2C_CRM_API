package com.crm.dto;

import java.util.List;

public class PayoutBulkStatusRequest {

	private String AuthID;
	private List<Data> RequestData;
	
	
	
	public String getAuthID() {
		return AuthID;
	}




	public void setAuthID(String authID) {
		AuthID = authID;
	}

	

	
	


	




	









	public List<Data> getRequestData() {
		return RequestData;
	}




	public void setRequestData(List<Data> requestData) {
		RequestData = requestData;
	}




	public List<Data> getRequesData() {
		return RequestData;
	}




	public void setRequesData(List<Data> requesData) {
		RequestData = requesData;
	}



















	public class Data{
		private String ClientTxnId;

		public String getClientTxnId() {
			return ClientTxnId;
		}

		public void setClientTxnId(String clientTxnId) {
			ClientTxnId = clientTxnId;
		}

	
		
		
	}
	
}
