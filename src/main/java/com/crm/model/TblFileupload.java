package com.crm.model;

import java.sql.Timestamp;

public class TblFileupload {
	private Long fileId;
	private String fileName;
	private Long fileConfigId;
	private String fileType;
	private Timestamp uploadedOn;
	private String uploadedBy;
	private Timestamp modifiedOn;
	private String modifiedBy;
	private String isDeleted;
	private String statusCode;
	private Integer serviceId;
	
	private Integer totalRecordsInFile;
	private Integer noOfBadRecords;
	private String fileUploadPath;
	private String badRecordsFileName;
	private String badRecordsFilePath;
	public Long getFileId() {
		return this.fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileConfigId() {
		return this.fileConfigId;
	}

	public void setFileConfigId(Long fileConfigId) {
		this.fileConfigId = fileConfigId;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Timestamp getUploadedOn() {
		return this.uploadedOn;
	}

	public void setUploadedOn(Timestamp uploadedOn) {
		this.uploadedOn = uploadedOn;
	}

	public String getUploadedBy() {
		return this.uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Timestamp getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getTotalRecordsInFile() {
		return totalRecordsInFile;
	}

	public void setTotalRecordsInFile(Integer totalRecordsInFile) {
		this.totalRecordsInFile = totalRecordsInFile;
	}

	public Integer getNoOfBadRecords() {
		return noOfBadRecords;
	}

	public void setNoOfBadRecords(Integer noOfBadRecords) {
		this.noOfBadRecords = noOfBadRecords;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getBadRecordsFileName() {
		return badRecordsFileName;
	}

	public void setBadRecordsFileName(String badRecordsFileName) {
		this.badRecordsFileName = badRecordsFileName;
	}

	public String getBadRecordsFilePath() {
		return badRecordsFilePath;
	}

	public void setBadRecordsFilePath(String badRecordsFilePath) {
		this.badRecordsFilePath = badRecordsFilePath;
	}
}
