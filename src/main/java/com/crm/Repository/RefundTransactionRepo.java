package com.crm.Repository;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.crm.model.RefundAmtRequest;
import com.crm.model.RefundTransaction;

public interface RefundTransactionRepo extends MongoRepository<RefundTransaction, String>{
	
	@Query("{'id' : ?0}")
	List<RefundTransaction> findBycId(String id);
	@Query("{'txnId' : ?0}")
	List<RefundTransaction> findByctxnId(String txnId);
	@Query("{'merchantId' : ?0}")
	List<RefundTransaction> findBycmerchanId(String merchantId);
	@Query("{'dateTime' : {$gte : ?0, $lt: ?1}}")
	List<RefundTransaction> findBycdatetime(String fromDate, String toDate);
	@Query("{'bankId' : ?0}")
	List<RefundTransaction> findBycbankId(String bankId);
	@Query("{'custMail' : ?0}")
	List<RefundTransaction> findByccustMail(String custMail);
	@Query("{'custMobile' : ?0}")
	List<RefundTransaction> findByccustMobile(String custMobile);
	
	@Query("{'id' : ?0, 'txnId' : ?1}")
	List<RefundTransaction> findBycIdandtxnId(String id, String txnId);
	@Query("{'id' : ?0, 'merchantId' : ?1}")
	List<RefundTransaction> findBycIdandmerchantId(String id, String merchantId);
	@Query("{'id' : ?0, 'dateTime' : {$gte : ?1, $lt: ?2}}")
	List<RefundTransaction> findBycIdandstrDateandendDate(String id, String fromDate, String toDate);
	@Query("{'id' : ?0, 'bankId' : ?1}")
	List<RefundTransaction> findBycIdandbankId(String id, String bankId);
	@Query("{'id' : ?0, 'custMail' : ?1}")
	List<RefundTransaction> findBycIdandcustMail(String id, String custMail);	
	@Query("{'id' : ?0, 'custMobile' : ?1}")
	List<RefundTransaction> findBycIdandcustMobile(String id, String custMobile);
	
	
	
//	@Query("{'id' : ?0}")
	
	@Aggregation(pipeline = {
			
			"{'$group':{'_id': '$TransId', 'total': {'$sum':{'$toDouble': '$RefundAmt'}}}}"})
	AggregationResults<RefundAmtRequest>  findBycustomId(String id);
	
	
	@Query("{'id' : ?0}")
	RefundTransaction findBytxnId(String id);
	
	@Query("{'id' : ?0, 'merchantId' : ?1}")
	RefundTransaction findBytxnIdandMerchantId(String id, String merchantId);
	
}
