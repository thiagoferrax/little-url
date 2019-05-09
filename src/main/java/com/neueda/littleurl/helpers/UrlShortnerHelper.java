package com.neueda.littleurl.helpers;

public class UrlShortnerHelper {

	public static String generateShortURL(String longUrl, int startIndex, int endIndex) {
		String hash = "abcdefghijlmnopqrstuvxz";
		return hash.substring(startIndex, endIndex+1);
	}
	
}
