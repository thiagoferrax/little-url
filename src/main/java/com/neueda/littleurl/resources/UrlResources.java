package com.neueda.littleurl.resources;

import java.net.URI;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neueda.littleurl.domain.Statistic;
import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.dto.UrlUpdateDTO;
import com.neueda.littleurl.services.StatisticService;
import com.neueda.littleurl.services.UrlService;
import com.neueda.littleurl.util.Constants;

@RestController
@RequestMapping(value = "/urls")
public class UrlResources {
	Logger logger = LoggerFactory.getLogger(UrlResources.class);

	@Autowired
	private UrlService service;

	@Autowired
	private StatisticService statisticService;

	@GetMapping(path = "/{code}")
	public ResponseEntity<Url> findAndRedirect(@PathVariable String code,
			@RequestHeader Map<String, String> headersMap) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.FINDING_URL_FOR_REDIRECTING, code);

		Url url = service.find(code);

		Statistic statistic = statisticService.mapFrom(headersMap, url);
		statisticService.create(statistic);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(url.getLongUrl()));

		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping(path = "/{code}/longUrl")
	public ResponseEntity<Url> find(@PathVariable String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.FINDING_LONG_URL, code);

		ResponseEntity<Url> responseEntity;

		Url url = service.find(code);
		responseEntity = ResponseEntity.ok().body(url);

		return responseEntity;
	}

	@PostMapping(value = "/")
	public ResponseEntity<Url> findOrCreate(@Valid @RequestBody UrlDTO urlDto) {
		logger.info(Constants.FINDING_OR_CREATING_URL, urlDto);

		Url url = service.fromDTO(urlDto);
		url = service.findOrCreate(url);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(url.getCode())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{code}")
	public ResponseEntity<Url> update(@Valid @RequestBody UrlUpdateDTO urlDto, @PathVariable String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.UPDATING_URL, urlDto);

		urlDto.setCode(code);
		Url url = service.fromUpdateDTO(urlDto);

		service.update(url);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{code}")
	public ResponseEntity<Url> delete(@PathVariable String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.REMOVING_URL, code);

		service.remove(code);

		return ResponseEntity.noContent().build();
	}
}