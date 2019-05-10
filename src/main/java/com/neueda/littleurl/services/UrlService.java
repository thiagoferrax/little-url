package com.neueda.littleurl.services;

import static com.neueda.littleurl.domains.Url.URL_CODE_SIZE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.littleurl.domains.Url;
import com.neueda.littleurl.helpers.UrlShortnerHelper;
import com.neueda.littleurl.repositories.UrlRepository;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

@Service
public class UrlService {

	public static final String URL_NOT_FOUND_FOR_CODE = "Url not found for code ";
	public static final String CODE_MUST_NOT_BE_NULL = "Code must not be null.";
	public static final String URL_MUST_NOT_BE_NULL = "Url must not be null.";
	public static final String LONG_URL_MUST_NOT_BE_NULL = "Long Url must not be null.";

	@Autowired
	private UrlRepository repository;

	public Url find(String code) {
		if (code == null) {
			throw new IllegalArgumentException(CODE_MUST_NOT_BE_NULL);
		}

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
		String longUrl = validate(url);
		
		int startIndex = 0;
		int endIndex = startIndex + URL_CODE_SIZE - 1;

		return recursiveInsert(longUrl, startIndex, endIndex);
	}

	private String validate(Url url) {
		if(url == null) {
			throw new IllegalArgumentException(URL_MUST_NOT_BE_NULL);
		}

		String longUrl = url.getLongUrl();
		if(longUrl == null) {
			throw new IllegalArgumentException(LONG_URL_MUST_NOT_BE_NULL);
		}
		return longUrl;
	}
}
