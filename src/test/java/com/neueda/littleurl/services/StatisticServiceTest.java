package com.neueda.littleurl.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.repositories.StatisticRepository;

/**
 * @author thiago
 *
 */
public class StatisticServiceTest {

	@InjectMocks
	private StatisticService service;

	@Mock
	private StatisticRepository repository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void whenCreatingAStatisticVerifyThatRepositorySaveIsCalled() {

		// Given
		Url url = new Url("3077yW", "http://www.neueda.com");
		Statistic statistic = new Statistic("Firefox 7", "Computer", "Windows XP", url);
		
		// When
		service.create(statistic);

		// Then
		Mockito.verify(repository).save(statistic);
	}
	
	@Test
	public void whenMappingFromHeadersAndStatisticReturnsStatistic() {
		// Given
		Url url = new Url("3077yW", "http://www.neueda.com");
		
		Map<String, String> headers = new HashMap<>();
		headers.put(HttpHeaders.USER_AGENT.toLowerCase(), "Mozilla/5.0 (Windows NT 5.1; rv:7.0.1) Gecko/20100101 Firefox/7.0.1");
		
		//When
		Statistic statistic = service.mapFrom(headers, url);
		
		//Then
		Assert.assertEquals("Firefox 7", statistic.getBrowser());
		Assert.assertEquals("Computer", statistic.getDeviceType());
		Assert.assertEquals("Windows XP", statistic.getOperatingSystem());
		Assert.assertEquals(url, statistic.getUrl());
	}

}
