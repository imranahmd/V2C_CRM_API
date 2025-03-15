package com.crm.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.crm.model.BulkRefundBatchDetails;

public interface BulkRefundBatchDetailRepo extends MongoRepository<BulkRefundBatchDetails, String>{

}
