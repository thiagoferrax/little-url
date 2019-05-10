package com.neueda.littleurl.services;

import static com.neueda.littleurl.util.Constants.URL_CODE_SIZE;
import static com.neueda.littleurl.util.Constants.URL_NOT_FOUND_FOR_CODE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

@Service
public class UrlService {

	@Autowired
	private UrlRepository repository;

	public Url find(String code) {
		Optional<Url> optional = repository.findById(code);
		return optional.orElseThrow(() -> new UrlNotFoundException(URL_NOT_FOUND_FOR_CODE + code));
	}

	private Url recursiveInsert(String longUrl, int startIndex, int endIndex) {
		String code = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);

		Url url;
		try {
			url = find(code);
			
			if (!url.getLongUrl().equals(longUrl)) {
				url = recursiveInsert(longUrl, startIndex + 1, endIndex + 1);
			}			
		} catch (UrlNotFoundException e) {
			url = repository.save(new Url(code, longUrl));
		}		

		return url;
	}

	public Url findOrCreate(Url url) {
		String longUrl = url.getLongUrl();
		
		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;

		return recursiveInsert(longUrl, startIndex, endIndex);
	}
	
	public Url fromDTO(UrlDTO urlDto) {
		return new Url(urlDto.getCode(), urlDto.getLongUrl());
	}
}
