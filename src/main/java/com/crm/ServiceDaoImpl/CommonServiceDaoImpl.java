package com.crm.ServiceDaoImpl;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import com.crm.model.User;
import com.crm.model.UserRequest;
import com.crm.services.CommonService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@Service
public class CommonServiceDaoImpl implements CommonService {

	@Autowired
	private JdbcTemplate JdbcTemplate;

	@Override
	public String GetMerchantList(String Name) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("Select Id,MerchantId,merchant_name from tbl_mstmerchant where Is_deleted='N'");
				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				return "Data does not exist";
			}
			Gson gson = new Gson();

			String jsonArray = gson.toJson(arrayList);

			return jsonArray.toString();
		} catch (Exception e1) {
			throw new Exception();
		}
	}

	@Override
	public UserRequest userLoginDetails(UserRequest loginRequestDTO) throws Exception {
		UserRequest pojo = null;
		try {
			List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
			prmtrsList.add(new SqlParameter(Types.VARCHAR));

			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_login(?)}");
				callableStatement.setString(1, loginRequestDTO.getUsername());
				System.out.println("\n::::::::::callableStatement::::"+callableStatement);
				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			  // Check if arrayList is null or empty
//	        if (arrayList == null || arrayList.isEmpty()) {
//	            System.out.println("No results found for the provided username.");
//	            return new UserRequest(); // or return null, or throw an exception
//	        }
			
			System.out.println();
			Map resultMap = (Map) arrayList.get(0);
			// LoginResponseDTO someDTO = (LoginResponseDTO)
			// resultData.get("#result-set-1");
			// log.info("someDTO : "+someDTO.toString());

			Gson gson = new Gson();
			JsonElement jsonElement = gson.toJsonTree(resultMap);
			pojo = gson.fromJson(jsonElement, UserRequest.class);
			return pojo;

		} catch (Exception e) {
			System.out.println("Exception throwing");
			e.printStackTrace();
		} finally {

			JdbcTemplate.getDataSource().getConnection().close();

		}
		return loginRequestDTO;

	}

	@Override
	public String getMenuHierarchy(String roleId) throws Exception {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.INTEGER));
		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getRolePermission(?)}");
				callableStatement.setString(1, roleId);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				throw new Exception();
			}
			Gson gson = new Gson();
			String jsonArray = gson.toJson(arrayList);
			return jsonArray;
		} catch (Exception e1) {
			throw new Exception();
		} finally {

			JdbcTemplate.getDataSource().getConnection().close();

		}
	}

	@Override
	public String uploadForm(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getDropDown(String Type, String Value) throws SQLException {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_CommonddlAPI(?,?)}");
				callableStatement.setString(1, Type);
				callableStatement.setString(2, Value);
				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				throw new Exception();
			}
			Gson gson = new Gson();
			String jsonArray = gson.toJson(arrayList);
			return arrayList;
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			JdbcTemplate.getDataSource().getConnection().close();
		}
		return null;
	}
	public String UpdateResellerStatus(String Status, String Remark, String Rid, String Type,String Date, String AddedBy) throws Exception {
		// TODO Auto-generated method stub
		
		String resp_date_Time = new Timestamp(new Date().getTime())+"";
		java.sql.Date dateDB = new java.sql.Date(new Date().getTime());

		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: "+prmtrsList);
		Map<String, Object> resultData = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection.prepareCall("{call pro_resellerstatusUpdateV1(?,?,?,?,?)}");
			callableStatement.setString(1, Status);
			callableStatement.setString(2, Remark);
			callableStatement.setString(3, Rid);
			callableStatement.setString(4, Type);
//			callableStatement.setDate(5, dateDB);
			callableStatement.setString(5, AddedBy);



			return callableStatement;
		}, prmtrsList);
		ArrayList arrayList = new ArrayList();
		arrayList = (ArrayList) resultData.get("#result-set-1");
		if (arrayList.isEmpty()) {
			return "Fields does not exist";
		}
		
		  Gson gson = new Gson(); 
		  String jsonArray = gson.toJson(arrayList);
		 
		return jsonArray;
	} catch (Exception e1) {
		throw new Exception();
	}
	
}
}
