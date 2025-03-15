package com.crm.Controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.BankAccountKycDTO;
import com.crm.dto.CINKycDTO;
import com.crm.dto.GSTINKycDTO;
import com.crm.dto.KycDTO;
import com.crm.dto.PANKycDTO;
import com.crm.services.IPInfoService;
import com.crm.services.KYCServices;
import com.crm.services.UserDetailsui;

import jakarta.websocket.server.PathParam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class KYCController {
	private static final Logger logger = LoggerFactory.getLogger(KYCController.class);

	@Autowired
	KYCServices kycServices;

	@PostMapping(path = "/kyc/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> gstinVerify(@RequestParam String source, @RequestBody KycDTO dto) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		try {
			logger.info("source::::::" + source);

			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			ResponseEntity<String> resp = kycServices.getKycVerifyDetails(dto, source);

			secondCon.insertIntoSecondSchema(username, "KYC validate", source + "/" + dto.toString(), resp.toString(),
					ipAddress);

			return resp;
		} catch (HttpClientErrorException ex) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpClientErrorException :::" + ex);
			secondCon.insertIntoSecondSchema(username, "KYC validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);
			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		} catch (HttpStatusCodeException ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpStatusCodeException  :" + ex.getResponseBodyAsString());

			secondCon.insertIntoSecondSchema(username, "KYC validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);
			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}
	}

	@PostMapping(path = "/bankAccount/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> bankAccountVerify(@RequestParam String source,
			@RequestBody BankAccountKycDTO dto) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		try {

			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			ResponseEntity<String> resp = kycServices.getBankAccountVerifyDetails(dto, source);

			secondCon.insertIntoSecondSchema(username, "Bank Account Validate", source + "/" + dto.toString(),
					resp.toString(), ipAddress);

			return resp;
		} catch (HttpClientErrorException ex) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpClientErrorException :::" + ex);

			secondCon.insertIntoSecondSchema(username, "Bank Account Validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);

			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());

		} catch (HttpStatusCodeException ex) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpStatusCodeException  :" + ex.getResponseBodyAsString());

			secondCon.insertIntoSecondSchema(username, "Bank Account Validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);

			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}

	}

	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping(path = "/IFSC/validate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ifscVerify(@RequestParam String source,
			@RequestBody BankAccountKycDTO dto) {

		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();

		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		try {

			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			ResponseEntity<String> resp = kycServices.getIFSCVerifyDetails(dto, source);

			secondCon.insertIntoSecondSchema(username, "IFSC validate", source + "/" + dto.toString(), resp.toString(),
					ipAddress);
			return resp;
		} catch (HttpClientErrorException ex) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpClientErrorException :::" + ex);

			secondCon.insertIntoSecondSchema(username, "IFSC validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);

			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		} catch (HttpStatusCodeException ex) {

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String stackTraceString = sw.toString();

			logger.info("HttpStatusCodeException  :" + ex.getResponseBodyAsString());

			secondCon.insertIntoSecondSchema(username, "IFSC validate-Exception", source + "/" + dto.toString(),
					stackTraceString, ipAddress);

			return ResponseEntity.status(ex.getRawStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}

	}

}
