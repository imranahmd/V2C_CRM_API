package com.crm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Component;

@Component
public class UserRequest {

	String username;
	String password;
	String USERID;
	String GROUPID;
	String ROLEID;
	String FullName;
	String ContactNo;
	String EmailId;
	String CurrentDate;
	
	
String menus;	 
	 







	public String getCurrentDate() {
	return CurrentDate;
}


public void setCurrentDate(String currentDate) {
	CurrentDate = currentDate;
}


	public UserRequest() {
	}
	
	
	public String getMenus() {
		return menus;
	}


	public void setMenus(String menus) {
		this.menus = menus;
	}


	public UserRequest(String USERID, String password) {
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public String getGROUPID() {
		return GROUPID;
	}
	public void setGROUPID(String gROUPID) {
		GROUPID = gROUPID;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String rOLEID) {
		ROLEID = rOLEID;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getContactNo() {
		return ContactNo;
	}
	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}
	public String getEmailId() {
		return EmailId;
	}
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	@Override
	public String toString() {
		return "UserRequest [username=" + username + ", password=" + password + ", USERID=" + USERID + ", GROUPID="
				+ GROUPID + ", ROLEID=" + ROLEID + ", FullName=" + FullName + ", ContactNo=" + ContactNo + ", EmailId="
				+ EmailId + "]";
	}



	
	
	
	
	
	
}

