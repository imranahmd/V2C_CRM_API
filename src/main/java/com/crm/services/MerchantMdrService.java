package com.crm.services;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.crm.Repository.MerchantBankRepo;
import com.crm.Repository.MerchantKycDocRepo;
import com.crm.Repository.MerchantMdrRepo;
import com.crm.Repository.MerchantMdrRepository;
import com.crm.Repository.MerchantRepository;
import com.crm.Repository.UserRepository;
import com.crm.dto.MerchantCreationDto;
import com.crm.dto.MerchantDocDto;
import com.crm.dto.MerchantDto;
import com.crm.dto.MerchantMdrDto;
import com.crm.dto.MerchantPaginationDto;
import com.crm.model.MerchantBank;
import com.crm.model.MerchantKycDoc;
import com.crm.model.MerchantMdr;
import com.crm.model.Response;
import com.crm.model.User;
import com.crm.util.GeneralUtil;
import com.crm.util.GenerateRandom;
import com.google.gson.Gson;

import java.util.Optional;


@Service
public class MerchantMdrService {
	 private static final Logger logger = LoggerFactory.getLogger(MerchantMdrService.class);
	@Autowired
	private MerchantMdrRepo merchantMdrRepo;

	@Autowired
	private MerchantMdrRepository merchantMdrRepository;


 @Autowired
private JdbcTemplate JdbcTemplate;



	public String getMerchantMdrList(String merchantId){
		JSONObject js = new JSONObject(merchantId);		
		String mId = js.getString("merchantId");
		List<String> merchantMdr = merchantMdrRepo.getDataByMerchantId(mId); 
		JSONArray jsonArray =null;
		JSONObject resp= new JSONObject();

		if(merchantMdr!=null) {
			 jsonArray= new JSONArray(merchantMdr);
			 resp.put("status", true);
			 resp.put("message", "Data found");
			 resp.put("Details", jsonArray);
			 return jsonArray.toString();
		}else {
			  resp.put("status", false);
			  resp.put("message", "No data found");
			  return resp.toString();
		}
	
		
		
	}


public Response createorupdateMerchantMdr(MerchantMdrDto dto) {
		
		MerchantMdr mdr = new MerchantMdr();
		
		
				String mdrType = dto.getMdrType();
		String bankMdrType = dto.getBankMdrType();
		
		String timestamp= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
		
		int i= merchantMdrRepository.createorupdateMerchantMdrMdrlist(dto.getMerchantId(),dto.getSpId(),dto.getBankId(), dto.getInstrumentId(), 
				dto.getMinAmt(), dto.getMaxAmt(),
				mdrType,dto.getAggrMdr(),dto.getResellerMdr(),dto.getBaseRate(),
						  dto.getMinBaseRate(), dto.getMaxBaseRate(), dto.getMinMdr(), dto.getMaxMdr(),
						  dto.getMid(), dto.getTid(), "2", dto.getStartDate().trim()+" 00:00:00",
						  dto.getEndDate().trim()+" 00:00:00",timestamp, dto.getPrefrences(), dto.getSurcharge(),
						  dto.getPayout(),dto.getCardVariantName(), dto.getInstrumentBrand(),
						  dto.getNetwork(), bankMdrType,
						  dto.getMinResellerMdr(),dto.getMaxResellerMdr(),dto.getResellerMdrType(),dto.getId(),dto.getPayout_escrow()
						 );
		logger.info("status:::::::::::::"+dto.getId());
		if(dto.getId()==0)
		{
		if(i==0) return new Response(true, "Merchant Mdr Details insert successfully", null); 
		}
		return new Response(true, "Merchant Mdr Details updated successfully", null);
	}


	/*public Response updateMerchantMdr(MerchantMdrDto dto) {

		String mdrType = (dto.getMdrType().equalsIgnoreCase("Percentage")) ? "1" : "2";
		
		String bankMdrType =
				  (dto.getbankMdrType().equalsIgnoreCase("Percentage")) ? "1" : "2";
		int i= merchantMdrRepository.updateMdrlist(dto.getMerchantId(),dto.getSpId(),dto.getBankId(), dto.getInstrumentId(), 
				dto.getMinAmt(), dto.getMaxAmt(),
				mdrType,dto.getAggrMdr(),dto.getResellerMdr(),dto.getBaseRate(),
						  dto.getMinBaseRate(), dto.getMaxBaseRate(), dto.getMinMdr(), dto.getMaxMdr(),
						  dto.getMid(), dto.getTid(), "2", dto.getStartDate().trim()+" 00:00:00",
						  dto.getEndDate().trim()+" 00:00:00", dto.getPrefrences(), dto.getSurcharge(),
						  dto.getPayout(),dto.getCardVariantName(), dto.getInstrumentBrand(),
						  dto.getNetwork(), bankMdrType,
						  dto.getminResellerMdr(),dto.getMaxResellerMdr()
						 );
		
		logger.info("status::::"+i);
		return new Response(true, "Merchant Mdr Details updated successfully", null);
	}
*/
	public Response deleteMerchantMdr(MerchantMdrDto dto) {

		int i= merchantMdrRepository.deleteMdrlist(dto.getId());
		
		logger.info("status::::"+i);
		return new Response(true, "Merchant Mdr Deleted successfully", null);
	}
	
}
