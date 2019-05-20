package com.neueda.littleurl.helpers.exceptions;

public class UrlShortnerHelperException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UrlShortnerHelperException(String message) {
		super(message);
	}
	
	public UrlShortnerHelperException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
