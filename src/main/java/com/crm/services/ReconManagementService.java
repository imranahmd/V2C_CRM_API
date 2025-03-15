package com.crm.services;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import com.crm.model.ReconRecord;
import com.crm.model.TblFileupload;
import com.crm.model.TblMstreconconfig;
import com.crm.model.TblTransactionmaster;

public interface ReconManagementService {
	
	public String saveFile(MultipartFile reconFile);
	public void deleteFile(String fileId, String addedby);
	public String getReconFileList(String ReconDate);
	public int checkFileName(String fileName, String serviceId);
	public TblMstreconconfig getReconConfigs(String propertyName, String value) throws Exception;
	public ReconRecord saveFileUpload(ReconRecord transientInstance);
	public ReconRecord updateFileUpload(ReconRecord transientInstance);
	public long fileUploadRecordCount();
	public List<List<String>> readDataFromXLS(File fileName, String serviceName, String dateFormat);
	public List<List<String>> readDataFromCSV(File fileName, String fileSeprator, String serviceName);
	public List<String> getColumnNames(String dbNmeValue);
//	public List<String> getColumnNames();
	//int drishti
	public int fileUploadReconNew(String columns, List<List<String>> data,int fileId, boolean isHeader,Map<String, String> errorMap);
	public HashMap<String,String> getReconSummary(String fileId,String fileName);
	@Async
	public long startRecon(String FileId);
	public String getReconProgressReport(String ServiceId,String ReconDate);
	String getReconExceptionExportReport(String ReconDate);
	int updateForceRecon(String Id,String type);
	
	public String SucessForceRecon(String param);
//	public String FailForceRecon(String param);
	public TblTransactionmaster getTxnMaster(long id) throws Exception;
	public List<TblMstreconconfig> getReconConfigsAllData(String propertyName, String value) throws Exception;
	public String addDataInReconConfig(TblMstreconconfig reconConfigs);
	public String editDataInReconConfig(TblMstreconconfig reconConfigs);
	public String getReconExceptionReportReconRefund(String fdate,String tdate,String transType)throws Exception;
	public int fileUploadReconOld(String columns, List<List<String>> data,int fileId, boolean isHeader,Map<String, String> errorMap);

}
