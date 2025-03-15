package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_audit_tail")
public class AuditTail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "user_name")
	private	String userName;
	
	@Column(name = "ip_address")
	private String ipAddrss;
	
	@Column(name = "updated_Date")
	private String updatedDate;
	
	@Column(name = "action_Name")
	private String actionName;
	
	@Column(name = "audit_Data")
	private String auditData;
	
	@Column(name = "data_new")
	private String data_new;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIpAddrss() {
		return ipAddrss;
	}
	public void setIpAddrss(String ipAddrss) {
		this.ipAddrss = ipAddrss;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getAuditData() {
		return auditData;
	}
	public void setAuditData(String auditData) {
		this.auditData = auditData;
	}
	
	
	
	
	
	
	public String getData_new() {
		return data_new;
	}
	public void setData_new(String data_new) {
		this.data_new = data_new;
	}
//	@Override
//	public String toString() {
//		return "AuditTail [id=" + id + ", userName=" + userName + ", ipAddrss=" + ipAddrss + ", updatedDate="
//				+ updatedDate + ", actionName=" + actionName + ", auditData=" + auditData + "]";
//	}
	@Override
	public String toString() {
		return "AuditTail [id=" + id + ", userName=" + userName + ", ipAddrss=" + ipAddrss + ", updatedDate="
				+ updatedDate + ", actionName=" + actionName + ", auditData=" + auditData + ", data_new=" + data_new
				+ "]";
	}
	
	

}