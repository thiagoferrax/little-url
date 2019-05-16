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
import com.neueda.littleurl.util.Constants;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class StatisticService {
	
	Logger logger = LoggerFactory.getLogger(StatisticService.class);
	
	@Autowired
	private StatisticRepository repository;

	public Statistic create(Statistic statistic) {
		logger.info(Constants.CREATING_A_STATISTIC + statistic);
		
		statistic.setId(null);
		return repository.save(statistic);		
	}
	
	public Statistic mapFrom(Map<String, String> headers, Url url) {

		String userAgentString = headers.get(HttpHeaders.USER_AGENT.toLowerCase());
		
		logger.info(Constants.MAPPING_STATISTIC_FROM_HEADERS + userAgentString);

		UserAgent agent = UserAgent.parseUserAgentString(userAgentString);
		String deviceType = agent.getOperatingSystem().getDeviceType().getName();
		String browser = agent.getBrowser().getName();
		String operatingSystem = agent.getOperatingSystem().getName();

		Statistic statistic = new Statistic(browser, deviceType, operatingSystem, url);
		
		return statistic;
	}
}
