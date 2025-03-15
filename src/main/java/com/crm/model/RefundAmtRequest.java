package com.crm.model;

import jakarta.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tbl_transactionrefund")
public class RefundAmtRequest {
	
	@Id
	private String TransId;
	private Double RefundAmt;
	private String Refund_Type;
	private String Refund_Status;
	private String Remark;
	private String Merchant_Id;
	private String Is_Processed;
	private String Refund_Process_date;
	private String IsApprove;
	private String Added_By;
	private String Added_On;
	private String Modify_By;
	private String Modified_On;
	private String Refund_RequestId;
	private String request_type;
	private String request_status;
	private String Is_Payout;
	private String ArnNo;
	private String resp_message;
	private String pay_refund_id;
	
	 private Double total;
	
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getTransId() {
		return TransId;
	}
	public void setTransId(String transId) {
		TransId = transId;
	}
	
	public Double getRefundAmt() {
		return RefundAmt;
	}
	public void setRefundAmt(Double refundAmt) {
		RefundAmt = refundAmt;
	}
	public String getRefund_Type() {
		return Refund_Type;
	}
	public void setRefund_Type(String refund_Type) {
		Refund_Type = refund_Type;
	}
	public String getRefund_Status() {
		return Refund_Status;
	}
	public void setRefund_Status(String refund_Status) {
		Refund_Status = refund_Status;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getMerchant_Id() {
		return Merchant_Id;
	}
	public void setMerchant_Id(String merchant_Id) {
		Merchant_Id = merchant_Id;
	}
	public String getIs_Processed() {
		return Is_Processed;
	}
	public void setIs_Processed(String is_Processed) {
		Is_Processed = is_Processed;
	}
	public String getRefund_Process_date() {
		return Refund_Process_date;
	}
	public void setRefund_Process_date(String refund_Process_date) {
		Refund_Process_date = refund_Process_date;
	}
	public String getIsApprove() {
		return IsApprove;
	}
	public void setIsApprove(String isApprove) {
		IsApprove = isApprove;
	}
	public String getAdded_By() {
		return Added_By;
	}
	public void setAdded_By(String added_By) {
		Added_By = added_By;
	}
	public String getAdded_On() {
		return Added_On;
	}
	public void setAdded_On(String added_On) {
		Added_On = added_On;
	}
	public String getModify_By() {
		return Modify_By;
	}
	public void setModify_By(String modify_By) {
		Modify_By = modify_By;
	}
	public String getModified_On() {
		return Modified_On;
	}
	public void setModified_On(String modified_On) {
		Modified_On = modified_On;
	}
	public String getRefund_RequestId() {
		return Refund_RequestId;
	}
	public void setRefund_RequestId(String refund_RequestId) {
		Refund_RequestId = refund_RequestId;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getRequest_status() {
		return request_status;
	}
	public void setRequest_status(String request_status) {
		this.request_status = request_status;
	}
	public String getIs_Payout() {
		return Is_Payout;
	}
	public void setIs_Payout(String is_Payout) {
		Is_Payout = is_Payout;
	}
	public String getArnNo() {
		return ArnNo;
	}
	public void setArnNo(String arnNo) {
		ArnNo = arnNo;
	}
	public String getResp_message() {
		return resp_message;
	}
	public void setResp_message(String resp_message) {
		this.resp_message = resp_message;
	}
	public String getPay_refund_id() {
		return pay_refund_id;
	}
	public void setPay_refund_id(String pay_refund_id) {
		this.pay_refund_id = pay_refund_id;
	}
	
	
	

}
