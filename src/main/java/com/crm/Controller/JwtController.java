package com.crm.Controller;

import java.io.File;
import java.io.InputStream;

import org.springframework.security.core.AuthenticationException;


import java.io.StringWriter;
import java.io.PrintWriter;
import com.crm.services.UserDetailsui;
import com.crm.ServiceDaoImpl.SecondCon; 

import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.UUID;
import java.nio.file.Paths;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.services.CommonService;
import com.crm.services.customUserDetailsService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.gson.JsonObject;

import io.jsonwebtoken.impl.DefaultClaims;

import com.crm.Repository.UserRepository;
import com.crm.model.*;

@RestController
/*
 * @CrossOrigin(origins = {"http://localhost:4200"})
 */


@CrossOrigin(origins = {"http://localhost:4200"})
public class JwtController {
	private static Logger log = LoggerFactory.getLogger(JwtController.class);

	@Autowired
	private com.crm.helper.JwtHelperUtil JwtHelperUtil;
	
	@Autowired
	private customUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManger;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private CommonService CommonService;
	@Autowired
	private UserRequest user2;
	
	
	
	@Autowired
	private UserRequest user1;
	
	
	@Autowired
	private com.crm.Repository.RmsRepo RmsRepo;
	
	@Value("${file.upload.location1}")
	private String fileUploadLocation;
	
	
	@CrossOrigin(origins = {"http://localhost:4200"})
	@PostMapping("/token")
	public ResponseEntity<?> generateToken(@RequestBody UserRequest userRequest,HttpServletRequest request) throws Exception
	{
		

		 
		  String ipAddress = request.getHeader("X-Forwarded-For");
		  SecondCon secondCon = new SecondCon();
		  log.info("::::::::IP Address::::::::::::::::::::::::::::::"+ipAddress);
		  
		  log.info("::::::::::::::::::::::::::::::::::::"+userRequest.getPassword());
		try {
			
			this.authenticationManger.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(),userRequest.getPassword()));
			
			//  secondCon.insertIntoSecondSchema(username, "Login", userRequest.getUsername(),ipAddress);
			//	
		}
		catch (AuthenticationException e3) {
	        // Handle authentication failure
	        System.out.println("Authentication failed: " + e3.getMessage());
	    }
		
