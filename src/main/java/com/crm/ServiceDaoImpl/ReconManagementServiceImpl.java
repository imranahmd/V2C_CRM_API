package com.crm.ServiceDaoImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import jakarta.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.ReconManagementRepo;
import com.crm.Repository.TblTmpReconRepository;
import com.crm.Repository.TransactionMasters;
import com.crm.dto.TransactionMaster;
import com.crm.model.ReconRecord;
import com.crm.model.TblMstreconconfig;
import com.crm.model.TblTmpRecon;
import com.crm.model.TblTransactionmaster;
import com.crm.services.AutoRefundProcessor;
import com.crm.services.ReconManagementService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
//import com.recon.pg.refundProcessor.SBIAutoRefundProcessor;
//import com.recon.utils.AdminUtils;

@Service
public class ReconManagementServiceImpl implements ReconManagementService {

	private final Logger log = LoggerFactory.getLogger(ReconManagementServiceImpl.class);

//	 private static final String reconFileUploadLocation = "D:/home/recon/upload";
	// // Localfile path
	private static final String reconFileUploadLocation = "/home/recon/upload";/// home/recon/upload"; //server
																					/// file path
	
	@Autowired(required = true)
	com.crm.Repository.tblFileUplaodRepo tblFileUplaodRepo;

	@Autowired(required = true)
	private ReconManagementRepo reconManagementRepo;

	@Autowired
	private org.springframework.jdbc.core.JdbcTemplate JdbcTemplate;

	@Override
	public String saveFile(MultipartFile reconFile) {
		// System.out.println("reconFile"+reconFile);
		String message = null;
		ArrayList arrayList = new ArrayList();
		HashMap<String, String> list = new HashMap();
		String reconOriginalFile = reconFile.getOriginalFilename();
		reconOriginalFile = reconOriginalFile.replaceAll("\\s", "");
		try {
			if (reconOriginalFile.contains("..")) {
				message = "Sorry! Filename contains invalid path sequence" + reconFile;
			}
			Path fileLocation = Path.of(reconFileUploadLocation).toAbsolutePath().normalize();
			try {
				File file = new File(reconOriginalFile);
				if (!file.exists()) {
					Files.createDirectories(fileLocation);
					message = "File Saved Sucessfully";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				message = "Could not create directory where the uploaded file will be stored.";
			}
			Path uploadLocation = Path.of(reconFileUploadLocation).resolve(reconOriginalFile);
			Files.copy(reconFile.getInputStream(), uploadLocation, StandardCopyOption.REPLACE_EXISTING);

			list.put("fileName", reconOriginalFile);
			list.put("filePath", reconFileUploadLocation);
			list.put("message", message);
			arrayList.add(list);
			Gson gson = new Gson();
			String jsonArray = gson.toJson(arrayList);
			return jsonArray.toString();
		} catch (IOException ex) {
			throw new RuntimeException("Could not save file " + reconOriginalFile + ". Please try again!", ex);
		}
	}

	

	@Override
	public String getReconFileList(String ReconDate) {
		
		String jsonArray = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::" + ReconDate);

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_reconfileListRevamp(?)}");
				callableStatement.setString(1, ReconDate);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				return "Fields does not exist";
			}

			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			jsonArray = gson.toJson(arrayList);

