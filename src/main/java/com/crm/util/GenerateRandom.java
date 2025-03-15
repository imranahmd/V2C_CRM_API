package com.crm.util;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateRandom {
	static Logger log = LoggerFactory.getLogger(GenerateRandom.class);
	private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC_STRING = "0123456789";

	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		int count1 = count;
		while (count-- != 0) {
			int strlen = (int) (ThreadLocalRandom.current().nextDouble() * ALPHA_STRING.length());
			builder.append(ALPHA_STRING.charAt(strlen));
			if (count % 2 == 0) {
				int numlen = (int) (ThreadLocalRandom.current().nextDouble() * NUMERIC_STRING.length());
				builder.append(NUMERIC_STRING.charAt(numlen));
			}
		}
		return builder.toString().substring(0, count1);
	}

	public static String randomAlpha(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (ThreadLocalRandom.current().nextDouble() * ALPHA_STRING.length());
			builder.append(ALPHA_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static String randomNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (ThreadLocalRandom.current().nextDouble() * NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randomAlphaNumericIBPS(int count) throws NoSuchAlgorithmException, NoSuchProviderException {
		StringBuilder builder = new StringBuilder();
		int count1 = count;
		SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
		int maxAlpha = ALPHA_STRING.length();
		int maxNum = NUMERIC_STRING.length();
		int min = 1;
		while (count-- != 0) {
			int randomAlpha = secureRandomGenerator.nextInt(maxAlpha-min+1) % maxAlpha;
			int randomNum = secureRandomGenerator.nextInt(maxNum-min+1)+ min;
			builder.append(ALPHA_STRING.charAt(randomAlpha));
			if (count % 2 == 0) {
				builder.append(randomNum);
			}
		}
		return builder.toString().substring(0, count1);
 }
}
