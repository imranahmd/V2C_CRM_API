package com.crm.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.crm.dto.BulkCsvMerMdrDto;
import com.crm.dto.BulkXlsInvoiceDto;


public class lbpsBulkHelper {
	private static Logger log = LoggerFactory.getLogger(lbpsBulkHelper.class);

//	public static String TYPE = "application/vnd.ms-excel"; ---xlx
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	 static String[] HEADERs = {"InvoiceNumber", "Amount", "ValidUpTo", "Name", "EmailId", "Mobile", "EmailNotification", "SMSNotification"};
	
	 public static boolean hasXlsFormat(MultipartFile file) {
		 
		 log.info("file.getContentType()::::::"+file.getContentType());

		    if (!TYPE.equals(file.getContentType())) {
		      return false;
		    }

		    return true;
		  }
	 
	 
	 
	 public static List<BulkXlsInvoiceDto> xlsBulkData(InputStream isa){
		  
		  try {
			  
			  List<BulkXlsInvoiceDto> Bulkinvoices = new ArrayList<BulkXlsInvoiceDto>();
			  log.info("inside the try ");
			  byte[] buffer = IOUtils.toByteArray(isa);
			    InputStream is1 = new ByteArrayInputStream(buffer);
			    Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(is1);

			log.info("workbook:::::"+workbook);
			
			

			
			Sheet rowCount = workbook.getSheetAt(0);
		
			int length = rowCount.getLastRowNum();
			log.info("length:::::::::"+length);
		//	length = length-1;
			
			log.info("length:::::::::"+length);
			
			 if (length == 0) {
		          throw new IOException("Xlsx cannot be empty");
		      }

		      
		      
		      if (length == 1 ) {
		          throw new IOException("Minimum Length should two Records");
		      }
		      
		      Iterator<Row> iterator = rowCount.iterator();
		      if (length > 1 ) {
		    	  Row nextRow = iterator.next();
		    	  while (iterator.hasNext()) {
		    		   nextRow = iterator.next();
		    		  
		    	        Iterator<Cell> cellIterator = nextRow.cellIterator();
		    	        BulkXlsInvoiceDto aBook = new BulkXlsInvoiceDto();
		    	        

		    	        	
		    	        	 while (cellIterator.hasNext()) {
				    	            Cell nextCell = cellIterator.next();
				    	          
				    	            int columnIndex = nextCell.getColumnIndex();
				    	            log.info("columnIndex:::::::::"+columnIndex);
				    	 
				    	            switch (columnIndex) {
				    	            case 0:
				    	                aBook.setInvoiceNumber(nextCell.getStringCellValue());
				    	                break;
				    	                
				    	            case 1:
				    	                aBook.setAmount((double) nextCell.getNumericCellValue());
				    	                break;
				    	                
				    	            case 2:
				    	                aBook.setValidUpTo(nextCell.getDateCellValue());
				    	                break;
				    	            
				    	            case 3:
				    	                aBook.setRemarks(nextCell.getStringCellValue());
				    	                break;
				    	                
				    	            case 4:
				    	                aBook.setName(nextCell.getStringCellValue());
				    	                break;
				    	                
				    	            case 5:
				    	                aBook.setEmailId(nextCell.getStringCellValue());
				    	                break;
				    	                
				    	            case 6:
				    	                aBook.setMobile((long) nextCell.getNumericCellValue());
				    	                break;
				    	                
				    	            case 7:
				    	                aBook.setEmailNotification(nextCell.getStringCellValue());
				    	                break;
				    	                
				    	            case 8:
				    	                aBook.setsMSNotification(nextCell.getStringCellValue());
				    	                break;
				    	            }
				    	 
				    	 
				    	        }
				    	        Bulkinvoices.add(aBook);
     
		    	  }
		    	  
		    	  
		    	  
		      }
			
		      return Bulkinvoices;
		}catch (IOException e) {
		      throw new RuntimeException("fail to parse Xlsx file: " + e.getMessage());
		}
	  
	  }
	
	 
//	 private static Object getCellValue(Cell cell) {
//		    switch (cell.getCellType()) {
//		    case Cell.getStringCellValue():
//		        return cell.getStringCellValue();
//		 
//		    case Cell.CELL_TYPE_BOOLEAN:
//		        return cell.getBooleanCellValue();
//		 
//		    case Cell.CELL_TYPE_NUMERIC:
//		        return cell.getNumericCellValue();
//		    }
//		 
//		    return null;
//		}
	
}
