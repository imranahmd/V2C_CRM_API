package com.crm.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bson.BsonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.crm.Controller.RmsConfigController;
import com.crm.Repository.TransactionMasters;
import com.crm.dto.MerchantIdDto;
import com.crm.dto.TransactionMaster;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Service
public class RmsMisService {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(RmsConfigController.class);

	
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	private TransactionMasters TransactionMasters;

	public List<TransactionMaster> showAllGroceryItems() {
		List<com.crm.dto.TransactionMaster> itm = TransactionMasters.findAll();

		return itm;
	}

	public List<TransactionMaster> findStudentsByProperties(String From, String ToDate, String Mid, String RiskCode,
			String RiskStage, String RiskFlag) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		if (From != "" && ToDate != "")
			Criteria.where("dateTime").gte(From).lte(ToDate);
		if (Mid != "") {
			criteria.add(Criteria.where("merchantId").is(Mid));
		}

		/*
		 * if (RiskCode != "") { criteria.add(Criteria.where("RiskCode").in(RiskCode));
		 * } if (RiskStage != "") {
		 * 
		 * criteria.add(Criteria.where("RiskStage").is(RiskStage)); }
		 */
		if (RiskFlag == "") {
			criteria.add(Criteria.where("rmsflag").is(RiskFlag));
		}
		System.out.print("Step 3:::::::::::::::: " + criteria);
		if (!criteria.isEmpty())
			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		System.out
				.print("Value 444 st step:::::::::::::::::::   " + mongoTemplate.find(query, TransactionMaster.class));

		return mongoTemplate.find(query, TransactionMaster.class);
	}

	/*
	 * public DistinctIterable<String> showAllGroceryItems1() { return
	 * mongoTemplate.getCollection("tbl_transactionmaster").distinct("merchantId",
	 * Filters.eq("rmsflag", ""), String.class); }
	 */
	
	
	public List<Map<String, Object>> showAllGroceryItems1() {		
		Map<String, Object> Msg = null ;	
		JSONArray js= new JSONArray();
		JSONObject js1 = new JSONObject();
		List<Map<String, Object>> resultdata=null;
		try {
			log.info("-------------------showAllGroceryItems1-------------------------");
			 resultdata = TransactionMasters.getMerchantId();
			
			log.info("resultdata:::::::::::::::::::::::::"+resultdata);
			System.out.println("resultdata:::::::::::::::::::::::::"+resultdata);
			if(!resultdata.isEmpty()) {
				// js1.put("Status", "Success");
				// js1.put("Message", "Data Found");
				 js1.put("data", resultdata);
				 js.put(resultdata);
				 log.info("gerRMSId  data ::::::::::::::::::{} "+ js1.toString());
			}else {
				// js1.put("Status", "Error");
				// js1.put("Message", "No Data found");
				 js1.put("data", "");
			}
		}catch(Exception e) {
			e.printStackTrace();
			//js1.put("Status", "Error");
			 js1.put("data", "Error Found");
			log.info("gerRMSId   ::::::::::::::::::{} "+e.getMessage());
		}
		Msg = js1.toMap();
		return resultdata;
	}
	
	
	
