package com.neueda.littleurl.resources;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

@RestController
@RequestMapping(value = "/urls")
public class UrlResources {
	Logger logger = LoggerFactory.getLogger(UrlResources.class);
	
	@Autowired
	private UrlService service;

	@RequestMapping(value = "/{code}", method = GET)
	public ResponseEntity<?> findAndRedirect(@PathVariable String code) {
		ResponseEntity<?> responseEntity;
		
		try {
			Url url = service.find(code);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(url.getLongUrl()));
			responseEntity = new ResponseEntity<>(headers, MOVED_PERMANENTLY);

		} catch (UrlNotFoundException e) {
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}
	
	@RequestMapping(value = "/{code}/longUrl", method = GET)
	public ResponseEntity<?> find(@PathVariable String code) {
		ResponseEntity<?> responseEntity;

		try {
			Url url = service.find(code);
			responseEntity = ResponseEntity.ok().body(url);
		} catch (UrlNotFoundException e) {
			responseEntity = ResponseEntity.notFound().build();
		}

		return responseEntity;
	}

	@RequestMapping(value = "/", method = POST)
	public ResponseEntity<?> findOrCreate(@Valid @RequestBody UrlDTO urlDto) {
		Url url = service.fromDTO(urlDto);
		url = service.findOrCreate(url);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(url.getCode())
				.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/", method = PUT)
	public ResponseEntity<?> update(@Valid @RequestBody UrlUpdateDTO urlDto) {
		
		Url url = service.fromUpdateDTO(urlDto);
		url = service.update(url);
		
		return ResponseEntity.ok().body(url);
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable String code) {
		
		service.remove(code);
		
		return ResponseEntity.ok().build();
	}
}
