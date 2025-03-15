package com.crm.dto;

public class BulkCsvMerMdrDto {
	
	private String merchant_id;
	private String sp_id;
	private String bank_id;
	private String instrument_id;
	private String min_amt;
	private String max_amt;
	private String mdr_type;
	private String aggr_mdr;
	private String reseller_mdr;
	private String base_rate;
	private String min_base_rate;
	private String max_base_rate;
	private String min_mdr;
	private String max_mdr;
	private String mid;
	private String tid;
	private String channel_id;
	private String start_date;
	private String end_date;
	private String prefrences;
	private String surcharge;
	private String payout;
	private String card_variant_name;
	private String network;
	private String instrument_brand;
	private String bank_mdr_type;
	private String min_reseller_mdr;
	private String max_reseller_mdr;
	private String reseller_mdr_type;
	private String payout_escrow;
	private String sr_no;
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getSp_id() {
		return sp_id;
	}
	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getInstrument_id() {
		return instrument_id;
	}
	public void setInstrument_id(String instrument_id) {
		this.instrument_id = instrument_id;
	}
	public String getMin_amt() {
		return min_amt;
	}
	public void setMin_amt(String min_amt) {
		this.min_amt = min_amt;
	}
	public String getMax_amt() {
		return max_amt;
	}
	public void setMax_amt(String max_amt) {
		this.max_amt = max_amt;
	}
	public String getMdr_type() {
		return mdr_type;
	}
	public void setMdr_type(String mdr_type) {
		this.mdr_type = mdr_type;
	}
	public String getAggr_mdr() {
		return aggr_mdr;
	}
	public void setAggr_mdr(String aggr_mdr) {
		this.aggr_mdr = aggr_mdr;
	}
	public String getReseller_mdr() {
		return reseller_mdr;
	}
	public void setReseller_mdr(String reseller_mdr) {
		this.reseller_mdr = reseller_mdr;
	}
	public String getBase_rate() {
		return base_rate;
	}
	public void setBase_rate(String base_rate) {
		this.base_rate = base_rate;
	}
	public String getMin_base_rate() {
		return min_base_rate;
	}
	public void setMin_base_rate(String min_base_rate) {
		this.min_base_rate = min_base_rate;
	}
	public String getMax_base_rate() {
		return max_base_rate;
	}
	public void setMax_base_rate(String max_base_rate) {
		this.max_base_rate = max_base_rate;
	}
	public String getMin_mdr() {
		return min_mdr;
	}
	public void setMin_mdr(String min_mdr) {
		this.min_mdr = min_mdr;
	}
	public String getMax_mdr() {
		return max_mdr;
	}
	public void setMax_mdr(String max_mdr) {
		this.max_mdr = max_mdr;
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
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getPrefrences() {
		return prefrences;
	}
	public void setPrefrences(String prefrences) {
		this.prefrences = prefrences;
	}
	public String getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}
	public String getPayout() {
		return payout;
	}
	public void setPayout(String payout) {
		this.payout = payout;
	}
	public String getCard_variant_name() {
		return card_variant_name;
	}
	public void setCard_variant_name(String card_variant_name) {
		this.card_variant_name = card_variant_name;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getInstrument_brand() {
		return instrument_brand;
	}
	public void setInstrument_brand(String instrument_brand) {
		this.instrument_brand = instrument_brand;
	}
	public String getBank_mdr_type() {
		return bank_mdr_type;
	}
	public void setBank_mdr_type(String bank_mdr_type) {
		this.bank_mdr_type = bank_mdr_type;
	}
	public String getMin_reseller_mdr() {
		return min_reseller_mdr;
	}
	public void setMin_reseller_mdr(String min_reseller_mdr) {
		this.min_reseller_mdr = min_reseller_mdr;
	}
	public String getMax_reseller_mdr() {
		return max_reseller_mdr;
	}
	public void setMax_reseller_mdr(String max_reseller_mdr) {
		this.max_reseller_mdr = max_reseller_mdr;
	}
	public String getReseller_mdr_type() {
		return reseller_mdr_type;
	}
	public void setReseller_mdr_type(String reseller_mdr_type) {
		this.reseller_mdr_type = reseller_mdr_type;
	}
	
	public String getSr_no() {
		return sr_no;
	}
	public void setSr_no(String sr_no) {
		this.sr_no = sr_no;
	}
	
	
	public String getPayout_escrow() {
		return payout_escrow;
	}
	public void setPayout_escrow(String payout_escrow) {
		this.payout_escrow = payout_escrow;
	}
	public BulkCsvMerMdrDto(String merchant_id, String sp_id, String bank_id, String instrument_id, String min_amt,
			String max_amt, String mdr_type, String aggr_mdr, String reseller_mdr, String base_rate,
			String min_base_rate, String max_base_rate, String min_mdr, String max_mdr, String mid, String tid,
			String channel_id, String start_date, String end_date, String prefrences, String surcharge, String payout,
			String card_variant_name, String network, String instrument_brand, String bank_mdr_type,
			String min_reseller_mdr, String max_reseller_mdr, String reseller_mdr_type, String payout_escrow, String sr_no) {
		super();
		this.merchant_id = merchant_id;
		this.sp_id = sp_id;
		this.bank_id = bank_id;
		this.instrument_id = instrument_id;
		this.min_amt = min_amt;
		this.max_amt = max_amt;
		this.mdr_type = mdr_type;
		this.aggr_mdr = aggr_mdr;
		this.reseller_mdr = reseller_mdr;
		this.base_rate = base_rate;
		this.min_base_rate = min_base_rate;
		this.max_base_rate = max_base_rate;
		this.min_mdr = min_mdr;
		this.max_mdr = max_mdr;
		this.mid = mid;
		this.tid = tid;
		this.channel_id = channel_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.prefrences = prefrences;
		this.surcharge = surcharge;
		this.payout = payout;
		this.card_variant_name = card_variant_name;
		this.network = network;
		this.instrument_brand = instrument_brand;
		this.bank_mdr_type = bank_mdr_type;
		this.min_reseller_mdr = min_reseller_mdr;
		this.max_reseller_mdr = max_reseller_mdr;
		this.reseller_mdr_type = reseller_mdr_type;
		this.payout_escrow= payout_escrow;
		this.sr_no = sr_no;
	}
	
}
