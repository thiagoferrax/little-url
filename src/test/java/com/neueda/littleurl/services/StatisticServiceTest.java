package com.neueda.littleurl.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.neueda.littleurl.dto.StatisticsDTO;
import com.neueda.littleurl.dto.StatisticsSummaryDTO;
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
	
	@Test
	public void whenGettingStatisticsSummaryReturnsResultsFromRepository() {
		Long numberOfHits = 3L;
		
		StatisticsDTO firefox = new StatisticsDTO("Firefox", 1L);
		StatisticsDTO chrome = new StatisticsDTO("Chrome", 2L);
		List<StatisticsDTO> browsers = Arrays.asList(new StatisticsDTO[] { firefox, chrome});

		StatisticsDTO computer = new StatisticsDTO("Computer", 1L);
		StatisticsDTO mobile = new StatisticsDTO("Mobile", 1L);
		StatisticsDTO tablet = new StatisticsDTO("Tablet", 1L);
		List<StatisticsDTO> deviceTypes = Arrays.asList(new StatisticsDTO[] { computer, mobile, tablet});

		Long totalOfLinuxHits = 3L;
		StatisticsDTO linux = new StatisticsDTO("Linux", totalOfLinuxHits);
		List<StatisticsDTO> operationSystems = Arrays.asList(new StatisticsDTO[] { linux });
		
		// Given
		Mockito.when(repository.getNumberOfHits()).thenReturn(numberOfHits);
		Mockito.when(repository.getBrowsers()).thenReturn(browsers);
		Mockito.when(repository.getDevicesTypes()).thenReturn(deviceTypes);
		Mockito.when(repository.getOperatingSystems()).thenReturn(operationSystems);

		//When
		StatisticsSummaryDTO statisticsSummary = service.getStatisticsSummary();
		
		//Then
		Assert.assertEquals(numberOfHits, statisticsSummary.getNumberOfHits());
		Assert.assertEquals(2, statisticsSummary.getBrowsers().size());
		Assert.assertEquals(3, statisticsSummary.getDevicesTypes().size());
		Assert.assertEquals(1, statisticsSummary.getOperatingSystems().size());
		Assert.assertEquals(totalOfLinuxHits, statisticsSummary.getOperatingSystems().get(0).getTotal());
	}
	
	@Test
	public void whenGettingStatisticsSummaryByCodeReturnsResultsFromRepository() {
		String code = "3077yW";
		Long numberOfHits = 3L;
		
		StatisticsDTO firefox = new StatisticsDTO("Firefox", 1L);
		StatisticsDTO chrome = new StatisticsDTO("Chrome", 2L);
		List<StatisticsDTO> browsers = Arrays.asList(new StatisticsDTO[] { firefox, chrome});

		StatisticsDTO computer = new StatisticsDTO("Computer", 1L);
		StatisticsDTO mobile = new StatisticsDTO("Mobile", 1L);
		StatisticsDTO tablet = new StatisticsDTO("Tablet", 1L);
		List<StatisticsDTO> deviceTypes = Arrays.asList(new StatisticsDTO[] { computer, mobile, tablet});

		Long totalOfLinuxHits = 2L;
		StatisticsDTO linux = new StatisticsDTO("Linux", totalOfLinuxHits);
		StatisticsDTO windows = new StatisticsDTO("Windows", 1L);
		List<StatisticsDTO> operationSystems = Arrays.asList(new StatisticsDTO[] { linux, windows });
		
		// Given
		Mockito.when(repository.getNumberOfHitsByCode(code)).thenReturn(numberOfHits);
		Mockito.when(repository.getBrowsersByCode(code)).thenReturn(browsers);
		Mockito.when(repository.getDevicesTypesByCode(code)).thenReturn(deviceTypes);
		Mockito.when(repository.getOperatingSystemsByCode(code)).thenReturn(operationSystems);

		//When
		StatisticsSummaryDTO statisticsSummary = service.getStatisticsSummaryByCode(code);
		
		//Then
		Assert.assertEquals(numberOfHits, statisticsSummary.getNumberOfHits());
		Assert.assertEquals(2, statisticsSummary.getBrowsers().size());
		Assert.assertEquals(3, statisticsSummary.getDevicesTypes().size());
		Assert.assertEquals(2, statisticsSummary.getOperatingSystems().size());
		Assert.assertEquals(totalOfLinuxHits, statisticsSummary.getOperatingSystems().get(0).getTotal());
	}

}
