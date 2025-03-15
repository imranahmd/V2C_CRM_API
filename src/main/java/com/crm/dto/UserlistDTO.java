package com.crm.dto;

public class UserlistDTO {

	
	private String USERID ;
	private String FullName;
	private String ROLEID;
	private String ROLENAME;
	private String password;
	private String email_id;
	private String ContactNo;
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String rOLEID) {
		ROLEID = rOLEID;
	}
	public String getROLENAME() {
		return ROLENAME;
	}
	public void setROLENAME(String rOLENAME) {
		ROLENAME = rOLENAME;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getContactNo() {
		return ContactNo;
	}
	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}
	
	
	
	
}
