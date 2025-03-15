package com.crm.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.services.IPInfoService;

@RestController

public class IPInfoController {

	@Autowired
	IPInfoService infoService;
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping(path="/ip-check", produces = MediaType.APPLICATION_JSON_VALUE )

    public ResponseEntity<String> gstinVerify(@RequestBody String ipAddress ) {

	String resp= infoService.getIPAddress(ipAddress);
	
	 return ResponseEntity.ok().body(resp);
    }
}