			return jsonArray;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		log.info("ReconDate:::::::::::::::::::::::: " + ReconDate);
		return jsonArray;
	}

	@Override
	public int checkFileName(String fileName, String serviceId) {
		int count = 0;
		try {
			count = reconManagementRepo.checkFileName(fileName, serviceId);
			log.info("CheckFile Name count Return:::::<<<<<<<<<<<<< " + count);
		} catch (Exception e) {
			log.info("Checking recong File Name exception::::{}", e);
		}
		return count;
	}

	@Override
	public TblMstreconconfig getReconConfigs(String propertyName, String value) throws SQLException {
		// System.out.println("ReconManagementServiceImpl.getReconConfigs() propertyName
		// ---> "+propertyName);
		// System.out.println("ReconManagementServiceImpl.getReconConfigs() value --->
		// "+value);
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		List<TblMstreconconfig> reconConfigs = null;
		try {
			
			Map<String, Object> resultData = null;
			if (propertyName != null) {
				String query1 = "select  t1.Id, t1.ServiceId, t2.sp_name, t1.ChannelId, t1.FileType, t1.Header, t1.IS_SUCCESS, t1.Is_Refund, t1.Dateformat, t1.TEXT_SEPERATOR, t1.Field1, "
						+ "t1.Field2, t1.Field3, t1.Field4, t1.Field5, t1.Field6, t1.Field7, t1.Field8, t1.Field9, t1.Field10, t1.Field11, t1.Field12, t1.Field13, t1.Field14, "
						+ "t1.Field15, t1.RefundRequestId, t1.ArnNo "
						+ "from tbl_mstreconconfig t1 inner join tbl_mstserviceprovider t2 on t1.ServiceId = t2.sp_id "
						+ "where t1.Is_Deleted = 'N' and t1.ServiceId=? order by id desc ;";
				
				
//				String query1 = "select * from tbl_mstreconconfig as model where model.ServiceId=? and model.Is_Deleted = 'N' order by model.Id desc";

				resultData = JdbcTemplate.call(connection -> {
				
					CallableStatement callableStatement = connection.prepareCall(query1);
					callableStatement.setString(1, value);
					return callableStatement;
				}, prmtrsList);

			} else {
				String query2 = "select  t1.Id, t1.ServiceId, t2.sp_name, t1.ChannelId, t1.FileType, t1.Header, t1.IS_SUCCESS, t1.Is_Refund, t1.Dateformat, t1.TEXT_SEPERATOR, t1.Field1, "
						+ "t1.Field2, t1.Field3, t1.Field4, t1.Field5, t1.Field6, t1.Field7, t1.Field8, t1.Field9, t1.Field10, t1.Field11, t1.Field12, t1.Field13, t1.Field14, "
						+ "t1.Field15, t1.RefundRequestId, t1.ArnNo "
						+ "from tbl_mstreconconfig t1 inner join tbl_mstserviceprovider t2 on t1.ServiceId = t2.sp_id "
						+ "where t1.Is_Deleted = 'N' order by id desc;";
				// System.out.println(query2);
				resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection.prepareCall(query2);
					return callableStatement;
				}, prmtrsList);
			}

			reconConfigs = (ArrayList) resultData.get("#result-set-1");
			// System.out.println(reconConfigs);
			Map resultMap = (Map) reconConfigs.get(0);
			String FileType = (String) resultMap.get("FileType");
			TblMstreconconfig reconMstreconconfig = new TblMstreconconfig();
			reconMstreconconfig.setId(String.valueOf(resultMap.get("Id")));
			reconMstreconconfig.setServiceId(String.valueOf(resultMap.get("ServiceId")));
			reconMstreconconfig.setServiceProviderName(String.valueOf(resultMap.get("sp_name")));
			reconMstreconconfig.setHeader(String.valueOf(resultMap.get("Header")));

            reconMstreconconfig.setIsSuccess(String.valueOf(resultMap.get("IS_SUCCESS")));
			reconMstreconconfig.setIsRefund(String.valueOf(resultMap.get("Is_Refund")));

			reconMstreconconfig.setFileType(String.valueOf(resultMap.get("FileType")));
			reconMstreconconfig.setTextSeperator(String.valueOf(resultMap.get("TEXT_SEPERATOR")));
			reconMstreconconfig.setDateformat(String.valueOf(resultMap.get("Dateformat")));
			reconMstreconconfig.setField1(String.valueOf(resultMap.get("Field1")));
			reconMstreconconfig.setField2(String.valueOf(resultMap.get("Field2")));
			reconMstreconconfig.setField3(String.valueOf(resultMap.get("Field3")));
			reconMstreconconfig.setField4(String.valueOf(resultMap.get("Field4")));
			reconMstreconconfig.setField5(String.valueOf(resultMap.get("Field5")));
			reconMstreconconfig.setField6(String.valueOf(resultMap.get("Field6")));
			reconMstreconconfig.setField7(String.valueOf(resultMap.get("Field7")));
			reconMstreconconfig.setField8(String.valueOf(resultMap.get("Field8")));
			reconMstreconconfig.setField9(String.valueOf(resultMap.get("Field9")));
			reconMstreconconfig.setField10(String.valueOf(resultMap.get("Field10")));
			reconMstreconconfig.setField11(String.valueOf(resultMap.get("Field11")));
			reconMstreconconfig.setField12(String.valueOf(resultMap.get("Field12")));
			reconMstreconconfig.setField13(String.valueOf(resultMap.get("Field13")));
			reconMstreconconfig.setField14(String.valueOf(resultMap.get("Field14")));
			reconMstreconconfig.setField15(String.valueOf(resultMap.get("Field15")));
			reconMstreconconfig.setField16(String.valueOf(resultMap.get("RefundRequestId")));
			reconMstreconconfig.setField17(String.valueOf(resultMap.get("ArnNo")));

			// log.info("reconConfigs end "+reconConfigs.get(0).getId());
			return reconMstreconconfig;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			JdbcTemplate.getDataSource().getConnection().close();
		}
		return null;
	}

	@Override
	public ReconRecord saveFileUpload(ReconRecord reconRecordData) {

		ReconRecord tblReconFileupload = tblFileUplaodRepo.save(reconRecordData);
		tblReconFileupload.setStatusCode("1");
		return tblReconFileupload;
	}

	

	public List<List<String>> readDataFromXLS(File fileName, String serviceName, String formateDate) {
		List<List<String>> rows = new ArrayList<List<String>>();
		Workbook wb = null;
		try {
			// File file = new File(reconFileUploadLocation
			// +"/"+fileName.getOriginalFilename());
			wb = WorkbookFactory.create(fileName);

			Sheet mySheet = wb.getSheetAt(0);

			// Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = mySheet.iterator();

			// Traversing over each row of XLSX file
			while (rowIterator.hasNext()) {
				List<String> rowValues = new ArrayList<String>();

				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();

				if (serviceName.equalsIgnoreCase("HDFC NB")) {
					if (row.getCell(6).toString().equals("0")) {
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();

							CellType type = cell.getCellTypeEnum();

							if (type == CellType.STRING) {

								String value = cell.getStringCellValue();

								value = value.replace(",", "");
								rowValues.add(value);
							} else if (type == CellType.NUMERIC) {
								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);

							} else if (type == CellType.BOOLEAN) {
								rowValues.add(cell.getBooleanCellValue() + "");
							} else if (type == CellType.BLANK) {
								rowValues.add(cell.getBooleanCellValue() + "");
							}
							// to read cell whose cell type is formula or a cell value which is calculated
							// using excel formula
							else if (type == CellType.FORMULA) {
								switch (cell.getCachedFormulaResultType()) {
								case NUMERIC:
									String value = cell.getNumericCellValue() + "";
									value = value.replace(",", "");
									rowValues.add(value);
									break;
								case STRING:
									String stringValue = cell.getStringCellValue();
									stringValue = stringValue.replace(",", "");
									rowValues.add(stringValue);
									break;
								}
							}
							/*
							 * switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING:
							 * System.out.print(cell.getStringCellValue() + "\t"); break; case
							 * Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue() + "\t");
							 * break; case Cell.CELL_TYPE_BOOLEAN:
							 * System.out.print(cell.getBooleanCellValue() + "\t"); break; default : }
							 */
						}

						rows.add(rowValues);
					}

				} else if (serviceName.equalsIgnoreCase("Atom NB")) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						CellType type = cell.getCellTypeEnum();

						if (type == CellType.STRING) {

							String value = cell.getStringCellValue();

							String dateRegEx = "^([0-3][0-9])-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC|Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-([0-9]{4}) ([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

							if (Pattern.matches(dateRegEx, value)) {
								value = new SimpleDateFormat("dd-MM-yyyy")
										.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(value));
							}
							value = value.replace(",", "");
							rowValues.add(value);
						} else if (type == CellType.NUMERIC) {
							String value = cell.getNumericCellValue() + "";
							value = value.replace(",", "");
							rowValues.add(value);
						} else if (type == CellType.BOOLEAN) {
							rowValues.add(cell.getBooleanCellValue() + "");
						} else if (type == CellType.BLANK) {
							rowValues.add(cell.getBooleanCellValue() + "");
						}
						// to read cell whose cell type is formula or a cell value which is calculated
						// using excel formula
						else if (type == CellType.FORMULA) {
							switch (cell.getCachedFormulaResultType()) {
							case NUMERIC:
								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
								break;
							case STRING:
								String stringValue = cell.getStringCellValue();
								stringValue = stringValue.replace(",", "");
								rowValues.add(stringValue);
								break;
							}
						}

					}

					if (rowValues.get(0).isEmpty()) {
						break;
					} else {
						rows.add(rowValues);
					}

				} else if (serviceName.equalsIgnoreCase("HDFC")) {

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						CellType type = cell.getCellTypeEnum();
						if (type == CellType.STRING) {

							String value = cell.getStringCellValue();
							String dateRegEx = "^([0-3][0-9])-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)-([0-9]{4}) ([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

							if (Pattern.matches(dateRegEx, value)) {
								value = new SimpleDateFormat("dd-MM-yyyy")
										.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(value));
							}
							value = value.replace(",", "");
							rowValues.add(value.trim());
						} else if (type == CellType.NUMERIC) {

							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								DataFormatter formatter = new DataFormatter(); // creating formatter using the default
																				// locale
								String fileDate = formatter.formatCellValue(cell);

								if (isValidFormat("dd-MMM-yy", fileDate)
										|| isValidFormat("dd-MMM-yy hh:mm:ss", fileDate)) {
									rowValues.add(new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue()));
								}

							} else {

								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
							}

						} else if (type == CellType.BOOLEAN) {
							rowValues.add(cell.getBooleanCellValue() + "");
						} else if (type == CellType.BLANK) {
							rowValues.add(cell.getBooleanCellValue() + "");
						}
						// to read cell whose cell type is formula or a cell value which is calculated
						// using excel formula
						else if (type == CellType.FORMULA) {
							switch (cell.getCachedFormulaResultType()) {
							case NUMERIC:
								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
								break;
							case STRING:
								String stringValue = cell.getStringCellValue();
								stringValue = stringValue.replace(",", "");
								rowValues.add(stringValue);
								break;
							}
						}
						/*
						 * switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING:
						 * System.out.print(cell.getStringCellValue() + "\t"); break; case
						 * Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue() + "\t");
						 * break; case Cell.CELL_TYPE_BOOLEAN:
						 * System.out.print(cell.getBooleanCellValue() + "\t"); break; default : }
						 */
					}

					if ((rowValues.get(0).isEmpty()) || (rowValues.get(1).isEmpty()) || (rowValues.get(2).isEmpty())) {
						break;
					} else {
						rows.add(rowValues);

					}
				} else if (serviceName.equalsIgnoreCase("HDFCUPI")) {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						CellType type = cell.getCellTypeEnum();

						if (type == CellType.STRING) {

							String value = cell.getStringCellValue();

							String dateRegEx = "^([0-3][0-9])-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)-([0-9]{4}) ([0-1][0-9]|[2][0-3]):([0-5][0-9]):([0-5][0-9])$";

							if (Pattern.matches(dateRegEx, value)) {
								value = new SimpleDateFormat("dd-MM-yyyy")
										.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(value));
							}
							value = value.replace(",", "");
							rowValues.add(value);
						} else if (type == CellType.NUMERIC) {

							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								DataFormatter formatter = new DataFormatter(); // creating formatter using the default
																				// locale
								String fileDate = formatter.formatCellValue(cell);

								if (isValidFormat("dd-MMM-yy", fileDate)
										|| isValidFormat("dd-MMM-yy hh:mm:ss", fileDate)) {
									rowValues.add(new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue()));
								}

							} else {

								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
							}

						} else if (type == CellType.BOOLEAN) {
							rowValues.add(cell.getBooleanCellValue() + "");
						} else if (type == CellType.BLANK) {
							rowValues.add(cell.getBooleanCellValue() + "");
						}
						// to read cell whose cell type is formula or a cell value which is calculated
						// using excel formula
						else if (type == CellType.FORMULA) {
							switch (cell.getCachedFormulaResultType()) {
							case NUMERIC:
								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
								break;
							case STRING:
								String stringValue = cell.getStringCellValue();
								stringValue = stringValue.replace(",", "");
								rowValues.add(stringValue);
								break;
							}
						}
						/*
						 * switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING:
						 * System.out.print(cell.getStringCellValue() + "\t"); break; case
						 * Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue() + "\t");
						 * break; case Cell.CELL_TYPE_BOOLEAN:
						 * System.out.print(cell.getBooleanCellValue() + "\t"); break; default : }
						 */
					}

					/*
					 * if((rowValues.get(0).isEmpty() && rowValues.get(0).isBlank()) ||
					 * (rowValues.get(1).isEmpty() && rowValues.get(1).isBlank()) ||
					 * (rowValues.get(2).isEmpty() && rowValues.get(2).isBlank())) { break; }
					 */ if ((rowValues.size() == 1 || rowValues.size() == 2 || rowValues.size() == 3)) {
						break;
					} else {

						rows.add(rowValues);

					}
				} else {
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();

						CellType type = cell.getCellTypeEnum();

						if (type == CellType.STRING) {

							String value = cell.getStringCellValue();
							value = value.replace(",", "");
							rowValues.add(value);
							
						} else if (type == CellType.NUMERIC) {
//							String value = cell.getNumericCellValue() + "";
//							value = value.replace(",", "");
//							rowValues.add(value);
							
							
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								DataFormatter formatter = new DataFormatter(); // creating formatter using the default
																				// locale
								String datevalue = cell.getDateCellValue().toString();
								log.info("datevalue:::::::"+datevalue);
													                
					                SimpleDateFormat originalDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

					    	        // Parse the original date string into a Date object
					    	        Date originalDate = originalDateFormat.parse(datevalue);

					    	        // Define the desired output date format
					    	        SimpleDateFormat desiredDateFormat = new SimpleDateFormat(formateDate);

					    	        // Format the date into the desired format
					    	        String value = desiredDateFormat.format(originalDate);

					    	        System.out.println("Original Date: " + value);
					    	        value = value.replace(",", "");
			                        rowValues.add(value.trim());								
							} else {

								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
							}


						} else if (type == CellType.BOOLEAN) {
							rowValues.add(cell.getBooleanCellValue() + "");
						} else if (type == CellType.BLANK) {
							rowValues.add(cell.getBooleanCellValue() + "");
						}
						// to read cell whose cell type is formula or a cell value which is calculated
						// using excel formula
						else if (type == CellType.FORMULA) {
							switch (cell.getCachedFormulaResultType()) {
							case NUMERIC:
								String value = cell.getNumericCellValue() + "";
								value = value.replace(",", "");
								rowValues.add(value);
								break;
							case STRING:
								String stringValue = cell.getStringCellValue();
								stringValue = stringValue.replace(",", "");
								rowValues.add(stringValue);
								break;
							}
						}
						/*
						 * switch (cell.getCellType()) { case Cell.CELL_TYPE_STRING:
						 * System.out.print(cell.getStringCellValue() + "\t"); break; case
						 * Cell.CELL_TYPE_NUMERIC: System.out.print(cell.getNumericCellValue() + "\t");
						 * break; case Cell.CELL_TYPE_BOOLEAN:
						 * System.out.print(cell.getBooleanCellValue() + "\t"); break; default : }
						 */
					}
					rows.add(rowValues);
				}

			}
			if (serviceName.equalsIgnoreCase("Atom NB")) {
				rows.remove(0);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return rows;
	}

	public List<List<String>> readDataFromCSV(File fileName, String fileSeprator, String serviceName) {
		List<List<String>> rows = new ArrayList<List<String>>();
		// File file = new File(reconFileUploadLocation
		// +"/"+fileName.getOriginalFilename());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;

			if (serviceName.equalsIgnoreCase("YES BANK NB") || serviceName.equalsIgnoreCase("YESBankNB")) {
				String read = null;
				List<String> listOfStrings = new ArrayList<String>();

				read = br.readLine();

				while (read != null) {
					listOfStrings.add(read);
					read = br.readLine();
				}

				String[] strLine = listOfStrings.toArray(new String[0]);

				for (int i = 4; i < strLine.length; i++) {

					lineNumber++;
					if (strLine[i].indexOf(fileSeprator) < 0) {
						rows = null;
						break;
					}
					// break comma separated line using ","

					st = new StringTokenizer(strLine[i], fileSeprator);
					List<String> row = new ArrayList<String>();
					while (st.hasMoreTokens()) {
						// display csv values
						tokenNumber++;
						// log.info("Line # " + lineNumber + ", Token # " + tokenNumber + ", Token : "+
						// st.nextToken());
						row.add(st.nextToken());
					}
					rows.add(row);
					// reset token number
					tokenNumber = 0;
					if (i == 4) {
						i = i + 1;
					}
				}
			} else {
				String strLine = null;
				while ((strLine = br.readLine()) != null) {
					lineNumber++;
					if (strLine.indexOf(fileSeprator) < 0) {
						rows = null;
						break;
					}
					// break comma separated line using ","
					st = new StringTokenizer(strLine, fileSeprator);
					List<String> row = new ArrayList<String>();

					while (st.hasMoreTokens()) {
						// display csv values
						tokenNumber++;
						// System.out.println("Line # " + lineNumber + ", Token # " + tokenNumber + ",
						// Token : "+ st.nextToken());
						row.add(st.nextToken());
					}
					rows.add(row);
					// reset token number
					tokenNumber = 0;
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error(e.getMessage(), e);
				}
			}
		}
		return rows;
	}

	public List<String> getColumnNames(String dbNmeValue) {

		/*
		 * List<String> columnNames = tblFileUplaodRepo.GetColumns();//new
		 * ArrayList<>();
		 * 
		 * 
		 */
		/*
		 * columnNames.add("aggre_txn_id"); columnNames.add("amount");
		 * columnNames.add("ArnNo"); columnNames.add("auth_id");
		 * columnNames.add("bank_txn_id"); columnNames.add("date_time");
		 * columnNames.add("merchantid"); columnNames.add("RefundRequestId");
		 * columnNames.add("txn_status"); columnNames.add("rfu1");
		 * columnNames.add("rfu2"); columnNames.add("rfu3"); columnNames.add("rfu5");
		 * columnNames.add("rfu4"); columnNames.add("rfu5"); columnNames.add("rfu6");
		 * columnNames.add("rrn"); columnNames.add("TXN_ID");
		 * columnNames.add("txn_status");
		 */
		// tblFileUplaodRepo.GetColumns();
		log.info("dbNmeValue::::::::"+dbNmeValue);
		return tblFileUplaodRepo.GetColumns(dbNmeValue);
	}

	public int fileUploadReconNew(String columns, List<List<String>> data, int FileId, boolean isHeader,
			Map<String, String> errorMap) {
		String fileId = String.valueOf(FileId);
		log.info("fileId ::::::::::::"+fileId);
		int statusCode = 0;
		try {

			boolean isColumnMismatch = false;
			CallableStatement callableStatement = null;
			// SessionImpl session = (SessionImpl) getSession();
			Connection con = JdbcTemplate.getDataSource().getConnection();// session.connection();
			/*
			 * log.info("fileUploadRecon details" + columns + "data "+ data.toString() +
			 * "fileId " + fileId + "isHeader " + isHeader + "errorMap " + errorMap );
			 */
			log.info("fileUploadRecon details" + columns // changes in log.info to log.debug for fast file uploading
															// process
					+ "data " + data.toString() + "fileId " + fileId + "isHeader " + isHeader + "errorMap " + errorMap);
			try {
				String exception = "NA";
				int IsExceptions = 0;
				// int counter = 0;
				log.info("ReconManagementDaoImpl.java ::: // for V2 pro_FileUpload_pa_v2() :: Call Procedure : "
						+ new Timestamp(System.currentTimeMillis()));

				// String query = "{call pro_FileUploadRecon(?,?,?,?,?,?,?)}";
				// String query = "{call pro_FileUploadReconV2(?,?,?,?,?,?,?)}"; // For new
				// recon UI changes
				String query = "{call pro_FileUpload_pa_v1(?,?,?,?,?,?,?)}"; // For recon versioning

				con.setAutoCommit(false);

				callableStatement = con.prepareCall(query);

				if (data != null && data.size() > 0) {
					// Fetch Date Index from Columns String
					String fields = columns.replaceAll("'", "");

					log.info("ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: fields : " + fields);

					String[] columnArr = fields.split(",");
					int dateIndex = -1;
					if (columnArr != null && columnArr.length > 0) {
						for (int i = 0; i < columnArr.length; i++) {
							if (columnArr[i].equalsIgnoreCase("date_time")) {
								dateIndex = i;
							}
						}
					}

					log.info("ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: columnArr length : "
							+ columnArr.length);

					for (int i = 0; i < data.size(); i++) {
						String columnValues = "";

						// Fetch File Data in Table Columns format in order to insert in Temp_Table
						List<String> tableColumns = data.get(i);
						String values = "";
						for (int j = 0; j < tableColumns.size(); j++) {
							// Arrange column values in the format of Insert Statement/Query
							String columnValue = tableColumns.get(j);
							log.info("columnValue:::::::::"+columnValue);
							
							columnValue = columnValue.replaceAll("'", "");
							values = values + columnValue + ",";
						}
						values = values.replaceAll(",$", " "); // Replace last comma by Empty String

						String[] valueArr = values.split(",");

						// int columnValueLen = valueArr.length;

						// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: valueArr
						// length : "+valueArr.length);
						// log.debug("ReconManagementDaoImpl.java ::: fileUploadRecon() :: valueArr
						// length : "+valueArr.length); //changes in log.info to log.debug for fast file
						// uploading process

						if (valueArr != null && valueArr.length > 0) {
							// Convert Date into String Format
							if (dateIndex >= 0)
								valueArr[dateIndex] = "'" + valueArr[dateIndex] + "'";

							for (int k = 0; k < valueArr.length; k++) {
								if (!isNumeric(valueArr[k]) && dateIndex != k)
									valueArr[k] = "'" + valueArr[k] + "'";

								columnValues = columnValues + valueArr[k] + ",";
							}
						}

						columnValues = columnValues.replaceAll(",$", "");

						// log.debug("ReconManagementDaoImpl.java ::: fileUploadRecon() :: columnValues
						// : "+columnValues);

						// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: columnValues :
						// "+columnValues);

						if (columnArr.length == valueArr.length) {
							try {
								/*
								 * log.info("columns ----->"+columns);
								 * log.info("columnValues ----->"+columnValues);
								 * log.info("IsExceptions ----->"+IsExceptions);
								 * log.info("exception ----->"+exception); log.info("counter ----->"+i);
								 * log.info("fileId ----->"+fileId); log.info("data size ----->"+data.size());
								 */

								// changes in log.info to log.debug for fast file uploading process
								log.debug("columns ----->" + columns);
								log.debug("columnValues ----->" + columnValues);
								log.debug("IsExceptions ----->" + IsExceptions);
								log.debug("exception ----->" + exception);
								log.debug("counter ----->" + i);
								log.debug("fileId ----->" + fileId);
								log.debug("data size ----->" + data.size());

								callableStatement.setString(1, columns);
								callableStatement.setString(2, columnValues);
								callableStatement.setInt(3, IsExceptions);
								callableStatement.setString(4, exception);
								callableStatement.setInt(5, i);
								callableStatement.setString(6, fileId.toString());
								callableStatement.setInt(7, data.size());

								callableStatement.addBatch();
							} catch (Exception e) {
								log.info(
										"ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Error while setting the Paramter values for Procedure for Record Number '"
												+ i + "'.");

								log.error(e.getMessage(), e);
								IsExceptions = 1;
								statusCode = 4;
								errorMap.put("4", e.getMessage());

								break;

							}
						} else {
							log.info("ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Column Names [Length="
									+ columnArr.length + "] and Column Values [Length=" + valueArr.length
									+ "] Mismatched for Record Number '" + i + "'.");

							isColumnMismatch = true;
							statusCode = 2;
							errorMap.put("2",
									"Column Names [Length=" + columnArr.length + "] and Column Values [Length="
											+ valueArr.length + "] Mismatched for Record Number '" + i + "'.");
							break;
						}
					}

					if (!isColumnMismatch && IsExceptions == 0) {
						log.info("ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Statements Executed : "
								+ new Timestamp(System.currentTimeMillis()));

						callableStatement.executeBatch();
						statusCode = 1;
						/*
						 * ResultSet rs = callableStatement.getResultSet(); if(rs != null && rs.next())
						 * {
						 * log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: Out Param : "
						 * +rs.getString("resp")); errorMap.put("1", rs.getString("resp")); }
						 */
						con.commit();
						log.info("ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Procedure Executed : "
								+ new Timestamp(System.currentTimeMillis()));
					}
				} else {
					log.info(
							"ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: No Data Found for further processing.");

					statusCode = 3;
					errorMap.put("3", "No Data Found for further processing.");
				}
			} catch (Exception e) {
				log.info(
						"ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Error occurred in Main Catch Block.");

				log.error(e.getMessage(), e);

				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				statusCode = 4;
				errorMap.put("4", e.getMessage());
			}
			/*
			 * catch (BatchFailedException e) { log.error(e.getMessage(),e); statusCode = 3;
			 * errorMap.put(3+"", e.getMessage()); } catch (SQLException e) {
			 * log.error(e.getMessage(),e); statusCode = 3; errorMap.put(3+"",
			 * e.getMessage()); }
			 */

			finally {
				try {
					if (callableStatement != null)
						callableStatement.close();
					if (con != null)
						con.close();

				} catch (Exception ex) {
					statusCode = -1;
					errorMap.put("-1", "File Uploaded Successfully but Error occurred while closing connections.");
					log.info(
							"ReconManagementDaoImpl.java ::: pro_FileUpload_pa_v2() :: Error occurred while closing connections."
									+ ex.getMessage());
				} finally {
					callableStatement = null;
					con = null;
				}
			}
			log.info("statusCode----->" + errorMap.toString());
			return statusCode;
		} catch (Exception e) {
			log.info("Recon Exception While Call procedure::::::::::::", e);

		}
		return statusCode;
	}
	
	private boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	@Override

	public long startRecon(String FileId) {

		String jsonArray = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::" + FileId);

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_ReconProcess_pa_v2_new(?)}");
				callableStatement.setString(1, FileId);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				return 1;
			}

			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			jsonArray = gson.toJson(arrayList);

			return 1;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return 1;
	}

	@Override
	public String getReconProgressReport(String ServiceId, String ReconDate) {

		
		String jsonArray = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::" + ReconDate);

		try {
//			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_reconFileProgressReportList(?,?)}");
				callableStatement.setString(1, ReconDate);
				callableStatement.setString(2, ServiceId);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			log.info("#result-set-1"+resultData.get("#result-set-1"));
			arrayList = (ArrayList) resultData.get("#result-set-1");
			log.info("arrayList:::::::"+arrayList);
			if (arrayList.isEmpty()) {
				log.info("arrayList:::::::"+arrayList);

				return "Fields does not exist";
			}
			log.info("arrayList:::::::"+arrayList);

			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			jsonArray = gson.toJson(arrayList);
			log.info("jsonArray:::::::"+jsonArray);

			return jsonArray;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		log.info("ReconDate:::::Progress report::::::::::::::::::: " + ReconDate);
		return jsonArray;

	}

	@Override
	public String getReconExceptionExportReport(String FileId) {

		String jsonArray = null;
		JSONObject js = new JSONObject();
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::" + FileId);
		log.info("call strored procedure------>pro_CheckReconTransaction()");

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_CheckReconTransaction(?)}");
				callableStatement.setString(1, FileId);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				js.put("Status", false);
				js.put("Message", "Record not found");
				return js.toString();
			}
			log.info("call strored procedure------>pro_CheckReconTransaction() executed::::{}");

			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			jsonArray = gson.toJson(arrayList);

			return jsonArray;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		log.info("ReconDate:::::Progress report::::done::::::::::::::: " + FileId);
		return jsonArray;

	}

	@Override
	public int updateForceRecon(String Id, String type) {
		// TODO Auto-generated method stub
		int Status = 0;
		String jsonArray = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		log.info("ParmVlaues::::{}{}{}{}{}{}<<<<>>>>>::" + Id);

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_UpdateReconException(?,?)}");
				callableStatement.setInt(1, 2);
				callableStatement.setString(2, Id);
				callableStatement.setString(3, type);
				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			Status = 1;
			if (arrayList.isEmpty()) {
				return 0;
			}
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			jsonArray = gson.toJson(arrayList);

			return Status;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		log.info("ReconDate:::Exception::{}{}::Progress report::::::::::::::::::: " + Status);
		return Status;

	}

	@Override
	public String SucessForceRecon(String param) {
		// TODO Auto-generated method stub
		return null;
	}

		
	
	@Override
	public TblTransactionmaster getTxnMaster(long id) throws SQLException {
//		TblTransactionmaster TM = new TblTransactionmaster();
//		Lis<TblTransactionmaster TM = new ArrayList<>();
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		List<TblTransactionmaster> tblTransactionmaster = null;
		try {
			Map<String, Object> resultData = null;
			log.info("inside the try block:::::::::");
			
			String query1 = "select txn_id ,merchant_id,date_format(date_time,'%Y-%m-%d %h:%m:%s') as 'date_time',process_id,txn_amount,service_id,return_url,mobile_no,email_id ,udf1,udf2,id,trans_status,instrument_id,udf3,udf4,remote_ip,date_format(resp_date_time,'%Y-%m-%d %h:%m:%s') as 'resp_date_time',udf5,resp_status,resp_message,service_rrn,bank_id,card_details,card_type,error_code,sp_error_code,rms_score,rms_reason,service_txn_id,sur_charge,Modified_By from tbl_transactionmaster where id = ?";
			resultData = JdbcTemplate.call(connection -> {
				
				CallableStatement callableStatement = connection.prepareCall(query1);
				callableStatement.setLong(1, id);
				return callableStatement;
			}, prmtrsList);


			tblTransactionmaster = (ArrayList) resultData.get("#result-set-1");
			// System.out.println(reconConfigs);
			Map resultMap = (Map) tblTransactionmaster.get(0);
			TblTransactionmaster TM = new TblTransactionmaster();

			 TM.setOrderId(String.valueOf(resultMap.get("txn_id")));
			 TM.setMerchantId(String.valueOf(resultMap.get("merchant_id")));
//			 TM.setDateTime(Timestamp.valueOf(String.valueOf(resultMap.get("date_time"))));
			 String datime = String.valueOf(resultMap.get("date_time"));
			 log.info("datime::::::::"+datime);
			 TM.setDateTime(Timestamp.valueOf(datime));
			 TM.setProcessId(String.valueOf(resultMap.get("process_id")));
			 TM.setTxnAmount(Double.valueOf(String.valueOf(resultMap.get("txn_amount"))));
			 TM.setServiceId(String.valueOf(resultMap.get("service_id")));
			 TM.setReturnUrl(String.valueOf(resultMap.get("return_url")));
			 TM.setMobileNo(String.valueOf(resultMap.get("mobile_no")));
			 TM.setEmailId(String.valueOf(resultMap.get("email_id")));
			 TM.setUdf1(String.valueOf(resultMap.get("udf1")));
			 TM.setUdf2(String.valueOf(resultMap.get("udf2")));
			 TM.setId(Long.valueOf(String.valueOf(resultMap.get("id"))));
			 TM.setTransStatus(String.valueOf(resultMap.get("trans_status")));
			 TM.setInstrumentId(String.valueOf(resultMap.get("instrument_id")));
			 TM.setUdf3(String.valueOf(resultMap.get("udf3")));
			 TM.setUdf4(String.valueOf(resultMap.get("udf4")));
			 TM.setRemoteIp(String.valueOf(resultMap.get("remote_ip")));
			 
			 String resp_date_time = String.valueOf(resultMap.get("resp_date_time"));
			 TM.setRespDateTime(Timestamp.valueOf(resp_date_time));
			 
			 TM.setUdf5(String.valueOf(resultMap.get("udf5")));
			 TM.setRespStatus(Integer.valueOf(String.valueOf(resultMap.get("resp_status"))));
			 TM.setRespMessage(String.valueOf(resultMap.get("resp_message")));
			 TM.setServiceRrn(String.valueOf(resultMap.get("service_rrn")));
			 TM.setBankId(String.valueOf(resultMap.get("bank_id")));
			 TM.setCardDetails(String.valueOf(resultMap.get("card_details")));
			 TM.setCardType(String.valueOf(resultMap.get("card_type")));
			 TM.setErrorCode(String.valueOf(resultMap.get("error_code")));
			 TM.setSpErrorCode(String.valueOf(resultMap.get("sp_error_code")));
			 TM.setRmsScore(String.valueOf(resultMap.get("rms_score")));
			 TM.setRmsReason(String.valueOf(resultMap.get("rms_reason")));
			 TM.setServiceTxnId((String.valueOf(resultMap.get("service_txn_id"))));
             TM.setSurCharge(Double.valueOf(String.valueOf(resultMap.get("sur_charge"))));
             TM.setModified_By((String.valueOf(resultMap.get("Modified_By"))));
			 
			 
			 
			 
			return TM;
		}
		catch(Exception e){
			
			log.info("Exception :::::::::::"+e);
		}
		return null;
		
		
	}
	
	
	
	
	@Override
	public List<TblMstreconconfig> getReconConfigsAllData(String propertyName, String value) throws SQLException {
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		List<TblMstreconconfig> reconConfigs = null;
		
		try {
			
			Map<String, Object> resultData = null;
			log.info("getReconConfigsAllData::::::propertyName::"+propertyName+"::::value::::"+value);
			if (propertyName != null) {
				String query1 = "select Id,ServiceId,ChannelId,FileType,Header,IS_SUCCESS,Is_Refund,Dateformat,TEXT_SEPERATOR,Field1,Field2,"
						+ "Field3,Field4,Field5,Field6,Field7,Field8,Field9,Field10,Field11,Field12,Field13,Field14,Field15 ,"
						+ "RefundRequestId as Field16,ArnNo as Field17,Is_Deleted from tbl_mstreconconfig where Id=? and Is_Deleted = 'N' order by Id desc";
	
				resultData = JdbcTemplate.call(connection -> {
					
					CallableStatement callableStatement = connection.prepareCall(query1);
					callableStatement.setString(1, value);
					return callableStatement;
				}, prmtrsList);

			} else {
				String query2 = "select  t1.Id, t1.ServiceId, t2.sp_name, t1.ChannelId,t3.ChannelDescription, t1.FileType, t1.Header, t1.IS_SUCCESS, t1.Is_Refund, t1.Dateformat, t1.TEXT_SEPERATOR, t1.Field1, "
						+ "t1.Field2, t1.Field3, t1.Field4, t1.Field5, t1.Field6, t1.Field7, t1.Field8, t1.Field9, t1.Field10, t1.Field11, t1.Field12, t1.Field13, t1.Field14, "
						+ "t1.Field15, t1.RefundRequestId as Field16,t1.ArnNo as Field17 "
						+ "from tbl_mstserviceprovider t2 inner join tbl_mstreconconfig t1 on t2.sp_id=t1.ServiceId inner join tbl_mstchannel t3 on t1.ChannelId = t3.ChannelId "
						+ "where t1.Is_Deleted = 'N' order by id desc;";
				
				resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection.prepareCall(query2);
					return callableStatement;
				}, prmtrsList);
			}

			reconConfigs = (ArrayList) resultData.get("#result-set-1");
