package com.crm.helper;

import java.util.Random;

import graphql.com.google.common.base.Supplier;

public class OTPgenrateHelper {
	

	    private final static Integer LENGTH = 6;

	    public static Supplier<String> createRandomOneTimePassword() {
	        return () -> {
	            Random random = new Random();
	            StringBuilder oneTimePassword = new StringBuilder();
	            for (int i = 0; i < LENGTH; i++) {
	                int randomNumber = random.nextInt(10);
	                
	                oneTimePassword.append(randomNumber);
	            }
	            String sOtp = String.valueOf(oneTimePassword);
	            return sOtp;
	        };
	    }
	
}
