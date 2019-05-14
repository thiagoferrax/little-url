package com.neueda.littleurl.resources;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.dto.UrlUpdateDTO;
import com.neueda.littleurl.services.UrlService;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;
import com.neueda.littleurl.util.Constants;

@RestController
@RequestMapping(value = "/urls")
public class UrlResources {
	Logger logger = LoggerFactory.getLogger(UrlResources.class);

	@Autowired
	private UrlService service;

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> findAndRedirect(@PathVariable String code) {
		logger.info(Constants.FINDING_URL_FOR_REDIRECTING + code);

		ResponseEntity<?> responseEntity;

		try {
			Url url = service.find(code);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(url.getLongUrl()));
			responseEntity = new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

		} catch (UrlNotFoundException e) {
			logger.warn(Constants.URL_NOT_FOUND_FOR_REDIRECTING + code, e);
			
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}

	@RequestMapping(value = "/{code}/longUrl", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable String code) {
		logger.info(Constants.FINDING_LONG_URL + code);

		ResponseEntity<?> responseEntity;

		try {

			Url url = service.find(code);
			responseEntity = ResponseEntity.ok().body(url);
		} catch (UrlNotFoundException e) {
			logger.warn(Constants.LONG_URL_NOT_FOUND + code, e);

			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> findOrCreate(@Valid @RequestBody UrlDTO urlDto) {
		logger.info(Constants.FINDING_OR_CREATING_URL + urlDto);
		
		Url url = service.fromDTO(urlDto);
		url = service.findOrCreate(url);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(url.getCode())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@Valid @RequestBody UrlUpdateDTO urlDto, @PathVariable String code) {
		logger.info(Constants.UPDATING_URL + urlDto);
		
		urlDto.setCode(code);
		Url url = service.fromUpdateDTO(urlDto);

		url = service.update(url);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String code) {
		logger.info(Constants.REMOVING_URL + code);
		
		service.remove(code);

		return ResponseEntity.noContent().build();
	}
}