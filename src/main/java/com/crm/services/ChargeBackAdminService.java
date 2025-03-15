package com.crm.services;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.ChargebackRepo;
import com.crm.Repository.lBPSRepo;

import com.crm.dto.lBPSDto;
import com.crm.dto.lBPSPaginationDto;

import io.jsonwebtoken.lang.Arrays;


@Service
public class ChargeBackAdminService {
	
	private static Logger log = LoggerFactory.getLogger(ChargeBackAdminService.class);

	
	private static final int BUFFER = 0;
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Value("${chargeback.uploadFile}")
    private String uploadDirChargebackFile;
	
	@Autowired
	private ChargebackRepo chargebackRepo;

	public String  getChargeBack(String txnId, String bankRefNo, String mid, String fdate, String toDate) throws Exception {
		System.out.println("txnID===================================="+txnId);
		System.out.println("MID===================================="+mid);
		System.out.println("fdate===================================="+fdate);
		System.out.println("todate===================================="+toDate);
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call getChargeBackDataForRaisedNew(?,?,?,?,?)}");
					callableStatement.setString(1, txnId);
					callableStatement.setString(2, bankRefNo);
					callableStatement.setString(3, mid);
					callableStatement.setString(4, fdate);
					callableStatement.setString(5, toDate);
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				ArrayList arrayList = new ArrayList();
				arrayList = (ArrayList) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					arrayList.add("Not Found Data");
					return arrayList.toString();
				}
				System.out.println("arrayList::::::"+arrayList);
				JSONArray rs = new JSONArray(arrayList);
//				Gson gson = new Gson();
//					  String jsonArray = gson.toJson(arrayList);
					 			 
					  System.out.println("rs::::::"+rs);
				return rs.toString();	
				}  catch (Exception e1) {
					throw new Exception();
				}
				}
	
	
	public String saveRaisedchargeback(String chargeBackId, String merchantId, String txnId, String amount, String remarks, String bankLastDate, String merchLastDate, String UpfrontNonUpfront, String fileName_Chargeback, String MerchUpfrontNonUpfront) {
		String responseVal = null;
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		
	      
		try {
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_ChargebackInsert(?,?,?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, chargeBackId);
			callableStatement.setString(2, merchantId);
			callableStatement.setString(3, txnId);
			callableStatement.setString(4, amount);
			callableStatement.setString(5, remarks);
			callableStatement.setString(6, bankLastDate);
			callableStatement.setString(7, merchLastDate);
			callableStatement.setString(8, UpfrontNonUpfront);
			callableStatement.setString(9, fileName_Chargeback);
			callableStatement.setString(10, MerchUpfrontNonUpfront);
			return callableStatement;
		}, prmtrsList);

		System.out.print("resultData::::::::::::::: " + resultData);
		Object val = resultData.get("#inserted-set-1");
		System.out.print("val::::::::::::::: " + val);
		String s2 = String.valueOf(val);
		System.out.print("s2::::::::::::::: " + s2);

		if (s2 != "0") {
			responseVal = "success";
		} else {
			responseVal = "fail";
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		return responseVal;
		
		
	}



	public String getChargeBackProcesssing(String MerchantId,String txnId,String cbStatus,String fromDate,String toDate) throws Exception {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));


		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call pro_getCbProcData(?,?,?,?,?)}");
					callableStatement.setString(1, MerchantId);
					callableStatement.setString(2, txnId);
					callableStatement.setString(3, cbStatus);
					callableStatement.setString(4, fromDate);
					callableStatement.setString(5, toDate);
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				ArrayList arrayList = new ArrayList();
				arrayList = (ArrayList) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					arrayList.add("Data Not Found");
					return arrayList.toString();
				}
				System.out.println("arrayList::::::"+arrayList);
				JSONArray rs = new JSONArray(arrayList);

					 			 
					  System.out.println("rs::::::"+rs);
				return rs.toString();	
				}  catch (Exception e1) {
					throw new Exception();
				}
			

	}


	public String UpdatechargeBack(String cbId, String action, String comments, String merchLastDate,
			String bankLastDate, String TxnId) {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			String responseVal = null;
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call pro_updateCbDataAdminNew(?,?,?,?,?,?)}");
					callableStatement.setString(1, cbId);
					callableStatement.setString(2, action);
					callableStatement.setString(3, comments);
					callableStatement.setString(4, merchLastDate);
					callableStatement.setString(5, bankLastDate);
					callableStatement.setString(6, TxnId);
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				Object val = resultData.get("#update-count-1");
				System.out.print("val::::::::::::::: " + val);
				String s2 = String.valueOf(val);
				System.out.print("s2::::::::::::::: " + s2);

				if (!s2.equalsIgnoreCase("0")) {
					responseVal = "success";
				} else {
					responseVal = "fail";
				}
					
			}catch (Exception e) {
				e.printStackTrace();
			}
				return responseVal;

				
	}


	public String chargeBackDocData(String txnId, String cbId, String merchantId, String Fdate, String ToDate) throws Exception {
		System.out.println("txnId==================="+txnId);
		System.out.println("cbId==================="+cbId);
		System.out.println("merchantId==================="+merchantId);
		System.out.println("Fdate==================="+Fdate);
		System.out.println("ToDate==================="+ToDate);
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call getCbDocDetails(?,?,?,?,?)}");
					callableStatement.setString(1, txnId);
					callableStatement.setString(2, cbId);
					callableStatement.setString(3, merchantId);
					callableStatement.setString(4, Fdate);
					callableStatement.setString(5, ToDate);
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				ArrayList arrayList = new ArrayList();
				arrayList = (ArrayList) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					arrayList.add("Data Not Found");
					return arrayList.toString();
				}
				System.out.println("arrayList::::::"+arrayList);
				JSONArray rs = new JSONArray(arrayList);
