package com.crm.dto;

public class AdditionContact {

	private String iName;
	private String contact_number;
	private String designation;
	private String email_id;
	
	

	public String getiName() {
		return iName;
	}
	public void setiName(String iName) {
		this.iName = iName;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	@Override
	public String toString() {
		return "AdditionContact [iName=" + iName + ", contact_number=" + contact_number + ", designation=" + designation
				+ ", email_id=" + email_id + "]";
	}
	
	
	
	
}