//			 log.info("reconConfigs::::::::::::"+reconConfigs);
			Map resultMap = (Map) reconConfigs.get(0);
//			log.info("resultMap::::::"+resultMap);

			 log.info("reconConfigs:::1:::::::::"+reconConfigs);
			return reconConfigs;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			JdbcTemplate.getDataSource().getConnection().close();
		}
		return null;
	}
	
	public String addDataInReconConfig(TblMstreconconfig reconConfigs){
		
		String statusval=null;
		try {
			int channelId=1;
			String typeValue = "add";
			int status = reconManagementRepo.addReconConfigData(typeValue,reconConfigs.getServiceId(),channelId,reconConfigs.getFileType(),reconConfigs.getHeader(),reconConfigs.getIsSuccess(),
					reconConfigs.getIsRefund(),reconConfigs.getDateformat(),reconConfigs.getTextSeperator(),reconConfigs.getField1(),reconConfigs.getField2(),reconConfigs.getField3(),
					reconConfigs.getField4(),reconConfigs.getField5(),reconConfigs.getField6(),reconConfigs.getField7(),reconConfigs.getField8(),reconConfigs.getField9(),
					reconConfigs.getField10(),reconConfigs.getField11(),reconConfigs.getField12(),reconConfigs.getField13(),reconConfigs.getField14(),
					reconConfigs.getField15(),reconConfigs.getField16(),reconConfigs.getField17(),reconConfigs.getCreatedBy(),reconConfigs.getId());
			log.info("status::::::"+status);
			
			if(status == 1) {
				statusval = "success";
			}
			else {
				statusval = "failed";
			}
			 
		}
		catch(Exception e) {
			log.info("e:::::::::::::::"+e);
			statusval = "failed";
		}
		
		
		return statusval;

	}
	
	
