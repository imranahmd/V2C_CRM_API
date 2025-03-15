package com.crm.dto;

import java.util.Date;

//@Data
//@Table(name= "tbl_invoicebulkuploadxlx")
//@Entity
public class bulkXlxInvoice {

	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "Id")
//	private Long id;
	
	
	//@Column(name = "invoiceNumber")
	
	
	private String invoiceNumber;
	
	//@Column(name = "amount")
	private String amount;
	
	//@Column(name = "validUpTo")
	private String validUpTo;
	
	//@Column(name = "remarks")
	private String remarks;
	
	//@Column(name = "name")
	private String name;	
	
	//@Column(name = "emailId")
	private String emailId;
		
	//@Column(name = "mobile")
	private long mobile;
			
	//@Column(name = "emailNotification")
	private String emailNotification;
	
	//@Column(name = "sMSNotification")
	private String sMSNotification;

	
	private int id;
	

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getsMSNotification() {
		return sMSNotification;
	}

	public void setsMSNotification(String sMSNotification) {
		this.sMSNotification = sMSNotification;
	}

	public String getValidUpTo() {
		return validUpTo;
	}

	public void setValidUpTo(String validUpTo) {
		this.validUpTo = validUpTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	
	
	
	
}
