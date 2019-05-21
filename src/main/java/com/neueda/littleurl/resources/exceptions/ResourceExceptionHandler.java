package com.neueda.littleurl.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<StandardError> urlNotFound(UrlNotFoundException e, HttpServletRequest httpServletRequest) {
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
