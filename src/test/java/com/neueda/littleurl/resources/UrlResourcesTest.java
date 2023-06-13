package com.thiagoferraz.littleurl.resources;

import static com.thiagoferraz.littleurl.util.Constants.URL_CODE_SIZE;
import static com.thiagoferraz.littleurl.util.Constants.URL_NOT_FOUND_FOR_CODE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.thiagoferraz.littleurl.domain.Url;
import com.thiagoferraz.littleurl.dto.UrlDTO;
import com.thiagoferraz.littleurl.helpers.UrlShortnerHelper;
import com.thiagoferraz.littleurl.services.StatisticService;
import com.thiagoferraz.littleurl.services.UrlService;
import com.thiagoferraz.littleurl.services.exceptions.UrlNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UrlResourcesTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StatisticService statisticService;
	
	@MockBean
	private UrlService urlService;

	@Test
	public void whenUrlCodeExistsReturnsExistingUrl() throws Exception {
		// Given
		String existingCode = "3077yW";

		Url url = new Url(existingCode, "http://www.thiagoferraz.com");

		given(urlService.find(existingCode)).willReturn(url);

		// When and Then
		this.mockMvc.perform(get("/urls/" + existingCode + "/longUrl")).andExpect(status().isOk())
				.andExpect(content().json("{'code': '3077yW','longUrl': 'http://www.thiagoferraz.com'}"));
	}

	@Test
	public void whenUrlCodeDoesNotExistReturnsNotFound() throws Exception {
		// Given
		String notExistingCode = "2YpwKFJ";

		given(urlService.find(notExistingCode))
				.willThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

		// When and Then
		this.mockMvc.perform(get("/urls/" + notExistingCode + "/longUrl")).andExpect(status().isNotFound());
	}

	@Test
	public void whenUrlCodeExistsItRedirectsToExistingUrl() throws Exception {
		// Given
		String existingCode = "3077yW";

		Url url = new Url(existingCode, "http://www.thiagoferraz.com");

		given(urlService.find(existingCode)).willReturn(url);

		// When and Then
		this.mockMvc.perform(get("/urls/" + existingCode)).andExpect(status().is3xxRedirection())
				.andExpect(header().string(HttpHeaders.LOCATION, equalTo(url.getLongUrl())));

	}

	@Test
	public void whenUrlCodeDoesNotExistItDoesNotRedirectAndReturnsNotFound() throws Exception {
		// Given
		String notExistingCode = "2YpwKFJ";

		given(urlService.find(notExistingCode))
				.willThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

		// When and Then
		this.mockMvc.perform(get("/urls/" + notExistingCode)).andExpect(status().isNotFound());
	}

	@Test
	public void whenLongUrlDoesNotExistItDoesNotRedirectAndReturnsNotFound() throws Exception {
		// Given
		String notExistingCode = "2YpwKFJ";

		given(urlService.find(notExistingCode))
				.willThrow(new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + notExistingCode));

		// When and Then
		this.mockMvc.perform(get("/urls/" + notExistingCode)).andExpect(status().isNotFound());
	}

	@Test
	public void whenLongUrlDoesNotExistSaveItAndReturnNewUrlCode() throws Exception {
		// Given
		String code = null;
		String notExistingLongUrl = "http://www.google.com";
		UrlDTO urlDtoToCreate = new UrlDTO(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String newCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

		Url urlToCreate = new Url(code, notExistingLongUrl);
		given(urlService.fromDTO(urlDtoToCreate)).willReturn(urlToCreate);

		Url newUrl = new Url(newCode, notExistingLongUrl);
		given(urlService.findOrCreate(urlToCreate)).willReturn(newUrl);

		String inputJson = "{\"longUrl\":\"http://www.google.com\"}";

		// When and Then
		this.mockMvc.perform(post("/urls/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/urls/" + newCode));

	}

	@Test
	public void whenLongUrlExistsThenReturnExistingCode() throws Exception {
		// Given
		String code = null;
		String existingLongUrl = "http://www.thiagoferraz.com";
		UrlDTO urlDtoToFind = new UrlDTO(code, existingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(existingLongUrl, startIndex, endIndex);

		Url urlToFind = new Url(code, existingLongUrl);
		given(urlService.fromDTO(urlDtoToFind)).willReturn(urlToFind);

		Url existingUrl = new Url(existingCode, existingLongUrl);
		given(urlService.findOrCreate(urlToFind)).willReturn(existingUrl);

		String inputJson = "{\"longUrl\":\"http://www.thiagoferraz.com\"}";

		// When and Then
		this.mockMvc.perform(post("/urls/").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andExpect(status().isCreated())
				.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/urls/" + existingCode));

	}

	@Test
	public void whenUpdatingAnUrlReturnsStatusNoContent() throws Exception {
		// Given
		String updatingLongUrl = "http://www.thiagoferraz.com/";
		String existingCode = "3077yW";

		UrlDTO urlDtoToFind = new UrlDTO(existingCode, updatingLongUrl);

		Url urlToFind = new Url(existingCode, updatingLongUrl);
		given(urlService.fromDTO(urlDtoToFind)).willReturn(urlToFind);

		String inputJson = "{\"code\":\"3077yW\", \"longUrl\":\"http://www.thiagoferraz.com\"}";

		// When and Then
		this.mockMvc
				.perform(put("/urls/" + existingCode).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andExpect(status().isNoContent());
	}

	@Test
	public void whenRemovingAnUrlReturnsStatusNoContent() throws Exception {
		// Given
		String existingCode = "3077yW";

		// When and Then
		this.mockMvc.perform(delete("/urls/" + existingCode)).andExpect(status().isNoContent());
	}
}