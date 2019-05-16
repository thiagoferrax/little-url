package com.neueda.littleurl.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.repositories.StatisticRepository;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class StatisticService {
	
	Logger logger = LoggerFactory.getLogger(StatisticService.class);
	
	@Autowired
	private StatisticRepository repository;

	public Statistic create(Statistic statistic) {
		statistic.setId(null);
		return repository.save(statistic);		
	}
	
	public Statistic mapFrom(Map<String, String> headers, Url url) {
		
		UserAgent agent = UserAgent.parseUserAgentString(headers.get(HttpHeaders.USER_AGENT.toLowerCase()));
		String deviceType = agent.getOperatingSystem().getDeviceType().getName();
		String browser = agent.getBrowser().getName();
		String operatingSystem = agent.getOperatingSystem().getName();

		Statistic statistic = new Statistic();
		statistic.setDeviceType(deviceType);
		statistic.setOperatingSystem(operatingSystem);
		statistic.setBrowser(browser);
		statistic.setUrl(url);
		
		return statistic;
	}
}
