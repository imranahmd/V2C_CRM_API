package com.crm.dto;

import java.sql.Timestamp;

public class MerchantMdrDto 
{
	// Fields
	private Integer id;
	private String instrumentId;
	private String channelId;
	private String bankId;
	private String merchantId;
	private String mid;
	private String tid;
	private Double aggrMdr;
	// private Double bankMdr;
	private Double resellerMdr;
	private String startDate;
	private String endDate;
	private String rodt;
	private Double minAmt;
	private Long maxAmt;
	private String mdrType;
	
	private Double minMdr;
	private Double maxMdr;
	private Double baseRate;
	private Double minBaseRate;
	

	private Double maxBaseRate;
	private Integer spId;
	private String prefrences;
	private Integer payout;
	private Integer surcharge;
	private String cardVariantName;
	private String network;
	private String bankMdrType;
	private String minResellerMdr;
	private String maxResellerMdr;
	private int instrumentBrand;
	private String resellerMdrType;
	private String payout_escrow;
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
	public int getInstrumentBrand() {
		return instrumentBrand;
	}
	public void setInstrumentBrand(int instrumentBrand) {
		this.instrumentBrand = instrumentBrand;
	}
	public String getResellerMdrType() {
		return resellerMdrType;
	}
	public void setResellerMdrType(String resellerMdrType) {
		this.resellerMdrType = resellerMdrType;
	}
	
	
	public String getPayout_escrow() {
		return payout_escrow;
	}
	public void setPayout_escrow(String payout_escrow) {
		this.payout_escrow = payout_escrow;
	}
	public MerchantMdrDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	// Constructors



}