package com.crm.dto;

import java.math.BigInteger;
import java.util.Arrays;

public class BusinessTypeDto {
	
	private int businessTypeId;
	private String businessType;
	private int docid;
	private String document;
	private String documentD;
	private String docname;
	private String docpath;
	private String docType;
	private BigInteger kycId;
	private String[] documentDescription;
	private String allow_wavier;
	private String wavier_date;
	private String wavier_remark;
	private String Type;
	private String subtext;

	public int getBusinessTypeId() {
		return businessTypeId;
	}
	public void setBusinessTypeId(int businessTypeId) {
		this.businessTypeId = businessTypeId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	
	
	public String[] getDocumentDescription() {
		return documentDescription;
	}
	public void setDocumentDescription(String[] documentDescription) {
		this.documentDescription = documentDescription;
	}
	public BigInteger getKycId() {
		return kycId;
	}
	public void setKycId(BigInteger kycId) {
		this.kycId = kycId;
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
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	
	public String getAllow_wavier() {
		return allow_wavier;
	}
	public void setAllow_wavier(String allow_wavier) {
		this.allow_wavier = allow_wavier;
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
	
	
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	@Override
	public String toString() {
		return "BusinessTypeDto [businessTypeId=" + businessTypeId + ", businessType=" + businessType + ", docid="
				+ docid + ", document=" + document + ", docname=" + docname + ", docpath=" + docpath + ", docType="
				+ docType + ", kycId=" + kycId + ", documentDescription=" + Arrays.toString(documentDescription)
				+ ", allow_wavier=" + allow_wavier + ", wavier_date=" + wavier_date + ", wavier_remark=" + wavier_remark
				+ ", Type=" + Type + ", subtext=" + subtext + ", getBusinessTypeId()=" + getBusinessTypeId()
				+ ", getBusinessType()=" + getBusinessType() + ", getDocid()=" + getDocid() + ", getDocument()="
				+ getDocument() + ", getDocumentDescription()=" + Arrays.toString(getDocumentDescription())
				+ ", getKycId()=" + getKycId() + ", getDocname()=" + getDocname() + ", getDocpath()=" + getDocpath()
				+ ", getDocType()=" + getDocType() + ", getAllow_wavier()=" + getAllow_wavier() + ", getWavier_date()="
				+ getWavier_date() + ", getWavier_remark()=" + getWavier_remark() + ", getSubtext()=" + getSubtext()
				+ ", getType()=" + getType() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
