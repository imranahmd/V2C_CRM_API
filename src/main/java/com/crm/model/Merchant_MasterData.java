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
@Table(name = "merchant_master_data")
public class Merchant_MasterData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name= "Mid")
	private String mid;

	@Column(name = "risk_data")
	private String RiskData;
	
	@Column(name = "Data1")
	private String Data1;
	
	@Column(name = "MDR")
	private String MDR;
	
	@Column(name = "mcc_code")
	private String mcc_code;
	
	@Column(name = "risk_type")
	private String risk_type;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getRiskData() {
		return RiskData;
	}

	public void setRiskData(String riskData) {
		RiskData = riskData;
	}

	public String getData1() {
		return Data1;
	}

	public void setData1(String data1) {
		Data1 = data1;
	}

	public String getMDR() {
		return MDR;
	}

	public void setMDR(String mDR) {
		MDR = mDR;
	}

	public String getMcc_code() {
		return mcc_code;
	}

	public void setMcc_code(String mcc_code) {
		this.mcc_code = mcc_code;
	}

	public String getRisk_type() {
		return risk_type;
	}

	public void setRisk_type(String risk_type) {
		this.risk_type = risk_type;
	}
	

	
	
}
