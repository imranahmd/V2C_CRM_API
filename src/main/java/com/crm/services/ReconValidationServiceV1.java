package com.crm.services;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.crm.Controller.ReconController;
import com.crm.model.ReconRecord;
import com.crm.model.TblMstreconconfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class ReconValidationServiceV1 {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ReconController.class);

	private static final String reconFileUploadLocation = "/home/recon/upload"; // server file path
//	private static final String reconFileUploadLocation = "D:/home/recon/upload"; // server file path

	@Autowired
	private ReconManagementServiceV1 reconManagementService;

	@Autowired
	com.crm.Repository.tblFileUplaodRepo tblFileUplaodRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${recon.column.db.name}")
    private String dbNmeValue;

	public String fileUpload(String validteReconInput) {
		String message = "";
		ArrayList arrayList = new ArrayList();
		HashMap<String, String> list = new HashMap();
		JSONObject js = new JSONObject(validteReconInput);
		// System.out.println("validteReconInput === " + validteReconInput);
		String serviceId = js.getString("serviceId");
		String ReconDate = js.getString("ReconDate");
		String fileName = js.getString("fileName");
		File filePath = new File(reconFileUploadLocation + "/" + fileName);
		String originalFileName = filePath.getName();
		try {
			long filecount = reconManagementService.checkFileName(originalFileName, serviceId);
			logger.info("filecount ====> " + filecount);
			// If File is not exists in DB then Proceed with further checking else throw an
			// error
			String status;
			if (filecount == 0) {

				TblMstreconconfig reconConfigs = reconManagementService.getReconConfigs("serviceId", serviceId);
				logger.info("controller file type" + reconConfigs.getFileType());

				if (reconConfigs != null) {
					String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String timeString = sf.format(timestamp);
					// Insert the uploaded File details in DB

					final String uuid = UUID.randomUUID().toString().replace("-", "");
					ReconRecord reconfileuploadData = new ReconRecord();
//					reconfileuploadData.setFileId(reconConfigs.getId());
					reconfileuploadData.setFileConfigId(reconConfigs.getId());
					reconfileuploadData.setFileName(originalFileName);
					reconfileuploadData.setFileType(reconConfigs.getFileType());
					reconfileuploadData.setIs_Deleted("N");
					reconfileuploadData.setUploadedBy("admin");
					reconfileuploadData.setStatusCode("0");
					reconfileuploadData.setUploadedOn(String.valueOf(timeString));
					reconfileuploadData.setServiceId(String.valueOf(reconConfigs.getServiceId()));
					reconfileuploadData.setFileUploadPath(reconFileUploadLocation + "/" + originalFileName);
					reconfileuploadData.setTotalRecordsInFile("0");
					reconfileuploadData.setNoOfBadRecords("0");
					reconfileuploadData.setBadRecordsFileName("NA");
					reconfileuploadData.setBadRecordsFilePath("NA");
//					reconfileuploadData.setNoofException(0);
					reconfileuploadData.setNoofException("0");
					reconfileuploadData.setException("0");
					reconfileuploadData.setVersion("2.0");
					ReconRecord tblfileUploadstatus = reconManagementService.saveFileUpload(reconfileuploadData);
					status = tblfileUploadstatus.getStatusCode();
					System.out.println("tblfileUploadstatus" + tblfileUploadstatus.getFileId() + " status: " + status);
					if (status == "1") {
						String fileType = tblfileUploadstatus.getFileType();
						tblfileUploadstatus.setStatusCode("0");
						if (fileType.equalsIgnoreCase("xlsx")
								&& (extension.equalsIgnoreCase("xlsx") || extension.equalsIgnoreCase("xls")))
							fileType = extension;
						if (!extension.equalsIgnoreCase(fileType)) {
							// File extension is not as per Recon Configuration for the selected SP/Bank.

							message = "File extension is not as per Recon Configuration for the selected SP/Bank.";
							reconfileuploadData.setFileId(tblfileUploadstatus.getFileId());
							reconfileuploadData.setStatusCode("3");
							tblFileUplaodRepo.save(reconfileuploadData);

						}
						if (tblfileUploadstatus.getStatusCode().equalsIgnoreCase("0")) {
							// ValidReconDetails(reconConfigs, tblfileUploadstatus, filePath);

							message = "1#" + tblfileUploadstatus.getFileId();
							reconfileuploadData.setFileId(tblfileUploadstatus.getFileId());
							reconfileuploadData.setStatusCode("0"); // yet to start
							tblFileUplaodRepo.save(reconfileuploadData);
							// HashMap<String, String> reconSummaryMap =
							// reconManagementService.getReconSummary(tblFileupload.getFileId().toString(),
							// tblFileupload.getFileName());

						}

					} else {
						// Error while storing file details in DB
						message = "0#Error while storing file details.";
					}

				} else {
					message = "0#Recon Configuration not found against the selected SP/Bank.";
				}

				/*
				 * list.put("message", message); arrayList.add(list); Gson gson = new Gson();
				 * String jsonArray = gson.toJson(arrayList); return message;
				 */

			} else {
				message = "0#File is Already Exists.Please upload a different file.";
			}

		} catch (Exception e) {
			logger.info("Fatching error from file upload:::::::::", e);
		}
		logger.info("Response in file:::::::::::::::: " + message);
		return message;
	}

	@Async
	public void ValidReconDetails(TblMstreconconfig reconConfigs, ReconRecord tblfileUploadstatus, File filePath) {
		ReconRecord reconfileuploadData = new ReconRecord();
		String message = null;
		String originalFileName = filePath.getName();
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

		String fileSeprator = reconConfigs.getTextSeperator();
		if (reconConfigs.getTextSeperator().equalsIgnoreCase("COM"))
			fileSeprator = ",";
		else if (reconConfigs.getTextSeperator().equalsIgnoreCase("PIP"))
			fileSeprator = "|";
		else if (reconConfigs.getTextSeperator().equalsIgnoreCase("TAB"))
			fileSeprator = "\t";

		List<List<String>> rows = null;
		String serviceName = reconConfigs.getServiceProviderName();
		logger.info("serviceid:::::::::"+serviceName);
		logger.info("serviceid:::::::::"+reconConfigs.getDateformat());
		// Read File Content
		if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			rows = reconManagementService.readDataFromXLS(filePath, serviceName,reconConfigs.getDateformat());
		} else {
			rows = reconManagementService.readDataFromCSV(filePath, fileSeprator, serviceName);
		}

		if (rows == null || rows.size() <= 0) {
			message = "Unable to Read File Content.Please check File Content or check Text Separator Configuration for the selected SP/Bank.";
		}
		logger.info("dbNmeValue ===> " + dbNmeValue);
		List<String> columnNames = reconManagementService.getColumnNames(dbNmeValue);
		logger.info("reconConfigs::::::::::::::::::::::::::::: " + reconConfigs.toString());
		Map<String, Object> columnDataMap = getColumnsOfTmpReconToInsertData(columnNames, reconConfigs);

		String columns = (String) columnDataMap.get("columns");
		Map<String, String> columsMap = (Map<String, String>) columnDataMap.get("columnMap");
		int dateIndex = Integer.parseInt(columsMap.get("date_time"));

		boolean isHeader = false;
		String fileDate = null;
		DateFormat dateFormatFile = new SimpleDateFormat(reconConfigs.getDateformat());
		Date givenFileDate = null;
		Date transactionDate = null;

		if (reconConfigs.getHeader().equalsIgnoreCase("Y")) {
			List<String> firstRow = rows.get(0);
			boolean isHeaderExist = true;

			for (int i = 0; i < firstRow.size(); i++) {
				if (isNumeric(firstRow.get(i))) {
					isHeaderExist = false;
					break;
				}
			}

			if (!isHeaderExist) {
				message = "Header is not present in uploaded file.Please check isHeader Configuration for the selected SP/Bank.";
			} else {
				isHeader = true;
				List<String> secondRow = rows.get(1);
				fileDate = secondRow.get(dateIndex - 1);
			}

		} else if (reconConfigs.getHeader().equalsIgnoreCase("N")) {
			fileDate = rows.get(0).get(dateIndex - 1);
			try {
				givenFileDate = dateFormatFile.parse(fileDate);
			} catch (ParseException e) {
				message = "Header is present in uploaded file.Please check isHeader Configuration for the selected SP/Bank.";
			}
		}

		try {
			givenFileDate = dateFormatFile.parse(fileDate);
			// DateFormat transDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			transactionDate = new SimpleDateFormat("yyyy-MM-dd").parse(reconConfigs.getDateformat());
			// .parse(fileUploadForm.getTransactionDate());

		} catch (ParseException e) {
			// Please Check File Date Format Configuration against the selected SP/Bank.
			// fileUploadForm.setMessage("TDERROR");

			message = "Unable to parse File/Txn Date.Please Check File Date Format Configuration against the selected SP/Bank.";
		}

		int amountIndex = Integer.parseInt(columsMap.get("amount"));
		int atrnIndex = Integer.parseInt(columsMap.get("TXN_ID"));
		int bankRefNoIndex = 0;
		if (columsMap.get("bank_txn_id") != null) {
			bankRefNoIndex = Integer.parseInt(columsMap.get("bank_txn_id"));
		}

		List<String> errorList = new ArrayList<String>();

		if (isHeader) {
			rows.remove(0);
			isHeader = false;
		}

		tblfileUploadstatus.setFileId(tblfileUploadstatus.getFileId());
		tblfileUploadstatus.setTotalRecordsInFile(String.valueOf(rows.size()));

		// reconManagementService.updateFileUpload(tblfileUploadstatus);

		tblFileUplaodRepo.save(tblfileUploadstatus);

		String fileAtrn = null;
		String fileAtrnString = "";

		for (int i = 0; i < rows.size(); i++) {
			String fileBankRefNo = "";
			if (bankRefNoIndex > 0) {
				fileBankRefNo = rows.get(i).get(bankRefNoIndex - 1);
			}

			String fileAmount = rows.get(i).get(amountIndex - 1);
			if (fileAmount == null || fileAmount.isEmpty()) {
				errorList.add("Amount is Null/Blank for ATRN ~ " + fileAtrn);
			} else /* if(!fileAmount.isEmpty()) */
			{
				try {
					Double.parseDouble(fileAmount);
				} catch (NumberFormatException e) {
					errorList.add("Invalid Amount [" + fileAmount + "] for ATRN ~ " + fileAtrn);
					// log.error(e.getMessage(),e);
				}
			}
		}

		if (errorList.size() == 0 && fileAtrnString != null && fileAtrnString.length() > 0) {
			List<Object> dbAtrnList = new ArrayList<Object>();

			ArrayList<String> fileAtrnList = new ArrayList<String>(Arrays.asList(fileAtrnString.split("','")));
		}

		Map<String, String> errorMap = new HashMap<String, String>();
		List<List<String>> rowValues = getRowValuesToDB(columns, rows, columsMap, isHeader);

		// System.out.println("columns===: "+columns);
		// System.out.println("rowValues===: "+rowValues);
		// System.out.println("getFileId===: "+reconfileuploadData.getFileId());
		// System.out.println("isHeader===: "+isHeader);
		// System.out.println("errorMap===: "+errorMap);
		logger.info("reconConfigs::dateTime formate::"+reconConfigs.getDateformat());
		String formate = reconConfigs.getDateformat();
		logger.info("formate:::::::"+formate);
		int statusCode = reconManagementService.fileUploadReconNew(columns, rowValues, tblfileUploadstatus.getFileId(),
				isHeader, errorMap);

		if (statusCode == 1) {
			message = "File Added Successfully.";

			// HashMap<String, String> reconSummaryMap =
			// reconManagementService.getReconSummary(tblFileupload.getFileId().toString(),
			// tblFileupload.getFileName());

		} else {
			message = "Something went wrong.File Not Processed successfully.";
		}
	}

	private Map<String, Object> getColumnsOfTmpReconToInsertData(List<String> columnNames,
			TblMstreconconfig tblMstreconconfig) {
		Map<String, String> columsMap = new HashMap<>();
		logger.info("tbl:::::::::: " + tblMstreconconfig.toString());
		List<Integer> columsSeq = new ArrayList<Integer>();

		// log.info(columnNames.get(0)+","+tblMstreconconfig.getField1());
		if (tblMstreconconfig.getField1() != "null" && tblMstreconconfig.getField1().length() > 0) {
			columsMap.put(columnNames.get(0), tblMstreconconfig.getField1());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField1()));
		}
		if (tblMstreconconfig.getField2() != "null" && tblMstreconconfig.getField2().length() > 0) {
			columsMap.put(columnNames.get(1), tblMstreconconfig.getField2());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField2()));
		}
		if (tblMstreconconfig.getField3() != "null" && tblMstreconconfig.getField3().length() > 0) {
			columsMap.put(columnNames.get(2), tblMstreconconfig.getField3());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField3()));
		}
		if (tblMstreconconfig.getField4() != "null" && tblMstreconconfig.getField4().length() > 0) {
			columsMap.put(columnNames.get(3), tblMstreconconfig.getField4());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField4()));
		}
		if (tblMstreconconfig.getField5() != "null" && tblMstreconconfig.getField5().length() > 0) {
			columsMap.put(columnNames.get(4), tblMstreconconfig.getField5());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField5()));
		}
		if (tblMstreconconfig.getField6() != "null" && tblMstreconconfig.getField6().length() > 0) {
			columsMap.put(columnNames.get(5), tblMstreconconfig.getField6());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField6()));
		}
		if (tblMstreconconfig.getField7() != "null" && tblMstreconconfig.getField7().length() > 0) {
			columsMap.put(columnNames.get(6), tblMstreconconfig.getField7());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField7()));
		}
		if (tblMstreconconfig.getField8() != "null" && tblMstreconconfig.getField8().length() > 0) {
			columsMap.put(columnNames.get(7), tblMstreconconfig.getField8());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField8()));
		}
		if (tblMstreconconfig.getField9() != "null" && tblMstreconconfig.getField9().length() > 0) {
			columsMap.put(columnNames.get(8), tblMstreconconfig.getField9());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField9()));
		}
		if (tblMstreconconfig.getField10() != "null" && tblMstreconconfig.getField10().length() > 0) {
			columsMap.put(columnNames.get(9), tblMstreconconfig.getField10());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField10()));
		}
		if (tblMstreconconfig.getField11() != "null" && tblMstreconconfig.getField11().length() > 0) {
			columsMap.put(columnNames.get(10), tblMstreconconfig.getField11());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField11()));
		}
		if (tblMstreconconfig.getField12() != "null" && tblMstreconconfig.getField12().length() > 0) {
			columsMap.put(columnNames.get(11), tblMstreconconfig.getField12());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField12()));
		}
		if (tblMstreconconfig.getField13() != "null" && tblMstreconconfig.getField13().length() > 0) {
			columsMap.put(columnNames.get(12), tblMstreconconfig.getField13());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField13()));
		}
		if (tblMstreconconfig.getField14() != "null" && tblMstreconconfig.getField14().length() > 0) {
			columsMap.put(columnNames.get(13), tblMstreconconfig.getField14());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField14()));
		}
		if (tblMstreconconfig.getField15() != "null" && tblMstreconconfig.getField15().length() > 0) {
			columsMap.put(columnNames.get(14), tblMstreconconfig.getField15());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField15()));
		}

		if (tblMstreconconfig.getField16() != "null" && tblMstreconconfig.getField16().length() > 0) {
			columsMap.put(columnNames.get(15), tblMstreconconfig.getField16());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField16()));
		}
		if (tblMstreconconfig.getField17() != "null" && tblMstreconconfig.getField17().length() > 0) {
			columsMap.put(columnNames.get(16), tblMstreconconfig.getField17());
			columsSeq.add(Integer.parseInt(tblMstreconconfig.getField17()));
		}

		Collections.sort(columsSeq);

		String columns = "";
		if (columsSeq != null && columsSeq.size() > 0) {
			for (int i = 0; i < columsSeq.size(); i++) {
				int index = columsSeq.get(i);
				for (Map.Entry<String, String> entry : columsMap.entrySet()) {
					if (index == Integer.parseInt(entry.getValue())) {
						// columns = columns + "'"+entry.getKey()+"'" +",";
						columns = columns + entry.getKey() + ",";
					}
				}

			}
		}
		if (!columns.equals("")) {
			columns = columns.replaceAll(",$", "");
		}

		Map<String, Object> columnDataMap = new HashMap<String, Object>();
		columnDataMap.put("columnMap", columsMap);
		columnDataMap.put("columns", columns);

		return columnDataMap;
	}

	private boolean isNumeric(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	private List<List<String>> getRowValuesToDB(String colmmns, List<List<String>> rowData,
			Map<String, String> columnMap, boolean isHeader) {
		List<List<String>> rows = new ArrayList<List<String>>();
		if (rowData != null && rowData.size() > 0) {
			if (isHeader) {
				rowData.remove(0);
			}

			for (int i = 0; i < rowData.size(); i++) {
				List<String> row = rowData.get(i);
				// log.info("Row " + i + " : " + rowData.get(i));
				// log.debug("Row " + i + " : " + rowData.get(i)); //changes in log.info to
				// log.debug for fast file uploading process

				List<String> selectedRowValues = new ArrayList<String>();

				String[] columnNames = colmmns.split(",");
				if (columnNames != null && columnNames.length > 0) {
					for (int j = 0; j < columnNames.length; j++) {
						// log.info("Columnname : " + columnNames[j] + " ::: ColumnMap : " +
						// columnMap.get(columnNames[j]));
						// log.debug("Columnname : " + columnNames[j] + " ::: ColumnMap : " +
						// columnMap.get(columnNames[j])); //changes in log.info to log.debug for fast
						// file uploading process
						int index = Integer.parseInt(columnMap.get(columnNames[j])) - 1;
						selectedRowValues.add(row.get(index));
					}
					rows.add(selectedRowValues);
				}
			}
		}
		return rows;
	}
	
	@Async
	public String startRecon(String FileId) throws SQLException {
		List<ReconRecord> recondata = new ArrayList<ReconRecord>();
		CallableStatement callableStatement = null;
		ResultSet rs = null;
		String result =null;
		Connection con = jdbcTemplate.getDataSource().getConnection();
		logger.info("call strored procedure------>pro_ReconProcess_pa_v2_new()");
		try {
		//	String query = "{call pro_ReconProcess()}";
			String query = "{call pro_ReconProcess_pa_v2(?)}";  // added for recon versioning

			callableStatement = con.prepareCall(query);
			callableStatement.setString(1,FileId);

			callableStatement.execute();
			 
			rs = callableStatement.getResultSet();
			result= "Y";
			logger.info("procedure executed:::::::::::::::::::");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (callableStatement != null) callableStatement.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return result;
	}

}