public String editDataInReconConfig(TblMstreconconfig reconConfigs){
		
		String statusval=null;
		try {
			int channelId=1;
			String typeValue = "";
			int status = reconManagementRepo.editReconConfigData(typeValue,reconConfigs.getServiceId(),channelId,reconConfigs.getFileType(),reconConfigs.getHeader(),reconConfigs.getIsSuccess(),
					reconConfigs.getIsRefund(),reconConfigs.getDateformat(),reconConfigs.getTextSeperator(),reconConfigs.getField1(),reconConfigs.getField2(),reconConfigs.getField3(),
					reconConfigs.getField4(),reconConfigs.getField5(),reconConfigs.getField6(),reconConfigs.getField7(),reconConfigs.getField8(),reconConfigs.getField9(),
					reconConfigs.getField10(),reconConfigs.getField11(),reconConfigs.getField12(),reconConfigs.getField13(),reconConfigs.getField14(),
					reconConfigs.getField15(),reconConfigs.getField16(),reconConfigs.getField17(),reconConfigs.getModifiedBy(),reconConfigs.getId());
			log.info("status:::editDataInReconConfig:::"+status);
			
			if(status == 1) {
				statusval = "success";
			}
			else {
				statusval = "failed";
			}
			 
		}
		catch(Exception e) {
			log.info("e:::::::::::::::"+e);
			statusval = "failed";
		}
		
		
		return statusval;

	}




