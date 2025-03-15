package com.crm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Table(name= "tbl_merchant_mdr")
@Entity
public class MerchantMdr implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
		private Integer id;
	
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
		private Double aggrMdr;
		// private Double bankMdr;
	
	@Column(name = "reseller_mdr")
		private Double resellerMdr;
	
	
	@Column(name = "start_date")
		private String startDate;
	
	
	@Column(name = "end_date")
		private String endDate;
	
	
	@Column(name = "rodt")
		private String rodt;
	
	
	@Column(name = "min_amt")
		private Double minAmt;
	

	@Column(name = "max_amt")
		private Long maxAmt;
	
	
	@Column(name = "mdr_type")
		private String mdrType;
		
	
	@Column(name = "min_mdr")
		private Double minMdr;
	
	
	@Column(name = "max_mdr")
		private Double maxMdr;
	
	
	@Column(name = "base_rate")
		private Double baseRate;
	
	
	@Column(name = "min_base_rate")
		private Double minBaseRate;
		
	
	@Column(name = "max_base_rate")
		private Double maxBaseRate;
	
	
	@Column(name = "sp_id")
		private Integer spId;
	
	
	@Column(name = "prefrences")
		private String prefrences;
	
	
	@Column(name = "payout")
		private Integer payout;
	
	
	@Column(name = "surcharge")
		private Integer surcharge;
	
	
	@Column(name = "card_variant_name")
		private String cardVariantName;
	
	
	@Column(name = "network")
		private String network;
	
	@Column(name = "instrument_brand")
	private Integer instrumentBrand;
	
	
	


	@Column(name = "bank_mdr_type")
		private String bankMdrType;
	
	
	@Column(name = "min_reseller_mdr")
		private String minResellerMdr;
	
	
	@Column(name = "max_reseller_mdr")
		private String maxResellerMdr;
	
	@Column(name = "reseller_mdr_type")
	private String resellerMdrType;
		
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Double getAggrMdr() {
		return aggrMdr;
	}

	public void setAggrMdr(Double aggrMdr) {
		this.aggrMdr = aggrMdr;
	}

	public Double getResellerMdr() {
		return resellerMdr;
	}

	public void setResellerMdr(Double resellerMdr) {
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

	public Double getMinAmt() {
		return minAmt;
	}

	public void setMinAmt(Double minAmt) {
		this.minAmt = minAmt;
	}

	public Long getMaxAmt() {
		return maxAmt;
	}

	public void setMaxAmt(Long maxAmt) {
		this.maxAmt = maxAmt;
	}

	public String getMdrType() {
		return mdrType;
	}

	public void setMdrType(String mdrType) {
		this.mdrType = mdrType;
	}

	public Double getMinMdr() {
		return minMdr;
	}

	public void setMinMdr(Double minMdr) {
		this.minMdr = minMdr;
	}

	public Double getMaxMdr() {
		return maxMdr;
	}

	public void setMaxMdr(Double maxMdr) {
		this.maxMdr = maxMdr;
	}

	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}

	public Double getMinBaseRate() {
		return minBaseRate;
	}

	public void setMinBaseRate(Double minBaseRate) {
		this.minBaseRate = minBaseRate;
	}

	public Double getMaxBaseRate() {
		return maxBaseRate;
	}

	public void setMaxBaseRate(Double maxBaseRate) {
		this.maxBaseRate = maxBaseRate;
	}

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public String getPrefrences() {
		return prefrences;
	}

	public void setPrefrences(String prefrences) {
		this.prefrences = prefrences;
	}

	public Integer getPayout() {
		return payout;
	}

	public void setPayout(Integer payout) {
		this.payout = payout;
	}

	public Integer getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Integer surcharge) {
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

	public Integer getInstrumentBrand() {
		return instrumentBrand;
	}

	public void setInstrumentBrand(Integer instrumentBrand) {
		this.instrumentBrand = instrumentBrand;
	}
	
	public String getResellerMdrType() {
		return resellerMdrType;
	}

	public void setResellerMdrType(String resellerMdrType) {
		this.resellerMdrType = resellerMdrType;
	}

	@Override
	public String toString() {
		return "MerchantMdr [id=" + id + ", instrumentId=" + instrumentId + ", channelId=" + channelId + ", bankId="
				+ bankId + ", merchantId=" + merchantId + ", mid=" + mid + ", tid=" + tid + ", aggrMdr=" + aggrMdr
				+ ", resellerMdr=" + resellerMdr + ", startDate=" + startDate + ", endDate=" + endDate + ", rodt="
				+ rodt + ", minAmt=" + minAmt + ", maxAmt=" + maxAmt + ", mdrType=" + mdrType + ", minMdr=" + minMdr
				+ ", maxMdr=" + maxMdr + ", baseRate=" + baseRate + ", minBaseRate=" + minBaseRate + ", maxBaseRate="
				+ maxBaseRate + ", spId=" + spId + ", prefrences=" + prefrences + ", payout=" + payout + ", surcharge="
				+ surcharge + ", cardVariantName=" + cardVariantName + ", network=" + network + ", instrumentBrand="
				+ instrumentBrand + ", bankMdrType=" + bankMdrType + ", minResellerMdr=" + minResellerMdr
				+ ", maxResellerMdr=" + maxResellerMdr + ", resellerMdrType=" + resellerMdrType + "]";
	}

	


	}

