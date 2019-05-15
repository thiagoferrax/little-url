package com.neueda.littleurl.services;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.repositories.StatisticRepository;

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
	public void whenMappingFromHeadersAndStatisticReturnsStatistic() {
		// Given
		Url url = new Url("3077yW", "http://www.neueda.com");
		
		Map<String, String> headers = new HashMap<>();
		
		String referer = "http://www.google.com";
		headers.put(HttpHeaders.REFERER.toLowerCase(), referer);
		
		//When
		Statistic statistic = service.mapFrom(headers, url);
		
		Assert.assertEquals(referer, statistic.getReferer());
	}

}
