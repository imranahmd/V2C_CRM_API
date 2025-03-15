package com.crm.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.web.multipart.MultipartFile;

import com.crm.dto.BulkCsvMerchantDto;

public class BulkCsvHelper {
	
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "Sr_no", "contact_person", "contact_number", "email_id", "CompanyPAN", "merchant_name", "business_name" 
			  ,"BusinessType", "merchant_category_code","merchant_sub_category", "BusinessModel", "TurnoverinlastFinancialYear", "ExpectedMonthlyIncome", "AverageAmountPerTransaction",
			  "AuthorisedSignatoryPAN", "NameAsPerPAN", "GSTINNo", "mer_website_url", "reseller_id", "IsTestAccess", "mer_return_url"
			  , "is_auto_refund", "hours", "minutes", "is_push_url", "push_url", "settlement_cycle", "integration_type", "isretryAllowed", "ibps_email_notification"
			  , "ibps_sms_notification", "ibps_mail_remainder", "Reporting_cycle", "merchant_dashboard_refund", "md_disable_refund_cc", "md_disable_refund_dc", "md_disable_refund_nb", 
			  "md_disable_refund_upi", "md_disable_refund_wallet", "refund_api", "refund_api_disable_cc", "refund_api_disable_dc", "refund_api_disable_nb"
			  , "refund_api_disable_upi", "refund_api_disable_wallet"};

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }
	  
	  public static List<BulkCsvMerchantDto> csvToMstmerchant(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<BulkCsvMerchantDto> BulkDtos = new ArrayList<BulkCsvMerchantDto>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		      Long length = csvParser.getRecordNumber();
		      
//		      int size = attributes.size();
		      if (length == 0) {
		          throw new IOException("CSV cannot be empty");
		      }

		      
		      
		      if (length == 1 ) {
		          throw new IOException("Minimum Length Shoulb two Records");
		      }
		      
		      if (length > 1 ){

		      for (CSVRecord csvRecord : csvRecords) {
		    	  BulkCsvMerchantDto bulkDtos = new BulkCsvMerchantDto(
		    		  csvRecord.get("sr_no"),
		    		  csvRecord.get("contact_person"),
		              csvRecord.get("contact_number"),
		              csvRecord.get("email_id"),
		              csvRecord.get("CompanyPAN"),
		              csvRecord.get("merchant_name"),
		              csvRecord.get("business_name"),
		              csvRecord.get("BusinessType"),
		              csvRecord.get("merchant_category_code"),
		              csvRecord.get("merchant_sub_category"),
		              csvRecord.get("BusinessModel"),
		              csvRecord.get("TurnoverinlastFinancialYear"),
		              csvRecord.get("ExpectedMonthlyIncome"),
		              csvRecord.get("AverageAmountPerTransaction"),
		              csvRecord.get("AuthorisedSignatoryPAN"),
		              csvRecord.get("NameAsPerPAN"),
		              csvRecord.get("GSTINNo"),
		              csvRecord.get("mer_website_url"),
		              csvRecord.get("reseller_id"),
		              csvRecord.get("IsTestAccess"),
		              csvRecord.get("mer_return_url"),
		              csvRecord.get("is_auto_refund"),
		              csvRecord.get("hours"),
		              csvRecord.get("minutes"),
		              csvRecord.get("is_push_url"),
		              csvRecord.get("push_url"),
		              csvRecord.get("settlement_cycle"),
		              csvRecord.get("integration_type"),
		              csvRecord.get("isretryAllowed"),
		              csvRecord.get("ibps_email_notification"),
		              csvRecord.get("ibps_sms_notification"),
		              csvRecord.get("ibps_mail_remainder"),
		              csvRecord.get("Reporting_cycle"),
		              csvRecord.get("merchant_dashboard_refund"),
		              csvRecord.get("md_disable_refund_cc"),
		              csvRecord.get("md_disable_refund_dc"),
		              csvRecord.get("md_disable_refund_nb"),
		              csvRecord.get("md_disable_refund_upi"),
		              csvRecord.get("md_disable_refund_wallet"),
		              csvRecord.get("refund_api"),
		              csvRecord.get("refund_api_disable_cc"),
		              csvRecord.get("refund_api_disable_dc"),
		              csvRecord.get("refund_api_disable_nb"),
		              csvRecord.get("refund_api_disable_upi"),
		              csvRecord.get("refund_api_disable_wallet"),
		              csvRecord.get("status")
		            );

		    	  BulkDtos.add(bulkDtos);
		      }
		      }
		      return BulkDtos;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		  }


}
