package com.crm.dto;

import java.math.BigInteger;

public class lBPSDto {
	
	private BigInteger id;
	private String payment_Link_Id;
	private String invoice_Number;
	private String isDeleted;
	private String status;
	private String link;
	private String customer_Name;
	private String validity;
	private String amount;
	private String DateTime;
	
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getPayment_Link_Id() {
		return payment_Link_Id;
	}
	public void setPayment_Link_Id(String payment_Link_Id) {
		this.payment_Link_Id = payment_Link_Id;
	}
	public String getInvoice_Number() {
		return invoice_Number;
	}
	public void setInvoice_Number(String invoice_Number) {
		this.invoice_Number = invoice_Number;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCustomer_Name() {
		return customer_Name;
	}
	public void setCustomer_Name(String customer_Name) {
		this.customer_Name = customer_Name;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	
	
	

	
	
}
