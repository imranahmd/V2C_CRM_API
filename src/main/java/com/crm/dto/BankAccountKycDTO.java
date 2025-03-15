package com.crm.dto;



import java.util.List;

import com.crm.model.BeneficiaryDetails;

public class BankAccountKycDTO {

	private String consent;
	  
	private String ifsc;
	private String accountNumber;
	
    private String reference_id;
    private String purpose_message;
    private String transfer_amount;


    private BeneficiaryDetails beneficiary_details;

    public BankAccountKycDTO() {
    }


    public String getReference_id() {
        return reference_id;
    }

    public void setReference_id(String reference_id) {
        this.reference_id = reference_id;
    }

    public String getPurpose_message() {
        return purpose_message;
    }

    public void setPurpose_message(String purpose_message) {
        this.purpose_message = purpose_message;
    }

    public String getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(String transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public BeneficiaryDetails getBeneficiary_details() {
        return beneficiary_details;
    }

    public void setBeneficiary_details(BeneficiaryDetails beneficiary_details) {
        this.beneficiary_details = beneficiary_details;
    }
    public String getConsent() {
		return consent;
	}


	public void setConsent(String consent) {
		this.consent = consent;
	}


	public String getIfsc() {
		return ifsc;
	}


	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

    @Override
	public String toString() {
		return "BankAccountKycDTO [consent=" + consent + ", ifsc=" + ifsc + ", accountNumber=" + accountNumber
				+ ", reference_id=" + reference_id + ", purpose_message=" + purpose_message + ", transfer_amount="
				+ transfer_amount + ", beneficiary_details=" + beneficiary_details + "]";
	}
}