//	public DistinctIterable<String> showAllGroceryItems1() {
//		return mongoTemplate.getCollection("tbl_transactionmaster").distinct("merchantId", Filters.eq("riskFlag", "1"),
//				String.class);
//	}
	/*
	 * public Map<String, Object> getRiskLevelData(String Merchant_Id) {
	 * DistinctIterable<String> ds =
	 * mongoTemplate.getCollection("tbl_transactionmaster").distinct("rms_level",
	 * String.class); Query query = new Query(); JSONObject js = new JSONObject();
	 * query.addCriteria(Criteria.where("rmsflag").is("1"));
	 * System.out.print(Merchant_Id); if (!Merchant_Id.equalsIgnoreCase("")) {
	 * query.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double i =
	 * mongoTemplate.count(query, "tbl_transactionmaster"); if (i <= 0) {
	 * js.put("error", "Merchant_Id Does not Exits"); return js.toMap(); }
	 * MongoCursor<String> results = ds.iterator(); while (results.hasNext()) {
	 * System.out.println(i); Query query2 = new Query(); String Level =
	 * results.next(); query2.addCriteria(Criteria.where("rmsflag").is("1"));
	 * query2.addCriteria(Criteria.where("rms_level").is(Level)); if
	 * (!Merchant_Id.equalsIgnoreCase("")) {
	 * query2.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double j
	 * = mongoTemplate.count(query2, "tbl_transactionmaster"); double per = (j / i);
	 * double value = per * 100; js.put(Level, value); System.out.println(j); }
	 * return js.toMap(); }
	 *
	 */

	/*
	 * public Map<String, Object> getRiskLevelData(String Merchant_Id) {
	 * DistinctIterable<String> ds =
	 * mongoTemplate.getCollection("tbl_transactionmaster").distinct("rms_level",
	 * String.class); Query query = new Query(); JSONObject js = new JSONObject();
	 * query.addCriteria(Criteria.where("rmsflag").is("1"));
	 * System.out.print(Merchant_Id); if (!Merchant_Id.equalsIgnoreCase("")) {
	 * query.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double i =
	 * mongoTemplate.count(query, "tbl_transactionmaster"); if (i <= 0) {
	 * js.put("error", "Merchant_Id Does not Exits"); return js.toMap(); }
	 * MongoCursor<String> results = ds.iterator(); while (results.hasNext()) {
	 * System.out.println(i); Query query2 = new Query(); String Level =
	 * results.next(); query2.addCriteria(Criteria.where("rmsflag").is("1"));
	 * query2.addCriteria(Criteria.where("rms_level").is(Level)); if
	 * (!Merchant_Id.equalsIgnoreCase("")) {
	 * query2.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double j
	 * = mongoTemplate.count(query2, "tbl_transactionmaster"); double per = (j / i);
	 * double value = per * 100; js.put(Level, value); System.out.println(j); }
	 * return js.toMap(); }
	 */

