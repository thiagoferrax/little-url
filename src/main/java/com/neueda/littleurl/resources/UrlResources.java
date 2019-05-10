package com.neueda.littleurl.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.services.UrlService;

@RestController
@RequestMapping(value = "/urls")
public class UrlResources {

	@Autowired
	private UrlService service;

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable String code) {
		Url url = service.find(code);
		return ResponseEntity.ok().body(url);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> save(@Valid @RequestBody UrlDTO urlDto) {
		Url url = service.fromDTO(urlDto);
		url = service.findOrCreate(url);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(url.getCode()).toUri();	
		return ResponseEntity.created(uri).build();
	}
}
