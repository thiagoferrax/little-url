package com.thiagoferraz.littleurl.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import com.thiagoferraz.littleurl.domain.Statistic;
import com.thiagoferraz.littleurl.domain.Url;
import com.thiagoferraz.littleurl.repositories.StatisticRepository;

@RunWith(Parameterized.class)
public class StatisticServiceParameterizedTest {

	@InjectMocks
	private StatisticService service;

	@Mock
	private StatisticRepository repository;

	@Parameter(value = 0)
	public String userAgent;

	@Parameter(value = 1)
	public String browser;

	@Parameter(value = 2)
	public String deviceType;

	@Parameter(value = 3)
	public String operationSystem;

	@Parameter(value = 4)
	public String scenary;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Parameters(name = "Test {index} = {4}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
				{ "Mozilla/5.0 (Android; Mobile; rv:24.0) Gecko/24.0 Firefox/24.0", "Firefox Mobile", "Mobile",
						"Android Mobile", "When using Firefox through a mobile phone with Android operating system." },
				{ "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36",
						"Chrome 60", "Computer", "Windows 10",
						"When using Chrome through a personal computer with Windows operating system." },
				{ "Mozilla/5.0 (iPad; CPU OS 9_3_5 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13G36 Safari/601.1",
						"Mobile Safari", "Tablet", "iOS 9 (iPad)",
						"When using Safari through an iPad with Mac operating system." },
				{ "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.157 Safari/537.36",
						"Chrome 44", "Computer", "Linux",
						"When using Chrome through a personal computer with Linux operating system." },
				{ "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322)", "Internet Explorer 6",
						"Computer", "Windows XP",
						"When using Internet Explorer through a personal computer with Windows XP operating system." },
				{ "Mozilla/5.0 (iPhone; CPU iPhone OS 11_4_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.0 Mobile/15E148 Safari/604.1",
						"Mobile Safari", "Mobile", "iOS 11 (iPhone)",
						"When using Safari through a iPhone with iOS 11 operating system." },
				{ "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393",
						"Microsoft Edge (layout engine 14)", "Computer", "Windows 10",
						"When using Edge through a personal computer with Windows 10 operating system." },
				{ "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.73 Safari/537.36 OPR/34.0.2036.25",
						"Opera 34", "Computer", "Windows 7",
						"When using Opera through a personal computer with Windows 7 operating system." },
				{ "Mozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 635) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537",
						"IE Mobile 11", "Mobile", "Windows Phone 8.1",
						"When using Internet Explorer through a mobile phone with Windows Phone 8.1 operating system." },
				{ "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; en-en) AppleWebKit/533.19.4 (KHTML, like Gecko) Version/5.0.3 Safari/533.19.4",
						"Safari 5", "Computer", "Mac OS X",
						"When using Internet Explorer through a mobile phone with Windows Phone 8.1 operating system." } });
	}

	@Test
	public void whenMappingFromHeadersAndStatisticReturnsStatistic() {
		// Given
		Url url = new Url("3077yW", "http://www.thiagoferraz.com");
		Map<String, String> headers = new HashMap<>();
		headers.put(HttpHeaders.USER_AGENT.toLowerCase(), userAgent);

		// When
		Statistic statistic = service.mapFrom(headers, url);

		// Then
		Assert.assertEquals(browser, statistic.getBrowser());
		Assert.assertEquals(deviceType, statistic.getDeviceType());
		Assert.assertEquals(operationSystem, statistic.getOperatingSystem());
		Assert.assertEquals(url, statistic.getUrl());
	}

}