//		catch(UsernameNotFoundException e2)
//		{
////			StringWriter sw = new StringWriter();
////			PrintWriter pw = new PrintWriter(sw);
////			e.printStackTrace(pw);
////			String stackTraceString = sw.toString();
//
//		
//		//	secondCon.insertIntoSecondSchema(username, "Change Merchant Status-Exception", stackTraceString,ipAddress);
//				
//			//  secondCon.insertIntoSecondSchema(username, "Login-Exception", userRequest.getUsername(),ipAddress);
//				
//			  secondCon.insertIntoSecondSchema(userRequest.getUsername(), "User login-Exception", userRequest.toString() , "Bad Credentials - User Not Found!",ipAddress);
//				
//			e2.printStackTrace();
//			throw new Exception("Bad Credentials");
//		}
		
		catch (IndexOutOfBoundsException e1) {
	        // Handle another specific exception
	        System.err.println("Caught SomeOtherException: " + e1.getMessage());
	        
	        secondCon.insertIntoSecondSchema(userRequest.getUsername(), "User login-Exception",e1.getMessage() , "Bad Credentials - User Not Found!",ipAddress);
			
			e1.printStackTrace();
	
	        // Additional handling for this specific exception
	    } 
		
		catch (Exception e) {
	        // Handle another specific exception
	        System.err.println("Caught SomeOtherException: " + e.getMessage());
	        
	        secondCon.insertIntoSecondSchema(userRequest.getUsername(), "User login-Exception",e.getMessage() , "Bad Credentials - User Not Found!",ipAddress);
			
			e.printStackTrace();
	
	        // Additional handling for this specific exception
	    } 
		
		
		
		UserDetails user=this.customUserDetailsService.loadUserByUsername(userRequest.getUsername());
		String token = this.JwtHelperUtil.generateToken(user);
		 RefreshToken refreshToken = new RefreshToken();
		 refreshToken= JwtHelperUtil.createRefreshToken(user.getUsername());
		System.out.println("JWT   "+refreshToken);
		TokenReq TokenReq = new TokenReq();
		TokenReq.setToken(token);
		
	//	SecondCon secondCon = new SecondCon();//mithi
	//	  UserDetailsui userDetailsui = new UserDetailsui();					
		//	String data = userDetailsui.getUserDetails();
		//	log.info(":::::::::token:::::::::::::::::::::::::::::"+data);
		//	JSONObject userDetails = new JSONObject(data);

			//log.info(":::::::token:::::::::::::::::::::::::::::::"+userDetails);
			//String username = userDetails.optString("Username");
			//log.info("::::token:::::username:::::::::::::::::::::::::::::"+username);

		//	String ipAddress = userRequest.optString("ipAddress");
		//	log.info("::::::::token::::::::::::::::::::::::::::::"+ipAddress);
			log.info(":::::::::::::::::::::::::::::::::::::userRequest:"+userRequest.toString());

			  secondCon.insertIntoSecondSchema(user.getUsername(), "User login", userRequest.toString() , "User Logged In",ipAddress);
		

		return ResponseEntity.ok(TokenReq);
	}
	@GetMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		
		String ipAddress = request.getHeader("X-Forwarded-For");
		
		  log.info("::::::::IP Address::::::::::::::::::::::::::::::"+ipAddress);
		  
		// From the HttpRequest get the claims
		//DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		//Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		//String token = JwtHelperUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("Refresh Token request received for user id :{}",username);
		
		UserDetails user=this.customUserDetailsService.loadUserByUsername(username);
		String token = this.JwtHelperUtil.generateToken(user);
		SecondCon secondCon = new SecondCon();
		 secondCon.insertIntoSecondSchema(username, "Refresh Token", token,token,ipAddress);
			
		
		return ResponseEntity.ok(new  TokenReq(token));
	}
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	
	  @CrossOrigin(origins ={"http://localhost:4200"})
	@GetMapping(value = "/GetDetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllMenuSubMenu(HttpServletRequest request) throws Exception {
		
		String RequestTokenHeader= request.getHeader("Authorization");
		 System.out.println("JwtAuthFilter.java::::::::::::::;Token is Validated"+RequestTokenHeader);
		 String Username=null;
		 String JwtToken=null;
		 if(RequestTokenHeader !=null &&RequestTokenHeader.startsWith("Bearer "))
		 {
			 JwtToken = RequestTokenHeader.substring(7);
		 }
		 List<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();

		 HashMap<String, String> map = new HashMap<>();
		  Username= JwtHelperUtil.extractUsername(JwtToken);
		 user1.setUsername(Username);
		 user2 = CommonService.userLoginDetails(user1);
		String menus = this.customUserDetailsService.getMenuHierarchy(user2.getROLEID());
			 JSONObject js1 = new JSONObject(user2);
			 JSONObject js2 = new JSONObject(menus);

			 js1.remove("password");
				/*
				 * JSONArray jss = new JSONArray(menus);
				 */			 js1.put("Menu", js2);
		return ResponseEntity.ok(js1.toMap());
	
		
	}
	  @CrossOrigin(origins ={"http://localhost:4200"})
		@GetMapping(value = "/GetDetailsApi", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> getMenusAccess(HttpServletRequest request) throws Exception {
			
		  
		  SecondCon secondCon = new SecondCon();
		  UserDetailsui userDetailsui = new UserDetailsui();
			
			String data = userDetailsui.getUserDetails();
			JSONObject userDetails = new JSONObject(data);

			String username = userDetails.optString("Username");

			String ipAddress = userDetails.optString("ipAddress");
			String RequestTokenHeader= request.getHeader("Authorization");
			 System.out.println("JwtAuthFilter.java::::::::::::::;Token is Validated"+RequestTokenHeader);
			 String Username=null;
			 String JwtToken=null;
			 JSONObject js1;
			 
			 
			 try {
			 if(RequestTokenHeader !=null &&RequestTokenHeader.startsWith("Bearer "))
			 {
				 JwtToken = RequestTokenHeader.substring(7);
			 }
			 List<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();

			 HashMap<String, String> map = new HashMap<>();
			  Username= JwtHelperUtil.extractUsername(JwtToken);
//			  System.out.print("RequestTokenHeader:::::::::: "+RequestTokenHeader);
//			  System.out.print("JwtToken:::::::::: "+JwtToken);
						
			  log.info("Username::::::::::::::::::: "+Username);
			 user1.setUsername(Username);
			 user2 = CommonService.userLoginDetails(user1);
			ArrayList menus = this.customUserDetailsService.GetMenusHiracy(user2.getROLEID());
			 Date date = new Date();
		      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		       String str = formatter.format(date);
		      System.out.print("Current date: "+str);
		      user2.setCurrentDate(str);
				  js1 = new JSONObject(user2);
				 JSONObject js2 = new JSONObject();
				 js1.remove("password");
				 
					/*
					 * JSONArray jss = new JSONArray(menus);
					 * 
					 * 
					 */	
				
				
				 js1.put("Menu", menus);
				 
				 
				  System.out.println("Username::::::::::::::"+Username);
				  boolean MerchantVerify = Username.startsWith("M00");
				  
				  
				  if(MerchantVerify == true) {
					  System.out.println("MerchantVerify::::true::::::::::"+MerchantVerify);
					   
					 
					  
					  String verificationLogo = UserRepository.findVerificationLogo(Username);
					  
					  
					  
					  System.out.println("verificationLogo::::::::::::::"+verificationLogo);
//					  // if logo not then
//					  if(verificationLogo == null || verificationLogo.equalsIgnoreCase("null") || verificationLogo.equalsIgnoreCase("")) {
//						  
//						  String defaultLogo = fileUploadLocation;
//						  System.out.println("defaultLogo:::::::::"+defaultLogo);
//						  
//						  String s1 = defaultLogo.substring(defaultLogo.lastIndexOf(".") + 1);
//						  System.out.println("s1:::::::"+s1);
//							s1.trim();
//							
//							Path path = Path.of(defaultLogo);
//							File file = new File(defaultLogo);
//							byte[] fileContent = Files.readAllBytes(file.toPath());
//							
//							js1.put("Data","data:image/"+s1+";base64,"+Base64.getEncoder().encodeToString(fileContent));
//						  
//					  }
					//Lalit, with relative path of Project
					// if logo not then
					  if(verificationLogo == null || verificationLogo.equalsIgnoreCase("null") || verificationLogo.equalsIgnoreCase("")) {
						  
						  try {
						        ClassPathResource resource = new ClassPathResource(fileUploadLocation);
						        InputStream inputStream = resource.getInputStream();

						        String defaultLogoExtension = fileUploadLocation.substring(fileUploadLocation.lastIndexOf(".") + 1).trim();
						        byte[] fileContent = inputStream.readAllBytes();

						        js1.put("Data", "data:image/" + defaultLogoExtension + ";base64," + Base64.getEncoder().encodeToString(fileContent));
						        inputStream.close();
						    } catch (Exception e) {
						        e.printStackTrace();
						    }		  
					  }
					  // logo yes
					  else 
					  {
						  
						  String s1 = verificationLogo.substring(verificationLogo.lastIndexOf(".") + 1);
						  System.out.println("s1:::::::"+s1);
							s1.trim();
							
							// Path path = Path.of(verificationLogo);
							// File file = new File(verificationLogo);
							// byte[] fileContent = Files.readAllBytes(file.toPath());

					     //src/main/resources/home/static/LogoFolder/default.png
                                             Path path = Paths.get(verificationLogo);
                                             byte[] fileContent = Files.readAllBytes(path);
							
							js1.put("Data","data:image/"+s1+";base64,"+Base64.getEncoder().encodeToString(fileContent));
					  }
					  
//					 
					 	
					  
				  }
				  
				 

					 secondCon.insertIntoSecondSchema(username, "GetDetailsApi", "Gets All Menu List for "+username, js1.toMap().toString(), ipAddress);
				
				  log.info("=========>>>>>...>>>>"+(js1.toMap().toString()));
				  
				  
						return ResponseEntity.ok(js1.toMap());
			 }catch(Exception e )
			 {
				 log.info("Exception e:::::::::::::::::: ",e);
				 
				 StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String stackTraceString = sw.toString();
								
					secondCon.insertIntoSecondSchema(username, "Get Dropdown-Exception", "Gets All Menu List for "+username, stackTraceString, ipAddress);
			
				 
				 
				 e.printStackTrace();
				 return new ResponseEntity<Object>("Unauthrized Acces", HttpStatus.UNAUTHORIZED);
			 }
		
			
		}
	  
	  @CrossOrigin(origins ={"http://localhost:4200"})
		@PostMapping(value = "/CheckPermission", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> CheckPermssion(@RequestBody String fields) throws Exception {
		boolean Status;
		  JSONObject js = new JSONObject(fields);
			String Role_Id=  js.getString("Role_id");
			String ButtonValue=  js.getString("Permission");
			int i = customUserDetailsService.CheckPermission(Role_Id, ButtonValue);
			if(i<=0)
			{
				Status=false;
			}else
			{
				Status=true;
			}
			JSONObject Staus= new JSONObject();
			Staus.put("Status", Status);
			
		  return ResponseEntity.ok(Staus.toMap());
		
		  
		  
		  
	  }
	  
}
	
	

