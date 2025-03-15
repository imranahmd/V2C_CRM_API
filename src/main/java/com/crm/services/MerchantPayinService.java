package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.Controller.MerchantPayinController;
import com.crm.Repository.MerchantMdrRepository;
import com.crm.model.Response;

@Service
public class MerchantPayinService {

	private static Logger log = LoggerFactory.getLogger(MerchantPayinService.class);

	@Autowired
	private JdbcTemplate JdbcTemplate;
	@Autowired
	private MerchantMdrRepository merchantMdrRepository;
	
	public Response insertMerchant_payoutmdrRecord(String merchant_id, String sp_id, String mop, String aggr_mdr_Type,
			String aggr_mdr, String base_mdr_Type, String base_mdr, String start_date, String end_date, String mid,
			String tid, String min_amt, String max_amt,int Id,String MDR_apply_Type) {
			
		
			log.info("inside the insert payout mdr in db:::::::::::::::+Id "+Id);
			int i=merchantMdrRepository.createpayoutmdr(Id,merchant_id,sp_id,mop,aggr_mdr_Type,aggr_mdr,base_mdr_Type,base_mdr,start_date,end_date,mid,tid,min_amt,max_amt,MDR_apply_Type);
			
		if(Id==0)
		{
		if(i==0) return new Response(true, "Payout MDR Insert Successfully", null); 
		}
		return new Response(true, "Payout MDR Updated Successfully", null);
		}
	
	public String DeleteByID(String Id) {
		log.info("DeleteByID::"+Id);

		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> resultData ;
		try {
			log.info("DeleteByID in try ::"+Id);

		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("Delete from tbl_merchant_payout_mdr where Id='"+Id+"';");
//				callableStatement.setString(1, Id);
				
				return callableStatement;
		}, prmtrsList);
		
		
		System.out.print("resultData::::::::::::::: " + resultData);
		Object val = resultData.get("#inserted-set-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		if (s2 != "0") {
			responseVal = "success";
		} else {
			responseVal = "fail";
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return responseVal;


	}

	public Map<String, Object> getPayoutmdr(String merchantId) {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, Object> role1Data =    JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_gettbl_merchant_payout_mdr(?)}");
				callableStatement.setString(1, merchantId);
				return callableStatement;
	}, prmtrsList);
				if(!role1Data.isEmpty()) {
				Object obj=role1Data.get("#result-set-1");
				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", obj);
			 }else {
				 js1.put("Status", "Error");
				 js1.put("Message", "Data Not Found");
			 }
		Msg = js1.toMap();
		return Msg;
	}

	

}
