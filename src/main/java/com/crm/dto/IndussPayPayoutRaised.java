package com.crm.dto;

import java.lang.reflect.Field;
import java.util.List;

import com.google.gson.Gson;

public class IndussPayPayoutRaised {

	private String type;
	private String description;

	private List<PaymentList> paymentRequests;

	public List<PaymentList> getPaymentRequests() {
		return paymentRequests;
	}

	public void setPaymentRequests(List<PaymentList> paymentRequests) {
		this.paymentRequests = paymentRequests;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public class PaymentList {
		private String amount;
		private String CustomerId; // provide by rewardoo
		private String ClientRefNo;
		private String txnMode;
		private String account_number;
		private String name;

		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public String getCustomerId() {
			return CustomerId;
		}

		public void setCustomerId(String customerId) {
			CustomerId = customerId;
		}

		public String getClientRefNo() {
			return ClientRefNo;
		}

		public void setClientRefNo(String clientRefNo) {
			ClientRefNo = clientRefNo;
		}

		public String getTxnMode() {
			return txnMode;
		}

		public void setTxnMode(String txnMode) {
			this.txnMode = txnMode;
		}

		public String getAccount_number() {
			return account_number;
		}

		public void setAccount_number(String account_number) {
			this.account_number = account_number;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			name = name;
		}

		
	}
	public String checkSumValidation() {
		StringBuilder sb = new StringBuilder();
		Gson gson = new Gson();
		Class<?> thisClass = null;
		try {
			thisClass = Class.forName(this.getClass().getName());
			Field[] aClassFields = thisClass.getDeclaredFields();
			for (Field f : aClassFields) {
				if (!f.getName().equalsIgnoreCase("signature")) {
					if (f.getName().equalsIgnoreCase("paymentRequests")) {
						sb.append(gson.toJson(paymentRequests).replaceAll("?:[^a-zA-Z0-9]|\\|", ""));
					} else {
						if (f.get(this) != null) {
							sb.append(f.get(this));
						}
					}
				}
			}
			return sb.toString();

		} catch (Exception e) {

		}
		return sb.toString();

	}
}
