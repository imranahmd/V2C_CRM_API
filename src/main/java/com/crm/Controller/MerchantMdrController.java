package com.crm.Controller;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.MerchantCreationDto;
import com.crm.dto.MerchantDto;
import com.crm.dto.MerchantMdrDto;
import com.crm.dto.MerchantPaginationDto;
import com.crm.model.MerchantBank;
import com.crm.model.MerchantKycDoc;
import com.crm.model.Response;
import com.crm.services.MerchantMdrService;
import com.crm.services.MerchantService;
import com.crm.services.UserDetailsui;

/*@CrossOrigin(origins = {"http://localhost:4200"})
*/

@RestController
public class MerchantMdrController {
	static Logger log = LoggerFactory.getLogger(MerchantMdrController.class);

	@Autowired
	private MerchantMdrService merchantmdrService;

	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(path= "/get-merchantmdrlist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getMerchantMdrlistByMID(@RequestBody String merchantId) {
		
		
		  SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			 
		
		
		
		
		String mdrList = null;		
	
		mdrList = merchantmdrService.getMerchantMdrList(merchantId);
		
		secondCon.insertIntoSecondSchema(username, "Get-merchantmdrlist", merchantId, mdrList, ipAddress);
		
		return  ResponseEntity.ok().body(mdrList);
	}
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(path= "/createorupdate-merchantmdrlist", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Response> createorupdateMerchantMdrlist(@RequestBody MerchantMdrDto dto) {
		
		  SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");

			  
		
		
		Response mdrList = null;		
	log.info("Get ID of bank:::::::::::; "+dto.getId());
	secondCon.insertIntoSecondSchema(username, "Change Merchant Status", dto.toString(), dto.toString(),ipAddress);
	mdrList = merchantmdrService.createorupdateMerchantMdr(dto);	
	return  ResponseEntity.ok().body(mdrList);
	}
	

	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(path= "/delete-merchantmdrlist", produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Response> deleteMerchantMdrlist(@RequestBody MerchantMdrDto dto) {
		
		Response mdrList = null;		
	
		mdrList = merchantmdrService.deleteMerchantMdr(dto);
		return  ResponseEntity.ok().body(mdrList);
	}
}
