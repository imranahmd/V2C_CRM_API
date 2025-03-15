package com.crm.services;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.MerchantRepository;
import com.crm.Repository.lBPSRepo;
import com.crm.dto.BulkCsvMerMdrDto;
import com.crm.dto.BulkInvoiceList;
import com.crm.dto.BulkMdrList;
import com.crm.dto.BulkXlsInvoiceDto;
import com.crm.dto.MerchantDto;
import com.crm.dto.bulkXlxInvoice;
import com.crm.dto.lBPSDto;
import com.crm.dto.lBPSPaginationDto;
import com.crm.helper.MdrBulkCsvHelper;
import com.crm.helper.lbpsBulkHelper;
import com.crm.model.BulkCsvMdr;
import com.crm.util.GenerateRandom;
import com.external.api.BitlyAPI;
import com.ibm.icu.util.Calendar;

@Service
public class lBPSService {

	static Logger log = LoggerFactory.getLogger(lBPSService.class.getName());

	
	
	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private lBPSRepo iBPSRepo;

	public String getMerchantDetails(String Mid) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_MerchantDetailForIBPS(?)}");
				callableStatement.setString(1, Mid);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Not Found Data");

			}
//		logger.info("arrayList::::::"+arrayList);
			JSONArray jsArray = new JSONArray(arrayList);
//		logger.info("jsArray::::"+jsArray);
//		System.out.print("arrayList::::::::::"+arrayList.toString());
			return jsArray.toString();
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	public String createPaymentLink(String invoice_no, String merchant_id, String amount, Timestamp date_time,
			String valid_upto, String cust_name, String remarks, String email_id, String contact_number, String link,
			String status, Timestamp createdOn, String createdBy, Timestamp modifiedOn, String modifiedBy,
			String payment_link_id, String merchat_transaction_id) throws Exception {
		// TODO Auto-generated method stub
		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_InsertIBPSData(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				callableStatement.setString(1, invoice_no);
				callableStatement.setString(2, merchant_id);
				callableStatement.setString(3, amount);
				callableStatement.setTimestamp(4, date_time);
				callableStatement.setString(5, valid_upto);
				callableStatement.setString(6, cust_name);
				callableStatement.setString(7, remarks);
				callableStatement.setString(8, email_id);
				callableStatement.setString(9, contact_number);
				callableStatement.setString(10, link);
				callableStatement.setString(11, status);
				callableStatement.setTimestamp(12, createdOn);
				callableStatement.setString(13, createdBy);
				callableStatement.setTimestamp(14, modifiedOn);
				callableStatement.setString(15, modifiedBy);
				callableStatement.setString(16, payment_link_id);
				callableStatement.setString(17, merchat_transaction_id);

				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			Object val = resultData.get("#update-count-1");
			System.out.print("val::::::::::::::: " + val);
			String s2 = String.valueOf(val);
			System.out.print("s2::::::::::::::: " + s2);

			if (s2 != "0") {
				responseVal = "success";
			} else {
				responseVal = "fail";
			}

			return responseVal;
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	public String notificationType(JSONObject mstMerchant, String smsNotifi, String emailNotifi) {
		log.info("Inside notificationType()");
		String notificationType = "";

		if ((mstMerchant.getString("ibps_email_notification").equalsIgnoreCase("1")
				&& emailNotifi.equalsIgnoreCase("Y"))
				&& (smsNotifi.equalsIgnoreCase("N") || smsNotifi == null
						|| mstMerchant.getString("ibps_sms_notification").equalsIgnoreCase("0"))) {
			log.info("EMAIL type");
			notificationType = "EMAIL";
		} else if ((mstMerchant.getString("ibps_sms_notification").equalsIgnoreCase("1")
				&& smsNotifi.equalsIgnoreCase("Y"))
				&& (emailNotifi.equalsIgnoreCase("N") || emailNotifi == null
						|| mstMerchant.getString("ibps_email_notification").equalsIgnoreCase("0"))) {
			log.info("SMS type");
			notificationType = "SMS";
		} else if ((mstMerchant.getString("ibps_sms_notification").equalsIgnoreCase("1")
				&& smsNotifi.equalsIgnoreCase("Y"))
				&& (mstMerchant.getString("ibps_email_notification").equalsIgnoreCase("1")
						&& emailNotifi.equalsIgnoreCase("Y"))) {
			log.info("BOTH type");
			notificationType = "BOTH";
		}

		return notificationType;
	}

	public lBPSPaginationDto getlbpsReport(String Fdate, String Todate, String Amount, String StatusV, int norecord,
			int pageno, String iUserId) {
		// log.info("getlbpsReport:::::::::::");

		lBPSPaginationDto dto = new lBPSPaginationDto();
		dto.setNumberOfRecords(norecord);
		dto.setPageNumber(pageno);
		// log.info("getlbpsReport::1:::::::::");

		List<lBPSDto> invoices = new ArrayList<>();
		List<Object[]> dbinvoices = iBPSRepo.findByIvoiceByDateStatusAmt(Fdate, Todate, Amount, StatusV, norecord,
				pageno, iUserId);
//		log.info("getlbpsReport::2:::::::::"+dbinvoices.toString()+":::::"+dbinvoices.get(0), dbinvoices.get(1),dbinvoices.get(2),dbinvoices.get(3),dbinvoices.get(4),
//				dbinvoices.get(5),dbinvoices.get(6),dbinvoices.get(7),dbinvoices.get(8));

		for (Object[] obj : dbinvoices) {
			lBPSDto invoice = new lBPSDto();
			invoice.setId((BigInteger) obj[0]);
			invoice.setPayment_Link_Id((String) obj[1]);
			invoice.setInvoice_Number((String) obj[2]);
			invoice.setAmount((String) obj[3].toString());
			
			invoice.setDateTime((String) obj[4].toString());
			
			invoice.setValidity((String) obj[5]);
			invoice.setCustomer_Name((String) obj[6]);
			invoice.setLink((String) obj[7]);
			invoice.setStatus((String) obj[8]);
			invoice.setIsDeleted((String) obj[9]);

			invoices.add(invoice);
		}
		// log.info("getlbpsReport::2invoices:::::::::");
		List<String> invoicesDataCount = getrecordCount(Fdate, Todate, Amount, StatusV, norecord, pageno,iUserId);
		// log.info("getlbpsReport::2invoinvoicesDataCountices:::::::::"+invoicesDataCount.toString());
		JSONArray jsonArray = null;
		jsonArray = new JSONArray(invoicesDataCount);
		JSONObject rec = jsonArray.getJSONObject(0);
		Long noRecords = rec.getLong("TotalRecords");
		// log.info("getlbpsReport::2inv8888888oices:::::::::");
		dto.setInvoices(invoices);
		dto.setTotalRecords(noRecords);

		return dto;

	}

	public String cencelInvoice(String invoice_no) throws Exception {
		// TODO Auto-generated method stub
		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {

			System.out.print("inside the cancel invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_invoiceActionButton(?)}");
				callableStatement.setString(1, invoice_no);

				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			Object val = resultData.get("#update-count-1");
			System.out.print("val::::::::::::::: " + val);
			String s2 = String.valueOf(val);
			System.out.print("s2::::::::::::::: " + s2);

			if (s2 != "0") {
				responseVal = "success";
			} else {
				responseVal = "fail";
			}

			return responseVal;
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	public List<String> getrecordCount(String Fdate, String Todate, String Amount, String StatusV, int norecord,
			int pageno, String iUserId) {

		List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));

		System.out.print("ParmVlaues::::::::::::::: " + parameters);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_getInvoiceReport1(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, Fdate);
			callableStatement.setString(2, Todate);
			callableStatement.setString(3, Amount);
			callableStatement.setString(4, StatusV);
			callableStatement.setInt(5, norecord);
			callableStatement.setInt(6, pageno);
			callableStatement.setString(7, iUserId);
			return callableStatement;
		}, parameters);

		ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");

		if (arrayList.isEmpty())
			return null;

		return arrayList;
	}

	// ibps bulk code---------

