package com.crm.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.PayoutRepository;
import com.crm.dto.PayoutStatusdto;

@Service
public class PayoutService {
	private static Logger log = LoggerFactory.getLogger(PayoutService.class);

	String fileLocation = "/home/KYCDOCUMENTS/PayoutGenerate1/";

	@Value("${spring.datasource.url}")
	private String dbURL;

	@Value("${spring.datasource.username}")
	private String dbName;

	@Value("${spring.datasource.password}")
	private String dbPass;

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Autowired
	private PayoutRepository payoutRepository;

	// processing the payout by hdfc or yes bank

	@Async
	public JSONObject payoutProcess(String username, String filePath, String payoutBy, PayoutStatusdto payoutStatus) {
		System.out.println("username=================PayoutService===================" + username);
		System.out.println("filePath=================PayoutService===================" + filePath);
		System.out.println("payoutStatus=============PayoutService===================" + payoutStatus);
		System.out.println("payoutBy=============PayoutService===================" + payoutStatus);

		JSONObject tFileName = new JSONObject();
		JSONObject mFileName = new JSONObject();

		JSONObject js1 = new JSONObject();
		try {

			log.info("PayoutService.java  ::::  Started :  " + new Date());

			if (payoutBy.equalsIgnoreCase("HDFC")) {

				java.sql.Date dateTime = payoutStatus.getDateTime();
				log.info("dateTime::::::" + dateTime);
				String transactionDate = dateTime.toString();
				log.info("transactionDate::::::" + transactionDate);
				if (!payoutStatus.isStatus()) {
					// Transaction wise payout file

					tFileName = createWorkbook(transactionDate, "TransactionWise", username, payoutBy, payoutStatus);
					log.info("PayoutService.java::::::::::tFileName::::::::::" + tFileName);
//						if (tFileName != null || !tFileName.isEmpty()) {
					if (tFileName.get("Status") == "Success") {

						mFileName = createWorkbook(transactionDate, "MerchantWise", username, payoutBy, payoutStatus);
						log.info("mFileName::::::::::::::::::::" + mFileName);

						if (mFileName.get("Status") == "Success") {

							mFileName = mFileName;
						} else {

							List<Map<String, Object>> result = payoutRepository.selectDataIfTranExist(transactionDate,
									payoutStatus.getId(), payoutStatus.getPayoutEscrow());
							log.info("result:::::::" + result);
							js1.put("Status", "Success");

							if (payoutStatus.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
								js1.put("ResponseMessage", "You can download payout files.");
							} else {
								js1.put("ResponseMessage", "You can download Yes Bank payout files.");
							}
							js1.put("Data", result);
							js1.put("merchantwise_payout_path", "null");
							mFileName = js1;
						}

					} else {
						js1.put("ResponseMessage", "Data Not Available.");
						mFileName = js1;
					}

				}

			} else {
				// yes bank
				java.sql.Date dateTime = payoutStatus.getDateTime();
				log.info("dateTime::::::" + dateTime);
				String transactionDate = dateTime.toString();
				log.info("transactionDate::::::" + transactionDate);
				if (!payoutStatus.isStatus()) {
					// Transaction wise payout file

					tFileName = createWorkbookYBL(transactionDate, "TransactionWise", username, payoutBy, payoutStatus);
					log.info("tFileName::::::::::::::::::::" + tFileName);

					if (tFileName.get("Status") == "Success") {

						mFileName = createWorkbookYBL(transactionDate, "MerchantWise", username, payoutBy,
								payoutStatus);
						log.info("mFileName::::::::::::::::::::" + mFileName);

						if (mFileName.get("Status") == "Success") {

							mFileName = mFileName;
						} else {

							List<Map<String, Object>> result = payoutRepository.selectDataIfTranExist(transactionDate,
									payoutStatus.getId(), payoutStatus.getPayoutEscrow());
							log.info("result:::::::" + result);
							js1.put("Status", "Success");

							if (payoutStatus.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
								js1.put("ResponseMessage", "You can download payout files.");
							} else {
								js1.put("ResponseMessage", "You can download Yes Bank payout files.");
							}
							js1.put("Data", result);
							js1.put("merchantwise_payout_path", "null");

							mFileName = js1;
						}

					}

					else {
						js1.put("ResponseMessage", "Data Not Available.");
						mFileName = js1;
					}
				}

			}

		} catch (Exception e1) {

		}
		return mFileName;
	}

