package com.neueda.littleurl.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.StatisticsDTO;
import com.neueda.littleurl.dto.StatisticsSummaryDTO;
import com.neueda.littleurl.repositories.StatisticRepository;
import com.neueda.littleurl.util.Constants;

import eu.bitwalker.useragentutils.UserAgent;

@Service
public class StatisticService {

	Logger logger = LoggerFactory.getLogger(StatisticService.class);

	@Autowired
	private StatisticRepository repository;

	public Statistic create(Statistic statistic) {
		logger.info(Constants.CREATING_A_STATISTIC, statistic);

		statistic.setId(null);
		return repository.save(statistic);
	}

	public Statistic mapFrom(Map<String, String> headers, Url url) {

		String userAgentString = headers.get(HttpHeaders.USER_AGENT.toLowerCase());

		userAgentString = userAgentString.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");
		
		logger.info(Constants.MAPPING_STATISTIC_FROM_HEADERS, userAgentString);

		UserAgent agent = UserAgent.parseUserAgentString(userAgentString);
		String deviceType = agent.getOperatingSystem().getDeviceType().getName();
		String browser = agent.getBrowser().getName();
		String operatingSystem = agent.getOperatingSystem().getName();

		return new Statistic(browser, deviceType, operatingSystem, url);
	}

	public StatisticsSummaryDTO getStatisticsSummary() {
		logger.info(Constants.GETTING_STATISTICS_SUMMARY);

		Long numberOfHits = repository.getNumberOfHits();
		List<StatisticsDTO> browsers = repository.getBrowsers();
		List<StatisticsDTO> devicesTypes = repository.getDevicesTypes();
		List<StatisticsDTO> operatingSystems = repository.getOperatingSystems();

		return new StatisticsSummaryDTO(numberOfHits, browsers, devicesTypes, operatingSystems);
	}

	public StatisticsSummaryDTO getStatisticsSummaryByCode(String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.GETTING_STATISTICS_SUMMARY_BY_CODE, code);

		Long numberOfHits = repository.getNumberOfHitsByCode(code);
		List<StatisticsDTO> browsers = repository.getBrowsersByCode(code);
		List<StatisticsDTO> devicesTypes = repository.getDevicesTypesByCode(code);
		List<StatisticsDTO> operatingSystems = repository.getOperatingSystemsByCode(code);

		return new StatisticsSummaryDTO(numberOfHits, browsers, devicesTypes, operatingSystems);
	}
}
