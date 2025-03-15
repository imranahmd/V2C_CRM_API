package com.crm.dto;

public class MerchantDocDto {
	
	private int id;
	private String docName;
	private String docpath;
	private String docType;
	private String merchantId;
	private String modifiedBy;
	private String modifiedOn;
	private String updatedBy;
	private String updateOn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocpath() {
		return docpath;
	}
	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
	@Override
	public String toString() {
		return "MerchantDocDto [id=" + id + ", docName=" + docName + ", docpath=" + docpath + ", docType=" + docType
				+ ", merchantId=" + merchantId + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
				+ ", updatedBy=" + updatedBy + ", updateOn=" + updateOn + ", getId()=" + getId() + ", getDocName()="
				+ getDocName() + ", getDocpath()=" + getDocpath() + ", getDocType()=" + getDocType()
				+ ", getMerchantId()=" + getMerchantId() + ", getModifiedBy()=" + getModifiedBy() + ", getModifiedOn()="
				+ getModifiedOn() + ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdateOn()=" + getUpdateOn()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
