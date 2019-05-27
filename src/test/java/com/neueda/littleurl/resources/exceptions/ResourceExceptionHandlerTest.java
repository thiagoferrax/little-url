package com.neueda.littleurl.resources.exceptions;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.neueda.littleurl.dto.UrlDTO;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;

public class ResourceExceptionHandlerTest {

	@Mock
	HttpServletRequest httpServletRequest;

	@Mock
	BindingResult bindingResult;

	@InjectMocks
	ResourceExceptionHandler handler;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenHandlingANotFoundExceptionReturnsStatusNotFoundAndExpectedMessage() {
		String message = "Url not found exception.";
		UrlNotFoundException e = new UrlNotFoundException(message);
		ResponseEntity<StandardError> urlNotFound = handler.urlNotFound(e, httpServletRequest);

		Assert.assertEquals(HttpStatus.NOT_FOUND, urlNotFound.getStatusCode());
		Assert.assertEquals(message, urlNotFound.getBody().getMessage());
		Assert.assertEquals(Integer.valueOf(HttpStatus.NOT_FOUND.value()), urlNotFound.getBody().getStatus());
	}
	
	@Test
	public void whenHandlingAMethodArgumentNotValidExceptionReturnsStatusBadRequestAndExpectedMessage() throws NoSuchMethodException, SecurityException {
		Method method = UrlDTO.class.getMethod("setLongUrl", String.class);
		int parameterIndex = 0;
		MethodParameter parameter = new MethodParameter(method, parameterIndex);
		
		MethodArgumentNotValidException e = new MethodArgumentNotValidException(parameter, bindingResult);
		ResponseEntity<ValidationError> validationError = handler.validation(e, httpServletRequest);

		Assert.assertEquals(HttpStatus.BAD_REQUEST, validationError.getStatusCode());
		Assert.assertEquals(Integer.valueOf(HttpStatus.BAD_REQUEST.value()), validationError.getBody().getStatus());
	}

}
