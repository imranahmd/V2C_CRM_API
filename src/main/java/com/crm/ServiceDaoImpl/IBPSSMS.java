package com.crm.ServiceDaoImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class IBPSSMS {
  private static Logger log = LoggerFactory.getLogger(IBPSSMS.class);

	public String getURL(String longURL) {

		String URL = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyDfzzw4lwXwKlPZK47L5-mwXKiJFcreuRI";
		String data = "{\"longUrl\": \"" + longURL + "\"}";

		try {

			String line = null;
			System.out.println("finalURL >>>>>> >>>> " + URL);
			URL url = new URL(URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
			con.addRequestProperty("Content-Length", data.getBytes().length + "");
			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			System.out.println("Data length :: " + data.getBytes().length);
			wr.write(data);
			wr.flush();
			StringBuffer response = new StringBuffer();
			// StringBuilder response = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("HTTP OK....");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null) {
					System.out.println(line);
					response.append(line);
				}
				br.close();
			} else {
				System.out.println("HTTP Error....");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()));

				while ((line = br.readLine()) != null) {
					System.out.println(line);
					response.append(line);
				}
				br.close();
				return null;
			}

			con.disconnect();
			response.toString();

			System.out.println("response message :: " + response.toString());
			return response.toString();
		} catch (Exception e) {
			log.error(e.getMessage(),e);;
		}

		return null;
	}

	public void sendSMS(String sText, String c_mobile_no) throws Exception {
		// TODO Auto-generated method stub
		String sURL = "http://apps.instatechnow.com/sendsms/sendsms.php?";

		URL obj = new URL(sURL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		String urlParameters = "username=ISVpay&password=123456&type=TEXT&sender=PAYFI"
				+ "&mobile="+c_mobile_no+"&message=" + sText+"&peId=1001845912764659704&tempId=1007044768335602643";
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		log.info("Sending 'GET' request to URL : " + sURL);
		log.info("Post parameters : " + urlParameters);
		log.info("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	// where is ur invoce getting generated?

	public static void main(String[] args) {

		IBPSSMS sms = new IBPSSMS();
		try {
			
			
			String folderPath = "E:/dsnjsd-docs/home/Documents/Templates/EmailTemplates/";
			folderPath = folderPath + "M00024";
			File file = new File(folderPath,"invoiceTemplate.html");
			System.out.println("file Path Exist :::::: "+file.getPath() + "path :::::::"+file.exists());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);;
		}
	}

}
