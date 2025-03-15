package com.crm.util;

import java.util.HashMap;
import java.util.Map;

public class ActionUtil {

	public static Map<String, String> actionMap = new HashMap<>();
	
	static {
		actionMap.put("1029","com.recon.pg.refundProcessor.SBIAutoRefundProcessor");
	}
	
}
