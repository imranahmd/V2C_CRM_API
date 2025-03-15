package com.crm.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@Service
public class ChargebackMerchantService {
	
	

	static Logger log = LoggerFactory.getLogger(ChargebackMerchantService.class.getName());

	@Autowired
	private JdbcTemplate JdbcTemplate;
	
	@Value("${chargeback.uploadFile}")
    private String uploadDirChargebackFile;
	
	
	public String getChargeBackMerListData(String iMerchantId,String iTxnId,String FromDate,String ToDate) throws Exception {
		// TODO Auto-generated method stub
		List<SqlParameter> prmtrsList = new ArrayList<SqlParameter>();
		prmtrsList.add(new SqlParameter(Types.VARCHAR));
		try {
			System.out.print("ParmVlaues::::::::::::::: " + prmtrsList);
			Map<String, Object> resultData = JdbcTemplate.call(connection -> {
				CallableStatement callableStatement = connection.prepareCall("{call pro_getChargeBackMerList(?,?,?,?)}");
				callableStatement.setString(1, iMerchantId);
				callableStatement.setString(2, iTxnId);
				callableStatement.setString(3, FromDate);
				callableStatement.setString(4, ToDate);

				return callableStatement;
			}, prmtrsList);
			ArrayList arrayList = new ArrayList();
			arrayList = (ArrayList) resultData.get("#result-set-1");
			if (arrayList.isEmpty()) {
				arrayList.add("Not Found Data");

			}
			Gson gson = new Gson();
			  String jsonArray = gson.toJson(arrayList);
			  log.info("jsArray::::"+jsonArray);		 

			  return jsonArray;

//		logger.info("arrayList::::::"+arrayList);
			// JSONArray jsArray = new JSONArray(arrayList);
//		logger.info("jsArray::::"+jsArray);
//		System.out.print("jsArray::::::::::"+jsArray.toString());
//			return jsArray.toString();
		} catch (Exception e1) {
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
	
	
	
	
	
}
