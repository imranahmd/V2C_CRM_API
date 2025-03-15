package com.crm.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.crm.Controller.BankDetailsController;
import com.crm.Repository.AuditTailRepository;
import com.crm.dto.MerchantDto;
import com.crm.model.AuditTail;

@Service
public class AuditTailService {

	private static final Logger log = LoggerFactory.getLogger(BankDetailsController.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	AuditTailRepository auditTailRepository;

	public void auditInsert(String updateBy, String actionName, String Data, String ipAddress) {
		
		

		try {
			AuditTail audittail = new AuditTail();

			audittail.setActionName(actionName);
			log.info("ActionName>>>>>>>>>>"+actionName);
			audittail.setAuditData(Data);
			log.info("ActionName>>>>>>>>>>"+Data);
			
			audittail.setUserName(updateBy);
			audittail.setUpdatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			audittail.setIpAddrss(ipAddress);
			log.info("audit data for insert{}", audittail.toString());

			auditTailRepository.save(audittail);
		} catch (Exception e) {
			log.info("exception in catch block in audit  tails{}", e.getMessage());
		}
	}
	 public List<AuditTail> getAuditTailRecord(String user, String date) {
	        if (date == null || date.isBlank()) {
	            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	        }

	        String fromDate = date.concat(" 00:00:00");
	        String toDate = date.concat(" 23:59:59");

	        if (user == null || user.trim().isEmpty()) {
	            return auditTailRepository.findAuditTailRecords(fromDate, toDate, null);
	        } else {
	            return auditTailRepository.findAuditTailRecords(fromDate, toDate, user);
	        }
	    }

}
