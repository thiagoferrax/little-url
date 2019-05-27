package com.neueda.littleurl.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
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

		StatisticsSummaryDTO summaryDTO = new StatisticsSummaryDTO();
		summaryDTO.setNumberOfHits(repository.getNumberOfHits());
		summaryDTO.setBrowsers(repository.getBrowsers());
		summaryDTO.setDevicesTypes(repository.getDevicesTypes());
		summaryDTO.setOperatingSystems(repository.getOperatingSystems());

		return summaryDTO;
	}

	public StatisticsSummaryDTO getStatisticsSummaryByCode(String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.GETTING_STATISTICS_SUMMARY_BY_CODE, code);

		StatisticsSummaryDTO summaryDTO = new StatisticsSummaryDTO();
		summaryDTO.setNumberOfHits(repository.getNumberOfHitsByCode(code));
		summaryDTO.setBrowsers(repository.getBrowsersByCode(code));
		summaryDTO.setDevicesTypes(repository.getDevicesTypesByCode(code));
		summaryDTO.setOperatingSystems(repository.getOperatingSystemsByCode(code));

		return summaryDTO;
	}
}
