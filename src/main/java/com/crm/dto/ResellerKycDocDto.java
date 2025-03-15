package com.crm.dto;

public class ResellerKycDocDto {
	
	private int id;
	private String docName;
	private String docpath;
	private String docType;
	private String resellerId;
	private String modifiedBy;
	private String modifiedOn;
	private String updatedBy;
	private String updateOn;
	private int documentId;
	private int docId;
	private String allow_Wavier;
	private String wavier_date;
	private String wavier_remark;
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
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
	public String getResellerId() {
		return resellerId;
	}
	public void setResellerId(String resellerId) {
		this.resellerId = resellerId;
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
	public String getAllow_Wavier() {
		return allow_Wavier;
	}
	public void setAllow_Wavier(String allow_Wavier) {
		this.allow_Wavier = allow_Wavier;
	}
	public String getWavier_date() {
		return wavier_date;
	}
	public void setWavier_date(String wavier_date) {
		this.wavier_date = wavier_date;
	}
	public String getWavier_remark() {
		return wavier_remark;
	}
	public void setWavier_remark(String wavier_remark) {
		this.wavier_remark = wavier_remark;
	}
	


}
