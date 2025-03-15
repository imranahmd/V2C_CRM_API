package com.crm.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.crm.dto.PaymentRequestList;
import com.crm.model.BulkCsvMerchant;
import com.crm.model.BulkRefundSql;

public interface BulkRefundSqlRepo extends JpaRepository<BulkRefundSql, Long>{
	
	@Query(value = "call getRefundApprovedTransactionlist(:merchantId, :fromDate, :toDate, :id, :txnId, :custMail, :custMobile,"
			+ "	 :count, :spId, :pageNo, :type, :searchBy, :refundType);", nativeQuery = true)
	List<Map<String, Object>> refundDownloadStatusList(String merchantId, String fromDate, String toDate, BigInteger id,
			String txnId, String custMail, String custMobile, String count, String spId, int pageNo,
			int type, String searchBy, String refundType);
	
	@Query(value = "call pro_GetRefundListAdmin(:fromDate, :toDate, :id, :txnId, :refundId, :refundType, :refundStatus,"
			+ "	 :count, :pageNo,  :searchBy, :type);", nativeQuery = true)
	List<Map<String, Object>> refundTransactionStatus(String fromDate, String toDate, String id, String txnId,
			String refundId, String refundType, String refundStatus, String count, int pageNo, int type, String searchBy);
	
	@Query(value = "select a.TransId,b.merchant_id, a.RefundAmt, a.Refund_RequestId,a.Refund_Status from tbl_transactionrefund a inner join tbl_transactionmaster b on a.TransId=b.id where Refund_RequestId=:refundId ;", nativeQuery = true)
	List<Tuple> getrefundInfo(String refundId);
	
	@Query(value = "select * from tbl_transactionmaster where id==:refundinfo ;", nativeQuery = true)
	Map<String, Object> getTransaction(String refundinfo);

	
	
	@Query(value = "call pro_MerchantWallet(:Mid, :sumAmount, :custrefno);", nativeQuery = true)
	Map<String, Object> GetWalletDeatils(String Mid,double sumAmount,String custrefno);

	
	@Query(value = "select * from tbl_indusspay_Bene_Deatails where Customer_Id=:refundinfo ;", nativeQuery = true)
	Map<String, Object> GetCustomerBeneId(String refundinfo);

	@Query(value = "Select sp_id from tbl_merchant_payout_mdr where merchant_id=:Mid and IsDeleted='N' and mop=:ModPay ;", nativeQuery = true)
	String GetSpId(String Mid,String ModPay);

	/*
	 * @Query(value =
	 * "Select count(1) from tbl_merchant_payout_mdr where merchant_id=:Mid and IsDeleted='N' and mop=:ModPay ;"
	 * , nativeQuery = true) String GetSpId(String Mid,String ModPay);
	 */
	

	@Query(value = "Select * from tbl_merchant_payout_mdr where merchant_id=:Mid and IsDeleted='N' and mop=:ModPay ;", nativeQuery = true)
	Map<String, Object> GetAllDeatilsById(String Mid,String ModPay);
	
	@Query(value="select available_amt as totalBalance from tbl_merchant_wallet where merchant_Id=:MerchantId ;",nativeQuery = true)
	String CheckWalletdetails (String MerchantId);
	
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_merchantpayout_request set Payout_status=:payoutStatus, utr_no=:utrNo, Is_Approve=:isApproveFlag where ClientRefId=:refNo;", nativeQuery = true)
	int UpdatePayoutTransResponse(String payoutStatus, String utrNo, String isApproveFlag,String refNo);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_transactionrefund set Refund_Status=:refundStatus, request_status=:requestStatus, IsApprove=:isApproveFlag, resp_message=:refundStatusrep, Modified_On=:timeSpan, Remark=:refundRemark, Is_Processed=:isApproveFlag where RefundId =:refundRequestId ;", nativeQuery = true)
	int updateRefundStatusForManual(String refundStatus, String requestStatus, String isApproveFlag, String refundRequestId, String refundStatusrep, String refundRemark, Timestamp timeSpan);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_transactionmaster set service_txn_id=:atomTxnId,service_rrn=:bankRefNo,service_auth_id=:bankRefNo,trans_status=:txnStatus where id=:merchantTxnId ;", nativeQuery = true)
	int updateTxnMaster(String atomTxnId, String merchantTxnId, String bankRefNo, String txnStatus);
	
	@Query(value = "select T1.process_id from tbl_transactionmaster T1 left join tbl_transactionrefund T2 on T1.Id = T2.TransId where T2.Refund_RequestId = :refundRequestId ;", nativeQuery = true)
	String getProcessId(String refundRequestId);
	

	@Query(value = "select T1.Id from tbl_transactionmaster T1 left join tbl_transactionrefund T2 on T1.Id = T2.TransId where T2.Refund_RequestId = :refundRequestId ;", nativeQuery = true)
	String getiDVal(String refundRequestId);
	
	@Query(value = "select T1.service_txn_id,T1.txn_Id,T2.Refund_RequestId,T2.RefundAmt, T1.date_time, T2.Added_On, T2.Added_By,T1.txn_amount from tbl_transactionmaster T1 left join tbl_transactionrefund T2 on T1.Id = T2.TransId where T1.Id =:idVal ;", nativeQuery = true)
	List<Object[]> listDataRefund (String idVal);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_transactionrefund set Refund_Status=:refundStatus, request_status=:requestStatus, IsApprove=:isApproveFlag, resp_message=:refundStatusrep, Modify_By=:userId, Modified_On=:timeSpan, Remark=:refundRemark, Is_Processed=:isApproveFlag where Refund_RequestId =:refundRequestId ;", nativeQuery = true)
	int updateRefundStatusForManual1(String refundStatus, String requestStatus, String isApproveFlag, String refundRequestId, String refundStatusrep, String refundRemark, String userId, Timestamp timeSpan);
	
	
	@Query(value = "call pro_GetRefundListMerchant(:merchantId, :fromDate, :toDate, :id, :txnId, :refundId, :refundType, :refundStatus,"
			+ "	 :count, :pageNo,  :searchBy, :type);", nativeQuery = true)
	List<Map<String, Object>> refundTransactionStatusMerchant(String merchantId, String fromDate, String toDate,
			String id, String txnId, String refundId, String refundType, String refundStatus, String count, int pageNo,
			String searchBy, int type);
	
}
