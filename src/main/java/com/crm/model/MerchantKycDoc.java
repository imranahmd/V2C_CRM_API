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
@Table(name = "kyc_upload_doc")
public class MerchantKycDoc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int id;
	
	@Column(name= "doc_name")
	private String docName;
	
	@Column(name= "path")
	private String docpath;
	
	@Column(name= "doc_type")
	private String docType;
	
	@Column(name= "merchant_id")
	private String merchantId;
	
	@Column(name= "modified_by")
	private String modifiedBy;
	
	@Column(name= "modified_on")
	private String modifiedOn;
	
	@Column(name= "updated_by")
	private String updatedBy;
	
	@Column(name= "updated_on")
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
		return "MerchantKycDoc [id=" + id + ", docName=" + docName + ", docpath=" + docpath + ", docType=" + docType
				+ ", merchantId=" + merchantId + ", modifiedBy=" + modifiedBy + ", modifiedOn=" + modifiedOn
				+ ", updatedBy=" + updatedBy + ", updateOn=" + updateOn + ", getId()=" + getId() + ", getDocName()="
				+ getDocName() + ", getDocpath()=" + getDocpath() + ", getDocType()=" + getDocType()
				+ ", getMerchantId()=" + getMerchantId() + ", getModifiedBy()=" + getModifiedBy() + ", getModifiedOn()="
				+ getModifiedOn() + ", getUpdatedBy()=" + getUpdatedBy() + ", getUpdateOn()=" + getUpdateOn()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
	

}
