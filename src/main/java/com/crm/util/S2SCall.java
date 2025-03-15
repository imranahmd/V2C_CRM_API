package com.crm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class S2SCall {
	static Logger logger= LoggerFactory.getLogger(S2SCall.class);

	
	
	public static String ServerToServerCallINDUSS(String sURL, String data) {
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;
		String ClentId="I3ZY102cb110dea1407e9dad6c54b037cf3bHXfZ";
		String ClientSecretKey="3inS4IXr9S1EqHD8raMAGKpDBWubMxxsddLY5H27";
		logger.info("S2SCall.java ::: secureServerCall() :: Posting URL : " + sURL);
		String auth = ClentId+ ":" + ClientSecretKey;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);
		try
		{
			logger.info("INDUSSPAY::::::::::::::: "+authHeaderValue);
			URL obj = new URL(sURL);
			HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();



			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json");
			con.addRequestProperty("Authorization", authHeaderValue);

			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

			wr.write(data);
			wr.flush();

			respString = new StringBuffer();

			logger.info("S2SCall.java ::: secureServerCall() :: "+con.getResponseCode());
			logger.info("S2SCall.java ::: secureServerCall()res :: "+con.getResponseMessage());

			if (con.getResponseCode() == 200)
			{
				logger.info("S2SCall.java ::: secureServerCall() :: HTTP OK");
				br = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null)
				{
					logger.info("S2SCall.java ::: secureServerCall() :: Response : " + line);
					respString.append(line);
				}

				br.close();

				return respString.toString().trim();
			}
		}
		catch (Exception e)
		{
			logger.error("S2SCall.java ::: secureServerCall() :: Error Occurred while Processing Request : ", e);
		}


		return null;
	}
	
	public static String ServerToServerCallMerchant(String sURL, String data) {
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;

		try
		{
			logger.info("INDUSSPAY::::::::::::::payput");
			URL obj = new URL(sURL);
			HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();



			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json");

			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

			wr.write(data);
			wr.flush();

			respString = new StringBuffer();

			logger.info("S2SCall.java ::: secureServerCall() :: "+con.getResponseCode());
			logger.info("S2SCall.java ::: secureServerCall()res :: "+con.getResponseMessage());

			if (con.getResponseCode() == 200)
			{
				logger.info("S2SCall.java ::: secureServerCall() :: HTTP OK");
				br = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null)
				{
					logger.info("S2SCall.java ::: secureServerCall() :: Response : " + line);
					respString.append(line);
				}

				br.close();

				return respString.toString().trim();
			}
		}
		catch (Exception e)
		{
			logger.error("S2SCall.java ::: secureServerCall() :: Error Occurred while Processing Request : ", e);
		}


		return null;
	}
	
//	public static String restapcall(String mainrequest, String RBL_SALE_URL,String Key,String Path,String clientId,String clientSecret) {
//
//		try {
//			FileInputStream pfxFile = new FileInputStream(Path);
//	
//			logger.info(Key);
//			String Key1="Basic "+Key;
//			KeyStore keyStore = KeyStore.getInstance("PKCS12");
//			char[] password = "password".toCharArray();
//			keyStore.load(pfxFile, password);
//
//			SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore, password)
//					.loadTrustMaterial((TrustStrategy) (chain, authType) -> true).build();
//
//			CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContext).build();
//
//			logger.info("saleURl for  request for Rbl " + RBL_SALE_URL);
//			// Set request parameters in the URL
//            String urlWithParams = RBL_SALE_URL + "?client_id=" + clientId + "&client_secret=" + clientSecret;
//			logger.info("S2S :: urlWithParams :: "+urlWithParams);
//            HttpUriRequest request = new HttpPost(urlWithParams);
//			String jsonPayload = mainrequest;
//			StringEntity entity = new StringEntity(jsonPayload);
//			((HttpPost) request).setEntity(entity);
//			request.addHeader("Authorization", Key1);
//			request.setHeader("Content-Type", "application/json");
//
//			HttpResponse response = httpClient.execute(request);
//
//			HttpEntity responseEntity = response.getEntity();
//			String responseBody = EntityUtils.toString(responseEntity);
//
//			logger.info("Response: " + responseBody);
//
//			httpClient.close();
//			return responseBody;
//
//		} catch (Exception e) {
//
//			logger.error("error for cummication for rbl payout", e);
//		}
//		return null;
//
//	}
//	
	
	public static String ServerToServerCallProcess(String sURL, String data,String authkey) {
		
		logger.info("url::"+sURL+"::::::::::::data::"+data+":::::::::::");
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;
		String ClentId="I3ZY102cb110dea14dddad6c54b037cf3bHXfZ";
		String ClientSecretKey="3inS4IXr9S1EqHD8raMAGKpDB00bMxxsddLY5H27";
		logger.info("S2SCall.java ::: secureServerCall() :: Posting URL : " + sURL);
		String auth = ClentId+ ":" + ClientSecretKey;
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
	String authHeaderValue = "Bearer " + new String(authkey);
		
				try
		{
			//logger.info("::::::::::::::: "+authHeaderValue);
			URL obj = new URL(sURL);
			HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();



			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "application/json");
			con.addRequestProperty("Authorization", authHeaderValue);

			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

			wr.write(data);
			wr.flush();

			respString = new StringBuffer();

			logger.info("S2SCall.java ::: secureServerCall() :: "+con.getResponseCode());
			logger.info("S2SCall.java ::: secureServerCall()res :: "+con.getResponseMessage());

			if (con.getResponseCode() == 200)
			{
				logger.info("S2SCall.java ::: secureServerCall() :: HTTP OK");
				br = new BufferedReader(
						new InputStreamReader(con.getInputStream()));

				while ((line = br.readLine()) != null)
				{
					logger.info("S2SCall.java ::: secureServerCall() :: Response : " + line);
					respString.append(line);
				}

				br.close();

				return respString.toString().trim();
			}
		}
		catch (Exception e)
		{
			logger.error("S2SCall.java ::: secureServerCall() :: Error Occurred while Processing Request : ", e);
		}


		return null;
	}
	

}