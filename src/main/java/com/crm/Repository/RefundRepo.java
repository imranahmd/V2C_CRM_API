package com.crm.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import org.bson.json.JsonObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.crm.model.RefundAmtRequest;
import com.crm.model.RefundTransaction;
import com.crm.model.RefundTrasactionMod;
import com.google.gson.Gson;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository

public class RefundRepo {
	@Autowired
	MongoTemplate mongoTemplate;
	
	public RefundAmtRequest getRefundtotal (String TransId, String Merchant_Id){
		 	MatchOperation matchStage = Aggregation.match(new Criteria("TransId").is(TransId));
		    MatchOperation matchStage1 = Aggregation.match(new Criteria("Merchant_Id").is(Merchant_Id));
		    ProjectionOperation projectStage = Aggregation.project("TransId", ("RefundAmt"));
		    GroupOperation groupByStateAndSumPop = Aggregation.group("TransId")
		    		  .sum("RefundAmt").as("total");
		    Aggregation aggregation = Aggregation.newAggregation(matchStage, matchStage1, projectStage, groupByStateAndSumPop);
		    
		    AggregationResults<RefundAmtRequest> output= mongoTemplate.aggregate(aggregation, "tbl_transactionrefund", RefundAmtRequest.class);
		    
		    RefundAmtRequest refundTotal =output.getUniqueMappedResult();
		    return refundTotal;
	}
	
	public RefundTrasactionMod updateTransaction(RefundTrasactionMod department , String TransId){

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(TransId));
        Update update = new Update();
        update.set("errorCode", department.getErrorCode());
        update.set("respMessage", department.getRespMessage());
        update.set("reconstatus", department.getReconstatus());
        return mongoTemplate.findAndModify(query, update, RefundTrasactionMod.class);
    }
	
public String refundAmtList(Bson filter ){
		MongoCollection<Document> collection = mongoTemplate.getCollection("tbl_transactionmaster");
		
		ArrayList<Document>  result = collection.aggregate(Arrays.asList(new Document("$match",filter),
				new Document("$project", 
			    new Document("_id", 0L)
			            .append("TransactionId", "$id")
			            .append("TransactiontxnId", "$txnId")
			            .append("Transaction_Amount", 
			    new Document("$toDouble", "$amount"))
			            .append("MerchantId", "$merchantId")
			            .append("Date_Time", "$dateTime")
			            .append("ServiceRRN", "$serviceRRN")
			            .append("InstrumentId", "$instrumentId")
			            .append("BankId", "$bankId")
			            .append("ProcessId", "$processId")
			            .append("ReconStatus", "$reconstatus")), 
			    new Document("$lookup", 
			    new Document("from", "tbl_transactionrefund")
			            .append("localField", "TransactionId")
			            .append("foreignField", "TransId")
			            .append("pipeline", Arrays.asList(new Document("$group", 
			                new Document("_id", 
			                new Document("Merchant_Id", "$merchantId")
			                            .append("TransId", "$TransId"))
			                        .append("total", 
			                new Document("$sum", "$RefundAmt"))
			                        .append("count", 
			                new Document("$sum", 1L)))))
			            .append("as", "result")), 
			    new Document("$project", 
			   
			    new Document("Transaction_Id", "$TransactionId")
			                .append("TransactiontxnId", "$TransactiontxnId")
			                .append("TransactionAmount", "$Transaction_Amount")
			                .append("MerchantId", "$MerchantId")
			                .append("DatTime", "$Date_Time")
			                .append("ServiceRRN", "$ServiceRRN")
			                .append("BankId", "$BankId")
			                .append("ProcessId", "$ProcessId")
			                .append("ReconStatus", "$ReconStatus")
			                .append("BalanceAmt", 
			    new Document("$reduce", 
			    new Document("input", "$result")
			                        .append("initialValue", "$Transaction_Amount")
			                        .append("in", 
			    new Document("$subtract", Arrays.asList(new Document("$toDouble", "$Transaction_Amount"), 
			                                new Document("$toDouble", "$$this.total"))))))))).into(new ArrayList<>());
		String json = new Gson().toJson(result);
		
        return json;
    }
}
