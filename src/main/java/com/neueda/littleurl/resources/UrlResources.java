package com.neueda.littleurl.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.services.UrlService;

@RestController
@RequestMapping(value="/")
public class UrlResources {
	
	@Autowired
	private UrlService service;

	/*
	 * @RequestMapping(value="/shorten", method=RequestMethod.POST) public
	 * ResponseEntity<?> save(@RequestBody String longUrl) { Url url =
	 * service.findOrCreate(longUrl); return ResponseEntity.ok().body(url); }
	 */
	
	@RequestMapping(value="/{code}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable String code) {
		Url url = service.find(code);
		return ResponseEntity.ok().body(url);
	}		
}
