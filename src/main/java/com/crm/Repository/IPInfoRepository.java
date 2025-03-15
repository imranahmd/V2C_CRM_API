package com.crm.Repository;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class IPInfoRepository {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean getIPAddress(String ipAddress) {
	
	    List<SqlParameter> parameters = Arrays.asList(new SqlParameter(Types.NVARCHAR));
	
	    Map<String, Object> resultData = jdbcTemplate.call(connection -> {
            CallableStatement callableStatement = connection.prepareCall("{call pro_getIPAddress(?)}");
            callableStatement.setString(1, ipAddress);
            return callableStatement;
        }, parameters);
		
	    ArrayList arrayList = (ArrayList) resultData.get("#result-set-1");
	    
	    if(arrayList.isEmpty()) return true;

	    return false;
		
	}
}
