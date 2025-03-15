//package com.crm.services;
//
//import java.sql.CallableStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.io.IOException;
//import java.security.SecureRandom;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.HashMap;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.SqlParameter;
//import org.springframework.stereotype.Service;
//
//import com.crm.Repository.LifeCycleTransactionRepo;
//import com.crm.Repository.MerchantRepository;
//import com.crm.dto.LifeCycleDto;
//import com.crm.util.Encrypt_Decrypt_Payout;
//
//@Service
//public class PayloutService {
//	private static Logger log = LoggerFactory.getLogger(PayloutService.class);
//
//	@Autowired
//	private JdbcTemplate JdbcTemplate;
//
//	@Autowired
//	private Encrypt_Decrypt_Payout encryp_decrypt;
//
//	@Autowired
//	private MerchantRepository repo;
//	@Autowired
//	private LifeCycleTransactionRepo lifeCycleTransactionRepo;
//
//
//	private static final SecureRandom random = new SecureRandom();
//
//	private String generateUniqueId() {
//		// Get the current timestamp
//		String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//
//		// Generate a random number (e.g., 5 digits)
//		int randomNumber = random.nextInt(100000);
//
//		// Combine prefix, timestamp, and random number to form the unique ID
//		return String.format("%s%s%05d", "PAY", timestamp, randomNumber);
//	}
//
//	private static String getFirst16Chars(String key) {
//		// Ensure the key is at least 16 characters long
//		if (key.length() >= 16) {
//			return key.substring(0, 16);
//		} else {
//			// Handle the case where the key is shorter than 16 characters
//			throw new IllegalArgumentException("Transaction key must be at least 16 characters long");
//		}
//	}
//
//	public String MarkSettlement(String merchantId, String cycleId, String reqDate, String proccessId) {
//		Map<String, Object> Msg = null;
//		String newDateStr=null;
//		if(cycleId.equalsIgnoreCase("14")){
//		 // Example date string
//        String dateStr = reqDate;
//        
//        // Convert string to LocalDate object
//        LocalDate date = LocalDate.parse(dateStr);
//        // Decrement the date by one day
//        LocalDate newDate = date.minusDays(1);
//        // Convert LocalDate object back to string
//         newDateStr = newDate.toString();
//
//        log.info(newDateStr);
//		}else {
//			newDateStr = reqDate;
//		}
//        
//		String Transactionlifecycle = lifeCycleTransactionRepo.getStatus(cycleId, proccessId, merchantId,
//				newDateStr + " 00:00:00", reqDate + " 23:59:59");
//		if (Transactionlifecycle.equalsIgnoreCase("RNS")) {
//            String sqlRNS = "SELECT 'admin' AS addedBy, '' AS fileName, Id AS transactionId, " +
//                            "CASE WHEN TransLifeCycle = 'RNS' THEN 'RSR' END AS rrnStatus, " +
//                            "CASE WHEN TransLifeCycle = 'RNS' THEN 'RSR Settled' END AS remarks " +
//                            "FROM tbl_transactionmaster WHERE cycleId = ? AND process_id = ? AND merchant_id = ? " +
//                            "AND is_settled_consider ='Y' AND date_time BETWEEN ? AND ? " +
//                            "AND TransLifeCycle IN ('RNS')";
//            log.info("sql : " + sqlRNS);
//
//            List<LifeCycleDto> lifeCycleDtoListRNS = JdbcTemplate.query(sqlRNS,
//                    new Object[]{cycleId, proccessId, merchantId, newDateStr + " 00:00:00", reqDate + " 23:59:59"},
//                    new RowMapper<LifeCycleDto>() {
//                        @Override
//                        public LifeCycleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            return new LifeCycleDto(rs.getString("addedBy"), rs.getString("fileName"),
//                                    rs.getString("transactionId"), rs.getString("rrnStatus"), rs.getString("remarks"));
//                        }
//                    });
//            log.info("lifeCycleDtoListRNS : " + lifeCycleDtoListRNS);
//
//            if (!lifeCycleDtoListRNS.isEmpty()) {
//                updateRecords(lifeCycleDtoListRNS);
//            }
//        }
//		String Transactionlifecycle1 = lifeCycleTransactionRepo.getStatus(cycleId, proccessId, merchantId,
//				newDateStr + " 00:00:00", reqDate + " 23:59:59");
//        if (Transactionlifecycle1.equalsIgnoreCase("RSR")) {
//            String sqlRSR = "SELECT 'admin' AS addedBy, '' AS fileName, Id AS transactionId, " +
//                            "CASE WHEN TransLifeCycle = 'RSR' THEN 'RS' END AS rrnStatus, " +
//                            "CASE WHEN TransLifeCycle = 'RSR' THEN 'Settled' END AS remarks " +
//                            "FROM tbl_transactionmaster WHERE cycleId = ? AND process_id = ? AND merchant_id = ? " +
//                            "AND is_settled_consider ='Y' AND date_time BETWEEN ? AND ? " +
//                            "AND TransLifeCycle IN ('RSR')";
//            log.info("sql : " + sqlRSR);
//
//            List<LifeCycleDto> lifeCycleDtoListRSR = JdbcTemplate.query(sqlRSR,
//                    new Object[]{cycleId, proccessId, merchantId, newDateStr + " 00:00:00", reqDate + " 23:59:59"},
//                    new RowMapper<LifeCycleDto>() {
//                        @Override
//                        public LifeCycleDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                            return new LifeCycleDto(rs.getString("addedBy"), rs.getString("fileName"),
//                                    rs.getString("transactionId"), rs.getString("rrnStatus"), rs.getString("remarks"));
//                        }
//                    });
//            log.info("lifeCycleDtoListRSR : " + lifeCycleDtoListRSR);
//
//            if (!lifeCycleDtoListRSR.isEmpty()) {
//                Msg = updateRecords(lifeCycleDtoListRSR);
//            }
//        }
//        log.info("JSONObject(Msg).toString() : " + Msg.toString());
//
//        return new JSONObject(Msg).toString();
//	}
//
//	 private Map<String, Object> updateRecords(List<LifeCycleDto> lifeCycleDtoList) {
//	        String trans = null;
//	        JSONArray array = new JSONArray();
//	        JSONObject js1 = new JSONObject();
//	        Map<String, LifeCycleDto> docTypes = new HashMap<>();
//	        String TransStatus = lifeCycleDtoList.get(0).getRrnStatus();
//	        String FileName = lifeCycleDtoList.get(0).getFileName();
//	        String Remark = lifeCycleDtoList.get(0).getRemarks();
//	        String AddedBy = lifeCycleDtoList.get(0).getAddedBy();
//
//	        log.info("TransStatus ::::::: " + TransStatus);
//	        log.info("FileName ::::::: " + FileName);
//	        log.info("Remark ::::::: " + Remark);
//	        log.info("AddedBy ::::::: " + AddedBy);
//
//	        for (LifeCycleDto dto : lifeCycleDtoList) {
//	            log.info("dto ::::::: " + dto);
//
//	            if (trans == null) {
//	                trans = dto.getTransactionId();
//	            } else {
//	                trans = trans.concat("#" + dto.getTransactionId());
//	            }
//
//	            log.info("trans ::::::: " + trans);
//	        }
//
//	        log.info("Transaction by hash:::::::::::: " + trans);
//	        List<Map<String, Object>> StatusCount = lifeCycleTransactionRepo.updaterecords(trans, TransStatus, FileName, Remark, AddedBy);
//	        log.info("Transaction by hash::::::StatusCount:::::: " + StatusCount);
//	        js1.put("Data", StatusCount);
//	        return js1.toMap();
//	    }
//}
