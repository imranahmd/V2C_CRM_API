package com.crm.dto;

public class MerchantBasicSetupDto {

	private String MerchantId;
//	private String merReturnUrl;
	private String isAutoRefund;
	private String hours;
	private String minutes;
	private String isPushUrl;
	private String pushUrl;
	private String settlementCycle;
	private String merchantDashboardRefund;
	private String mdDisableRefundCc;
	private String mdDisableRefundDc;
	private String mdDisableRefundNb;
	private String imdDisableRefundUpi;
	private String imdDisableRefundWallet;
	private String refundApi;
	private String refundApiDisableCc;
	private String refundApiDisableDc;
	private String refundApiDisableNb;
	private String refundApiDisableUpi;
	private String refundApiDisableWallet;
	private String integrationType;
	private String isretryAllowed;
	private String ibpsEmailNotification;
	private String ibpsSmsNotification;
	private String ibpsMailReminder;
	private String Reporting_cycle;
	private String Upi_loader;
	private String upi_intent;
	private String upi_collect;
	
	private String static_QR;
	private String dynamic_QR;
	private String partnerBy;
	private String partnerBy_name;
	
	
	public String getMerchantId() {
		return MerchantId;
	}
	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
//	public String getMerReturnUrl() {
//		return merReturnUrl;
//	}
//	public void setMerReturnUrl(String merReturnUrl) {
//		this.merReturnUrl = merReturnUrl;
//	}
//	public String getIsAutoRefund() {
//		return isAutoRefund;
//	}
	public void setIsAutoRefund(String isAutoRefund) {
		this.isAutoRefund = isAutoRefund;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getIsPushUrl() {
		return isPushUrl;
	}
	public void setIsPushUrl(String isPushUrl) {
		this.isPushUrl = isPushUrl;
	}
	public String getPushUrl() {
		return pushUrl;
	}
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	public String getSettlementCycle() {
		return settlementCycle;
	}
	public void setSettlementCycle(String settlementCycle) {
		this.settlementCycle = settlementCycle;
	}
	public String getMerchantDashboardRefund() {
		return merchantDashboardRefund;
	}
	public void setMerchantDashboardRefund(String merchantDashboardRefund) {
		this.merchantDashboardRefund = merchantDashboardRefund;
	}
	public String getMdDisableRefundCc() {
		return mdDisableRefundCc;
	}
	public void setMdDisableRefundCc(String mdDisableRefundCc) {
		this.mdDisableRefundCc = mdDisableRefundCc;
	}
	public String getMdDisableRefundDc() {
		return mdDisableRefundDc;
	}
	public void setMdDisableRefundDc(String mdDisableRefundDc) {
		this.mdDisableRefundDc = mdDisableRefundDc;
	}
	public String getMdDisableRefundNb() {
		return mdDisableRefundNb;
	}
	public void setMdDisableRefundNb(String mdDisableRefundNb) {
		this.mdDisableRefundNb = mdDisableRefundNb;
	}
	public String getImdDisableRefundUpi() {
		return imdDisableRefundUpi;
	}
	public void setImdDisableRefundUpi(String imdDisableRefundUpi) {
		this.imdDisableRefundUpi = imdDisableRefundUpi;
	}
	public String getImdDisableRefundWallet() {
		return imdDisableRefundWallet;
	}
	public void setImdDisableRefundWallet(String imdDisableRefundWallet) {
		this.imdDisableRefundWallet = imdDisableRefundWallet;
	}
	public String getRefundApi() {
		return refundApi;
	}
	public void setRefundApi(String refundApi) {
		this.refundApi = refundApi;
	}
	public String getRefundApiDisableCc() {
		return refundApiDisableCc;
	}
	public void setRefundApiDisableCc(String refundApiDisableCc) {
		this.refundApiDisableCc = refundApiDisableCc;
	}
	public String getRefundApiDisableDc() {
		return refundApiDisableDc;
	}
	public void setRefundApiDisableDc(String refundApiDisableDc) {
		this.refundApiDisableDc = refundApiDisableDc;
	}
	public String getRefundApiDisableNb() {
		return refundApiDisableNb;
	}
	public void setRefundApiDisableNb(String refundApiDisableNb) {
		this.refundApiDisableNb = refundApiDisableNb;
	}
	public String getRefundApiDisableUpi() {
		return refundApiDisableUpi;
	}
	public void setRefundApiDisableUpi(String refundApiDisableUpi) {
		this.refundApiDisableUpi = refundApiDisableUpi;
	}
	public String getRefundApiDisableWallet() {
		return refundApiDisableWallet;
	}
	public void setRefundApiDisableWallet(String refundApiDisableWallet) {
		this.refundApiDisableWallet = refundApiDisableWallet;
	}
	public String getIntegrationType() {
		return integrationType;
	}
	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}
	public String getIsretryAllowed() {
		return isretryAllowed;
	}
	public void setIsretryAllowed(String isretryAllowed) {
		this.isretryAllowed = isretryAllowed;
	}
	public String getIbpsEmailNotification() {
		return ibpsEmailNotification;
	}
	public void setIbpsEmailNotification(String ibpsEmailNotification) {
		this.ibpsEmailNotification = ibpsEmailNotification;
	}
	public String getIbpsSmsNotification() {
		return ibpsSmsNotification;
	}
	public void setIbpsSmsNotification(String ibpsSmsNotification) {
		this.ibpsSmsNotification = ibpsSmsNotification;
	}
	
