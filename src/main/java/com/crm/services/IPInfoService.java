package com.crm.services;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.crm.Repository.IPInfoRepository;
import com.crm.restCall.RestCall;

@Service
public class IPInfoService {

	@Value("${neutrino.api.ip-info}")
	private String url;
	
	@Autowired
	private IPInfoRepository ipInfoRepository;
	
	@Autowired
	private RestCall restCall;
	
	String response= null;
	public String getIPAddress(String ipAddress) {
		 JSONObject json= new JSONObject(ipAddress);
		 String ipAdd= json.get("ip").toString();
		  boolean ip= ipInfoRepository.getIPAddress(ipAdd);
		  
		  if(!ip) {
			  JSONObject resp= new JSONObject();
			  resp.put("status", false);
			  resp.put("message", "This IP is blocked");
			  return resp.toString();
		  }else {
			  
			response=  restCall.sendGetRequest(ipAdd, url).getBody();
			
			System.out.println("response:::"+response);
		  }
		 
		  return response;
	}
	
	
}