public String insertData(bulkXlxInvoice bUlkXlxInvoice) {
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {

			System.out.print("inside the invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_insertIBPSxls(?,?,?,?,?,?,?,?,?)}");
				callableStatement.setString(1, bUlkXlxInvoice.getInvoiceNumber());
				callableStatement.setString(2, bUlkXlxInvoice.getAmount());
				callableStatement.setString(3, bUlkXlxInvoice.getValidUpTo());
				callableStatement.setString(4, bUlkXlxInvoice.getRemarks());
				callableStatement.setString(5, bUlkXlxInvoice.getName());
				callableStatement.setString(6, bUlkXlxInvoice.getEmailId());
				callableStatement.setString(7, String.valueOf(bUlkXlxInvoice.getMobile()));
				callableStatement.setString(8, bUlkXlxInvoice.getEmailNotification());
				callableStatement.setString(9, bUlkXlxInvoice.getsMSNotification());

				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);

		} catch (Exception e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
		return null;
	}

public String createInvoiceByXls(String invoceNo,String merchatId,String amountV,String validUpto,String remarks,String name,String emailID, String mobileNo,String link, String status,String modifiedBy,String createdBy,String payment_link_id, String merchat_transaction_id) throws Exception {

	// TODO Auto-generated method stub
	String responseVal = null;
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));

	try {

		System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_InsertIBPSBulkXls(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, invoceNo);
			callableStatement.setString(2, merchatId);
			callableStatement.setString(3, amountV);
			callableStatement.setString(4, validUpto);
			callableStatement.setString(5, remarks);
			callableStatement.setString(6, name);
			callableStatement.setString(7, emailID);
			callableStatement.setString(8, mobileNo);
			callableStatement.setString(9, link);
			callableStatement.setString(10, status);
			callableStatement.setString(11, createdBy);
			callableStatement.setString(12, modifiedBy);
			callableStatement.setString(13, payment_link_id);
			callableStatement.setString(14, merchat_transaction_id);

			return callableStatement;
		}, prmtrsList);

		System.out.print("resultData::::::::::::::: " + resultData);
		Object val = resultData.get("#update-count-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		if (s2 != "0") {
			responseVal = "success";
		} else {
			responseVal = "fail";
		}

		return responseVal;
	} catch (Exception e1) {
		throw new Exception();
	}
}

public void truncateTblinvoicexlx() throws Exception {
   
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));

	try {

		System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_tranctaetbl_invoice_xlx()}");
			
			return callableStatement;
		}, prmtrsList);

		System.out.print("resultData::::::::::::::: " + resultData);
		Object val = resultData.get("#update-count-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		

		//return null;
	} catch (Exception e1) {
		throw new Exception();
	}
}




public void updateInvoiceVerification(String invoiceverifedStatus, String uploadStatus,int id ) throws Exception {
	   
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));

	try {

		System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_updatetbl_invoice_xlx(?,?,?)}");
			callableStatement.setString(1, invoiceverifedStatus);
			callableStatement.setString(2, uploadStatus);
			callableStatement.setInt(3, id);
			return callableStatement;
		}, prmtrsList);

		System.out.print("resultData::::::::::::::: " + resultData);
		Object val = resultData.get("#update-count-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		

		//return null;
	} catch (Exception e1) {
		throw new Exception();
	}
}


}