	public String getIbpsMailReminder() {
		return ibpsMailReminder;
	}
	public void setIbpsMailReminder(String ibpsMailReminder) {
		this.ibpsMailReminder = ibpsMailReminder;
	}
	public String getReporting_cycle() {
		return Reporting_cycle;
	}
	public void setReporting_cycle(String reporting_cycle) {
		Reporting_cycle = reporting_cycle;
	}
	
	public String getUpi_loader() {
		return Upi_loader;
	}
	public void setUpi_loader(String upi_loader) {
		Upi_loader = upi_loader;
	}
	public String getIsAutoRefund() {
		return isAutoRefund;
	}
	
	public String getUpi_intent() {
		return upi_intent;
	}
	public void setUpi_intent(String upi_intent) {
		this.upi_intent = upi_intent;
	}
	public String getUpi_collect() {
		return upi_collect;
	}
	public void setUpi_collect(String upi_collect) {
		this.upi_collect = upi_collect;
	}
	
	
	
	
	
	public String getStatic_QR() {
		return static_QR;
	}
	public void setStatic_QR(String static_QR) {
		this.static_QR = static_QR;
	}
	public String getDynamic_QR() {
		return dynamic_QR;
	}
	public void setDynamic_QR(String dynamic_QR) {
		this.dynamic_QR = dynamic_QR;
	}
	
	
	public String getPartnerBy() {
		return partnerBy;
	}
	public void setPartnerBy(String partnerBy) {
		this.partnerBy = partnerBy;
	}
	
	
	public String getPartnerBy_name() {
		return partnerBy_name;
	}
	public void setPartnerBy_name(String partnerBy_name) {
		this.partnerBy_name = partnerBy_name;
	}
	@Override
	public String toString() {
		return "MerchantBasicSetupDto [MerchantId=" + MerchantId + ", isAutoRefund=" + isAutoRefund + ", hours="
				+ hours + ", minutes=" + minutes + ", isPushUrl=" + isPushUrl + ", pushUrl=" + pushUrl
				+ ", settlementCycle=" + settlementCycle + ", merchantDashboardRefund=" + merchantDashboardRefund
				+ ", mdDisableRefundCc=" + mdDisableRefundCc + ", mdDisableRefundDc=" + mdDisableRefundDc
				+ ", mdDisableRefundNb=" + mdDisableRefundNb + ", imdDisableRefundUpi=" + imdDisableRefundUpi
				+ ", imdDisableRefundWallet=" + imdDisableRefundWallet + ", refundApi=" + refundApi
				+ ", refundApiDisableCc=" + refundApiDisableCc + ", refundApiDisableDc=" + refundApiDisableDc
				+ ", refundApiDisableNb=" + refundApiDisableNb + ", refundApiDisableUpi=" + refundApiDisableUpi
				+ ", refundApiDisableWallet=" + refundApiDisableWallet + ", integrationType=" + integrationType
				+ ", isretryAllowed=" + isretryAllowed + ", ibpsEmailNotification=" + ibpsEmailNotification
				+ ", ibpsSmsNotification=" + ibpsSmsNotification + ", ibpsMailReminder=" + ibpsMailReminder
				+ ", Reporting_cycle=" + Reporting_cycle + ", Upi_loader=" + Upi_loader + ", upi_intent="
				+ upi_intent + ", upi_collect=" + upi_collect + ", static_QR=" + static_QR + ", dynamic_QR="
				+ dynamic_QR + ", partnerBy=" + partnerBy + ", partnerBy_name=" + partnerBy_name
				+ ", getMerchantId()=" + getMerchantId() + ", getHours()=" + getHours() + ", getMinutes()="
				+ getMinutes() + ", getIsPushUrl()=" + getIsPushUrl() + ", getPushUrl()=" + getPushUrl()
				+ ", getSettlementCycle()=" + getSettlementCycle() + ", getMerchantDashboardRefund()="
				+ getMerchantDashboardRefund() + ", getMdDisableRefundCc()=" + getMdDisableRefundCc()
				+ ", getMdDisableRefundDc()=" + getMdDisableRefundDc() + ", getMdDisableRefundNb()="
				+ getMdDisableRefundNb() + ", getImdDisableRefundUpi()=" + getImdDisableRefundUpi()
				+ ", getImdDisableRefundWallet()=" + getImdDisableRefundWallet() + ", getRefundApi()="
				+ getRefundApi() + ", getRefundApiDisableCc()=" + getRefundApiDisableCc()
				+ ", getRefundApiDisableDc()=" + getRefundApiDisableDc() + ", getRefundApiDisableNb()="
				+ getRefundApiDisableNb() + ", getRefundApiDisableUpi()=" + getRefundApiDisableUpi()
				+ ", getRefundApiDisableWallet()=" + getRefundApiDisableWallet() + ", getIntegrationType()="
				+ getIntegrationType() + ", getIsretryAllowed()=" + getIsretryAllowed()
				+ ", getIbpsEmailNotification()=" + getIbpsEmailNotification() + ", getIbpsSmsNotification()="
				+ getIbpsSmsNotification() + ", getIbpsMailReminder()=" + getIbpsMailReminder()
				+ ", getReporting_cycle()=" + getReporting_cycle() + ", getUpi_loader()=" + getUpi_loader()
				+ ", getIsAutoRefund()=" + getIsAutoRefund() + ", getUpi_intent()=" + getUpi_intent()
				+ ", getUpi_collect()=" + getUpi_collect() + ", getStatic_QR()=" + getStatic_QR()
				+ ", getDynamic_QR()=" + getDynamic_QR() + ", getPartnerBy()=" + getPartnerBy()
				+ ", getPartnerBy_name()=" + getPartnerBy_name() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

	
	
}
