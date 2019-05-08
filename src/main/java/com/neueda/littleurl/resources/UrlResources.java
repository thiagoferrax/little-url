package com.neueda.littleurl.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.littleurl.domains.Url;

@RestController
@RequestMapping(value="/")
public class UrlResources {
	
	@RequestMapping(method=RequestMethod.GET)
	public Url findByCode() {
		Url url = new Url("abcde", "www.neueda.com");	
		return url;
	}	
}
