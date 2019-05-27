package com.neueda.littleurl.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domain.Url;
import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;
import com.neueda.littleurl.util.Constants;

@Service
public class UrlService {

	Logger logger = LoggerFactory.getLogger(UrlService.class);

	@Autowired
	private UrlRepository repository;

	public Url find(String urlCode) {

		final String code = urlCode.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.FINDING_URL_BY_CODE, code);

		Optional<Url> optional = repository.findById(code);
		return optional.orElseThrow(() -> new UrlNotFoundException(Constants.URL_NOT_FOUND_FOR_CODE + code));
	}

	private Url recursiveInsert(String longUrl, int startIndex, int endIndex) {

		longUrl = longUrl.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.RECURSIVE_INSERT, longUrl);

		String code = UrlShortnerHelper.generateShortURL(longUrl, startIndex, endIndex);

		Url url;
		try {
			url = find(code);

			if (!url.getLongUrl().equals(longUrl)) {
				logger.info(Constants.FOUND_DIFFERENT_URLS_FOR_SAME_CODE, code);

				url = recursiveInsert(longUrl, startIndex + 1, endIndex + 1);
			}
		} catch (UrlNotFoundException e) {
			logger.warn(Constants.URL_NOT_FOUND_CREATING_NEW_ONE, code, e);

			url = repository.save(new Url(code, longUrl));
		}

		return url;
	}

	public Url findOrCreate(Url url) {
		String longUrl = url.getLongUrl();

		logger.info(Constants.FINDING_OR_CREATING_URL, longUrl);

		int startIndex = 0;
		int endIndex = startIndex + Constants.URL_CODE_SIZE - 1;

		return recursiveInsert(longUrl, startIndex, endIndex);
	}

	public Url update(Url url) {
		logger.info(Constants.UPDATING_URL, url);

		Url foundUrl = find(url.getCode());
		url.setCreatedAt(foundUrl.getCreatedAt());

		return repository.save(url);
	}

	public void remove(String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.REMOVING_URL, code);

		find(code);
		repository.deleteById(code);
	}

	public Url fromDTO(UrlDTO urlDto) {
		return new Url(urlDto.getCode(), urlDto.getLongUrl());
	}
}
