package com.neueda.littleurl.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping(path = "/summary")
	public ResponseEntity<StatisticsSummaryDTO> getSummary() {
		logger.info(Constants.GETTING_STATISTICS_SUMMARY);

		StatisticsSummaryDTO summary = service.getStatisticsSummary();

		return ResponseEntity.ok().body(summary);
	}

	@GetMapping(path = "/summary/{code}")
	public ResponseEntity<StatisticsSummaryDTO> getSummaryByCode(@PathVariable String code) {
		logger.info(Constants.GETTING_STATISTICS_SUMMARY_BY_CODE, code);

		StatisticsSummaryDTO summary = service.getStatisticsSummaryByCode(code);

		return ResponseEntity.ok().body(summary);
	}

}