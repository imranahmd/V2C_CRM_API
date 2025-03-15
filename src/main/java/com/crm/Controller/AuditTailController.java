package com.crm.Controller;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.services.AuditTailService;
import com.crm.services.Role1Masterservice;

@RestController
public class AuditTailController {
	
	private static Logger log = LoggerFactory.getLogger(Role1Masterservice.class);
	
	@Autowired
	AuditTailService auditTailService;
	
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/AuditTail", produces = "application/json")	
	public ResponseEntity<?> AuditTail(@RequestParam String user, @RequestParam String date) {
		
		log.info("Hiiiiiiii");
		
		
		List<com.crm.model.AuditTail> auditData=null;
		 try {
			 auditData=auditTailService.getAuditTailRecord(user,date);
			 
			 log.info("------->>"+auditData.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(auditData);
		
	}

}