	// create workbook for trans wise
	synchronized JSONObject createWorkbook(String transDate, String transactionType, String username, String payoutBy,
			PayoutStatusdto payoutStatustableval) {
		String fileName = null;
		String fileNameXls;
		int type = 0;
		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject();
		try {

			long dateTime = System.currentTimeMillis();

			if (transactionType != null && transactionType.equals("TransactionWise")) {
				fileName = "Transactionwise_Payout_" + dateTime + ".txt";// csv
				fileNameXls = "Transactionwise_Payout_" + dateTime + ".xlsx";
				type = 1;
				List<Tuple> resuldata = payoutRepository.getTranctionwisePayout(type, transDate, username, payoutBy);

				log.info("resuldata:::::::::::" + resuldata);

				if (!resuldata.isEmpty()) {

					js = createReportDataToXLSX(resuldata, fileNameXls);

					if (js.get("Status").equals("Success")) {

						String filename = js.getString("FileName");
						String fileLocation1 = js.getString("FilePath");
						int typev = 1;

						List<Map<String, Object>> result = payoutRepository.updatePayoutStatus(typev,
								payoutStatustableval.getId(), payoutStatustableval.getPayoutEscrow(), filename,
								fileLocation1);
						log.info("result:::::::" + result);
						js1.put("Status", "Success");
						js1.put("Message", js.getString("Message"));

						if (payoutStatustableval.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
							js1.put("ResponseMessage", "You can download payout files.");
						} else {
							js1.put("ResponseMessage", "You can download Yes Bank payout files.");
						}

						js1.put("Data", result);

					}
				} else {
					log.info("PayoutService:::else:::::::::::::::{} " + resuldata.size());
					js1.put("Status", "Failed");
					js1.put("Message", "Record Not Found");
				}

			} else {
				fileName = "Merchantwise_Settlement_" + dateTime + ".txt";// csv
				fileNameXls = "Merchantwise_Settlement_" + dateTime + ".xlsx";
				type = 2;
				Map<String, Object[]> transactionData = new TreeMap<String, Object[]>();
				transactionData = getMerchantwisePayout(transDate, username, payoutBy, type);

//					List<Tuple> resuldata = payoutRepository.getTranctionwisePayout(type, transDate, username, payoutBy);
				log.info("transactionData:::::::::::" + transactionData);

				if (!transactionData.isEmpty()) {

//						js = createReportDataToXLSX(resuldata, fileNameXls);
					js = createReportDataToXLSX1(transactionData, fileNameXls);
					if (js.get("Status").equals("Success")) {

						String filename = js.getString("FileName");
						String fileLocation1 = js.getString("FilePath");
						int typev = 2;

						List<Map<String, Object>> result = payoutRepository.updatePayoutStatus(typev,
								payoutStatustableval.getId(), payoutStatustableval.getPayoutEscrow(), filename,
								fileLocation1);
						log.info("result:::::::" + result);
						js1.put("Status", "Success");
						js1.put("Message", js.getString("Message"));

						if (payoutStatustableval.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
							js1.put("ResponseMessage", "You can download payout files.");
						} else {
							js1.put("ResponseMessage", "You can download Yes Bank payout files.");
						}

						js1.put("Data", result);

					}
				} else {
//						log.info("payoutGenerate Service:::else:::::::::::::::{} " + resuldata.size());
					js1.put("Status", "Failed");
					js1.put("Message", "Record Not Found");
				}

			}

		} catch (Exception e) {
			log.info("Error in payout file generation" + e.getMessage());
			log.error(e.getMessage(), e);
		}

		return js1;
	}

