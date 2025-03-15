package com.crm.Repository;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.model.MerchantList;


public interface lBPSRepo extends JpaRepository<MerchantList, Long> {

	@Query(value = "SELECT IF(COUNT(invoice_no) > 0, 'true', 'false') as invoice_no FROM tbl_mstibps WHERE invoice_no = :invoiceNo and merchant_id= :merchantId", nativeQuery = true)
	String existingInvoiceOrNot(String invoiceNo,String merchantId);
	
	@Query(value = "SELECT * FROM tbl_mstmerchant WHERE MerchantId= :merchantId", nativeQuery = true)
	List<Object> getMerchantDetails(String merchantId);
	
	
	
	@Query(value = "call pro_getInvoiceReport1(:Fdate, :Todate, :Amount, :StatusV, :norecord, :pageno, :iUserId);", nativeQuery = true)
	List<Object[]> findByIvoiceByDateStatusAmt(String Fdate, String Todate, String Amount, String StatusV , int norecord, int pageno, String iUserId);
//	
//	@Modifying
//	@Query(value = "TRUNCATE table tbl_invoice_xlx", nativeQuery = true)
//	void truncateTblinvoicexlx();
	
	
	@Query(value = "SELECT * FROM tbl_invoice_xlx", nativeQuery = true)
	List<Object[]> getXlsRedordIBPS();
	
	
	@Query(value = "SELECT if(COUNT(*)>0,'true','false') AS my_bool FROM tbl_mstibps where merchant_id=? and invoice_no =?;", nativeQuery = true)
	boolean findinvoiceNoDupBymerchInvocN(String merchatId, String invoceNo);
	
	
	
//	@Query(value = "UPDATE tbl_invoice_xlx set reason = :invoiceverifedStatus, upload_status = :uploadStatus where id= :id", nativeQuery = true)
//	void updateInvoiceVerification(String invoiceverifedStatus, String uploadStatus, int id );
//	
	
	@Query(value = "select * from tbl_invoice_xlx", nativeQuery = true)
	List<Object[]> invoicelistAllRecords ();
	
//	@Query(value ="call pro_InsertIBPSData(:invoice_no, :merchant_id,:amount, :date_time,:valid_upto, :cust_name, :remarks, :email_id,:contact_number, :link, :status, :createdOn, :createdBy, :modifiedOn,:modifiedBy, :payment_link_id,:merchat_transaction_id);", nativeQuery = true)
//	String createPaymentLink(String invoice_no, String merchant_id, String amount, Timestamp date_time,
//			String valid_upto, String cust_name, String remarks, String email_id, String contact_number, String link,
//			String status, Timestamp createdOn, String createdBy, Timestamp modifiedOn, String modifiedBy,
//			String payment_link_id, String merchat_transaction_id);
}
