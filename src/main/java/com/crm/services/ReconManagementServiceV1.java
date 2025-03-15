package com.crm.services;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.crm.model.ReconRecord;
import com.crm.model.TblMstreconconfig;

public interface ReconManagementServiceV1 {
	
	public int checkFileName(String fileName, String serviceId);
	public TblMstreconconfig getReconConfigs(String propertyName, String value) throws Exception;
	public ReconRecord saveFileUpload(ReconRecord transientInstance);
	public List<List<String>> readDataFromXLS(File fileName, String serviceName, String formatDate);
	public List<List<String>> readDataFromCSV(File fileName, String fileSeprator, String serviceName);
	public List<String> getColumnNames(String dbNmeValue);
	public int fileUploadReconNew(String columns, List<List<String>> data,int fileId, boolean isHeader,Map<String, String> errorMap);
	
}
