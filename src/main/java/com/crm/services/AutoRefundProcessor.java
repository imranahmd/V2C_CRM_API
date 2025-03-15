package com.crm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crm.dto.TransactionMaster;
import com.crm.util.ActionUtil;

public abstract class AutoRefundProcessor {

	static Logger log = LoggerFactory.getLogger(AutoRefundProcessor.class);
	
	@Autowired
	static
	JdbcTemplate jdbcTemplate;
	
	public abstract String verficationCall(TransactionMaster txnMaster) ;
	
	 public static ArrayList<String> getSPMidKey(final String mid, final String bankId, final String instrumentId, final String spId) {
	        String sProviderSQL = null;
	       
	    	
	        ArrayList<String> spMidKey = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        Connection con=null;
	        try {
	        con =jdbcTemplate.getDataSource().getConnection();
	        	//	jdbcTemplate.getDataSource().getConnection();
	        
	       
	            sProviderSQL = "select mid, tid from tbl_merchant_mdr where merchant_id = '" + mid + "' and sp_id = '" + spId + "' and instrument_id = '" + instrumentId + "' and bank_id = '" + bankId + "'";
	           log.info("getSPMidKey() : " + sProviderSQL);
	            if (con != null) {
	                ps = con.prepareStatement(sProviderSQL);
	                rs = ps.executeQuery();
	                if (rs.next()) {
	                   log.info("==================================inside while===============================");
	                    spMidKey = new ArrayList<String>();
	                    spMidKey.add(rs.getString(1));
	                    spMidKey.add(rs.getString(2));
	                }
	               log.info("spMidKey ::: " + spMidKey.get(0).toString());
	               log.info("spMidKey ::: " + spMidKey.get(1).toString());
	                rs.close();
	                ps.close();
	                con.close();
	            }
	        }
	        catch (SQLException e) {
	            log.error(e.getMessage(),e);
	            log.error("DataManager.java ::: getSPMidKey() :: Error Occurred : ", (Throwable)e);
	        }
	        finally {
	            try {
	                if (rs != null) {
	                    rs.close();
	                    rs = null;
	                }
	                if (ps != null) {
	                    ps.close();
	                    ps = null;
	                }
	                if (con != null) {
	                    con.close();
	                    con = null;
	                }
	            }
	            catch (SQLException e4) {
	                log.error("DataManager.java ::: getSPMidKey() :: Error Occurred while closing Connection : ", (Throwable)e4);
	            }
	        }
	        return spMidKey;
	    }

		
	public static Object getRequestedData(String bankId)
	{
		Class cls;
		
		try 
		{
			cls = Class.forName(ActionUtil.actionMap.get(bankId));
			return (AutoRefundProcessor) cls.newInstance();
		} 
		catch (ClassNotFoundException e) 
		{
			log.error(e.getMessage(),e);
		} 
		catch (InstantiationException e) 
		{
			log.error(e.getMessage(),e);
		} 
		catch (IllegalAccessException e) 
		{
			log.error(e.getMessage(),e);
		}
		
		return null;
		
	}

	public abstract Integer insertRefundData(String transId, String refundAmt, String userId);
}
