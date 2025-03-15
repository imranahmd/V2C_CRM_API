package com.crm.dto;

public class UploadDocDetailsDto {

	private String documentId;	//": "2",
	private String docname;	//": 
	private String docpath;	//": 
	private String doctype;	//": "pdf",
	private int docid;	//": 7,
	private int kycid;	//": 0,
	private String allow_Wavier;	//": "Y",
	private String wavier_date;	//": "2023-01-04",
	private String wavier_remark;	//": "asdasdas"
	
	public UploadDocDetailsDto() {
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getDocpath() {
		return docpath;
	}

	public void setDocpath(String docpath) {
		this.docpath = docpath;
	}

	public String getDoctype() {
		return doctype;
	}

	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public int getKycid() {
		return kycid;
	}

	public void setKycid(int kycid) {
		this.kycid = kycid;
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