public String getReconExceptionReportReconRefund(String fdate,String tdate,String transType) throws Exception {
	System.out.println("fdate==================="+fdate);
	System.out.println("tdate==================="+tdate);
	System.out.println("transType==================="+transType);
	
	
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	
	
		System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_ReconExceptionList(?,?,?)}");
				callableStatement.setString(1, fdate);
				callableStatement.setString(2, tdate);
				callableStatement.setString(3, transType);
				
				
				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Data Not Found");
				return arrayList.toString();
			}
			Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			  log.info("jsArray::::"+jsonArray);		 

			  return jsonArray;
			
			}  catch (Exception e1) {
				throw new Exception();
			}
			}

public int fileUploadReconOld(String columns, List<List<String>> data, int FileId, boolean isHeader,
		Map<String, String> errorMap) {
	String fileId = String.valueOf(FileId);
	log.info("fileId ::::::::::::"+fileId);
	int statusCode = 0;
	try {

		boolean isColumnMismatch = false;
		CallableStatement callableStatement = null;
		// SessionImpl session = (SessionImpl) getSession();
		Connection con = JdbcTemplate.getDataSource().getConnection();// session.connection();
		/*
		 * log.info("fileUploadRecon details" + columns + "data "+ data.toString() +
		 * "fileId " + fileId + "isHeader " + isHeader + "errorMap " + errorMap );
		 */
		log.info("fileUploadRecon details" + columns // changes in log.info to log.info for fast file uploading
														// process
				+ "data " + data.toString() + "fileId " + fileId + "isHeader " + isHeader + "errorMap " + errorMap);
		try {
			String exception = "NA";
			int IsExceptions = 0;
			// int counter = 0;
			log.info("ReconManagementDaoImpl.java ::: // for V2 pro_FileUploadReconV2_Old() :: Call Procedure : "
					+ new Timestamp(System.currentTimeMillis()));

			// String query = "{call pro_FileUploadRecon(?,?,?,?,?,?,?)}";
			// String query = "{call pro_FileUploadReconV2(?,?,?,?,?,?,?)}"; // For new
			// recon UI changes
			String query = "{call pro_FileUploadReconV2_Old(?,?,?,?,?,?,?)}"; // For recon versioning

			con.setAutoCommit(false);

			callableStatement = con.prepareCall(query);

			if (data != null && data.size() > 0) {
				// Fetch Date Index from Columns String
				String fields = columns.replaceAll("'", "");

				log.info("ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: fields : " + fields);

				String[] columnArr = fields.split(",");
				int dateIndex = -1;
				if (columnArr != null && columnArr.length > 0) {
					for (int i = 0; i < columnArr.length; i++) {
						if (columnArr[i].equalsIgnoreCase("date_time")) {
							dateIndex = i;
						}
					}
				}

				log.info("ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: columnArr length : "
						+ columnArr.length);

				for (int i = 0; i < data.size(); i++) {
					String columnValues = "";

					// Fetch File Data in Table Columns format in order to insert in Temp_Table
					List<String> tableColumns = data.get(i);
					log.info("tableColumns:::::::::::::"+tableColumns);
					String values = "";
					for (int j = 0; j < tableColumns.size(); j++) {
						// Arrange column values in the format of Insert Statement/Query
						String columnValue = tableColumns.get(j);
						log.info("columnValue:::::::::"+columnValue);
						columnValue = columnValue.replaceAll("'", "");
						log.info("columnValue:::::::1::"+columnValue);

						values = values + columnValue + ",";
						log.info("values:::::::::"+values);

					}
					values = values.replaceAll(",$", " "); // Replace last comma by Empty String
					log.info("values:::::::1::"+values);

					String[] valueArr = values.split(",");
					log.info("valueArr:::::::::"+valueArr);

					// int columnValueLen = valueArr.length;

					// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: valueArr
					// length : "+valueArr.length);
					// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: valueArr
					// length : "+valueArr.length); //changes in log.info to log.info for fast file
					// uploading process

					if (valueArr != null && valueArr.length > 0) {
						// Convert Date into String Format
						if (dateIndex >= 0)
							valueArr[dateIndex] = "'" + valueArr[dateIndex] + "'";

						for (int k = 0; k < valueArr.length; k++) {
							if (!isNumeric(valueArr[k]) && dateIndex != k)
								valueArr[k] = "'" + valueArr[k] + "'";
							log.info("columnValues:::::::::::::::::::"+valueArr[k]);
							columnValues = columnValues + valueArr[k] + ",";
							log.info("columnValues:::::::::::::::::::"+columnValues);
						}
					}

					columnValues = columnValues.replaceAll(",$", "");

					// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: columnValues
					// : "+columnValues);

					// log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: columnValues :
					// "+columnValues);

					if (columnArr.length == valueArr.length) {
						try {
							/*
							 * log.info("columns ----->"+columns);
							 * log.info("columnValues ----->"+columnValues);
							 * log.info("IsExceptions ----->"+IsExceptions);
							 * log.info("exception ----->"+exception); log.info("counter ----->"+i);
							 * log.info("fileId ----->"+fileId); log.info("data size ----->"+data.size());
							 */

							// changes in log.info to log.info for fast file uploading process
							log.info("columns ----->" + columns);
							log.info("columnValues ----->" + columnValues);
							log.info("IsExceptions ----->" + IsExceptions);
							log.info("exception ----->" + exception);
							log.info("counter ----->" + i);
							log.info("fileId ----->" + fileId);
							log.info("data size ----->" + data.size());

							callableStatement.setString(1, columns);
							callableStatement.setString(2, columnValues);
							callableStatement.setInt(3, IsExceptions);
							callableStatement.setString(4, exception);
							callableStatement.setInt(5, i);
							callableStatement.setString(6, fileId.toString());
							callableStatement.setInt(7, data.size());

							callableStatement.addBatch();
						} catch (Exception e) {
							log.info(
									"ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Error while setting the Paramter values for Procedure for Record Number '"
											+ i + "'.");

							log.error(e.getMessage(), e);
							IsExceptions = 1;
							statusCode = 4;
							errorMap.put("4", e.getMessage());

							break;

						}
					} else {
						log.info("ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Column Names [Length="
								+ columnArr.length + "] and Column Values [Length=" + valueArr.length
								+ "] Mismatched for Record Number '" + i + "'.");

						isColumnMismatch = true;
						statusCode = 2;
						errorMap.put("2",
								"Column Names [Length=" + columnArr.length + "] and Column Values [Length="
										+ valueArr.length + "] Mismatched for Record Number '" + i + "'.");
						break;
					}
				}

				if (!isColumnMismatch && IsExceptions == 0) {
					log.info("ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Statements Executed : "
							+ new Timestamp(System.currentTimeMillis()));

					callableStatement.executeBatch();
					statusCode = 1;
					/*
					 * ResultSet rs = callableStatement.getResultSet(); if(rs != null && rs.next())
					 * {
					 * log.info("ReconManagementDaoImpl.java ::: fileUploadRecon() :: Out Param : "
					 * +rs.getString("resp")); errorMap.put("1", rs.getString("resp")); }
					 */
					con.commit();
					log.info("ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Procedure Executed : "
							+ new Timestamp(System.currentTimeMillis()));
				}
			} else {
				log.info(
						"ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: No Data Found for further processing.");

				statusCode = 3;
				errorMap.put("3", "No Data Found for further processing.");
			}
		} catch (Exception e) {
			log.info(
					"ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Error occurred in Main Catch Block.");

			log.error(e.getMessage(), e);

			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			statusCode = 4;
			errorMap.put("4", e.getMessage());
		}
		/*
		 * catch (BatchFailedException e) { log.error(e.getMessage(),e); statusCode = 3;
		 * errorMap.put(3+"", e.getMessage()); } catch (SQLException e) {
		 * log.error(e.getMessage(),e); statusCode = 3; errorMap.put(3+"",
		 * e.getMessage()); }
		 */

		finally {
			try {
				if (callableStatement != null)
					callableStatement.close();
				if (con != null)
					con.close();

			} catch (Exception ex) {
				statusCode = -1;
				errorMap.put("-1", "File Uploaded Successfully but Error occurred while closing connections.");
				log.info(
						"ReconManagementDaoImpl.java ::: pro_FileUploadReconV2_Old() :: Error occurred while closing connections."
								+ ex.getMessage());
			} finally {
				callableStatement = null;
				con = null;
			}
		}
		log.info("statusCode----->" + errorMap.toString());
		return statusCode;
	} catch (Exception e) {
		log.info("Recon Exception While Call procedure::::::::::::", e);

	}
	return statusCode;
}



