package com.neueda.littleurl.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.repositories.UrlRepository;

@Service
public class UrlService {
	
	@Autowired
	private UrlRepository repository;
	
	public Url find(String code) {
		Optional<Url> optional = repository.findById(code);
		return optional.orElse(null);
	}
}
