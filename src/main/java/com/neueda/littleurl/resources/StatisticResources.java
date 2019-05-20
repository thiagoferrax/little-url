package com.neueda.littleurl.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.littleurl.dto.StatisticsSummaryDTO;
import com.neueda.littleurl.services.StatisticService;
import com.neueda.littleurl.util.Constants;

@RestController
@RequestMapping(value = "/statistics")
public class StatisticResources {
	Logger logger = LoggerFactory.getLogger(StatisticResources.class);

	@Autowired
	private StatisticService service;

	@RequestMapping(value = "/summary", method = RequestMethod.GET)
	public ResponseEntity<?> getSummary() {
		logger.info(Constants.GETTING_STATISTICS_SUMMARY);

		StatisticsSummaryDTO summary = service.getStatisticsSummary();

		return ResponseEntity.ok().body(summary);
	}

	@RequestMapping(value = "/summary/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> getSummaryByCode(@PathVariable String code) {
		logger.info(Constants.GETTING_STATISTICS_SUMMARY_BY_CODE + code);

		StatisticsSummaryDTO summary = service.getStatisticsSummaryByCode(code);

		return ResponseEntity.ok().body(summary);
	}

}