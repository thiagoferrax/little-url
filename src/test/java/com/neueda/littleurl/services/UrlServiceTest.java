package com.neueda.littleurl.services;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neueda.littleurl.domains.Url;
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
		String code = null;
		service.find(code);
	}

	@Test
	public void whenCodeExistsReturnsUrl() {
		String existingCode = "3077yW";

		Url existingUrl = new Url(existingCode, "www.neueda.com");
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		Url url = service.find(existingCode);

		Assert.assertEquals(existingUrl, url);
	}

	@Test(expected = UrlNotFoundException.class)
	public void whenCodeDoesNotExistsThrowsUrlNotFoundException() {
		String notExistingCode = "2YpwKFJ";

		Mockito.when(repository.findById(notExistingCode))
				.thenThrow(new UrlNotFoundException(UrlService.URL_NOT_FOUND_FOR_CODE + notExistingCode));

		service.find(notExistingCode);
	}

}
