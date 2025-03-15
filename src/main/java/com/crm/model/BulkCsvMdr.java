package com.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Table(name= "tbl_mdrbulkuploadcsv")
@Entity

public class BulkCsvMdr {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	
	@Column(name = "instrument_id")
	private String instrumentId;
	
	@Column(name = "channel_id")
	private String channelId;
	
	@Column(name = "bank_id")
	private String bankId;
	
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "mid")
	private String mid;	
	
	@Column(name = "tid")
	private String tid;
		
	@Column(name = "aggr_mdr")
	private String aggrMdr;
			
	@Column(name = "reseller_mdr")
	private String resellerMdr;	
	
	@Column(name = "start_date")
	private String startDate;	
	
	@Column(name = "end_date")
	private String endDate;	
	
	@Column(name = "rodt")
	private String rodt;	
	
	@Column(name = "min_amt")
	private String minAmt;	

	@Column(name = "max_amt")
	private String maxAmt;	
	
	@Column(name = "mdr_type")
	private String mdrType;		
	
	@Column(name = "min_mdr")
	private String minMdr;	
	
	@Column(name = "max_mdr")
	private String maxMdr;	
	
	@Column(name = "base_rate")
	private String baseRate;	
	
	@Column(name = "min_base_rate")
	private String minBaseRate;		
	
	@Column(name = "max_base_rate")
	private String maxBaseRate;	
	
	@Column(name = "sp_id")
	private String spId;	
	
	@Column(name = "prefrences")
	private String prefrences;	
	
	@Column(name = "payout")
	private String payout;	
	
	@Column(name = "surcharge")
	private String surcharge;	
	
	@Column(name = "card_variant_name")
	private String cardVariantName;	
	
	@Column(name = "network")
	private String network;
	
	@Column(name = "instrument_brand")
	private String instrumentBrand;

	@Column(name = "bank_mdr_type")
	private String bankMdrType;	
	
	@Column(name = "min_reseller_mdr")
	private String minResellerMdr;	
	
	@Column(name = "max_reseller_mdr")
	private String maxResellerMdr;
	
	@Column(name = "reseller_mdr_type")
	private String resellerMdrType;
	
	@Column(name = "Sr_no")
	private String SrNo;
	
	@Column(name = "upload_status")
	private String uploadStatus;
	
	@Column(name = "remarks")
	private String Remarks;
	
	@Column(name = "payout_escrow")
	private String payout_escrow;

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getAggrMdr() {
		return aggrMdr;
	}

	public void setAggrMdr(String aggrMdr) {
		this.aggrMdr = aggrMdr;
	}

	public String getResellerMdr() {
		return resellerMdr;
	}

	public void setResellerMdr(String resellerMdr) {
		this.resellerMdr = resellerMdr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRodt() {
		return rodt;
	}

	public void setRodt(String rodt) {
		this.rodt = rodt;
	}

	public String getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(String minAmt) {
		this.minAmt = minAmt;
	}

	public String getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(String maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getMdrType() {
		return mdrType;
	}

	public void setMdrType(String mdrType) {
		this.mdrType = mdrType;
	}

	public String getMinMdr() {
		return minMdr;
	}

	public void setMinMdr(String minMdr) {
		this.minMdr = minMdr;
	}

	public String getMaxMdr() {
		return maxMdr;
	}

	public void setMaxMdr(String maxMdr) {
		this.maxMdr = maxMdr;
	}

	public String getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}

	public String getMinBaseRate() {
		return minBaseRate;
	}

	public void setMinBaseRate(String minBaseRate) {
		this.minBaseRate = minBaseRate;
	}

	public String getMaxBaseRate() {
		return maxBaseRate;
	}

	public void setMaxBaseRate(String maxBaseRate) {
		this.maxBaseRate = maxBaseRate;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getPrefrences() {
		return prefrences;
	}

	public void setPrefrences(String prefrences) {
		this.prefrences = prefrences;
	}

	public String getPayout() {
		return payout;
	}

	public void setPayout(String payout) {
		this.payout = payout;
	}

	public String getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	public String getCardVariantName() {
		return cardVariantName;
	}

	public void setCardVariantName(String cardVariantName) {
		this.cardVariantName = cardVariantName;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getInstrumentBrand() {
		return instrumentBrand;
	}

	public void setInstrumentBrand(String instrumentBrand) {
		this.instrumentBrand = instrumentBrand;
	}

	public String getBankMdrType() {
		return bankMdrType;
	}

	public void setBankMdrType(String bankMdrType) {
		this.bankMdrType = bankMdrType;
	}

	public String getMinResellerMdr() {
		return minResellerMdr;
	}

	public void setMinResellerMdr(String minResellerMdr) {
		this.minResellerMdr = minResellerMdr;
	}

	public String getMaxResellerMdr() {
		return maxResellerMdr;
	}

	public void setMaxResellerMdr(String maxResellerMdr) {
		this.maxResellerMdr = maxResellerMdr;
	}

	public String getResellerMdrType() {
		return resellerMdrType;
	}

	public void setResellerMdrType(String resellerMdrType) {
		this.resellerMdrType = resellerMdrType;
	}

	public String getSrNo() {
		return SrNo;
	}

	public void setSrNo(String srNo) {
		SrNo = srNo;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getPayout_escrow() {
		return payout_escrow;
	}

	public void setPayout_escrow(String payout_escrow) {
		this.payout_escrow = payout_escrow;
	}

	@Override
	public String toString() {
		return "BulkCsvMdr [id=" + id + ", instrumentId=" + instrumentId + ", channelId=" + channelId + ", bankId="
				+ bankId + ", merchantId=" + merchantId + ", mid=" + mid + ", tid=" + tid + ", aggrMdr=" + aggrMdr
				+ ", resellerMdr=" + resellerMdr + ", startDate=" + startDate + ", endDate=" + endDate + ", rodt="
				+ rodt + ", minAmt=" + minAmt + ", maxAmt=" + maxAmt + ", mdrType=" + mdrType + ", minMdr=" + minMdr
				+ ", maxMdr=" + maxMdr + ", baseRate=" + baseRate + ", minBaseRate=" + minBaseRate + ", maxBaseRate="
				+ maxBaseRate + ", spId=" + spId + ", prefrences=" + prefrences + ", payout=" + payout + ", surcharge="
				+ surcharge + ", cardVariantName=" + cardVariantName + ", network=" + network + ", instrumentBrand="
				+ instrumentBrand + ", bankMdrType=" + bankMdrType + ", minResellerMdr=" + minResellerMdr
				+ ", maxResellerMdr=" + maxResellerMdr + ", resellerMdrType=" + resellerMdrType + ", SrNo=" + SrNo
				+ ", uploadStatus=" + uploadStatus + ", Remarks=" + Remarks + ", payout_escrow=" + payout_escrow
				+ ", getInstrumentId()=" + getInstrumentId() + ", getChannelId()=" + getChannelId() + ", getBankId()="
				+ getBankId() + ", getMerchantId()=" + getMerchantId() + ", getMid()=" + getMid() + ", getTid()="
				+ getTid() + ", getAggrMdr()=" + getAggrMdr() + ", getResellerMdr()=" + getResellerMdr()
				+ ", getStartDate()=" + getStartDate() + ", getEndDate()=" + getEndDate() + ", getRodt()=" + getRodt()
				+ ", getMinAmt()=" + getMinAmt() + ", getMaxAmt()=" + getMaxAmt() + ", getMdrType()=" + getMdrType()
				+ ", getMinMdr()=" + getMinMdr() + ", getMaxMdr()=" + getMaxMdr() + ", getBaseRate()=" + getBaseRate()
				+ ", getMinBaseRate()=" + getMinBaseRate() + ", getMaxBaseRate()=" + getMaxBaseRate() + ", getSpId()="
				+ getSpId() + ", getPrefrences()=" + getPrefrences() + ", getPayout()=" + getPayout()
				+ ", getSurcharge()=" + getSurcharge() + ", getCardVariantName()=" + getCardVariantName()
				+ ", getNetwork()=" + getNetwork() + ", getInstrumentBrand()=" + getInstrumentBrand()
				+ ", getBankMdrType()=" + getBankMdrType() + ", getMinResellerMdr()=" + getMinResellerMdr()
				+ ", getMaxResellerMdr()=" + getMaxResellerMdr() + ", getResellerMdrType()=" + getResellerMdrType()
				+ ", getSrNo()=" + getSrNo() + ", getUploadStatus()=" + getUploadStatus() + ", getRemarks()="
				+ getRemarks() + ", getId()=" + getId() + ", getPayout_escrow()=" + getPayout_escrow() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
