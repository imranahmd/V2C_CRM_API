package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Table(name= "tbl_payrequest")
@Entity
public class Payrequest {

	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private int Id;
	
	@Column(name="MID")
	private String MID;
	
	@Column(name="pspid")
	private String pspid;
	
	@Column(name="PayReqId")
	private String PayReqId;
	
	@Column(name="RequestType")
	private String RequestType;
	
	@Column(name="Descriptions")
	private String Descriptions;
	
	@Column(name="RequestJson")
	private String RequestJson;
	
	@Column(name="ResCode")
	private String ResCode;
	
	@Column(name="ResMessage")
	private String ResMessage;
	
	@Column(name="Requestdate")
	private String Requestdate;
	
	@Column(name="CreatedBy")
	private String CreatedBy;
	
	@Column(name="status")
	private String status;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getPspid() {
		return pspid;
	}

	public void setPspid(String pspid) {
		this.pspid = pspid;
	}

	public String getPayReqId() {
		return PayReqId;
	}

	public void setPayReqId(String payReqId) {
		PayReqId = payReqId;
	}

	public String getRequestType() {
		return RequestType;
	}

	public void setRequestType(String requestType) {
		RequestType = requestType;
	}

	public String getDescriptions() {
		return Descriptions;
	}

	public void setDescriptions(String descriptions) {
		Descriptions = descriptions;
	}

	public String getRequestJson() {
		return RequestJson;
	}

	public void setRequestJson(String requestJson) {
		RequestJson = requestJson;
	}

	public String getResCode() {
		return ResCode;
	}

	public void setResCode(String resCode) {
		ResCode = resCode;
	}

	public String getResMessage() {
		return ResMessage;
	}

	public void setResMessage(String resMessage) {
		ResMessage = resMessage;
	}

	public String getRequestdate() {
		return Requestdate;
	}

	public void setRequestdate(String requestdate) {
		Requestdate = requestdate;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
