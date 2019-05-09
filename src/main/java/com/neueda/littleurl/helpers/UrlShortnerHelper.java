package com.neueda.littleurl.helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UrlShortnerHelper {

	private static final int RADIX = 16;
	private static final int HASH_LENGTH = 32;
	private static final String MD5_ALGORITHM = "MD5";

	public static String generateShortURL(String longUrl, int startIndex, int endIndex) {
		String md5 = getMd5(longUrl);
		String base64 = getBase64(md5);
		String hash = base64.replace('/', '_').replace('+', '-');
		
		return hash.substring(startIndex, endIndex + 1);
	}

	private static String getBase64(String md5) {
		return Base64.getEncoder().encodeToString(md5.getBytes());
	}

	private static String getMd5(String longUrl) {
		try {
			byte[] message = MessageDigest.getInstance(MD5_ALGORITHM).digest(longUrl.getBytes());

			String hash = new BigInteger(1, message).toString(RADIX);
			hash = hash.length() < HASH_LENGTH ? "0".concat(hash) : hash;
			
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
}