	// write data to xlsx file --> trans wise and merchant -wise(HDFC) and
	// trans-wise (YES)
	public JSONObject createReportDataToXLSX(List<Tuple> resuldata, String fileName) {

		JSONObject js1 = new JSONObject();
		StringBuilder filesname = new StringBuilder();
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("ReportData");
		XSSFRow hrow = sheet.createRow(0);
		XSSFRow vrow = sheet.createRow(1);
		int rowCount = 0;
		int rowCounthead = 0;

		for (Tuple value : resuldata) {
			int columnCount = -1;
			int columnCounthead = -1;
			Row row = sheet.createRow(++rowCount);
			Row rowhead = sheet.createRow(rowCounthead);
			List<TupleElement<?>> elements = value.getElements();
			for (TupleElement<?> element : elements) {
				Cell vcell = row.createCell(++columnCount);
				vcell.setCellValue("" + value.get(element.getAlias()));
				Cell hcell = rowhead.createCell(++columnCounthead);
				hcell.setCellValue(element.getAlias());
			}

		}

		String filesname1 = fileLocation;
		String nameFileconvert = fileName;
		File file = new File(filesname1);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			Path path = Path.of(filesname1, nameFileconvert);
			filesname.append(nameFileconvert);
			String DocRef = filesname1 + nameFileconvert;
			FileOutputStream outputStream = new FileOutputStream(DocRef);
			workBook.write(outputStream);
			log.info("File Create :::Name:::::::::::::::{} " + DocRef);
			js1.put("Status", "Success");
			js1.put("Message", "File Created and Uploaded ");
			js1.put("FilePath", DocRef);
			js1.put("FileName", nameFileconvert);
		} catch (Exception e) {
			log.info("Error in File Create :::Name:::::::::::::::{} " + e.getMessage());
			e.printStackTrace();
			js1.put("Status", "Failed");
			js1.put("Message", "File Not Created " + e.getMessage());
		}

