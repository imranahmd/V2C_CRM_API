package com.crm.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class VassFileReader {
	public static String uploadDirectory = "/home/payRefund/";
	public static String getPropertyValue(String variableName) throws IOException {
		FileInputStream inputStream = null;
		String variableValue = null;

		try {
			File file = new File(uploadDirectory + "paypg.Properties");
			if (file != null && file.exists()) {
				inputStream = new FileInputStream(file);

				if (inputStream != null) {
					Properties prop = new Properties();
					prop.load(inputStream);
					variableValue = prop.getProperty(variableName);


				}
			} else {
				System.out.println("Error Message :: File Not Found");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return variableValue;
	}
	
	public String refundRequestCall(String requestURL, String data) {
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;

		System.out.println("S2SCall.java ::: secureServerCall() :: Posting URL : " + requestURL);

		try {
			URL obj = new URL(requestURL);
			// HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json;charset=UTF-8");
			con.addRequestProperty("Content-Length", data.getBytes().length + "");
			con.setDoOutput(true);
			con.connect();
			
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			// log.info(new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date())+" :::
			// Data length :: " + data.getBytes().length);
			wr.write(data);
			wr.flush();
			System.out.println("wrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrL : " + con.getResponseCode() );

			respString = new StringBuffer();
			
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				System.out.println("S2SCall.java ::: secureServerCall() :: HTTP OK");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null) {
					System.out.println("S2SCall.java ::: secureServerCall() :: Response : " + line);
					respString.append(line);
				}

				br.close();
				System.out.println("S2SCall.java ::: secureServerCall() :: Response : " + respString.toString());
				return respString.toString();
			}
		} catch (Exception e) {
			System.out.println("S2SCall.java ::: secureServerCall() :: Error Occurred while Processing Request : ");

		}

		return null;
	}

	
}
