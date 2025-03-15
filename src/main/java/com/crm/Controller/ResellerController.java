package com.crm.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.ResellerRepository;
import com.crm.ServiceDaoImpl.SecondCon;
import com.crm.dto.BusinessTypeDto;
import com.crm.dto.MerchantDocDto;
import com.crm.dto.ResellerBankAccountDto;
import com.crm.dto.ResellerBasicSetupDto;
import com.crm.dto.ResellerDto;
import com.crm.dto.ResellerKycDocDto;
import com.crm.dto.ResellerListPaginationDto;
import com.crm.dto.UploadDocDetailsDto;
import com.crm.dto.UploadDocDto;
import com.crm.model.MerchantKycDoc;
import com.crm.model.ResellerBankAcc;
import com.crm.model.ResellerEntity;
//import com.crm.services.EmailService;
import com.crm.services.ResellerService;
import com.crm.services.UserDetailsui;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


@CrossOrigin(origins = { "http://localhost:4200"})


@RestController

public class ResellerController {
	private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	public static String uploadDirectory = "/home/KYCDOCUMENTS";
	
	@Autowired
	private ResellerService resellerService;
	
//	@Autowired
//    private EmailService emailService;
//	
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	 @PostMapping("/create-update-reseller")
	public ResellerDto createUpdateReseller(@RequestBody ResellerDto resellerReqDto) {
    	ResellerDto respResellerDto = null;
    	
    	
    	
    	SecondCon secondCon = new SecondCon();
    	UserDetailsui userDetailsui = new UserDetailsui();
    				
    	String data = userDetailsui.getUserDetails();
    	JSONObject userDetails = new JSONObject(data);

    	String username = null;
    	String ipAddress = null;
    	
    	
    	try {
    		
    		
    		
    		username = userDetails.optString("Username");
    		ipAddress = userDetails.optString("ipAddress");
    		
    		
			respResellerDto = resellerService.createUpdateReseller(resellerReqDto);
			
			
			secondCon.insertIntoSecondSchema(username, "Create-update-reseller", resellerReqDto.toString(), respResellerDto.toString(), ipAddress);
			
			
		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Create-update-reseller-Exception", resellerReqDto.toString(), stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
    	
    	return respResellerDto;
    	
	}
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/reseller-view-list")
	public ResellerListPaginationDto ResellerListDetails(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String merchantName = js.getString("rname");
		String merchantId = js.getString("rid");
		String fromDate = js.getString("startDate");
		String toDate = js.getString("endDate");
		int norecord = js.getInt("pageRecords");
		int pageno =js.getInt("pageNumber");
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		ResellerListPaginationDto resellerDetails = null;
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resellerDetails = resellerService.GetCreationDetails(fromDate, toDate, merchantId, merchantName, norecord, pageno); 
			
			
			
			ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(resellerDetails);
			
			
			
			
			
			
			secondCon.insertIntoSecondSchema(username, "Reseller-view-list", jsonBody, jsonString, ipAddress);
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Reseller-view-list-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return resellerDetails;
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping("/get-reseller-detail-by-resellerid")
	public ResellerDto getListbyPId(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String resellerId = js.getString("resellerId");
		ResellerDto resellerDetails = null;
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			resellerDetails = resellerService.getResellerPersonalDetailByResellerId(resellerId);
			
			
			secondCon.insertIntoSecondSchema(username, "Get-reseller-detail-by-resellerid", jsonBody, resellerDetails.toString(), ipAddress);

		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get-reseller-detail-by-resellerid-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}

		return resellerDetails;
	}

    
    @CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/create-reseller-bank")
	public ResellerBankAccountDto postResellerBankData(@RequestBody ResellerBankAcc resellerBankAcc) {
    	ResellerBankAccountDto resellerBankinfo = null;
    	
    	
    	SecondCon secondCon = new SecondCon();
    	UserDetailsui userDetailsui = new UserDetailsui();
    				
    	String data = userDetailsui.getUserDetails();
    	JSONObject userDetails = new JSONObject(data);

    	String username = null;
    	String ipAddress = null;
    	
    	
    	
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			resellerBankinfo = resellerService.createResellerBankDetails(resellerBankAcc);
			
			
			secondCon.insertIntoSecondSchema(username, "Create-reseller-bank", resellerBankAcc.toString(), resellerBankinfo.toString(), ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Create-reseller-bank-Exception", resellerBankAcc.toString(), stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
		}
		return resellerBankinfo;
	}

	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping("/update-reseller-bank")
	public ResellerBankAccountDto postResellerBankUpdt(@RequestBody ResellerBankAcc resellerBankAcc) {
		ResellerBankAccountDto resellerBankupdtinfo = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resellerBankupdtinfo = resellerService.updateResellerBankDetails(resellerBankAcc);
			
			
			secondCon.insertIntoSecondSchema(username, "Update-reseller-bank", resellerBankAcc.toString(), resellerBankupdtinfo.toString(), ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update-reseller-bank-Exception", resellerBankAcc.toString(), stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
		}
		return resellerBankupdtinfo;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping("/get-reseller-bankacc-by-resellerid")
	public ResellerBankAccountDto getListBankAccbyPId(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		String resellerId = js.getString("resellerId");
		ResellerBankAccountDto resellerBankDetails = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");

			resellerBankDetails = resellerService.getResellerBankDetailByResellerId(resellerId);
			
			
			secondCon.insertIntoSecondSchema(username, "Get-reseller-bankacc-by-resellerid", jsonBody, resellerBankDetails.toString(), ipAddress);

		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get-reseller-bankacc-by-resellerid-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}

		return resellerBankDetails;
	}
	
	
	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/reseller-view-list-bankacc")
	public List<ResellerBankAcc> ResellerListBankAccDetails() {
		
		List<ResellerBankAcc> resellerListBankAccDetails = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resellerListBankAccDetails = resellerService.GetViewListOfBankAccOfReseller(); 
			
			
			
			secondCon.insertIntoSecondSchema(username, "Reseller-view-list-bankacc", "Reseller-view-list-bank acc", resellerListBankAccDetails.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Reseller-view-list-bank acc", "Reseller-view-list-bank acc", stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return resellerListBankAccDetails;
	}
	
	
	
	
	// soft delete only
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/delete-reseller-detail-byid")
	public Optional<ResellerEntity> deleteResellerPersonaDetail(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		BigInteger Pid = js.getBigInteger("id");
		Optional<ResellerEntity> resellerInfo = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			resellerInfo = resellerService.deleteResellerPersonaDetail(Pid);
			
			
			secondCon.insertIntoSecondSchema(username, "Delete-reseller-detail-byid", jsonBody, resellerInfo.toString(), ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete-reseller-detail-byid-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
		}
		return resellerInfo;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/delete-resellerBank-byid")
	public List<ResellerBankAccountDto> deleteResellerBankAcc(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		BigInteger Pid = js.getBigInteger("id");
		String resellerId = js.getString("resellerId");
		List<ResellerBankAccountDto> resellerBankDetails = null;
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resellerBankDetails = resellerService.deleteResellerBankAcc(Pid, resellerId);
			
			
			secondCon.insertIntoSecondSchema(username, "Delete-resellerBank-byid", jsonBody, resellerBankDetails.toString(), ipAddress);
			
		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete-resellerBank-byid-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
		return resellerBankDetails;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/resellerKyc-docUpload")
	public List<ResellerKycDocDto> kycDocumentUpload(@RequestParam MultipartFile imageFile , String resellerId, String docType, String docId, String username) {

		StringBuilder filesname = new StringBuilder();
		String ResellerId = resellerId;
		String DocType = docType;
		String DocId = docId;
		String User = username;
		String fileOrgname = imageFile.getOriginalFilename();
		String filesname1 = uploadDirectory + "/" + ResellerId +"/";
		fileOrgname= fileOrgname.replaceAll("\\s", "");
		String nameFileconvert = DocId +"_"+fileOrgname;
		
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		//String username = null;
		String ipAddress = null;
		
		
		
		
		File file = new File(filesname1);
		if (!file.exists()) {            
            file.mkdirs();            
        }		
		List<ResellerKycDocDto> resellerKycDocList = new ArrayList<>();
		
		
		try {
			
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			Path path = Path.of(filesname1,nameFileconvert);
			filesname.append(nameFileconvert);
			Files.write(path, imageFile.getBytes());
			String DocRef = filesname1 + nameFileconvert;
			ResellerKycDocDto resellerKycDoc = new ResellerKycDocDto();
			
			
			resellerKycDoc.setDocName(nameFileconvert);
			resellerKycDoc.setDocType(DocType);
			resellerKycDoc.setDocpath(DocRef);
			resellerKycDoc.setResellerId(ResellerId);
			resellerKycDoc.setModifiedBy(User);
			long millis = System.currentTimeMillis();
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(millis));
			resellerKycDoc.setModifiedOn(timeStamp);
			resellerKycDoc.setUpdatedBy(User);
			resellerKycDoc.setUpdateOn(timeStamp);			

			resellerKycDocList.add(resellerKycDoc);
			
			
			secondCon.insertIntoSecondSchema(username, "ResellerKyc-docUpload", resellerId, resellerKycDocList.toString(), ipAddress);
			
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "ResellerKyc-docUpload-Exception", resellerId, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
			
		return resellerKycDocList;		
	
	}
	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/reseller-insertKycdocs-details")
	public List<ResellerKycDocDto> uploadImage(@RequestParam String resellerId, String documentDetails  ) {
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;

		String MerchantId = resellerId;
		String DocType = documentDetails;		
		List<ResellerKycDocDto> resellerkycdto = null;

		try {

			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resellerkycdto = resellerService.insertKycDoc(MerchantId, DocType);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Reseller-insertKycdocs-details", resellerId, resellerkycdto.toString(), ipAddress);
	
		} catch (Exception e) {
			
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Reseller-insertKycdocs-details-Exception", resellerId, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
			
		return resellerkycdto;
		
	}
	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/reseller-insertKycdocs-detailsV2")
	public List<ResellerKycDocDto> uploadImageV2(@RequestBody UploadDocDto uploadDoc) {
		
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		

		String MerchantId = uploadDoc.getMerchantId();
		Map<String,UploadDocDetailsDto> docTypes = new HashMap<>();
		
		int i = 0;
        for(UploadDocDetailsDto dto : uploadDoc.getDocType())
        {
            docTypes.put(String.valueOf(i), dto);
            i++;
        }
		List<ResellerKycDocDto> resellerkycdto = null;

		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			String DocType = mapper.writeValueAsString(docTypes);
					
			resellerkycdto = resellerService.insertKycDoc(MerchantId, DocType);
			
			
			secondCon.insertIntoSecondSchema(username, "Reseller-insertKycdocs-detailsV2", uploadDoc.toString(), resellerkycdto.toString(), ipAddress);
	
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Reseller-insertKycdocs-detailsV2-Exception", uploadDoc.toString(), stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
			
		return resellerkycdto;
		
	}
	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/getResellerKyc-DocumentList")
	public List<BusinessTypeDto> getDocumentList(@RequestBody String jsonBody) {
		
		JSONObject js = new JSONObject(jsonBody);		
		int businesstypeId = js.getInt("businesstypeId");
		String resellerId = js.getString("resellerId");
		
		List<BusinessTypeDto> bTypeDto = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			bTypeDto = resellerService.getKycDocumentList(businesstypeId, resellerId);
			
			
			secondCon.insertIntoSecondSchema(username, "GetResellerKyc-DocumentList", jsonBody, bTypeDto.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "GetResellerKyc-DocumentList-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
		return bTypeDto;
		
	}
	
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/download-reseller-uploadfiles")
	public String getuploadFiles(@RequestParam String urlfile, String docname) throws IOException {	

		String urlfile1 = urlfile;
		String fileName = docname;
		Path path = Path.of(urlfile1);
		File file = new File(urlfile1);
		byte[] fileContent = Files.readAllBytes(file.toPath());	
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		username = userDetails.optString("Username");
		ipAddress = userDetails.optString("ipAddress");
		
		
		secondCon.insertIntoSecondSchema(username, "Get User List", docname+"/"+urlfile, fileContent.toString(), ipAddress);
		return Base64.getEncoder().encodeToString(fileContent);
		
	}
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/delete-resellerupload-image")
	public List<ResellerKycDocDto> deleteImage(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		int Id = js.getInt("id");
//		String docName = js.getString("docname");
		String path = js.getString("path");
		String ResellerId = js.getString("resellerId");
		List<ResellerKycDocDto> kycdto = null;
		ResellerKycDocDto merchantKycDoc = new ResellerKycDocDto();
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			

//			merchantKycDoc.setDocName(docName);
			merchantKycDoc.setId(Id);
			merchantKycDoc.setDocpath(path);
			merchantKycDoc.setResellerId(ResellerId);
			kycdto = resellerService.deleteuploaddoc(merchantKycDoc);
			
			
			
			secondCon.insertIntoSecondSchema(username, "Delete-resellerupload-image", jsonBody, kycdto.toString(), ipAddress);

		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Delete-resellerupload-image-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			
			e.printStackTrace();
		}

		return kycdto;

	}
	@CrossOrigin(origins = {"http://localhost:4200" })
	@PostMapping("/update-Reseller-BasicSetup")
	public List<ResellerBasicSetupDto> updByMerchantId(@RequestBody String jsonBody) {
		
		JSONObject js = new JSONObject(jsonBody);		
		String ressellerId = js.getString("ressellerId");
		String returnUrl = js.getString("returnUrl");
		String isAutoRefund = js.getString("isAutoRefund");
		String hours = js.getString("hours");
		String minutes = js.getString("minutes");
		String isPushUrl = js.getString("isPushUrl");
		String pushUrl = js.getString("pushUrl");
		String settlementCycle = js.getString("settlementCycle");
		String resellerDashboardRefund = js.getString("resellerDashboardRefund");
		String rsDisableRefundCc = js.getString("rsDisableRefundCc");
		String rsDisableRefundDc = js.getString("rsDisableRefundDc");
		String rsDisableRefundNb = js.getString("rsDisableRefundNb");
		String rsDisableRefundUpi = js.getString("rsDisableRefundUpi");
		String rsDisableRefundWallet = js.getString("rsDisableRefundWallet");
		String refundApi = js.getString("refundApi");
		String refundApiDisableCc = js.getString("refundApiDisableCc");
		String refundApiDisableDc = js.getString("refundApiDisableDc");
		String refundApiDisableNb = js.getString("refundApiDisableNb");
		String refundApiDisableUpi = js.getString("refundApiDisableUpi");
		String refundApiDisableWallet = js.getString("refundApiDisableWallet");
		String integrationType = js.getString("integrationType");
		String isretryAllowed = js.getString("isretryAllowed");
		String bpsEmailNotification = js.getString("bpsEmailNotification");
		String bpsSmsNotification = js.getString("bpsSmsNotification");
		String bpsEmailReminder = js.getString("bpsEmailReminder");
		String reportCycling = js.getString("reportCycling");
		List<ResellerBasicSetupDto> resBasicSetup = null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			resBasicSetup = resellerService.setResellerBasicInfo(ressellerId, returnUrl, isAutoRefund, hours, minutes, isPushUrl, pushUrl, 
					settlementCycle, resellerDashboardRefund, rsDisableRefundCc, rsDisableRefundDc, rsDisableRefundNb, rsDisableRefundUpi,
					rsDisableRefundWallet, refundApi, refundApiDisableCc, refundApiDisableDc, refundApiDisableNb, refundApiDisableUpi, 
					refundApiDisableWallet, integrationType, isretryAllowed, bpsEmailNotification, bpsSmsNotification, bpsEmailReminder,reportCycling );
			
			
			
			secondCon.insertIntoSecondSchema(username, "Update-Reseller-BasicSetup", jsonBody, resBasicSetup.toString(), ipAddress);
			
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Update-Reseller-BasicSetup-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		
		return resBasicSetup;
		
	}
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/ChangeResellerStatus")
	public String ChangeResellerStatus(@RequestBody String jsonBody) {
		JSONObject js = new JSONObject(jsonBody);
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
		String Date = dateFormat.format(date);  
		String Rid = js.getString("Rid");
		String Status = js.getString("Status");
		String Remark = js.getString("Remark");
		String Type= js.getString("Approvel_type");
		String Added_By= js.getString("Added_By");

		String ResellerDetails = null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
		if(Status.equalsIgnoreCase("Suspended") && Type.equalsIgnoreCase("Suspended"))
		{
			 Date= js.getString("Date");
		}
		

		ResellerDetails = resellerService.Reseller_Status_change(Status,Remark,Rid,Type,Date,Added_By);
		if(Rid!=null && !Rid.isBlank() && Status.equalsIgnoreCase("Active") && Type.isBlank()) {
			System.out.println("ResellerDetails:::"+ResellerDetails);
			
		
			if(ResellerDetails!=null) {
				
				JSONArray jsonArray = new JSONArray(ResellerDetails);
				
	            for(int i=0;i<jsonArray.length();i++)
	            {
	                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
	                String resellerId = jsonObject1.optString("reseller_id");
	                String resellerName = jsonObject1.optString("reseller_name");
	                String emailId = jsonObject1.optString("reseller_email_id");
	                String merReturnUrl = jsonObject1.optString("return_url");
	                String password = jsonObject1.optString("password");
	                String oldStatus = jsonObject1.optString("oldStatus");
	                String emailTrigger = jsonObject1.optString("emailTrigger");
	                String transactionKey = "";

	                
	                if(emailTrigger.equalsIgnoreCase("0") && oldStatus.equalsIgnoreCase("Pending")) {
	                 //emailService.sendresellerMessage(resellerId, resellerName, emailId, merReturnUrl, password, transactionKey);
	                	}
	            	}
	           
				}
			}
		
		secondCon.insertIntoSecondSchema(username, "ChangeResellerStatus", jsonBody, ResellerDetails.toString(), ipAddress);
		 
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Change Reseller Status-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return ResellerDetails;
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getFinancial-year")
	public ResponseEntity<?> getFinanacialYears(@RequestBody String jsonBody){		
		Date date = Calendar.getInstance().getTime();  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String Date = dateFormat.format(date);
		Map<String, Object> responseMsg= null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			responseMsg= resellerService.getFinanacialYear(Date);
			
			
			secondCon.insertIntoSecondSchema(username, "Get Financial-year", jsonBody, responseMsg.toString(), ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Financial-year-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseMsg);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getFinancial-Month")
	public ResponseEntity<?> getFinanacialMonth(@RequestBody String jsonBody){
		JSONObject js = new JSONObject(jsonBody);
		String fYear= js.getString("fYearName");
		Map<String, Object> responseMsg= null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			responseMsg= resellerService.getFinanacialMonth(fYear);
			
			
			secondCon.insertIntoSecondSchema(username, "Get Financial-Month", jsonBody, responseMsg.toString(), ipAddress);
			
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Financial-Month-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseMsg);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/getMonth-date")
	public ResponseEntity<?> getFinanacialMonthDate(@RequestBody String jsonBody){
		JSONObject js = new JSONObject(jsonBody);
		String fMonth= js.getString("fmonthName");	
		Map<String, Object> responseMsg= null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {	
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			responseMsg= resellerService.getFinanacialMonthDt(fMonth);
			
			secondCon.insertIntoSecondSchema(username, "Get Month-date", jsonBody, responseMsg.toString(), ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "Get Month-date-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseMsg);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/AttachmentUpload")
	public ResponseEntity<?> getAttachmentUpload(@RequestParam MultipartFile imageFile , String MerchantId, String AddedBy){
		Map<String, Object> responseMsg= null;
		
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		
		try {			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			
			responseMsg= resellerService.getAttachmentUploafFile(imageFile, MerchantId, AddedBy);
			
			
			secondCon.insertIntoSecondSchema(username, "AttachmentUpload", imageFile+"/"+MerchantId+"/"+AddedBy, responseMsg.toString(), ipAddress);
		} catch (Exception e) {
			
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "AttachmentUpload-Exception", imageFile+"/"+MerchantId+"/"+AddedBy, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseMsg);
	}
	
	@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/AttachmentUpload-List")
	public ResponseEntity<?> getAttachmentUploadList(@RequestBody String jsonBody){
		JSONObject js = new JSONObject(jsonBody);
		String merchantId= js.getString("MerchantId");
		Map<String, Object> responseMsg= null;
		
		
		SecondCon secondCon = new SecondCon();
		UserDetailsui userDetailsui = new UserDetailsui();
					
		String data = userDetailsui.getUserDetails();
		JSONObject userDetails = new JSONObject(data);

		String username = null;
		String ipAddress = null;
		
		
		try {
			
			
			username = userDetails.optString("Username");
			ipAddress = userDetails.optString("ipAddress");
			
			
			responseMsg= resellerService.getAttachmentUploafList(merchantId);
			
			
			
			
			secondCon.insertIntoSecondSchema(username, "Attachment Upload-List", jsonBody, responseMsg.toString(), ipAddress);
		} catch (Exception e) {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTraceString = sw.toString();
									
			secondCon.insertIntoSecondSchema(username, "AttachmentUpload-List-Exception", jsonBody, stackTraceString, ipAddress);
			
			
			e.printStackTrace();
		}
		return ResponseEntity.ok(responseMsg);
	}
}
