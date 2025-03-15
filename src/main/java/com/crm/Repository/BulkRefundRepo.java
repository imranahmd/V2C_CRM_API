package com.crm.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crm.model.BulkRefund;

public interface BulkRefundRepo extends MongoRepository<BulkRefund, String> {

}