//				Gson gson = new Gson();
//					  String jsonArray = gson.toJson(arrayList);
					 			 
					  System.out.println("rs::::::"+rs);
				return rs.toString();	
				}  catch (Exception e1) {
					throw new Exception();
				}
				}


	
	public void uploadFile(MultipartFile file)
    {
//        String uploadDir = "D:/home/abc";
		 File fileval = new File(uploadDirChargebackFile);

			if (!fileval.exists()) {
				fileval.mkdirs();
			}
			String uploadDir = uploadDirChargebackFile;

        try {
            Path copyLocation = Path.of(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            log.info("copyLocation::::::::"+copyLocation.toString());
            Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);
   
        } catch (IOException e) {
            e.printStackTrace();
           log.info("File Not Found");
        }
       
    }
	
	
	
	public String getChargebackReportData(String iTxnId, String iMerchantId, String iFdate, String iToDate) throws Exception {
		System.out.println("txnId==================="+iTxnId);
		System.out.println("merchantId==================="+iMerchantId);
		System.out.println("Fdate==================="+iFdate);
		System.out.println("ToDate==================="+iToDate);
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call pro_getChargebackReportData(?,?,?,?)}");
					callableStatement.setString(1, iTxnId);
					callableStatement.setString(2, iMerchantId);
					callableStatement.setString(3, iFdate);
					callableStatement.setString(4, iToDate);
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				ArrayList arrayList = new ArrayList();
				arrayList = (ArrayList) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					arrayList.add("Data Not Found");
					return arrayList.toString();
				}
				System.out.println("arrayList::::::"+arrayList);
				JSONArray rs = new JSONArray(arrayList);
//				Gson gson = new Gson();
//					  String jsonArray = gson.toJson(arrayList);
					 			 
					  System.out.println("rs::::::"+rs);
				return rs.toString();	
				}  catch (Exception e1) {
					throw new Exception();
				}
				}
	
	public String getCBcomment(String txnId) throws Exception {
		System.out.println("txnID===================================="+txnId);
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		
			try {

				System.out.print("inside the create invoice in db::::::::::::::: " + prmtrsList);
				Map<String, Object> resultData = JdbcTemplate.call(connection -> {
					CallableStatement callableStatement = connection
							.prepareCall("{call Pro_chargebackcomments(?)}");
					callableStatement.setString(1, txnId);
					
					
					return callableStatement;
				}, prmtrsList);

				System.out.print("resultData::::::::::::::: " + resultData);
				ArrayList arrayList = new ArrayList();
				arrayList = (ArrayList) resultData.get("#result-set-1");
				if (arrayList.isEmpty()) {
					arrayList.add("Not Found Data");
					return arrayList.toString();
				}
				System.out.println("arrayList::::::"+arrayList);
				JSONArray rs = new JSONArray(arrayList);
//				Gson gson = new Gson();
//					  String jsonArray = gson.toJson(arrayList);
					 			 
					  System.out.println("rs::::::"+rs);
				return rs.toString();	
				}  catch (Exception e1) {
					throw new Exception();
				}
				}
	



}
	
	
		
	




