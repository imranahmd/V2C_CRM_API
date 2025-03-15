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

import com.crm.dto.BulkCsvMerMdrDto;
import com.crm.dto.BulkCsvMerchantDto;

public class MdrBulkCsvHelper {
	
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "sr_no", "merchant_id", "sp_id", "bank_id", "instrument_id", "min_amt", "max_amt" 
			  ,"mdr_type", "aggr_mdr","reseller_mdr", "base_rate", "min_base_rate", "max_base_rate", "min_mdr",
			  "max_mdr", "mid", "tid", "channel_id", "start_date", "end_date", "prefrences"
			  , "surcharge", "payout", "card_variant_name", "network", "instrument_brand", "bank_mdr_type", "min_reseller_mdr", "max_reseller_mdr", "reseller_mdr_type", "payout_escrow"};

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }
	  
	  public static List<BulkCsvMerMdrDto> csvToMstmerchantMdr(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<BulkCsvMerMdrDto> BulkMDRDtos = new ArrayList<BulkCsvMerMdrDto>();

		      Iterable<CSVRecord> csvMdrRecords = csvParser.getRecords();
		      Long length = csvParser.getRecordNumber();
		      
//		      int size = attributes.size();
		      if (length == 0) {
		          throw new IOException("CSV cannot be empty");
		      }

		      
		      
		      if (length == 1 ) {
		          throw new IOException("Minimum Length Shoulb two Records");
		      }
		      
		      if (length > 1 ) {
		      for (CSVRecord csvRecord : csvMdrRecords) {
		    	  BulkCsvMerMdrDto bulkmdrDtos = new BulkCsvMerMdrDto(
		    		  
		    		  csvRecord.get("merchant_id"),
		              csvRecord.get("sp_id"),
		              csvRecord.get("bank_id"),
		              csvRecord.get("instrument_id"),
		              csvRecord.get("min_amt"),
		              csvRecord.get("max_amt"),
		              csvRecord.get("mdr_type"),
		              csvRecord.get("aggr_mdr"),
		              csvRecord.get("reseller_mdr"),
		              csvRecord.get("base_rate"),
		              csvRecord.get("min_base_rate"),
		              csvRecord.get("max_base_rate"),
		              csvRecord.get("min_mdr"),
		              csvRecord.get("max_mdr"),
		              csvRecord.get("mid"),
		              csvRecord.get("tid"),
		              csvRecord.get("channel_id"),
		              csvRecord.get("start_date"),
		              csvRecord.get("end_date"),
		              csvRecord.get("prefrences"),
		              csvRecord.get("surcharge"),
		              csvRecord.get("payout"),
		              csvRecord.get("card_variant_name"),
		              csvRecord.get("network"),
		              csvRecord.get("instrument_brand"),
		              csvRecord.get("bank_mdr_type"),
		              csvRecord.get("min_reseller_mdr"),
		              csvRecord.get("max_reseller_mdr"),
		              csvRecord.get("reseller_mdr_type"),
		              csvRecord.get("payout_escrow"),
		              csvRecord.get("sr_no")
		            );

		    	  BulkMDRDtos.add(bulkmdrDtos);
		      	}
		      }
		      return BulkMDRDtos;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		  }



}
