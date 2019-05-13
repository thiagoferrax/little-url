package com.neueda.littleurl.resources;

import static com.neueda.littleurl.util.Constants.URL_CODE_SIZE;
import static com.neueda.littleurl.util.Constants.URL_NOT_FOUND_FOR_CODE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.services.UrlService;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UrlResourcesTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UrlService urlService;

	@Test
	public void whenUrlCodeExistsReturnsExistingUrl() throws Exception {
		// Given
		String existingCode = "3077yW";

		Url url = new Url(existingCode, "www.neueda.com");

		given(urlService.find(existingCode)).willReturn(url);

		// When and Then
		this.mockMvc.perform(get("/urls/" + existingCode + "/longUrl")).andExpect(status().isOk())
				.andExpect(content().json("{'code': '3077yW','longUrl': 'www.neueda.com'}"));
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

		Url url = new Url(existingCode, "www.neueda.com");

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
	@Ignore
	public void whenLongUrlDoesNotExistSaveItAndReturnNewUrlCode() {
		// Given
		String code = null;
		String notExistingLongUrl = "www.google.com";
		Url urlToCreate = new Url(code, notExistingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String notExistingCode = UrlShortnerHelper.generateShortURL(notExistingLongUrl, startIndex, endIndex);

	}
	
	
	@Test
	@Ignore
	public void whenLongUrlExistsThenReturnExistingCode() {
		// Given
		String code = null;
		String existingLongUrl = "www.neueda.com";
		Url urlToFind = new Url(code, existingLongUrl);

		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String existingCode = UrlShortnerHelper.generateShortURL(existingLongUrl, startIndex, endIndex);		
	}
}
