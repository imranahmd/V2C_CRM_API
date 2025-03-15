package com.crm.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class SMSDto {
	@Value("${SMSConfig.username}")
    private String SMSusername;
	
	@Value("${SMSConfig.password}")
    private String SMSpassword;
	
	@Value("${SMSConfig.senderId}")
    private String SMSsenderId;
	
	@Value("${SMSConfig.type}")
    private String SMSType;
	
	@Value("${SMSConfig.peId}")
    private String SMSPeId;
	
	@Value("${SMSConfig.tempid}")
    private String SMSTempId;
	
	@Value("${SMSConfig.url}")
    private String SMSUrl;

	public String getSMSusername() {
		return SMSusername;
	}

	public void setSMSusername(String sMSusername) {
		SMSusername = sMSusername;
	}

	public String getSMSpassword() {
		return SMSpassword;
	}

	public void setSMSpassword(String sMSpassword) {
		SMSpassword = sMSpassword;
	}

	public String getSMSsenderId() {
		return SMSsenderId;
	}

	public void setSMSsenderId(String sMSsenderId) {
		SMSsenderId = sMSsenderId;
	}

	public String getSMSType() {
		return SMSType;
	}

	public void setSMSType(String sMSType) {
		SMSType = sMSType;
	}

	public String getSMSPeId() {
		return SMSPeId;
	}

	public void setSMSPeId(String sMSPeId) {
		SMSPeId = sMSPeId;
	}

	public String getSMSTempId() {
		return SMSTempId;
	}

	public void setSMSTempId(String sMSTempId) {
		SMSTempId = sMSTempId;
	}

	public String getSMSUrl() {
		return SMSUrl;
	}

	public void setSMSUrl(String sMSUrl) {
		SMSUrl = sMSUrl;
	}
	
	
	
}
