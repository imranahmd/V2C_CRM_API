package com.crm.services;

import java.sql.CallableStatement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
@Service
public class Role1Masterservice {
	
	private static Logger log = LoggerFactory.getLogger(Role1Masterservice.class);

	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	
	
	public Map<String, Object> getrole1list() {
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> Msg = null ;
		JSONObject js1 = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String, Object> role1Data =    JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_getRoleDisplay()}");
				return callableStatement;
}, prmtrsList);
				if(!role1Data.isEmpty()) {
				Object obj=role1Data.get("#result-set-1");
				 js1.put("Status", "Success");
				 js1.put("Message", "Data Found");
				 js1.put("data", obj);
			 }else {
				 js1.put("Status", "Error");
				 js1.put("Message", "Data Not Found");
			 }
		Msg = js1.toMap();
		return Msg;
		
		}



	public String createrole(String RoleName, String RoleDescription, String StatusRole) {
		log.info("entered if in controller");
		String responseVal=null;
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		
		Map<String,Object> resultData;
		try {
		resultData=JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_mstmenutbl(?,?,?)}");
			callableStatement.setString(1, RoleName);
			callableStatement.setString(2, RoleDescription);
			callableStatement.setString(3, StatusRole);
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






	public String Updaterole(String Rolename, String RoleDescriptionId, String RoleId,String Status) {

		String responseVal = null;
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		prmtrsList.add(new SqlParameter(Types.VARCHAR));

		
		Map<String, Object> resultData ;
		try {
		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_updaterole1(?,?,?,?)}");
				callableStatement.setString(1, Rolename);
				callableStatement.setString(2, RoleDescriptionId);
				callableStatement.setString(3, RoleId);
				callableStatement.setString(4, Status);

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



	public String Deleterole(String RoleId) {
		String responseVal = null;
		
		
		
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		Map<String, Object> resultData ;
		try {
		resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection
						.prepareCall("{call pro_Deleterole1(?)}");
				callableStatement.setString(1, RoleId);
				
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

public String getRecordById(String role) {
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	Map<String, Object> reportdata ;
	
	reportdata = JdbcTemplate.call(connection -> {
		CallableStatement callableStatement = connection
				.prepareCall("{call pro_getRoleMenuPermissionV1(?)}");
		callableStatement.setString(1, role);
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



public String getroleById() {
	List<SqlParameter> prmtrsList = new ArrayList<>();
    prmtrsList.add(new SqlParameter(Types.VARCHAR));
    JSONObject js1 = new JSONObject();
    Map<String, Object> Msg = null;
    List<JsonObject> reformattedList = new ArrayList<>();
    Set<String> seenSubMenuNames = new HashSet<>(); // HashSet to track duplicate SubMenuNames

    Map<String, Object> reportdata = JdbcTemplate.call(connection -> {
        CallableStatement callableStatement = connection.prepareCall("{call Pro_RolePermissionV2()}");
        return callableStatement;
    }, prmtrsList);
    
    
   // log.info("reportdata::::::"+reportdata);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    //log.info("gson::::"+gson);

    if (!reportdata.isEmpty()) {
        @SuppressWarnings("unchecked")
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) reportdata.get("#result-set-1");
       log.info("resultList:::::::::::"+resultList);

       
        // Create the main array to hold the reformatted data
       ArrayList<String> abc= new  ArrayList<String>();

        // Iterate through the result list
        for (Map<String, Object> resultMap : resultList) {
            String menuName = (String) resultMap.get("MenuName");
            Integer menuId = (int) resultMap.get("MenuId");

            String submenuName = (String) resultMap.get("SubmenuName");
            Integer submenuId = (int) resultMap.get("SubmenuId");

            String permissionAction = (String) resultMap.get("PermissionAction");

            long Permission =  (long) resultMap.get("Permission");
            //log.info("Permission"+Permission);
            // Check if permissionID is null
         // Check if the SubMenuName is already seen, if yes, skip adding to the response
//            if (!seenSubMenuNames.contains(submenuName)) {
//                seenSubMenuNames.add(submenuName);

      //  log.info("got data from resultset::::::::"+menuName+"::::::::"+submenuName+"::::::::"+permissionAction+"::::::::"+permission);
            String aa="";
            // Create the sub-array object
            JsonObject subMenuObject = new JsonObject();
            if(abc.contains(submenuName)) {
            subMenuObject.addProperty("SubMenuName", aa);
            }else {
            	abc.add(submenuName);
                subMenuObject.addProperty("SubMenuName", submenuName);

            }
            subMenuObject.addProperty("SubMenuId",String.valueOf(submenuId));
            subMenuObject.addProperty("permissionID", String.valueOf(Permission));
            subMenuObject.addProperty("PermissionAction", permissionAction);
           // log.info("subMenuObject::::::::"+subMenuObject.toString());
          //  log.info("::::::::::$$$$$$$$$$$$$$::::"+subMenuObject.get("SubMenuName").getAsString());


            // Check if the main array already contains an object with the same menuName
            JsonObject existingMenuObject = null;
            for (JsonObject obj : reformattedList) {
                if (obj.get("MenuName").getAsString().equals(menuName) && obj.get("MenuId").getAsNumber().equals(menuId)) {
                	
               //     log.info("obj::::::::"+obj);

                    existingMenuObject = obj;
                    break;
                }
            
            }
           
         // Create the sub-menu map as a JsonObject with the submenuName as an object within a JSON array
            JsonObject subMenuMapObject = new JsonObject();
            JsonArray subMenuArray = new JsonArray();
            subMenuArray.add(subMenuObject);
         
            subMenuMapObject.add(submenuName, subMenuArray);
          
            if (existingMenuObject != null) {
                // If an object with the same menuName exists, add the sub-menu map object to its SubMenu array
                existingMenuObject.get("SubMenu").getAsJsonArray().add(subMenuMapObject);
            } else {
                // If no object with the same menuName exists, create a new object with the sub-menu map object
                JsonObject reformattedObject = new JsonObject();
                reformattedObject.addProperty("MenuName", menuName);
                reformattedObject.addProperty("MenuId", menuId);
                JsonArray subMenuArrayWrapper = new JsonArray();
                subMenuArrayWrapper.add(subMenuMapObject);
                reformattedObject.add("SubMenu", subMenuArrayWrapper);
                // Add the main array object to the reformatted list
                reformattedList.add(reformattedObject);
                

            }
            
        }

        js1.put("Status", "Success");
        js1.put("Message", "Data Found");
        js1.put("data", gson.toJson(reformattedList));
        log.info("gson.toJson(reformattedList)::::::::"+gson.toJson(reformattedList));
    } else {
        js1.put("Status", "Error");
        js1.put("Message", "Data Not Found");
    }
    
    Msg = js1.toMap();

    return gson.toJson(reformattedList);
}






public String Insertrolemenupermission(JSONArray data) {
	log.info("entered if in Service Insertrolemenupermission______________");
	String responseVal=null;
	log.info("entered if in Service Insertrolemenupermission____2__________");
	try {
        List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
        prmtrsList.add(new SqlParameter(Types.VARCHAR));
        

        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            String sql1 = "delete from tbl_mstrolemenupermission where roleId = ? ;";
            	        log.info("sql1::"+sql1);
            JdbcTemplate.update(sql1, item.getString("roleId"));
        }

        responseVal = "success";
        
    } catch (Exception e) {
        e.printStackTrace();
        responseVal = "fail";
    }
	
	try {
        List<SqlParameter> prmtrsList1 = new ArrayList<SqlParameter>();
        prmtrsList1.add(new SqlParameter(Types.VARCHAR));
        prmtrsList1.add(new SqlParameter(Types.VARCHAR));
        prmtrsList1.add(new SqlParameter(Types.VARCHAR));
        prmtrsList1.add(new SqlParameter(Types.VARCHAR));

        for (int i = 0; i < data.length(); i++) {
            JSONObject item = data.getJSONObject(i);
            String sql = "INSERT INTO tbl_mstrolemenupermission (roleId, submenuId, permissionID, MenuId) VALUES (?, ?, ?, ?);";
            log.info("sql::"+sql);
            JdbcTemplate.update(sql, item.getString("roleId"), item.getString("SubmenuId"), item.getString("permissionID"), item.getString("SubmenuId"));

        }

        responseVal = "success";
        
    } catch (Exception e) {
        e.printStackTrace();
        responseVal = "fail";
    }
    
	return responseVal;
}



public Map<String, Object> getrolelist(String role) {
	List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
	prmtrsList.add(new SqlParameter(Types.VARCHAR));
	Map<String, Object> Msg = null ;
	JSONObject js1 = new JSONObject();
	JSONArray array = new JSONArray();
	Map<String, Object> role1Data =    JdbcTemplate.call(connection -> {
			CallableStatement callableStatement = connection
					.prepareCall("{call pro_getRoleMenuPermissionV1(?)}");
			callableStatement.setString(1, role);

			return callableStatement;
}, prmtrsList);
			if(!role1Data.isEmpty()) {
			Object obj=role1Data.get("#result-set-1");
			 js1.put("Status", "Success");
			 js1.put("Message", "Data Found");
			 js1.put("data", obj);
		 }else {
			 js1.put("Status", "Error");
			 js1.put("Message", "Data Not Found");
		 }
	Msg = js1.toMap();
	return Msg;
	
	}


}
