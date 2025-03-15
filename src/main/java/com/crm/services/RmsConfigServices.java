package com.crm.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.crm.Repository.MerchantMasterData;
import com.crm.Repository.MerchantRepository;
import com.crm.Repository.RmsRepo;
import com.crm.Repository.UserRepository;
import com.crm.model.MerchantList;
import com.crm.model.Merchant_MasterData;
import com.crm.model.User;

@Service
public class RmsConfigServices {

	@Autowired
	private RmsRepo RmsRepo;

	@Autowired
	private com.crm.ServiceDaoImpl.RMSServiceDaoImpl RMSServiceDaoImpl;

	@Autowired
	private MerchantMasterData MerchantMaster;
	@Autowired
	private UserRepository userRepository;

	public List<MerchantList> getMerchantByName(String Name) {
		List<MerchantList> customer = RmsRepo.findByMerchantNameLike(Name);
		return customer;

	}

	public List<MerchantList> getAllMerchants() {
		List<MerchantList> customer = RmsRepo.findAll();
		return customer;

	}

	public Map<String, List<String>> getAllMenus(String userId) {
		Optional<User> users = userRepository.findById(userId);
		if (users.isPresent()) {
			User user = users.get();

			List<Object[]> allMenus = RmsRepo.getAllMenus(user.getRoleId());
			Map<String, List<String>> menuMap = new HashMap<String, List<String>>();
			for (Object[] obj : allMenus) {
				String action = (String) obj[4];
				if (action == null || action.length() == 0) {
					action = "javascript:void(0)";
				}
				if (menuMap.get((String) obj[1]) == null) {
					List<String> submenus = new ArrayList<String>();
					submenus.add(obj[3] + "##" + action);
					menuMap.put((String) obj[1], submenus);
				} else {
					List<String> submenus = menuMap.get((String) obj[1]);
					if (submenus != null && submenus.size() > 0) {
						submenus.add((String) obj[3] + "##" + action);
					} else {
						submenus = new ArrayList<String>();
						submenus.add((String) obj[3] + "##" + action);
					}
					menuMap.put((String) obj[1], submenus);
				}
			}
			return menuMap;

		} else {
			return null;
		}

	}

	public int InserRmsValue(String RmsData, String mid, String mcc, String risk_code,String AddedBy) throws Exception {

		Merchant_MasterData Merchant_MasterData = new Merchant_MasterData();
		
		
		List<Merchant_MasterData> merchantMasterData =  MerchantMaster.findAllByMid(mid);
		Merchant_MasterData merchant = new Merchant_MasterData();
		  if(merchantMasterData.size() > 0) {
			  merchant = merchantMasterData.get(0);
			  Merchant_MasterData.setId(merchant.getId());
			  String jsonOld=merchant.getRiskData();
			  System.out.println("MerchantId form Rms"+merchant.getMid());
			  String Value=RMSServiceDaoImpl.GetMerchantStatusBYId(merchant.getMid());
			  System.out.print("Insert Value on Risk Action M "+Value);
			  JSONArray jr = new JSONArray(Value);
			  System.out.print("Insert Value on Risk Action M "+jr.toString());
			  System.out.print("Insert Value on Risk  M "+jr.getJSONObject(0));
				/*
				 * JSONObject js= new JSONObject();
				 */			 
			  System.out.print("Insert Value on Risk  M "+jsonOld);
			  System.out.print("Value of json:::::::::::"+jr.getJSONObject(0).getString("status"));
			  
			 
			  if(jr.getJSONObject(0).getString("status").equalsIgnoreCase("Active"))
			  {
				  
				  System.out.println("Rms Data::::::::::::::::::"+RmsData);
			  String InsertAction=RMSServiceDaoImpl.InsertRmsAtionLogs(merchant.getMid(),AddedBy,RmsData,jsonOld);
			  System.out.print("Insert Value on Risk Action Merchant Status is Sucesss "+InsertAction);
			  }
			  String ReturnValue = RMSServiceDaoImpl.UpdateApprovelProcess(merchant.getMid(),"Risk");

			    
		  }
		  System.out.print(merchant.getId());
		Merchant_MasterData.setMid(mid);
		Merchant_MasterData.setRiskData(RmsData);
		Merchant_MasterData.setMcc_code(mcc);
		Merchant_MasterData.setRisk_type(risk_code);

		Merchant_MasterData = MerchantMaster.save(Merchant_MasterData);

		
		
		int i = Merchant_MasterData.getId();
		if (i != 0 && i > 0) {
			i = 1;
		} else {
			i = 0;
		}
		return i;

	}

	public String GetRMSDataByMerchant(String name) throws Exception {
		return RMSServiceDaoImpl.GetRmsData(name);

	}

	public String Merchant_Status_change(String Mid, String Status, String Remark, String Type,String Date, String Added_By) throws Exception {
		return RMSServiceDaoImpl.UpdateMerchantStatus(Mid,Status, Remark,Type,Date,Added_By);

	}

	public String GetRmsFileds(String Type, String Value) throws Exception {
		return RMSServiceDaoImpl.GetRmsFields(Type, Value);

	}

	public ArrayList GetRiskAlert(String from, String todate, String Mid, String RiskCode, String RiskStage,
			String RiskFlag,int pageRecords, int pageNumber) throws Exception {
		return RMSServiceDaoImpl.GetRiskAlertDetails(from, todate, Mid, RiskCode, RiskStage, RiskFlag, pageRecords, pageNumber );

	}
	public ArrayList GetRiskAlerttotal(String from, String todate, String Mid, String RiskCode, String RiskStage,
			String RiskFlag,int pageRecords, int pageNumber) throws Exception {
		return RMSServiceDaoImpl.GetRiskAlertDetailsrecordtotal(from, todate, Mid, RiskCode, RiskStage, RiskFlag, pageRecords, pageNumber );

	}

	public ArrayList RiskActionlogs(String from, String todate, String Mid, String RiskCode, String Action, int pageRecords, int pageNumber)
			throws Exception {
		return RMSServiceDaoImpl.getRiskLogs(from, todate, Mid, RiskCode, Action, pageRecords, pageNumber );

	}
	
	public ArrayList RiskActionlogsTotal(String from, String todate, String Mid, String RiskCode, String Action, int pageRecords, int pageNumber)
			throws Exception {
		return RMSServiceDaoImpl.getRiskLogsRecordTotal(from, todate, Mid, RiskCode, Action, pageRecords, pageNumber );

	}
}