//	public Map<String, Object> getRiskLevelData(String Merchant_Id) {
//		DistinctIterable<String> ds = mongoTemplate.getCollection("tbl_transactionmaster").distinct("riskLabel",
//				String.class);
//		Query query = new Query();
//		JSONObject js = new JSONObject();
//		query.addCriteria(Criteria.where("riskFlag").is("1"));
//		System.out.print(Merchant_Id);
//		if (!Merchant_Id.equalsIgnoreCase("")) {
//			query.addCriteria(Criteria.where("merchantId").is(Merchant_Id));
//		}
//		double i = mongoTemplate.count(query, "tbl_transactionmaster");
//		if (i <= 0) {
//			js.put("error", "Merchant_Id Does not Exits");
//			return js.toMap();
//		}
//		MongoCursor<String> results = ds.iterator();
//		while (results.hasNext()) {
//			System.out.println(i);
//			Query query2 = new Query();
//			String Level = results.next();
//			query2.addCriteria(Criteria.where("riskFlag").is("1"));
//			query2.addCriteria(Criteria.where("riskLabel").is(Level));
//			if (!Merchant_Id.equalsIgnoreCase("")) {
//				query2.addCriteria(Criteria.where("merchantId").is(Merchant_Id));
//			}
//			double j = mongoTemplate.count(query2, "tbl_transactionmaster");
//			double per = (j / i);
//			double value = per * 100;
//			js.put(Level, value);
//			System.out.println(j);
//		}
//		js.remove("");
//		return js.toMap();
//	}

	/*
	 * public Map<String, Object> getRiskCountData(String Merchant_Id,String Levels)
	 * { DistinctIterable<String> ds =
	 * mongoTemplate.getCollection("tbl_transactionmaster").distinct("rms_code",
	 * String.class); Query query = new Query(); JSONObject js = new JSONObject();
	 * 
	 * query.addCriteria(Criteria.where("rmsflag").is("1"));
	 * query.addCriteria(Criteria.where("rms_level").is(Levels));
	 * System.out.print(Merchant_Id); if (!Merchant_Id.equalsIgnoreCase("")) {
	 * query.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double i =
	 * mongoTemplate.count(query, "tbl_transactionmaster");
	 * 
	 * if (i <= 0) { js.put("error", "Data Does not Exits"); return js.toMap(); }
	 * 
	 * MongoCursor<String> results = ds.iterator(); while (results.hasNext()) {
	 * System.out.println(i); Query query2 = new Query(); String Level =
	 * results.next(); query2.addCriteria(Criteria.where("rmsflag").is("1"));
	 * query2.addCriteria(Criteria.where("rms_code").is(Level));
	 * query2.addCriteria(Criteria.where("rms_level").is(Levels)); if
	 * (!Merchant_Id.equalsIgnoreCase("")) {
	 * query2.addCriteria(Criteria.where("merchantId").is(Merchant_Id)); } double j
	 * = mongoTemplate.count(query2, "tbl_transactionmaster"); js.put(Level, j);
	 * System.out.println(j); } return js.toMap(); }
	 */

	public Map<String, Object> getRiskLevelData(String Merchant_Id) {
		Map<String, Object> Msg = null ;		
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		try {
			log.info("mid::::::::"+Merchant_Id);
			
			
		List<Object[]> ds = TransactionMasters.getRiskLbel();
		double countAll = 0;
		if(Merchant_Id.equalsIgnoreCase("")) 
		{
			countAll = TransactionMasters.getCountAll();
			log.info("if------------Merchant_Id=empty----------Merchant_Id"+Merchant_Id);
		}
		else {
			countAll = TransactionMasters.getCountMerchant(Merchant_Id);	
			
			log.info("else:::::::::::::::::::Merchant_Id"+Merchant_Id);
		}
	
		
		log.info(":::::::::::::::::::::::: ds ::::::::::::::::::::"+ds);
		
		log.info(":::::::::::::::::::::::: countAll ::::::::::::::::::::"+countAll);
		
		if(ds !=null) {
			for(Object[] obj: ds) 
            {
				//JSONObject item = new JSONObject();
				String riskLavel= (String) obj[0];
				
				switch(riskLavel)
				{
				
				case "Red":
					riskLavel="red";
				break;
				
				case "Green":
					riskLavel="green";
				break;
				
				case "Voilet":
					riskLavel="voilet";
				break;
				
				case "Orange":
					riskLavel="Orange";
				break;
				
				}
				log.info("------------riskLavel-------------"+riskLavel);
				double count = 0;
				if(Merchant_Id.equalsIgnoreCase("")) 
				{
					count = TransactionMasters.getCountWithoutMid(riskLavel);
					log.info("if------------Merchant_Id=empty----------Merchant_Id"+Merchant_Id);
					log.info("if------------Merchant_Id=empty----------riskLavel"+riskLavel);
					log.info("count::::::::::::::::::::::::"+count);
				}
				else {
					count = TransactionMasters.getCount(Merchant_Id, riskLavel);					
					log.info("else:::::::::::::::::::Merchant_Id"+Merchant_Id);
					log.info("else------------Merchant_Id != empty----------Merchant_Id"+Merchant_Id);
					log.info("else------------Merchant_Id != empty----------riskLavel"+riskLavel);
					log.info("count::::::::::::::::::::::::"+count);
				}
				double per = (count / countAll);
				double value = per * 100;
				
				log.info("per:::::::::::::::"+per);
				log.info("value:::::::::::::"+value);
				
				BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
				
				item.put(riskLavel,bd);
			//	item.put(riskLavel,value);
				
				log.info("item:::::::::::::::::::::::::::"+item);
				array.put(item);
				
				log.info("array::::::::::::::::::::::"+array);
            }
				//js1.put("Status", "Success");
				//js1.put("Message", "Data Found");
				//js1.put("data", array);
				//js1.put(item);
				log.info("js1:::::::::::::::::::::::::::::"+js1);
		}else {
				//js1.put("Status", "Error");
				//js1.put("Message", "No Data found");
				js1.put("data", "");
			
			}
		}catch(Exception e) {
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("data", "Error Found");
			log.info("getRiskLavel   ::::::::::::::::::{} "+e.getMessage());
		}
	//	Msg = js1.toMap();
		//return js1.toMap();
		
		Msg = item.toMap();
		return item.toMap();
	}

	
	
