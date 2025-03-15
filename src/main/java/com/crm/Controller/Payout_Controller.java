//package com.crm.Controller;
//
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.crm.services.PayloutService;
//@RestController
//public class Payout_Controller {
//	private static Logger log = LoggerFactory.getLogger(Payout_Controller.class);
//
//	@Autowired
//	PayloutService payoutService;
//	
//
//	@CrossOrigin(origins = { "http://localhost:4200"})
//	@PostMapping(value = "/Settlement", produces = "application/json")
//	public String SettlementRS(@RequestBody String jsonBody) {
//		String ResponseValue=null;
//		log.info("entered SettlementRS+++++++============");
//		JSONObject js = new JSONObject(jsonBody);
//		String MerchantId=js.getString("MerchantId");
//		String CycleId=js.getString("CycleId");
//		String ReqDate=js.getString("ReqDate");
//		String ProccessId=js.getString("ProccessId");
//
//		String payoutraised=null;
//		try {
//			log.info("MerchantId::::" + MerchantId + "CycleId ::::: " + CycleId + "ReqDate ::::::::: "+ReqDate);
//			//JSONObject js1 = new JSONObject();
//				
//			
//			 payoutraised=payoutService.MarkSettlement(MerchantId,CycleId,ReqDate,ProccessId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		ResponseValue = payoutraised;
//
//
//		return ResponseValue;
//}
//
//}
