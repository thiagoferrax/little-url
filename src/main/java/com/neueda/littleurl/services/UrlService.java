package com.neueda.littleurl.services;

import static com.neueda.littleurl.domains.Url.URL_CODE_SIZE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;

@Service
public class UrlService {
	@Autowired
	private UrlRepository repository;

	public Url find(String code) {
		return repository.findById(code).orElse(null);
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

	public Url findOrCreate(Url url) {
		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;
		String longUrl = url.getLongUrl();		
		
		return recursiveInsert(longUrl, startIndex, endIndex);
	}
}
