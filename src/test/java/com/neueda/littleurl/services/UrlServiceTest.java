package com.neueda.littleurl.services;

import static com.neueda.littleurl.util.Constants.URL_CODE_SIZE;
import static com.neueda.littleurl.util.Constants.URL_NOT_FOUND_FOR_CODE;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.dto.UrlUpdateDTO;
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

	@Test
	public void whenCodeExistsReturnsUrl() {
		// Given
		String existingCode = "3077yW";

		Url existingUrl = new Url(existingCode, "http://www.neueda.com");
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
				.thenThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

		// When
		service.find(notExistingCode);
	}

	@Test
	public void whenLongUrlDoesNotExistSaveItAndReturnNewUrlCode() {
		// Given
		String code = null;
		String notExistingLongUrl = "http://www.neueda.com";
		Url urlToCreate = new Url(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String notExistingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

		Mockito.when(repository.findById(notExistingCode))
				.thenThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

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
		String existingLongUrl = "http://www.neueda.com";
		Url urlToCreate = new Url(code, existingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(existingLongUrl, startIndex, endIndex);

		Url existingUrl = new Url(existingCode, existingLongUrl);
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		// When
		Url newUrl = service.findOrCreate(urlToCreate);

		// Then
		assertEquals(existingUrl, newUrl);
	}

	@Test
	public void whenLongUrlDoesNotExistButGeneratedCodeExistsThenSaveANewCodeAndReturnIt() {
		// Given
		String code = null;
		String notExistingLongUrl = "http://www.google.com";
		Url urlToCreate = new Url(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

		Url existingUrl = new Url(existingCode, "http://www.neueda.com");
		Optional<Url> optional = Optional.of(existingUrl);
		Mockito.when(repository.findById(existingCode)).thenReturn(optional);

		startIndex = startIndex + 1;
		endIndex = endIndex + 1;
		String notExistingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

		Mockito.when(repository.findById(notExistingCode))
				.thenThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

		Url url = new Url(notExistingCode, notExistingLongUrl);
		Mockito.when(repository.save(url)).thenReturn(url);

		// When
		Url newUrl = service.findOrCreate(urlToCreate);

		// Then
		Assert.assertEquals(url, newUrl);
	}

	@Test
	public void whenUpdatinAnUrlVerifyThatRepositorySaveIsCalled() {
		// Given
		String existingCode = "3077yW";
		Url existingUrl = new Url(existingCode, "http://www.neueda.com");

		// When
		service.update(existingUrl);

		// Then
		Mockito.verify(repository).save(existingUrl);
	}
	
	@Test
	public void whenDeletingAnUrlVerifyThatRepositoryDeleteByIdIsCalled() {
		// Given
		String existingCode = "3077yW";

		// When
		service.remove(existingCode);

		// Then
		Mockito.verify(repository).deleteById(existingCode);
	}
	
	@Test
	public void whenBuildingUrlFromDtoReturnAnUrlWithSameCodeAndLongUrl() {
		// Given
		String existingCode = "3077yW";
		String existingLongUrl = "http://www.neueda.com";

		// When
		UrlDTO urlDto = new UrlDTO(existingCode, existingLongUrl);
		Url url = service.fromDTO(urlDto);

		// Then
		Url expectedUrl = new Url(existingCode, existingLongUrl);
		assertEquals(expectedUrl, url);
	}
	
	@Test
	public void whenBuildingUrlFromUpdateDtoReturnAnUrlWithSameCodeAndLongUrl() {
		// Given
		String existingCode = "3077yW";
		String existingLongUrl = "http://www.neueda.com";

		// When
		UrlUpdateDTO urlDto = new UrlUpdateDTO(existingCode, existingLongUrl);
		Url url = service.fromUpdateDTO(urlDto);

		// Then
		Url expectedUrl = new Url(existingCode, existingLongUrl);
		assertEquals(expectedUrl, url);
	}
}
