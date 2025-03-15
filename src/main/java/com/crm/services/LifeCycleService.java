package com.crm.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Controller.ReconController;
import com.crm.Repository.LifeCycleTransactionRepo;
import com.crm.dto.LifeCycleDto;

import com.crm.helper.FileUploadHelper;
import com.crm.model.MerchantKycDoc;



@Service
public class LifeCycleService {
	private static Logger log = LoggerFactory.getLogger(LifeCycleService.class);

	
	public static String uploadDirectory = "/home/LifecycleUpload";
	public static String uploadDirectoryGenrate = "/home/GenrateSettlmentUpload";
	public static String uploadDirectoryBulk = "/home/BulkTransactionUpdate/TransactionUpdate";

	@Autowired
	private LifeCycleTransactionRepo lifeCycleTransactionRepo;
	
	public Map<String, Object> insertLifeCycleDetails(List<LifeCycleDto> lifeCycleDto){
		Map<String, Object> Msg = null ;
		String message = null;
		String trans =null;
		JSONArray array = new JSONArray();
		JSONObject js1 = new JSONObject();
		 Map<String,LifeCycleDto> docTypes = new HashMap<>();
		 String TransStatus=lifeCycleDto.get(0).getRrnStatus();
		 String FileName=lifeCycleDto.get(0).getFileName();
		 String Remark=lifeCycleDto.get(0).getRemarks();
		 String AddedBy=lifeCycleDto.get(0).getAddedBy();


		for(LifeCycleDto dto : lifeCycleDto)
        {
			
			
			 if(trans==null)
			 {
				 trans =dto.getTransactionId();
			 }else {
				 trans=trans.concat("#"+dto.getTransactionId());
			 }
			
			
        }
	//String trans =dto.getTransactionId();
	//BigInteger bigIntegerStr=new BigInteger(trans);
		log.info("Transaction by hash:::::::::::: "+trans);
		List<Map<String, Object>> StatusCount = lifeCycleTransactionRepo.updaterecords(trans,TransStatus,FileName,Remark,AddedBy);
		log.info("Transaction by hash::::::StatusCount:::::: "+StatusCount);
		js1.put("Data", StatusCount);
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public Map<String, Object> uploadFileStatus(MultipartFile imageFile, String addedBy) {
		Map<String, Object> Msg = null ;
		StringBuilder filesname = new StringBuilder();
		String FilexlName = imageFile.getOriginalFilename();
		File filexl = new File(FilexlName);
		String jsonArray = null;
		JSONObject js1 = new JSONObject();
		if(imageFile !=null) {
			String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
			// if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("docx") 
					// || extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg")) {
				String MerchantId = addedBy;
				Date date = Calendar.getInstance().getTime();  
		        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		        String strDate = dateFormat.format(date);  
		        String strDatewospc = strDate.replaceAll("\\s", "");
		        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
				String fileOrgname = imageFile.getOriginalFilename();
				String filesname1 = uploadDirectory + "/" + MerchantId +"/";
				fileOrgname= fileOrgname.replaceAll("\\s", "");
				String nameFileconvert =  strDatewosp +"_"+fileOrgname;
				File file = new File(filesname1);
				if (!file.exists()) {            
		            file.mkdirs();            
		        }	
				try {
					Path path = Path.of(filesname1,nameFileconvert);
					filesname.append(nameFileconvert);
					Files.write(path, imageFile.getBytes());
					String DocRef = filesname1 + nameFileconvert;
					js1.put("Status", "Success");
					js1.put("Message", "File Uploaded ");
					js1.put("DocPath", DocRef);
				} catch (Exception e) {
					e.printStackTrace();
				}
			// }
			
		}else {
			js1.put("Status", "Error");
			js1.put("Message", "File Not Found ");
		}
		Msg = js1.toMap();
		return Msg;
	}
	
	public Map<String, Object> findAuditTrail( String trnsactionId) {
		Map<String, Object> Msg = null ;
		String jsonArray = null;
		JSONObject js1 = new JSONObject();		
		BigInteger bigIntegerStr=new BigInteger(trnsactionId);
		try {
			List<Map<String, Object>> resuldata = lifeCycleTransactionRepo.findbyTransId(bigIntegerStr);
			js1.put("Data", resuldata);
			
		}catch(Exception e) {
			log.info("Audit Trail ::::::::::::::::::{} "+e.getMessage());
		}
		
		Msg = js1.toMap();
		return Msg;
	}
	
	public Map<String, Object> findSettleClaim(String Type,String SPId,String Instrument,String Cycle,String ReconDate, String UserId){
		Map<String, Object> Msg = null ;
		String jsonArray = null;
		JSONObject js1 = new JSONObject();	
		int type = Integer.parseInt(Type);
		int spId = Integer.parseInt(SPId);
		List<Map<String, Object>> resuldata = lifeCycleTransactionRepo.findSettleReport(type,spId,Instrument,Cycle,ReconDate, UserId);
		if(!resuldata.isEmpty()) {
			js1.put("Status", "Success");
			js1.put("Message", "Record Found");
			js1.put("Data", resuldata);
		}else {
			js1.put("Status", "Error");
			js1.put("Message", "Record Not Found");
		}
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public Map<String, Object> findSettleClaimT0(String Type,String SPId,String Instrument,String Cycle,String ReconDate, String UserId){
		Map<String, Object> Msg = null ;
		String jsonArray = null;
		JSONObject js1 = new JSONObject();	
		int type = Integer.parseInt(Type);
		int spId = Integer.parseInt(SPId);
		List<Map<String, Object>> resuldata = lifeCycleTransactionRepo.findSettleReportT0(type,spId,Instrument,Cycle,ReconDate, UserId);
		if(!resuldata.isEmpty()) {
			js1.put("Status", "Success");
			js1.put("Message", "Record Found");
			js1.put("Data", resuldata);
		}else {
			js1.put("Status", "Error");
			js1.put("Message", "Record Not Found");
		}
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public Map<String, Object> findSettleClaimGentrate(String SPId,String Instrument,String Cycle,String ReconDate, String UserId){
		Map<String, Object> Msg = null ;
		String jsonArray = null;
		JSONObject js1 = new JSONObject();	
		JSONObject js = new JSONObject();
		int spId = Integer.parseInt(SPId);
		int cycleId = Integer.parseInt(Cycle);
		String selectcount = lifeCycleTransactionRepo.findrecordsduplicate(spId, Instrument, cycleId, ReconDate);
		if(selectcount.equals("0")) {
			List<Tuple> resuldata = lifeCycleTransactionRepo.findSettleReportGenerate(spId,Instrument,Cycle,ReconDate, UserId);
			log.info("ResultData::::::::::::::::::{} "+resuldata.size());
			if(!resuldata.isEmpty()) {				
					js = createxlsxFile(resuldata, UserId, Cycle);
					if(js.get("Status").equals("Success")) {
						String filename =js.getString("FileName");
						String fileLocation = js.getString("FilePath");
						List<Map<String, Object>> result = lifeCycleTransactionRepo.findSettleReportGenerateUpdate(spId, Instrument, cycleId, ReconDate, UserId, filename, fileLocation );
						js1.put("Status", "Success");
						js1.put("Message", js.getString("Message"));
						js1.put("Data", result);
//						js1.put("FileName", nameFileconvert);
					}		
				}else {
					log.info("ResultData:::else:::::::::::::::{} "+resuldata.size());
					js1.put("Status", "Error");
					js1.put("Message", "Record Not Found");
			}
		}else {
			js1.put("Status", "Error");
			js1.put("Message", "For The Given Combination File Already Generated. Please Use Search for File Details");
		}
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public Map<String, Object> findSettleClaimGentrateTo(String SPId,String Instrument,String Cycle,String ReconDate, String UserId){
		Map<String, Object> Msg = null ;
		String jsonArray = null;
		JSONObject js1 = new JSONObject();	
		JSONObject js = new JSONObject();
		int spId = Integer.parseInt(SPId);
		int cycleId = Integer.parseInt(Cycle);
		String selectcount = lifeCycleTransactionRepo.findrecordsduplicate(spId, Instrument, cycleId, ReconDate);
		if(selectcount.equals("0")) {
			try {
			List<Tuple> resuldata = lifeCycleTransactionRepo.findSettleReportGenerateTo(spId,Instrument,Cycle,ReconDate, UserId);
			log.info("ResultDataT0::::::::::::::::::{} "+resuldata.size());
			if(!resuldata.isEmpty()) {				
					js = createxlsxFile(resuldata, UserId, Cycle);
					if(js.get("Status").equals("Success")) {
						String filename =js.getString("FileName");
						String fileLocation = js.getString("FilePath");
						List<Map<String, Object>> result = lifeCycleTransactionRepo.findSettleReportGenerateUpdateTo(spId, Instrument, cycleId, ReconDate, UserId, filename, fileLocation );
						js1.put("Status", "Success");
						js1.put("Message", js.getString("Message"));
						js1.put("Data", result);

					}		
				}else {
					log.info("ResultData:::else:::::::::::::::{} "+resuldata.size());
					js1.put("Status", "Error");
					js1.put("Message", "Record Not Found");
			}
			}catch(Exception e) {
				log.info("ResultDataT0 Execute Error::::::::::::::::::{} "+e.getMessage());
			}
		}else {
			js1.put("Status", "Error");
			js1.put("Message", "For The Given Combination File Already Generated. Please Use Search for File Details");
		}
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public JSONObject createxlsxFile(List<Tuple> resuldata, String userId, String cycleId) {
		JSONObject js1 = new JSONObject();
		StringBuilder filesname = new StringBuilder();
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("data");
		XSSFRow hrow = sheet.createRow(0);
		XSSFRow vrow = sheet.createRow(1);
		int rowCount = 0;
		int rowCounthead = 0;        

		for(Tuple value : resuldata) {
			int columnCount = -1;
            int columnCounthead = -1;
			Row row = sheet.createRow(++rowCount);
			Row rowhead = sheet.createRow(rowCounthead);
			List<TupleElement<?>> elements = value.getElements();
		    for (TupleElement<?> element : elements ) {
		    	Cell vcell = row.createCell(++columnCount);
		        vcell.setCellValue("" + value.get(element.getAlias()));
		        Cell hcell = rowhead.createCell(++columnCounthead);
		        hcell.setCellValue(element.getAlias());
		    }

		}
		String MerchantId = userId;
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
        String strDatewospc = strDate.replaceAll("\\s", "");
        String strDatewosp = strDatewospc.replaceAll("[-:]*", "");
		String fileOrgname = (strDatewosp +".xlsx");
		String filesname1 = uploadDirectoryGenrate + "/" + MerchantId +"/";
		fileOrgname= fileOrgname.replaceAll("\\s", "");
		String nameFileconvert =  "Settlement_Cycle"+ cycleId +"_"+fileOrgname;
		File file = new File(filesname1);
		if (!file.exists()) {            
            file.mkdirs();            
        }	
		try {
			Path path = Path.of(filesname1,nameFileconvert);
			filesname.append(nameFileconvert);
			String DocRef = filesname1 + nameFileconvert;
			FileOutputStream outputStream = new FileOutputStream(DocRef);
			workBook.write(outputStream);
			log.info("File Create :::Name:::::::::::::::{} "+ DocRef);
			js1.put("Status", "Success");
			js1.put("Message", "File Created and Uploaded ");
			js1.put("FilePath", DocRef);
			js1.put("FileName", nameFileconvert);
		} catch (Exception e) {
			log.info("Error in File Create :::Name:::::::::::::::{} "+ e.getMessage());
			e.printStackTrace();
			js1.put("Status", "Error");
			js1.put("Message", "File Not Created " + e.getMessage());
		}
		
		return js1;
		
	}
	
	public Map<String, Object> insertBulkLifeCycleDetails(MultipartFile file, String UserId,String Remarks,String filesName){
		Map<String, Object> Msg = null ;
		String message = null;
		JSONArray array = new JSONArray();
		JSONObject js1 = new JSONObject();
		String FilexlName = file.getOriginalFilename();
		File filexl = new File(FilexlName);
		String rrnStatus =null;
		Map<String,LifeCycleDto> docTypes = new HashMap<>();
		if(file !=null) {
			String trans = null;
			String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
			log.info ("File  extension :::  " + extension);
			log.info("File name=" + FilexlName);
			if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
				String filesPath = uploadDirectoryBulk + "/" ;
				File newFile = new File(uploadDirectoryBulk + "/" + FilexlName );
				
				if (!newFile.exists()) {            
					newFile.mkdirs();    
					log.info("File Make=" + newFile);
		        }
				if(!("").equals(filesPath)){
					
					if (newFile.exists()) {
						newFile.delete();
						log.info("File Deleted=" + newFile);
					}
				}
				Path path = Path.of(filesPath,FilexlName);
				try {
					Files.write(path, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				List<List<String>> rows = FileUploadHelper.readDataFromXLS(newFile);
				if (rows != null) {
					int Datasize = rows.size();
					for (int i = 1; i < Datasize; i++) {
						List<String> dataRows = rows.get(i);
						trans = null;
						if (dataRows.get(0).contains("'")) {
							 trans =dataRows.get(0).substring(0);
						}else {
							 trans = dataRows.get(0);
						}
						rrnStatus = dataRows.get(1);						
						if(!trans.equals("")){
							//BigInteger bigIntegerStr=new BigInteger(trans);
							
							trans=trans.concat("#"+trans);
							
						}else {
							JSONObject item = new JSONObject();
							item.put("TransacId", "NA");
							item.put("Status", "Error");
							item.put("Message", "TransactionId Not Found ");
							item.put("StatusCode", rrnStatus);
							array.put(item);
						}
					}
					log.info(rrnStatus);
					log.info("TransaStatus:::::::::{}{}{}{}{:::::::::::::::: "+trans);

					
					List<Map<String, Object>> StatusCount = lifeCycleTransactionRepo.updaterecords(trans, rrnStatus, filesName,Remarks, UserId);
					 js1.put("Data", StatusCount);
				}				
			}		
		}
		Msg = js1.toMap();
		return Msg;
		
	}
	
	public Map<String, Object> searchBulkLifeCycleDetails(MultipartFile file, String Type){
		Map<String, Object> Msg = null ;
		String message = null;
		JSONArray array = new JSONArray();
		JSONObject js1 = new JSONObject();
		String FilexlName = file.getOriginalFilename();
		File filexl = new File(FilexlName);
		Map<String,LifeCycleDto> docTypes = new HashMap<>();
		if(file !=null) {
			String extension = FilexlName.substring(FilexlName.lastIndexOf(".") + 1);
			log.info ("File  extension :::  " + extension);
			log.info("File name=" + FilexlName);
			if (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")) {
				String filesPath = uploadDirectoryBulk + "/" ;
				File newFile = new File(uploadDirectoryBulk + "/" + FilexlName );
				
				if (!newFile.exists()) {            
					newFile.mkdirs();    
					log.info("File Make=" + newFile);
		        }
				if(!("").equals(filesPath)){
					
					if (newFile.exists()) {
						newFile.delete();
						log.info("File Deleted=" + newFile);
					}
				}
				Path path = Path.of(filesPath,FilexlName);
				try {
					Files.write(path, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				List<List<String>> rows = FileUploadHelper.readDataFromXLS(newFile);
				if (rows != null) {
					int Datasize = rows.size();
					for (int i = 1; i < Datasize; i++) {
						List<String> dataRows = rows.get(i);
						String trans = null;
						String rrnStatus = null;
						if (dataRows.get(0).contains("'")) {
							if(Type.equals("TR")) {
							 trans =dataRows.get(0).substring(0);
							 rrnStatus = "";
							}else if(Type.equals("RR")){
								rrnStatus =dataRows.get(0).substring(0);
								trans = "";
							}
						}else {
							if(Type.equals("TR")) {
							 trans = dataRows.get(0);
							 rrnStatus = "";
							}else if(Type.equals("RR")) {
								rrnStatus = dataRows.get(0);
								trans = "";
							}
						}
												
						if(!trans.equals("")){
							BigInteger bigIntegerStr=new BigInteger(trans);
							try {
								
								Map<String, Object> StatusCount = lifeCycleTransactionRepo.findreccords(bigIntegerStr, rrnStatus);
								if (!StatusCount.isEmpty()) {
									Map<String, Object> map = new HashMap<String, Object>(StatusCount);
									map.put("Status", "Matched");
									array.put(map);
								}else {
									JSONObject item = new JSONObject();
									item.put("TransactionId", trans );
									item.put("Status", "Not Matched");
									item.put("merchant_id", "NA");
									item.put("merchant_name", "NA");
									item.put("txn_Id", "NA");
									item.put("TxnDate", "NA");
									item.put("RRNNo", "NA");
									item.put("LifeCycleStatus", "NA");
									item.put("StatusDescription", "NA");
									item.put("ServiceProvider", "NA");
									item.put("SPId", "NA");
									array.put(item);
								}

							}catch(Exception e) {
								log.info("Error in Update Records" + e.getMessage());
							}
						}else if(!rrnStatus.equals("")){
//							BigInteger bigIntegerStr=new BigInteger(trans);
							try {
								
								Map<String, Object> StatusCount = lifeCycleTransactionRepo.findreccordsRR(trans, rrnStatus);
								if (!StatusCount.isEmpty()) {
									Map<String, Object> map = new HashMap<String, Object>(StatusCount);
									map.put("Status", "Matched");
									array.put(map);
								}else {
									JSONObject item = new JSONObject();									
									item.put("Status", "Not Matched");
									item.put("TransactionId", "NA");
									item.put("merchant_id", "NA");
									item.put("merchant_name", "NA");
									item.put("txn_Id", "NA");
									item.put("TxnDate", "NA");
									item.put("RRNNo", rrnStatus);
									item.put("LifeCycleStatus", "NA");
									item.put("StatusDescription", "NA");
									item.put("ServiceProvider", "NA");
									item.put("SPId", "NA");
									array.put(item);
								}

							}catch(Exception e) {
								log.info("Error in Update Records" + e.getMessage());
							}
						}else {
							JSONObject item = new JSONObject();
							item.put("TransacId", "NA");
							item.put("Status", "Not Matched");
							item.put("Message", "TransactionId OR RRN NO Not Found ");

							array.put(item);
						}
					}
					js1.put("Data", array);
				}				
			}		
		}
		Msg = js1.toMap();
		return Msg;
		
	}
}
