package com.crm.dto;

import java.util.List;

public class payout_request {
	  private String type;
	    private String description;
	    private String AuthID;
	    private List<PaymentRequest> paymentRequests;

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

	    public List<PaymentRequest> getPaymentRequests() {
	        return paymentRequests;
	    }

	    public void setPaymentRequests(List<PaymentRequest> paymentRequests) {
	        this.paymentRequests = paymentRequests;
	    }
	    
	    
	    
	    

	   

		


		public String getAuthID() {
			return AuthID;
		}

		public void setAuthID(String authID) {
			AuthID = authID;
		}

		@Override
		public String toString() {
			return "payout_request [type=" + type + ", description=" + description + ", merchantId=" + AuthID
					+ ", paymentRequests=" + paymentRequests + "]";
		}



		public static class PaymentRequest {
	        private String amount;
	        private String ClientTxnId;
	        private String txnMode;
	        private String account_number;
	        private String account_Ifsc;
	        private String bank_name;
	        private String account_holder_name;
	        private String beneficiary_name;
	        private String vpa;
	        private String adf1;
	        private String adf2;
	        private String adf3;
	        private String adf4;
	        private String adf5;

	        public String getAmount() {
	            return amount;
	        }

	        public void setAmount(String amount) {
	            this.amount = amount;
	        }

	       

	        

			public String getClientTxnId() {
				return ClientTxnId;
			}

			public void setClientTxnId(String clientTxnId) {
				ClientTxnId = clientTxnId;
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

			public String getAccount_Ifsc() {
				return account_Ifsc;
			}

			public void setAccount_Ifsc(String account_Ifsc) {
				this.account_Ifsc = account_Ifsc;
			}

			public String getBank_name() {
				return bank_name;
			}

			public void setBank_name(String bank_name) {
				this.bank_name = bank_name;
			}

			public String getAccount_holder_name() {
				return account_holder_name;
			}

			public void setAccount_holder_name(String account_holder_name) {
				this.account_holder_name = account_holder_name;
			}

			public String getBeneficiary_name() {
				return beneficiary_name;
			}

			public void setBeneficiary_name(String beneficiary_name) {
				this.beneficiary_name = beneficiary_name;
			}

			public String getVpa() {
	            return vpa;
	        }

	        public void setVpa(String vpa) {
	            this.vpa = vpa;
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

			public void setAdf2(String adf2) {
				this.adf2 = adf2;
			}

			@Override
			public String toString() {
				return "PaymentRequest [amount=" + amount + ", ClientTxnId=" + ClientTxnId + ", txnMode=" + txnMode
						+ ", account_number=" + account_number + ", account_Ifsc=" + account_Ifsc + ", bank_name="
						+ bank_name + ", account_holder_name=" + account_holder_name + ", beneficiary_name="
						+ beneficiary_name + ", vpa=" + vpa + ", adf1=" + adf1 + ", adf2=" + adf2 + ", adf3=" + adf3
						+ ", adf4=" + adf4 + ", adf5=" + adf5 + "]";
			}

			
	        
	        
	    }
}
