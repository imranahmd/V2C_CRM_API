package com.crm.Controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crm.Repository.BulkRefundSqlRepo;
import com.crm.dto.BeneFiledsDto;
import com.crm.dto.PayouCallbackResponse;
import com.crm.model.MerchantList;
import com.crm.util.GeneralUtil;
import com.crm.util.S2SCall;
import com.google.gson.Gson;

@Controller
@RequestMapping("/payout")
public class MerchantPayoutCallback {

	
}
