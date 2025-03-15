package com.external.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BitlyAPI 
{
	
//	private static final String POST_URL = "https://api-ssl.bitly.com/v3/shorten?";
	private static final String POST_URL = "https://api-ssl.bitly.com/v4/shorten";// Version change v3 to v4

//	private static final String BITLY_TOKEN = "a810bde1b391177a2f57b04c50485c42a1b5c4bc";
	private static final String BITLY_TOKEN = "Bearer 8a156f25d99dad10c858fd2309f0a5d0e92e6803";// token change and add bearer
	
	private static final Logger log = LoggerFactory.getLogger(BitlyAPI.class);

	public String getLongurl() {
		return longurl;
	}

	public void setLongurl(String longurl) {
		this.longurl = longurl;
	}

	private String longurl;

	public static void main(String[] args) 
	{
		/*// TODO Auto-generated method stub
		BitlyClient client = new BitlyClient("a810bde1b391177a2f57b04c50485c42a1b5c4bc");
		Response<ShortenResponse> resp = client.shorten()
		                          .setLongUrl("https://github.com/stackmagic/bitly-api-client")
		                          .call();
		
		String string = resp.toString();
		log.info("string"+string);*/
		
		BitlyAPI bitlyAPIObject = new BitlyAPI();
		bitlyAPIObject.getShortenUrl();
		
		
		                          
	}
	
	//create method for the data 
	private static String getRequestData(String longUrl)
    {
        return "{"
                + "  \"long_url\": \"" + longUrl + "\","
                + "  \"domain\": \"bit.ly\","
                + "  \"group_guid\": \"Bmbp46jdjNT\""
                + "}"; 
    }
	public String getShortenUrl() 
	{
		String shortUrl = null;
		
		try 
		{
			
//			log.info("inside the try::::::::");
			OutputStreamWriter wr = null;
			BufferedReader br = null;
			
//			log.info("this.longurl:::::"+this.longurl);


			URL url = new URL(POST_URL);
			
			HttpURLConnection con = (HttpURLConnection) url
					.openConnection();
			
			con.setConnectTimeout(3000);
			con.setReadTimeout(40*1000);
			
			String contentType = "application/json";
			String acceptType = "application/json";
			
			con.setRequestMethod("POST");
			con.addRequestProperty("Content-Type",contentType);
			con.addRequestProperty("Accept", acceptType);
			con.addRequestProperty("Authorization", BITLY_TOKEN);
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
            
            wr = new OutputStreamWriter(con.getOutputStream());
        
            wr.write(getRequestData(this.longurl));
//			log.info("getRequestData(this.longurl)::::::::"+getRequestData(this.longurl));

            wr.flush();
            
            try {
                if(wr != null) wr.close(); // io exception
            }catch(Exception ex) {
                ex.printStackTrace();
            }
//            log.info("url4::::::::");
            log.info("Response received  with code : " + con.getResponseCode() );
    
            StringBuilder response = new StringBuilder();
            String line = null;

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK || con.getResponseCode() == 201) { //io exception
                br = new BufferedReader(new InputStreamReader(con.getInputStream())); //io exception
                while((line = br.readLine()) != null) { //io exception
                    response.append(line);
                }
                log.info("200 status response data received :" + response.toString());
                JSONObject jsonResponse = new JSONObject(response.toString());
                String shortenUrl = jsonResponse.getString("link");
                shortUrl = shortenUrl;
                log.info("shortUrl::::::"+shortUrl);

                //parst and set short url here
            }
            else{
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                while((line = br.readLine()) != null) { //io exception
                    response.append(line);
                }
                log.info("Http error status code with response data : " + response.toString());
            }
		} 
		catch (Exception ex) 
		{
//			log.info("inside the catch::::::::");
			ex.printStackTrace();
		}
		
		return shortUrl;
	}
	


}
