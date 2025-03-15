package com.crm.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.Repository.ChargebackRepo;
import com.crm.helper.Response;
import com.crm.services.ChargebackMerchantService;
import com.crm.services.lBPSService;

@RestController
public class ChargeBackMerchantController {

	private static Logger log = LoggerFactory.getLogger(ChargeBackMerchantController.class);

	
	@Autowired
	private ChargebackMerchantService chargebackMerchantService;
	
	@Value("${chargeback.remove.files}")
	private String chargebackRemoveFiles;
	
	
	@Value("${chargeback.zip.file.store}")
	private String chargebackZipFile;
	
	@Autowired
	private ChargebackRepo chargebackRepo;
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "/getChargeBackMerList", produces = "application/json")
	public String getChargeBackMerList(@RequestBody String jsonBody) throws Exception {
		
		 Response res = new Response();
		log.info("inside the chargeback:::::");
		log.info("jsonBody::::::::" + jsonBody);
		JSONObject js = new JSONObject(jsonBody);
		log.info("js::::::" + js);
		String iMerchantId = js.getString("iMerchantId");
		String iTxnId = js.getString("iTxnId");
		String FromDate = js.getString("FromDate");
		String ToDate = js.getString("ToDate");
		
		log.info("iMerchantId:::"+iMerchantId+"::iTxnId::"+iTxnId+ "::FromDate::"+FromDate+"::ToDate::"+ToDate);
		
		String getChargeBackMerListData = chargebackMerchantService.getChargeBackMerListData(iMerchantId,iTxnId,FromDate,ToDate);
		
//		log.info("getChargeBackMerListData::::::::"+getChargeBackMerListData+":::::::::");
//		log.info("getChargeBackMerListData::::::::"+getChargeBackMerListData.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("^\"|\"$", ""));
		
		if(getChargeBackMerListData.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("^\"|\"$", "").equalsIgnoreCase("Not Found Data")) {
			
			JSONObject response = new JSONObject();
			response.append("Error", "No Data Found!");
			
			getChargeBackMerListData = response.toString();
		}
		
		res.setStatus(true);
//        res.setMessage(message);
        res.setResult(getChargeBackMerListData);
		
		return getChargeBackMerListData;
	
	}
	
	
	@CrossOrigin(origins = { "http://localhost:4200"})
	@PostMapping(value = "RaisedchargebackinsertMerchant", produces = "application/json")
	public String RaisedchargebackinsertMerchant(@RequestParam MultipartFile[] files, String CBId, String TranId,
			String refNo, String commenMerchant, String merchantId) {
		String ResponseValue = null;
		ZipOutputStream zos = null;
		BufferedInputStream bis = null;
		String ChargeBackId = CBId;
		String MerchantId = merchantId;
		String tranId = TranId;
		String RefNo = refNo;
		String Remarks = commenMerchant;
		int insertchargebacksMerchant = 0;
//		String insertchargebacksMerchant = null;
		try {

			log.info("ChargeBackId::::" + ChargeBackId + ":::MerchantId:::" + MerchantId + ":::::TxnId:::" + tranId
					+":::Remark:::::" + Remarks + "::::RefNo::::" + RefNo
					);
			// upload multiple files and make it zip

			List<String> fileNames = new ArrayList<>();

			Arrays.asList(files).stream().forEach(file -> {
				chargebackMerchantService.uploadFile(file);
				fileNames.add(file.getOriginalFilename());
			});
			log.info("fileNames:::::" + fileNames.get(0));
			

			if (fileNames.get(0) == null || fileNames.get(0).equalsIgnoreCase("")) {
		        // The files array is null or empty
		        log.info("No files uploaded.:::::::::::");
		        String zipName="No Document";
		        insertchargebacksMerchant = chargebackRepo.saveRaisedchargebackMerchantupdateChargeBackData(ChargeBackId, merchantId, tranId, refNo, zipName, Remarks);
				
				 log.info("insertchargebacksMerchant::::::::::"+insertchargebacksMerchant);
		    } else {
		        // The files array is not null and contains at least one file
		    	log.info("Files uploaded::::::::: " + files.length);
		        
		    	List<String> filess = new ArrayList<String>();

				for (int i = 0; i < fileNames.size(); i++) {
					log.info("fileNames:::::" + fileNames.get(i));
//					filess.add("D:/home/abc/" + fileNames.get(i));
					
					 File fileval = new File(chargebackRemoveFiles);

						if (!fileval.exists()) {
							fileval.mkdirs();
						}
					filess.add(chargebackRemoveFiles + fileNames.get(i));
				}

				// create random name for zip
				// create a string of all characters
				String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

				// create random string builder
				StringBuilder sb = new StringBuilder();

				// create an object of Random class
				Random random = new Random();

				// specify length of random string
				int length = 7;

				for (int i = 0; i < length; i++) {

					// generate random index number
					int index = random.nextInt(alphabet.length());

					// get character specified by index
					// from the string
					char randomChar = alphabet.charAt(index);

					// append the character to string builder
					sb.append(randomChar);
				}

				String randomString = sb.toString();
				log.info("Random String is: " + randomString);
				String zipName = randomString + ".zip";
				log.info("zipName:::::" + zipName);
				zipFiles1(filess, zipName);

				 insertchargebacksMerchant = chargebackRepo.saveRaisedchargebackMerchantupdateChargeBackData(ChargeBackId, merchantId, tranId, refNo, zipName, Remarks);
			
				 log.info("insertchargebacksMerchant::::::::::"+insertchargebacksMerchant);
		     
		    }
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (insertchargebacksMerchant == 1) {
			JSONObject respons = new JSONObject();
			respons.put("Status", "success");
			respons.put("Reason", "Chargeback Raised Successfully.");
			ResponseValue = respons.toString();
		} else {
			JSONObject respons = new JSONObject();
			respons.put("Status", "fail");
			respons.put("Reason", "Oops, something went wrong!");
			ResponseValue = respons.toString();

		}

		return ResponseValue;
	}
	
	
	public void zipFiles1(List<String> files, String zipName) {

		FileOutputStream fos = null;
		ZipOutputStream zipOut = null;
		FileInputStream fis = null;
		try {
			File fileval = new File(chargebackZipFile);

			if (!fileval.exists()) {
				fileval.mkdirs();
			}
			

			
//              fos = new FileOutputStream("D:/home/chargebackDocumentAdmin/"+zipName);
			fos = new FileOutputStream(chargebackZipFile + zipName);

			zipOut = new ZipOutputStream(new BufferedOutputStream(fos));
			for (String filePath : files) {
				File input = new File(filePath);
				fis = new FileInputStream(input);
				ZipEntry ze = new ZipEntry(input.getName());
				log.info("Zipping the file: " + input.getName());
				zipOut.putNextEntry(ze);
				byte[] tmp = new byte[4 * 1024];
				int size = 0;
				while ((size = fis.read(tmp)) != -1) {
					zipOut.write(tmp, 0, size);
				}
				zipOut.flush();
				fis.close();
			}
			zipOut.close();
			log.info("Done... Zipped the files...");

			// remove the files from the directory

//              File directory = new File("D:/home/abc/");
			
			
			File directory = new File(chargebackRemoveFiles);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			log.info("directory::::::" + directory);

			for (File file : Objects.requireNonNull(directory.listFiles())) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ex) {

			}
		}
	}
}
