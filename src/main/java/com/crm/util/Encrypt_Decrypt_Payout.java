package com.crm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Encrypt_Decrypt_Payout {
	private static Logger log = LoggerFactory.getLogger(Encrypt_Decrypt_Payout.class);

	public static String encrypt(String key, String initVector, String value)
	  {
	    try
	    {
	    
	      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	      
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	      cipher.init(1, skeySpec, iv);
	      
	      byte[] encrypted = cipher.doFinal(value.getBytes());
	      System.out.println("encrypted string: " + 
	        java.util.Base64.getEncoder().encodeToString(encrypted));
	      
	      return java.util.Base64.getEncoder().encodeToString(encrypted);

	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    
	    return null;
	  }
	  
	  public static String decrypt(String key, String initVector, String encrypted)
	  {
	    try
	    {
	   
	      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	      
	      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	      cipher.init(2, skeySpec, iv);
	      
	      byte[] original = cipher.doFinal(java.util.Base64.getDecoder().decode(encrypted));
	      return new String(original);

	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    
	    return null;
	  }

	public static String sendPostRequest(String requestUrl, String jsonInputString) throws IOException {

		URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        log.info("Response Code: " + responseCode);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
	}
	}

}
