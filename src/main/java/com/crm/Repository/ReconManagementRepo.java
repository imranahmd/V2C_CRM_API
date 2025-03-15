package com.crm.Repository;

import java.sql.Timestamp;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.crm.model.MerchantList;
import com.crm.model.ReconRecord;
import com.crm.model.TblFileupload;

@Repository
public interface ReconManagementRepo extends JpaRepository<MerchantList,Long>{
	/*
	 * @Modifying
	 * 
	 * @Transactional
	 */
	@Query(value = "CALL pro_CheckFileName(:fileName,:serviceId);", nativeQuery = true)
	int checkFileName(@Param("fileName") String fileName,@Param("serviceId") String serviceId);
	
	@Query(value = "CALL pro_reconfileList(:Date);", nativeQuery = true)
	List<ReconRecord> pro_reconfileList(@Param("Date") String Date);
	
	@Query(value = "CALL pro_UpdateReconException(:valu,:txnId,:dropDownValue);", nativeQuery = true)
	int updateFailRecon(int valu,String txnId,String dropDownValue);
	
	
	@Query(value = "CALL pro_UpdateReconException(:valu,:txnId,:dropDownValue);", nativeQuery = true)
	int updateForceRecon(int valu,String txnId,String dropDownValue);
	

	@Query(value = "select mid, tid from tbl_merchant_mdr where merchant_id = ? and bank_id = ? and instrument_id = ? and sp_id = ?", nativeQuery = true)
	List<Object[]> getSPMidKey(final String mid, final String bankId, final String instrumentId, final String spId);

	@Query(value = "update tbl_transactionmaster set service_txn_id=? ,service_auth_id=?, service_rrn=?  where Id = ?", nativeQuery = true)
	int updateResponse(String referenceNo,String referenceNo1,String referenceNo2,String id); 

	@Query(value = "CALL pro_InsertRefund(:transId,:refundAmt,:userId);", nativeQuery = true)
	void insertRefundData(String transId, String refundAmt, String userId);
	
	@Query(value = "select t.txn_id,refund.RefundAmt,refund.RefundId,refund.Refund_Type,t.service_txn_id from tbl_transactionmaster t, tbl_transactionrefund refund where t.merchant_id  = refund.merchant_id and refund.Refund_Status='Pending' and t.Id = refund.TransId and t.Id = ?", nativeQuery = true)
	List<Object[]> initiateRefund(String transactionId);

	@Query(value = "select 99 as 'txn_code',date_format(t.date_time,'%y%m%d') as 'txn_date' ,t.service_txn_id as 'order_id',refund.RefundAmt from tbl_transactionmaster t,tbl_transactionrefund refund where refund.Refund_Status='Pending'and t.Id = refund.TransId and t.Id = ?", nativeQuery = true)
	List<Object[]> getDataForReconRefund(String transId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE tbl_mstreconconfig SET Modified_On=?, Modified_By=?, Is_Deleted =? WHERE Id = ?", nativeQuery = true)
	int deleteReconConfigById(Timestamp timestamp,String username,String Is_Deleted,int idval);
	
	
	//recon config 
	
	@Transactional
	@Modifying
	@Query(value = "CALL pro_addReconConfigData(:iTypeValue,:iSpId,:channelId,:iFileType,:iHeader,:iIsSuccess,:iIsRefund,"
			+ ":iDateFormat,:iTextSeperator,:iField1,:iField2,:iField3,:iField4,:iField5,:iField6,:iField7,:iField8,"
			+ ":iField9,:iField10,:iField11,:iField12,:iField13,:iField14,:iField15,:iField16,:iField17,:iCreatedBy,:iId);", nativeQuery = true)
	int addReconConfigData(String iTypeValue, String iSpId, int channelId,String iFileType,String iHeader,String iIsSuccess,
			String iIsRefund,String iDateFormat,String iTextSeperator,String iField1,String iField2,String iField3,String iField4,
			String iField5,String iField6,String iField7,String iField8,String iField9,String iField10,String iField11,String iField12,
			String iField13,String iField14,String iField15,String iField16,String iField17, String iCreatedBy, String iId);
	
	@Transactional
	@Modifying
	@Query(value = "CALL pro_addReconConfigData(:iTypeValue,:iSpId,:channelId,:iFileType,:iHeader,:iIsSuccess,:iIsRefund,"
			+ ":iDateFormat,:iTextSeperator,:iField1,:iField2,:iField3,:iField4,:iField5,:iField6,:iField7,:iField8,"
			+ ":iField9,:iField10,:iField11,:iField12,:iField13,:iField14,:iField15,:iField16,:iField17,:iCreatedBy,:iId);", nativeQuery = true)
	int editReconConfigData(String iTypeValue, String iSpId, int channelId,String iFileType,String iHeader,String iIsSuccess,
			String iIsRefund,String iDateFormat,String iTextSeperator,String iField1,String iField2,String iField3,String iField4,
			String iField5,String iField6,String iField7,String iField8,String iField9,String iField10,String iField11,String iField12,
			String iField13,String iField14,String iField15,String iField16,String iField17, String iCreatedBy, String iId);
	
	
	
	}