//	public Map<String, Object> getRiskCountData(String Merchant_Id, String Levels) {
//		DistinctIterable<String> ds = mongoTemplate.getCollection("tbl_transactionmaster").distinct("riskCode",String.class);
//		Query query = new Query();
//		JSONObject js = new JSONObject();
//
//		query.addCriteria(Criteria.where("riskFlag").is("1"));
//		query.addCriteria(Criteria.where("riskLabel").is(Levels));
//		System.out.print(Merchant_Id);
//		if (!Merchant_Id.equalsIgnoreCase("")) {
//			query.addCriteria(Criteria.where("merchantId").is(Merchant_Id));
//		}
//		double i = mongoTemplate.count(query, "tbl_transactionmaster");
//		/*
//		 * if (i <= 0) { js.put("error", "Data Does not Exits"); return js.toMap(); }
//		 */
//		MongoCursor<String> results = ds.iterator();
//		while (results.hasNext()) {
//			System.out.println(i);
//			Query query2 = new Query();
//			String Level = results.next();
//			query2.addCriteria(Criteria.where("riskFlag").is("1"));
//			query2.addCriteria(Criteria.where("riskCode").is(Level));
//			query2.addCriteria(Criteria.where("riskLabel").is(Levels));
//			if (!Merchant_Id.equalsIgnoreCase("")) {
//				query2.addCriteria(Criteria.where("merchantId").is(Merchant_Id));
//			}
//			double j = mongoTemplate.count(query2, "tbl_transactionmaster");
//			js.put(Level, j);
//			System.out.println(j);
//		}js.remove("");
//		return js.toMap();
//	}

	/*
	 * public List<TransactionMaster> GetRiskTransaction(String level, String Mid,
	 * String Riskcode) { final Query query = new Query(); final List<Criteria>
	 * criteria = new ArrayList<>(); if (!Mid.equals("")) {
	 * criteria.add(Criteria.where("merchantId").is(Mid)); } else if
	 * (!level.equalsIgnoreCase("")) {
	 * criteria.add(Criteria.where("rms_level").is(level)); } if
	 * (!Riskcode.equals("")) {
	 * criteria.add(Criteria.where("rms_code").is(Riskcode)); }
	 * System.out.print("Step 3:::::::::::::::: " + criteria); if
	 * (!criteria.isEmpty()) query.addCriteria(new
	 * Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
	 * System.out .print("Value 444 st step:::::::::::::::::::   " +
	 * mongoTemplate.find(query, TransactionMaster.class));
	 * 
	 * return mongoTemplate.find(query, TransactionMaster.class); }
	 */
	
	
	
	public Map<String, Object> getRiskCountData(String Merchant_Id, String Levels) {
		Map<String, Object> Msg = null ;		
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
		try {
		List<Object[]> ds = TransactionMasters.getRiskCode();
		double countAllByCode = TransactionMasters.getCountAllCode(Merchant_Id,Levels);
		if(ds !=null) {
			for(Object[] obj: ds) 
            {
				//JSONObject item = new JSONObject();
				String riskCode= (String) obj[0];
				
				double countCode = 0;//TransactionMasters.getCountCodes(Merchant_Id, Levels, riskCode);
				
				if(Merchant_Id.equalsIgnoreCase("")) 
				{
					countCode = TransactionMasters.getCountCodeswithoutMid(Levels, riskCode);
					log.info("if merchant id is empty-----------------------------------------");
					
				}
				else 
				{
					log.info("---------------------------else if merchant id is not null or empty------------");
					countCode = TransactionMasters.getCountCodes(Merchant_Id, Levels, riskCode);

				}
								
				log.info("\ncountCode:::::::::::::::::::::::::::::::::"+countCode);
				log.info("\nMerchant_Id:::::::::::::::::::::::::::::::::"+Merchant_Id);
				log.info("\nLevels:::::::::::::::::::::::::::::::::"+Levels);
				log.info("\nriskCode:::::::::::::::::::::::::::::::::"+riskCode);
				
				item.put(riskCode,countCode);
				array.put(item);
            }
				js1.put("Status", "Success");
				js1.put("Message", "Data Found");
				js1.put("data", array);
		}else {
				js1.put("Status", "Error");
				js1.put("Message", "No Data found");
				js1.put("data", "");
			
			}
		}catch(Exception e) {
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("data", "Error Found");
			log.info("getRiskLavel   ::::::::::::::::::{} "+e.getMessage());
		}
		Msg = item.toMap();	
		return item.toMap();
	}

	
