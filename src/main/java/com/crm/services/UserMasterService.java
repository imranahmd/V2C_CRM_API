package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.crm.Repository.MerchantRepository;
import com.crm.dto.BulkMerchantList;
import com.crm.dto.UserlistDTO;
import com.crm.util.GeneralUtil;
@Service
public class UserMasterService {
private static Logger log = LoggerFactory.getLogger(UserMasterService.class);
	
	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Autowired
	private MerchantRepository merchantRepo;
	
	
	public JSONObject insertrecord_User(String userId, String fullName, String emailId, String contactNo, String roleId,
			String password) {
		String responseVal=null;
		 JSONObject js = new JSONObject();


		//	
//				GeneralUtil generalUtil = new GeneralUtil();
//				String firstName = fullName.contains(" ") ? fullName.split(" ")[0]
//						: fullName;
//				String password1 = generalUtil.crypt(firstName.length()>11?firstName.substring(0,11):firstName+"@"+new SimpleDateFormat("Y").format(new Date()));
//						//(firstName + "@2022");
//				
				 String password1= encrypt(password);

		 int resultData = merchantRepo.insertUser(userId,fullName,emailId,contactNo,roleId,password1);

		log.info("resultData::::::::::::::"+resultData);
		
		String valueData =String.valueOf(resultData);
		log.info("valueData:::::::::"+valueData);
		
		if (valueData.equalsIgnoreCase("0")) {
			js.put("Status", true);
			js.put("Message", "Inserted Successfully");
		
		} else {
			js.put("Status", false);
			 js.put("Message", "User Id exists");
		}
			
		
		return js;
	}
	
	 public static String encrypt(String value)
	  {
		 
		 
	    try
	    {
	    	String key = "Bar12345Bar12345"; // 128 bit key
			String initVector = "RandomInitVector"; // 16 bytes IV
	      System.out.println(initVector.getBytes().length);
	      

	      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	      
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	      cipher.init(1, skeySpec, iv);
	      
	      byte[] encrypted = cipher.doFinal(value.getBytes());
	      System.out.println("encrypted string: " + 
	        java.util.Base64.getEncoder().encodeToString(encrypted));
	      
	      return java.util.Base64.getEncoder().encodeToString(encrypted);

	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    
	    return null;
	  }
	 
	public String updaterecord_User(String userId, String fullName, String emailId, String contactNo, String roleId,
			String password) {
		String responseVal=null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
	
//		GeneralUtil generalUtil = new GeneralUtil();
//		String firstName = fullName.contains(" ") ? fullName.split(" ")[0]
//				: fullName;
//		String password1 = generalUtil.crypt(firstName.length()>11?firstName.substring(0,11):firstName+"@"+new SimpleDateFormat("Y").format(new Date()));
//				//(firstName + "@2022");
//		
		 String password1= encrypt(password);


		Map<String,Object> resultData;
		try {
		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_UpdateUser1(?,?,?,?,?,?)}");
			callableStatement.setString(1, userId);
			callableStatement.setString(2, fullName);
			callableStatement.setString(3, emailId);
			callableStatement.setString(4, contactNo);
			callableStatement.setString(5, roleId);
			callableStatement.setString(6, password1);
			

			return callableStatement;
}, prmtrsList);
		log.info("ParmVlaues::::::::::::::: " + prmtrsList);
		System.out.print("roledata::::::::::::::: " + resultData);
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

	

	public String Deleteuser(String USERID) {
		String responseVal = null;
		
		
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> resultData ;
		try {
		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_DeleteUser(?)}");
				callableStatement.setString(1, USERID);
				
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


	public List<UserlistDTO> getUserData() {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
//		Map<String, Object> reportdata ;
//		
//		reportdata = JdbcTemplate.call(connection -> {
//			CallableStatement callableStatement = connection
//					.prepareCall("{call pro_GetUserList()}");
//			
//			return callableStatement;
//		}, prmtrsList);
		List<UserlistDTO> User = new ArrayList<>();

		List<Object[]> reportdata =  merchantRepo.listAllRecordsUser();
		for (Object[] obj : reportdata) {
			UserlistDTO  userlist=new UserlistDTO();
			userlist.setUSERID((String) obj[0]);
			
			userlist.setFullName((String) obj[1]);
			userlist.setROLEID((String) obj[2]);
			userlist.setROLENAME((String) obj[3]);
			log.info("Pass::::::"+(String) obj[4]);
			String Pass_for_decrypt=(String) obj[4];
			String PassDecrpted=decrypt(Pass_for_decrypt);//
			log.info("Decryp::::::::"+PassDecrpted);
			//userlist.setPassword((String) obj[3]);
			userlist.setPassword(PassDecrpted);
			userlist.setEmail_id((String) obj[5]);
			userlist.setContactNo((String) obj[6]);
			User.add(userlist);
		}
//		
	
		
		
//		ArrayList arrayList = new ArrayList();
//		arrayList =  (ArrayList) reportdata.get("#result-set-1");
//		if (arrayList.isEmpty()) {
//			arrayList.add("Not Found Data");
//			return arrayList.toString();
		
		
		//JSONArray rs = new JSONArray(arrayList);
		return User;	
}
	public String decrypt(String encrypted) {

		String key = "Bar12345Bar12345"; // 128 bit key
		String initVector = "RandomInitVector"; // 16 bytes IV
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			System.out.println(new String(original));

			return new String(original);

		} catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
		}

		return null;

	}
	public String getRoleIdName() {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> reportdata ;
		
		reportdata = JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("Select CAST(RoleID AS Char) AS RoleID,RoleName from tbl_mstrole;");
			
			return callableStatement;
		}, prmtrsList);
		
		ArrayList arrayList = new ArrayList();
		arrayList =  (ArrayList) reportdata.get("#result-set-1");
		if (arrayList.isEmpty()) {
			arrayList.add("Not Found Data");
			return arrayList.toString();
		}
		
		JSONArray rs = new JSONArray(arrayList);
		return rs.toString();	
}
	
}
