package com.crm.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_mstuser")
public class User {
	
	@Id
	@Column(name = "USERID")
	private String userId;
	
	@Column(name="GROUPID")
	private Integer groupId;
	
	@Column(name = "ROLEID")
	private Integer roleId;
	
	@Column(name = "FullName")
	private String fullName;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column (name = "Created_On")
	private Date createdOn;
	
	@Column(name = "Created_By")
	private String createdBy;
	
	@Column(name="Is_Deleted")
	private Character isDeleted;
	
	@Column(name ="ContactNo")
	private String contactNumber;
	
	@Column(name="EmailId")
	private String emailId;
	
	@Column(name="MerchantId")
	private String merchantId;
	
	@Column(name="blocked")
	private Boolean blocked;
	
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", groupId=" + groupId + ", roleId=" + roleId + ", fullName=" + fullName
				+ ", password=" + password + ", isActive=" + isActive + ", createdOn=" + createdOn + ", createdBy="
				+ createdBy + ", isDeleted=" + isDeleted + ", contactNumber=" + contactNumber + ", emailId=" + emailId
				+ ", merchantId=" + merchantId + ", blocked=" + blocked + "]";
	}
	

}
