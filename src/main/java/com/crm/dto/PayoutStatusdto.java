package com.crm.dto;

import java.sql.Date;

public class PayoutStatusdto {

private int id;
	
	private Date dateTime;

	private String transactionwisePayoutPath;
	
	private String merchantwisePayoutPath;
	
	private boolean status;

	private String payoutEscrow;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getTransactionwisePayoutPath() {
		return transactionwisePayoutPath;
	}

	public void setTransactionwisePayoutPath(String transactionwisePayoutPath) {
		this.transactionwisePayoutPath = transactionwisePayoutPath;
	}

	public String getMerchantwisePayoutPath() {
		return merchantwisePayoutPath;
	}

	public void setMerchantwisePayoutPath(String merchantwisePayoutPath) {
		this.merchantwisePayoutPath = merchantwisePayoutPath;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getPayoutEscrow() {
		return payoutEscrow;
	}

	public void setPayoutEscrow(String payoutEscrow) {
		this.payoutEscrow = payoutEscrow;
	}
	
	

	@Override
	public String toString() {
		return "PayoutStatus [id=" + id + ", dateTime=" + dateTime + ", transactionwisePayoutPath="
				+ transactionwisePayoutPath + ", merchantwisePayoutPath=" + merchantwisePayoutPath + ", status="
				+ status + ", payoutEscrow=" + payoutEscrow + "]";
	}
	
}

