package com.crm.ServiceDaoImpl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.Repository.RmsRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class RMSServiceDaoImpl {

	private static Logger logger = LoggerFactory.getLogger(RMSServiceDaoImpl.class);

	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	private RmsRepo RMSRepo;
	
	public String GetRmsData(String Mid) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("Select JSON_UNQUOTE(risk_data) as risk_data ,mcc_code as Mcc , risk_type as Risk FROM merchant_master_data where Mid=?");
            callableStatement.setString(1, Mid);
			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return "";
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	// This method working as update Risk Approvel and accout approvel type
	public String UpdateApprovelProcess(String Merchant_id,String type) throws Exception {
		// TODO Auto-generated method stub
		
	logger.info("Approvel Type::::{}"+type);
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_merchantApprovel(?,?)}");
			callableStatement.setString(1, Merchant_id);
			callableStatement.setString(2, type);
			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	
	
	public String InsertRmsAtionLogs(String Merchant_id, String AddedBy, String NewJson, String OldJson) throws Exception {
		// TODO Auto-generated method stub
		
	
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {
			System.out.print("ParmVlaues::::::Insert Rms Logs::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_RiskTracelog(?,?,?,?)}");
			callableStatement.setString(1, Merchant_id);
			callableStatement.setString(2, AddedBy);
			callableStatement.setString(3, NewJson);
			callableStatement.setString(4, OldJson);
			
			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}

	
	
	public String GetRmsFields(String type, String Value) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));


		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_riskFieldManagement(?,?)}");
			callableStatement.setString(1, type);
			callableStatement.setString(2, Value);

			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
		return jsonArray.toString();
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public ArrayList GetRiskAlertDetails(String from,String todate,String Mid,String RiskCode, String RiskStage, String RiskFlag, int pageRecords, int pageNumber ) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));


		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call proc_GetRiskAlertDetails(?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, from);
			callableStatement.setString(2, todate);
			callableStatement.setString(3, Mid);
			callableStatement.setString(4, RiskCode);
			callableStatement.setString(5, RiskStage);
			callableStatement.setString(6, RiskFlag);
			callableStatement.setInt(7, pageRecords );
			callableStatement.setInt(8, pageNumber);

			return callableStatement;
		}, prmtrsList);
		ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-1");
		
		if (arrayList.isEmpty()) {
			return null;
		}
				 		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}

	public ArrayList GetRiskAlertDetailsrecordtotal(String from,String todate,String Mid,String RiskCode, String RiskStage, String RiskFlag, int pageRecords, int pageNumber ) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));


		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call proc_GetRiskAlertDetails(?,?,?,?,?,?,?,?)}");
			callableStatement.setString(1, from);
			callableStatement.setString(2, todate);
			callableStatement.setString(3, Mid);
			callableStatement.setString(4, RiskCode);
			callableStatement.setString(5, RiskStage);
			callableStatement.setString(6, RiskFlag);
			callableStatement.setInt(7, pageRecords );
			callableStatement.setInt(8, pageNumber);

			return callableStatement;
		}, prmtrsList);
		ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");
		
		
		if (arrayList.isEmpty()) {
			return null;
		}
		
		 		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	
	
	public ArrayList getRiskLogs(String from,String todate,String Mid,String RiskCode, String Action, int pageRecords, int pageNumber   ) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));



		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_getriskactionlogs(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, from);
			callableStatement.setString(2, todate);
			callableStatement.setString(3, Mid);
			callableStatement.setString(4, RiskCode);
			callableStatement.setString(5, Action);
			callableStatement.setInt(6, pageRecords);
			callableStatement.setInt(7, pageNumber);

			return callableStatement;
		}, prmtrsList);
		ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			
			return null;
		}
		
		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public ArrayList getRiskLogsRecordTotal(String from,String todate,String Mid,String RiskCode, String Action, int pageRecords, int pageNumber   ) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));



		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_getriskactionlogs(?,?,?,?,?,?,?)}");
			callableStatement.setString(1, from);
			callableStatement.setString(2, todate);
			callableStatement.setString(3, Mid);
			callableStatement.setString(4, RiskCode);
			callableStatement.setString(5, Action);
			callableStatement.setInt(6, pageRecords);
			callableStatement.setInt(7, pageNumber);

			return callableStatement;
		}, prmtrsList);
		ArrayList<String> arrayList = (ArrayList<String>) resultData.get("#result-set-2");
		
		if (arrayList.isEmpty()) {
			arrayList.add("[{Data:Not Found }]");
			return arrayList;
		}
		
		return arrayList;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public String UpdateMerchantStatus(String Mid, String Status, String Remark, String Type,String Date, String Added_By) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		System.out.print("ParmVlaues::::::"+Mid+"::"+Status+":"+Remark+"::"+Type+":::"+Added_By+": "+Date);

		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_merchantstatusUpdateV1(?,?,?,?,?,?)}");
			
			logger.info("callableStatement:::::::::::::::::::::"+callableStatement);
			
			callableStatement.setString(1, Status);
			callableStatement.setString(2, Remark);
			callableStatement.setString(3, Mid);
			callableStatement.setString(4, Type);
			callableStatement.setDate  (5, dateDB);
			callableStatement.setString(6,Added_By);
			logger.info("callableStatement:::::::::::::::::::::"+callableStatement);


			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		//  Gson gson = new Gson();
		  Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		  String jsonArray = gson.toJson(arrayList);
		  logger.info("returning:::::::::::::::::::::::"+jsonArray);
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
	public String GetMerchantStatusBYId(String Mid) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("Select status from tbl_mstmerchant where merchantId=?");
            callableStatement.setString(1, Mid);
			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return "";
		}
		Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			 			 

		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
	}
	 @Transactional 
	 public int InsertActionLogs(String Mid, String AddedBy, String JsonOld, String RMS) throws SQLException {
			 int i=0;
	try {
		
		logger.info("Enter In Insert Action Logs::::::::::::::::");
		
		RMSRepo.RiskTraceLogs(Mid,AddedBy,RMS,JsonOld);
		//i= JdbcTemplate.update("insert into tbl_riskactionlogs (MID,RiskStage,RiskCode,Actiontype,reason,AddedOn,TransTime, AddedBy,beforevalue,aftervalue) values ("+Mid+"','Merchant','RSK0033', 'Risk Updated','Merchant Risk configuration updated',now(),now(),user,'','');");
	i=1;
	}catch(Exception e)
				 {
	                e.printStackTrace();
	}finally { 					
		JdbcTemplate.getDataSource().getConnection().close();
		System.out.println("finally block is always executed for the insert Rms ");  
	}    
				return i;
	  
			 
		 }
	 @Transactional 
	 public int inserRmsData(String Json,String Mid) throws SQLException {
			 int i=0;
	try {
		Map<String, Object> params = new HashMap<>();
		params.put("1",Mid); 
		params.put("2","qq"); 
		params.put("3","qq"); 
		params.put("4","qq");
		
		System.out.println(params);
		i= JdbcTemplate.update("insert into merchant_master_data (Mid, RiskData, Data1, MDR) values('"+Mid+"','t','9', '7')");
		
	}catch(Exception e)
				 {
	                         e.printStackTrace();
				 }finally { 					
						JdbcTemplate.getDataSource().getConnection().close();
					System.out.println("finally block is always executed for the insert Rms ");  
				}    
				return i;
				  
			 
		 }
}
