package com.crm.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crm.model.RefundAmtRequest;

public interface RefundAmountInsertRepo extends MongoRepository <RefundAmtRequest, String>{

}