		return js1;

	}

	// get data for merchant wise -> from the db

	// merchant wise code:::::::::::::

	public Map<String, Object[]> getMerchantwisePayout(String transDate, String username, String payoutBy, int type) {

		Map<String, Object[]> tranctioniseData = null;
		Statement st = null;
		ResultSet rs = null;
		CallableStatement csmt = null;
		Connection connection = null;
		try {

			log.info("driverClassName:::::::::::::" + driverClassName);
			log.info("dbPass:::::::::::::::::" + dbPass);
			log.info("dbName:::::::::::::" + dbName);
			log.info("dbURL:::::::::::" + dbURL);

			Class.forName(driverClassName);

			connection = DriverManager.getConnection(dbURL, dbName, dbPass);
			csmt = connection.prepareCall("{call PPpro_PayoutGenerate_pa_v1(?,?,?,?)}");
			csmt.setInt(1, type);
			csmt.setString(2, transDate);
			csmt.setString(3, username);
			csmt.setString(4, payoutBy);
			csmt.execute();
//			connection.commit();

			rs = csmt.getResultSet();
			ResultSetMetaData rsmd = rs.getMetaData();

			tranctioniseData = new TreeMap();
			int columns = rsmd.getColumnCount();
			Object[] columnName = new Object[columns];

			int rowCount;
			for (rowCount = 1; rowCount <= columns; ++rowCount) {
				columnName[rowCount - 1] = rsmd.getColumnLabel(rowCount);
			}

			/*
			 * tranctioniseData.put("1", columnName);
			 */
			for (rowCount = 2; rs.next(); ++rowCount) {
				Object[] row = new Object[columns];

				for (int i = 1; i <= columns; ++i) {
					row[i - 1] = rs.getString(i);
					// log.info(row[i - 1]);
					// log.info("Product Id ="+row[0] + "-- Net Settlement Value "+row [3] );
				}

				// log.info("Product Id "+row[0]);
				tranctioniseData.put(String.valueOf(rowCount), row);
				// log.info("tranctioniseData******"+tranctioniseData);
			}

			if (tranctioniseData.size() > 1) {
				Set<Entry<String, Object[]>> entrySet = tranctioniseData.entrySet();
				for (Entry<String, Object[]> objectArray : entrySet) {
					String key = objectArray.getKey();
					Object[] value = tranctioniseData.get(key);
					String productId = value[1].toString();
					String totalamount = value[3].toString();
					System.out.println("productId" + productId);
					if (productId.contains("|")) {
						// tranctioniseData.remove(objectArray);

						String[] split = productId.split("\\|");

						for (int j = 0; j < split.length; j++) {
							String productShares = split[j];
							System.out.println("productShares" + split[j]);
							String[] productSharesArray = productShares.split("~");
							String share = productSharesArray[1];
							System.out.println("share" + share);

							Double valueOf = Double.valueOf(totalamount);
							Double sharedSettlemet = valueOf * Double.valueOf(share) / 100;
							System.out.println("sharedSettlemet" + sharedSettlemet);

							int i = Integer.parseInt(key);
							log.info("Remove row" + (i));
							tranctioniseData.remove(String.valueOf((i)));
							log.info("add calculated value row" + rowCount++);
							Object[] valueNew = new Object[value.length];
							for (int i1 = 0; i1 < value.length; i1++) {
								if (i1 == 3 || i1 == 1) {
									valueNew[3] = String.valueOf(sharedSettlemet);
									valueNew[1] = String.valueOf(productSharesArray[0]);
									log.info("add calculated value row" + valueNew[1]);
									log.info("add calculated value row" + valueNew[3]);
								} else {
									valueNew[i1] = value[i1];
								}
							}
							tranctioniseData.put(String.valueOf(rowCount++), valueNew);
							log.info("tranctioniseData++++++++++" + tranctioniseData);
						}
					}

				}
			}

			Set<Entry<String, Object[]>> entrySet2 = tranctioniseData.entrySet();
			for (Entry<String, Object[]> objectArray : entrySet2) {
				Object[] value = objectArray.getValue();
				for (int i = 0; i < value.length; i++) {
					log.info("----------------" + value[i]);
				}
			}

		} catch (Exception var24) {
			log.error("PayoutSettlementDaoImpl.java ::: ::: Getting error :: ", var24);
			var24.printStackTrace();
		} finally {
			// session.close();

			try {
				if (rs != null) {
					rs.close();
				}

				if (st != null) {
					((Statement) st).close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException var23) {
				log.error("PayoutSettlementDaoImpl.java ::: ::: Getting error :: ", var23);
				var23.printStackTrace();
			}

		}

		log.info("tranctioniseData::::" + tranctioniseData);
		return tranctioniseData;
	}

	// code for the DataToXLSX1
	public JSONObject createReportDataToXLSX1(Map<String, Object[]> transactionData, String fileName)
			throws IOException {
		JSONObject js1 = new JSONObject();
		StringBuilder filesname = new StringBuilder();
		SXSSFWorkbook workbook;
		// if (fileName.endsWith("xlsx")) { //for heap memory issue resolved
		workbook = new SXSSFWorkbook(1000);
		Set<String> keyid = transactionData.keySet();

		try {
			if (keyid != null && keyid.size() > 0) {
				Sheet sheet = workbook.createSheet("ReportData");
				int rowid = 0;
				for (String key : keyid) {
					Row row = sheet.createRow(rowid++);
					Object[] objectArr = transactionData.get(key);
					int cellid = 0;
					for (Object obj : objectArr) {
						Cell cell = row.createCell(cellid++);
						cell.setCellValue((String) obj);
					}
				}
				String filesname1 = fileLocation;
				String nameFileconvert = fileName;

				File folder = new File(filesname1);
				if (!folder.exists()) {
					folder.mkdir();
				}

				Path path = Path.of(filesname1, nameFileconvert);
				filesname.append(nameFileconvert);
				String DocRef = filesname1 + nameFileconvert;

				FileOutputStream fos = new FileOutputStream(DocRef);
				workbook.write(fos);
				fos.flush();
				fos.close();
				workbook.dispose();
				workbook.close();

				js1.put("Status", "Success");
				js1.put("Message", "File Created and Uploaded ");
				js1.put("FilePath", fileLocation);
				js1.put("FileName", nameFileconvert);

			}

			log.info("Getting Null Value>>>>>>>>>>>>>>>>>");
		} catch (Exception e) {
			log.info("Error in File Create :::Name:::::::::::::::{} " + e.getMessage());
			e.printStackTrace();
			js1.put("Status", "Failed");
			js1.put("Message", "File Not Created " + e.getMessage());
		}

		return js1;
	}

	// createWorkbookYBL

	synchronized JSONObject createWorkbookYBL(String transDate, String transacyionType, String username,
			String payoutBy, PayoutStatusdto payoutStatustableval) {
		String fileNameXls;
		int type = 0;

		JSONObject js1 = new JSONObject();
		JSONObject js = new JSONObject();
		try {

			log.info("transacyionType:::::::::::::::" + transacyionType);
			long dateTime = System.currentTimeMillis();

			if (transacyionType != null && transacyionType.equals("TransactionWise")) {
				fileNameXls = "Transactionwise_Payout_YBL" + dateTime + ".xlsx";
				type = 1;
				List<Tuple> resuldata = payoutRepository.getTranctionwisePayout(type, transDate, username, payoutBy);
				log.info("resuldata:::::::::::" + resuldata);
				if (!resuldata.isEmpty()) {

					js = createReportDataToXLSX(resuldata, fileNameXls);

					String filename = js.getString("FileName");
					String fileLocation1 = js.getString("FilePath");
					int typev = 1;

					List<Map<String, Object>> result = payoutRepository.updatePayoutStatus(typev,
							payoutStatustableval.getId(), payoutStatustableval.getPayoutEscrow(), filename,
							fileLocation1);
					log.info("result:::::::" + result);
					js1.put("Status", "Success");
					js1.put("Message", js.getString("Message"));
					if (payoutStatustableval.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
						js1.put("ResponseMessage", "You can download payout files.");
					} else {
						js1.put("ResponseMessage", "You can download Yes Bank payout files.");
					}

//						js1.put("ResponseMessage", "You can download Yes Bank payout files after some time.");
					js1.put("Data", result);
				} else {
					log.info("PayoutGenerate:::Service:::::::::::::::{} " + resuldata.size());
					js1.put("Status", "Failed");
					js1.put("Message", "Record Not Found");
				}

			} else {
				fileNameXls = "Merchantwise_Settlement_YBL" + dateTime + ".xlsx";
				type = 3;
//					List<Tuple> resuldata = payoutRepository.getTranctionwisePayout(type, transDate, username, payoutBy);

//					log.info("resuldata:::::::::::" + resuldata);
				Map<String, Object[]> transactionData = new TreeMap<String, Object[]>();
				transactionData = getMerchantwisePayout(transDate, username, payoutBy, type);

				log.info("transactionData:::::::::::" + transactionData);

				if (!transactionData.isEmpty()) {

					js = createReportDataToXLSXYBL(transactionData, fileNameXls);
//						js = createReportDataToXLSX(resuldata, fileNameXls);
					String filename = js.getString("FileName");
					String fileLocation1 = js.getString("FilePath");
					int typev = 2;

					List<Map<String, Object>> result = payoutRepository.updatePayoutStatus(typev,
							payoutStatustableval.getId(), payoutStatustableval.getPayoutEscrow(), filename,
							fileLocation1);
					log.info("result:::::::" + result);
					js1.put("Status", "Success");
					js1.put("Message", js.getString("Message"));
					if (payoutStatustableval.getPayoutEscrow().equalsIgnoreCase("HDFC")) {
						js1.put("ResponseMessage", "You can download payout files.");
					} else {
						js1.put("ResponseMessage", "You can download Yes Bank payout files.");
					}

//						js1.put("ResponseMessage", "You can download Yes Bank payout files after some time.");
					js1.put("Data", result);
				} else {
//						log.info("PayoutGenerate:::Service:::::::::::::::{} " + resuldata.size());
					js1.put("Status", "Failed");
					js1.put("Message", "Record Not Found");
				}

			}

		} catch (Exception e) {
			log.info("Error in payout file generation" + e.getMessage());
			log.error(e.getMessage(), e);
		}
		/* String filenames = ""+fileName+","+fileNameTM+""; */

		return js1;
	}

	// createReportDataToXLSXYBL --> merchant-wise (YSE Bank)

	public JSONObject createReportDataToXLSXYBL(Map<String, Object[]> transactionData, String fileName)
			throws IOException {
		SXSSFWorkbook workbook;
		JSONObject js1 = new JSONObject();
		StringBuilder filesname = new StringBuilder();
		// if (fileName.endsWith("xlsx")) { //for heap memory issue resolved
		workbook = new SXSSFWorkbook(1000);
		int i = 0;
		Set<String> keyid = transactionData.keySet();

		try {
			if (keyid != null && keyid.size() > 0) {
				Sheet sheet = workbook.createSheet("ReportData");

				Row row1 = sheet.createRow(0);
				row1.createCell(0).setCellValue("H");
				row1.createCell(1).setCellValue(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				row1.createCell(2).setCellValue("VPay Test");

				int rowid = 1;
				Double amount = 0.0;
				for (String key : keyid) {
					Row row = sheet.createRow(rowid++);
					Object[] objectArr = transactionData.get(key);
					int cellid = 0;
					for (Object obj : objectArr) {
						Cell cell = row.createCell(cellid++);
						cell.setCellValue((String) obj);
					}
					Double l = Double.valueOf((String) objectArr[17]);
					amount = amount + l;
					i = i + 1;
				}
				log.info("amount::::" + amount);
				log.info("rowid::::" + rowid + " ");
				log.info("merchant count::::" + i + " ");
				Row row2 = sheet.createRow(rowid);
				row2.createCell(0).setCellValue("F");
				row2.createCell(1).setCellValue(i);
				row2.createCell(2).setCellValue(amount);

				String filesname1 = fileLocation;
				String nameFileconvert = fileName;
				File folder = new File(filesname1);
				if (!folder.exists()) {
					folder.mkdir();
				}

				Path path = Path.of(filesname1, nameFileconvert);
				filesname.append(nameFileconvert);
				String DocRef = filesname1 + nameFileconvert;

				FileOutputStream fos = new FileOutputStream(DocRef);
				workbook.write(fos);
				fos.flush();
				fos.close();
				workbook.dispose();
				workbook.close();

				js1.put("Status", "Success");
				js1.put("Message", "File Created and Uploaded ");
				js1.put("FilePath", fileLocation);
				js1.put("FileName", nameFileconvert);

			}
			log.info("Getting Null Value>>>>>>>>>>>>>>>>>");

		} catch (Exception e) {
			log.info("Error in File Create :::Name:::::::::::::::{} " + e.getMessage());
			e.printStackTrace();
			js1.put("Status", "Failed");
			js1.put("Message", "File Not Created " + e.getMessage());
		}

		return js1;

	}

	// if status= 1 then get records from the payoutstatus
	public Map<String, Object> getPayoutStatusData(Date date, String payoutEscrow) {

		Map<String, Object> Msg = null;
		List<Map<String, Object>> result = null;

		JSONObject js1 = new JSONObject();
		try {

			result = payoutRepository.selectDataPayoutSatus(date, payoutEscrow);

			if (!result.isEmpty()) {

				String transPathName = payoutRepository.transPathName(date, payoutEscrow);
				log.info("statusCheck::::::::::" + transPathName);
				String merchantPathName = payoutRepository.merchantPathName(date, payoutEscrow);
				log.info("merchantPathName::::::::::" + merchantPathName);

				if (merchantPathName == null || merchantPathName.equalsIgnoreCase("null")) {

					js1.put("Status", "Success");
					js1.put("Data", result);

					if (payoutEscrow.equalsIgnoreCase("HDFC")) {

						js1.put("ResponseMessage", "You can download payout files.");

					} else {

						js1.put("ResponseMessage", "You can download Yes Bank payout files.");
					}
					js1.put("merchantwise_payout_path", "null");
				} else {
					js1.put("Status", "Success");
					js1.put("Data", result);

					if (payoutEscrow.equalsIgnoreCase("HDFC")) {

						js1.put("ResponseMessage", "You can download payout files.");

					} else {

						js1.put("ResponseMessage", "You can download Yes Bank payout files.");
					}
				}

			} else {
				log.info("PayoutGenerate: ::Service::else:::::::::::::::{} " + result.size());
				js1.put("Status", "Failed");
				js1.put("Message", "Record Not Found");
			}

			Msg = js1.toMap();

		} catch (Exception e1) {
			log.info("e1::::::::::::" + e1);
		}

		return Msg;
	}

	// code for the settlement - update utr no single data

	public String updateURTNo(String utrNo, String payoutRefId, String userName) throws Exception {
		String responseVal = null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {

			System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pr_updateUtrNo_pa_v1(?,?,?)}");
				callableStatement.setString(1, utrNo);
				callableStatement.setString(2, payoutRefId);
				callableStatement.setString(3, userName);

				return callableStatement;
			}, prmtrsList);

			System.out.print("resultData::::::::::::::: " + resultData);
			Object val = resultData.get("#update-count-1");
			System.out.print("val::::::::::::::: " + val);
			String s2 = String.valueOf(val);
			System.out.print("s2::::::::::::::: " + s2);

			responseVal = "success";

		} catch (Exception e1) {

			responseVal = "fail";
			throw new Exception();
		}

		return responseVal;
	}

	// bulk upload settlement file

	public void storeFile(MultipartFile file, String fileLocationBulk)

	{
		log.info("fileLocationBulk:::::::::::" + fileLocationBulk);

		try {
			Path copyLocation = Path
					.of(fileLocationBulk + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
			log.info("copyLocation::::::::" + copyLocation.toString());
			Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
			log.info("File Not Found");
		}

	}
}
