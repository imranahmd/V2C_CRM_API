package com.crm.model;

import jakarta.persistence.Table;

import org.hibernate.annotations.GenerationTime;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity

@Table(name="tbl_fileupload")
public class ReconRecord {
	
	@Id
	@Column(name="FileId", insertable = false, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int FileId;
		

	private String FileConfigId;
	
	private String ServiceId; 
	
	private String FileName;
	
	private String FileType;
	
	private String FileUploadPath;
	
	private String TotalRecordsInFile;
	
	private String NoOfBadRecords;
	
	private String BadRecordsFileName;
	
	private String BadRecordsFilePath;
	
	private String statusCode;
	
	private String UploadedOn;
	
	private String Modified_On;
	
	private String Modified_By;
	
	private String Version;

	
	private String Is_Deleted;
	
//	private int NoofException;
	private String NoofException;

	
	private String Exception;
	
	private String UploadedBy;

	public int getFileId() {
		return FileId;
	}

	public void setFileId(int fileId) {
		FileId = fileId;
	}

	public String getFileConfigId() {
		return FileConfigId;
	}

	public void setFileConfigId(String fileConfigId) {
		FileConfigId = fileConfigId;
	}

	public String getServiceId() {
		return ServiceId;
	}

	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileType() {
		return FileType;
	}

	public void setFileType(String fileType) {
		FileType = fileType;
	}

	public String getFileUploadPath() {
		return FileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		FileUploadPath = fileUploadPath;
	}

	public String getTotalRecordsInFile() {
		return TotalRecordsInFile;
	}

	public void setTotalRecordsInFile(String totalRecordsInFile) {
		TotalRecordsInFile = totalRecordsInFile;
	}

	public String getNoOfBadRecords() {
		return NoOfBadRecords;
	}

	public void setNoOfBadRecords(String noOfBadRecords) {
		NoOfBadRecords = noOfBadRecords;
	}

	public String getBadRecordsFileName() {
		return BadRecordsFileName;
	}

	public void setBadRecordsFileName(String badRecordsFileName) {
		BadRecordsFileName = badRecordsFileName;
	}

	public String getBadRecordsFilePath() {
		return BadRecordsFilePath;
	}

	public void setBadRecordsFilePath(String badRecordsFilePath) {
		BadRecordsFilePath = badRecordsFilePath;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getUploadedOn() {
		return UploadedOn;
	}

	public void setUploadedOn(String uploadedOn) {
		UploadedOn = uploadedOn;
	}

	public String getModified_On() {
		return Modified_On;
	}

	public void setModified_On(String modified_On) {
		Modified_On = modified_On;
	}

	public String getModified_By() {
		return Modified_By;
	}

	public void setModified_By(String modified_By) {
		Modified_By = modified_By;
	}

	public String getIs_Deleted() {
		return Is_Deleted;
	}

	public void setIs_Deleted(String is_Deleted) {
		Is_Deleted = is_Deleted;
	}

//	public int getNoofException() {
//		return NoofException;
//	}
//
//	public void setNoofException(int i) {
//		NoofException = i;
//	}

	public String getException() {
		return Exception;
	}

	public void setException(String i) {
		Exception = i;
	}

	public String getUploadedBy() {
		return UploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		UploadedBy = uploadedBy;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}


	public String getNoofException() {
		return NoofException;
	}

	public void setNoofException(String i) {
		NoofException = i;
	}
}
