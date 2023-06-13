package com.thiagoferraz.littleurl.services.exceptions;

public class UrlNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UrlNotFoundException(String message) {
		super(message);
	}
	
	public UrlNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
