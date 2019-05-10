package com.neueda.littleurl.services;

import static org.junit.Assert.fail;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

public class UrlServiceTest {

	@InjectMocks
	private UrlService service;

	@Mock
	private UrlRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenCodeIsNullThrowsIllegalArgumentException() {
		// Given
		String code = null;
		
		// When
		service.find(code);
	}

	@Test
	public void whenCodeExistsReturnsUrl() {
		// Given
		String existingCode = "3077yW";

		Url existingUrl = new Url(existingCode, "www.neueda.com");
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		// When
		Url url = service.find(existingCode);

		// Then
		Assert.assertEquals(existingUrl, url);
	}

	@Test(expected = UrlNotFoundException.class)
	public void whenCodeDoesNotExistThrowsUrlNotFoundException() {
		// Given
		String notExistingCode = "2YpwKFJ";

		Mockito.when(repository.findById(notExistingCode))
				.thenThrow(new UrlNotFoundException(UrlService.URL_NOT_FOUND_FOR_CODE + notExistingCode));

		// When
		service.find(notExistingCode);
	}

	@Test
	public void whenUrlIsNullThrowsIllegalArgumentException() {
		// Given
		Url url = null;
		
		try {
			// When
			service.findOrCreate(url);
			fail();
		} catch(IllegalArgumentException e) {
			// Then
			Assert.assertThat(e.getMessage(), Matchers.is(UrlService.URL_MUST_NOT_BE_NULL));
		}		
	}

	@Test
	public void whenLongUrlIsNullThrowsIllegalArgumentException() {
		// Given
		String code = null;
		String longUrl = null;

		Url url = new Url(code, longUrl);

		try {
			// When
			service.findOrCreate(url);	
			fail();
		} catch(IllegalArgumentException e) {
			// Then
			Assert.assertThat(e.getMessage(), Matchers.is(UrlService.LONG_URL_MUST_NOT_BE_NULL));
		}
	}

	@Test
	public void whenLongUrlDoesNotExistSaveItAndReturnNewUrlCode() {
		// Given
		String code = null;
		String notExistingLongUrl = "www.neueda.com";
		Url urlToCreate = new Url(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + Url.URL_CODE_SIZE - 1;
		String notExistingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

		Mockito.when(repository.findById(notExistingCode))
				.thenThrow(new UrlNotFoundException(UrlService.URL_NOT_FOUND_FOR_CODE + notExistingCode));

		Url url = new Url(notExistingCode, notExistingLongUrl);
		Mockito.when(repository.save(url)).thenReturn(url);

		// When
		Url newUrl = service.findOrCreate(urlToCreate);

		// Then
		Assert.assertEquals(url, newUrl);
	}
	
	@Test
	public void whenLongUrlExistsFindItAndReturnExistingUrlCode() {
		// Given
		String code = null;
		String existingLongUrl = "www.neueda.com";
		Url urlToCreate = new Url(code, existingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + Url.URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(existingLongUrl, startIndex, endIndex);		
		
		Url existingUrl = new Url(existingCode, existingLongUrl);
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		// When
		Url newUrl = service.findOrCreate(urlToCreate);

		// Then
		Assert.assertEquals(existingUrl, newUrl);
	}
	
	@Test
	public void whenLongUrlDoesNotExistButGeneratedCodeExistsThenSaveANewCodeAndReturnIt() {
		// Given
		String code = null;
		String notExistingLongUrl = "www.neueda.com";
		Url urlToCreate = new Url(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + Url.URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);		
		
		Url existingUrl = new Url(existingCode, "www.google.com");
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		startIndex = startIndex + 1;
		endIndex = endIndex + 1;
		String notExistingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);		

		Mockito.when(repository.findById(notExistingCode))
		.thenThrow(new UrlNotFoundException(UrlService.URL_NOT_FOUND_FOR_CODE + notExistingCode));
		
		Url url = new Url(notExistingCode, notExistingLongUrl);
		Mockito.when(repository.save(url)).thenReturn(url);

		// When
		Url newUrl = service.findOrCreate(urlToCreate);

		// Then
		Assert.assertEquals(url, newUrl);
	}

}
