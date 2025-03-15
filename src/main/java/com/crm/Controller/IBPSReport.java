package com.crm.Controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.lBPSPaginationDto;
import com.crm.services.UserDetailsui;
import com.crm.services.lBPSService;

@RestController
public class IBPSReport {

	@Autowired
	private lBPSService lbpsService;

	private static Logger log = LoggerFactory.getLogger(IBPSReport.class);
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/InvoiceReport")
	public lBPSPaginationDto InvoiceReport(@RequestBody String jsonBody) throws Exception {
		
		log.info("inside the LBPS:::Report:::::");
		log.info("jsonBody::::::::" + jsonBody);
		JSONObject js = new JSONObject(jsonBody);
		//log.info("js::::::" + js);
		
		String Amount = js.getString("iAmount");
		String Fdate = js.getString("iFDate");
		String Todate = js.getString("iToDate");
		String RportType = js.getString("iType");
		String StatusV = js.getString("iStatus");
		int norecord = js.getInt("pageRecords");
		int pageno = js.getInt("pageNumber");
		String iUserId = js.getString("iUserId");
		
		lBPSPaginationDto invoices = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		if(RportType.equalsIgnoreCase("5")) {
		//	log.info("type:::5::::::::::");
			try {
				
				username = userDetails.optString("Username");
				ipAddress = userDetails.optString("ipAddress");
				//log.info("try:::::::::::::");
				invoices = lbpsService.getlbpsReport(Fdate,Todate,Amount,StatusV,norecord,pageno, iUserId);
				
				secondCon.insertIntoSecondSchema(username, "Invoice Report", jsonBody, invoices.toString(), ipAddress);
				
				//log.info(":::::::::invoices:::::::::");
			} catch (Exception e) {
				
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String stackTraceString = sw.toString();
										
				secondCon.insertIntoSecondSchema(username, "Invoice Report-Exception", jsonBody, stackTraceString, ipAddress);
				
				e.printStackTrace();
			}
		}
		
		
		return invoices;
	
	}
	
	@PostMapping("/ActionInvoiceButton")
	public String ActionInvoiceButton(@RequestBody String jsonBody) throws Exception {
		
		log.info("inside the LBPS:::Report:::::");
		log.info("jsonBody::::::::" + jsonBody);
		JSONObject js = new JSONObject(jsonBody);
		log.info("js::::::" + js);
		
		String IdV = js.getString("id");
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");




		
		
		String resp = null;
		
			
			log.info("IdV::::::" + IdV);
			
			String cancleData = lbpsService.cencelInvoice(IdV); 
			
			log.info("cancleData::::::::"+cancleData);
			if(cancleData.equalsIgnoreCase("success")) {
				
				JSONObject successMsg = new JSONObject();
				successMsg.put("status", "Successfully change");
				resp=successMsg.toString();
			}
			else {
				JSONObject errorMsg = new JSONObject();
				errorMsg.put("status", "Oops sometimes wrong !");
				resp=errorMsg.toString();
			}
			secondCon.insertIntoSecondSchema(username, "Action Invoice Button", jsonBody, resp, ipAddress);
		
		return resp;
	
	}
}
