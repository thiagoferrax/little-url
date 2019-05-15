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
		
		headers.forEach((key, value) -> {
			logger.info(String.format("Header '%s' = %s", key, value));
	    });
		
		Statistic statistic = new Statistic();
		statistic.setReferer(headers.get(HttpHeaders.REFERER.toLowerCase()));
		
		return statistic;
	}
}
