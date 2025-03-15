package com.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenReq {

    String Token="";
    String RefreshToken="";
	
    
    
	public TokenReq() {
		// TODO Auto-generated constructor stub
	}
	public TokenReq(String token) {
		super();
		Token = token;
	}
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		Token = token;
	}
	public String getRefreshToken() {
		return RefreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		RefreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "TokenReq [Token=" + Token + ", RefreshToken=" + RefreshToken + "]";
	}
    
    
	
	
}
