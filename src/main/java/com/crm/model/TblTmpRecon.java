package com.crm.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="tbl_tmprecon")

public class TblTmpRecon {
	
	@jakarta.persistence.Id
	private String Id;
	private String merchantid="";
	//@JsonProperty("TXN_ID")
	private String TXN_ID;
	private String bank_txn_id="";
	private String aggre_txn_id="";
	private String amount="";
	private String date_time="";
	private String rrn="";
	private String auth_id="";
	private String txn_status="";
	private String rfu1="";
	private String rfu2="";
	private String rfu3="";
	private String rfu4="";
	private String rfu5="";
	private String rfu6="";
	private String RefundRequestId="";
	private String ArnNo="";
	
	//@JsonProperty("FileId")
	private String FileId="";
	
	//@JsonProperty("IsExceptions")
	private String IsException="";
	
	//@JsonProperty("Exception")
	private String Exception="";
	
	//@JsonProperty("Counter")
	private int Counter=0;

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getTXN_ID() {
		return TXN_ID;
	}
	public void setTXN_ID(String tXN_ID) {
		TXN_ID = tXN_ID;
	}
	public String getBank_txn_id() {
		return bank_txn_id;
	}
	public void setBank_txn_id(String bank_txn_id) {
		this.bank_txn_id = bank_txn_id;
	}
	public String getAggre_txn_id() {
		return aggre_txn_id;
	}
	public void setAggre_txn_id(String aggre_txn_id) {
		this.aggre_txn_id = aggre_txn_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate_time() {
		return date_time;
	}
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
	public String getTxn_status() {
		return txn_status;
	}
	public void setTxn_status(String txn_status) {
		this.txn_status = txn_status;
	}
	public String getRfu1() {
		return rfu1;
	}
	public void setRfu1(String rfu1) {
		this.rfu1 = rfu1;
	}
	public String getRfu2() {
		return rfu2;
	}
	public void setRfu2(String rfu2) {
		this.rfu2 = rfu2;
	}
	public String getRfu3() {
		return rfu3;
	}
	public void setRfu3(String rfu3) {
		this.rfu3 = rfu3;
	}
	public String getRfu4() {
		return rfu4;
	}
	public void setRfu4(String rfu4) {
		this.rfu4 = rfu4;
	}
	public String getRfu5() {
		return rfu5;
	}
	public void setRfu5(String rfu5) {
		this.rfu5 = rfu5;
	}
	public String getRfu6() {
		return rfu6;
	}
	public void setRfu6(String rfu6) {
		this.rfu6 = rfu6;
	}
	public String getRefundRequestId() {
		return RefundRequestId;
	}
	public void setRefundRequestId(String refundRequestId) {
		RefundRequestId = refundRequestId;
	}
	public String getArnNo() {
		return ArnNo;
	}
	public void setArnNo(String arnNo) {
		ArnNo = arnNo;
	}
	public String getFileId() {
		return FileId;
	}
	public void setFileId(String fileId) {
		FileId = fileId;
	}
	public String getIsException() {
		return IsException;
	}
	public void setIsException(String isException) {
		IsException = isException;
	}
	public String getException() {
		return Exception;
	}
	public void setException(String exception) {
		Exception = exception;
	}
	public int getCounter() {
		return Counter;
	}
	public void setCounter(int counter) {
		Counter = counter;
	}
	
	
	
	@Override
	public String toString() {
		return "TblTmpRecon [Id=" + Id + ", merchantid=" + merchantid + ", TXN_ID=" + TXN_ID + ", bank_txn_id="
				+ bank_txn_id + ", aggre_txn_id=" + aggre_txn_id + ", amount=" + amount + ", date_time=" + date_time
				+ ", rrn=" + rrn + ", auth_id=" + auth_id + ", txn_status=" + txn_status + ", rfu1=" + rfu1 + ", rfu2="
				+ rfu2 + ", rfu3=" + rfu3 + ", rfu4=" + rfu4 + ", rfu5=" + rfu5 + ", rfu6=" + rfu6
				+ ", RefundRequestId=" + RefundRequestId + ", ArnNo=" + ArnNo + ", FileId=" + FileId + ", IsException="
				+ IsException + ", Exception=" + Exception + ", Counter=" + Counter + "]";
	}
	public List<String> getColumnNames()
	{
		
		List<String> columnNames =new ArrayList<String>();
		columnNames.add("merchantid");
		columnNames.add("TXN_ID");
		columnNames.add("bank_txn_id");
		columnNames.add("aggre_txn_id");
		columnNames.add("amount");
		columnNames.add("date_time");
		columnNames.add("rrn");
		columnNames.add("auth_id");
		columnNames.add("txn_status");
		columnNames.add("rfu1");
		columnNames.add("rfu2");
		columnNames.add("rfu3");
		columnNames.add("rfu5");
		columnNames.add("rfu4");
		columnNames.add("rfu5");
		columnNames.add("rfu6");
		columnNames.add("RefundRequestId");
		columnNames.add("ArnNo");
		return columnNames;
	}
}
