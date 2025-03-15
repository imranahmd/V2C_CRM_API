package com.crm.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.crm.model.RefundTransaction;
import com.crm.model.RefundTrasactionMod;

public interface RefundTransactionUpdateRepo extends MongoRepository<RefundTrasactionMod, String> {

	@Query("{'atrn' : ?0, 'merchantId' : ?1 }")
	RefundTrasactionMod findByatrn(String atrn, String merchantId);

	@Query("{'id' : ?0, 'merchantId' : ?1}")
	RefundTrasactionMod findBytxnIdandMerchantId(String id, String merchantId);
	
	@Query("{'id' : ?0}")
	RefundTrasactionMod findBytxnId(String id);

}
