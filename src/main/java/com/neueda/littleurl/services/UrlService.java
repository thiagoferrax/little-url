package com.neueda.littleurl.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;

@Service
public class UrlService {
	final static public int URL_CODE_SIZE = 6;
	
	@Autowired
	private UrlRepository repository;

	public Url find(String code) {
		Optional<Url> optional = repository.findById(code);
		return optional.orElse(null);
	}

	private Url recursiveInsert(String longUrl, int startIndex, int endIndex) {
		String code = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);

		Url url = find(code);
		if (url == null) {
			url = repository.save(new Url(code, longUrl));
		} else if (!url.getLongUrl().equals(longUrl)) {
			url = recursiveInsert(longUrl, endIndex + 1, endIndex + URL_CODE_SIZE);
		}

		return url;
	}

	public Url findOrCreate(String longUrl) {
		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE;
		
		Url url = recursiveInsert(longUrl, startIndex, endIndex);

		return url;
	}
}
