package com.crm.services;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import com.crm.Repository.IPInfoRepository;
import com.crm.dto.BankAccountKycDTO;
import com.crm.dto.CINKycDTO;
import com.crm.dto.GSTINKycDTO;
import com.crm.dto.KycDTO;
import com.crm.dto.PANKycDTO;
import com.crm.restCall.RestCall;

@Service
public class KYCServices {


	@Autowired
	private RestCall restCall;



	public ResponseEntity<String> getBankAccountVerifyDetails(BankAccountKycDTO dto, String source) {

		return restCall.sendPostRequestBankAccout(dto, source);

	}


	/*public ResponseEntity<String> getGSTINVerifyDetails(GSTINKycDTO dto) {

		return restCall.sendPostRequestGSTIN(dto);

	}*/
	
	public ResponseEntity<String> getKycVerifyDetails(KycDTO dto, String source) {
		
			return restCall.sendPostRequestKyc(dto, source);
		
	
	}
	
	


	/*public ResponseEntity<String> getCINVerifyDetails(CINKycDTO dto) {
		
		return restCall.sendPostRequestCIN(dto);
	}*/


	public ResponseEntity<String> getIFSCVerifyDetails(BankAccountKycDTO dto, String source) {
	
		return restCall.sendPostRequestIFSC(dto, source);
	}
		
/*	public ResponseEntity<String> getPANVerifyDetails(PANKycDTO dto) {

		return restCall.sendPostRequestPAN(dto);
	}*/
}

