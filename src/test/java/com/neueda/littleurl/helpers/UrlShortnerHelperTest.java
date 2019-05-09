package com.neueda.littleurl.helpers;

import org.junit.Assert;
import org.junit.Test;

public class UrlShortnerHelperTest {
	
	@Test
	public void sameLongUrlsShouldResultSameHash() {
		String longUrl = "www.neueda.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash1 = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);
		String hash2 = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);
		
		Assert.assertEquals(hash1, hash2);
	}
	
	@Test
	public void differentLongUrlsShouldResultDifferentHashes() {
		String longUrl1 = "www.neueda.com";
		String longUrl2 = "www.google.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash1 = UrlShortnerHelper.generateShortURL(longUrl1, startIndex, endIndex);
		String hash2 = UrlShortnerHelper.generateShortURL(longUrl2, startIndex, endIndex);
		
		Assert.assertNotEquals(hash1, hash2);
	}
	
	@Test
	public void hashSizeMustBeConsistentWithStartAndEndIndexes() {
		String longUrl = "www.neueda.com";
		int startIndex = 0;
		int endIndex = 5;
		
		String hash = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);
		
		Assert.assertEquals(endIndex - startIndex + 1, hash.length());
	}


}