@Override
public void deleteFile(String iFILEID, String iUSER) {
	 System.out.println("FileId == "+iFILEID);
	 System.out.println("AddedBy == "+iUSER);
	 
	 List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		

		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			String responseVal = null;
			try {

				System.out.print("delete:::recon in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
					.prepareCall("{call pro_DeleteRecon(?,?)}");
					callableStatement.setString(1,iFILEID);
					callableStatement.setString(2,iUSER);
					
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				Object val = resultData.get("#update-count-1");
				System.out.print("val::::::::::::::: " + val);
				
			}catch (Exception e) {
				e.printStackTrace();
			}
	
}


@Override
public ReconRecord updateFileUpload(ReconRecord transientInstance) {
	// TODO Auto-generated method stub
	return null;
}



@Override
public long fileUploadRecordCount() {
	// TODO Auto-generated method stub
	return 0;
}



@Override
public HashMap<java.lang.String, java.lang.String> getReconSummary(java.lang.String fileId, java.lang.String fileName) {
	// TODO Auto-generated method stub
	return null;
}

public static Date excelSerialToDate(double serial) {
    if (serial > 0) {
        long offset = 25569; // Number of days between 1900-01-01 and 1970-01-01
        long milliseconds = (long) ((serial - offset) * 24 * 60 * 60 * 1000);
        return new Date(milliseconds);
    } else {
        return null;
    }
}
public static String formatDate(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
}
}
