package com.crm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.crm.services.UserDetailsui;
import com.crm.Controller.BankDetailsController;
import com.crm.helper.JwtHelperUtil;

public class UserDetailsui {
	
	
	private static final Logger log = LoggerFactory.getLogger(BankDetailsController.class);
	
	
	public String getUserDetails() {
		
		JSONObject userDetails = new JSONObject();
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = attributes.getRequest();
	    
	    //IP retrived
	    String ipAddress = request.getHeader("X-Forwarded-For");
	    if (ipAddress == null) {
	        ipAddress = request.getRemoteAddr(); // Fallback to remote address if X-Forwarded-For is not set
	    }
		log.info("Ip Addess"+ipAddress);
		String RequestTokenHeader= request.getHeader("Authorization");
		 System.out.println("JwtAuthFilter.java::::::::::::::;Token is Validated"+RequestTokenHeader);
		 String Username=null;
		 String JwtToken=null;
		 if(RequestTokenHeader !=null &&RequestTokenHeader.startsWith("Bearer "))
		 {
			 JwtToken = RequestTokenHeader.substring(7);
		 }
		 List<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();

		 HashMap<String, String> map = new HashMap<>();
		 //Username retrieved
		  Username= JwtHelperUtil.extractUsername(JwtToken);
		log.info("Username"+Username);
		
		userDetails.put("Username",Username);
		userDetails.put("ipAddress",ipAddress);
		
		return userDetails.toString();
	}

}