//	public List<TransactionMaster> GetRiskTransaction(String level, String Mid, String Riskcode) {
//		final Query query = new Query();
//		final List<Criteria> criteria = new ArrayList<>();
//		if (!Mid.equals("")) {
//			criteria.add(Criteria.where("merchantId").is(Mid));
//		} if (!level.equalsIgnoreCase("")) {
//			criteria.add(Criteria.where("riskLabel").is(level));
//		}
//		if (!Riskcode.equals("")) {
//			criteria.add(Criteria.where("riskCode").is(Riskcode));
//		}
//		System.out.print("Step 3:::::::::::::::: " + criteria);
//		if (!criteria.isEmpty())
//			query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()]))).with(Sort.by(Direction.DESC,"dateTime"));
//		System.out
//				.print("Value 444 st step:::::::::::::::::::   " + mongoTemplate.find(query, TransactionMaster.class));
//
//		return mongoTemplate.find(query, TransactionMaster.class);
//	}

	public Map<String, Object> GetRiskTransaction(String level, String Mid, String Riskcode) 
	{
		log.info("::::::::::::::::::::GetRiskTransaction::::::::::::::::::::::::");
		Map<String, Object> Msg = null ;	
		
		log.info("level::::"+level+"::::::::::::::::::Mid:"+Mid+":::::::::::::::::::::::::::Riskcode:"+Riskcode);
		JSONObject js1 = new JSONObject();
		try {
			List<Map<String, Object>> resultdata = null;
			
			if(Mid.equalsIgnoreCase("")) 
			{
				log.info("Mid is ::::::::::"+Mid);
				resultdata = TransactionMasters.getTransactionDataWithoutMod(level, Riskcode);				log.info("if merchant id is empty-----------------------------------------");
				
			}
			else 
			{
				log.info("---------------------------else if merchant id is not null or empty------------");
				resultdata = TransactionMasters.getTransactionData(level,Mid, Riskcode);
			}
			
			
			
			
			log.info("::::::::::::::::::::::::::::::::resultdata::::::::::::"+resultdata);

			if(!resultdata.isEmpty()) {
				
				log.info(":::::::::::::::::::::::resultdata is Empty:::::::::::::::::::::");

				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", resultdata);
				 log.info("getTransactioin  data ::::::::::::::::::{} "+ js1.toString());
			}else {
				log.info("::::::::::::::::else no data found::::::::::::::::::::::::::::");

				 js1.put("Status", "Error");
				 js1.put("Message", "No Data found");
				 js1.put("data", "");
			}
		}catch(Exception e) {
			e.printStackTrace();
			js1.put("Status", "Error");
			 js1.put("data", "Error Found");
			log.info("gerRMSId   ::::::::::::::::::{} "+e.getMessage());
		}
		Msg = js1.toMap();
		log.info(":::::::::::::::::::::::::::::::::Msg:::::::::::"+Msg);

		return Msg;
	}


	
}
