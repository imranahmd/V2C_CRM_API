package com.crm.Repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.dto.TransactionMaster;

@Repository
public interface TransactionMasters extends JpaRepository<TransactionMaster, String> {
	    
	 /*@Query("{rmsflag:'',dateTime: {$gte: '', $lt: ''}}")
	 List<TransactionMaster> findItemByName(String name);
	
	 @Query("{'$group': { 'merchantId' : 1, '_id' : 1 }}")
	    List<String> findByStatusIncludeItemAndStatusFields(String status);
	 
	 
	 @Aggregation(pipeline = { "{ '$group': { '_id' : '$merchantId' } }" })
	    List<MerchantIdDto> findDistinctFirstNames();
}*/
	
	@Query(value = "SELECT distinct(merchant_id) as 'Merchant_Id' FROM tbl_transactionmaster where risk_flag='1' ;", nativeQuery = true)
	List<Map<String, Object>> getMerchantId();
	
	@Query(value = "SELECT distinct(risk_label) FROM tbl_transactionmaster where risk_flag='1' ;", nativeQuery = true)
	List<Object[]> getRiskLbel();
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and merchant_id=:merchant_Id ;", nativeQuery = true)
	double getCountAll(String merchant_Id);
	
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and risk_label=:riskLavel and merchant_id=:merchant_Id ;", nativeQuery = true)
	double getCount(String merchant_Id, String riskLavel);
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1';", nativeQuery = true)
	double getCountAll();
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and merchant_id=:merchant_Id ;", nativeQuery = true)
	double getCountMerchant(String merchant_Id);
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where  risk_label=:riskLavel ;", nativeQuery = true)
	double getCountWithoutMid( String riskLavel);
	
	
	@Query(value = "SELECT distinct(risk_code) FROM tbl_transactionmaster where risk_flag='1' ;", nativeQuery = true)
	List<Object[]> getRiskCode();
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and merchant_id=:merchant_Id and risk_label=:levels ;", nativeQuery = true)
	double getCountAllCode(String merchant_Id, String levels);
	
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and risk_label=:levels and merchant_id=:merchant_Id and risk_code=:riskCode ;", nativeQuery = true)
	double getCountCodes(String merchant_Id, String levels, String riskCode);
	
	@Query(value = "SELECT count(*) FROM tbl_transactionmaster where risk_flag='1' and risk_label=:levels and risk_code=:riskCode ;", nativeQuery = true)
	double getCountCodeswithoutMid( String levels, String riskCode);
	
	

	@Query(value = "Select  txn_Id as'Txn ID',merchant_id as'Auth ID',txn_amount as'Txn Amt',resp_status as'Resp Status',resp_message as'Resp Msg',trans_status as'Trans Status',date_time as'Txn Date',sp_error_code as'SP Error Code',risk_code as'Risk Code',risk_flag as'Risk Flag',upiInterval as'UPI Interval',cycleId as'Cycle Id',reseller_txn_Id as'Reseller Txn ID',service_auth_id as'Service Auth ID',inquiry_status as'Inquiry Status',remote_ip as'Remote IP',bank_id as'Bank ID',card_details as'Card Details',recon_status as'Recon Status',UploadedBy as'Uploaded By',service_txn_id as'Service Txn ID',service_rrn as'Service RRN',resp_date_time as'Resp Date Time',card_name as'Card Name',rodt as'RODT',error_code as'Error Code',is_posted_back_res as'Is Posted Back Res',Id,reseller_Id as'Reseller Id',email_id as'Email Id',process_id as'Process Id',settle_date_time as'Settle Date Time',mobile_no as'Mobile No',Modified_On as'Modified No',rms_score as'RMS Score',Modified_By as'Modified By',sur_charge as'Surcharge',service_id as'Service Id',return_url as'Return URL',Is_Deleted as'Is Deleted',risk_label as'Risk Label',udf5 as'UDF5',udf6 as'UDF6',udf3 as 'UDF3',udf4 as 'UDF4',card_type as 'Card Type',udf1 as'UDF1',udf2 as'UDF2',instrument_id as'Instrument Id',is_verified_once as'Is Verified Once',recon_date_time as'Recon Date Time',TransLifeCycle as'Trans Life Cycle',is_settled_consider as 'Is Settled Consider',sp_call_timestamp as'SP Call Timestamp',rms_reason as'RMS Reason' FROM tbl_transactionmaster where risk_label=:level and merchant_id=:mid and risk_code=:riskcode order by date_time DESC;", nativeQuery = true)
	List<Map<String, Object>> getTransactionData(String level, String mid, String riskcode);
	
	
	@Query(value = "Select  txn_Id as'Txn ID',merchant_id as'Auth ID',txn_amount as'Txn Amt',resp_status as'Resp Status',resp_message as'Resp Msg',trans_status as'Trans Status',date_time as'Txn Date',sp_error_code as'SP Error Code',risk_code as'Risk Code',risk_flag as'Risk Flag',upiInterval as'UPI Interval',cycleId as'Cycle Id',reseller_txn_Id as'Reseller Txn ID',service_auth_id as'Service Auth ID',inquiry_status as'Inquiry Status',remote_ip as'Remote IP',bank_id as'Bank ID',card_details as'Card Details',recon_status as'Recon Status',UploadedBy as'Uploaded By',service_txn_id as'Service Txn ID',service_rrn as'Service RRN',resp_date_time as'Resp Date Time',card_name as'Card Name',rodt as'RODT',error_code as'Error Code',is_posted_back_res as'Is Posted Back Res',Id,reseller_Id as'Reseller Id',email_id as'Email Id',process_id as'Process Id',settle_date_time as'Settle Date Time',mobile_no as'Mobile No',Modified_On as'Modified No',rms_score as'RMS Score',Modified_By as'Modified By',sur_charge as'Surcharge',service_id as'Service Id',return_url as'Return URL',Is_Deleted as'Is Deleted',risk_label as'Risk Label',udf5 as'UDF5',udf6 as'UDF6',udf3 as 'UDF3',udf4 as 'UDF4',card_type as 'Card Type',udf1 as'UDF1',udf2 as'UDF2',instrument_id as'Instrument Id',is_verified_once as'Is Verified Once',recon_date_time as'Recon Date Time',TransLifeCycle as'Trans Life Cycle',is_settled_consider as 'Is Settled Consider',sp_call_timestamp as'SP Call Timestamp',rms_reason as'RMS Reason' FROM tbl_transactionmaster where risk_label=:level and  risk_code=:riskcode order by date_time DESC;", nativeQuery = true)
	List<Map<String, Object>> getTransactionDataWithoutMod(String level,  String riskcode);


}