package com.crm.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class GeneralUtil {
	
	public static String crypt(String value) {
		String key = "Bar12345Bar12345"; // 128 bit key
		String initVector = "RandomInitVector"; // 16 bytes IV
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			//log.info("encrypted string: " + Base64.encodeBase64String(encrypted));
			

			
			  System.out.println("" + Base64.encodeBase64String(encrypted));
			  return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			//log.error("GeneralDaoImpl.java ::: ::: Getting error :: ", ex);
			ex.printStackTrace();
		}

		return null;
	}
	
	public static String decrypt2(String key, String initVector, String encrypted)
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

	public static String decrypt(String encrypted) {

		String key = "Bar12345Bar12345"; // 128 bit key
		String initVector = "RandomInitVector"; // 16 bytes IV
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			//log.error("GeneralDaoImpl.java ::: ::: Getting error :: ", ex);
			ex.printStackTrace();
		}

		return null;
	}
	
	public static String encrypt(String key, String initVector, String value) {
		try {
			System.out.println(initVector.getBytes().length);

			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(1, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			System.out.println("encrypted string: " + java.util.Base64.getEncoder().encodeToString(encrypted));

			return java.util.Base64.getEncoder().encodeToString(encrypted);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	public static String getEncData(String sData, String encKey) {
//		log.info("inside getEncData method " + encKey.length());
		try {

			if (encKey.length() == 8) {
				encKey = encKey + encKey;
			}
			String ALGO = "AES";
			byte[] keyByte = encKey.getBytes();
			Key key = new SecretKeySpec(keyByte, ALGO);

			Cipher c = Cipher.getInstance(ALGO);
			c.init(1, key);
			byte[] encVal = c.doFinal(sData.getBytes());
			byte[] encryptedByteValue = java.util.Base64.getEncoder().encode(encVal);
			return new String(encryptedByteValue);

		} catch (Exception e) {
			System.out.printf("PGUtils.java ::: getDecData() :: Error occurred while Encryption : ", e);
		}

		return null;
	}
	
	public String isUserMatching(String authString){
		String Statusapp = null;
        if (authString.equals("4ae7df22936b7be31fd9ca5d99c6614a")) {
        	Statusapp= "Admin";
        }else if(authString.equals("af9d1620e1bd350400ca30a793782d21")) {
        	Statusapp= "Merchant";
        }else if(authString.equals("b2b799c9e6a4557990afc253ec54e521")) {
        	Statusapp= "Aggregator";
        }
            
         
        return Statusapp;
    }
	
}
