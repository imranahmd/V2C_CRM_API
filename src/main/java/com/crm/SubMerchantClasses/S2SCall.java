package com.crm.SubMerchantClasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S2SCall
{
    private static Logger logger = LoggerFactory.getLogger(S2SCall.class);

	static
	{
		disableSslVerification();
	}

	public S2SCall() {}



	public static String secureServerCall1(String sURL, String data) {
		String line = null;
		BufferedReader br = null;
		StringBuffer respString = null;

		logger.info("S2SCall.java ::: secureServerCall() :: Posting URL : " + sURL);
		logger.info("S2SCall.java ::: secureServerCall() :: data : " + data);
		try
		{
			URL obj = new URL(sURL);
			HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();



			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type", "text/plain; charset=UTF-8");
			//con.addRequestProperty("Content-Length", data.getBytes().length+"");
			con.setDoOutput(true);
			con.connect();

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

			wr.write(data);
			wr.flush();

			respString = new StringBuffer();

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



	private static void disableSslVerification()
	{
		try
		{
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[]
					{
							new X509TrustManager()
							{
								public X509Certificate[] getAcceptedIssuers() 
								{
									return null;
								}
								public void checkClientTrusted(X509Certificate[] certs, String authType)
								{
								}
								public void checkServerTrusted(X509Certificate[] certs, String authType)
								{
								}
							}
					};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier()
			{
				public boolean verify(String hostname, SSLSession session)
				{
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		}
		catch (Exception e) 
		{

			logger.error("S2SCall.java ::: disableSslVerification() :: Error Occurred while disabling SSL Verification : ",e);
		} 
	}



	
}
